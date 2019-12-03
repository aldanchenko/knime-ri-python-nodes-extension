package com.rationalinsights.generator.model;

/**
 * Basic UI Knime tree element with element name and icon.
 */
public abstract class AbstractTreeElement {
    private String name;
    private String icon;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
