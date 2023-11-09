package fr.itakademy.messenger_jhibster.service.impl;

import fr.itakademy.messenger_jhibster.domain.Reaction;
import fr.itakademy.messenger_jhibster.repository.ReactionRepository;
import fr.itakademy.messenger_jhibster.service.ReactionService;
import fr.itakademy.messenger_jhibster.service.dto.ReactionDTO;
import fr.itakademy.messenger_jhibster.service.mapper.ReactionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.itakademy.messenger_jhibster.domain.Reaction}.
 */
@Service
@Transactional
public class ReactionServiceImpl implements ReactionService {

    private final Logger log = LoggerFactory.getLogger(ReactionServiceImpl.class);

    private final ReactionRepository reactionRepository;

    private final ReactionMapper reactionMapper;

    public ReactionServiceImpl(ReactionRepository reactionRepository, ReactionMapper reactionMapper) {
        this.reactionRepository = reactionRepository;
        this.reactionMapper = reactionMapper;
    }

    @Override
    public ReactionDTO save(ReactionDTO reactionDTO) {
        log.debug("Request to save Reaction : {}", reactionDTO);
        Reaction reaction = reactionMapper.toEntity(reactionDTO);
        reaction = reactionRepository.save(reaction);
        return reactionMapper.toDto(reaction);
    }

    @Override
    public ReactionDTO update(ReactionDTO reactionDTO) {
        log.debug("Request to update Reaction : {}", reactionDTO);
        Reaction reaction = reactionMapper.toEntity(reactionDTO);
        reaction = reactionRepository.save(reaction);
        return reactionMapper.toDto(reaction);
    }

    @Override
    public Optional<ReactionDTO> partialUpdate(ReactionDTO reactionDTO) {
        log.debug("Request to partially update Reaction : {}", reactionDTO);

        return reactionRepository
            .findById(reactionDTO.getId())
            .map(existingReaction -> {
                reactionMapper.partialUpdate(existingReaction, reactionDTO);

                return existingReaction;
            })
            .map(reactionRepository::save)
            .map(reactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReactionDTO> findAll() {
        log.debug("Request to get all Reactions");
        return reactionRepository.findAll().stream().map(reactionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReactionDTO> findOne(Long id) {
        log.debug("Request to get Reaction : {}", id);
        return reactionRepository.findById(id).map(reactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reaction : {}", id);
        reactionRepository.deleteById(id);
    }
}
