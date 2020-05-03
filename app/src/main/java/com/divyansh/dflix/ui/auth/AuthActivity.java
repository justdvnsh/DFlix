package com.divyansh.dflix.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.divyansh.dflix.BaseActivity;
import com.divyansh.dflix.R;
import com.divyansh.dflix.models.User;
import com.divyansh.dflix.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends BaseActivity {

    private static final String TAG = "AuthActivity";
    private AuthViewModel viewModel;
    private EditText email, password, email_login, password_login;
    private Button signupBtn, loginBtn;

    @Inject
    public ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        viewModel = new ViewModelProvider(this, providerFactory).get(AuthViewModel.class);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signupBtn = findViewById(R.id.signup);

        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        loginBtn = findViewById(R.id.login);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeObservers();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeObserversLogin();
            }
        });
    }

    private void subscribeObserversLogin() {
        if (TextUtils.isEmpty(email_login.getText().toString()) || TextUtils.isEmpty(password_login.getText().toString())) {
            Toast.makeText(this, "Fill the values", Toast.LENGTH_SHORT).show();
            return ;
        }

        viewModel.login(email_login.getText().toString(), password_login.getText().toString()).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d(TAG, "onChanged: subscribeObserversLogin " + user.getEmail() );
            }
        });
    }

    private void subscribeObservers() {
        viewModel.signup(email.getText().toString(), password.getText().toString()).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d(TAG, "onChanged: subscribeObservers " + user.getEmail());
            }
        });
    }
}
