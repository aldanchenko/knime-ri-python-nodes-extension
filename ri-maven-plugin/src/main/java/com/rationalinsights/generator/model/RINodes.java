package com.rationalinsights.generator.model;

import java.util.List;

/**
 * Contains RationalInsights nodes description.
 */
public class RINodes extends AbstractTreeElement {
    private List<Catalog> catalogs;
    private List<Node> nodes;

    public List<Catalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
