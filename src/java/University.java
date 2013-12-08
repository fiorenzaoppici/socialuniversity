
import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fiorenza
 */
public class University {
    private String name;
    private String website;
    private int studentnumber;
    private List<FacebookAlbum>albums;
    private String facebookName;

    public String getFacebookName() {
        return facebookName;
    }

    public void setFacebookName(String facebookName) {
        this.facebookName = facebookName;
    }

    public List<FacebookAlbum> getAlbums() {
        return albums;
    }

    public void setAlbums(List<FacebookAlbum> albums) {
        this.albums = albums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getStudentnumber() {
        return studentnumber;
    }

    public void setStudentnumber(int studentnumber) {
        this.studentnumber = studentnumber;
    }
    
}
