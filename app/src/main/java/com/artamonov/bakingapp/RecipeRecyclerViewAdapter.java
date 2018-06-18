package com.artamonov.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artamonov.bakingapp.data.Recipes;

import java.util.List;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private static ItemClickListener listener;
    private final List<Recipes> recipesList;
    private Context context;

    public RecipeRecyclerViewAdapter(Context context, List<Recipes> recipesList, ItemClickListener itemClickListener) {
        this.context = context;
        this.recipesList = recipesList;
        listener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_recipe_steps_list,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Recipes recipes = recipesList.get(position);
        viewHolder.imageView.setImageDrawable(recipes.getRecipeImage());
        viewHolder.textView.setText(recipes.getRecipeName());
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imageView;
        private final TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivRecipeImage);
            textView = itemView.findViewById(R.id.tvRecipeName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onItemClick(position);
        }
    }
}
