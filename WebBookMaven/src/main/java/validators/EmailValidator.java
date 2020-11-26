/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import beans.User;
import java.sql.Connection;
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
// validator which validate email field
@FacesValidator("validators.EmailValidator")
public class EmailValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
       boolean isExist=false;
       
          Connection conn = ConnectionDBUtility.getInstance();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from users");) {
            while (rs.next()) {
                if(rs.getString("userid").equals(value.toString())){
              isExist=true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

       if(isExist){
       FacesMessage message = new FacesMessage("User with current email already exist.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
       
       }
    }
    
}
