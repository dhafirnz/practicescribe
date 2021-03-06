package nz.co.threepoints.ps.core.web.rest;

import com.codahale.metrics.annotation.Timed;
import nz.co.threepoints.ps.core.domain.Address;
import nz.co.threepoints.ps.core.repository.AddressRepository;
import nz.co.threepoints.ps.core.repository.search.AddressSearchRepository;
import nz.co.threepoints.ps.core.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    @Inject
    private AddressRepository addressRepository;

    @Inject
    private AddressSearchRepository addressSearchRepository;

    /**
     * POST  /addresss -> Create a new address.
     */
    @RequestMapping(value = "/addresss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Address> createAddress(@Valid @RequestBody Address address) throws URISyntaxException {
        log.debug("REST request to save Address : {}", address);
        if (address.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("address", "idexists", "A new address cannot already have an ID")).body(null);
        }
        Address result = addressRepository.save(address);
//        addressSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/addresss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("address", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addresss -> Updates an existing address.
     */
    @RequestMapping(value = "/addresss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Address> updateAddress(@Valid @RequestBody Address address) throws URISyntaxException {
        log.debug("REST request to update Address : {}", address);
        if (address.getId() == null) {
            return createAddress(address);
        }
        Address result = addressRepository.save(address);
//        addressSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("address", address.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addresss -> get all the addresss.
     */
    @RequestMapping(value = "/addresss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Address> getAllAddresss() {
        log.debug("REST request to get all Addresss");
        return addressRepository.findAll();
    }

    /**
     * GET  /addresss/:id -> get the "id" address.
     */
    @RequestMapping(value = "/addresss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        log.debug("REST request to get Address : {}", id);
        Address address = addressRepository.findOne(id);
        return Optional.ofNullable(address)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /addresss/:id -> delete the "id" address.
     */
    @RequestMapping(value = "/addresss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        addressRepository.delete(id);
//        addressSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("address", id.toString())).build();
    }

    /**
     * SEARCH  /_search/addresss/:query -> search for the address corresponding
     * to the query.
     */
//    @RequestMapping(value = "/_search/addresss/{query}",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<Address> searchAddresss(@PathVariable String query) {
//        log.debug("REST request to search Addresss for query {}", query);
//        return StreamSupport
//            .stream(addressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
//            .collect(Collectors.toList());

}
