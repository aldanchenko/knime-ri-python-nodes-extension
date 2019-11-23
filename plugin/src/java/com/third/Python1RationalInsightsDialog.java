package us.third;

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
 * <code>NodeDialog</code> for the "HelloWorldRationalInsights" Node.
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more
 * complex dialog please derive directly from
 * {@link org.knime.core.node.NodeDialogPane}.
 */
public class Python1RationalInsightsDialog extends DefaultNodeSettingsPane {
	private PythonOptionsPanel<Python1RationalInsightsConfig> pythonOptions;
	Python1RationalInsightsConfig config;

    /**
     * New pane for configuring HelloWorldRationalInsights node dialog.
     * This is just a suggestion to demonstrate possible default dialog
     * components.
     */
    protected Python1RationalInsightsDialog() {
        super();
		config = new Python1RationalInsightsConfig();

        SettingsModelIntegerBounded count = config.getCount();
        addDialogComponent(new DialogComponentNumber(count, "Counter", 1, 5));

        pythonOptions = new PythonOptionsPanel<Python1RationalInsightsConfig>();
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
