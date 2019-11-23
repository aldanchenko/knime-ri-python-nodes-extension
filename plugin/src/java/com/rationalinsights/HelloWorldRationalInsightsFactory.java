package com.rationalinsights;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "HelloWorldRationalInsights" Node.
 *
 */
public class HelloWorldRationalInsightsFactory
        extends NodeFactory<HelloWorldRationalInsightsModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public HelloWorldRationalInsightsModel createNodeModel() {
        return new HelloWorldRationalInsightsModel();
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
    public NodeView<HelloWorldRationalInsightsModel> createNodeView(final int viewIndex,
            final HelloWorldRationalInsightsModel nodeModel) {
        return new HelloWorldRationalInsightsView(nodeModel);
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
        return new HelloWorldRationalInsightsDialog();
    }

}
