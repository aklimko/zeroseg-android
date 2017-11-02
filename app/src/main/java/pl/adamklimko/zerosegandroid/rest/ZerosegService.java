package pl.adamklimko.zerosegandroid.rest;

import pl.adamklimko.zerosegandroid.model.Message;
import pl.adamklimko.zerosegandroid.model.Profile;
import pl.adamklimko.zerosegandroid.model.Token;
import pl.adamklimko.zerosegandroid.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ZerosegService {
    @POST("messages")
    Call<Message> postMessage(@Body Message message);

    @POST("login")
    Call<Token> login(@Body User user);

    @GET("user/profile")
    Call<Profile> getProfile();
}
