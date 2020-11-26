/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Admin
 */
// validator which validate password field
@FacesValidator("validators.PasswordValidator")
public class PasswordValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        boolean isErrorOccurred = false;
        boolean isNumberContains=false;
        boolean isBigLetterContains=false;
        //check if password contains only number or big and small english letter
        for(int i= 0; i<value.toString().length();i++){
      char c= value.toString().charAt(i);
      if((int)c<48 ||((int)c>57 && (int)c<65) || ((int)c>90 && (int)c<97) || (int)c>122 ){
      isErrorOccurred=true;
      }
      if((int)c>=48 && (int)c<=57){
      isNumberContains=true;
      }
      if((int)c>=65 && (int)c<=90){
      isBigLetterContains=true;
      }
        }
        if(!isBigLetterContains || !isNumberContains || isErrorOccurred){
            FacesMessage message = new FacesMessage("Password have to contain at least one big letter and one number symbol. You can use just english letter.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
}
