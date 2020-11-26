/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Book;
import beans.Recension;
import beans.User;
import emums.SearchType;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Eager;
import service.RecensionService;
import utilities.ConnectionDBUtility;
import tool.Formatting;

/**
 *
 * @author Admin
 */
// controller which work with single book page
@Named
@Eager
@SessionScoped
public class SingleBookController implements Serializable {

    @Inject
    User user;

    @Inject
    UserPageController userPageController;

    @Inject
    SearchController searchController;

    private Book book;

    private final RecensionService recensionService = new RecensionService();

    private String recensionText;
    private String modifyRecensionText;
    private long checkedRecentionID;
    private boolean recencionModifying = false;
    private int mark;

    private boolean inReadingMode;
    private boolean inInterestingMode;
    private boolean inReadedMode;

    //ceck if book is marcked as reading
    public boolean checkIfInReadingMode() {

        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select * from reading_book where book_id=" + String.valueOf(book.getId()) + " and user_id='" + user.getUserEmail() + "'");) {
            if (res.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //ceck if book is marcked as interesting
    public boolean checkIfInInterestingMode() {
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select * from interesting_book where book_id=" + String.valueOf(book.getId()) + " and user_id='" + user.getUserEmail() + "'");) {
            if (res.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //ceck if book is marcked as read
    public boolean checkIfInReadedMode() {
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select * from readed_book where book_id=" + String.valueOf(book.getId()) + " and user_id='" + user.getUserEmail() + "'");) {
            if (res.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //marke or unmarcke book as readiing
    public String addBookInReadingMode() {
        Connection conn = ConnectionDBUtility.getInstance();
        if (!inReadingMode) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO reading_book (book_id,user_id) VALUES(?,?)");) {
                st.setLong(1, book.getId());
                st.setString(2, user.getUserEmail());
                st.execute();
            } catch (SQLException ex) {
                Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
            }

            inReadingMode = true;
        } else {
            try (Statement st = conn.createStatement();) {
                st.execute("DELETE FROM reading_book WHERE book_id=" + String.valueOf(book.getId()) + " and user_id='" + user.getUserEmail() + "'");
            } catch (SQLException ex) {
                Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
            inReadingMode = false;

        }
        return "singleBook";

    }

    //marke or unmarcke book as interesting
    public String addBookInInterestingMode() {

        Connection conn = ConnectionDBUtility.getInstance();
        if (!inInterestingMode) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO interesting_book (book_id,user_id) VALUES(?,?)");) {
                st.setLong(1, book.getId());
                st.setString(2, user.getUserEmail());
                st.execute();
            } catch (SQLException ex) {
                Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
            }

            inInterestingMode = true;
        } else {
            try (Statement st = conn.createStatement();) {
                st.execute("DELETE FROM interesting_book WHERE book_id=" + String.valueOf(book.getId()) + " and user_id='" + user.getUserEmail() + "'");
            } catch (SQLException ex) {
                Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
            inInterestingMode = false;

        }
        return "singleBook";
    }

//marke or unmarcke book as read
    public String addBookInReadedMode() {

        Connection conn = ConnectionDBUtility.getInstance();
        if (!inReadedMode) {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO readed_book (book_id,user_id) VALUES(?,?)");) {
                st.setLong(1, book.getId());
                st.setString(2, user.getUserEmail());
                st.execute();
            } catch (SQLException ex) {
                Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
            }

            inReadedMode = true;
        } else {
            try (Statement st = conn.createStatement();) {
                st.execute("DELETE FROM readed_book WHERE book_id=" + String.valueOf(book.getId()) + " and user_id='" + user.getUserEmail() + "'");
            } catch (SQLException ex) {
                Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
            inReadedMode = false;
        }
        return "singleBook";
    }

    public Book getBook() {
        return book;
    }
// method executed where user move to selected book page

    public String chosenBookPage() {
        userPageController.setInteresting(false);
        userPageController.setRead(false);
        userPageController.setReading(false);
        userPageController.setShowUserPage(false);
        searchController.setLetter(' ');
        searchController.setGenreId(-1);
        searchController.setSearchFrace("");
        searchController.setSearchType(SearchType.AUTHOR);
        String bookID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("book_id");
        book = searchController.getBookService().selectBookByID(bookID);
        inReadingMode = checkIfInReadingMode();
        inInterestingMode = checkIfInInterestingMode();
        inReadedMode = checkIfInReadedMode();
        checkedRecentionID = -1;
        return "singleBook";
    }
//add pdf content to selected book

    public void fillBookContent() {
        recencionModifying = false;
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select content from book where id=" + book.getId());) {

            while (rs.next()) {
                book.setContent(rs.getBytes("content"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getMark() {
        return mark;
    }

    public String getRecensionText() {
        return recensionText;
    }

    public void setRecensionText(String recensionText) {
        this.recensionText = recensionText;
    }

    public boolean isInReadingMode() {
        return inReadingMode;
    }

    public boolean isInInterestingMode() {
        return inInterestingMode;
    }

    public boolean isInReadedMode() {
        return inReadedMode;
    }

    public void setInReadingMode(boolean inReadingMode) {
        this.inReadingMode = inReadingMode;
    }

    public void setInInterestingMode(boolean inInterestingMode) {
        this.inInterestingMode = inInterestingMode;
    }

    public void setInReadedMode(boolean inReadedMode) {
        this.inReadedMode = inReadedMode;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public boolean isEvaluatedByUser() {
        boolean isEvaluated = false;
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select * from evaluation where book_id=" + String.valueOf(book.getId()) + " and user_id='" + user.getUserEmail() + "'");) {
            if (res.next()) {
                isEvaluated = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isEvaluated;
    }

    public String getMarkExergue() {
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet res = st.executeQuery("select sum(mark) as summ, count(evaluation_id) as numb from evaluation where book_id=" + String.valueOf(book.getId()));) {
            if (res.next()) {
                long sum = res.getLong("summ");
                int count = res.getInt("numb");
                float result = Math.round(100 * (sum / (double) count)) / (float) 100;
                if (result != 0.0f) {
                    return String.valueOf(result);
                } else {
                    return "Not evaluated yet";
                }
            } else {
                return "Not evaluated yet";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error";
    }

    public String updateMark() {
        Connection conn = ConnectionDBUtility.getInstance();
        try (PreparedStatement st = conn.prepareStatement(
                "INSERT INTO evaluation (user_id,book_id,mark) "
                + "VALUES(?,?,?)");) {

            st.setString(1, user.getUserEmail());
            st.setLong(2, book.getId());
            st.setInt(3, mark);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "singleBook";
    }

    public String sendRecension() {

        Connection conn = ConnectionDBUtility.getInstance();
        try (PreparedStatement st = conn.prepareStatement(
                "INSERT INTO recension (user_id,book_id, recension_text) "
                + "VALUES(?,?,?)");) {

            st.setString(1, user.getUserEmail());
            st.setLong(2, book.getId());

            st.setString(3, Formatting.handleEmailText(recensionText));

            st.executeUpdate();
            recensionText = "";
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "singleBook";

    }

    public String modifyRecension() {
        long recentionId = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("recension_id"));

        Connection conn = ConnectionDBUtility.getInstance();
        try (PreparedStatement st = conn.prepareStatement("update recension set recension_text=? where recension_id=?");) {

            st.setString(1, Formatting.handleEmailText(modifyRecensionText));
            st.setLong(2, recentionId);

            st.executeUpdate();
            modifyRecensionText = "";
        } catch (SQLException ex) {
            Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        checkedRecentionID = -1;
        recencionModifying = false;
        return "singleBook";
    }

    public List<Recension> getRecensionListByUser() {
        return recensionService.getRecensionListByUser(book.getId());

    }

    public String delateRecension() {
        long recentionId = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("recension_id"));
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();) {
            String deleteSql = "DELETE FROM recension WHERE recension_id=" + String.valueOf(recentionId);
            st.executeUpdate(deleteSql);

        } catch (SQLException ex) {
            Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "singleBook";

    }

    public boolean isRecencionModifying() {
        return recencionModifying;
    }

    public void setRecencionModifying(boolean recencionModifying) {
        this.recencionModifying = recencionModifying;
    }

    public void canselRecensionModifyMode() {
        checkedRecentionID = -1;
        recencionModifying = false;
    }

    public String getModifyRecensionText() {
        return modifyRecensionText;
    }

    public void setModifyRecensionText(String modifyRecensionText) {
        this.modifyRecensionText = modifyRecensionText;
    }

    public long getCheckedRecentionID() {
        return checkedRecentionID;
    }

    public void setCheckedRecentionID(long checkedRecentionID) {
        this.checkedRecentionID = checkedRecentionID;
    }

    public void modifyngRecensionTextFormText() {
        recencionModifying = true;
        checkedRecentionID = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("recension_id"));
        Connection conn = ConnectionDBUtility.getInstance();
        try (Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("Select recension_text from recension where recension_id=" + String.valueOf(checkedRecentionID));) {
            while (rs.next()) {
                modifyRecensionText = rs.getString("recension_text");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SingleBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
