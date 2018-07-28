package com.artamonov.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import static com.artamonov.bakingapp.StepDetailFragment.ARG_ITEM_ID;
import static com.artamonov.bakingapp.StepDetailFragment.ARG_ITEM_ID_LIST_SIZE;
import static com.artamonov.bakingapp.StepDetailFragment.ARG_RECIPE_POSITION;

/**
 * An activity representing a single Step detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StepListActivity}.
 */
public class StepDetailActivity extends AppCompatActivity implements View.OnClickListener, Player.EventListener {

    private static int recipePosition;
    private int stepPosition;
    private int stepListSize;

    @Override
    protected void onPause() {
        super.onPause();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(getApplicationContext(), RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lvAppWidget);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Log.i(MainActivity.TAG, "savedInstanceState == NULL! ");
            Intent intent = getIntent();

            recipePosition = intent.getIntExtra(ARG_RECIPE_POSITION, 0);
            stepPosition = intent.getIntExtra(ARG_ITEM_ID, 0);
            if (stepPosition == 0) {
                ImageView ivPreviousStep = findViewById(R.id.ivPreviousStep);
                ImageView ivNextStep = findViewById(R.id.ivNextStep);
                ivPreviousStep.setVisibility(View.INVISIBLE);
                ivNextStep.setVisibility(View.INVISIBLE);
            }
            stepListSize = intent.getIntExtra(ARG_ITEM_ID_LIST_SIZE, 0);
            Bundle arguments = new Bundle();
            arguments.putInt(ARG_ITEM_ID, stepPosition);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        } else {
            stepPosition = savedInstanceState.getInt(ARG_ITEM_ID, 0);
            stepListSize = savedInstanceState.getInt(ARG_ITEM_ID_LIST_SIZE, 0);
            recipePosition = savedInstanceState.getInt(ARG_RECIPE_POSITION, 0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_ITEM_ID, stepPosition);
        outState.putInt(ARG_ITEM_ID_LIST_SIZE, stepListSize);
        outState.putInt(ARG_RECIPE_POSITION, recipePosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, StepListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

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

    public void goToPreviousStep(View view) {
        Integer prevStepPosition = stepPosition - 1;
        if (prevStepPosition <= stepListSize && stepPosition != 1) {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(ARG_ITEM_ID, prevStepPosition);
            intent.putExtra(ARG_ITEM_ID_LIST_SIZE, stepListSize);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "This is the first step",
                    Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public void goToNextStep(View view) {
        Integer nextStepPosition = stepPosition + 1;
        if (nextStepPosition <= stepListSize) {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(ARG_ITEM_ID, nextStepPosition);
            intent.putExtra(ARG_ITEM_ID_LIST_SIZE, stepListSize);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "This is the last step",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
