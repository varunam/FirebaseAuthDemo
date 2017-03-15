package app.firebaseauthdemo.com.firebaseauthdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText emailTextlogin;
    private EditText passwordTextlogin;
    private TextView textViewSignUp;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = (Button) findViewById(R.id.loginButton);
        emailTextlogin = (EditText) findViewById(R.id.emailTextlogin);
        passwordTextlogin = (EditText) findViewById(R.id.passwordTextlogin);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            Toast.makeText(getApplicationContext(),"Already you have logged in!", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(LoginActivity.this, profileActivity.class));
        }

        buttonLogin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogin)
            userLogin();
        if(v == textViewSignUp) {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    private void userLogin() {
        String email = emailTextlogin.getText().toString().trim();
        String password = passwordTextlogin.getText().toString().trim();

        if(TextUtils.isEmpty(email))
            emailTextlogin.setError("Required!");
        else if(TextUtils.isEmpty(password))
            passwordTextlogin.setError("Required!");
        else
        {
            progressDialog.setMessage("Signing in..");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                                 progressDialog.dismiss();

                                if(task.isSuccessful()){
                                    //start the profile activity
                                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(new Intent(LoginActivity.this, profileActivity.class));
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "Invalid credentials!", Toast.LENGTH_LONG).show();


                        }
                    });
        }
    }
}
