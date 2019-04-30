package com.example.haroonahmed.theeyegym;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class signupActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword;
ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }
private void registerUser() {
    String email = editTextEmail.getText().toString().trim();
    String password = editTextPassword.getText().toString().trim();

    if (email.isEmpty()) {
        editTextEmail.setError("Email is Required to sign up");
        editTextEmail.requestFocus();
        return;
    }

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        editTextEmail.setError("Please enter a valid Email Address");
        editTextEmail.requestFocus();
        return;
    }

    if (password.isEmpty()) {
        editTextPassword.setError("A Password is required to sign up");
        editTextPassword.requestFocus();
        return;
    }

    if (password.length() < 6) {
        editTextPassword.setError("Your Password must be a minimum of 6 characters");
        editTextPassword.requestFocus();
        return;
    }

    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                Toast.makeText(signupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(signupActivity.this, editActivity.class));
                final FirebaseUser user = mAuth.getCurrentUser();
                user.sendEmailVerification();
            } else {
                if (task.getException()instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();


                } else {

                }
                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    });

}
    @Override

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                registerUser();
                break;

            case R.id.textViewLogin:
                startActivity(new Intent(this, loginActivity.class ));
                break;


        }
    }
}
