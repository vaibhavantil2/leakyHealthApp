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
import io.privado.privadohealthapp.models.PharmacyInformation;
import io.privado.privadohealthapp.utils.PIIUtils;
import io.privado.privadohealthapp.viewmodels.PersonalInfoViewModel;
import io.privado.privadohealthapp.viewmodels.PharmacyInfoViewModel;

public class PharmacyInfoActivity extends AppCompatActivity {

    EditText drug, form, pharmacyLocation, isCouponTaken;
    private PharmacyInfoViewModel pharmacyInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_info);
        drug = (EditText) findViewById(R.id.editTextDrug);
        pharmacyLocation = (EditText) findViewById(R.id.editTextPharmacyLocation);
        form = (EditText) findViewById(R.id.editTextForm);
        isCouponTaken = (EditText) findViewById(R.id.editTextIsPurchaseCouponTaken);

        pharmacyInfoViewModel = ViewModelProviders.of(this).get(PharmacyInfoViewModel.class);
    }
    public void onClickSubmit(View view){
        new Thread(() -> {
            logEvents();

        }).start();
    }
    private void logEvents(){
        PersonalyIndentifiableInformation personalyIndentifiableInformation = pharmacyInfoViewModel.getPii(PharmacyInfoActivity.this);
        PharmacyInformation pharmacyInformation = new PharmacyInformation(drug.getText().toString(), form.getText().toString(), pharmacyLocation.getText().toString(), isCouponTaken.getText().toString());

        pharmacyInfoViewModel.logPIIFacebook(PharmacyInfoActivity.this, personalyIndentifiableInformation, pharmacyInformation);
        pharmacyInfoViewModel.logPIIGoogleAnalyitcs(PharmacyInfoActivity.this, personalyIndentifiableInformation, pharmacyInformation);
    }
}