package com.rationalinsights;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActivatorTest {

    @Test
    public void veryStupidTest() {
        assertEquals("rationalinsights-knime-plugin.plugin", Activator.PLUGIN_ID);
    }
}
