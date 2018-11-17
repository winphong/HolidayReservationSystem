/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import entity.PartnerReservationEntity;
import java.util.List;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author twp10
 */
public interface PartnerEntityControllerLocal {
    
    public PartnerEntity createNewPartner(PartnerEntity newPartner);

    public PartnerEntity retrievePartnerByUsername(String username);

    public PartnerEntity retrievePartnerById(Long partnerId);
    
    public PartnerEntity partnerLogin(String username, String password) throws InvalidLoginCredentialException;
    
    public List<PartnerReservationEntity> retrieveAllReservations(Long partnerId);
}
