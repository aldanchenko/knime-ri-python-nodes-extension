package com.rationalinsights.generator.knime;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Description of plugin.xml
 */
public class KnimeNode {

    @XmlAttribute(name = "category-path")
    private String categoryPath;

    @XmlAttribute(name = "factory-class")
    private String factoryClass;
}
