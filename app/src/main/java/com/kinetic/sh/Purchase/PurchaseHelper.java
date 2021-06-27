package com.kinetic.sh.Purchase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.R;

import java.util.ArrayList;
import java.util.List;

public class PurchaseHelper implements PurchasesUpdatedListener, ConsumeResponseListener {
    private static final String TAG = "PurchaseHelper";

    public BillingClient billingClient;

    public Context mContext;

    public void onConsumeResponse(BillingResult billingResult, String str) {
    }

    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list) {
    }

    public PurchaseHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void consumePurchase(final Purchase purchase) {
        BillingClient build = BillingClient.newBuilder(this.mContext).enablePendingPurchases().setListener(this).build();
        this.billingClient = build;
        build.startConnection(new BillingClientStateListener() {
            public void onBillingServiceDisconnected() {
            }

            public void onBillingSetupFinished(BillingResult billingResult) {
                PurchaseHelper.this.billingClient
                        .consumeAsync(ConsumeParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
//                                .setDeveloperPayload(purchase.getDeveloperPayload())
                                .build(), PurchaseHelper.this);
                ((MainApplication) ((AppCompatActivity) PurchaseHelper.this.mContext).getApplication()).setPremium(false);
                Log.i(PurchaseHelper.TAG, "consumed :" + purchase.getSkus().get(0));
            }
        });
    }

    public void setupBillingClient() {

        ArrayList<String> listKeySub = new ArrayList<>();

        listKeySub.add(this.mContext.getString(R.string.subscription_sku_monthly));
        listKeySub.add(this.mContext.getString(R.string.subscription_sku_threemonthly));
        listKeySub.add(this.mContext.getString(R.string.subscription_sku_annually));


        if (BillingProcessor.isIabServiceAvailable(this.mContext)) {
            BillingProcessor billingProcessor = new BillingProcessor(this.mContext, this.mContext.getString(R.string.iap_license_base64_key), new BillingProcessor.IBillingHandler() {
                @Override
                public void onProductPurchased(@NonNull String productId, TransactionDetails details) {

                }

                @Override
                public void onPurchaseHistoryRestored() {

                }

                @Override
                public void onBillingError(int errorCode, Throwable error) {
                    Log.e(getClass().getSimpleName(), "Log errorCode " + errorCode);
                    Toast.makeText(mContext, "onBillingError errorCode " + errorCode, Toast.LENGTH_SHORT).show();
//                    error.printStackTrace();
                }

                @Override
                public void onBillingInitialized() {
                    Toast.makeText(mContext, "onBillingInitialized", Toast.LENGTH_SHORT).show();
                    System.out.println("Log onBillingInitialized");
                }
            });
            billingProcessor.initialize();

            MainApplication mainApplication = (MainApplication) ((AppCompatActivity) PurchaseHelper.this.mContext).getApplication();
            boolean purchaseResult = billingProcessor.loadOwnedPurchasesFromGoogle();
            if (purchaseResult) {
                boolean isSub = false;
                for (String s : listKeySub) {
                    TransactionDetails subscriptionTransactionDetails = billingProcessor.getSubscriptionTransactionDetails(s);

                    if (subscriptionTransactionDetails != null) {
                        isSub = true;
                    } else {
                        Log.e("sub", "setupBillingClient: no sub :  " + s);
                    }
                }
                mainApplication.setPremium(isSub);
            } else {
                Log.e("sub", "setupBillingClient:  user no sub");
            }
        }

    }
}
