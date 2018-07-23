package com.artamonov.bakingapp;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "StepListActivity - onCreate ");
        setContentView(R.layout.activity_step_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = false;
        }

        RecyclerView recyclerView = findViewById(R.id.rv_step_list);
        Log.i(TAG, "StepListActivity - recyclerView: " + recyclerView);
        recyclerView.setAdapter(new StepRecyclerViewAdapter(StepListActivity.this,
                RecipesParser.stepsList, MainActivity.responseJSON));

    }

    private class StepRecyclerViewAdapter
            extends RecyclerView.Adapter<StepRecyclerViewAdapter.ViewHolder> {

        String json;
        private List<Recipes> stepsList;
        private Context context;

        public StepRecyclerViewAdapter(Context context, List<Recipes> stepsList, String json) {
            this.context = context;
            this.stepsList = stepsList;
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
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            Log.i(TAG, "onBindViewHolder - position: " + position);
            String stepShortDescription = null;
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
                        Log.i(TAG, "onClick: mTwoPane - true, position: " + holder.getAdapterPosition());
                        Bundle arguments = new Bundle();
                        arguments.putInt(StepDetailFragment.ARG_ITEM_ID, holder.getAdapterPosition());
                        StepDetailFragment fragment = new StepDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_detail_container, fragment)
                                .commit();
                    } else {
                        Intent i = getIntent();
                        Integer recipePosition = i.getIntExtra(StepDetailFragment.ARG_ITEM_ID, 0);
                        Log.i(TAG, "onClick: mTwoPane - false, position: " + holder.getAdapterPosition());
                        Intent intent = new Intent(context, StepDetailActivity.class);
                        intent.putExtra(StepDetailFragment.ARG_ITEM_ID, holder.getAdapterPosition());
                        intent.putExtra("recipe position", recipePosition);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return stepsList.size() + 1;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView stepShortDescription;

            public ViewHolder(View itemView) {
                super(itemView);
                stepShortDescription = itemView.findViewById(R.id.step_short_description);
            }
        }
    }

}
