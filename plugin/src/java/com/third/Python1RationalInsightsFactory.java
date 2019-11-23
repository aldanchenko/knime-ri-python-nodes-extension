package us.third;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "HelloWorldRationalInsights" Node.
 *
 */
public class Python1RationalInsightsFactory
        extends NodeFactory<Python1RationalInsightsModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Python1RationalInsightsModel createNodeModel() {
        return new Python1RationalInsightsModel();
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
    public NodeView<Python1RationalInsightsModel> createNodeView(final int viewIndex,
                                                                 final Python1RationalInsightsModel nodeModel) {
        return new Python1RationalInsightsView(nodeModel);
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
        return new Python1RationalInsightsDialog();
    }

}
