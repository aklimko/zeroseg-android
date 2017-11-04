package pl.adamklimko.zerosegandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("expirationDate")
    private String expirationDate;

    public String getToken() {
        return token;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}
