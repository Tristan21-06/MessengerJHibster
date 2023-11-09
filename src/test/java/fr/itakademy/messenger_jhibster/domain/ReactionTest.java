package fr.itakademy.messenger_jhibster.domain;

import static fr.itakademy.messenger_jhibster.domain.MessageTestSamples.*;
import static fr.itakademy.messenger_jhibster.domain.ReactionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.itakademy.messenger_jhibster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reaction.class);
        Reaction reaction1 = getReactionSample1();
        Reaction reaction2 = new Reaction();
        assertThat(reaction1).isNotEqualTo(reaction2);

        reaction2.setId(reaction1.getId());
        assertThat(reaction1).isEqualTo(reaction2);

        reaction2 = getReactionSample2();
        assertThat(reaction1).isNotEqualTo(reaction2);
    }

    @Test
    void messageTest() throws Exception {
        Reaction reaction = getReactionRandomSampleGenerator();
        Message messageBack = getMessageRandomSampleGenerator();

        reaction.setMessage(messageBack);
        assertThat(reaction.getMessage()).isEqualTo(messageBack);

        reaction.message(null);
        assertThat(reaction.getMessage()).isNull();
    }
}
