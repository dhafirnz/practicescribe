package nz.co.threepoints.ps.core.web.rest;

import nz.co.threepoints.ps.core.Application;
import nz.co.threepoints.ps.core.domain.Address;
import nz.co.threepoints.ps.core.repository.AddressRepository;
import nz.co.threepoints.ps.core.repository.search.AddressSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AddressResource REST controller.
 *
 * @see AddressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AddressResourceIntTest {

    private static final String DEFAULT_STREET_NUM = "AAAAA";
    private static final String UPDATED_STREET_NUM = "BBBBB";
    private static final String DEFAULT_STREET_NAME = "AAAAA";
    private static final String UPDATED_STREET_NAME = "BBBBB";
    private static final String DEFAULT_PO_BOX_NUM = "AAAAA";
    private static final String UPDATED_PO_BOX_NUM = "BBBBB";
    private static final String DEFAULT_SUBURB = "AAAAA";
    private static final String UPDATED_SUBURB = "BBBBB";
    private static final String DEFAULT_TOWN = "AAAAA";
    private static final String UPDATED_TOWN = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";
    private static final String DEFAULT_COUNTRY = "AAAAA";
    private static final String UPDATED_COUNTRY = "BBBBB";

    @Inject
    private AddressRepository addressRepository;

    @Inject
    private AddressSearchRepository addressSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAddressMockMvc;

    private Address address;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AddressResource addressResource = new AddressResource();
        ReflectionTestUtils.setField(addressResource, "addressSearchRepository", addressSearchRepository);
        ReflectionTestUtils.setField(addressResource, "addressRepository", addressRepository);
        this.restAddressMockMvc = MockMvcBuilders.standaloneSetup(addressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        address = new Address();
        address.setStreetNum(DEFAULT_STREET_NUM);
        address.setStreetName(DEFAULT_STREET_NAME);
        address.setPoBoxNum(DEFAULT_PO_BOX_NUM);
        address.setSuburb(DEFAULT_SUBURB);
        address.setTown(DEFAULT_TOWN);
        address.setCity(DEFAULT_CITY);
        address.setCountry(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address

        restAddressMockMvc.perform(post("/api/addresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(address)))
                .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addresss = addressRepository.findAll();
        assertThat(addresss).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addresss.get(addresss.size() - 1);
        assertThat(testAddress.getStreetNum()).isEqualTo(DEFAULT_STREET_NUM);
        assertThat(testAddress.getStreetName()).isEqualTo(DEFAULT_STREET_NAME);
        assertThat(testAddress.getPoBoxNum()).isEqualTo(DEFAULT_PO_BOX_NUM);
        assertThat(testAddress.getSuburb()).isEqualTo(DEFAULT_SUBURB);
        assertThat(testAddress.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllAddresss() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addresss
        restAddressMockMvc.perform(get("/api/addresss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
                .andExpect(jsonPath("$.[*].streetNum").value(hasItem(DEFAULT_STREET_NUM.toString())))
                .andExpect(jsonPath("$.[*].streetName").value(hasItem(DEFAULT_STREET_NAME.toString())))
                .andExpect(jsonPath("$.[*].poBoxNum").value(hasItem(DEFAULT_PO_BOX_NUM.toString())))
                .andExpect(jsonPath("$.[*].suburb").value(hasItem(DEFAULT_SUBURB.toString())))
                .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresss/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.streetNum").value(DEFAULT_STREET_NUM.toString()))
            .andExpect(jsonPath("$.streetName").value(DEFAULT_STREET_NAME.toString()))
            .andExpect(jsonPath("$.poBoxNum").value(DEFAULT_PO_BOX_NUM.toString()))
            .andExpect(jsonPath("$.suburb").value(DEFAULT_SUBURB.toString()))
            .andExpect(jsonPath("$.town").value(DEFAULT_TOWN.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

		int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        address.setStreetNum(UPDATED_STREET_NUM);
        address.setStreetName(UPDATED_STREET_NAME);
        address.setPoBoxNum(UPDATED_PO_BOX_NUM);
        address.setSuburb(UPDATED_SUBURB);
        address.setTown(UPDATED_TOWN);
        address.setCity(UPDATED_CITY);
        address.setCountry(UPDATED_COUNTRY);

        restAddressMockMvc.perform(put("/api/addresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(address)))
                .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addresss = addressRepository.findAll();
        assertThat(addresss).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addresss.get(addresss.size() - 1);
        assertThat(testAddress.getStreetNum()).isEqualTo(UPDATED_STREET_NUM);
        assertThat(testAddress.getStreetName()).isEqualTo(UPDATED_STREET_NAME);
        assertThat(testAddress.getPoBoxNum()).isEqualTo(UPDATED_PO_BOX_NUM);
        assertThat(testAddress.getSuburb()).isEqualTo(UPDATED_SUBURB);
        assertThat(testAddress.getTown()).isEqualTo(UPDATED_TOWN);
        assertThat(testAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

		int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Get the address
        restAddressMockMvc.perform(delete("/api/addresss/{id}", address.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Address> addresss = addressRepository.findAll();
        assertThat(addresss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
