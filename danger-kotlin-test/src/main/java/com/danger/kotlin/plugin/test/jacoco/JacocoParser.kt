package com.danger.kotlin.plugin.test.jacoco

import com.danger.kotlin.plugin.test.TestPlugin.onGit
import com.danger.kotlin.plugin.test.internal.XmlParserFactory
import com.danger.kotlin.plugin.test.jacoco.model.ClassCoverage
import com.danger.kotlin.plugin.test.jacoco.model.Coverage
import com.danger.kotlin.plugin.test.jacoco.model.ProjectCoverage
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.File
import java.io.StringReader
import kotlin.math.round

object JacocoParser {

    private const val COUNTER_TAG_NAME = "counter"
    private const val COUNTER_ATTRIBUTE_TYPE = "type"
    private const val COUNTER_ATTRIBUTE_MISSED = "missed"
    private const val COUNTER_ATTRIBUTE_COVERED = "covered"
    private const val COUNTER_ATTRIBUTE_INSTRUCTION_VALUE = "INSTRUCTION"
    private const val CLASS_TAG_NAME = "class"
    private const val CLASS_ATTRIBUTE_NAME = "name"
    private const val PATH_DELIMITER = "/java/"

    private val classesCoverage = mutableSetOf<ClassCoverage>()

    fun parse(filePath: String): Coverage? {
        try {
            val xmlFile = File(filePath)
            val document = XmlParserFactory
                .createDocumentBuilderFactory()
                .newDocumentBuilder().let {
                    val xmlInputString = InputSource(StringReader(xmlFile.readText()))
                    it.parse(xmlInputString)
                }

            val rootElement = document.documentElement
            val projectCoverage = ProjectCoverage(getCoverage(rootElement))

            onGit {
                println(modifiedFiles)
                val affectedClasses = (modifiedFiles + createdFiles)
                    .filter {
                        it.endsWith(".kt")
                    }
                    .map {
                        it.split(".").first().split(PATH_DELIMITER)[1]
                    }
                    .toSet()

                for (x in 0 until rootElement.childNodes.length) {
                    with(rootElement.childNodes.item(x) as Element) {
                        if (this.nodeType == Node.ELEMENT_NODE) {
                            collectAffectedFilesCoverage(this, affectedClasses)
                        }
                    }
                }
            }
            println(classesCoverage)
            return Coverage(projectCoverage, classesCoverage)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun getCoverage(rootElement: Element): Double {
        for (x in 0 until rootElement.childNodes.length) {
            with(rootElement.childNodes.item(x) as Element) {
                if (this.nodeType == Node.ELEMENT_NODE) {
                    if (tagName == COUNTER_TAG_NAME && getAttribute(COUNTER_ATTRIBUTE_TYPE) == COUNTER_ATTRIBUTE_INSTRUCTION_VALUE) {
                        return coverage()
                    }
                }
            }
        }
        return 0.0
    }

    private fun collectAffectedFilesCoverage(
        rootElement: Element?,
        affectedFilesPath: Set<String>
    ) {
        if (rootElement == null) return

        for (x in 0 until rootElement.childNodes.length) {
            with(rootElement.childNodes.item(x) as Element) {
                if (this.nodeType == Node.ELEMENT_NODE) {
                    if (tagName == CLASS_TAG_NAME && affectedFilesPath.contains(getAttribute(CLASS_ATTRIBUTE_NAME))) {
                        val coverage = getCoverage(this)
                        classesCoverage.add(
                            ClassCoverage(
                                getAttribute(CLASS_ATTRIBUTE_NAME),
                                coverage
                            )
                        )
                    } else {
                        collectAffectedFilesCoverage(this, affectedFilesPath)
                    }
                }
            }
        }
    }

    private fun Element.coverage(): Double {
        val missed = getAttribute(COUNTER_ATTRIBUTE_MISSED).toDouble()
        val covered = getAttribute(COUNTER_ATTRIBUTE_COVERED).toDouble()
        val totalInstruction = missed + covered
        return round(covered * 100 / totalInstruction)
    }
}
