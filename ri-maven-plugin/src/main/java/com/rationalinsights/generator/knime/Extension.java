package com.rationalinsights.generator.knime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Description of plugin.xml extension.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Extension {

    private static final String KNIME_NODES_POINT = "org.knime.workbench.repository.nodes";

    @XmlAttribute
    private String point;

    @XmlElement
    private Category category;

    @XmlElement(name = "node")
    private List<KnimeNode> nodes;

    public boolean isNodesExtension() {
        return KNIME_NODES_POINT.equals(point);
    }

    public void addNode(KnimeNode knimeNode) {
        this.nodes.add(knimeNode);
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<KnimeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<KnimeNode> nodes) {
        this.nodes = nodes;
    }
}
