package fr.itakademy.messenger_jhibster.repository;

import fr.itakademy.messenger_jhibster.domain.Conversation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ConversationRepositoryWithBagRelationshipsImpl implements ConversationRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Conversation> fetchBagRelationships(Optional<Conversation> conversation) {
        return conversation.map(this::fetchUsers).map(this::fetchActivitys);
    }

    @Override
    public Page<Conversation> fetchBagRelationships(Page<Conversation> conversations) {
        return new PageImpl<>(
            fetchBagRelationships(conversations.getContent()),
            conversations.getPageable(),
            conversations.getTotalElements()
        );
    }

    @Override
    public List<Conversation> fetchBagRelationships(List<Conversation> conversations) {
        return Optional.of(conversations).map(this::fetchUsers).map(this::fetchActivitys).orElse(Collections.emptyList());
    }

    Conversation fetchUsers(Conversation result) {
        return entityManager
            .createQuery(
                "select conversation from Conversation conversation left join fetch conversation.users where conversation.id = :id",
                Conversation.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Conversation> fetchUsers(List<Conversation> conversations) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, conversations.size()).forEach(index -> order.put(conversations.get(index).getId(), index));
        List<Conversation> result = entityManager
            .createQuery(
                "select conversation from Conversation conversation left join fetch conversation.users where conversation in :conversations",
                Conversation.class
            )
            .setParameter("conversations", conversations)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Conversation fetchActivitys(Conversation result) {
        return entityManager
            .createQuery(
                "select conversation from Conversation conversation left join fetch conversation.activitys where conversation.id = :id",
                Conversation.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Conversation> fetchActivitys(List<Conversation> conversations) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, conversations.size()).forEach(index -> order.put(conversations.get(index).getId(), index));
        List<Conversation> result = entityManager
            .createQuery(
                "select conversation from Conversation conversation left join fetch conversation.activitys where conversation in :conversations",
                Conversation.class
            )
            .setParameter("conversations", conversations)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
