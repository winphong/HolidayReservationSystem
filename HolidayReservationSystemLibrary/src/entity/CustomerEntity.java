/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Asus
 */
@Entity
public class CustomerEntity extends GuestEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private PartnerEntity partner;
    @OneToMany (mappedBy = "customer")
    private List <PartnerReservationEntity> partnerReservationEntities;

    public CustomerEntity() {
    }

    public CustomerEntity(PartnerEntity partner, String firstName, String lastName, String userName, String password, Long phoneNumber, String email) {
        super(firstName, lastName, userName, password, phoneNumber, email);
        this.partner = partner;
        this.partnerReservationEntities = new ArrayList <PartnerReservationEntity>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerEntity)) {
            return false;
        }
        CustomerEntity other = (CustomerEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CustomerEntity[ id=" + id + " ]";
    }

    /**
     * @return the partner
     */
    public PartnerEntity getPartner() {
        return partner;
    }

    /**
     * @param partner the partner to set
     */
    public void setPartner(PartnerEntity partner) {
        this.partner = partner;
    }

    /**
     * @return the partnerReservationEntities
     */
    public List <PartnerReservationEntity> getPartnerReservationEntities() {
        return partnerReservationEntities;
    }

    /**
     * @param partnerReservationEntities the partnerReservationEntities to set
     */
    public void setPartnerReservationEntities(List <PartnerReservationEntity> partnerReservationEntities) {
        this.partnerReservationEntities = partnerReservationEntities;
    }
    
}
