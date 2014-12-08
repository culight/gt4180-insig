package edu.gatech.gt4823.presentation.authentication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.gatech.gt4823.R;
import edu.gatech.gt4823.data.authentication.Authenticate;
import edu.gatech.gt4823.domain.authentication.User;
import edu.gatech.gt4823.presentation.splash_screen.SplashScreenLayoutActivity;

/**
 * Created by Demerrick on 11/8/2014.
 */
public class AuthenticationActivity extends Activity {
    private final String TAG =
            "edu.gatech.gt4823:AuthenticationActivity.java";
    public Dialog loginDialog;
    public Context context, dialogContext;
    public TextView output;
    public ProgressBar progress;
    public boolean authenticated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_card);

        output = (TextView) findViewById(R.id.splashText);
        context = this.getBaseContext();

        if (User.isValid()) {
            Intent intent = new Intent(context, SplashScreenLayoutActivity.class);
            startActivity(intent);
            finish();
        }

        progress = (ProgressBar) findViewById(R.id.splashProgressBar);
        progress.setVisibility(View.INVISIBLE);

        loginDialog = createLoginDialog();
        //loginDialog.show();

        authenticated = false;

        output.setText("Initializing File System");
        //Initialize.initFileSystem(context);
    }

    /**
     * Dialog box for login
     *
     * @return Dialog
     */
    public Dialog createLoginDialog() {
        Dialog dialog = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflater.inflate(R.layout.authentication_card,
                null));
        dialog.setCancelable(false);
        return dialog;
    }

    public void loginOnClick(View v) {
        loginDialog.hide();
        output.setText("Authenticating");
        progress.setVisibility(View.VISIBLE);
        EditText un = (EditText) loginDialog.findViewById(R.id.userName);
        String username = un.getText().toString();
        EditText pw = (EditText) loginDialog.findViewById(R.id.userPassword);
        String password = pw.getText().toString();

        dialogContext = v.getContext();
        Authenticate auth = new Authenticate(this);
        auth.execute(new String[] { username, password });

        if(auth.isAuthenticated()) {
            Intent intent = new Intent(context, SplashScreenLayoutActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
