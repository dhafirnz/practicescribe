package nz.co.threepoints.ps.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 20)
    @Column(name = "street_num", length = 20)
    private String streetNum;

    @Size(max = 100)
    @Column(name = "street_name", length = 100)
    private String streetName;

    @Size(max = 20)
    @Column(name = "po_box_num", length = 20)
    private String poBoxNum;

    @Size(max = 100)
    @Column(name = "suburb", length = 100)
    private String suburb;

    @Size(max = 50)
    @Column(name = "town", length = 50)
    private String town;

    @Size(max = 50)
    @Column(name = "city", length = 50)
    private String city;

    @Size(max = 50)
    @Column(name = "country", length = 50)
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPoBoxNum() {
        return poBoxNum;
    }

    public void setPoBoxNum(String poBoxNum) {
        this.poBoxNum = poBoxNum;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", streetNum='" + streetNum + "'" +
            ", streetName='" + streetName + "'" +
            ", poBoxNum='" + poBoxNum + "'" +
            ", suburb='" + suburb + "'" +
            ", town='" + town + "'" +
            ", city='" + city + "'" +
            ", country='" + country + "'" +
            '}';
    }
}
