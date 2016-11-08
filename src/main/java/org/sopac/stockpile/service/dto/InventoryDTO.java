package org.sopac.stockpile.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Inventory entity.
 */
public class InventoryDTO implements Serializable {

    private Long id;

    private Integer year;

    private Integer month;

    private Double quanity;


    private Long itemId;
    

    private String itemName;

    private Long countryId;
    

    private String countryCountryName;

    private Long locationId;
    

    private String locationLocation;

    private Long agencyId;
    

    private String agencyName;

    private Long clusterId;
    

    private String clusterName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
    public Double getQuanity() {
        return quanity;
    }

    public void setQuanity(Double quanity) {
        this.quanity = quanity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }


    public String getCountryCountryName() {
        return countryCountryName;
    }

    public void setCountryCountryName(String countryCountryName) {
        this.countryCountryName = countryCountryName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }


    public String getLocationLocation() {
        return locationLocation;
    }

    public void setLocationLocation(String locationLocation) {
        this.locationLocation = locationLocation;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }


    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }


    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InventoryDTO inventoryDTO = (InventoryDTO) o;

        if ( ! Objects.equals(id, inventoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InventoryDTO{" +
            "id=" + id +
            ", year='" + year + "'" +
            ", month='" + month + "'" +
            ", quanity='" + quanity + "'" +
            '}';
    }
}
