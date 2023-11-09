package fr.itakademy.messenger_jhibster.domain;

import static fr.itakademy.messenger_jhibster.domain.ConversationTestSamples.*;
import static fr.itakademy.messenger_jhibster.domain.MessageTestSamples.*;
import static fr.itakademy.messenger_jhibster.domain.ReactionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.itakademy.messenger_jhibster.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MessageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Message.class);
        Message message1 = getMessageSample1();
        Message message2 = new Message();
        assertThat(message1).isNotEqualTo(message2);

        message2.setId(message1.getId());
        assertThat(message1).isEqualTo(message2);

        message2 = getMessageSample2();
        assertThat(message1).isNotEqualTo(message2);
    }

    @Test
    void reactionsTest() throws Exception {
        Message message = getMessageRandomSampleGenerator();
        Reaction reactionBack = getReactionRandomSampleGenerator();

        message.addReactions(reactionBack);
        assertThat(message.getReactions()).containsOnly(reactionBack);
        assertThat(reactionBack.getMessage()).isEqualTo(message);

        message.removeReactions(reactionBack);
        assertThat(message.getReactions()).doesNotContain(reactionBack);
        assertThat(reactionBack.getMessage()).isNull();

        message.reactions(new HashSet<>(Set.of(reactionBack)));
        assertThat(message.getReactions()).containsOnly(reactionBack);
        assertThat(reactionBack.getMessage()).isEqualTo(message);

        message.setReactions(new HashSet<>());
        assertThat(message.getReactions()).doesNotContain(reactionBack);
        assertThat(reactionBack.getMessage()).isNull();
    }

    @Test
    void conversationsTest() throws Exception {
        Message message = getMessageRandomSampleGenerator();
        Conversation conversationBack = getConversationRandomSampleGenerator();

        message.addConversations(conversationBack);
        assertThat(message.getConversations()).containsOnly(conversationBack);
        assertThat(conversationBack.getMessage()).isEqualTo(message);

        message.removeConversations(conversationBack);
        assertThat(message.getConversations()).doesNotContain(conversationBack);
        assertThat(conversationBack.getMessage()).isNull();

        message.conversations(new HashSet<>(Set.of(conversationBack)));
        assertThat(message.getConversations()).containsOnly(conversationBack);
        assertThat(conversationBack.getMessage()).isEqualTo(message);

        message.setConversations(new HashSet<>());
        assertThat(message.getConversations()).doesNotContain(conversationBack);
        assertThat(conversationBack.getMessage()).isNull();
    }
}
