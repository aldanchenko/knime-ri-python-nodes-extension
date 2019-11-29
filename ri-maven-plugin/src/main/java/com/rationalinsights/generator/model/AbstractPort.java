package com.rationalinsights.generator.model;

/**
 * Abstract Knime plugin node port handler.
 */
public class AbstractPort {

    private int index;
    private String name;
    private String description;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
