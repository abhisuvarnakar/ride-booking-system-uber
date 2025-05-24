package com.cs.project.uber.uberApp.strategies;

import com.cs.project.uber.uberApp.strategies.impl.DriverMatchingHighestRatedStrategy;
import com.cs.project.uber.uberApp.strategies.impl.DriverMatchingNearestStrategy;
import com.cs.project.uber.uberApp.strategies.impl.RideFareSurgePricingCalculationStrategy;
import com.cs.project.uber.uberApp.strategies.impl.RiderFareDefaultCalculationStrategy;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class RideStrategyManager {

    private final DriverMatchingNearestStrategy driverMatchingNearestStrategy;
    private final DriverMatchingHighestRatedStrategy driverMatchingHighestRatedStrategy;
    private final RideFareSurgePricingCalculationStrategy rideFareSurgePricingCalculationStrategy;
    private final RiderFareDefaultCalculationStrategy riderFareDefaultCalculationStrategy;

    public RideStrategyManager(DriverMatchingNearestStrategy driverMatchingNearestStrategy, DriverMatchingHighestRatedStrategy driverMatchingHighestRatedStrategy, RideFareSurgePricingCalculationStrategy rideFareSurgePricingCalculationStrategy, RiderFareDefaultCalculationStrategy riderFareDefaultCalculationStrategy) {
        this.driverMatchingNearestStrategy = driverMatchingNearestStrategy;
        this.driverMatchingHighestRatedStrategy = driverMatchingHighestRatedStrategy;
        this.rideFareSurgePricingCalculationStrategy = rideFareSurgePricingCalculationStrategy;
        this.riderFareDefaultCalculationStrategy = riderFareDefaultCalculationStrategy;
    }

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating) {
        if (riderRating >= 4.5) {
            return driverMatchingHighestRatedStrategy;
        }
        return driverMatchingNearestStrategy;
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy() {
        LocalTime surgeStartTime = LocalTime.of(18, 0);
        LocalTime surgeEndTime = LocalTime.of(21, 0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime =
                currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if (isSurgeTime) {
            return rideFareSurgePricingCalculationStrategy;
        }

        return riderFareDefaultCalculationStrategy;
    }
}
