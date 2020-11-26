/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.Eager;

/**
 *
 * @author Admin
 */
@Named
@Eager
@SessionScoped
//controller which work with user marckbook page
public class UserPageController implements Serializable {
    
    @Inject
    SearchController searchController;
    
    @Inject
    EditUserDataController editUserDataController;

  

    
    private boolean showUserPage;
    private boolean reading;
    private boolean read;
    private boolean interesting;
    
    public UserPageController() {
    }
    
     // method executed where user move to user marckbook page
    public String selectUserPage() {
        
        editUserDataController.setEditPersonalData(false);
       
        showUserPage = true;
        reading = true;
        read = false;
        interesting = false;
        searchController.prepareUserReadingBookList();
        
        return "userPage";
    }
    // executed where user trying to retrieve book which marcked as reading
    public String selectReadingState() {
        reading = true;
        read = false;
        interesting = false;
        searchController.prepareUserReadingBookList();
        return "userPage";
    }
    // executed where user trying to retrieve book which marcked as read
    public String selectReadState() {
        reading = false;
        read = true;
        interesting = false;
        searchController.prepareUserReadedBookList();
        return "userPage";
    }
    // executed where user trying to retrieve book which marcked as interesting 
    public String selectInterestingState() {
        reading = false;
        read = false;
        interesting = true;
        searchController.prepareUserInterestingBookList();
        return "userPage";
    }
    
    
    public boolean isShowUserPage() {
        return showUserPage;
    }
    
    public void setShowUserPage(boolean showUsetPage) {
        this.showUserPage = showUsetPage;
    }
    
    public boolean isReading() {
        return reading;
    }
    
    public boolean isRead() {
        return read;
    }
    
    public boolean isInteresting() {
        return interesting;
    }
    
    public void setReading(boolean reading) {
        this.reading = reading;
    }
    
    public void setRead(boolean readed) {
        this.read = readed;
    }
    
    public void setInteresting(boolean interesting) {
        this.interesting = interesting;
    }
    
}
