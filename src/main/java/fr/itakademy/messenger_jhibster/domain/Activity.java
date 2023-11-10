package fr.itakademy.messenger_jhibster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "image_acivity")
    private String imageAcivity;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "activitys")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "activitys", "message" }, allowSetters = true)
    private Set<Conversation> conversations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Activity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageAcivity() {
        return this.imageAcivity;
    }

    public Activity imageAcivity(String imageAcivity) {
        this.setImageAcivity(imageAcivity);
        return this;
    }

    public void setImageAcivity(String imageAcivity) {
        this.imageAcivity = imageAcivity;
    }

    public Set<Conversation> getConversations() {
        return this.conversations;
    }

    public void setConversations(Set<Conversation> conversations) {
        if (this.conversations != null) {
            this.conversations.forEach(i -> i.removeActivitys(this));
        }
        if (conversations != null) {
            conversations.forEach(i -> i.addActivitys(this));
        }
        this.conversations = conversations;
    }

    public Activity conversations(Set<Conversation> conversations) {
        this.setConversations(conversations);
        return this;
    }

    public Activity addConversations(Conversation conversation) {
        this.conversations.add(conversation);
        conversation.getActivitys().add(this);
        return this;
    }

    public Activity removeConversations(Conversation conversation) {
        this.conversations.remove(conversation);
        conversation.getActivitys().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return getId() != null && getId().equals(((Activity) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", imageAcivity='" + getImageAcivity() + "'" +
            "}";
    }
}
