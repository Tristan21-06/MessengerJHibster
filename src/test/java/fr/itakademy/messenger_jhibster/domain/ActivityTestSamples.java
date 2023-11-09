package fr.itakademy.messenger_jhibster.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ActivityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Activity getActivitySample1() {
        return new Activity().id(1L).imageAcivity("imageAcivity1");
    }

    public static Activity getActivitySample2() {
        return new Activity().id(2L).imageAcivity("imageAcivity2");
    }

    public static Activity getActivityRandomSampleGenerator() {
        return new Activity().id(longCount.incrementAndGet()).imageAcivity(UUID.randomUUID().toString());
    }
}
