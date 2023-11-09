package fr.itakademy.messenger_jhibster.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ConversationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Conversation getConversationSample1() {
        return new Conversation().id(1L).name("name1").color("color1");
    }

    public static Conversation getConversationSample2() {
        return new Conversation().id(2L).name("name2").color("color2");
    }

    public static Conversation getConversationRandomSampleGenerator() {
        return new Conversation().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).color(UUID.randomUUID().toString());
    }
}
