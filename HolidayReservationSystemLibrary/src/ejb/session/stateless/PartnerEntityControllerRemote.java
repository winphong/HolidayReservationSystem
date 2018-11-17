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

public interface PartnerEntityControllerRemote {

    public PartnerEntity createNewPartner(PartnerEntity newPartner);

    public List<PartnerEntity> viewAllPartner();

    public PartnerEntity partnerLogin(String username, String password) throws InvalidLoginCredentialException;

    public List<PartnerReservationEntity> retrieveAllReservations(Long partnerId);
    
}
