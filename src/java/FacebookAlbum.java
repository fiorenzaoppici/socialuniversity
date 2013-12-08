

public class FacebookAlbum {
    private String id;
    private String name;
    private int count;
    private String link;
    private String location;
    private String description;
    private String created_time;
    private String updated_time;

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }
    private boolean can_upload;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCan_upload() {
        return can_upload;
    }

    public void setCan_upload(boolean can_upload) {
        this.can_upload = can_upload;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getCount() {
        return count;
    }
   
    public void setCount(int count) {
        this.count = count;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    //private int like_count;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
