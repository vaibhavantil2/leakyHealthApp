package io.privado.privadohealthapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.privado.privadohealthapp.R;
import io.privado.privadohealthapp.models.PersonalyIndentifiableInformation;
import io.privado.privadohealthapp.utils.HashUtils;
import io.privado.privadohealthapp.utils.PIIUtils;
import io.privado.privadohealthapp.viewmodels.LoginViewModel;
import io.privado.privadohealthapp.viewmodels.SignupViewModel;

public class SignupActivity extends AppCompatActivity {

    EditText email, password, repassword;
    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        repassword = (EditText) findViewById(R.id.editTextTextRePassword);
        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel.class);



    }
    public void onClickSignup(View view){
        new Thread(() -> {
            logEvents();
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);

        }).start();
    }
    public void logEvents(){
        PersonalyIndentifiableInformation pii = signupViewModel.getPii(SignupActivity.this);
        signupViewModel.logPIIFacebook(SignupActivity.this, email.getText().toString(), pii);
        signupViewModel.logPIIGoogleAnalyitcs(SignupActivity.this, email.getText().toString(), pii);
    }
}