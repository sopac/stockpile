package org.sopac.stockpile.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Inventory.
 */
@Entity
@Table(name = "inventory")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "quanity")
    private Double quanity;

    @ManyToOne
    private Item item;

    @ManyToOne
    private Country country;

    @ManyToOne
    private Location location;

    @ManyToOne
    private Agency agency;

    @ManyToOne
    private Cluster cluster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public Inventory year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public Inventory month(Integer month) {
        this.month = month;
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getQuanity() {
        return quanity;
    }

    public Inventory quanity(Double quanity) {
        this.quanity = quanity;
        return this;
    }

    public void setQuanity(Double quanity) {
        this.quanity = quanity;
    }

    public Item getItem() {
        return item;
    }

    public Inventory item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Country getCountry() {
        return country;
    }

    public Inventory country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Location getLocation() {
        return location;
    }

    public Inventory location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Agency getAgency() {
        return agency;
    }

    public Inventory agency(Agency agency) {
        this.agency = agency;
        return this;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public Inventory cluster(Cluster cluster) {
        this.cluster = cluster;
        return this;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Inventory inventory = (Inventory) o;
        if(inventory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, inventory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Inventory{" +
            "id=" + id +
            ", year='" + year + "'" +
            ", month='" + month + "'" +
            ", quanity='" + quanity + "'" +
            '}';
    }
}
