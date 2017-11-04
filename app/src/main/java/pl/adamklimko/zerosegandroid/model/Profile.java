package pl.adamklimko.zerosegandroid.model;

public class Profile {
    private String fullName;
    private String facebookId;

    public Profile(String fullName, String facebookId) {
        this.fullName = fullName;
        this.facebookId = facebookId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFacebookId() {
        return facebookId;
    }

}
