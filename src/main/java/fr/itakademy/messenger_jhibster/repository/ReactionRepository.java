package fr.itakademy.messenger_jhibster.repository;

import fr.itakademy.messenger_jhibster.domain.Reaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Reaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {}
