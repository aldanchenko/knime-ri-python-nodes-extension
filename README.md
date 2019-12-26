Skeleton for generate Python based KNIME nodes with sample code as described [here](https://tech.knime.org/developer-guide).

This project uses [Eclipse Tycho](https://www.eclipse.org/tycho/).

This project uses [Knime Python Wrapper](https://github.com/3D-e-Chem/knime-python-wrapper).

# Installation

Requirements:

* KNIME, https://www.knime.org, version 4.0 or higher
* Maven, http://maven.apache.org

# Clone

Clone this repository:
```
git clone https://github.com/aldanchenko/knime-ri-python-nodes-extension
```

# Configure nodes.json file

Configure base catalog name, icon and description. Configure sub catalogs, nodes and specify your Python scripts:
```
{
  "name": "Your root catalog name",
  "icon": "./icons/python_48_48.png",
  "description": "Your description",
  "catalogs": [
    {
      "name": "YourSubCatalogName1",
      "icon": "./icons/devops.png",
      "description": "Python Display Nodes"
    },
    {
      "name": "YourSubCatalogName2",
      "icon": "./icons/developer.png",
      "description": "Python Models Nodes"
    }
  ],
  "nodes": [
    {
      "parentCatalog": "YourSubCatalogName1",
      "name": "TwoInputTwoOutputPythonNode",
      "icon": "./icons/icon1.png",
      "pythonScriptPath": "./python_scripts/hello_world_1.py",
      "shortDescription": "Test Knime Python node",
      "fullDescription": "Test Knime Python node",
      "inputPorts": [
        {
          "index": 0,
          "name": "input_table1",
          "description": "inPort1 desc"
        },
        {
          "index": 1,
          "name": "input_table2",
          "description": "inPort2 desc"
        }
      ],
      "outputPorts": [
        {
          "index": 0,
          "name": "output_table1",
          "description": "output_table1 desc"
        },
        {
          "index": 1,
          "name": "output_table2",
          "description": "output_table2 desc"
        }
      ]
    },
    {
      "parentCatalog": "YourSubCatalogName2",
      "name": "OneInputOneOutputPythonNode",
      "icon": "./icons/icon2.png",
      "pythonScriptPath": "./python_scripts/hello_world_2.py",
      "shortDescription": "Test Knime Python node",
      "fullDescription": "Test Knime Python node",
      "inputPorts": [
        {
          "index": 0,
          "name": "input_table",
          "description": "inPort1 desc"
        }
      ],
      "outputPorts": [
        {
          "index": 0,
          "name": "output_table",
          "description": "inPort2 desc"
        }
      ]
    }
  ]
}
```

Please, note node.parentCatalog must match one of the catalogs defined in catalogs array.

# Build

To build the node extension and verify the tests run with the following command:
```
mvn clean package
```

# Install extension to KNIME

Steps to get the generated KNIME nodes extension inside KNIME:


1. Goto Help > Install new software ... menu
2. Press add button
3. Fill text fields with url of update site which contains this node.
4. Select --all sites-- in `work with` pulldown
5. Select the node
6. Install software
7. Restart KNIME

# Usage

1. Create a new KNIME workflow.
2. Find node in Node navigator panel.
3. Drag node to workflow canvas.