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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
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
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Step detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment implements View.OnClickListener, Player.EventListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_MODE = "orientation mode";
    public static final String ARG_ITEM_ID_LIST_SIZE = "step_list_size";
    public static final String ARG_RECIPE_POSITION = "recipe position";
    private static final String EXO_PLAYER_POSITION = "position";
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView playerView;
    private Integer stepPosition;
    private long exoPlayerPosition;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            stepPosition = getArguments().getInt(ARG_ITEM_ID);
        } else {
            stepPosition = getActivity().getIntent().getIntExtra(StepDetailFragment.ARG_ITEM_ID, 0);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        playerView = rootView.findViewById(R.id.playerView);
        TextView tvStepDescription = rootView.findViewById(R.id.tv_step_description);
        ImageView ivStepThumbnail = rootView.findViewById(R.id.ivStepThumbnail);
        RecyclerView rvIngredients = rootView.findViewById(R.id.rvIngredientsList);
        Integer recipePosition = getActivity().getIntent().getIntExtra("recipe position", 0);
        boolean isSteps = true;

        /**
         * Go to Ingredients item
         */

        if (stepPosition == 0) {
            isSteps = false;
            tvStepDescription.setVisibility(View.GONE);
            ivStepThumbnail.setImageDrawable(setIngredientsDrawable(recipePosition));
            ivStepThumbnail.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.INVISIBLE);
            IngredientsRecyclerViewAdapter adapter = new IngredientsRecyclerViewAdapter(RecipesParser.ingredientList, getContext());
            rvIngredients.setAdapter(adapter);
        }

        if (isSteps) {

            /**
             * stepPosition - step position from intent which was pressed
             * stepID - actual stepPosition due to the fact that first item inn recycler view is
             * Ingredients Item and not a step item
             */

            if (RecipesParser.stepsList != null) {
                Integer stepID = stepPosition - 1;
                if (stepID < 0) {
                    stepID = 0;
                }
                String stepDescription = RecipesParser.stepsList.get(stepID).getStepDescription();
                String stepVideoUrl = RecipesParser.stepsList.get(stepID).getStepVideoUrl();
                String stepThumbnailUrl = RecipesParser.stepsList.get(stepID).getStepThumbnailUrl();
                if (TextUtils.isEmpty(stepThumbnailUrl)) {
                    stepThumbnailUrl = null;
                }

                boolean isThumbnail = !TextUtils.isEmpty(stepThumbnailUrl);
                if (TextUtils.isEmpty(stepVideoUrl)) {
                    playerView.setVisibility(View.INVISIBLE);
                    Picasso.get()
                            .load(stepThumbnailUrl)
                            .placeholder(R.drawable.brownies)
                            .error(R.drawable.brownies)
                            .into(ivStepThumbnail);
                    if (isThumbnail) {
                        playerView.setVisibility(View.VISIBLE);
                        ivStepThumbnail.setVisibility(View.GONE);
                        initializePlayer(rootView, Uri.parse(stepThumbnailUrl), savedInstanceState);
                    }
                } else {
                    ivStepThumbnail.setVisibility(View.INVISIBLE);
                    initializePlayer(rootView, Uri.parse(stepVideoUrl), savedInstanceState);
                }

                tvStepDescription.setText(stepDescription);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Steps List is Empty", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            exoPlayerPosition = exoPlayer.getCurrentPosition();
            exoPlayer.release();
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(EXO_PLAYER_POSITION, exoPlayerPosition);
    }

    private void initializePlayer(View view, Uri url, Bundle savedInstanceState) {
        Context context = view.getContext();
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            playerView.setPlayer(exoPlayer);

            exoPlayerPosition = C.TIME_UNSET;
            if (savedInstanceState != null) {
                exoPlayerPosition = savedInstanceState.getLong(EXO_PLAYER_POSITION, C.TIME_UNSET);
                exoPlayer.seekTo(exoPlayerPosition);
            }

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
