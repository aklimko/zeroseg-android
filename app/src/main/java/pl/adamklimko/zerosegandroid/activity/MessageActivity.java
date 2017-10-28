package pl.adamklimko.zerosegandroid.activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import pl.adamklimko.zerosegandroid.R;
import pl.adamklimko.zerosegandroid.rest.UserSession;
import pl.adamklimko.zerosegandroid.model.Message;
import pl.adamklimko.zerosegandroid.rest.ApiClient;
import pl.adamklimko.zerosegandroid.rest.ZerosegService;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class MessageActivity extends AppCompatActivity {

    private MessageTask mMessageTask;

    private EditText mMessageView;
    private Button mSendButton;

    private ZerosegService zerosegService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // TODO: make singleton class for zerosegService
        zerosegService = ApiClient.createService(ZerosegService.class, UserSession.getToken(), this);

        mMessageView = (EditText) findViewById(R.id.message);
        mSendButton = (Button) findViewById(R.id.message_send_button);

        mSendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        if (mMessageTask != null) {
            return;
        }
        mMessageView.setError(null);
        String text = mMessageView.getText().toString();

        if (TextUtils.isEmpty(text)) {
            mMessageView.setError("Empty message");
            return;
        }

        Message message = new Message(text);
        mMessageTask = new MessageTask(message);
        mMessageTask.execute((Void) null);
    }

    public class MessageTask extends AsyncTask<Void, Void, Boolean> {

        private final Message message;

        MessageTask(Message message) {
            this.message = message;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            final Call<Message> messageCall = zerosegService.postMessage(message);
            final Response<Message> response;
            try {
                response = messageCall.execute();
            } catch (IOException e) {
                return false;
            }
            if (!response.isSuccessful()) {
                return false;
            }
            if (response.code() == 200) {
                Log.i("LOGIN", "Successful login");
                return true;
            } else if (response.code() == 403 || response.code() == 401) {
                Log.e("LOGIN", "Token expired");
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mMessageTask = null;
//            showProgress(false);

            if (success) {
                showMessageSentDialog();
//                mPasswordView.setError("Successful login");
//                mPasswordView.requestFocus();
            } else {
                mMessageView.setError("Cannot send message");
            }
        }

        private void showMessageSentDialog() {
            AlertDialog alertDialog = new AlertDialog.Builder(MessageActivity.this).create();
            alertDialog.setTitle("Success");
            alertDialog.setMessage("Message has been sent");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mMessageView.setText("");
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        @Override
        protected void onCancelled() {
            mMessageTask = null;
//            showProgress(false);
        }
    }
}
