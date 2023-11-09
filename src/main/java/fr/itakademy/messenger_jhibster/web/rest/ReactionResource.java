package fr.itakademy.messenger_jhibster.web.rest;

import fr.itakademy.messenger_jhibster.repository.ReactionRepository;
import fr.itakademy.messenger_jhibster.service.ReactionService;
import fr.itakademy.messenger_jhibster.service.dto.ReactionDTO;
import fr.itakademy.messenger_jhibster.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.itakademy.messenger_jhibster.domain.Reaction}.
 */
@RestController
@RequestMapping("/api/reactions")
public class ReactionResource {

    private final Logger log = LoggerFactory.getLogger(ReactionResource.class);

    private static final String ENTITY_NAME = "reaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReactionService reactionService;

    private final ReactionRepository reactionRepository;

    public ReactionResource(ReactionService reactionService, ReactionRepository reactionRepository) {
        this.reactionService = reactionService;
        this.reactionRepository = reactionRepository;
    }

    /**
     * {@code POST  /reactions} : Create a new reaction.
     *
     * @param reactionDTO the reactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reactionDTO, or with status {@code 400 (Bad Request)} if the reaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ReactionDTO> createReaction(@RequestBody ReactionDTO reactionDTO) throws URISyntaxException {
        log.debug("REST request to save Reaction : {}", reactionDTO);
        if (reactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new reaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReactionDTO result = reactionService.save(reactionDTO);
        return ResponseEntity
            .created(new URI("/api/reactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reactions/:id} : Updates an existing reaction.
     *
     * @param id the id of the reactionDTO to save.
     * @param reactionDTO the reactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reactionDTO,
     * or with status {@code 400 (Bad Request)} if the reactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReactionDTO> updateReaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReactionDTO reactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Reaction : {}, {}", id, reactionDTO);
        if (reactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReactionDTO result = reactionService.update(reactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reactions/:id} : Partial updates given fields of an existing reaction, field will ignore if it is null
     *
     * @param id the id of the reactionDTO to save.
     * @param reactionDTO the reactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reactionDTO,
     * or with status {@code 400 (Bad Request)} if the reactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReactionDTO> partialUpdateReaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReactionDTO reactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Reaction partially : {}, {}", id, reactionDTO);
        if (reactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReactionDTO> result = reactionService.partialUpdate(reactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reactions} : get all the reactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reactions in body.
     */
    @GetMapping("")
    public List<ReactionDTO> getAllReactions() {
        log.debug("REST request to get all Reactions");
        return reactionService.findAll();
    }

    /**
     * {@code GET  /reactions/:id} : get the "id" reaction.
     *
     * @param id the id of the reactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReactionDTO> getReaction(@PathVariable Long id) {
        log.debug("REST request to get Reaction : {}", id);
        Optional<ReactionDTO> reactionDTO = reactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reactionDTO);
    }

    /**
     * {@code DELETE  /reactions/:id} : delete the "id" reaction.
     *
     * @param id the id of the reactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Long id) {
        log.debug("REST request to delete Reaction : {}", id);
        reactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
