package net.raeesaamir.coinz.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A unit test class for the feature class.
 *
 * @author raeesaamir
 */
public class FeatureTest {

    /**
     * A mocked feature instance.
     */
    private Feature feature;

    /**
     * Sets up the feature instance before running the unit tests.
     */
    @Before
    public void initFeatureTest() {
        feature = TestUtils.getExampleFeature();
    }

    /**
     * Checks if the feature has the correct properties.
     */
    @Test
    public void testFeatureProperties() {
        Properties actualProperties = feature.getProperties();

        Properties expectedProperties = new Properties();
        expectedProperties.id = "c58a-9e18-1285-6a70-c44b-f4f2";
        expectedProperties.value = "3.639937895762324";
        expectedProperties.currency = "SHIL";
        expectedProperties.markerSymbol = "3";
        expectedProperties.markerColor = "#0000ff";

        assertEquals(expectedProperties, actualProperties);
    }

    /**
     * Checks if the feature has the correct geometry.
     */
    @Test
    public void testFeatureGeometry() {
        Geometry actualGeometry = feature.getGeometry();

        Geometry expectedGeometry = new Geometry();
        expectedGeometry.type = "Point";
        expectedGeometry.coordinates = new double[]{-3.190015783400452, 55.94457042402356};

        assertEquals(expectedGeometry, actualGeometry);
    }
}
