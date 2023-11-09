package fr.itakademy.messenger_jhibster.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.itakademy.messenger_jhibster.domain.Reaction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReactionDTO implements Serializable {

    private Long id;

    private String type;

    private MessageDTO message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MessageDTO getMessage() {
        return message;
    }

    public void setMessage(MessageDTO message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReactionDTO)) {
            return false;
        }

        ReactionDTO reactionDTO = (ReactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReactionDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", message=" + getMessage() +
            "}";
    }
}
