package io.privado.privadohealthapp.viewmodels;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.amplitude.android.Amplitude;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.HashMap;

import io.privado.privadohealthapp.models.PersonalyIndentifiableInformation;
import io.privado.privadohealthapp.utils.LoggingUtils;
import io.privado.privadohealthapp.utils.PIIUtils;

public class LoginViewModel extends ViewModel {

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
        LoggingUtils.logFacebookEvent(context, LoggingUtils.EVENT_LOGIN, parameters);
    }

    public void logPIIGoogleAnalyitcs(Context context, String email, PersonalyIndentifiableInformation pii) {
        Bundle parameters = new Bundle();
        LoggingUtils.addPiiToBundle(parameters, pii);
        LoggingUtils.addEmailToBundle(parameters, email);
        LoggingUtils.logFirebaseEvent(context, LoggingUtils.EVENT_LOGIN, parameters);
    }

    public void logPIIAmplitude(Amplitude amplitude, String email, PersonalyIndentifiableInformation pii) {
        HashMap<String, String> properties = new HashMap<>();
        LoggingUtils.addPiiToProperties(properties, pii);
        LoggingUtils.addEmailToProperties(properties, email);
        LoggingUtils.logAmplitudeEvent(amplitude, LoggingUtils.EVENT_LOGIN, properties);
    }

    public void logPIIMixpanel(MixpanelAPI mixpanel, String email, PersonalyIndentifiableInformation pii) {
        HashMap<String, String> properties = new HashMap<>();
        LoggingUtils.addPiiToProperties(properties, pii);
        LoggingUtils.addEmailToProperties(properties, email);
        LoggingUtils.logMixpanelEvent(mixpanel, LoggingUtils.EVENT_LOGIN, properties);
    }
}
