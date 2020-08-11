package com.danger.kotlin.plugin.test.internal

import javax.xml.parsers.DocumentBuilderFactory

object XmlParserFactory {

    fun createDocumentBuilderFactory() = DocumentBuilderFactory.newInstance().apply {
        isValidating = false
        isNamespaceAware = true
        setFeature("http://xml.org/sax/features/namespaces", false)
        setFeature("http://xml.org/sax/features/validation", false)
        setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false)
        setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
    }
}
