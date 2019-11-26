package com.rationalinsights.generator.knime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Description of plugin.xml
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class KnimeNode {

    @XmlAttribute(name = "category-path")
    private String categoryPath;

    @XmlAttribute(name = "factory-class")
    private String factoryClass;

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public String getFactoryClass() {
        return factoryClass;
    }

    public void setFactoryClass(String factoryClass) {
        this.factoryClass = factoryClass;
    }
}
