package com.rationalinsights.generator.knime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Description of plugin.xml
 */
@XmlRootElement(name = "plugin")
@XmlAccessorType(XmlAccessType.FIELD)
public class KnimePlugin {

    @XmlElement(name = "extension")
    private List<Extension> extensions;

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }
}
