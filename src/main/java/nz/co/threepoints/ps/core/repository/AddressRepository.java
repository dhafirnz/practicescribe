package nz.co.threepoints.ps.core.repository;

import nz.co.threepoints.ps.core.domain.Address;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Address entity.
 */
public interface AddressRepository extends JpaRepository<Address,Long> {

}
