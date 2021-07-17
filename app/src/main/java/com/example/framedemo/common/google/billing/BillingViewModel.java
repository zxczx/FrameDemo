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

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.example.framedemo.FrameApplication;

import java.util.List;
import java.util.Map;

public class BillingViewModel extends AndroidViewModel {

    private static final String TAG = "BillingViewModel";

    /**
     * Local billing purchase data.
     */
    private final MutableLiveData<List<Purchase>> purchases;

    /**
     * SkuDetails for all known SKUs.
     */
    private final MutableLiveData<Map<String, SkuDetails>> skusWithSkuDetails;


    /**
     * Send an event when the Activity needs to buy something.
     */
    public SingleLiveEvent<BillingFlowParams> buyEvent = new SingleLiveEvent<>();

    /**
     * Send an event when the UI should open the Google Play
     * Store for the user to manage their subscriptions.
     */
    public SingleLiveEvent<String> openPlayStoreSubscriptionsEvent = new SingleLiveEvent<>();

    private final BillingClientLifecycle billingClientLifecycle;

    public BillingViewModel(Application application) {
        super(application);
        FrameApplication app = ((FrameApplication) application);
        purchases = app.getBillingClientLifecycle().purchases;
        skusWithSkuDetails = app.getBillingClientLifecycle().skusWithSkuDetails;
        billingClientLifecycle = BillingClientLifecycle.getInstance(app);
    }

    /**
     * Open the Play Store subscription center. If the user has exactly one SKU,
     * then open the deeplink to the specific SKU.
     */
    public void openPlayStoreSubscriptions() {
        boolean hasMonthlySub = BillingUtilities.deviceHasGooglePlaySubscription(
                purchases.getValue(), BillingUtilities.MONTHLY_SKU);
        boolean hasYearlySub = BillingUtilities.deviceHasGooglePlaySubscription
                (purchases.getValue(), BillingUtilities.YEARLY_SKU);

        if (hasMonthlySub && !hasYearlySub) {
            // If we just have a basic subscription, open the basic SKU.
            openPlayStoreSubscriptionsEvent.postValue(BillingUtilities.MONTHLY_SKU);
        } else if (!hasMonthlySub && hasYearlySub) {
            // If we just have a premium subscription, open the premium SKU.
            openPlayStoreSubscriptionsEvent.postValue(BillingUtilities.YEARLY_SKU);
        } else {
            // If we do not have an active subscription,
            // or if we have multiple subscriptions, open the default subscription center.
            openPlayStoreSubscriptionsEvent.call();
        }
    }

    /**
     * Open the Play Store basic subscription.
     */
    public void openMonthlyPlayStoreSubscriptions() {
        openPlayStoreSubscriptionsEvent.postValue(BillingUtilities.MONTHLY_SKU);
    }

    /**
     * Open the Play Store premium subscription.
     */
    public void openYearlyPlayStoreSubscriptions() {
        openPlayStoreSubscriptionsEvent.postValue(BillingUtilities.YEARLY_SKU);
    }

    /**
     * Buy a basic subscription.
     */
    public void buyMonthly() {
        boolean hasMonthlySub = BillingUtilities
                .deviceHasGooglePlaySubscription(purchases.getValue(), BillingUtilities.MONTHLY_SKU);
        boolean hasYearlySub = BillingUtilities
                .deviceHasGooglePlaySubscription(purchases.getValue(), BillingUtilities.YEARLY_SKU);
        Log.d(TAG, "hasMonthlySub: " + hasMonthlySub + ", hasYearlySub: " + hasYearlySub);
        if (hasMonthlySub && hasYearlySub) {
            // If the user has both subscriptions, open the basic SKU on Google Play.
            openPlayStoreSubscriptionsEvent.postValue(BillingUtilities.MONTHLY_SKU);
        } else if (hasMonthlySub && !hasYearlySub) {
            // If the user just has a basic subscription, open the basic SKU on Google Play.
            openPlayStoreSubscriptionsEvent.postValue(BillingUtilities.MONTHLY_SKU);
        } else if (!hasMonthlySub && hasYearlySub) {
            // If the user just has a premium subscription, downgrade.
            buy(BillingUtilities.MONTHLY_SKU, BillingUtilities.YEARLY_SKU);
        } else {
            // If the user dooes not have a subscription, buy the basic SKU.
            buy(BillingUtilities.MONTHLY_SKU, null);
        }
    }

    /**
     * Buy a premium subscription.
     */
    public void buyYearly() {
        boolean hasMonthlySub = BillingUtilities
                .deviceHasGooglePlaySubscription(purchases.getValue(), BillingUtilities.MONTHLY_SKU);
        boolean hasYearlySub = BillingUtilities
                .deviceHasGooglePlaySubscription(purchases.getValue(), BillingUtilities.YEARLY_SKU);
        Log.d(TAG, "hasMonthlySub: " + hasMonthlySub + ", hasYearlySub: " + hasYearlySub);
        if (hasMonthlySub && hasYearlySub) {
            // If the user has both subscriptions, open the premium SKU on Google Play.
            openPlayStoreSubscriptionsEvent.postValue(BillingUtilities.YEARLY_SKU);
        } else if (!hasMonthlySub && hasYearlySub) {
            // If the user just has a premium subscription, open the premium SKU on Google Play.
            openPlayStoreSubscriptionsEvent.postValue(BillingUtilities.YEARLY_SKU);
        } else if (hasMonthlySub && !hasYearlySub) {
            // If the user just has a basic subscription, upgrade.
            buy(BillingUtilities.YEARLY_SKU, BillingUtilities.MONTHLY_SKU);
        } else {
            // If the user does not have a subscription, buy the premium SKU.
            buy(BillingUtilities.YEARLY_SKU, null);
        }
    }

    /**
     * Upgrade to a premium subscription.
     */
    public void buyUpgrade() {
        buy(BillingUtilities.YEARLY_SKU, BillingUtilities.MONTHLY_SKU);
    }

    /**
     * An observer which observer skuDetails‘ refreshing。
     */
    private Observer<Map<String, SkuDetails>> skuDetailsRefreshObserver;

    /**
     * Use the Google Play Billing Library to make a purchase.
     */
    private void buy(String sku, @Nullable String oldSku) {
        removeSkuRefreshObservers();
        // First, determine whether the new SKU can be purchased.
        boolean isSkuOnDevice = BillingUtilities
                .deviceHasGooglePlaySubscription(purchases.getValue(), sku);
        if (isSkuOnDevice) {
            Log.e(TAG, "You cannot buy a SKU that is already owned: " + sku +
                    "This is an error in the application trying to use Google Play Billing.");
        } else {
            // Second, determine whether the old SKU can be replaced.
            // If the old SKU cannot be used, set this value to null and ignore it.
            String oldSkuToBeReplaced = null;
            if (isOldSkuReplaceable(purchases.getValue(), oldSku)) {
                oldSkuToBeReplaced = oldSku;
            }
            // Third, create the billing parameters for the purchase.
            if (sku.equals(oldSkuToBeReplaced)) {
                Log.i(TAG, "Re-subscribe.");
            } else if (BillingUtilities.YEARLY_SKU.equals(sku)
                    && BillingUtilities.MONTHLY_SKU.equals(oldSkuToBeReplaced)) {
                Log.i(TAG, "Upgrade!");
            } else if (BillingUtilities.MONTHLY_SKU.equals(sku)
                    && BillingUtilities.YEARLY_SKU.equals(oldSkuToBeReplaced)) {
                Log.i(TAG, "Downgrade...");
            } else {
                Log.i(TAG, "Regular purchase.");
            }
            SkuDetails skuDetails = null;
            // Create the parameters for the purchase.
            if (skusWithSkuDetails.getValue() != null) {
                skuDetails = skusWithSkuDetails.getValue().get(sku);
            }
            if (skuDetails == null) {
                Log.e(TAG, "Could not find SkuDetails to make purchase.Try refreshing.");
                //用户点击购买，当sku为空时，对sku进行刷新，如果刷新结果为空，不自动进行二次刷新，如果结果不为空，则继续购买逻辑。
                String finalOldSkuToBeReplaced = oldSkuToBeReplaced;
                skuDetailsRefreshObserver = new Observer<Map<String, SkuDetails>>() {
                    @Override
                    public void onChanged(Map<String, SkuDetails> refreshedSkusWithSkuDetails) {
                        skusWithSkuDetails.removeObserver(this);
                        // Create the parameters for the purchase.
                        if (refreshedSkusWithSkuDetails != null) {
                            SkuDetails refreshedSkuDetails = refreshedSkusWithSkuDetails.get(sku);
                            if (refreshedSkuDetails != null) {
                                Log.d(TAG, "SkuDetails refreshed, continue buying.");
                                continueBuying(sku, finalOldSkuToBeReplaced, refreshedSkuDetails);
                            } else {
                                Log.e(TAG, "SkuDetails refreshed, but still null");
                            }
                        }
                    }
                };
                skusWithSkuDetails.observeForever(skuDetailsRefreshObserver);
                billingClientLifecycle.querySkuDetails();
                return;
            }
            continueBuying(sku, oldSkuToBeReplaced, skuDetails);
        }
    }

    /**
     * Continue buying if the skuDetails isn't null.
     */
    private void continueBuying(String sku, String oldSkuToBeReplaced, SkuDetails skuDetails) {
        BillingFlowParams.Builder billingBuilder =
                BillingFlowParams.newBuilder().setSkuDetails(skuDetails);
        // Only set the old SKU parameter if the old SKU is already owned.
        if (oldSkuToBeReplaced != null && !oldSkuToBeReplaced.equals(sku)) {
            Purchase oldPurchase = BillingUtilities
                    .getPurchaseForSku(purchases.getValue(), oldSkuToBeReplaced);
            billingBuilder.setOldSku(oldSkuToBeReplaced, oldPurchase.getPurchaseToken());
        }
        BillingFlowParams billingParams = billingBuilder.build();
        // Send the parameters to the Activity in order to launch the billing flow.
        buyEvent.postValue(billingParams);
    }

    @Override
    protected void onCleared() {
        removeSkuRefreshObservers();
    }

    /**
     * Developers should manually remove skuDetailsRefreshObserver when the Billing page was closed.
     */
    private void removeSkuRefreshObservers() {
        if (skusWithSkuDetails.hasObservers()) {
            skusWithSkuDetails.removeObserver(skuDetailsRefreshObserver);
        }
    }

    /**
     * Determine if the old SKU can be replaced.
     */
    private boolean isOldSkuReplaceable(
            List<Purchase> purchases,
            String oldSku) {
        if (oldSku == null) return false;
        return BillingUtilities.deviceHasGooglePlaySubscription(purchases, oldSku);
    }
}
