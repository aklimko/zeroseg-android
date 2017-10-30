package pl.adamklimko.zerosegandroid.exception;

import java.io.IOException;

public class NoNetworkConnectedException extends IOException {
    public NoNetworkConnectedException() {
        super();
    }

    public NoNetworkConnectedException(String message) {
        super(message);
    }
}
