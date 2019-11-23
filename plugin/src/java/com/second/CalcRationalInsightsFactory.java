package us.second;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "HelloWorldRationalInsights" Node.
 *
 */
public class CalcRationalInsightsFactory
        extends NodeFactory<CalcRationalInsightsModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CalcRationalInsightsModel createNodeModel() {
        return new CalcRationalInsightsModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<CalcRationalInsightsModel> createNodeView(final int viewIndex,
            final CalcRationalInsightsModel nodeModel) {
        return new CalcRationalInsightsView(nodeModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new CalcRationalInsightsDialog();
    }

}
