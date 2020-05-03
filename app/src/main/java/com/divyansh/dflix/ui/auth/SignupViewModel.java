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
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;

public class SignupViewModel extends ViewModel {

    private static final String TAG = "SignupViewModel";
    private FirebaseAuth firebaseAuth;

    @Inject
    public SignupViewModel(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
        Log.d(TAG, "SignupViewModel: SignupViewModel is working");
        Log.d(TAG, "SignupViewModel: Firebase: " + firebaseAuth.toString());
    }

    public MutableLiveData<User> signup(String email, String password) {
        final MutableLiveData<User> user = new MutableLiveData<>();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String uid = task.getResult().getUser().getUid();
                    String email = task.getResult().getUser().getEmail();
                    User firebaseUser = new User(uid, email);
                    user.setValue(firebaseUser);
                    Log.d(TAG, "onComplete: authviewmodel, usersignedup" );
                } else {
                    Log.d(TAG, "onComplete: " + task.getException());
                }
            }
        });

        return user;
    };
}
