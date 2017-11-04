package pl.adamklimko.zerosegandroid.rest;

import android.content.Context;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class ApiClient {
    private static final String API_URL = "http://api.adamklimko.pl/raspberry/";
    private static final int TIMEOUT = 5;

    private final static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS);

    private final static OkHttpClient.Builder httpClientAuth = new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS);

    private final static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    // TODO: write another interceptor for network check and rewrite this shit
    public static <S> S createService(Class<S> serviceClass, Context context) {
        httpClient.addNetworkInterceptor(new NetworkInterceptor(context));
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceWithAuth(Class<S> serviceClass, final Context context) {
        if (UserSession.hasToken()) {
            httpClientAuth.addInterceptor(new AuthenticationInterceptor(UserSession.getToken()));
            httpClientAuth.addNetworkInterceptor(new NetworkInterceptor(context));
            builder.client(httpClientAuth.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}
