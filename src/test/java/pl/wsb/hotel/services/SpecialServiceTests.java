package pl.wsb.hotel.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;


public class SpecialServiceTests {
    @Test
    public void testLuggageServiceCalculateCost() {
        // given
        LuggageService luggageService = new LuggageService("Basic Luggage");
        int quantity = 3;
        double unitPrice = 100.0;
        int expectedCost = 300;

        // when
        int actualCost = luggageService.calculateCost(quantity, unitPrice);

        // then
        assertEquals(expectedCost, actualCost);
    }

    @Test
    public void testLuggageServiceHighDemand() {
        // given
        LuggageService luggageService = new LuggageService("Basic Luggage");

        // when
        boolean demand = luggageService.highDemand();

        // then
        assertFalse(demand);
    }

    @Test
    public void testTimeServiceCalculateCost() {
        // given
        TimeService timeService = new TimeService("Time Check");
        int quantity = 5;
        double unitPrice = 200.0;
        int expectedCost = 1000;

        // when
        int actualCost = timeService.calculateCost(quantity, unitPrice);

        // then
        assertEquals(expectedCost, actualCost);
    }

    @Test
    public void testTimeServiceHighDemandMorning() {
        // given
        TimeService timeService = new TimeService("Time Check");
        LocalTime testTime = LocalTime.of(8, 0); // godziny szczytu

        // when
        boolean isHighDemand = timeService.highDemand(testTime);

        // then
        assertTrue(isHighDemand);
    }

    @Test
    public void testTimeServiceHighDemandNonPeakHours() {
        // given
        TimeService timeService = new TimeService("Time Check");
        LocalTime testTime = LocalTime.of(11, 0); // poza godzinami szczytu

        // when
        boolean isHighDemand = timeService.highDemand(testTime);

        // then
        assertFalse(isHighDemand);
    }
}