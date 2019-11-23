package com.rationalinsights;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.rationalinsights.HelloWorldRationalInsightsFactory;

public class HelloWorldRationalInsightsFactoryTest {
	
	@Test
    public void testGetNrNodeViews() {
		HelloWorldRationalInsightsFactory factory = new HelloWorldRationalInsightsFactory();
		
		int nrviews = factory.getNrNodeViews();
		
		assertEquals(1, nrviews);
	}
}