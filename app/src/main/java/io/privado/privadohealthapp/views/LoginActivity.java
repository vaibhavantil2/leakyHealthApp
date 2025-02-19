package io.privado.privadohealthapp.views;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyZone;
import com.adcolony.sdk.AdColonyAd;
import com.adcolony.sdk.AdColonyAdAvailabilityListener;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAdRequestError;
import com.adcolony.sdk.AdColonyAdViewListener;
import com.adcolony.sdk.AdColonyAdView;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyRewardListener;

import com.amplitude.android.Amplitude;
import com.amplitude.android.Configuration;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import io.privado.privadohealthapp.R;
import io.privado.privadohealthapp.models.PersonalyIndentifiableInformation;
import io.privado.privadohealthapp.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private static final String EMAIL = "email";
    private EditText email;
    private Amplitude amplitude;
    private MixpanelAPI mixpanel;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        amplitude = new Amplitude(new Configuration(getString(R.string.amplitude_api_key), getApplicationContext() ));
        mixpanel = MixpanelAPI.getInstance(this, getString(R.string.mixpanel_token), true);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        email = (EditText) findViewById(R.id.editTextTextEmailAddress);

        // Initialize AdColony
        AdColonyAppOptions appOptions = new AdColonyAppOptions()
            .setUserID("unique_user_id");
        AdColony.configure(this, appOptions, ADCOLONY_APP_ID, ADCOLONY_ZONE_ID);
        
        // Initialize Appsflyer
        AppsFlyerLib.getInstance().init(getString(R.string.appsflyer_dev_key), new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                // Handle conversion data success
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                // Handle conversion data failure
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                // Handle app open attribution
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                // Handle attribution failure
            }
        }, getApplicationContext());
        AppsFlyerLib.getInstance().start(this);
        

    }
    public void onClickLogin(View view){
        new Thread(() -> {
            logEvents();
            Intent intent = new Intent(LoginActivity.this, PersonalInfoActivity.class);
            startActivity(intent);

        }).start();
    }
    public void logEvents(){
        PersonalyIndentifiableInformation pii = loginViewModel.getPii(LoginActivity.this);
        String emailAddress = email.getText().toString();
        loginViewModel.logPIIFacebook(LoginActivity.this, emailAddress, pii);
        loginViewModel.logPIIGoogleAnalyitcs(emailAddress, pii);
        loginViewModel.logPIIAmplitude(amplitude, emailAddress, pii);
        loginViewModel.logPIIMixpanel(mixpanel, emailAddress, pii);
    }
    public void onClickSignup(View view){
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Branch.getInstance().initSession((referringParams, error) -> {
            if (error == null) {
                // Branch initialization successful, handle deep link data
            } else {
                // Error handling for Branch SDK initialization
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        Branch.getInstance().reInitSession(this, (referringParams, error) -> {
            if (error == null) {
                // Handle deep link data (if any) for the new intent
            }
        });
    }



}
