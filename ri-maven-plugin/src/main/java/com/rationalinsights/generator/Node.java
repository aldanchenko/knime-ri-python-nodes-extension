package com.rationalinsights.generator;

import java.util.List;

//"Rationalinsights.Models.InputNode1": {
//        'icon_path'          : 'Path to node icon',
//        'python_script_path' :'Path to Python Script',

//        'input'              : {
//        'InputTable1' :'Description of InputTable1',
//        'InputTable2' :'Description of InputTable2'
//        },

//        'output'             : {
//        'OutputTable1' : 'Description of OutputTable1'
//        'OutputTable2' :'Description of OutputTable2'
//        },
public class Node {
    private String organization;
    private String catalog;
    private String name;
    private String iconPath;
    private String pythonScriptPath;
    private List<String> inputList;
    private List<String> outputList;

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

    public List<String> getInputList() {
        return inputList;
    }

    public void setInputList(List<String> inputList) {
        this.inputList = inputList;
    }

    public List<String> getOutputList() {
        return outputList;
    }

    public void setOutputList(List<String> outputList) {
        this.outputList = outputList;
    }
}
