package fr.itakademy.messenger_jhibster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.itakademy.messenger_jhibster.IntegrationTest;
import fr.itakademy.messenger_jhibster.domain.Reaction;
import fr.itakademy.messenger_jhibster.domain.enumeration.ReactionType;
import fr.itakademy.messenger_jhibster.repository.ReactionRepository;
import fr.itakademy.messenger_jhibster.service.dto.ReactionDTO;
import fr.itakademy.messenger_jhibster.service.mapper.ReactionMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReactionResourceIT {

    private static final ReactionType DEFAULT_TYPE = ReactionType.SMILE;
    private static final ReactionType UPDATED_TYPE = ReactionType.SAD;

    private static final String ENTITY_API_URL = "/api/reactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private ReactionMapper reactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReactionMockMvc;

    private Reaction reaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reaction createEntity(EntityManager em) {
        Reaction reaction = new Reaction().type(DEFAULT_TYPE);
        return reaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reaction createUpdatedEntity(EntityManager em) {
        Reaction reaction = new Reaction().type(UPDATED_TYPE);
        return reaction;
    }

    @BeforeEach
    public void initTest() {
        reaction = createEntity(em);
    }

    @Test
    @Transactional
    void createReaction() throws Exception {
        int databaseSizeBeforeCreate = reactionRepository.findAll().size();
        // Create the Reaction
        ReactionDTO reactionDTO = reactionMapper.toDto(reaction);
        restReactionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reactionDTO)))
            .andExpect(status().isCreated());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeCreate + 1);
        Reaction testReaction = reactionList.get(reactionList.size() - 1);
        assertThat(testReaction.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createReactionWithExistingId() throws Exception {
        // Create the Reaction with an existing ID
        reaction.setId(1L);
        ReactionDTO reactionDTO = reactionMapper.toDto(reaction);

        int databaseSizeBeforeCreate = reactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReactionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReactions() throws Exception {
        // Initialize the database
        reactionRepository.saveAndFlush(reaction);

        // Get all the reactionList
        restReactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getReaction() throws Exception {
        // Initialize the database
        reactionRepository.saveAndFlush(reaction);

        // Get the reaction
        restReactionMockMvc
            .perform(get(ENTITY_API_URL_ID, reaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reaction.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReaction() throws Exception {
        // Get the reaction
        restReactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReaction() throws Exception {
        // Initialize the database
        reactionRepository.saveAndFlush(reaction);

        int databaseSizeBeforeUpdate = reactionRepository.findAll().size();

        // Update the reaction
        Reaction updatedReaction = reactionRepository.findById(reaction.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReaction are not directly saved in db
        em.detach(updatedReaction);
        updatedReaction.type(UPDATED_TYPE);
        ReactionDTO reactionDTO = reactionMapper.toDto(updatedReaction);

        restReactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeUpdate);
        Reaction testReaction = reactionList.get(reactionList.size() - 1);
        assertThat(testReaction.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingReaction() throws Exception {
        int databaseSizeBeforeUpdate = reactionRepository.findAll().size();
        reaction.setId(longCount.incrementAndGet());

        // Create the Reaction
        ReactionDTO reactionDTO = reactionMapper.toDto(reaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReaction() throws Exception {
        int databaseSizeBeforeUpdate = reactionRepository.findAll().size();
        reaction.setId(longCount.incrementAndGet());

        // Create the Reaction
        ReactionDTO reactionDTO = reactionMapper.toDto(reaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReaction() throws Exception {
        int databaseSizeBeforeUpdate = reactionRepository.findAll().size();
        reaction.setId(longCount.incrementAndGet());

        // Create the Reaction
        ReactionDTO reactionDTO = reactionMapper.toDto(reaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReactionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reactionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReactionWithPatch() throws Exception {
        // Initialize the database
        reactionRepository.saveAndFlush(reaction);

        int databaseSizeBeforeUpdate = reactionRepository.findAll().size();

        // Update the reaction using partial update
        Reaction partialUpdatedReaction = new Reaction();
        partialUpdatedReaction.setId(reaction.getId());

        restReactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReaction))
            )
            .andExpect(status().isOk());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeUpdate);
        Reaction testReaction = reactionList.get(reactionList.size() - 1);
        assertThat(testReaction.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateReactionWithPatch() throws Exception {
        // Initialize the database
        reactionRepository.saveAndFlush(reaction);

        int databaseSizeBeforeUpdate = reactionRepository.findAll().size();

        // Update the reaction using partial update
        Reaction partialUpdatedReaction = new Reaction();
        partialUpdatedReaction.setId(reaction.getId());

        partialUpdatedReaction.type(UPDATED_TYPE);

        restReactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReaction))
            )
            .andExpect(status().isOk());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeUpdate);
        Reaction testReaction = reactionList.get(reactionList.size() - 1);
        assertThat(testReaction.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingReaction() throws Exception {
        int databaseSizeBeforeUpdate = reactionRepository.findAll().size();
        reaction.setId(longCount.incrementAndGet());

        // Create the Reaction
        ReactionDTO reactionDTO = reactionMapper.toDto(reaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReaction() throws Exception {
        int databaseSizeBeforeUpdate = reactionRepository.findAll().size();
        reaction.setId(longCount.incrementAndGet());

        // Create the Reaction
        ReactionDTO reactionDTO = reactionMapper.toDto(reaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReaction() throws Exception {
        int databaseSizeBeforeUpdate = reactionRepository.findAll().size();
        reaction.setId(longCount.incrementAndGet());

        // Create the Reaction
        ReactionDTO reactionDTO = reactionMapper.toDto(reaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReactionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(reactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reaction in the database
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReaction() throws Exception {
        // Initialize the database
        reactionRepository.saveAndFlush(reaction);

        int databaseSizeBeforeDelete = reactionRepository.findAll().size();

        // Delete the reaction
        restReactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, reaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reaction> reactionList = reactionRepository.findAll();
        assertThat(reactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
