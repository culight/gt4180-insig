package edu.gatech.gt4823.data.authentication;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.gatech.gt4823.R;
import edu.gatech.gt4823.data.utilities.MongoDBClient;
import edu.gatech.gt4823.domain.authentication.User;
import edu.gatech.gt4823.presentation.authentication.AuthenticationActivity;

/**
 * Created by Demerrick on 11/8/2014.
 */
public class Authenticate extends AsyncTask<String, Void, Boolean> {
    private Dialog loginDialog;
    private Context context, dialogContext;
    private TextView output;
    private ProgressBar progress;
    private boolean authenticated;

    public Authenticate(AuthenticationActivity authActivity) {
        setLoginDialog(authActivity);
        setContext(authActivity);
        setProgress(authActivity);
        setOutput(authActivity);
    }

    public void setAuthenticatedFlag(AuthenticationActivity authActivity) {
        this.authenticated = authActivity.authenticated;
    }
    public void setLoginDialog(AuthenticationActivity authActivity) {
        this.loginDialog = authActivity.loginDialog;
    }

    public void setContext(AuthenticationActivity authActivity) {
        this.context = authActivity.context;
        this.dialogContext = authActivity.dialogContext;
    }

    public void setProgress(AuthenticationActivity authActivity) {
        this.progress = authActivity.progress;
    }

    public void setOutput(AuthenticationActivity authActivity) {
        this.output = authActivity.output;
    }
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        if (params.length != 2)
            return false;
        String username = params[0];
        String password = params[1];
        return User.checkLoginCredentials(username, password);
    }

    @Override
    protected void onPostExecute(Boolean auth) {
        if (auth) {
            loginDialog.dismiss();
            output.setText("Authentication Complete!");
            authenticated = true;
        } else {
            progress.setVisibility(View.INVISIBLE);
            output.setText("Authentication Failed.");
            EditText pw = (EditText) loginDialog
                    .findViewById(R.id.userPassword);
            pw.setText("");
            loginDialog.show();
            String err = MongoDBClient.getErrorFlag();
            if (err == null)
                Toast.makeText(dialogContext, "Login Failed!",
                        Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(dialogContext, err, Toast.LENGTH_SHORT)
                        .show();
        }
    }

}
