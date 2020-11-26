/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Book;
import beans.User;
import emums.SearchType;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.Eager;
import service.BookService;
import utilities.ConnectionDBUtility;

/**
 *
 * @author Admin
 */
/*
* Controller which work with whole search functionality in application
* */
@Named
@Eager
@SessionScoped
public class SearchController implements Serializable {

    @Inject
    EditUserDataController editUserDataController;
    @Inject
    SingleBookController singleBookController;
    @Inject
    UserPageController userPageController;
    @Inject
    User user;

    private final BookService bookService = new BookService();
    private SearchType searchType;
    private String searchFrace = "";
    private static Map<String, SearchType> searchMap = new HashMap<String, SearchType>();

    private int genreId;
    private char letter;
    private int selectedPageNumber;
    private int numberBookOnPage = 3;
    private long searchedBooksNumber;
    private List<Integer> pageNumbers = new ArrayList<Integer>();
    private List<Integer> showedPageNumbers = new ArrayList<Integer>();
    private List<Book> currentSearchList_Page = new ArrayList<Book>();
    private List<Book> currentSearchList_Full;

    public SearchController() {
        currentSearchList_Full = bookService.selectAll();
        searchedBooksNumber = currentSearchList_Full.size();
        selectedPageNumber = 1;
        letter = ' ';
        genreId = 17;
        fillPageNumberArray(searchedBooksNumber, numberBookOnPage);
        preparePageList();

        searchMap.put(SearchType.AUTHOR.getType(), SearchType.AUTHOR);
        searchMap.put(SearchType.BOOK_NAME.getType(), SearchType.BOOK_NAME);
    }
//search books by selected genre
    public String bookListByGenre() {
        genreId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("genre_id"));
        if (genreId == 17) {
            currentSearchList_Full = bookService.selectAll();
        } else {
            currentSearchList_Full = bookService.selectByGenreID(String.valueOf(genreId));
        }
        searchedBooksNumber = currentSearchList_Full.size();
        searchFrace = "";
        searchType = SearchType.AUTHOR;
        letter = ' ';
        selectedPageNumber = 1;
        fillPageNumberArray(searchedBooksNumber, numberBookOnPage);
        preparePageList();
        singleBookController.setRecencionModifying(false);
        userPageController.setShowUserPage(false);
        userPageController.setReading(false);
        userPageController.setRead(false);
        userPageController.setInteresting(false);
        editUserDataController.setEditPersonalData(false);

        return "main";
    }

    //search books by author or book name
    public String bookListBySearch() {
        currentSearchList_Full = bookService.selectByFrace(searchFrace, searchType);
        searchedBooksNumber = currentSearchList_Full.size();

        letter = ' ';
        genreId = -1;
        selectedPageNumber = 1;
        fillPageNumberArray(searchedBooksNumber, numberBookOnPage);
        preparePageList();
        singleBookController.setRecencionModifying(false);
        userPageController.setShowUserPage(false);
        userPageController.setReading(false);
        userPageController.setRead(false);
        userPageController.setInteresting(false);
        editUserDataController.setEditPersonalData(false);
        return "main";
    }

    //search books by first letter of book name
    public String bookListByLetter() {
        letter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("letter").charAt(0);
        currentSearchList_Full = bookService.selectByLetter(String.valueOf(letter));
        searchedBooksNumber = currentSearchList_Full.size();
        searchFrace = "";
        searchType = SearchType.AUTHOR;
        genreId = -1;
        selectedPageNumber = 1;
        fillPageNumberArray(searchedBooksNumber, numberBookOnPage);
        preparePageList();
        singleBookController.setRecencionModifying(false);
        userPageController.setShowUserPage(false);
        userPageController.setReading(false);
        userPageController.setRead(false);
        userPageController.setInteresting(false);
        editUserDataController.setEditPersonalData(false);
        return "main";
    }

    //select list of books which marked as reading by user
    public void prepareUserReadingBookList() {

        currentSearchList_Full = bookService.selectReadingBookByUserID(user.getUserEmail());
        searchedBooksNumber = currentSearchList_Full.size();
        letter = ' ';
        searchFrace = "";
        searchType = SearchType.AUTHOR;
        genreId = -1;
        selectedPageNumber = 1;
        fillPageNumberArray(searchedBooksNumber, numberBookOnPage);
        preparePageList();

    }

    //select list of books which marked as read by user
    public void prepareUserReadedBookList() {

        currentSearchList_Full = bookService.selectReadedBookByUserID(user.getUserEmail());
        searchedBooksNumber = currentSearchList_Full.size();

        letter = ' ';
        searchFrace = "";
        searchType = SearchType.AUTHOR;
        genreId = -1;
        selectedPageNumber = 1;
        fillPageNumberArray(searchedBooksNumber, numberBookOnPage);
        preparePageList();

    }

    //select list of books which marked as interesting by user
    public void prepareUserInterestingBookList() {

        currentSearchList_Full = bookService.selectInterestingBookByUserID(user.getUserEmail());
        searchedBooksNumber = currentSearchList_Full.size();

        letter = ' ';
        searchFrace = "";
        searchType = SearchType.AUTHOR;
        genreId = -1;
        selectedPageNumber = 1;
        fillPageNumberArray(searchedBooksNumber, numberBookOnPage);
        preparePageList();

    }

    //create array with page number
    public void fillPageNumberArray(long totalBooksCount, int booksCountOnPage) {
        int pageCount = 0;
        if (totalBooksCount > 0) {
            if (totalBooksCount % (float) booksCountOnPage == 0) {
                pageCount = (int) (totalBooksCount / booksCountOnPage);
            } else {
                pageCount = (int) (totalBooksCount / booksCountOnPage) + 1;
            }
        }
        pageNumbers.clear();
        for (int i = 1; i <= pageCount; i++) {
            pageNumbers.add(i);
        }
        fillShowedPageNumberArray();
    }

    //create array with ;age number which will be show
    public void fillShowedPageNumberArray() {
        showedPageNumbers.clear();
        if (pageNumbers.size() > 4) {
            if (selectedPageNumber == 1 || selectedPageNumber == 2) {
                for (int i = 0; i < 5; i++) {
                    showedPageNumbers.add(pageNumbers.get(i));
                }
            } else if (selectedPageNumber == pageNumbers.size() || selectedPageNumber == pageNumbers.size() - 1) {
                for (int i = pageNumbers.size() - 5; i < pageNumbers.size(); i++) {
                    showedPageNumbers.add(pageNumbers.get(i));
                }
            } else {
                for (int i = selectedPageNumber - 3; i < selectedPageNumber + 2; i++) {
                    showedPageNumbers.add(pageNumbers.get(i));
                }
            }
        } else {
            for (int i = 0; i < pageNumbers.size(); i++) {
                showedPageNumbers.add(pageNumbers.get(i));
            }

        }

    }

    //crate list of book which will be show on the page
    public void preparePageList() {
        currentSearchList_Page.clear();
        int firstBookNumber = numberBookOnPage * selectedPageNumber - numberBookOnPage;
        long lastBookNumber = 0;
        if (firstBookNumber + numberBookOnPage > searchedBooksNumber) {
            lastBookNumber = searchedBooksNumber;
        } else {
            lastBookNumber = firstBookNumber + numberBookOnPage;
        }
        for (int i = firstBookNumber; i < lastBookNumber; i++) {
            currentSearchList_Page.add(currentSearchList_Full.get(i));
        }

    }

    public void previousPage() {
        if (selectedPageNumber > 1) {
            selectedPageNumber--;
            fillShowedPageNumberArray();
            preparePageList();
        } else {
            selectedPageNumber = pageNumbers.size();
            fillShowedPageNumberArray();
            preparePageList();
        }
    }

    public void nextPage() {
        if (selectedPageNumber < pageNumbers.size()) {
            selectedPageNumber++;
            fillShowedPageNumberArray();
            preparePageList();
        } else {
            selectedPageNumber = 1;
            fillShowedPageNumberArray();
            preparePageList();
        }
    }

    //change selected page
    public void selectPage() {
        selectedPageNumber = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("page_number"));
        fillShowedPageNumberArray();
        preparePageList();
    }

    public byte[] getImage(long id) {

        Connection conn = ConnectionDBUtility.getInstance();

        byte[] image = null;

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select image from book where id=" + id);) {
            while (rs.next()) {
                image = rs.getBytes("image");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Book.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return image;
    }

    public List<Book> getCurrentSearchList() {
        return currentSearchList_Page;

    }

    public BookService getBookService() {
        return bookService;
    }

    public Map<String, SearchType> getSearchMap() {
        return searchMap;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public String getSearchFrace() {
        return searchFrace;
    }

    public void setSearchFrace(String searchFrace) {
        this.searchFrace = searchFrace;
    }

    public int getGenreId() {
        return genreId;
    }

    public char getLetter() {
        return letter;
    }

    public long getSelectedPageNumber() {
        return selectedPageNumber;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public void setSelectedPageNumber(int selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public void setSearchedBooksNumber(int searchedBooksNumber) {
        this.searchedBooksNumber = searchedBooksNumber;
    }

    public int getNumberBookOnPage() {
        return numberBookOnPage;
    }

    public void setNumberBookOnPage(int numberBookOnPage) {
        this.numberBookOnPage = numberBookOnPage;
    }

    public long getSearchedBooksNumber() {
        return searchedBooksNumber;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public List<Integer> getShowedPageNumbers() {
        return showedPageNumbers;
    }

    public List<Book> getCurrentSearchList_Full() {
        return currentSearchList_Full;
    }

}
