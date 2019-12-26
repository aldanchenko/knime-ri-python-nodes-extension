package com.${rootPackageName}.${nodePackageName};

import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.data.def.IntCell;
import org.knime.core.data.def.StringCell;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.port.PortType;

import org.knime.core.node.NodeLogger;

import nl.esciencecenter.e3dchem.python.PythonWrapperNodeModel;

/**
 * This is the model implementation of ${nodeName}PythonNodesExtension.
 *
 */
public class ${nodeName}Model extends PythonWrapperNodeModel<${nodeName}Config> {

    private static final NodeLogger LOGGER = NodeLogger.getLogger(${nodeName}Model.class);

    /**
     * Constructor for the node model.
     */
    protected ${nodeName}Model() {
        // TODO one incoming port and one outgoing port is assumed

        super(new PortType[]{
                    <#list inputPorts as inputPort>
                        BufferedDataTable.TYPE,
                    </#list>
                    },
                    new PortType[]{
                    <#list outputPorts as outputPort>
                        BufferedDataTable.TYPE,
                    </#list>
                    });

        python_code_filename = "${pythonScript}";
    }

    @Override
    protected ${nodeName}Config createConfig() {
        return new ${nodeName}Config();
    }

    @Override
    protected DataTableSpec[] getOutputSpecs(DataTableSpec[] inSpecs) {
        // Columns returned by Python script, allows connection of downstream nodes without executing node.
        return new DataTableSpec[] {
                    <#list inputPorts as inputPort>
                        null,
                    </#list>
                    };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
            throws InvalidSettingsException {

        // TODO: check if user settings are available, fit to the incoming
        // table structure, and the incoming types are feasible for the node
        // to execute. If the node can execute in its current state return
        // the spec of its output data table(s) (if you can, otherwise an array
        // with null elements), or throw an exception with a useful user message

        return super.configure(inSpecs);
    }
}
