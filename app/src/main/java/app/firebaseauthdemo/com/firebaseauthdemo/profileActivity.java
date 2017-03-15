package app.firebaseauthdemo.com.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profileActivity extends AppCompatActivity {

    private Button logoutButton;
    private TextView textViewUserEmail;
    private EditText fullNameText;
    private EditText addressText;
    private Button saveInfoButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        textViewUserEmail = (TextView) findViewById(R.id.userEmailText);
        addressText = (EditText) findViewById(R.id.addressText);
        fullNameText = (EditText) findViewById(R.id.fullnameText);
        saveInfoButton = (Button) findViewById(R.id.saveButton);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(profileActivity.this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        textViewUserEmail.setText("Welcome " + firebaseUser.getEmail());

        saveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(profileActivity.this, LoginActivity.class));
            }
        });
    }

    private void saveUserInformation(){
        String name = fullNameText.getText().toString().trim();
        String address = addressText.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name , address);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(getApplicationContext(), "Information Added Successfully!", Toast.LENGTH_LONG).show();
    }
}
