package com.rationalinsights.generator.knime;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Description of plugin.xml extension category.
 */
public class Category {

    @XmlAttribute
    private String description;

    @XmlAttribute
    private String icon;

    @XmlAttribute(name = "level-id")
    private String levelId;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String path;
}
