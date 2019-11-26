package com.rationalinsights.${nodePackageName};

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "RationalInsightsPythonNodesExtension" Node.
 *
 */
public class ${nodeName}Factory
        extends NodeFactory<${nodeName}Model> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ${nodeName}Model createNodeModel() {
        return new ${nodeName}Model();
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
    public NodeView<${nodeName}Model> createNodeView(final int viewIndex,
            final ${nodeName}Model nodeModel) {
        return new ${nodeName}View(nodeModel);
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
        return new ${nodeName}Dialog();
    }

}
