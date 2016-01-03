package nz.co.threepoints.ps.core.repository;

import nz.co.threepoints.ps.core.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
