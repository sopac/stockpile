package org.sopac.stockpile.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Agency.
 */
@Entity
@Table(name = "agency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "organisation_type")
    private String organisationType;

    @Column(name = "contact")
    private String contact;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @OneToMany(mappedBy = "agency")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inventory> agencies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Agency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganisationType() {
        return organisationType;
    }

    public Agency organisationType(String organisationType) {
        this.organisationType = organisationType;
        return this;
    }

    public void setOrganisationType(String organisationType) {
        this.organisationType = organisationType;
    }

    public String getContact() {
        return contact;
    }

    public Agency contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Agency logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Agency logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Set<Inventory> getAgencies() {
        return agencies;
    }

    public Agency agencies(Set<Inventory> inventories) {
        this.agencies = inventories;
        return this;
    }

    public Agency addAgency(Inventory inventory) {
        agencies.add(inventory);
        inventory.setAgency(this);
        return this;
    }

    public Agency removeAgency(Inventory inventory) {
        agencies.remove(inventory);
        inventory.setAgency(null);
        return this;
    }

    public void setAgencies(Set<Inventory> inventories) {
        this.agencies = inventories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Agency agency = (Agency) o;
        if(agency.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, agency.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Agency{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", organisationType='" + organisationType + "'" +
            ", contact='" + contact + "'" +
            ", logo='" + logo + "'" +
            ", logoContentType='" + logoContentType + "'" +
            '}';
    }
}
