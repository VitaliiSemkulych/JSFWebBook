/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
//class which contain model for book author
public class Author implements Serializable{
    private long id;
     private String name;

    public Author() {
    }

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    public void setName(String name) {
        this.name = name;
    }

  
    public String getName() {
        return name;
    }

   
    @Override
    public String toString() {
        return "authorID"+id+", authorName: " + name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

   

  
    
    
}
