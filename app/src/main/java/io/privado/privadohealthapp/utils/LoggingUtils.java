package io.privado.privadohealthapp.utils;

import android.content.Context;
import android.os.Bundle;

import com.amplitude.android.Amplitude;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.privado.privadohealthapp.models.PersonalyIndentifiableInformation;

public class LoggingUtils {

    public static final String EVENT_LOGIN = "login";
    public static final String EVENT_SIGNUP = "signup";
    public static final String EVENT_PERSONAL_INFO = "personal_info";
    public static final String EVENT_DRUG_INFO = "drug_info";
    public static final String EVENT_PHARMACY_INFO = "pharmacy_info";

    public static void addPiiToBundle(Bundle bundle, PersonalyIndentifiableInformation pii) {
        if (pii == null || bundle == null) {
            return;
        }
        bundle.putString("device_id", pii.getImei());
        bundle.putString("wlan_mac", pii.getWlanMac());
        bundle.putString("eth0_mac", pii.getEthernetMac());
        bundle.putString("ipv4", pii.getIpAddressv4());
        bundle.putString("ipv6", pii.getIpAddressv6());
        bundle.putString("advertising_id", pii.getAdId());
    }

    public static void addPiiToProperties(HashMap<String, String> properties, PersonalyIndentifiableInformation pii) {
        if (pii == null || properties == null) {
            return;
        }
        properties.put("device_id", pii.getImei());
        properties.put("wlan_mac", pii.getWlanMac());
        properties.put("eth0_mac", pii.getEthernetMac());
        properties.put("ipv4", pii.getIpAddressv4());
        properties.put("ipv6", pii.getIpAddressv6());
        properties.put("advertising_id", pii.getAdId());
    }

    public static void addEmailToBundle(Bundle bundle, String email) {
        if (bundle == null || email == null) {
            return;
        }
        bundle.putString("user_email", HashUtils.getHash(email));
    }

    public static void addEmailToProperties(HashMap<String, String> properties, String email) {
        if (properties == null || email == null) {
            return;
        }
        properties.put("user_email", HashUtils.getHash(email));
    }

    public static void logFacebookEvent(Context context, String eventName, Bundle parameters) {
        if (context == null || eventName == null || parameters == null) {
            return;
        }
        AppEventsLogger logger = AppEventsLogger.newLogger(context);
        logger.logEvent(eventName, parameters);
    }

    public static void logFirebaseEvent(Context context, String eventName, Bundle parameters) {
        if (context == null || eventName == null || parameters == null) {
            return;
        }
        FirebaseAnalytics.getInstance(context.getApplicationContext()).logEvent(eventName, parameters);
    }

    public static void logAmplitudeEvent(Amplitude amplitude, String eventName, HashMap<String, String> properties) {
        if (amplitude == null || eventName == null || properties == null) {
            return;
        }
        amplitude.track(eventName, properties);
    }

    public static void logMixpanelEvent(MixpanelAPI mixpanel, String eventName, HashMap<String, String> properties) {
        if (mixpanel == null || eventName == null || properties == null) {
            return;
        }
        try {
            mixpanel.track(eventName, new JSONObject(properties));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
