package com.artamonov.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.artamonov.bakingapp.data.Recipes;

import java.util.List;


/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity {

    public int stepPosition;
    public int stepListSize;
    public boolean mTwoPane;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(StepDetailFragment.ARG_ITEM_ID, stepPosition);
        outState.putInt(StepDetailFragment.ARG_ITEM_ID_LIST_SIZE, stepListSize);
        Log.e(MainActivity.TAG, "onSaveInstanceState: stepPosition: " + stepPosition
                + " stepListSize: " + stepListSize);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        stepListSize = RecipesParser.stepsList.size();

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            Log.e(MainActivity.TAG, "mTwoPane: " + true);
        }
        Log.e(MainActivity.TAG, "mTwoPane: " + mTwoPane);
        if (savedInstanceState != null) {
            stepPosition = savedInstanceState.getInt(StepDetailFragment.ARG_ITEM_ID, 0);
            stepListSize = savedInstanceState.getInt(StepDetailFragment.ARG_ITEM_ID_LIST_SIZE, 0);
            Log.e(MainActivity.TAG, "from savedInstanceState: stepPosition" + stepPosition
                    + " stepListSize: " + stepListSize);
        }

        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new StepRecyclerViewAdapter(this, RecipesParser.stepsList,
                MainActivity.responseJSON, mTwoPane));
    }

    public void goToNextStepTwoPane(View view) {
       Log.e(MainActivity.TAG, "goToNextStepTwoPane: stepPosition prev:" + stepPosition
              + " stepListSize: " + stepListSize);
        stepPosition = stepPosition + 1;
        Log.e(MainActivity.TAG, "goToNextStepTwoPane: stepPosition = stepPosition + 1; stepPosition:" + stepPosition
        );
        if (stepPosition <= stepListSize) {
            Bundle arguments = new Bundle();
            arguments.putInt(StepDetailFragment.ARG_ITEM_ID, stepPosition);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();
        } else {
            Toast.makeText(getApplicationContext(), "This is the last step",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


    public void goToPreviousStepTwoPane(View view) {
        Log.e(MainActivity.TAG, "goToPreviousStepTwoPane: stepPosition prev:" + stepPosition
                + " stepListSize: " + stepListSize);
        stepPosition = stepPosition - 1;
        Log.e(MainActivity.TAG, "goToPreviousStepTwoPane: stepPosition = stepPosition - 1; stepPosition:" + stepPosition
              );
        if (stepPosition <= stepListSize && stepPosition != 1) {
            Bundle arguments = new Bundle();
            arguments.putInt(StepDetailFragment.ARG_ITEM_ID, stepPosition);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();
        } else {
            Toast.makeText(getApplicationContext(), "This is the first step",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


    private class StepRecyclerViewAdapter
            extends RecyclerView.Adapter<StepRecyclerViewAdapter.ViewHolder> {

        public final List<Recipes> stepsList;
        final String json;
        private final Context context;
        private final boolean mTwoPane;

        StepRecyclerViewAdapter(Context context, List<Recipes> stepsList, String json, boolean twoPane) {
            this.context = context;
            this.stepsList = stepsList;
            this.mTwoPane = twoPane;
            this.json = json;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_list_content,
                            parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            String stepShortDescription;


            switch (position) {
                case 0:
                    holder.stepShortDescription.setText(context.getResources()
                            .getString(R.string.ingredients));
                    break;
                case 1:
                    stepShortDescription = stepsList.get(position - 1).getStepShortDescription();
                    holder.stepShortDescription.setText(stepShortDescription);
                    break;
                default:
                    String stepShortDescriptionWithId = String.format(context.getResources()
                            .getString(R.string.step_id), position - 1, stepsList.get(position - 1)
                            .getStepShortDescription());
                    holder.stepShortDescription.setText(stepShortDescriptionWithId);
                    break;
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTwoPane) {
                        //  holder.ivPreviousStep.setVisibility(View.INVISIBLE);
                        //  holder.ivNextStep.setVisibility(View.INVISIBLE);
                        Log.e(MainActivity.TAG, "onClick: stepPosition prev:" + stepPosition
                                + " stepListSize: " + stepListSize);
                        stepPosition = position;
                        Log.e(MainActivity.TAG, "onClick:  stepPosition = position; stepPosition:" + stepPosition
                              );
                        Bundle arguments = new Bundle();
                        arguments.putInt(StepDetailFragment.ARG_ITEM_ID, position);
                        //arguments.putBoolean(StepDetailFragment.ARG_ITEM_ID, true);
                        StepDetailFragment fragment = new StepDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_detail_container, fragment)
                                .commit();
                    } else {
                        // holder.ivPreviousStep.setVisibility(View.INVISIBLE);
                        //  holder.ivNextStep.setVisibility(View.INVISIBLE);
                        stepPosition = position;
                        Intent intent = new Intent(context, StepDetailActivity.class);
                        intent.putExtra(StepDetailFragment.ARG_ITEM_ID, position);
                        intent.putExtra(StepDetailFragment.ARG_MODE, true);
                        intent.putExtra(StepDetailFragment.ARG_ITEM_ID_LIST_SIZE, stepsList.size());
                        context.startActivity(intent);
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return stepsList.size() + 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView stepShortDescription;

            private ViewHolder(View itemView) {
                super(itemView);
                stepShortDescription = itemView.findViewById(R.id.step_short_description);
            }
        }
    }


}
