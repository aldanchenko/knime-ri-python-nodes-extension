package us.third;

import java.util.Set;

import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;
import org.knime.core.node.workflow.FlowVariable;

import nl.esciencecenter.e3dchem.python.PythonWrapperNodeConfig;

public class Python1RationalInsightsConfig extends PythonWrapperNodeConfig {
    /** the settings key which is used to retrieve and
        store the settings (from the dialog or from a settings file)
       (package visibility to be usable from the dialog). */
    private static final int DEFAULT_COUNT = 100;

    /** initial default count value. */
    private static final String CFGKEY_COUNT = "Count";

    // example value: the models count variable filled from the dialog
    // and used in the models execution method. The default components of the
    // dialog work with "SettingsModels".
    private final SettingsModelIntegerBounded m_count =
        new SettingsModelIntegerBounded(Python1RationalInsightsConfig.CFGKEY_COUNT,
                    Python1RationalInsightsConfig.DEFAULT_COUNT,
                    Integer.MIN_VALUE, Integer.MAX_VALUE);

    public Python1RationalInsightsConfig(String[] inputTables, String[] outputTables) {
		super(inputTables, outputTables);
		// this Python package will be checked before executing the node
		addRequiredModule("pandas");
	}

	public Python1RationalInsightsConfig() {
		super();
		// this Python package will be checked before executing the node
		addRequiredModule("pandas");
	}

    @Override
    public void saveTo(final NodeSettingsWO settings) {
        super.saveTo(settings);
        m_count.saveSettingsTo(settings);
    }

    @Override
    public void loadFrom(final NodeSettingsRO settings) throws InvalidSettingsException {
        m_count.loadSettingsFrom(settings);
        super.loadFrom(settings);
    }

    @Override
    public Set<FlowVariable> getOptionsValues() {
        Set<FlowVariable> variables = super.getOptionsValues();
        variables.add(new FlowVariable(CFGKEY_COUNT, m_count.getIntValue()));
        return variables;
    }

    public SettingsModelIntegerBounded getCount() {
        return m_count;
    }
}
