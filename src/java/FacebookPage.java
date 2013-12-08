
import java.math.BigDecimal;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonProperty;

public class FacebookPage {

    private String university;
    private String id;
    private String name;
    private int talkabout;
    private int likes;
    private Date established;
    private String url;
    private String description;
    private String about;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    private int uniInt;
    private boolean profile;

    public boolean isProfile() {
        return profile;
    }

    public void setProfile(boolean profile) {
        this.profile = profile;
    }



    public int getUniInt() {
        return uniInt;
    }

    public void setUniInt(int uniInt) {
        this.uniInt = uniInt;
    }
    public String getUrl() {
        return url;
    }
@JsonProperty("page_url")
    public void setUrl(String url) {
        this.url = url;
    }

    public FacebookPage() {
    }
    public void setUniversity(String U) {
        this.university = U;
    }
@JsonProperty("page_id")
    public void setId(String I) {
        this.id = I;
    }

    public void setName(String N) {
        this.name = N;
    }
@JsonProperty("talking_about_count")
    public void setTalkabout(int T) {
        this.talkabout = T;
    }
@JsonProperty("fan_count")
    public void setLikes(int L) {
        this.likes = L;
    }

    public void setEstablished(Date D) {
        this.established = D;
    }

    public String getUniversity() {
        return (this.university);
    }

    public String getId() {
        return (this.id);
    }

    public String getName() {
        return (this.name);
    }

    public int getTalkabout() {
        return (this.talkabout);
    }

    public int getLikes() {
        return (this.likes);
    }
    public Date getEstablished() {
        return (this.established);
    }
    
    @Override
    public String toString(){
        String desc = "Nome: "+this.university+"\n"+"Id Facebook: "+this.id+"\n"+"Url: "+this.url+"\n";      
        return (desc);
    }

    void setId(BigDecimal bigDecimal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
