package com.example.organizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register,forgotPassword;
    private EditText editTextEmail,editTextPassword;
    private Button signIn;

    private FirebaseAuth  mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        register =(TextView)  findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn=(Button) findViewById(R.id.logowanie);
        signIn.setOnClickListener(this);

        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.register:
                startActivity(new Intent(this,RegisterUser.class));
                break;

            case     R.id.logowanie:
                userLogin();
                break;

            case     R.id.forgotPassword:
                startActivity(new Intent(this,ForgotPassword.class));
                break;
        }

    }

    private void userLogin() {



        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length()<6){
            editTextPassword.setError("Min password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));

                    }
                    else{
                        user.sendEmailVerification();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,"Check your email to verify youre account!",Toast.LENGTH_LONG).show();

                    }

                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}