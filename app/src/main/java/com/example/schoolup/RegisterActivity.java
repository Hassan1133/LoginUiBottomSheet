package com.example.schoolup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameEt, emailEt, passEt;
    Button signupBtn;

    String name, email, pass;

    //Firebase
    FirebaseAuth auth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Widget initialization
        init();

        //Firebase initialization
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("students");

    }

    void init() {
        //Initialization
        nameEt = findViewById(R.id.name);
        emailEt = findViewById(R.id.email);
        passEt = findViewById(R.id.pass);
        signupBtn = findViewById(R.id.signupBtn);

        //OnClickListener
        signupBtn.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signupBtn:

                name = nameEt.getText().toString();
                email = emailEt.getText().toString();
                pass = passEt.getText().toString();

                User u = new User(name, email, pass);

                if (isValid(u)) {
                    createAccount(u);
                } else {
                    Toast.makeText(this, "Enter valid data!", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }

    private boolean isValid(User user) {
        boolean valid = true;
        if (user.getName().length() < 3) {
            nameEt.setError("Enter valid name");
            valid = false;
        }
        Toast.makeText(this, "" + Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches(), Toast.LENGTH_SHORT).show();
        if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            emailEt.setError("Enter valid email");
            valid = false;
        }

        if (user.getPass().length() < 6) {
            passEt.setError("Enter valid password");
            valid = false;
        }
        return valid;
    }

    void createAccount(User u) {
        auth
                .createUserWithEmailAndPassword(u.getEmail(), u.getPass())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                            u.setUserId(auth.getUid());
                            addToDB(u);
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addToDB(User u)
    {
        ref
                .child(u.getUserId())
                .setValue(u)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                            nameEt.setText("");
                            emailEt.setText("");
                            passEt.setText("");
                            goToHomeScreen();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToHomeScreen() {
        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
        finish();
    }

}