package us.third;

import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "HelloWorldRationalInsights" Node.
 *
 */
public class Python1RationalInsightsView extends NodeView<Python1RationalInsightsModel> {

    /**
     * Creates a new view.
     *
     * @param nodeModel The model (class: {@link HelloWorldRationalInsightsModel})
     */
    protected Python1RationalInsightsView(final Python1RationalInsightsModel nodeModel) {
        super(nodeModel);
// Testing some changes added
        // TODO instantiate the components of the view here.

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void modelChanged() {

        // TODO retrieve the new model from your nodemodel and
        // update the view.
        Python1RationalInsightsModel nodeModel =
            (Python1RationalInsightsModel)getNodeModel();
        assert nodeModel != null;

        // be aware of a possibly not executed nodeModel! The data you retrieve
        // from your nodemodel could be null, emtpy, or invalid in any kind.

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onClose() {

        // TODO things to do when closing the view
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onOpen() {

        // TODO things to do when opening the view
    }

}
