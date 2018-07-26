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

import static com.artamonov.bakingapp.MainActivity.TAG;


/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private int stepPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView)recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new StepRecyclerViewAdapter(this, RecipesParser.stepsList,
                MainActivity.responseJSON, mTwoPane));
    }



    private class StepRecyclerViewAdapter
            extends RecyclerView.Adapter<StepRecyclerViewAdapter.ViewHolder> {

        final String json;
        public final List<Recipes> stepsList;
        private final Context context;
        private final boolean mTwoPane;

        private StepRecyclerViewAdapter(Context context, List<Recipes> stepsList, String json, boolean twoPane) {
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
            stepPosition = position;

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
                        Bundle arguments = new Bundle();
                        arguments.putInt(StepDetailFragment.ARG_ITEM_ID, position);
                        arguments.putInt(StepDetailFragment.ARG_ITEM_ID, position);
                        StepDetailFragment fragment = new StepDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_detail_container, fragment)
                                .commit();
                    } else {
                        Intent intent = new Intent(context, StepDetailActivity.class);
                        intent.putExtra(StepDetailFragment.ARG_ITEM_ID, position);
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
