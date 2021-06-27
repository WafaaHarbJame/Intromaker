package com.kinetic.sh.Fragments.Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.kinetic.sh.Adapters.AdapterImagePicker;
import com.kinetic.sh.Helpers.ImageDownloaderHelper;
import com.kinetic.sh.Helpers.PixabayImg;
import com.kinetic.sh.Intefaces.PermissionListener;
import com.kinetic.sh.Permissions.StoragePermission;
import com.kinetic.sh.R;
import com.kinetic.sh.Template.Template;
import com.kinetic.sh.Template.TemplateUtils.TemplateUtils;
import com.kinetic.sh.Ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageBgFactory {
    private static final String TAG = "ImageBgFactory";

    public AdapterImagePicker adapter;

    public LottieAnimationView animationView;

    public LottieAnimationView bgAnimationView;

    public BottomSheetBehavior bsbImagePicker;
    private ChipGroup chipGroup;

    public Context context;
    private SeekBar darkenSeekbar;

    public LinearLayout darkenSeekbarContainer;

    public ImageView imageView;

    public Boolean isDataLoaded = false;

    public List<JSONObject> jsonObjects;

    public Snackbar loadingSandbar;

    public MainActivity mainActivity;
    private LinearLayout parentImagePicker;

    public PixabayImg pixabayImg;

    public String query;
    private RecyclerView recyclerView;

    public Template template;

    public TemplateUtils templateUtils;

    public VideoView videoView;

    public ImageBgFactory(Context context2) {
        this.context = context2;
    }

    public void onCreate() {
        MainActivity mainActivity2 = (MainActivity) this.context;
        this.mainActivity = mainActivity2;
        this.recyclerView = mainActivity2.findViewById(R.id.bs_image_picker_rv);
        this.imageView = this.mainActivity.findViewById(R.id.main_image_view);
        LinearLayout linearLayout = this.mainActivity.findViewById(R.id.bottom_sheet_image_picker);
        this.parentImagePicker = linearLayout;
        this.bsbImagePicker = BottomSheetBehavior.from(linearLayout);
        this.chipGroup = this.mainActivity.findViewById(R.id.bs_image_picker_chip_group);
        this.darkenSeekbar = this.mainActivity.findViewById(R.id.transparency_seekbar_image_view);
        this.darkenSeekbarContainer = this.mainActivity.findViewById(R.id.daren_seekbar_container_image_bg);
        this.videoView = this.mainActivity.findViewById(R.id.video_view);
        this.bgAnimationView = this.mainActivity.findViewById(R.id.animation_view_bg);
        this.template = ((MainActivity) this.context).global.getTemplate();
        this.animationView = this.mainActivity.findViewById(R.id.animation_view);
        this.templateUtils = new TemplateUtils();
        this.darkenSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                Log.i(ImageBgFactory.TAG, "onProgressChanged: ");
                float f = (float) i;
                ImageBgFactory.this.imageView.setAlpha(Math.abs((f / 100.0f) - 1.0f));
                ImageBgFactory.this.template.getImageBgMeta().setAlpha(f);
            }
        });
    }

    public void init() {
        PixabayImg pixabayImg2 = new PixabayImg(this.context);
        this.pixabayImg = pixabayImg2;
        String str = this.query;
        if (str == null) {
            pixabayImg2.setQuery("Abstract");
        } else {
            pixabayImg2.setQuery(str);
        }
        this.pixabayImg.init();
        this.jsonObjects = new ArrayList<>();
        this.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        AdapterImagePicker adapterImagePicker = new AdapterImagePicker(this.jsonObjects);
        this.adapter = adapterImagePicker;
        this.recyclerView.setAdapter(adapterImagePicker);
        this.loadingSandbar = Snackbar.make(this.mainActivity.findViewById(R.id.main_coordinator_layout), "Loading Images, please wait..", -2);
        if (this.bsbImagePicker.getState() == 5) {
            this.bsbImagePicker.setState(6);
        }
        setData();
        this.chipGroup.removeAllViews();
        chipManager();
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (!recyclerView.canScrollVertically(1)) {
                    ImageBgFactory.this.loadingSandbar.show();
                    if (ImageBgFactory.this.isDataLoaded) {
                        ImageBgFactory.this.isDataLoaded = false;
                        ImageBgFactory.this.loadingSandbar.dismiss();
                        ImageBgFactory.this.pixabayImg.getNextPage();
                    }
                }
            }
        });
        this.adapter.setImagePickerListener(new AdapterImagePicker.ImagePickerListener() {
            public void onSelected(final JSONObject jSONObject, final AdapterImagePicker.ViewHolder viewHolder) {
                if (new StoragePermission((Activity) ImageBgFactory.this.context).checkPermissions()) {
                    ImageBgFactory.this.setImage(jSONObject, viewHolder);
                } else {
                    ImageBgFactory.this.mainActivity.setPermissionListener(new PermissionListener() {
                        public void onStorageAccess() {
                            ImageBgFactory.this.setImage(jSONObject, viewHolder);
                        }
                    });
                }
            }
        });
        this.bsbImagePicker.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == 5 && ImageBgFactory.this.loadingSandbar.isShown()) {
                    ImageBgFactory.this.loadingSandbar.dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                GradientDrawable gradientDrawable = new GradientDrawable();
                String string = ImageBgFactory.this.context.getResources().getString(R.color.colorPrimaryComet);
                gradientDrawable.setColor(Color.parseColor(string));
                gradientDrawable.setCornerRadius(ImageBgFactory.convertDpToPixel((1.0f - slideOffset) * 20.0f, ImageBgFactory.this.context));
                gradientDrawable.setStroke(2, Color.parseColor(string));
                bottomSheet.setBackground(gradientDrawable);
            }
        });
    }

    public void setAlpha(float f) {
        this.darkenSeekbar.setProgress((int) f);
    }


    public static float convertDpToPixel(float f, Context context2) {
        return f * (((float) context2.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }


    public void setData() {
        this.loadingSandbar.show();
        this.pixabayImg.setPixabayImgListener(new PixabayImg.PixabayImgListener() {
            public void onDataLoaded(JSONObject jSONObject) {
                ImageBgFactory.this.isDataLoaded = true;
                ImageBgFactory.this.loadingSandbar.dismiss();
                try {
                    JSONArray jSONArray = jSONObject.getJSONArray("hits");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        ImageBgFactory.this.jsonObjects.add(jSONArray.getJSONObject(i));
                    }
                    ImageBgFactory.this.adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.i(ImageBgFactory.TAG, "onDataLoaded:  " + e.getMessage());
                    e.printStackTrace();
                }
            }

            public void onError(VolleyError volleyError) {
                ImageBgFactory.this.loadingSandbar.dismiss();
                Toast.makeText(ImageBgFactory.this.context, "Something went wrong while getting images", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setImage(String str) {
        this.bgAnimationView.setVisibility(View.GONE);
        this.imageView.setVisibility(View.VISIBLE);
        this.videoView.setVisibility(View.GONE);
        this.darkenSeekbarContainer.setVisibility(View.VISIBLE);
        this.template.setBgType(3);
        this.mainActivity.currentVideoPath = null;
        this.template.getImageBgMeta().setLocalUrl(str);
        Glide.with(this.context).load(str).into(this.imageView);
    }

    public void downloadAndLoadImage(String str) {
        final ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Please wait... \nDownloading background image");
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(R.drawable.rounded_dialog);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.setProgressStyle(1);
        progressDialog.setCancelable(false);
        final ImageDownloaderHelper imageDownloaderHelper = new ImageDownloaderHelper(this.context, str, "temp/imgs");
        imageDownloaderHelper.init();
        progressDialog.setButton(-2, "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                imageDownloaderHelper.cancel();
                progressDialog.dismiss();
            }
        });
        progressDialog.show();
        progressDialog.getButton(-2).setBackgroundColor(0);
        progressDialog.getButton(-2).setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent));
        imageDownloaderHelper.setImageDownloaderListner(new ImageDownloaderHelper.ImageDownloaderListener() {
            public void onDone(String str) {
                ImageBgFactory.this.bgAnimationView.setVisibility(View.GONE);
                ImageBgFactory.this.animationView.setFrame(0);
                ImageBgFactory.this.animationView.playAnimation();
                ImageBgFactory.this.darkenSeekbarContainer.setVisibility(View.VISIBLE);
                ImageBgFactory.this.imageView.setVisibility(View.VISIBLE);
                ImageBgFactory.this.videoView.setVisibility(View.GONE);
                ImageBgFactory.this.template.setBgType(3);
                ImageBgFactory.this.mainActivity.currentVideoPath = null;
                ImageBgFactory.this.template.getImageBgMeta().setLocalUrl(str);
                ImageBgFactory.this.templateUtils.cacheAssets();
                Glide.with(ImageBgFactory.this.context).load(str).into(ImageBgFactory.this.imageView);
                progressDialog.dismiss();
            }

            public void onProgress(int i) {
                progressDialog.setProgress(i);
                Log.i(ImageBgFactory.TAG, "onProgress: " + i);
            }

            public void onError(String str) {
                Toast.makeText(ImageBgFactory.this.context, "Something went wrong , Please try again", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void setImage(JSONObject jSONObject, final AdapterImagePicker.ViewHolder viewHolder) {
        try {
            String string = jSONObject.getString("largeImageURL");
            this.template.setBgType(3);
            this.mainActivity.currentVideoPath = null;
            this.template.getImageBgMeta().setUrl(string);
            this.imageView.setVisibility(View.VISIBLE);
            this.videoView.setVisibility(View.GONE);
            this.bgAnimationView.setVisibility(View.GONE);
            viewHolder.loadingContainer.setVisibility(View.VISIBLE);
            ImageDownloaderHelper imageDownloaderHelper = new ImageDownloaderHelper(this.context, string, "temp/imgs");
            imageDownloaderHelper.init();
            Log.i(TAG, "setImage: " + string);
            imageDownloaderHelper.setImageDownloaderListner(new ImageDownloaderHelper.ImageDownloaderListener() {
                public void onDone(String str) {
                    ImageBgFactory.this.template.setBgType(3);
                    ImageBgFactory.this.mainActivity.currentVideoPath = null;
                    ImageBgFactory.this.template.getImageBgMeta().setLocalUrl(str);
                    Log.i(ImageBgFactory.TAG, "onDoneImageDownload: " + str);
                    Glide.with(ImageBgFactory.this.context).load(str).into(ImageBgFactory.this.imageView);
                    ImageBgFactory.this.darkenSeekbarContainer.setVisibility(View.VISIBLE);
                    viewHolder.loadingContainer.setVisibility(View.GONE);
                    ImageBgFactory.this.bsbImagePicker.setState(6);
                }

                public void onProgress(int i) {
                    viewHolder.progressBar.setProgress(i);
                }

                public void onError(String str) {
                    viewHolder.loadingContainer.setVisibility(View.GONE);
                    Toast.makeText(ImageBgFactory.this.context, "Something went wrong , Please try again", Toast.LENGTH_LONG).show();
                    viewHolder.loadingContainer.setVisibility(View.GONE);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void chipManager() {
        String[] strArr = {"Wallpaper", "Abstract", "Education", "Nature", "Flower", "cooking", "technology", "Background", "city", "ocean", "summer"};
        for (int i = 0; i < 11; i++) {
            String str = strArr[i];
            Chip chip = new Chip(this.context);
            chip.setText(str);
            chip.setChipBackgroundColorResource(R.color.chip_select_color_light);
            chip.setTextColor(ContextCompat.getColor(this.context, R.color.white));
            chip.setClickable(true);
            chip.setCheckable(true);
            this.chipGroup.addView(chip);
        }
        this.chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                if (i != -1) {
                    ImageBgFactory.this.query = ((Chip) chipGroup.findViewById(chipGroup.getCheckedChipId())).getText().toString();
                    ImageBgFactory.this.pixabayImg.setQuery(ImageBgFactory.this.query);
                    ImageBgFactory.this.pixabayImg.init();
                    ImageBgFactory.this.jsonObjects.clear();
                    ImageBgFactory.this.setData();
                }
            }
        });
    }
}
