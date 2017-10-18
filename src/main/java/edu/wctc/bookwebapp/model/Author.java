package edu.wctc.bookwebapp.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author jlombardo
 */
public class Author {
    private Integer authorId;
    private String authorName;
    private Date dateAdded;

    /**
     *
     */
    public Author() {
    }

    /**
     *
     * @param authorId
     */
    public Author(Integer authorId) {
        this.authorId = authorId;
    }

    /**
     *
     * @param authorId
     * @param authorName
     * @param dateAdded
     */
    public Author(Integer authorId, String authorName, Date dateAdded) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateAdded = dateAdded;
    }

    /**
     *
     * @return
     */
    public Integer getAuthorId() {
        return authorId;
    }

    /**
     *
     * @param authorId
     */
    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    /**
     *
     * @return
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     *
     * @param authorName
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     *
     * @return
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     *
     * @param dateAdded
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.authorId);
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
        if (!Objects.equals(this.authorId, other.authorId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + "authorId=" + authorId + ", authorName=" + authorName + ", dateAdded=" + dateAdded + '}';
    }
    
    
}