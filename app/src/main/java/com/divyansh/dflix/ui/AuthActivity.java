package com.divyansh.dflix.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.divyansh.dflix.R;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}
