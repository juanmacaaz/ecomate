package com.ecomate;

public class Image implements java.io.Serializable{
    // Class to store the information of an image (document in Firebase)
    private String iatype;
    private String image_url;
    private long timestamp;
    private String user_id;
    private String usertype;

    public Image() {
    }

    public Image(String iatype, String image_url, long timestamp, String user_id, String usertype) {
        this.iatype = iatype;
        this.image_url = image_url;
        this.timestamp = timestamp;
        this.user_id = user_id;
        this.usertype = usertype;
    }

    public String getIatype() {
        return iatype;
    }

    public void setIatype(String iatype) {
        this.iatype = iatype;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    @Override
    public String toString() {
        return "Image{" +
                "iatype='" + iatype + '\'' +
                ", image_url='" + image_url + '\'' +
                ", timestamp=" + timestamp +
                ", user_id='" + user_id + '\'' +
                ", usertype='" + usertype + '\'' +
                '}';
    }
}
