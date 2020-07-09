package com.artamonov.bakingapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artamonov.bakingapp.data.Recipes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private static ItemClickListener listener;
    private final List<Recipes> recipesList;

    public RecipeRecyclerViewAdapter(List<Recipes> recipesList, ItemClickListener itemClickListener) {
        this.recipesList = recipesList;
        listener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_list,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Recipes recipes = recipesList.get(position);
        String recipeThumbnail = recipes.getRecipeThumbnailUrl();
        if (TextUtils.isEmpty(recipeThumbnail)) {
            recipeThumbnail = null;
        }
        Picasso.get()
                .load(recipeThumbnail)
                .placeholder(recipes.getRecipeImage())
                .error(R.drawable.nutellapie)
                .into(viewHolder.imageView);
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
