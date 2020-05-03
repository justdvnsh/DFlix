package com.divyansh.dflix.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.divyansh.dflix.BaseActivity;
import com.divyansh.dflix.R;
import com.divyansh.dflix.models.User;
import com.divyansh.dflix.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private LoginViewModel viewModel;
    private EditText email_login, password_login;
    private Button loginBtn;
    private TextView loginTextView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        loginBtn = findViewById(R.id.login);
        loginTextView = findViewById(R.id.loginTextView);

        viewModel = new ViewModelProvider(this, providerFactory).get(LoginViewModel.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeObservers();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        Log.d(TAG, "onCreate: Firebase User " + mAuthInstance.getCurrentUser().getEmail());
    }

    private void subscribeObservers() {
        if (TextUtils.isEmpty(email_login.getText().toString()) || TextUtils.isEmpty(password_login.getText().toString())) {
            Toast.makeText(this, "Fill the values", Toast.LENGTH_SHORT).show();
            return ;
        }

        viewModel.login(email_login.getText().toString(), password_login.getText().toString()).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d(TAG, "onChanged: subscribeObserversLogin " + user.getEmail() );
                Log.d(TAG, "onChanged: current USer " + mAuthInstance.getCurrentUser().getEmail());
            }
        });
    }
}
