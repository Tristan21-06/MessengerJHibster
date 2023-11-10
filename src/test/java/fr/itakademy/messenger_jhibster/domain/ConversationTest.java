package fr.itakademy.messenger_jhibster.domain;

import static fr.itakademy.messenger_jhibster.domain.ActivityTestSamples.*;
import static fr.itakademy.messenger_jhibster.domain.ConversationTestSamples.*;
import static fr.itakademy.messenger_jhibster.domain.MessageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.itakademy.messenger_jhibster.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ConversationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conversation.class);
        Conversation conversation1 = getConversationSample1();
        Conversation conversation2 = new Conversation();
        assertThat(conversation1).isNotEqualTo(conversation2);

        conversation2.setId(conversation1.getId());
        assertThat(conversation1).isEqualTo(conversation2);

        conversation2 = getConversationSample2();
        assertThat(conversation1).isNotEqualTo(conversation2);
    }

    @Test
    void activitysTest() throws Exception {
        Conversation conversation = getConversationRandomSampleGenerator();
        Activity activityBack = getActivityRandomSampleGenerator();

        conversation.addActivitys(activityBack);
        assertThat(conversation.getActivitys()).containsOnly(activityBack);

        conversation.removeActivitys(activityBack);
        assertThat(conversation.getActivitys()).doesNotContain(activityBack);

        conversation.activitys(new HashSet<>(Set.of(activityBack)));
        assertThat(conversation.getActivitys()).containsOnly(activityBack);

        conversation.setActivitys(new HashSet<>());
        assertThat(conversation.getActivitys()).doesNotContain(activityBack);
    }

    @Test
    void messageTest() throws Exception {
        Conversation conversation = getConversationRandomSampleGenerator();
        Message messageBack = getMessageRandomSampleGenerator();

        conversation.setMessage(messageBack);
        assertThat(conversation.getMessage()).isEqualTo(messageBack);

        conversation.message(null);
        assertThat(conversation.getMessage()).isNull();
    }
}
