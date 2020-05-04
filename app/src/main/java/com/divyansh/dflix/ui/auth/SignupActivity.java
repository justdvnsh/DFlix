package com.divyansh.dflix.ui.auth;

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
import com.divyansh.dflix.ui.main.MainActivity;
import com.divyansh.dflix.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

public class SignupActivity extends BaseActivity {

    private static final String TAG = "SignupActivity";
    private SignupViewModel viewModel;
    private EditText email, password;
    private Button signupBtn;
    private TextView signupTextView;

    @Inject
    public ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (mAuthInstance.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        viewModel = new ViewModelProvider(this, providerFactory).get(SignupViewModel.class);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signupBtn = findViewById(R.id.signup);
        signupTextView = findViewById(R.id.signupTextView);
        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeObservers();
            }
        });
    }


    private void subscribeObservers() {
        if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(this, "Fill the values", Toast.LENGTH_SHORT).show();
            return ;
        }
        viewModel.signup(email.getText().toString(), password.getText().toString()).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d(TAG, "onChanged: subscribeObservers " + user.getEmail());
            }
        });
    }
}
