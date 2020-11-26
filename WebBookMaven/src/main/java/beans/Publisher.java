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
//class which contain model for book publisher
public class Publisher implements Serializable{
     private long id;
    private String publisherName;

    public Publisher() {
    }

    public Publisher(long id, String publisherName) {
        this.id = id;
        this.publisherName = publisherName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Publisher other = (Publisher) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

  
      @Override
    public String toString() {
        return "publisherID: " + id+", publisherName: " + publisherName;
    }
}
