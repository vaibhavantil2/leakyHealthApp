package io.privado.privadohealthapp;

import android.app.Application;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.amplitude.api.Amplitude;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.google.android.gms.ads.MobileAds;
import com.segment.analytics.Analytics;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Facebook SDK
        FacebookSdk.setApplicationId(getString(R.string.facebook_application_id));
        FacebookSdk.setClientToken(getString(R.string.facebook_client_token));
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        // Initialize Firebase Analytics
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Initialize Amplitude
        Amplitude.getInstance().initialize(this, getString(R.string.amplitude_api_key)).enableForegroundTracking(this);

        // Initialize Mixpanel
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, getString(R.string.mixpanel_token));

        // Initialize Google AdMob
        MobileAds.initialize(this, initializationStatus -> {});

        // Initialize Segment
        Analytics analytics = new Analytics.Builder(this, getString(R.string.segment_write_key))
            .trackApplicationLifecycleEvents() // Enable this to track lifecycle events automatically
            .recordScreenViews() // Enable this to automatically record screen views
            .build();
        Analytics.setSingletonInstance(analytics);


        // Commented-out Initializations
        // Criteo.getInstance().init(getApplicationContext(), getString(R.string.criteo_id));
        // SnapPixel.init(this, getString(R.string.snap_pixel_id));
    }
}
