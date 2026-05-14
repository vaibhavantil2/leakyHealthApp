package io.privado.privadohealthapp.viewmodels;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.HashMap;

import io.privado.privadohealthapp.models.PersonalyIndentifiableInformation;
import io.privado.privadohealthapp.models.PharmacyInformation;
import io.privado.privadohealthapp.utils.LoggingUtils;
import io.privado.privadohealthapp.utils.PIIUtils;

public class PharmacyInfoViewModel extends ViewModel {

    public PersonalyIndentifiableInformation getPii(Context context) {
        String wlanMac = PIIUtils.getMACAddress("wlan0");
        String eth0Mac = PIIUtils.getMACAddress("eth0");
        String ipv4 = PIIUtils.getIPAddress(true); // IPv4
        String ipv6 = PIIUtils.getIPAddress(false); // IPv6
        String imei = PIIUtils.getDeviceIMEI(context);
        String adId = PIIUtils.getAdId(context);

        return new PersonalyIndentifiableInformation(wlanMac, eth0Mac, ipv4, ipv6, imei, adId);
    }

    public void logPIIFacebook(Context context, PersonalyIndentifiableInformation pii, PharmacyInformation pharmacyInformation) {
        Bundle parameters = new Bundle();
        LoggingUtils.addPiiToBundle(parameters, pii);
        parameters.putString("drug", pharmacyInformation.getDrug());
        parameters.putString("form", pharmacyInformation.getForm());
        parameters.putString("pharmacy_location", pharmacyInformation.getPharmacyLocation());
        parameters.putString("purchase_coupon_taken", pharmacyInformation.getIsCouponTaken());
        LoggingUtils.logFacebookEvent(context, LoggingUtils.EVENT_PHARMACY_INFO, parameters);
    }

    public void logPIIGoogleAnalyitcs(Context context, PersonalyIndentifiableInformation pii, PharmacyInformation pharmacyInformation) {
        Bundle parameters = new Bundle();
        LoggingUtils.addPiiToBundle(parameters, pii);
        parameters.putString("drug", pharmacyInformation.getDrug());
        parameters.putString("form", pharmacyInformation.getForm());
        parameters.putString("pharmacy_location", pharmacyInformation.getPharmacyLocation());
        parameters.putString("purchase_coupon_taken", pharmacyInformation.getIsCouponTaken());
        LoggingUtils.logFirebaseEvent(context, LoggingUtils.EVENT_PHARMACY_INFO, parameters);
    }

    public void logPIIMixpanel(MixpanelAPI mixpanel, PersonalyIndentifiableInformation pii, PharmacyInformation pharmacyInformation) {
        HashMap<String, String> properties = new HashMap<>();
        LoggingUtils.addPiiToProperties(properties, pii);
        properties.put("drug", pharmacyInformation.getDrug());
        properties.put("form", pharmacyInformation.getForm());
        properties.put("pharmacy_location", pharmacyInformation.getPharmacyLocation());
        properties.put("purchase_coupon_taken", pharmacyInformation.getIsCouponTaken());
        LoggingUtils.logMixpanelEvent(mixpanel, LoggingUtils.EVENT_PHARMACY_INFO, properties);
    }
}
