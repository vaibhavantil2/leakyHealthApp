package io.privado.privadohealthapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.appevents.AppEventsLogger;

import io.privado.privadohealthapp.R;
import io.privado.privadohealthapp.models.PersonalInformation;
import io.privado.privadohealthapp.models.PersonalyIndentifiableInformation;
import io.privado.privadohealthapp.utils.PIIUtils;
import io.privado.privadohealthapp.viewmodels.LoginViewModel;
import io.privado.privadohealthapp.viewmodels.PersonalInfoViewModel;

public class PersonalInfoActivity extends AppCompatActivity {

    EditText therapyTaken, gender, financialStatus;

    PersonalInfoViewModel personalInfoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        therapyTaken = (EditText) findViewById(R.id.editTextTherapyTaken);
        gender = (EditText) findViewById(R.id.editTextGender);
        financialStatus = (EditText) findViewById(R.id.editTextFinancialStatus);

        personalInfoViewModel = ViewModelProviders.of(this).get(PersonalInfoViewModel.class);
    }
    public void onClickSubmit(View view){
        new Thread(() -> {
            logEvents();
            Intent intent = new Intent(PersonalInfoActivity.this, DrugInfoActivity.class);
            startActivity(intent);

        }).start();
    }
    private void logEvents(){
        PersonalInfoActivity personalInfoActivity = new PersonalInfoActivity(therapyTaken.getText().toString(), gender.getText().toString(), financialStatus.getText().toString());
        PersonalyIndentifiableInformation personalyIndentifiableInformation = personalInfoViewModel.getPii(personalInfoActivity);
        PersonalInformation personalInformation = new PersonalInformation(therapyTaken.getText().toString(), gender.getText().toString(), financialStatus.getText().toString());

        personalInfoViewModel.logPIIFacebook(PersonalInfoActivity.this, personalyIndentifiableInformation, personalInformation);
        personalInfoViewModel.logPIIGoogleAnalyitcs(PersonalInfoActivity.this, personalyIndentifiableInformation, personalInformation);
    }
}
