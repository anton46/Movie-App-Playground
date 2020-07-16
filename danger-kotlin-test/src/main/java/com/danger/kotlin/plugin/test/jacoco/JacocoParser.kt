package com.danger.kotlin.plugin.test.jacoco

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

    fun parse(filePath: String): Coverage? {
        val xmlFile = File(filePath)
        val factory = XmlParserFactory.createDocumentBuilderFactory()
        val builder = factory.newDocumentBuilder()
        val xmlInputString = InputSource(StringReader(xmlFile.readText()))
        val document = builder.parse(xmlInputString)
        val rootElement = document.documentElement
        var projectCoverage: ProjectCoverage? = null
        val classCoverage: List<ClassCoverage> = listOf()
        for (x in 0 until rootElement.childNodes.length) {
            with(rootElement.childNodes.item(x) as Element) {
                if (this.nodeType == Node.ELEMENT_NODE) {
                    if (tagName == COUNTER_TAG_NAME && getAttribute(COUNTER_ATTRIBUTE_TYPE) == COUNTER_ATTRIBUTE_INSTRUCTION_VALUE) {
                        projectCoverage = totalProjectCoverage()
                    }
                }
            }
        }

        return projectCoverage?.let {
            Coverage(it, classCoverage)
        }
    }

    private fun Element.totalProjectCoverage(): ProjectCoverage {
        val missed = getAttribute(COUNTER_ATTRIBUTE_MISSED).toDouble()
        val covered = getAttribute(COUNTER_ATTRIBUTE_COVERED).toDouble()
        val totalInstruction = missed + covered
        val coveragePercentage = round(covered * 100 / totalInstruction)
        return ProjectCoverage(coveragePercentage)
    }
}
