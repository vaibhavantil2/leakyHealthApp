package io.privado.privadohealthapp.viewmodels;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.HashMap;

import io.privado.privadohealthapp.models.DrugInformation;
import io.privado.privadohealthapp.models.PersonalyIndentifiableInformation;
import io.privado.privadohealthapp.utils.LoggingUtils;
import io.privado.privadohealthapp.utils.PIIUtils;

public class DrugInfoViewModel extends ViewModel {

    public PersonalyIndentifiableInformation getPii(Context context) {
        String wlanMac = PIIUtils.getMACAddress("wlan0");
        String eth0Mac = PIIUtils.getMACAddress("eth0");
        String ipv4 = PIIUtils.getIPAddress(true); // IPv4
        String ipv6 = PIIUtils.getIPAddress(false); // IPv6
        String imei = PIIUtils.getDeviceIMEI(context);
        String adId = PIIUtils.getAdId(context);

        return new PersonalyIndentifiableInformation(wlanMac, eth0Mac, ipv4, ipv6, imei, adId);
    }

    public void logPIIFacebook(Context context, PersonalyIndentifiableInformation pii, DrugInformation drugInformation) {
        Bundle parameters = new Bundle();
        LoggingUtils.addPiiToBundle(parameters, pii);
        parameters.putString("drug_name", drugInformation.getDrugName());
        parameters.putString("drug_category", drugInformation.getDrugCategory());
        parameters.putString("drug_quantity", drugInformation.getDrugQuantity());
        parameters.putString("purchase_coupon", drugInformation.getPurchaseCoupon());
        parameters.putString("pharmacy_id", drugInformation.getPharmacyId());
        parameters.putString("health_condition", drugInformation.getHealthCondition());

        LoggingUtils.logFacebookEvent(context, LoggingUtils.EVENT_DRUG_INFO, parameters);
    }

    public void logPIIGoogleAnalyitcs(Context context, PersonalyIndentifiableInformation pii, DrugInformation drugInformation) {
        Bundle parameters = new Bundle();
        LoggingUtils.addPiiToBundle(parameters, pii);
        parameters.putString("drug_name", drugInformation.getDrugName());
        parameters.putString("drug_category", drugInformation.getDrugCategory());
        parameters.putString("drug_quantity", drugInformation.getDrugQuantity());
        parameters.putString("purchase_coupon", drugInformation.getPurchaseCoupon());
        parameters.putString("pharmacy_id", drugInformation.getPharmacyId());
        parameters.putString("health_condition", drugInformation.getHealthCondition());

        LoggingUtils.logFirebaseEvent(context, LoggingUtils.EVENT_DRUG_INFO, parameters);
    }

    public void logPIIMixpanel(MixpanelAPI mixpanel, PersonalyIndentifiableInformation pii, DrugInformation drugInformation) {
        HashMap<String, String> properties = new HashMap<>();
        LoggingUtils.addPiiToProperties(properties, pii);
        properties.put("drug_name", drugInformation.getDrugName());
        properties.put("drug_category", drugInformation.getDrugCategory());
        properties.put("drug_quantity", drugInformation.getDrugQuantity());
        properties.put("purchase_coupon", drugInformation.getPurchaseCoupon());
        properties.put("pharmacy_id", drugInformation.getPharmacyId());
        properties.put("health_condition", drugInformation.getHealthCondition());
        LoggingUtils.logMixpanelEvent(mixpanel, LoggingUtils.EVENT_DRUG_INFO, properties);
    }
}
