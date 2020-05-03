package com.divyansh.dflix.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";
    private FirebaseAuth firebaseAuth;
    
    @Inject
    public AuthViewModel(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
        Log.d(TAG, "AuthViewModel: AuthViewModel is working");
        Log.d(TAG, "AuthViewModel: Firebase: " + firebaseAuth.toString());
    }
}
