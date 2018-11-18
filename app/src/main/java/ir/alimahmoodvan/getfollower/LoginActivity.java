package ir.alimahmoodvan.getfollower;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import I4A.Auth;
import I4A.DataSaver;
import I4A.Helper;

public class LoginActivity extends AppCompatActivity {
    String TAG="LoginActivityTAG";
    TextInputLayout userTextLayout;
    TextInputLayout passTextLayout;
    AppCompatEditText userText;
    AppCompatEditText passText;
    AppCompatButton loginButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(DataSaver.getString(this,"username").isEmpty()) {
            setContentView(R.layout.activity_login);
            userTextLayout = findViewById(R.id.username_txt_lyt);
            passTextLayout = findViewById(R.id.password_txt_lyt);
            userText = findViewById(R.id.username_txt);
            passText = findViewById(R.id.password_txt);
            loginButton = findViewById(R.id.login_btn);
            progressBar = findViewById(R.id.pbar);
            final View view=findViewById(R.id.activity_login);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Helper.getPermission(LoginActivity.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    progressBar.setVisibility(View.VISIBLE);
                    String username = userText.getText().toString();
                    String password = passText.getText().toString();
                    Auth auth = new Auth(getApplicationContext());
                    try {
                        auth.login( username, password);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception ex) {
                        final Snackbar snakbar = Snackbar.make(view,ex.getMessage() , Snackbar.LENGTH_INDEFINITE);
                        snakbar.setDuration(5000);
                        snakbar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                snakbar.dismiss();
                            }
                        });
                        snakbar.show();
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
        else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
