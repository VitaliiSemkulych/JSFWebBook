/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Admin
 */

@Named
@RequestScoped
//controller which work with error page
public class ErrorController {
    
    
    
    public String goToLoginPage(){
    return "exit";
    
    }
    public String goToMainPage(){
    return "main";
    
    }
}
