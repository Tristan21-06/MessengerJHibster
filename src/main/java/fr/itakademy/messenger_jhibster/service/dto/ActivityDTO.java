package fr.itakademy.messenger_jhibster.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.itakademy.messenger_jhibster.domain.Activity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActivityDTO implements Serializable {

    private Long id;

    private String imageActivity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageActivity() {
        return imageActivity;
    }

    public void setImageActivity(String imageActivity) {
        this.imageActivity = imageActivity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityDTO)) {
            return false;
        }

        ActivityDTO activityDTO = (ActivityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, activityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityDTO{" +
            "id=" + getId() +
            ", imageActivity='" + getImageActivity() + "'" +
            "}";
    }
}
