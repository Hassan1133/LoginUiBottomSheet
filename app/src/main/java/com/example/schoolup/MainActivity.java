package com.example.schoolup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolup.ui.login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button button;
    ProgressBar progressBar;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.show);
      //  progressBar = findViewById(R.id.visible);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait...");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment frag = new LoginFragment();
                frag.show(getSupportFragmentManager(),"sheet");
            }
        });

    }

    void dialog()
    {
        CharSequence options[] = {"Gallery","Camera","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select image");
        builder.setCancelable(false);

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                if(which == 0)
                {
                    Toast.makeText(MainActivity.this, options[which], Toast.LENGTH_SHORT).show();
                }
                else if(which == 1)
                {
                    Toast.makeText(MainActivity.this, options[which], Toast.LENGTH_SHORT).show();
                }
               /* switch (which)
                {
                    case 0:

                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, options[which], Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        //dialog.dismiss();
                        break;

                }*/
            }
        });

        builder.show();
    }


}