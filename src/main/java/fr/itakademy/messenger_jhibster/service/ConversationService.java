package fr.itakademy.messenger_jhibster.service;

import fr.itakademy.messenger_jhibster.service.dto.ConversationDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fr.itakademy.messenger_jhibster.domain.Conversation}.
 */
public interface ConversationService {
    /**
     * Save a conversation.
     *
     * @param conversationDTO the entity to save.
     * @return the persisted entity.
     */
    ConversationDTO save(ConversationDTO conversationDTO);

    /**
     * Updates a conversation.
     *
     * @param conversationDTO the entity to update.
     * @return the persisted entity.
     */
    ConversationDTO update(ConversationDTO conversationDTO);

    /**
     * Partially updates a conversation.
     *
     * @param conversationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConversationDTO> partialUpdate(ConversationDTO conversationDTO);

    /**
     * Get all the conversations.
     *
     * @return the list of entities.
     */
    List<ConversationDTO> findAll();

    /**
     * Get all the conversations with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConversationDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" conversation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConversationDTO> findOne(Long id);

    /**
     * Delete the "id" conversation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
