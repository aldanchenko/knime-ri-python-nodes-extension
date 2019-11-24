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
    private List<InPort> inPorts;
    private List<OutPort> outPorts;

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

    public List<InPort> getInPorts() {
        return inPorts;
    }

    public void setInPorts(List<InPort> inPorts) {
        this.inPorts = inPorts;
    }

    public List<OutPort> getOutPorts() {
        return outPorts;
    }

    public void setOutPorts(List<OutPort> outPorts) {
        this.outPorts = outPorts;
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
