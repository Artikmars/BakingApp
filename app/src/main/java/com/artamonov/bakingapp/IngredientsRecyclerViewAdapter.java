package com.artamonov.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artamonov.bakingapp.data.Recipes;

import java.util.List;

import static com.artamonov.bakingapp.MainActivity.TAG;

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.ViewHolder> {

    private List<Recipes> ingredientsList;
    private Context context;

    public IngredientsRecyclerViewAdapter(List<Recipes> ingredientsList, Context context) {
        this.ingredientsList = ingredientsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_content,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder - position: " + position);

        String ingredientName = ingredientsList.get(position).getIngredientName();
        String ingredientMeasure = ingredientsList.get(position).getIngredientMeasure();
        Integer ingredientQuantity = ingredientsList.get(position).getIngredientQuantity();
        String ingredientDescription = String.format(context
                        .getResources().getString(R.string.ingredient_description), ingredientName,
                ingredientQuantity, ingredientMeasure);
        holder.ingredientsDescription.setText(ingredientDescription);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount - ingredientsList.size(): " + ingredientsList.size());
        return ingredientsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ingredientsDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ingredientsDescription = itemView.findViewById(R.id.step_short_description);
        }
    }
}
