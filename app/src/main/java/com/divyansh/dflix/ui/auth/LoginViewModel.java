package com.divyansh.dflix.ui.auth;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.divyansh.dflix.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";
    private FirebaseAuth firebaseAuth;
    
    @Inject
    public LoginViewModel(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
        Log.d(TAG, "LoginViewModel: viewmodel working");
    }


    public MutableLiveData<User> login(String email, String password) {
        final MutableLiveData<User> user = new MutableLiveData<>();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String uid = task.getResult().getUser().getUid();
                    String email = task.getResult().getUser().getEmail();
                    User firebaseUser = new User(uid, email);
                    user.setValue(firebaseUser);
                    Log.d(TAG, "onComplete: loginviewmodel, userloggedin" );
                } else {
                    Log.d(TAG, "onComplete: " + task.getException());
                }
            }
        });

        return user;
    };
}
