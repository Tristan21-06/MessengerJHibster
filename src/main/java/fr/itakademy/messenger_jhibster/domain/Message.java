package fr.itakademy.messenger_jhibster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "texte")
    private String texte;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "message")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "message" }, allowSetters = true)
    private Set<Reaction> reactions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "message")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "message" }, allowSetters = true)
    private Set<Conversation> conversations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Message id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexte() {
        return this.texte;
    }

    public Message texte(String texte) {
        this.setTexte(texte);
        return this;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Set<Reaction> getReactions() {
        return this.reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        if (this.reactions != null) {
            this.reactions.forEach(i -> i.setMessage(null));
        }
        if (reactions != null) {
            reactions.forEach(i -> i.setMessage(this));
        }
        this.reactions = reactions;
    }

    public Message reactions(Set<Reaction> reactions) {
        this.setReactions(reactions);
        return this;
    }

    public Message addReactions(Reaction reaction) {
        this.reactions.add(reaction);
        reaction.setMessage(this);
        return this;
    }

    public Message removeReactions(Reaction reaction) {
        this.reactions.remove(reaction);
        reaction.setMessage(null);
        return this;
    }

    public Set<Conversation> getConversations() {
        return this.conversations;
    }

    public void setConversations(Set<Conversation> conversations) {
        if (this.conversations != null) {
            this.conversations.forEach(i -> i.setMessage(null));
        }
        if (conversations != null) {
            conversations.forEach(i -> i.setMessage(this));
        }
        this.conversations = conversations;
    }

    public Message conversations(Set<Conversation> conversations) {
        this.setConversations(conversations);
        return this;
    }

    public Message addConversations(Conversation conversation) {
        this.conversations.add(conversation);
        conversation.setMessage(this);
        return this;
    }

    public Message removeConversations(Conversation conversation) {
        this.conversations.remove(conversation);
        conversation.setMessage(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return getId() != null && getId().equals(((Message) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", texte='" + getTexte() + "'" +
            "}";
    }
}
