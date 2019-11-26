package com.rationalinsights;

import org.knime.core.data.DataTableSpec;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;
import org.knime.core.node.port.PortObjectSpec;

import nl.esciencecenter.e3dchem.python.PythonOptionsPanel;

/**
 * <code>NodeDialog</code> for the "RationalInsightsPythonNodesExtension" Node.
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more
 * complex dialog please derive directly from
 * {@link org.knime.core.node.NodeDialogPane}.
 */
public class ${nodeName}Dialog extends DefaultNodeSettingsPane {
	private PythonOptionsPanel<${nodeName}Config> pythonOptions;
	${nodeName}Config config;

    /**
     * New pane for configuring RationalInsightsPythonNodesExtension node dialog.
     * This is just a suggestion to demonstrate possible default dialog
     * components.
     */
    protected ${nodeName}Dialog() {
        super();
		config = new ${nodeName}Config();

        SettingsModelIntegerBounded count = config.getCount();
        addDialogComponent(new DialogComponentNumber(count, "Counter", 1, 5));

        pythonOptions = new PythonOptionsPanel<${nodeName}Config>();
		addTab("Python options", pythonOptions);
    }

    @Override
	public void loadAdditionalSettingsFrom(NodeSettingsRO settings, PortObjectSpec[] specs)
			throws NotConfigurableException {
		super.loadAdditionalSettingsFrom(settings, specs);
		config.loadFromInDialog(settings);
		pythonOptions.loadSettingsFrom(config);
	}

	@Override
	public void loadAdditionalSettingsFrom(NodeSettingsRO settings, DataTableSpec[] specs)
			throws NotConfigurableException {
		super.loadAdditionalSettingsFrom(settings, specs);
		config.loadFromInDialog(settings);
		pythonOptions.loadSettingsFrom(config);
	}

	@Override
	public void saveAdditionalSettingsTo(NodeSettingsWO settings) throws InvalidSettingsException {
		super.saveAdditionalSettingsTo(settings);
		pythonOptions.saveSettingsTo(config);
		config.saveToInDialog(settings);
	}
}
