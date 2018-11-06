/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author twp10
 */
@Remote
public interface PartnerEntityControllerRemote {

    public PartnerEntity createNewPartner(PartnerEntity newPartner);

    public List<PartnerEntity> viewAllPartner();
    
}
