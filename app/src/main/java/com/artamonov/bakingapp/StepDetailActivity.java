package com.artamonov.bakingapp;

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

/**
 * An activity representing a single Step detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StepListActivity}.
 */
public class StepDetailActivity extends AppCompatActivity implements View.OnClickListener, Player.EventListener {

    private int stepPosition;
    private int stepListSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //   getSupportActionBar().setDisplayShowHomeEnabled(true);
        Log.i(MainActivity.TAG, "onCreate: stepPosition - " + stepPosition);
        Log.i(MainActivity.TAG, "onCreate: stepListSize - " + stepListSize);
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // SharedPreferences prefs = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        //  stepPosition = prefs.getInt(PREF_KEY, 0);
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Log.i(MainActivity.TAG, "savedInstanceState == NULL! ");
            Intent intent = getIntent();
            //  Boolean isTwoPane = intent.getBooleanExtra(ARG_MODE, false);
            //  if (isTwoPane) {
        /*    ImageView ivPreviousStepTwoPane = findViewById(R.id.ivPreviousStepTwoPane);
            ImageView ivNextStepTwoPane = findViewById(R.id.ivNextStepTwoPane);
            ivPreviousStepTwoPane.setVisibility(View.INVISIBLE);
            ivNextStepTwoPane.setVisibility(View.INVISIBLE);*/
            //   }
            stepPosition = intent.getIntExtra(ARG_ITEM_ID, 0);
            if (stepPosition == 0) {
                ImageView ivPreviousStep = findViewById(R.id.ivPreviousStep);
                ImageView ivNextStep = findViewById(R.id.ivNextStep);
                ivPreviousStep.setVisibility(View.INVISIBLE);
                ivNextStep.setVisibility(View.INVISIBLE);
            }
            Log.i(MainActivity.TAG, "get stepPosition from Intent: " + stepPosition);
            stepListSize = intent.getIntExtra(ARG_ITEM_ID_LIST_SIZE, 0);
            Log.i(MainActivity.TAG, "get stepListSize from Intent: " + stepListSize);
            Bundle arguments = new Bundle();
            arguments.putInt(ARG_ITEM_ID, stepPosition);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();

           /* SharedPreferences.Editor editor = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).edit();
            editor.putInt(PREF_KEY, stepPosition);
            editor.apply();*/
        } else {
            stepPosition = savedInstanceState.getInt(ARG_ITEM_ID, 0);
            stepListSize = savedInstanceState.getInt(ARG_ITEM_ID_LIST_SIZE, 0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_ITEM_ID, stepPosition);
        outState.putInt(ARG_ITEM_ID_LIST_SIZE, stepListSize);
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
     /*   Bundle arguments = new Bundle();
        Integer prevStepPosition = stepPosition - 1;
        if (prevStepPosition <= stepListSize) {
            arguments.putInt(StepDetailFragment.ARG_ITEM_ID, prevStepPosition);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        } else {
            Toast.makeText(getApplicationContext(), "This is the last step",
                    Toast.LENGTH_SHORT)
                    .show();
        }*/

        Log.i(MainActivity.TAG, "in goToPreviousStep");
        Log.i(MainActivity.TAG, "in goToPreviousStep: stepPosition: " + stepPosition);
        Integer prevStepPosition = stepPosition - 1;
        Log.i(MainActivity.TAG, "in goToPreviousStep: prevStepPosition: " + prevStepPosition);
        Log.i(MainActivity.TAG, "in goToPreviousStep: stepListSize: " + stepListSize);
        if (prevStepPosition <= stepListSize && stepPosition != 1) {
            Log.i(MainActivity.TAG, "prevStepPosition <= stepListSize");
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(ARG_ITEM_ID, prevStepPosition);
            intent.putExtra(ARG_ITEM_ID_LIST_SIZE, stepListSize);
            startActivity(intent);
        } else {
            Log.i(MainActivity.TAG, "prevStepPosition > stepListSize");
            Toast.makeText(getApplicationContext(), "This is the first step",
                    Toast.LENGTH_SHORT)
                    .show();
        }

    }


    public void goToNextStep(View view) {
        Log.i(MainActivity.TAG, "in goToNextStep");
        Log.i(MainActivity.TAG, "in goToNextStep: stepPosition: " + stepPosition);
        Integer nextStepPosition = stepPosition + 1;
        Log.i(MainActivity.TAG, "in goToNextStep: nextStepPosition: " + nextStepPosition);
        Log.i(MainActivity.TAG, "in goToNextStep: stepListSize: " + stepListSize);
        if (nextStepPosition <= stepListSize) {
            Log.i(MainActivity.TAG, "nextStepPosition <= stepListSize");
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(ARG_ITEM_ID, nextStepPosition);
            intent.putExtra(ARG_ITEM_ID_LIST_SIZE, stepListSize);
            startActivity(intent);
        } else {
            Log.i(MainActivity.TAG, "nextStepPosition > stepListSize");
            Toast.makeText(getApplicationContext(), "This is the last step",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
