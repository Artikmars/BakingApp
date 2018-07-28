package com.artamonov.bakingapp;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.artamonov.bakingapp.data.Recipes;

import java.util.ArrayList;
import java.util.List;

import static com.artamonov.bakingapp.MainActivity.responseJSON;

class RecipeWidgetFactory implements RemoteViewsFactory {

    private final Context context;
    private List<Recipes> widgetIngredientsList;

    RecipeWidgetFactory(Context context) {
        this.context = context;
        widgetIngredientsList = RecipesParser.ingredientList;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int getCount() {
        return widgetIngredientsList.size();

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rView = new RemoteViews(context.getPackageName(),
                R.layout.ingredients_widget_item);
        String ingredientName = widgetIngredientsList.get(position).getIngredientName();
        String ingredientMeasure = widgetIngredientsList.get(position).getIngredientMeasure();
        Integer ingredientQuantity = widgetIngredientsList.get(position).getIngredientQuantity();
        String ingredientDescription = String.format(context
                        .getResources().getString(R.string.ingredient_description), ingredientName,
                ingredientQuantity, ingredientMeasure);
        rView.setTextViewText(R.id.tvItemRecipe, ingredientDescription);
        return rView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        if (widgetIngredientsList != null) {
            widgetIngredientsList.clear();
        }
        RecipesParser.parseJSONIngredientsSteps(responseJSON, StepListActivity.recipePosition);
        widgetIngredientsList = RecipesParser.ingredientList;

    }

    @Override
    public void onDestroy() {

    }

}