package fr.itakademy.messenger_jhibster.service;

import fr.itakademy.messenger_jhibster.service.dto.ReactionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.itakademy.messenger_jhibster.domain.Reaction}.
 */
public interface ReactionService {
    /**
     * Save a reaction.
     *
     * @param reactionDTO the entity to save.
     * @return the persisted entity.
     */
    ReactionDTO save(ReactionDTO reactionDTO);

    /**
     * Updates a reaction.
     *
     * @param reactionDTO the entity to update.
     * @return the persisted entity.
     */
    ReactionDTO update(ReactionDTO reactionDTO);

    /**
     * Partially updates a reaction.
     *
     * @param reactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReactionDTO> partialUpdate(ReactionDTO reactionDTO);

    /**
     * Get all the reactions.
     *
     * @return the list of entities.
     */
    List<ReactionDTO> findAll();

    /**
     * Get the "id" reaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReactionDTO> findOne(Long id);

    /**
     * Delete the "id" reaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
