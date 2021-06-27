package com.kinetic.sh.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.VolleyError;
import com.downloader.Error;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.kinetic.sh.Adapters.AdapterDynamicBg;
import com.kinetic.sh.Adapters.AdapterGradients;
import com.kinetic.sh.Adapters.AdapterVideoPicker;
import com.kinetic.sh.Effects.BgUtils;
import com.kinetic.sh.Fragments.Helper.ImageBgFactory;
import com.kinetic.sh.Helpers.ColorPickerHelper;
import com.kinetic.sh.Helpers.DynamicBg;
import com.kinetic.sh.Helpers.GD;
import com.kinetic.sh.Helpers.Gradients;
import com.kinetic.sh.Helpers.Pixabay;
import com.kinetic.sh.Helpers.VideoDownloadHelper;
import com.kinetic.sh.Intefaces.MainListener;
import com.kinetic.sh.Intefaces.PermissionListener;
import com.kinetic.sh.Models.CompMeta;
import com.kinetic.sh.Models.MainApplication;
import com.kinetic.sh.Models.VideoMeta;
import com.kinetic.sh.Permissions.StoragePermission;
import com.kinetic.sh.R;
import com.kinetic.sh.Template.Template;
import com.kinetic.sh.Template.TemplateUtils.TemplateUtils;
import com.kinetic.sh.Ui.MainActivity;
import com.kinetic.sh.Ui.PremiumFeatureActivity2;

import net.alhazmy13.mediapicker.Image.ImagePicker;
import net.alhazmy13.mediapicker.Video.VideoPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BackgroundPanel extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "BackgroundPanel";
    private AdapterDynamicBg adapter2;
    private AdapterGradients adapterGradient;

    public AdapterVideoPicker adapterVideoPicker;
    private LottieAnimationView animationView;
    private LottieAnimationView bgAnimationView;
    private RecyclerView bgRv;
    private BgUtils bgUtils;

    public BottomSheetBehavior bsbVideoPicker;
    private Chip chipDynamic;
    private Chip chipGradient;
    private ChipGroup chipGroup;
    private Chip chipImage;
    private Chip chipSolid;
    private Chip chipVideo;
    private List<CompMeta> compMetas;

    public Context context;
    private Boolean crashOnIlligalSatate = false;
    private View darkenLayer;

    public VideoDownloadDialog dialog;
    private List<DynamicBg> dynamicBgs;
    private LinearLayout dynamicContainer;
    private TextView exportButton;
    private FrameLayout frameLayout;

    public MainApplication global;

    public BottomSheetBehavior gradientBottomSheetBehavior;
    private LinearLayout gradientBottomSheetParent;
    private CardView gradientCard;
    private LinearLayout gradientCardBackground;
    private LinearLayout gradientContainer;

    public ImageBgFactory imageBgFactory;
    private LinearLayout imagePickerContainer;
    private ImageView imageView;
    private MaterialCardView libraryImagePicker;
    private CardView libraryVideoPicker;
    private final Boolean loading = true;

    public Snackbar loadingSandbar;
    private MaterialCardView localImagePicker;
    private CardView localVideoPicker;

    public MainActivity mainActivity;

    public Activity parent;

    public Pixabay pixabay;
    private TextView playButton;

    public int previousColor = -1;

    public String query = "Abstract";
    private RecyclerView rvGradient;
    private CardView solidCard;
    private LinearLayout solidContainer;
    private Template template;

    public TemplateUtils templateUtils;
    private SeekBar videoAlphaSeekbar;
    private LinearLayout videoBgContainer;

    public List<VideoMeta> videoMetas;

    public JSONObject videoObj;
    private LinearLayout videoPikerParent;

    public VideoView videoView;
    private ChipGroup vpChipGroup;

    public RecyclerView vpRv;

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.activity_main_background_panel, viewGroup, false);
    }

    public void onResume() {
        MainApplication mainApplication = this.global;
        if (mainApplication != null && mainApplication.getFromProActivity()) {
            this.global.setFromProActivity(false);
            if (this.bsbVideoPicker.getState() == 3 || this.bsbVideoPicker.getState() == 6) {
                setVideoPicker(this.query);
            }
        }
        if (this.crashOnIlligalSatate) {
            this.crashOnIlligalSatate = false;
            JSONObject jSONObject = this.videoObj;
            if (jSONObject != null) {
                String str = null;
                try {
                    str = jSONObject.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                videoDownloader(str, false);
            }
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.parent = getActivity();
        this.context = getContext();
        this.bgAnimationView = this.parent.findViewById(R.id.animation_view_bg);
        this.frameLayout = this.parent.findViewById(R.id.frameLayout2);
        this.rvGradient = this.parent.findViewById(R.id.rv_gradients);
        LinearLayout linearLayout = this.parent.findViewById(R.id.bottom_sheet_gradients);
        this.gradientBottomSheetParent = linearLayout;
        this.gradientBottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        this.videoView = this.parent.findViewById(R.id.video_view);
        this.videoPikerParent = this.parent.findViewById(R.id.bottom_sheet_video_picker);
        this.vpRv = this.parent.findViewById(R.id.bs_video_picker_rv);
        this.vpChipGroup = this.parent.findViewById(R.id.bs_video_picker_chip_group);
        this.exportButton = this.parent.findViewById(R.id.export_button);
        this.imageView = this.parent.findViewById(R.id.main_image_view);
        this.playButton = this.parent.findViewById(R.id.main_play_button);
        this.animationView = this.parent.findViewById(R.id.animation_view);
        this.darkenLayer = this.parent.findViewById(R.id.darken_layer);
        this.bgRv = view.findViewById(R.id.rvDynamicBgs);
        this.solidCard = view.findViewById(R.id.card_solid_color);
        this.gradientCard = view.findViewById(R.id.card_gradient);
        this.gradientCardBackground = view.findViewById(R.id.card_gradient_background);
        this.solidContainer = view.findViewById(R.id.solid_bg_container);
        this.gradientContainer = view.findViewById(R.id.gradient_bg_container);
        this.dynamicContainer = view.findViewById(R.id.dynamic_bg_container);
        this.chipGroup = view.findViewById(R.id.background_type_chip_group);
        this.chipDynamic = view.findViewById(R.id.chip_dynamic);
        this.chipSolid = view.findViewById(R.id.chip_solid);
        this.chipGradient = view.findViewById(R.id.chip_gradient);
        this.chipVideo = view.findViewById(R.id.chip_video);
        this.chipImage = view.findViewById(R.id.chip_image);
        this.videoBgContainer = view.findViewById(R.id.video_picker_container);
        this.libraryVideoPicker = view.findViewById(R.id.video_picker_library);
        this.localVideoPicker = view.findViewById(R.id.video_picker_local_storage);
        this.localImagePicker = view.findViewById(R.id.image_picker_local_storage);
        this.libraryImagePicker = view.findViewById(R.id.image_picker_library);
        this.videoAlphaSeekbar = view.findViewById(R.id.transparency_seekbar_for_video);
        this.imagePickerContainer = view.findViewById(R.id.image_picker_container);
        ArrayAdapter<CharSequence> createFromResource = ArrayAdapter.createFromResource(this.context, R.array.background_type, 17367048);
        this.bsbVideoPicker = BottomSheetBehavior.from(this.videoPikerParent);
        this.mainActivity = (MainActivity) this.context;
        this.global = (MainApplication) getActivity().getApplication();
        this.template = ((MainActivity) this.context).global.getTemplate();
        String[] strArr = {"Abstract", "Education", "Nature", "Flower", "Background", "city", "ocean", "summer"};
        for (int i = 0; i < 8; i++) {
            String str = strArr[i];
            Chip chip = new Chip(this.context, null, 2131886612);
            chip.setText(str);
            chip.setChipBackgroundColorResource(R.color.chip_select_color_light);
            chip.setTextColor(ContextCompat.getColor(this.context, R.color.white));
            chip.setCheckable(true);
            chip.setClickable(true);
            this.vpChipGroup.addView(chip);
        }
        this.vpChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                if (i != -1) {
                    BackgroundPanel.this.query = ((Chip) chipGroup.findViewById(chipGroup.getCheckedChipId())).getText().toString();
                    Log.i(BackgroundPanel.TAG, "onCheckedChanged: " + i + " " + BackgroundPanel.this.query.replace(" ", "%20"));
                    if (BackgroundPanel.this.pixabay != null) {
                        BackgroundPanel.this.pixabay.setPixabayListener(null);
                    }
                    BackgroundPanel backgroundPanel = BackgroundPanel.this;
                    backgroundPanel.setVideoPicker(backgroundPanel.query);
                }
            }
        });
        this.bsbVideoPicker.setHideable(true);
        this.bsbVideoPicker.setState(5);
        this.loadingSandbar = Snackbar.make(this.parent.findViewById(R.id.main_coordinator_layout), "loading video(s) , please wait..", -2);
        createFromResource.setDropDownViewResource(17367049);
        this.chipDynamic.setOnClickListener(this);
        this.chipSolid.setOnClickListener(this);
        this.chipGradient.setOnClickListener(this);
        this.chipVideo.setOnClickListener(this);
        this.chipImage.setOnClickListener(this);
        this.exportButton.setOnClickListener(this);
        this.libraryVideoPicker.setOnClickListener(this);
        this.localVideoPicker.setOnClickListener(this);
        this.playButton.setOnClickListener(this);
        this.libraryImagePicker.setOnClickListener(this);
        this.localImagePicker.setOnClickListener(this);
        this.videoAlphaSeekbar.setOnSeekBarChangeListener(this);
        setSolidBackground(0xff000000);
        ImageBgFactory imageBgFactory2 = new ImageBgFactory(this.context);
        this.imageBgFactory = imageBgFactory2;
        imageBgFactory2.onCreate();
        this.bgUtils = new BgUtils();
        this.templateUtils = new TemplateUtils();
        init();
        this.bgAnimationView.enableMergePathsForKitKatAndAbove(true);
        onBackgroundTypeSelect(R.id.chip_dynamic);
        _load();
        this.mainActivity.setMainListener(new MainListener() {
            public void onVideoPicked(String str) {
                if (BackgroundPanel.this.videoView.isPlaying()) {
                    BackgroundPanel.this.videoView.pause();
                }
                BackgroundPanel.this.mainActivity.currentVideoPath = str;
                BackgroundPanel.this.setBgVideo(str);
            }

            public void onImagePicked(String str) {
                BackgroundPanel.this.imageBgFactory.setImage(str);
            }
        });
    }


    public static float convertDpToPixel(float f, Context context2) {
        return f * (((float) context2.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    private void init() {
        prepareDynamicBG();
        prepareGradient();
        this.solidCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ColorPickerHelper colorPickerHelper = new ColorPickerHelper((Activity) BackgroundPanel.this.context);
                colorPickerHelper.setVisibility(true);
                if (BackgroundPanel.this.previousColor != -1) {
                    colorPickerHelper.setPreviousColor(BackgroundPanel.this.previousColor);
                }
                colorPickerHelper.setColorPickerListener(new ColorPickerHelper.ColorPickerListener() {
                    public void onColorChanged(int i) {
                        BackgroundPanel.this.setSolidBackground(i);
                        BackgroundPanel.this.previousColor = i;
                    }
                });
            }
        });
        this.gradientCardBackground.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BackgroundPanel.this.gradientBottomSheetBehavior.setState(3);
            }
        });
    }

    private void prepareDynamicBG() {
        this.dynamicBgs = new ArrayList<>();
        this.compMetas = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            CompMeta compMeta = new CompMeta();
            compMeta.setDynamicBg("Bg" + i);
            this.compMetas.add(compMeta);
        }
        this.adapter2 = new AdapterDynamicBg(this.compMetas);
        this.bgRv.setLayoutManager(new LinearLayoutManager(this.context, 0, false));
        this.bgRv.setAdapter(this.adapter2);
        this.adapter2.setInterAdapterDynamicBg(new AdapterDynamicBg.InterAdapterDynamicBg() {
            public void onClick(CompMeta compMeta, AdapterDynamicBg.ViewHolder viewHolder) {
                BackgroundPanel.this.setDynamicBg(compMeta.getDynamicBg());
            }
        });
    }


    public void setDynamicBg(String str) {
        this.mainActivity.currentVideoPath = null;
        this.videoView.setVisibility(View.INVISIBLE);
        this.imageView.setVisibility(View.INVISIBLE);
        this.frameLayout.setBackgroundColor(this.context.getColor(R.color.colorPrimaryLessLight));
        DynamicBg dynamicBg = new DynamicBg(this.context, str, this.bgAnimationView);
        this.animationView.playAnimation();
        startBgAnimation(dynamicBg);
    }


    public void setSolidBackground(int i) {
        this.mainActivity.currentVideoPath = null;
        this.videoView.setVisibility(View.INVISIBLE);
        this.imageView.setVisibility(View.INVISIBLE);
        this.bgAnimationView.setVisibility(View.INVISIBLE);
        ViewCompat.setBackgroundTintList(this.solidCard, ColorStateList.valueOf(i));
        this.template.setBgType(4);
        this.template.getSolidBgMeta().setColor(i);
        this.frameLayout.setBackgroundColor(i);
    }

    private void prepareGradient() {
        this.adapterGradient = new AdapterGradients(new Gradients(this.context).getGradients());
        this.rvGradient.setLayoutManager(new GridLayoutManager(this.context, 1));
        this.rvGradient.setAdapter(this.adapterGradient);
        this.adapterGradient.setGradientsInterface(new AdapterGradients.GradientsInterface() {
            public void onClick(GD gd) {
                BackgroundPanel.this.setGradient(gd);
            }
        });
    }


    public void setGradient(GD gd) {
        this.template.setBgType(5);
        this.template.getGradientBgMeta().setColors(gd.getClrs());
        this.mainActivity.currentVideoPath = null;
        this.bgAnimationView.setVisibility(View.INVISIBLE);
        this.videoView.setVisibility(View.INVISIBLE);
        this.imageView.setVisibility(View.VISIBLE);
        this.imageView.setImageDrawable(gd);
        this.gradientCardBackground.setBackground(gd);
    }


    public void setVideoPicker(String str) {
        Pixabay pixabay2 = new Pixabay();
        this.pixabay = pixabay2;
        pixabay2.getInitialData(this.context, str);
        this.vpRv.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        List<VideoMeta> list = this.videoMetas;
        if (list != null) {
            list.clear();
        }
        this.loadingSandbar.show();
        this.pixabay.setPixabayListener(new Pixabay.PixabayListener() {
            public void onDataLoaded(List<VideoMeta> list) {
                if (BackgroundPanel.this.videoMetas == null) {
                    BackgroundPanel.this.videoMetas = new ArrayList<>();
                    BackgroundPanel.this.videoMetas = list;
                    BackgroundPanel backgroundPanel = BackgroundPanel.this;
                    backgroundPanel.adapterVideoPicker = new AdapterVideoPicker(backgroundPanel.videoMetas);
                    BackgroundPanel.this.vpRv.setAdapter(BackgroundPanel.this.adapterVideoPicker);
                    BackgroundPanel.this.loadingSandbar.dismiss();
                    BackgroundPanel.this.adapterVideoPicker.setVideoPickerListener(new AdapterVideoPicker.videoPickerListener() {
                        public void onItemClick(final JSONObject jSONObject) {
                            if (new StoragePermission((Activity) BackgroundPanel.this.context).checkPermissions()) {
                                try {
                                    String string = jSONObject.getString("height");
                                    if (BackgroundPanel.this.global.getPremium() || Integer.parseInt(string) > 1080 || Integer.parseInt(string) < 720) {
                                        BackgroundPanel.this.videoObj = jSONObject;
                                        BackgroundPanel.this.videoDownloader(jSONObject.getString("url"), false);
                                        return;
                                    }
                                    BackgroundPanel.this.context.startActivity(new Intent(BackgroundPanel.this.context, PremiumFeatureActivity2.class));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                BackgroundPanel.this.mainActivity.setPermissionListener(new PermissionListener() {
                                    public void onStorageAccess() {
                                        try {
                                            String string = jSONObject.getString("height");
                                            if (Integer.parseInt(string) > 1080 || Integer.parseInt(string) < 720) {
                                                BackgroundPanel.this.videoObj = jSONObject;
                                                BackgroundPanel.this.videoDownloader(jSONObject.getString("url"), false);
                                                return;
                                            }
                                            BackgroundPanel.this.context.startActivity(new Intent(BackgroundPanel.this.context, PremiumFeatureActivity2.class));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }
                    });
                    return;
                }
                BackgroundPanel.this.videoMetas.addAll(list);
                BackgroundPanel.this.adapterVideoPicker.notifyDataSetChanged();
                BackgroundPanel.this.loadingSandbar.dismiss();
            }

            public void onError(VolleyError volleyError) {
                BackgroundPanel.this.loadingSandbar.dismiss();
                Snackbar.make(BackgroundPanel.this.parent.findViewById(R.id.main_coordinator_layout), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
        this.vpRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (!recyclerView.canScrollVertically(1) && !BackgroundPanel.this.loadingSandbar.isShown()) {
                    BackgroundPanel.this.loadingSandbar.show();
                    BackgroundPanel.this.pixabay.nextPage(BackgroundPanel.this.context);
                }
            }
        });
        this.bsbVideoPicker.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (5 == i && BackgroundPanel.this.loadingSandbar.isShown()) {
                    BackgroundPanel.this.loadingSandbar.dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View view, float f) {
                GradientDrawable gradientDrawable = new GradientDrawable();
                String string = BackgroundPanel.this.getResources().getString(R.color.colorPrimaryComet);
                gradientDrawable.setColor(Color.parseColor(string));
                gradientDrawable.setCornerRadius(BackgroundPanel.convertDpToPixel((1.0f - f) * 20.0f, BackgroundPanel.this.context));
                gradientDrawable.setStroke(2, Color.parseColor(string));
                view.setBackground(gradientDrawable);
            }
        });
    }

    private FragmentManager getHostFragmentManager() {
        FragmentManager fragmentManager = getFragmentManager();
        return (fragmentManager != null || !isAdded()) ? fragmentManager : Objects.requireNonNull(getActivity()).getSupportFragmentManager();
    }


    public void videoDownloader(String str, final Boolean bool) {
        try {
            VideoDownloadDialog videoDownloadDialog = new VideoDownloadDialog();
            this.dialog = videoDownloadDialog;
            videoDownloadDialog.show(getHostFragmentManager(), "video_download_process_from_video_picker");
            final VideoDownloadHelper videoDownloadHelper = new VideoDownloadHelper(this.context, str, "/" + this.context.getString(R.string.tf_filepath) + "/temp/");
            videoDownloadHelper.init();
            this.dialog.setVideoDownloadHelper(videoDownloadHelper);
            this.dialog.setCancelable(false);
            videoDownloadHelper.setVideoDownloadListener(new VideoDownloadHelper.VideoDownloadListener() {
                public void onProgress(int i, String str) {
                    BackgroundPanel.this.dialog.setProgress(i, str);
                }

                public void onDone() {
                    BackgroundPanel.this.mainActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                BackgroundPanel.this.setBgVideo(new File(videoDownloadHelper.getFileName()).getAbsolutePath());
                                if (!bool) {
                                    BackgroundPanel.this.bsbVideoPicker.setState(6);
                                } else {
                                    BackgroundPanel.this.templateUtils.cacheAssets();
                                }
                                BackgroundPanel.this.dialog.dismiss();
                            } catch (IllegalStateException unused) {
                                Toast.makeText(BackgroundPanel.this.context, "something went wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                public void onError(Error error) {
                    BackgroundPanel.this.dialog.onError(error);
                }
            });
        } catch (IllegalStateException unused) {
            this.crashOnIlligalSatate = true;
        }
    }


    public void setBgVideo(String str) {
        this.mainActivity.currentVideoPath = str;
        this.bgUtils._setBgType(this.context, 2);
        this.template.getVideoBgMeta().setLocalUrl(str);
        VideoView videoView2 = this.videoView;
        if (videoView2 != null) {
            videoView2.setOnPreparedListener(null);
        }
        this.animationView.setFrame(0);
        this.animationView.playAnimation();
        this.bgAnimationView.setVisibility(View.INVISIBLE);
        this.imageView.setVisibility(View.INVISIBLE);
        this.frameLayout.setBackgroundColor(0xff000000);
        this.videoView.setVisibility(View.VISIBLE);
        this.frameLayout.setBackgroundColor(0xff000000);
        this.mainActivity.currentVideoPath = str;
        this.videoView.setVideoPath(str);
        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setVolume(0.0f, 0.0f);
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });
    }

    private void startBgAnimation(DynamicBg dynamicBg) {
        try {
            this.bgAnimationView.setVisibility(View.VISIBLE);
            dynamicBg.init();
            dynamicBg.startAnimation();
        } catch (Exception e) {
            Context context2 = this.context;
            Toast.makeText(context2, "Background not found: " + dynamicBg.getClassName(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void onBackgroundTypeSelect(int i) {
        switch (i) {
            case R.id.chip_dynamic:
                this.darkenLayer.setAlpha(0.0f);
                this.chipDynamic.setChecked(true);
                this.imageView.setAlpha(1.0f);
                this.videoBgContainer.setVisibility(View.GONE);
                this.dynamicContainer.setVisibility(View.VISIBLE);
                this.gradientContainer.setVisibility(View.GONE);
                this.solidContainer.setVisibility(View.GONE);
                this.imagePickerContainer.setVisibility(View.GONE);
                return;
            case R.id.chip_gradient:
                this.darkenLayer.setAlpha(0.0f);
                this.chipGradient.setChecked(true);
                this.imageView.setAlpha(1.0f);
                this.videoBgContainer.setVisibility(View.GONE);
                this.dynamicContainer.setVisibility(View.GONE);
                this.gradientContainer.setVisibility(View.VISIBLE);
                this.solidContainer.setVisibility(View.GONE);
                this.imagePickerContainer.setVisibility(View.GONE);
                return;
            case R.id.chip_image:
                this.darkenLayer.setAlpha(0.0f);
                this.chipImage.setChecked(true);
                this.dynamicContainer.setVisibility(View.GONE);
                this.gradientContainer.setVisibility(View.GONE);
                this.solidContainer.setVisibility(View.GONE);
                this.videoBgContainer.setVisibility(View.GONE);
                this.imagePickerContainer.setVisibility(View.VISIBLE);
                return;
            case R.id.chip_solid:
                this.darkenLayer.setAlpha(0.0f);
                this.chipSolid.setChecked(true);
                this.imageView.setAlpha(1.0f);
                this.videoBgContainer.setVisibility(View.GONE);
                this.dynamicContainer.setVisibility(View.GONE);
                this.gradientContainer.setVisibility(View.GONE);
                this.solidContainer.setVisibility(View.VISIBLE);
                this.imagePickerContainer.setVisibility(View.GONE);
                return;
            case R.id.chip_video:
                this.chipVideo.setChecked(true);
                this.imageView.setAlpha(1.0f);
                this.dynamicContainer.setVisibility(View.GONE);
                this.gradientContainer.setVisibility(View.GONE);
                this.solidContainer.setVisibility(View.GONE);
                this.imagePickerContainer.setVisibility(View.GONE);
                this.videoBgContainer.setVisibility(View.VISIBLE);
                return;
            default:
                break;
        }
    }


    public void renderQue() {
        Boolean checkPermissions = new StoragePermission((Activity) this.context).checkPermissions();
        String string = PreferenceManager.getDefaultSharedPreferences(this.context).getString("software encoder", "");
        if (checkPermissions) {
            if (this.mainActivity.currentVideoPath != null) {
                this.videoView.setVideoPath(this.mainActivity.currentVideoPath);
                this.videoView.stopPlayback();
            }
            if (string.equals("hardware_encoder")) {
                MainActivity mainActivity2 = this.mainActivity;
                mainActivity2._bakeIntoMp4(mainActivity2.currentVideoPath);
                return;
            }
            MainActivity mainActivity3 = this.mainActivity;
            mainActivity3.bakeIntoMp4(mainActivity3.currentVideoPath);
            return;
        }
        this.mainActivity.setPermissionListener(new PermissionListener() {
            public void onStorageAccess() {
                BackgroundPanel.this.renderQue();
            }
        });
    }

    private void _load() {
        Template template2 = ((MainActivity) this.context).global.getcTemplate();
        if (template2 != null) {
            if (template2.getId() != null) {
                this.global.getTemplate().setId(template2.getId());
            }
            if (template2.getBgType() == 1 && template2.getDynamicBgMeta() != null) {
                onBackgroundTypeSelect(R.id.chip_dynamic);
                String camelCase = BgUtils.toCamelCase(template2.getDynamicBgMeta().getName().split("/", 2)[1]);
                if (camelCase != null) {
                    int parseInt = Integer.parseInt(camelCase.replaceAll("[^0-9]", "")) - 1;
                    this.bgRv.scrollToPosition(parseInt);
                    this.adapter2.setSelectedPosition(parseInt);
                    setDynamicBg(camelCase);
                }
            }
            if (template2.getBgType() == 2) {
                onBackgroundTypeSelect(R.id.chip_video);
                if (template2.getVideoBgMeta() != null) {
                    String localUrl = template2.getVideoBgMeta().getLocalUrl();
                    if (localUrl != null) {
                        File file = new File(localUrl);
                        if (file.isFile()) {
                            setBgVideo(file.getAbsolutePath());
                        } else {
                            String url = template2.getVideoBgMeta().getUrl();
                            if (url != null) {
                                videoDownloader(url, true);
                            }
                        }
                    } else {
                        String url2 = template2.getVideoBgMeta().getUrl();
                        Log.i(TAG, "_load: " + template2.makeJson());
                        if (url2 != null) {
                            videoDownloader(url2, true);
                        }
                    }
                    if (template2.getVideoBgMeta().getAlpha() != 0.0f) {
                        this.videoAlphaSeekbar.setProgress((int) template2.getVideoBgMeta().getAlpha());
                    }
                    Log.i(TAG, "_load: " + this.template.getId());
                }
            }
            if (template2.getBgType() == 3 && template2.getImageBgMeta() != null) {
                onBackgroundTypeSelect(R.id.chip_image);
                String localUrl2 = template2.getImageBgMeta().getLocalUrl();
                if (localUrl2 == null) {
                    String url3 = template2.getImageBgMeta().getUrl();
                    if (url3 != null) {
                        this.imageBgFactory.downloadAndLoadImage(url3);
                    }
                } else if (new File(localUrl2).isFile()) {
                    Log.i(TAG, "_load: image file exist");
                    this.imageBgFactory.setImage(localUrl2);
                } else {
                    Log.i(TAG, "_load: image file does not exist");
                    String url4 = template2.getImageBgMeta().getUrl();
                    if (url4 != null) {
                        this.imageBgFactory.downloadAndLoadImage(url4);
                    }
                }
                Log.i(TAG, "_load: " + template2.getImageBgMeta().getAlpha());
                this.imageBgFactory.setAlpha(template2.getImageBgMeta().getAlpha());
            }
            if (template2.getBgType() == 4) {
                onBackgroundTypeSelect(R.id.chip_solid);
                if (template2.getSolidBgMeta() != null) {
                    setSolidBackground(template2.getSolidBgMeta().getColor());
                    this.previousColor = template2.getSolidBgMeta().getColor();
                }
            }
            if (template2.getBgType() == 5) {
                onBackgroundTypeSelect(R.id.chip_gradient);
                if (template2.getGradientBgMeta() != null) {
                    int[] colors = template2.getGradientBgMeta().getColors();
                    GD gd = new GD(GradientDrawable.Orientation.LEFT_RIGHT, colors);
                    gd.setClrs(colors);
                    setGradient(gd);
                }
            }
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        onBackgroundTypeSelect(id);
        switch (id) {
            case R.id.export_button:
                renderQue();
                return;
            case R.id.image_picker_library:
                this.imageBgFactory.init();
                return;
            case R.id.image_picker_local_storage:
                new ImagePicker.Builder((Activity) this.context)
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.JPG)
                        .scale(600, 600)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
                return;
            case R.id.main_play_button:
                this.animationView.playAnimation();
                if (this.mainActivity.currentVideoPath != null) {
                    this.videoView.seekTo(0);
                    this.videoView.start();
                    return;
                }
                this.bgAnimationView.playAnimation();
                return;
            case R.id.video_picker_library:
                this.bsbVideoPicker.setState(6);
                this.loadingSandbar.show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (BackgroundPanel.this.query != null) {
                            BackgroundPanel backgroundPanel = BackgroundPanel.this;
                            backgroundPanel.setVideoPicker(backgroundPanel.query);
                            return;
                        }
                        BackgroundPanel.this.setVideoPicker("abstract");
                    }
                }, 1000);
                return;
            case R.id.video_picker_local_storage:
                new VideoPicker.Builder((MainActivity) this.context)
                        .mode(VideoPicker.Mode.GALLERY)
                        .directory(VideoPicker.Directory.DEFAULT)
                        .extension(VideoPicker.Extension.MP4)
                        .build();
                return;
            default:
                break;
        }
    }

    private void setVideoAlpha(float f) {
        float f2 = f / 100.0f;
        Log.i(TAG, "setVideoAlpha: " + f2);
        this.darkenLayer.setAlpha(Math.abs(f2));
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (seekBar.getId() == R.id.transparency_seekbar_for_video) {
            float f = (float) i;
            setVideoAlpha(f);
            this.template.getVideoBgMeta().setAlpha(f);
        }
    }
}
