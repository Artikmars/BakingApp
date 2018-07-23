package com.artamonov.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.artamonov.bakingapp.data.Recipes;

import java.util.ArrayList;
import java.util.List;

import static com.artamonov.bakingapp.MainActivity.responseJSON;

public class RecipeWidgetFactory implements RemoteViewsFactory {

    ArrayList<String> data;
    List<Recipes> widgetRecipesList;
   // List<Recipes> widgetIngredientsList;
    Context context;
    int widgetID;

    RecipeWidgetFactory(Context context, Intent intent) {
        this.context = context;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        widgetRecipesList = RecipesParser.recipesList;
       // widgetIngredientsList = RecipesParser.ingredientList;
    }

    @Override
    public void onCreate() {
        // data = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return widgetRecipesList.size();
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
        rView.setTextViewText(R.id.tvItemRecipe, widgetRecipesList.get(position).getRecipeName());
        Intent clickIntent = new Intent();
        clickIntent.putExtra(RecipeWidgetProvider.ITEM_POSITION, position);
        rView.setOnClickFillInIntent(R.id.tvItemRecipe, clickIntent);
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
        widgetRecipesList.clear();
        RecipesParser.parseJSONRecipes(responseJSON);
        widgetRecipesList = RecipesParser.recipesList;
    }

    @Override
    public void onDestroy() {

    }

}