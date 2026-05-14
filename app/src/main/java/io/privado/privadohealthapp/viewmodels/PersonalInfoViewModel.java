package io.privado.privadohealthapp.viewmodels;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import io.privado.privadohealthapp.models.PersonalInformation;
import io.privado.privadohealthapp.models.PersonalyIndentifiableInformation;
import io.privado.privadohealthapp.utils.LoggingUtils;
import io.privado.privadohealthapp.utils.PIIUtils;

public class PersonalInfoViewModel extends ViewModel {

    public PersonalyIndentifiableInformation getPii(Context context) {
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
        LoggingUtils.addPiiToBundle(parameters, pii);
        parameters.putString("is_therapy_taken", personalInformation.getTherapyTaken());
        parameters.putString("gender", personalInformation.getGender());
        parameters.putString("financial_status", personalInformation.getFinancialStatus());
        LoggingUtils.logFacebookEvent(context, LoggingUtils.EVENT_PERSONAL_INFO, parameters);
    }

    public void logPIIGoogleAnalyitcs(Context context, PersonalyIndentifiableInformation pii, PersonalInformation personalInformation) {
        Bundle parameters = new Bundle();
        LoggingUtils.addPiiToBundle(parameters, pii);
        parameters.putString("is_therapy_taken", personalInformation.getTherapyTaken());
        parameters.putString("gender", personalInformation.getGender());
        parameters.putString("financial_status", personalInformation.getFinancialStatus());
        LoggingUtils.logFirebaseEvent(context, LoggingUtils.EVENT_PERSONAL_INFO, parameters);
    }
}
