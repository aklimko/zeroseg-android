package pl.adamklimko.zerosegandroid.rest;

import android.content.Context;
import android.text.TextUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String API_URL = "http://api.adamklimko.pl/raspberry/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().isEmpty()) {
            httpClient.interceptors().clear();
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceWithAuth(Class<S> serviceClass, final Context context) {
        if (UserSession.hasToken()) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(UserSession.getToken(), context);

            if (httpClient.interceptors().isEmpty()) {
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        } else {
            if (!httpClient.interceptors().isEmpty()) {
                httpClient.interceptors().clear();
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
