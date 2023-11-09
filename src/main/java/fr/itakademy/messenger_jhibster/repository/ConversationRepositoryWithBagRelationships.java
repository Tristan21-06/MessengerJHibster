package fr.itakademy.messenger_jhibster.repository;

import fr.itakademy.messenger_jhibster.domain.Conversation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ConversationRepositoryWithBagRelationships {
    Optional<Conversation> fetchBagRelationships(Optional<Conversation> conversation);

    List<Conversation> fetchBagRelationships(List<Conversation> conversations);

    Page<Conversation> fetchBagRelationships(Page<Conversation> conversations);
}
