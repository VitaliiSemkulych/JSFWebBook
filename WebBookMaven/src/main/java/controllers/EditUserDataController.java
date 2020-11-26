/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.User;
import emums.SearchType;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.Eager;
import utilities.ConnectionDBUtility;

/**
 *
 * @author Admin
 */
@Named
@Eager
@SessionScoped
//controller which work with update user information page
public class EditUserDataController implements Serializable {

    @Inject
    User user;

    @Inject
    SearchController searchController;
    
    private boolean editPersonalData = false;
    private String adminEmail;

    @Inject
    UserPageController userPageController;


    public EditUserDataController() {

    }

    // update personal data
    public void changePersonalData() {
        Connection conn = ConnectionDBUtility.getInstance();
        try (
                PreparedStatement st = conn.prepareStatement("update users set password=?,tephoneNumber=?,userName=? where userid=?");) {

            st.setString(1, user.getPassword());
            st.setString(2, user.getTelephoneNumber());
            st.setString(3, user.getUserName());
            st.setString(4, user.getUserEmail());
            st.executeUpdate();
            updateImage(conn);
        } catch (SQLException ex) {
            Logger.getLogger(EditUserDataController.class.getName()).log(Level.SEVERE, null, ex);
        }

        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("Updated");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        context.addMessage("updatePDForm", message);
        // return "Settings";
    }
   //update image by user id
    private void updateImage(Connection conn) {
        if (user.getImageContainer() != null) {
            user.fillImage();
            try (PreparedStatement st = conn.prepareStatement("update users set image=? where userid=?");) {
                st.setBytes(1, user.getImage());
                st.setString(2, user.getUserEmail());
                st.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    // method executed where user move to edit user information page
    public String changeEditPersonalData() {   
        userPageController.setShowUserPage(false);
        searchController.setLetter(' ');
        searchController.setGenreId(-1);
        searchController.setSearchFrace("");
        searchController.setSearchType(SearchType.AUTHOR);
        editPersonalData = true;
        return "Settings";
    }

    public boolean isEditPersonalData() {
        return editPersonalData;
    }

    public void setEditPersonalData(boolean editPersonalData) {
        this.editPersonalData = editPersonalData;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

}
