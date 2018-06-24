package com.artamonov.bakingapp.data;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artamonov.bakingapp.MainActivity;
import com.artamonov.bakingapp.R;
import com.artamonov.bakingapp.RecipesParser;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        RecyclerView recyclerView = findViewById(R.id.rv_step_list);
        recyclerView.setAdapter(new StepRecyclerViewAdapter(StepListActivity.this,
                RecipesParser.stepsList, MainActivity.responseJSON));

    }

    private static class StepRecyclerViewAdapter
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
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.i(TAG, "onBindViewHolder - position: " + position);
            if (position != 0) {
                String stepShortDescription = stepsList.get(position - 1).getStepShortDescription();
                String stepShortDescriptionWithId = String.format(context.getResources()
                        .getString(R.string.step_id), position, stepShortDescription);
                holder.stepShortDescription.setText(stepShortDescriptionWithId);

            } else {
                holder.stepShortDescription.setText(context.getResources()
                        .getString(R.string.ingredients));

            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
