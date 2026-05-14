package io.privado.privadohealthapp.viewmodels;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import io.privado.privadohealthapp.models.PersonalyIndentifiableInformation;
import io.privado.privadohealthapp.utils.HashUtils;
import io.privado.privadohealthapp.utils.LoggingUtils;
import io.privado.privadohealthapp.utils.PIIUtils;

public class SignupViewModel extends ViewModel {

    public PersonalyIndentifiableInformation getPii(Context context) {
        String wlanMac = PIIUtils.getMACAddress("wlan0");
        String eth0Mac = PIIUtils.getMACAddress("eth0");
        String ipv4 = PIIUtils.getIPAddress(true); // IPv4
        String ipv6 = PIIUtils.getIPAddress(false); // IPv6
        String imei = PIIUtils.getDeviceIMEI(context);
        String adId = PIIUtils.getAdId(context);

        return new PersonalyIndentifiableInformation(wlanMac, eth0Mac, ipv4, ipv6, imei, adId);
    }

    public void logPIIFacebook(Context context, String email, PersonalyIndentifiableInformation pii) {
        Bundle parameters = new Bundle();
        LoggingUtils.addPiiToBundle(parameters, pii);
        LoggingUtils.addEmailToBundle(parameters, email);
        parameters.putString("is_signed_up", "yes");
        LoggingUtils.logFacebookEvent(context, LoggingUtils.EVENT_SIGNUP, parameters);
    }

    public void logPIIGoogleAnalyitcs(Context context, String email, PersonalyIndentifiableInformation pii) {
        Bundle parameters = new Bundle();
        LoggingUtils.addPiiToBundle(parameters, pii);
        LoggingUtils.addEmailToBundle(parameters, email);
        parameters.putString("is_signed_up", "yes");
        LoggingUtils.logFirebaseEvent(context, LoggingUtils.EVENT_SIGNUP, parameters);
    }
}
