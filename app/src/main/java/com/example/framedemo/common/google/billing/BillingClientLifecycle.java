/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.framedemo.common.google.billing;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.example.framedemo.common.livedata.FlexibleMutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class BillingClientLifecycle implements LifecycleObserver, PurchasesUpdatedListener,
        BillingClientStateListener, SkuDetailsResponseListener {

    private static final String TAG = "BillingLifecycle";
    private static final String BILLING_SHARED_PREFERENCE_NAME = "billing_shared_prefs_name";
    private static final String PREF_KEY_SUBSCRIBED = "pref_key_subscribed";

    private static final List<String> LIST_OF_SKUS = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add(BillingUtilities.MONTHLY_SKU);
                add(BillingUtilities.YEARLY_SKU);
            }});

    /**
     * The purchase event is observable. Only one observer will be notified.
     */
    public SingleLiveEvent<List<Purchase>> purchaseUpdateEvent = new SingleLiveEvent<>();

    /**
     * Event that a new purchase was acknowledged.
     */
    public SingleLiveEvent<Purchase> purchaseAcknowledgedEvent = new SingleLiveEvent<>();

    /**
     * Purchases are observable. This list will be updated when the Billing Library
     * detects new or existing purchases. All observers will be notified.
     */
    public MutableLiveData<List<Purchase>> purchases = new MutableLiveData<>();

    /**
     * Store subscriptions status.
     */
    public MutableLiveData<Boolean> subscribed = new MutableLiveData<>();

    /**
     * SkuDetails for all known SKUs.
     */
    public MutableLiveData<Map<String, SkuDetails>> skusWithSkuDetails = new MutableLiveData<>();

    /**
     * Failed to get sku details.
     */
    public FlexibleMutableLiveData<Integer> skuDetailsErrorResponseCode = new FlexibleMutableLiveData<>();

    private static volatile BillingClientLifecycle INSTANCE;

    private final Application app;
    private BillingClient billingClient;

    private BillingClientLifecycle(Application app) {
        this.app = app;
        initSubscriptionStatus();
    }

    public static BillingClientLifecycle getInstance(Application app) {
        if (INSTANCE == null) {
            synchronized (BillingClientLifecycle.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BillingClientLifecycle(app);
                }
            }
        }
        return INSTANCE;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        Log.d(TAG, "ON_CREATE");
        // Create a new BillingClient in onCreate().
        // Since the BillingClient can only be used once, we need to create a new instance
        // after ending the previous connection to the Google Play Store in onDestroy().
        billingClient = BillingClient.newBuilder(app)
                .setListener(this)
                .enablePendingPurchases() // Not used for subscriptions.
                .build();
        if (!billingClient.isReady()) {
            Log.d(TAG, "BillingClient: Start connection...");
            billingClient.startConnection(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy() {
        Log.d(TAG, "ON_DESTROY");
//        if (billingClient.isReady()) {
//            Log.d(TAG, "BillingClient can only be used once -- closing connection");
//            // BillingClient can only be used once.
//            // After calling endConnection(), we must create a new BillingClient.
//            billingClient.endConnection();
//        }
    }

    @Override
    public void onBillingSetupFinished(BillingResult billingResult) {
        int responseCode = billingResult.getResponseCode();
        String debugMessage = billingResult.getDebugMessage();
        Log.d(TAG, "onBillingSetupFinished: " + responseCode + " " + debugMessage);
        if (responseCode == BillingClient.BillingResponseCode.OK) {
            // The billing client is ready. You can query purchases here.
            querySkuDetails();
            queryPurchases();
        }
    }

    @Override
    public void onBillingServiceDisconnected() {
        //To do somethings about retrying.
    }

    /**
     * Receives the result from {@link #querySkuDetails()}}.
     * <p>
     * Store the SkuDetails and post them in the {@link #skusWithSkuDetails}. This allows other
     * parts of the app to use the {@link SkuDetails} to show SKU information and make purchases.
     */
    @Override
    public void onSkuDetailsResponse(@NonNull BillingResult billingResult, List<SkuDetails> skuDetailsList) {
        int responseCode = billingResult.getResponseCode();
        String debugMessage = billingResult.getDebugMessage();
        switch (responseCode) {
            case BillingClient.BillingResponseCode.OK:
                Log.i(TAG, "onSkuDetailsResponse: " + responseCode + " " + debugMessage);
                final int expectedSkuDetailsCount = LIST_OF_SKUS.size();
                if (skuDetailsList == null) {
                    skusWithSkuDetails.postValue(Collections.emptyMap());
                    Log.e(TAG, "onSkuDetailsResponse: " +
                            "Expected " + expectedSkuDetailsCount + ", " +
                            "Found null SkuDetails. " +
                            "Check to see if the SKUs you requested are correctly published " +
                            "in the Google Play Console.");
                } else {
                    Map<String, SkuDetails> newSkusDetailList = new HashMap<>();
                    for (SkuDetails skuDetails : skuDetailsList) {
                        newSkusDetailList.put(skuDetails.getSku(), skuDetails);
                    }
                    skusWithSkuDetails.postValue(newSkusDetailList);
                    int skuDetailsCount = newSkusDetailList.size();
                    if (skuDetailsCount == expectedSkuDetailsCount) {
                        Log.i(TAG, "onSkuDetailsResponse: Found " + skuDetailsCount + " SkuDetails");
                    } else {
                        Log.e(TAG, "onSkuDetailsResponse: " +
                                "Expected " + expectedSkuDetailsCount + ", " +
                                "Found " + skuDetailsCount + " SkuDetails. " +
                                "Check to see if the SKUs you requested are correctly published " +
                                "in the Google Play Console.");
                    }
                }
                break;
            case BillingClient.BillingResponseCode.SERVICE_DISCONNECTED:
            case BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE:
            case BillingClient.BillingResponseCode.BILLING_UNAVAILABLE:
            case BillingClient.BillingResponseCode.ITEM_UNAVAILABLE:
            case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
            case BillingClient.BillingResponseCode.ERROR:
                Log.e(TAG, "onSkuDetailsResponse: " + responseCode + " " + debugMessage);
                skuDetailsErrorResponseCode.postValue(responseCode);
                break;
            case BillingClient.BillingResponseCode.USER_CANCELED:
                Log.i(TAG, "onSkuDetailsResponse: " + responseCode + " " + debugMessage);
                break;
            // These response codes are not expected.
            case BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED:
            case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
            case BillingClient.BillingResponseCode.ITEM_NOT_OWNED:
            default:
                Log.wtf(TAG, "onSkuDetailsResponse: " + responseCode + " " + debugMessage);
                skuDetailsErrorResponseCode.postValue(responseCode);
        }
    }

    /**
     * Query Google Play Billing for existing purchases.
     * <p>
     * New purchases will be provided to the PurchasesUpdatedListener.
     * You still need to check the Google Play Billing API to know when purchase tokens are removed.
     */
    public void queryPurchases() {
        if (billingClient == null) {
            Log.wtf(TAG, "querySkuDetails:billingClient is null");
            return;
        }
        if (!billingClient.isReady()) {
            Log.e(TAG, "queryPurchases: BillingClient is not ready");
            return;//return to avoid the null result of this query makes local cached sub status to false.
        }
        Log.d(TAG, "queryPurchases: SUBS");
        Purchase.PurchasesResult result = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
        if (result.getPurchasesList() == null) {
            Log.i(TAG, "queryPurchases: null purchase list");
            processPurchases(null);
        } else {
            processPurchases(result.getPurchasesList());
        }
    }

    /**
     * Called by the Billing Library when new purchases are detected.
     */
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, List<Purchase> purchases) {
        int responseCode = billingResult.getResponseCode();
        String debugMessage = billingResult.getDebugMessage();
        Log.d(TAG, "onPurchasesUpdated:" + responseCode + ":" + debugMessage);
        switch (responseCode) {
            case BillingClient.BillingResponseCode.OK:
                if (purchases == null) {
                    Log.d(TAG, "onPurchasesUpdated: null purchase list");
                    processPurchases(null);
                } else {
                    processPurchases(purchases);
                }
                break;
            case BillingClient.BillingResponseCode.USER_CANCELED:
                Log.i(TAG, "onPurchasesUpdated: User canceled the purchase");
                break;
            case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                Log.i(TAG, "onPurchasesUpdated: The user already owns this item");
                break;
            case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
                Log.e(TAG, "onPurchasesUpdated: Developer error means that Google Play " +
                        "does not recognize the configuration. If you are just getting started, " +
                        "make sure you have configured the application correctly in the " +
                        "Google Play Console. The SKU product ID must match and the APK you " +
                        "are using must be signed with release keys."
                );
                break;
        }
    }

    /**
     * Send purchase SingleLiveEvent and update purchases LiveData.
     * <p>
     * The SingleLiveEvent will trigger network call to verify the subscriptions on the sever.
     * The LiveData will allow Google Play settings UI to update based on the latest purchase data.
     */
    private void processPurchases(List<Purchase> purchasesList) {
        if (purchasesList != null) {
            Log.d(TAG, "processPurchases: " + purchasesList.size() + " purchase(s)");
        } else {
            Log.d(TAG, "processPurchases: with no purchases");
        }
        if (isUnchangedPurchaseList(purchasesList)) {
            Log.d(TAG, "processPurchases: Purchase list has not changed");
            return;
        }
        purchaseUpdateEvent.postValue(purchasesList);
        purchases.postValue(purchasesList);
        if (purchasesList != null) {
            acknowledgePurchases(purchasesList);
            logAcknowledgementStatus(purchasesList);
        }
        storeSubscriptionStatus(purchasesList);
    }

    /**
     * Acknowledge the purchase if any of them is unacknowledged.
     */
    private void acknowledgePurchases(List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            if (!purchase.isAcknowledged()) {
                acknowledgePurchase(purchase);
            }
        }
    }

    /**
     * Store subscription status.
     */
    private void storeSubscriptionStatus(List<Purchase> purchases) {
        //不能使用hasSubscriptions()方法进行判断，因为其中间接使用的purchases列表，通过post异步更新，此时可能获取不到最新结果。
        boolean isSubscribed = BillingUtilities.deviceHasGooglePlaySubscription(purchases, BillingUtilities.YEARLY_SKU)
                || BillingUtilities.deviceHasGooglePlaySubscription(purchases, BillingUtilities.MONTHLY_SKU);
        subscribed.postValue(isSubscribed);
        Log.d(TAG, "storeSubscriptionStatus:" + isSubscribed);
        getSharedPreference().edit().putBoolean(PREF_KEY_SUBSCRIBED, isSubscribed).apply();
    }

    /**
     * Fetch subscription status from shared preference.
     */
    private void initSubscriptionStatus() {
        boolean isSubscribed = getSubStatusFromSP();
        subscribed.postValue(isSubscribed);
    }

    /**
     * Get subscription status which is stored in the SharedPreference.
     *
     * @return true for has subscriptions.
     */
    public boolean getSubStatusFromSP() {
        return getSharedPreference().getBoolean(PREF_KEY_SUBSCRIBED, false);
    }

    /**
     * Get SharedPreference instance.
     */
    private SharedPreferences getSharedPreference() {
        return app.getSharedPreferences(BILLING_SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }

    /**
     * Log the number of purchases that are acknowledge and not acknowledged.
     * <p>
     * https://developer.android.com/google/play/billing/billing_library_releases_notes#2_0_acknowledge
     * <p>
     * When the purchase is first received, it will not be acknowledge.
     * This application sends the purchase token to the server for registration. After the
     * purchase token is registered to an account, the Android app acknowledges the purchase token.
     * The next time the purchase list is updated, it will contain acknowledged purchases.
     */
    private void logAcknowledgementStatus(List<Purchase> purchasesList) {
        int ack_yes = 0;
        int ack_no = 0;
        for (Purchase purchase : purchasesList) {
            if (purchase.isAcknowledged()) {
                ack_yes++;
            } else {
                ack_no++;
            }
        }
        Log.d(TAG, "logAcknowledgementStatus: acknowledged=" + ack_yes +
                " unacknowledged=" + ack_no);
    }

    /**
     * Check whether the purchases have changed before posting changes.
     */
    private boolean isUnchangedPurchaseList(List<Purchase> purchasesList) {
        // TODO: Optimize to avoid updates with identical data.
        return false;
    }

    /**
     * In order to make purchases, you need the {@link SkuDetails} for the item or subscription.
     * This is an asynchronous call that will receive a result in {@link #onSkuDetailsResponse}.
     */
    public void querySkuDetails() {
        if (billingClient == null) {
            Log.wtf(TAG, "querySkuDetails:billingClient is null");
            return;
        }
        Log.d(TAG, "querySkuDetails");
        SkuDetailsParams params = SkuDetailsParams.newBuilder()
                .setType(BillingClient.SkuType.SUBS)
                .setSkusList(LIST_OF_SKUS)
                .build();
        Log.i(TAG, "querySkuDetailsAsync");
        billingClient.querySkuDetailsAsync(params, this);
    }

    /**
     * Launching the billing flow.
     * <p>
     * Launching the UI to make a purchase requires a reference to the Activity.
     */
    public int launchBillingFlow(Activity activity, BillingFlowParams params) {
        if (billingClient == null) {
            Log.wtf(TAG, "querySkuDetails:billingClient is null");
            return BillingClient.BillingResponseCode.BILLING_UNAVAILABLE;
        }
        String sku = params.getSku();
        String oldSku = params.getOldSku();
        Log.i(TAG, "launchBillingFlow: sku: " + sku + ", oldSku: " + oldSku);
        if (!billingClient.isReady()) {
            Log.e(TAG, "launchBillingFlow: BillingClient is not ready");
        }
        BillingResult billingResult = billingClient.launchBillingFlow(activity, params);
        int responseCode = billingResult.getResponseCode();
        String debugMessage = billingResult.getDebugMessage();
        Log.d(TAG, "launchBillingFlow: BillingResponse " + responseCode + " " + debugMessage);
        return responseCode;
    }

    /**
     * Acknowledge a purchase.
     * <p>
     * https://developer.android.com/google/play/billing/billing_library_releases_notes#2_0_acknowledge
     * <p>
     * Apps should acknowledge the purchase after confirming that the purchase token
     * has been associated with a user. This app only acknowledges purchases after
     * successfully receiving the subscription data back from the server.
     * <p>
     * Developers can choose to acknowledge purchases from a server using the
     * Google Play Developer API. The server has direct access to the user database,
     * so using the Google Play Developer API for acknowledgement might be more reliable.
     * <p>
     * If the purchase token is not acknowledged within 3 days,
     * then Google Play will automatically refund and revoke the purchase.
     * This behavior helps ensure that users are not charged for subscriptions unless the
     * user has successfully received access to the content.
     * This eliminates a category of issues where users complain to developers
     * that they paid for something that the app is not giving to them.
     */
    public void acknowledgePurchase(Purchase purchase) {
        if (billingClient == null) {
            Log.wtf(TAG, "querySkuDetails:billingClient is null");
            return;
        }
        Log.d(TAG, "acknowledgePurchase");
        AcknowledgePurchaseParams params = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();
        billingClient.acknowledgePurchase(params, billingResult -> {
            int responseCode = billingResult.getResponseCode();
            if (responseCode == BillingClient.BillingResponseCode.OK) {
                purchaseAcknowledgedEvent.postValue(purchase);
            }
            String debugMessage = billingResult.getDebugMessage();
            Log.d(TAG, "acknowledgePurchase: " + responseCode + " " + debugMessage);
        });
    }


    /**
     * Check if there is a subscription.
     */
    public boolean hasSubscriptions() {
        boolean hasMonthlySub = hasMonthlySub();
        boolean hasYearlySub = hasYearlySub();
        return hasMonthlySub || hasYearlySub;
    }

    /**
     * Check if monthly subscription owned;
     */
    public boolean hasMonthlySub() {
        return BillingUtilities
                .deviceHasGooglePlaySubscription(purchases.getValue(), BillingUtilities.MONTHLY_SKU);
    }

    /**
     * Check if yearly subscription owned;
     */
    public boolean hasYearlySub() {
        return BillingUtilities
                .deviceHasGooglePlaySubscription(purchases.getValue(), BillingUtilities.YEARLY_SKU);
    }

    /**
     * Check if subscribed.
     */
    public boolean isSubscribed() {
        if (subscribed.getValue() == null) {
            //Happens when check invoke this method before billing client connected.
            Log.d(TAG, "Get null when access subscribed.getValue().Use stored status instead");
            return getSubStatusFromSP();
        }
        return subscribed.getValue();
    }


    public boolean isBillingClientInitialized() {
        return billingClient != null;
    }

    public void endBillingClient() {
        if (billingClient != null && billingClient.isReady()) {
            Log.d(TAG, "BillingClient can only be used once -- closing connection");
//             BillingClient can only be used once.
//            After calling endConnection(), we must create a new BillingClient.
            billingClient.endConnection();
            billingClient = null;
        }
    }
}
