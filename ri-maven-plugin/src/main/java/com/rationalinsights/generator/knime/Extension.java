package com.rationalinsights.generator.knime;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Description of plugin.xml extension.
 */
public class Extension {

    @XmlAttribute
    private String point;

    @XmlElement
    private Category category;

    @XmlElement(name = "node")
    private List<KnimeNode> nodes;
}
