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
import io.privado.privadohealthapp.models.DrugInformation;
import io.privado.privadohealthapp.models.PersonalInformation;
import io.privado.privadohealthapp.models.PersonalyIndentifiableInformation;
import io.privado.privadohealthapp.utils.PIIUtils;
import io.privado.privadohealthapp.viewmodels.DrugInfoViewModel;
import io.privado.privadohealthapp.viewmodels.PersonalInfoViewModel;

public class DrugInfoActivity extends AppCompatActivity {

    EditText drugName, drugQuantity, drugCategory, purchaseCoupon, pharmacyId, healthCondition;
    private DrugInfoViewModel drugInfoViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_info);
        drugName = (EditText) findViewById(R.id.editTextDrugName);
        drugQuantity = (EditText) findViewById(R.id.editTextDrugQuantity);
        drugCategory = (EditText) findViewById(R.id.editTextDrugCategory);
        purchaseCoupon = (EditText) findViewById(R.id.editTextPurchaseCoupon);
        pharmacyId = (EditText) findViewById(R.id.editTextPharmacyID);
        healthCondition = (EditText) findViewById(R.id.editTextHealthCondition);

        drugInfoViewModel = ViewModelProviders.of(this).get(DrugInfoViewModel.class);


    }
    public void onClickSubmit(View view){
        new Thread(() -> {
            logEvents();
            Intent intent = new Intent(DrugInfoActivity.this, PharmacyInfoActivity.class);
            startActivity(intent);

        }).start();
    }
    private void logEvents(){
        PersonalyIndentifiableInformation personalyIndentifiableInformation = drugInfoViewModel.getPii(DrugInfoActivity.this);
        DrugInformation drugInformation = new DrugInformation(drugName.getText().toString(), drugQuantity.getText().toString(), drugCategory.getText().toString(), purchaseCoupon.getText().toString(), pharmacyId.getText().toString(), healthCondition.getText().toString());

        drugInfoViewModel.logPIIFacebook(DrugInfoActivity.this, personalyIndentifiableInformation, drugInformation);
        drugInfoViewModel.logPIIGoogleAnalyitcs(DrugInfoActivity.this, personalyIndentifiableInformation, drugInformation);
    }
}