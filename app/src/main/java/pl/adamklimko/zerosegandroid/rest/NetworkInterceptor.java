package pl.adamklimko.zerosegandroid.rest;

import android.content.Context;
import okhttp3.Interceptor;
import okhttp3.Response;
import pl.adamklimko.zerosegandroid.exception.NoNetworkConnectedException;
import pl.adamklimko.zerosegandroid.util.NetworkUtil;

import java.io.IOException;

public class NetworkInterceptor implements Interceptor {

    private final Context mContext;

    NetworkInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetworkUtil.isOnline(mContext)) {
            throw new NoNetworkConnectedException();
        }
        return chain.proceed(chain.request());
    }
}
