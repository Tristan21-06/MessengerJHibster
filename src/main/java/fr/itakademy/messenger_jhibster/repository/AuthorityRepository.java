package fr.itakademy.messenger_jhibster.repository;

import fr.itakademy.messenger_jhibster.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
