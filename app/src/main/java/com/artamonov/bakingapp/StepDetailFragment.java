package com.artamonov.bakingapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A fragment representing a single Step detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment implements View.OnClickListener, ExoPlayer.EventListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_BOTTOM_BAR_ITEM_ID = "bottom_view_item_id";
    SimpleExoPlayer exoPlayer;
    SimpleExoPlayerView playerView;
    Context context;
    private String stepDescription;
    private Integer stepID;
    private TextView tvStepDescription;
    private String stepThumbnailUrl;
    private Drawable stepThumbnail;
    private String stepVideoUrl;
    private ImageView ivStepThumbnail;
    private Integer stepIdFromIntent;
    private Integer stepIdFromBottomViewIntent;
    private RecyclerView rvIngredients;
    private Integer recipePosition;
    private View rootView = null;
    private String stepVideoUrl1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("step_id", stepIdFromIntent);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.step_detail, container, false);
        // RecyclerView recyclerView = rootView.findViewById(R.id.rv_step_list);
        playerView = rootView.findViewById(R.id.playerView);
        tvStepDescription = rootView.findViewById(R.id.tv_step_description);
        ivStepThumbnail = rootView.findViewById(R.id.ivStepThumbnail);
        rvIngredients = rootView.findViewById(R.id.rvIngredientsList);
        Log.w(MainActivity.TAG, "in StepDetailFragment: onCreateView");


        //  if (stepIdFromBottomViewIntent == -5) {
           /* Log.w(MainActivity.TAG, "in StepDetailFragment: stepIdFromIntent == -5 ");
            Intent i = getActivity().getIntent();
            stepIdFromIntent = i.getIntExtra(StepDetailFragment.ARG_ITEM_ID, 0);
            Log.w(MainActivity.TAG, "in StepDetailFragment: stepIdFromIntent from ARG_ITEM_ID "
                    + stepIdFromIntent);
            recipePosition = i.getIntExtra("recipe position", 0);*/
        //   } else {
        // stepIdFromIntent = stepIdFromBottomViewIntent;
        //   Log.w(MainActivity.TAG, "\"in StepDetailFragment: getIntent: stepIdFromIntent from " +
        //           " - ARG_BOTTOM_BAR_ITEM_ID - FINAL: " + stepIdFromIntent);
        //   }


        //    Intent intent = getActivity().getIntent();

        //  Log.w(MainActivity.TAG, "in StepDetailFragment: getIntent: intent - : " + intent);
        // stepIdFromBottomViewIntent = intent.getIntExtra(StepDetailFragment.ARG_BOTTOM_BAR_ITEM_ID, -5);
        //Log.w(MainActivity.TAG, "in StepDetailFragment: getIntent: stepIdFromBottomViewIntent from  - ARG_BOTTOM_BAR_ITEM_ID: "
        //        + stepIdFromBottomViewIntent);
        //   Log.w(MainActivity.TAG, "in StepDetailFragment: stepIdFromIntent == -5 ");

        if (savedInstanceState == null) {

            //Intent i = getActivity().getIntent();
            //  Log.w(MainActivity.TAG, "in StepDetailFragment: intent from ARG_BOTTOM_BAR_ITEM_ID "
            //           + i);
            //  stepIdFromIntent = getActivity().getIntent().getIntExtra(StepDetailFragment.ARG_BOTTOM_BAR_ITEM_ID, -1);
            //    Log.w(MainActivity.TAG, "in StepDetailFragment: stepIdFromIntent from ARG_BOTTOM_BAR_ITEM_ID "
            //            + stepIdFromIntent);
            //    if (stepIdFromIntent == -1) {
            // Log.w(MainActivity.TAG, "in StepDetailFragment: stepIdFromIntent from ARG_BOTTOM_BAR_ITEM_ID == -1 "
            //            + stepIdFromIntent);
            stepIdFromIntent = getActivity().getIntent().getIntExtra(StepDetailFragment.ARG_ITEM_ID, 0);
            Log.w(MainActivity.TAG, "in StepDetailFragment: stepIdFromIntent from ARG_ITEM_ID "
                    + stepIdFromIntent);
            recipePosition = getActivity().getIntent().getIntExtra("recipe position", 0);
            Log.w(MainActivity.TAG, "in StepDetailFragment: recipePosition "
                    + recipePosition);
        }
     //   }

        Log.w(MainActivity.TAG, "in StepDetailFragment: stepIdFromIntent: " + stepIdFromIntent);
        boolean isSteps = true;


       // Intent widgetIntent = getActivity().getIntent();
       // int widgetItemPosition = widgetIntent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

        // Go to Ingredients item
        if (stepIdFromIntent == 0 /*|| widgetItemPosition != -1*/) {
            isSteps = false;
            tvStepDescription.setVisibility(View.GONE);
            ivStepThumbnail.setImageDrawable(setIngredientsDrawable(recipePosition));
            ivStepThumbnail.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.INVISIBLE);
            Log.w(MainActivity.TAG, "in StepDetailFragment:  ivStepThumbnail.setImageDrawable: " +
                    RecipesParser.stepsList.get(stepIdFromIntent).getStepThumbnail());
            //  ivStepThumbnail.setImageDrawable(RecipesParser.recipesList.get(stepIdFromIntent).getStepThumbnail());

            IngredientsRecyclerViewAdapter adapter = new IngredientsRecyclerViewAdapter(RecipesParser.ingredientList, getContext());
            rvIngredients.setAdapter(adapter);
        }

        Log.i(MainActivity.TAG, "in StepDetailFragment: isSteps: " + isSteps);
        if (isSteps) {
            stepID = stepIdFromIntent - 1;
            Log.w(MainActivity.TAG, "in StepDetailFragment: stepID: " + stepID);
            Log.w(MainActivity.TAG, "in StepDetailFragment: stepsList.get(stepID): " + RecipesParser.stepsList
                    .get(stepID).toString());
            Log.w(MainActivity.TAG, "in StepDetailFragment: stepsList.get(stepID).getStepDescription: " + RecipesParser.stepsList
                    .get(stepID).getStepDescription());
            stepDescription = RecipesParser.stepsList.get(stepID).getStepDescription();

            Log.w(MainActivity.TAG, "in StepDetailFragment: stepsList.get(stepID).stepVideoUrl: " + RecipesParser.stepsList
                    .get(stepID).getStepVideoUrl());
            stepVideoUrl = RecipesParser.stepsList.get(stepID).getStepVideoUrl();
            stepThumbnailUrl = RecipesParser.stepsList.get(stepID).getStepThumbnailUrl();
            Log.w(MainActivity.TAG, "in StepDetailFragment: stepsList.get(stepID).getStepThumbnailUrl(): "
                    + RecipesParser.stepsList
                    .get(stepID).getStepThumbnailUrl());

            boolean isThumbnail = !TextUtils.isEmpty(stepThumbnailUrl);
            if (TextUtils.isEmpty(stepVideoUrl)) {
                Log.w(MainActivity.TAG, "in StepDetailFragment:stepVideoUrl is NULL");
                playerView.setVisibility(View.INVISIBLE);
                ivStepThumbnail.setImageDrawable(RecipesParser.stepsList.get(stepID).getStepThumbnail());
                if (isThumbnail) {
                    playerView.setVisibility(View.VISIBLE);
                    ivStepThumbnail.setVisibility(View.GONE);
                    initializePlayer(rootView, Uri.parse(stepThumbnailUrl));
                }
            } else {
                Log.w(MainActivity.TAG, "in StepDetailFragment: stepVideoUrl is NOT NULL and " +
                        "stepThumbnailUrl is NULL ");
                ivStepThumbnail.setVisibility(View.INVISIBLE);
                initializePlayer(rootView, Uri.parse(stepVideoUrl));
            }

            Log.w(MainActivity.TAG, "in StepDetailFragment: stepsList.get(stepID).stepThumbnailUrl: " + RecipesParser.stepsList
                    .get(stepID).getStepThumbnailUrl());

            tvStepDescription.setText(stepDescription);
            //initializePlayer(rootView, Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4"));
        }

     /*   BottomNavigationView bottomNavigationView =
                rootView.findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_previous_step:
                                Log.w(MainActivity.TAG, "in action_previous_step ");
                                // Bundle arguments = new Bundle();

                                stepIdFromIntent = stepIdFromIntent - 1;
                                // stepIdFromBottomViewIntent = stepIdFromIntent - 1;
                                //  stepIdFromIntent = stepIdFromIntent - 1;
                                Log.w(MainActivity.TAG, "in action_previous_step: stepIdFromIntent - 1 = "
                                        + stepIdFromIntent);

                                // arguments.putInt(StepDetailFragment.ARG_BOTTOM_BAR_ITEM_ID, previousStepID);
                                // StepDetailFragment currentFragment = new StepDetailFragment();
                                //  currentFragment.setArguments(arguments);
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.detach(StepDetailFragment.this).attach(StepDetailFragment.this)
                                        .commit();
                            case R.id.action_next_step:
                                //   Bundle argumentsNext = new Bundle();
                                //stepIdFromBottomViewIntent = stepIdFromIntent + 1;
                                //  Integer nextStepID = stepIdFromIntent + 1;
                                stepIdFromIntent = stepIdFromIntent + 1;
                                Log.w(MainActivity.TAG, "in action_next_step: stepIdFromIntent + 1 = "
                                        + stepIdFromIntent);
                                // stepIdFromIntent = stepIdFromIntent + 1;
                                //  argumentsNext.putInt(StepDetailFragment.ARG_BOTTOM_BAR_ITEM_ID, nextStepID);
                                //   StepDetailFragment fragmentNext = new StepDetailFragment();
                                // fragmentNext.setArguments(argumentsNext);
                                FragmentTransaction ftNext = getFragmentManager().beginTransaction();
                                ftNext.detach(StepDetailFragment.this).attach(StepDetailFragment.this)
                                        .commit();
                                // ftNext.detach(StepDetailFragment.this).attach(StepDetailFragment.this)
                                //        .commit();
                        }
                        return true;
                    }
                });
*/

        return rootView;
    }

    private void initializePlayer(View view, Uri url) {
        context = view.getContext();
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            playerView.setPlayer(exoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            exoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(context, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(url, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private Drawable setIngredientsDrawable(Integer recipePosition) {

        switch (recipePosition) {
            case 0:
                return ResourcesCompat.getDrawable(getResources(),
                        R.drawable.nutellapie, null);
            case 1:
                return ResourcesCompat.getDrawable(getResources(),
                        R.drawable.brownies, null);

            case 2:
                return ResourcesCompat.getDrawable(getResources(),
                        R.drawable.yellowcake, null);

            case 3:
                return ResourcesCompat.getDrawable(getResources(),
                        R.drawable.cheesecake, null);
            default:
                return null;
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray
            trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
