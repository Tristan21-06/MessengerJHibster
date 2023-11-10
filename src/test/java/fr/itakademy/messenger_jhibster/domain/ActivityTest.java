package fr.itakademy.messenger_jhibster.domain;

import static fr.itakademy.messenger_jhibster.domain.ActivityTestSamples.*;
import static fr.itakademy.messenger_jhibster.domain.ConversationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.itakademy.messenger_jhibster.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ActivityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activity.class);
        Activity activity1 = getActivitySample1();
        Activity activity2 = new Activity();
        assertThat(activity1).isNotEqualTo(activity2);

        activity2.setId(activity1.getId());
        assertThat(activity1).isEqualTo(activity2);

        activity2 = getActivitySample2();
        assertThat(activity1).isNotEqualTo(activity2);
    }

    @Test
    void conversationsTest() throws Exception {
        Activity activity = getActivityRandomSampleGenerator();
        Conversation conversationBack = getConversationRandomSampleGenerator();

        activity.addConversations(conversationBack);
        assertThat(activity.getConversations()).containsOnly(conversationBack);
        assertThat(conversationBack.getActivities()).containsOnly(activity);

        activity.removeConversations(conversationBack);
        assertThat(activity.getConversations()).doesNotContain(conversationBack);
        assertThat(conversationBack.getActivities()).doesNotContain(activity);

        activity.conversations(new HashSet<>(Set.of(conversationBack)));
        assertThat(activity.getConversations()).containsOnly(conversationBack);
        assertThat(conversationBack.getActivities()).containsOnly(activity);

        activity.setConversations(new HashSet<>());
        assertThat(activity.getConversations()).doesNotContain(conversationBack);
        assertThat(conversationBack.getActivities()).doesNotContain(activity);
    }
}
