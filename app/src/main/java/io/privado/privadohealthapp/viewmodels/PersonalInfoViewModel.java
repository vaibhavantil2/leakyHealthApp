package io.privado.privadohealthapp.viewmodels;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.privado.privadohealthapp.models.PersonalInformation;
import io.privado.privadohealthapp.models.PersonalyIndentifiableInformation;
import io.privado.privadohealthapp.utils.HashUtils;
import io.privado.privadohealthapp.utils.PIIUtils;
import io.privado.privadohealthapp.views.PersonalInfoActivity;

public class PersonalInfoViewModel extends ViewModel {

    public PersonalyIndentifiableInformation getPii(Context context){

        String wlanMac = PIIUtils.getMACAddress("wlan0");
        String eth0Mac = PIIUtils.getMACAddress("eth0");
        String ipv4 = PIIUtils.getIPAddress(true); // IPv4
        String ipv6 = PIIUtils.getIPAddress(false); // IPv6
        String imei = PIIUtils.getDeviceIMEI(context);
        String adId = PIIUtils.getAdId(context);

        return new PersonalyIndentifiableInformation(wlanMac, eth0Mac, ipv4, ipv6, imei, adId);
    }
    public void logPIIFacebook(Context context, PersonalyIndentifiableInformation pii, PersonalInformation personalInformation) {

        Bundle parameters = new Bundle();
        parameters.putString("device_id", pii.getImei());
        parameters.putString("wlan_mac", pii.getWlanMac());
        parameters.putString("eth0_mac", pii.getEthernetMac());
        parameters.putString("ipv4", pii.getIpAddressv4());
        parameters.putString("ipv6", pii.getIpAddressv6());
        parameters.putString("advertising_id", pii.getAdId());
        parameters.putString("is_therapy_taken", personalInformation.getTherapyTaken());
        parameters.putString("gender", personalInformation.getGender());
        parameters.putString("financial_status", personalInformation.getFinancialStatus());

        AppEventsLogger logger = AppEventsLogger.newLogger(context);
        logger.logEvent("personal_info", parameters);
    }
    public void logPIIGoogleAnalyitcs(PersonalyIndentifiableInformation pii, PersonalInformation personalInformation) {

        Bundle parameters = new Bundle();
        parameters.putString("device_id", pii.getImei());
        parameters.putString("wlan_mac", pii.getWlanMac());
        parameters.putString("eth0_mac", pii.getEthernetMac());
        parameters.putString("ipv4", pii.getIpAddressv4());
        parameters.putString("ipv6", pii.getIpAddressv6());
        parameters.putString("advertising_id", pii.getAdId());
        parameters.putString("is_therapy_taken", personalInformation.getTherapyTaken());
        parameters.putString("gender", personalInformation.getGender());
        parameters.putString("financial_status", personalInformation.getFinancialStatus());

        FirebaseAnalytics.getInstance(getApplicationContext()).logEvent("personal_info", parameters);
    }
}
