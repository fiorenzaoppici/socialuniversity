
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Fiorenza
 */
public class TwitterProfile {

    private int followees;
    private int followers;
    private String name;
    private int noTweets;
    private Date established;
    private long id;
    private String location;
    private int countreply;
    private int counttweet;
    private String firstdate;

    public String getFirstdate() {
        return firstdate;
    }

    public void setFirstdate(String firstdate) {
        this.firstdate = firstdate;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }
    private String lastdate;

    public int getCounttweet() {
        return counttweet;
    }

    public void setCounttweet(int counttweet) {
        this.counttweet = counttweet;
    }

    public int getCountreply() {
        return countreply;
    }

    public void setCountreply(int countreply) {
        this.countreply = countreply;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNoTweets() {
        return noTweets;
    }

    public void setNoTweets(int noTweets) {
        this.noTweets = noTweets;
    }

    public Date getEstablished() {
        return established;
    }

    public void setEstablished(Date established) {
        this.established = established;
    }
    private String university;
    private HashMap<Integer, TwitterProfile> followeesList;

    public HashMap<Integer, TwitterProfile> getFolloweesList() {
        return followeesList;
    }

    public void setFolloweesList(HashMap<Integer, TwitterProfile> followeesList) {
        this.followeesList = followeesList;
    }

    public HashMap<Integer, TwitterProfile> getFollowersList() {
        return followersList;
    }

    public void setFollowersList(HashMap<Integer, TwitterProfile> followersList) {
        this.followersList = followersList;
    }
    private HashMap<Integer, TwitterProfile> followersList;

    public TwitterProfile() {
    }
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollowees() {
        return followees;
    }

    public void setFollowees(int followees) {
        this.followees = followees;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public String toString() {
        String desc = "Nome: " + this.university + "\n" + "Handle twitter: " + this.description + "\n";//+ "Followers: " + this.followers + "\n" + "Followees: " + this.followees + "\n";
        return (desc);
    }
}
