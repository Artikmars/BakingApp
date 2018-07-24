package com.artamonov.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artamonov.bakingapp.data.Recipes;

import java.util.List;

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.ViewHolder> {

    private final List<Recipes> ingredientsList;
    private final Context context;

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
        return ingredientsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ingredientsDescription;

        private ViewHolder(View itemView) {
            super(itemView);
            ingredientsDescription = itemView.findViewById(R.id.step_short_description);
        }
    }
}
