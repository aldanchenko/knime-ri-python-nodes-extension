package com.rationalinsights.generator.model;

import java.util.List;

/**
 * Rationalinsights Knime node description (handler).
 */
public class Node {
    private String organization;
    private String catalog;
    private String name;
    private String iconPath;
    private String pythonScriptPath;
    private String shortDescription;
    private String fullDescription;
    private List<InputPort> inputPorts;
    private List<OutputPort> outputPorts;

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getPythonScriptPath() {
        return pythonScriptPath;
    }

    public void setPythonScriptPath(String pythonScriptPath) {
        this.pythonScriptPath = pythonScriptPath;
    }

    public List<InputPort> getInputPorts() {
        return inputPorts;
    }

    public void setInputPorts(List<InputPort> inputPorts) {
        this.inputPorts = inputPorts;
    }

    public List<OutputPort> getOutputPorts() {
        return outputPorts;
    }

    public void setOutputPorts(List<OutputPort> outputPorts) {
        this.outputPorts = outputPorts;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }
}
