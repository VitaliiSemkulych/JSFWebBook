/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import controllers.EditUserDataController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import utilities.ConnectionDBUtility;

/**
 *
 * @author Admin
 */
// validator which check if registr email already exist in the system
@FacesValidator("validators.UserExistValidator")
public class UserExistValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
       boolean isUserEmailExist=false;
    Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet rs =st.executeQuery("select * from users where userid='"+value.toString()+"'")) {
          if(rs.next()){
          isUserEmailExist=true;
          }

        } catch (SQLException ex) {
            Logger.getLogger(EditUserDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!isUserEmailExist){
          FacesMessage message = new FacesMessage("User with current email doesn't exist.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
}
