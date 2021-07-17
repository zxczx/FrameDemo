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

import androidx.annotation.Nullable;

import com.android.billingclient.api.Purchase;

import java.util.List;

public class BillingUtilities {

    public static final String MONTHLY_SKU = "amessage_one_month_sub";
    public static final String YEARLY_SKU = "amessage_one_year_sub";

    /**
     * Return purchase for the provided SKU, if it exists.
     */
    public static Purchase getPurchaseForSku(@Nullable List<Purchase> purchases, String sku) {
        if (purchases != null) {
            for (Purchase purchase : purchases) {
                if (sku.equals(purchase.getSku())) {
                    return purchase;
                }
            }
        }
        return null;
    }

    /*
     * This will return true if the Google Play Billing APIs have a record for the subscription.
     * This will not always match the server's record of the subscription for this app user.
     *
     * Example: App user buys the subscription on a different device with a different Google
     * account. The server will show that this app user has the subscription, even if the
     * Google account on this device has not purchased the subscription.
     * In this example, the method will return false.
     *
     * Example: The app user changes by signing out and signing into the app with a different
     * email address. The server will show that this app user does not have the subscription,
     * even if the Google account on this device has purchased the subscription.
     * In this example, the method will return true.
     */
    public static boolean deviceHasGooglePlaySubscription(List<Purchase> purchases, String sku) {
        return getPurchaseForSku(purchases, sku) != null;
    }
}
