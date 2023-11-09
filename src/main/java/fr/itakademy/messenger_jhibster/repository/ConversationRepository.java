package fr.itakademy.messenger_jhibster.repository;

import fr.itakademy.messenger_jhibster.domain.Conversation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Conversation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {}
