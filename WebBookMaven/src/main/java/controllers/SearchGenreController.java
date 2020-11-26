/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Genre;
import dao.GenreDAO;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.omnifaces.cdi.Eager;



@Named
@Eager
@ApplicationScoped
// controller work with genres of books
public class SearchGenreController implements Serializable{
    private List<Genre> genreList=null;

    public SearchGenreController() {
        genreList=new GenreDAO().getAll(); 
    }
   //return all posible genres
    public List<Genre> getGenreList() {
        return genreList;
    }
    
    
}
