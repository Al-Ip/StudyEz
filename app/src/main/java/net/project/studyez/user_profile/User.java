package net.project.studyez.user_profile;

public class User {

    private String id;
    private int ezPoints;
    private String profileImage;
    private String username;
    private String email;
    private String description;
    private int friendsCount;

    public User(){

    }

    //On initial sign up, REQUIRED
    public User(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public int getEzPoints() {
        return ezPoints;
    }

    public void setEzPoints(int ezPoints) {
        this.ezPoints = ezPoints;
    }
}