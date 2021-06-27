package com.kinetic.sh.Ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.material.snackbar.Snackbar;
import com.kinetic.sh.Adapters.AdapterTemplatePicker2;
import com.kinetic.sh.Fragments.LockedTemplateDialog;
import com.kinetic.sh.Helpers.MyPreferences;
import com.kinetic.sh.Helpers.RecyclerViewMargin;
import com.kinetic.sh.Models.CompMeta;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.Qutils.QuickUtils;
import com.kinetic.sh.R;
import com.kinetic.sh.ads.AdmobAds;

import java.util.ArrayList;
import java.util.List;

public class TemplatePickerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NUMBER_OF_ADS = 4;

    private final String TAG = "TemplatePickerActivity";
    // The AdLoader used to load ads.
    private AdLoader adLoader;

    // List of native ads that have been successfully loaded.
    private List<UnifiedNativeAd> mAdmobNativeAds = new ArrayList<>();


    private Button appUpdateButton;
    public LinearLayout appUpdateContainer;

    private Bundle bundle;
    private List<CompMeta> compMetas;
    private List<Object> mDataSet;
    private Context context;
    private MainApplication global;
    private RecyclerView mRecyclerView;
    private LinearLayout wrapperLayout;
    private TextView proTag;
    private ImageView moreAction;
    public CompMeta selectedCompMeta;

    private ImageView singleList, doubleList, trebleList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_picker);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        AdmobAds.loadBanner(this);

        this.context = this;
        this.bundle = new Bundle();
        this.mRecyclerView = findViewById(R.id.rv_anim_picker);
        this.appUpdateContainer = findViewById(R.id.app_update_container);
        this.appUpdateButton = findViewById(R.id.app_update_button);
        this.wrapperLayout = findViewById(R.id.activity_main_layout);
        this.proTag = findViewById(R.id.premierTag);
        this.moreAction = findViewById(R.id.more_action);
        this.moreAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TemplatePickerActivity.this, SettingsActivity.class));
            }
        });


        this.global = (MainApplication) getApplication();
        this.global.setFirst(MyPreferences.isFirst(this.context));

        Log.i(TAG, "onResume: " + this.global.getPremium());
        if (this.global.getPremium()) {
            this.proTag.setVisibility(View.VISIBLE);
        } else {
            this.proTag.setVisibility(View.GONE);
        }

        if (!QuickUtils.internetConnectionAvailable(this.context)) {
            final Snackbar make = Snackbar.make(this.wrapperLayout, "Make sure you have working internet otherwise some functions will not work properly", Snackbar.LENGTH_INDEFINITE);
            make.setAction("GOT IT", new View.OnClickListener() {
                public void onClick(View view) {
                    make.dismiss();
                }
            });
            make.show();
        }
        this.appUpdateButton.setOnClickListener(this);

        singleList = findViewById(R.id.listView);
        doubleList = findViewById(R.id.doubleListView);
        trebleList = findViewById(R.id.trebleListView);


        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadRecyclerViewType();


        loadData();

        showAd();

    }

    private void loadRecyclerViewType() {

        String val = sharedPreferences.getString("val","2");

        if (val.equals("1")) {
            this.mRecyclerView.addItemDecoration(new RecyclerViewMargin(8, 1));
            this.mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
            this.mRecyclerView.findViewHolderForLayoutPosition(0);

            singleList.setVisibility(View.GONE);
            trebleList.setVisibility(View.GONE);
            doubleList.setVisibility(View.VISIBLE);

        }
        if (val.equals("2")) {
            this.mRecyclerView.addItemDecoration(new RecyclerViewMargin(8, 1));
            this.mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
            this.mRecyclerView.findViewHolderForLayoutPosition(0);

            singleList.setVisibility(View.GONE);
            trebleList.setVisibility(View.VISIBLE);
            doubleList.setVisibility(View.GONE);
        }

        if (val.equals("3")) {
            this.mRecyclerView.addItemDecoration(new RecyclerViewMargin(8, 1));
            this.mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, 1));
            this.mRecyclerView.findViewHolderForLayoutPosition(0);

            singleList.setVisibility(View.VISIBLE);
            trebleList.setVisibility(View.GONE);
            doubleList.setVisibility(View.GONE);
        }


        singleList.setOnClickListener(v -> {
            singleList.setVisibility(View.GONE);
            trebleList.setVisibility(View.GONE);
            doubleList.setVisibility(View.VISIBLE);

            editor.putString("val","1");
            editor.commit();

            this.mRecyclerView.addItemDecoration(new RecyclerViewMargin(8, 1));
            this.mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
            this.mRecyclerView.findViewHolderForLayoutPosition(0);
        });

        doubleList.setOnClickListener(v -> {
            singleList.setVisibility(View.GONE);
            trebleList.setVisibility(View.VISIBLE);
            doubleList.setVisibility(View.GONE);
            editor.putString("val","2");
            editor.commit();
            this.mRecyclerView.addItemDecoration(new RecyclerViewMargin(8, 1));
            this.mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
            this.mRecyclerView.findViewHolderForLayoutPosition(0);
        });

        trebleList.setOnClickListener(v -> {
            singleList.setVisibility(View.VISIBLE);
            trebleList.setVisibility(View.GONE);
            doubleList.setVisibility(View.GONE);
            editor.putString("val","3");
            editor.commit();
            this.mRecyclerView.addItemDecoration(new RecyclerViewMargin(8, 1));
            this.mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, 1));
            this.mRecyclerView.findViewHolderForLayoutPosition(0);
        });


    }

    public void loadData() {

        this.mDataSet = new ArrayList<>();
        this.compMetas = new ArrayList<>();
        for (int i = 1; i <= 110; i++) {
            CompMeta compMeta = new CompMeta();
            compMeta.setEffectName("Tile" + i);
            if (!this.global.getPremium() && i == 1) {
                compMeta.setProTemplate(true);
            }


            if (this.global.getPremium() && i == 2) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 3) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 4) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 5) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 6) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 7) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 8) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 9) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 10) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 11) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 12) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 13) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 14) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 15) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 16) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 17) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 18) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 19) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 20) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 21) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 22) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 23) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 24) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 25) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 26) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 27) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 28) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 29) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 30) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 31) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 32) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 33) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 34) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 35) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 36) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 37) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 38) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 39) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 40) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 41) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 42) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 43) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 44) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 45) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 46) {
                compMeta.setProTemplate(false);
            }

            if (this.global.getPremium() && i == 47) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 48) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 49) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 50) {
                compMeta.setProTemplate(false);
            }
            if (this.global.getPremium() && i == 51) {
                compMeta.setProTemplate(false);
            }


            this.compMetas.add(compMeta);
        }

        this.mDataSet.addAll(this.compMetas);

        Boolean isShowAdmobNative = Boolean.valueOf(this.getString(R.string.is_show_admob_native));

        if (!this.global.getPremium()) {
            loadAdmobNativeAds();
        }

        AdapterTemplatePicker2 adapterTemplatePicker2 = new AdapterTemplatePicker2(this.context, this.mDataSet, isShowAdmobNative);
        this.mRecyclerView.setAdapter(adapterTemplatePicker2);
        adapterTemplatePicker2.setAnimationPickerListener(new AdapterTemplatePicker2.AnimationPickerListener() {
            @Override
            public void onClicked(CompMeta compMeta, AdapterTemplatePicker2.ViewHolderAnimation viewHolderAnimation) {
                TemplatePickerActivity.this.selectedCompMeta = compMeta;
                if (compMeta.isProTemplate()) {
                    TemplatePickerActivity.this.showLockedTemplateDialog();
                    return;
                }
                Intent intent = new Intent(TemplatePickerActivity.this, MainActivity.class);
                intent.putExtra("effectName", compMeta.getEffectName());
                intent.putExtra("CompMeta", compMeta);
                TemplatePickerActivity.this.startActivity(intent);
                AdmobAds.showFullAds(null);
            }
        });
    }

    private void updateRecyclerViewData() {
        if (this.mRecyclerView.getAdapter() instanceof AdapterTemplatePicker2) {
            final AdapterTemplatePicker2 adapter = (AdapterTemplatePicker2) this.mRecyclerView.getAdapter();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    adapter.updateData(TemplatePickerActivity.this.mDataSet);
                }
            });

        }
    }


    private void loadAdmobNativeAds() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                AdLoader.Builder builder = new AdLoader.Builder(TemplatePickerActivity.this.context, TemplatePickerActivity.this.getString(R.string.admob_native_id));
                TemplatePickerActivity.this.adLoader = builder.forUnifiedNativeAd(
                        new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                            @Override
                            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                                // A native ad loaded successfully, check if the ad loader has finished loading
                                // and if so, insert the ads into the list.
                                Log.e(TemplatePickerActivity.this.TAG, "Loaded native ads.");
                                TemplatePickerActivity.this.mAdmobNativeAds.add(unifiedNativeAd);
                                if (!TemplatePickerActivity.this.adLoader.isLoading()) {
                                    insertAdmobAdsInMenuItems();
                                }
                            }
                        }).withAdListener(
                        new AdListener() {
                            @Override
                            public void onAdFailedToLoad(LoadAdError loadAdError) {
                                // A native ad failed to load, check if the ad loader has finished loading
                                // and if so, insert the ads into the list.
                                Log.e(TemplatePickerActivity.this.TAG, "The previous native ad failed to load. Attempting to"
                                        + " load another.");
                                if (!TemplatePickerActivity.this.adLoader.isLoading()) {
                                    insertAdmobAdsInMenuItems();
                                }
                            }
                        }).build();

                // Load the Native ad in background thread.
                //
                TemplatePickerActivity.this.adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_ADS);
            }
        };

        new Thread(runnable).start();
    }


    private void insertAdmobAdsInMenuItems() {
        Log.e(TemplatePickerActivity.this.TAG, "Insert native ads to DataSet.");
        if (mAdmobNativeAds.size() <= 0) {
            return;
        }
        try {
            int offset = (this.mDataSet.size() / this.mAdmobNativeAds.size()) + 1;
            int index = NUMBER_OF_ADS;
            for (UnifiedNativeAd ad : mAdmobNativeAds) {
                if (this.mDataSet.size() > index) {
                    this.mDataSet.add(index, ad);
                    Log.i(TAG, "insertAdsInMenuItems: offset " + offset + " index " + index);
                    index += offset;
                }
            }
            updateRecyclerViewData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showAd() {
        if (!this.global.getPremium()) {
//            FacebookAds.loadBanner(this);
        }

    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.app_update_button) {
            this.bundle.putString("update_available", "update_from_app");

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(this.context, "Update sh failed! Result code: " + resultCode, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this.context, "Update has been successfully installed", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }


    public void showLockedTemplateDialog() {
        FragmentManager fm = getSupportFragmentManager();
        final LockedTemplateDialog lockedTemplateDialog = new LockedTemplateDialog();
        lockedTemplateDialog.show(fm, "LockedTemplateDialog");
        lockedTemplateDialog.setLockedTemplateListener(new LockedTemplateDialog.LockedTemplateListener() {
            public void onSeeAdClicked() {
            }

            public void onGetProClicked() {
                Intent intent = new Intent(TemplatePickerActivity.this.context, PremiumFeatureActivity2.class);
                lockedTemplateDialog.dismiss();
                TemplatePickerActivity.this.startActivity(intent);
            }

            public void onAdSeen() {
                if (TemplatePickerActivity.this.selectedCompMeta != null) {
                    Intent intent = new Intent(TemplatePickerActivity.this, MainActivity.class);
                    intent.putExtra("effectName", TemplatePickerActivity.this.selectedCompMeta.getEffectName());
                    intent.putExtra("CompMeta", TemplatePickerActivity.this.selectedCompMeta);
                    lockedTemplateDialog.dismiss();
                    TemplatePickerActivity.this.startActivity(intent);
                }
            }
        });
    }
}