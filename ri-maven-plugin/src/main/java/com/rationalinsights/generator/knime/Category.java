package com.rationalinsights.generator.knime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Description of plugin.xml extension category.
 */
@XmlAccessorType(XmlAccessType.FIELD)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
