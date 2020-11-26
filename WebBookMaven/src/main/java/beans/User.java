/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import tool.Formatting;
import utilities.ConnectionDBUtility;

/**
 *
 * @author Admin
 */
// form model in which data will be written after registration
@Named
@SessionScoped
public class User implements Serializable {

    private String userName;
    private String telephoneNumber;
    private String userEmail;
    private String password;
    private byte[] image;
    private  Part imageContainer;
    
    private boolean addBook = false;
    private String errorMessage = "";
    private String registrationInfo = "";
    

    public User() {

    }

    public User(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;

    }

    public boolean isAddBook() {
        return addBook;
    }

    public void setAddBook(boolean addBook) {
        this.addBook = addBook;
    }

    public String getUserName() {
        return userName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void changeAddBook() {
        addBook = !addBook;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    
    public void setimageContainer(Part imageContainer) {
        this.imageContainer = imageContainer;
    }

    public Part getImageContainer() {
        return imageContainer;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setRegistrationInfo(String registrationInfo) {
        this.registrationInfo = registrationInfo;
    }

    public String getRegistrationInfo() {
        return registrationInfo;
    }
    public String getFormedEmail(){
    
    return Formatting.handleEmailText(userEmail);
    }

    public void fillImage(){
    try (InputStream inputImage = imageContainer.getInputStream()) {
          image = new byte[inputImage.available()];
          inputImage.read(image);
             
    }    catch (IOException ex) {
             Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
     public byte[] getImage(String id) {

        Connection conn = ConnectionDBUtility.getInstance();
System.out.println(id);
        byte[] image = null;

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select image from users where userid='" + id+"'");) {
            while (rs.next()) {
                image = rs.getBytes("image");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Book.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return image;
    }
    public String login() {
        registrationInfo="";
        errorMessage = "";
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select * from users where userid='" + userEmail + "'");) {
            if (res.next()) {
                
               
                    try {

                        ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).login(userEmail, password);
                        updateUserData();
                        return "mainPage";
                    } catch (ServletException ex) {
                        errorMessage = "Password is not correct!";
                    }

               
            } else {

                errorMessage = "Account with current email is not exist.";
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "index";

    }

    public String logout() {
        registrationInfo="";
        errorMessage = "";
        String result = "exit";

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
        } catch (ServletException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
        }

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return result;
    }

    private void updateUserData() {
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from users where userid='"+userEmail+"'");) {
            while (rs.next()) {
               
                    userName = rs.getString("userName");
                    telephoneNumber = rs.getString("tephoneNumber");
                    password = rs.getString("password");
                    image = rs.getBytes("image");
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String registrate() {
        registrationInfo="";
        errorMessage = "";
        fillImage();
        Connection conn = ConnectionDBUtility.getInstance();
            
        String sql1 = "INSERT INTO users (userid, password, tephoneNumber, userName,image) "
                + "VALUES ('" + userEmail + "','" + password + "','" + telephoneNumber + "','" + userName + "',?)";
        String sql2 = "INSERT INTO users_groups (GROUPID,USERID) VALUES ('user','" + userEmail + "')";

        try (Statement stmt = conn.createStatement();
                PreparedStatement ps=conn.prepareStatement(sql1)) {

            ps.setBytes(1, image);
            ps.execute();
            stmt.execute(sql2);
           
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

       registrationInfo="Registration successful. Please login.";
        return "index";
    }
}
