package fr.itakademy.messenger_jhibster.service;

import fr.itakademy.messenger_jhibster.service.dto.MessageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.itakademy.messenger_jhibster.domain.Message}.
 */
public interface MessageService {
    /**
     * Save a message.
     *
     * @param messageDTO the entity to save.
     * @return the persisted entity.
     */
    MessageDTO save(MessageDTO messageDTO);

    /**
     * Updates a message.
     *
     * @param messageDTO the entity to update.
     * @return the persisted entity.
     */
    MessageDTO update(MessageDTO messageDTO);

    /**
     * Partially updates a message.
     *
     * @param messageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MessageDTO> partialUpdate(MessageDTO messageDTO);

    /**
     * Get all the messages.
     *
     * @return the list of entities.
     */
    List<MessageDTO> findAll();

    /**
     * Get the "id" message.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MessageDTO> findOne(Long id);

    /**
     * Delete the "id" message.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
