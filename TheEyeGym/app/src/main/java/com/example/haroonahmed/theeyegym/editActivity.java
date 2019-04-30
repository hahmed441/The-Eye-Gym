package com.example.haroonahmed.theeyegym;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;



public class editActivity extends AppCompatActivity {

    private EditText First_Name, Last_Name, Gender, Age;
    Button save_btn;
    private ProgressBar setup_progress;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
private String user_id;
private String firstname;
    private String lastname;
    private String gender;
    private String age;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Toolbar editToolbar = findViewById(R.id.editToolbar);
        setSupportActionBar(editToolbar);
        user_id = mAuth.getCurrentUser().getUid();


        First_Name = findViewById(R.id.First_Name);
        Last_Name = findViewById(R.id.Last_Name);
        Gender = findViewById(R.id.Gender);
        Age = findViewById(R.id.Age);
        setup_progress = findViewById(R.id.setup_progress);
        save_btn = (Button) findViewById(R.id.save_btn);

        firestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {



                    if (task.getResult().exists()) {
                        String firstname = task.getResult().getString("First_Name");
                        String lastname = task.getResult().getString("Last_Name");
                        String gender = task.getResult().getString("Gender");
                        String age = task.getResult().getString("Age");

                        First_Name.setText(firstname);
                        Last_Name.setText(lastname);
                        Gender.setText(gender);
                        Age.setText(age);

                    }

                } else {


                    String error = task.getException().getMessage();
                    Toast.makeText(editActivity.this, "Error Retrieving :" + error, Toast.LENGTH_LONG).show();

                }



            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname = First_Name.getText().toString();
                String lastname = Last_Name.getText().toString();
                String gender = Gender.getText().toString();
                String age = Age.getText().toString();



                if (!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(gender) && !TextUtils.isEmpty(age)) {


                    final String user_id = mAuth.getCurrentUser().getUid();
                    setup_progress.setVisibility(View.VISIBLE);


                    Map<String, String> usermap = new HashMap<>();
                    usermap.put("First_Name", firstname);
                    usermap.put("Last_Name", lastname);
                    usermap.put("Gender", gender);
                    usermap.put("Age", age);

                    firestore.collection("Users").document(user_id).set(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(editActivity.this, "Changes Saved", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(editActivity.this, profileActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(editActivity.this, "Error :" + error, Toast.LENGTH_LONG).show();


                            }

                            setup_progress.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });
    }











    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, loginActivity.class));

                break;

            case R.id.editdetails:
                startActivity(new Intent(this, editActivity.class));

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }



}




