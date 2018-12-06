package net.raeesaamir.coinz.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A unit test class for the feature collection class.
 *
 * @author raeesaamir
 */
public class FeatureCollectionTest {

    /**
     * A mocked feature collection instance.
     */
    private FeatureCollection featureCollection;

    /**
     * Sets up the feature collection instance before running the unit tests.
     */
    @Before
    public void initFeatureCollectionTest() {
        featureCollection = TestUtils.getExampleFeatureCollection();
    }

    /**
     * Checks if the feature collection has the correct date and time properties.
     */
    @Test
    public void testFeatureCollectionDateTimeGenerated() {
        assertEquals("23:59", featureCollection.approximateTimeRemaining);
        assertEquals("00:00", featureCollection.timeGenerated);
        assertEquals("Mon Jan 01 2018", featureCollection.dateGenerated);
    }

    /**
     * Checks if the feature collection has the correct number of features.
     */
    @Test
    public void testFeatureCollectionFeatures() {
        assertEquals(50, featureCollection.getFeatures().length);
    }

    /**
     * Checks if the exchange rates are correct.
     */
    @Test
    public void testFeatureCollectionExchangeRates() {
        ExchangeRates actualExchangeRates = featureCollection.getRates();

        ExchangeRates expectedExchangeRates = new ExchangeRates();
        expectedExchangeRates.dolr = 9.0;
        expectedExchangeRates.peny = 13.0;
        expectedExchangeRates.shil = 7.0;
        expectedExchangeRates.quid = 11.0;

        assertEquals(expectedExchangeRates, actualExchangeRates);
    }
}
