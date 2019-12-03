package com.rationalinsights.generator.model;

import java.util.List;

/**
 * Rationalinsights Knime node description (handler).
 */
public class Node extends AbstractTreeElement {
    private String parentCatalog;
    private String pythonScriptPath;
    private String shortDescription;
    private String fullDescription;
    private List<InputPort> inputPorts;
    private List<OutputPort> outputPorts;

    public String getParentCatalog() {
        return parentCatalog;
    }

    public void setParentCatalog(String parentCatalog) {
        this.parentCatalog = parentCatalog;
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
