package com.kinetic.sh.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.kinetic.sh.BuildConfig;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.R;

import java.util.ArrayList;
import java.util.List;

public class PremiumFeatureActivity2 extends AppCompatActivity implements BillingProcessor.IBillingHandler {
    private static final String TAG = "PremiumFeatureActivity2";

    public List<String> skuList = new ArrayList<>();
    BillingProcessor bp;
    ConstraintLayout oneMonth, threeMonth, year;
    MainApplication mainApplication;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_premium_feature2);

        mainApplication = (MainApplication) this.getApplication();

        skuList.add(this.getString(R.string.subscription_sku_monthly));
        skuList.add(this.getString(R.string.subscription_sku_threemonthly));
        skuList.add(this.getString(R.string.subscription_sku_annually));


        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        oneMonth = findViewById(R.id.oneMonth);
        threeMonth = findViewById(R.id.threeMonth);
        year = findViewById(R.id.year);

        bp = new BillingProcessor(this, this.getString(R.string.iap_license_base64_key), this);
        bp.initialize();

        oneMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.subscribe(PremiumFeatureActivity2.this, PremiumFeatureActivity2.this.getString(R.string.subscription_sku_monthly));
            }
        });
        threeMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.subscribe(PremiumFeatureActivity2.this, PremiumFeatureActivity2.this.getString(R.string.subscription_sku_threemonthly));
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.subscribe(PremiumFeatureActivity2.this, PremiumFeatureActivity2.this.getString(R.string.subscription_sku_annually));
            }
        });


        if (BuildConfig.DEBUG) {
            return;
        }
        if (!BillingProcessor.isIabServiceAvailable(this)) {
            Toast.makeText(this, "In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onProductPurchased(@NonNull String productId, TransactionDetails details) {
        mainApplication.setPremium(true);
        finish();
    }

    @Override
    public void onPurchaseHistoryRestored() {
        for (String sku : bp.listOwnedProducts())
            Log.e(TAG, "Owned Managed Product: " + sku);
        for (String sku : bp.listOwnedSubscriptions())
            Log.e(TAG, "Owned Subscription: " + sku);
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Log.e(getClass().getSimpleName(), "errorCode " + errorCode);
//        error.printStackTrace();
    }

    @Override
    public void onBillingInitialized() {

    }


    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}