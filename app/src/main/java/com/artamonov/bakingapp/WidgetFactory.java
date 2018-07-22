package com.artamonov.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.artamonov.bakingapp.data.Recipes;

import java.util.ArrayList;
import java.util.List;

import static com.artamonov.bakingapp.MainActivity.responseJSON;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    ArrayList<String> data;
    List<Recipes> widgetIngredientsList;
    Context context;
    Integer position;
  //  int widgetID;

    WidgetFactory(Context context, Intent intent) {
        this.context = context;
        // widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
        //         AppWidgetManager.INVALID_APPWIDGET_ID);
        widgetIngredientsList = RecipesParser.recipesList;
        position = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
    }

    @Override
    public void onCreate() {
        // data = new ArrayList<>();
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
        rView.setTextViewText(R.id.tvItemRecipe, widgetIngredientsList.get(position).getRecipeName());
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
        widgetIngredientsList.clear();
        RecipesParser.parseJSONIngredientsSteps(responseJSON, position);
        widgetIngredientsList = RecipesParser.recipesList;
    }

    @Override
    public void onDestroy() {

    }


}
