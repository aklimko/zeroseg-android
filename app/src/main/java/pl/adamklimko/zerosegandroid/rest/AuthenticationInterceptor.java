package pl.adamklimko.zerosegandroid.rest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import pl.adamklimko.zerosegandroid.activity.LoginActivity;

import java.io.IOException;

public class AuthenticationInterceptor implements Interceptor {

    private String authToken;
    private Context context;

    public AuthenticationInterceptor(String token, Context context) {
        this.authToken = token;
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken);

        Request request = builder.build();
        Response response = chain.proceed(request);

        if (response.code() == 401 || response.code() == 403) {
            UserSession.resetToken();
            final Intent login = new Intent(context, LoginActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(login);
            ((Activity) context).finish();
        }

        return response;
    }
}