package com.artamonov.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.artamonov.bakingapp.data.Recipes;
import com.artamonov.bakingapp.data.StepDetailActivity;
import com.artamonov.bakingapp.data.StepDetailFragment;
import com.artamonov.bakingapp.data.StepListActivity;

import java.util.List;

import static com.artamonov.bakingapp.MainActivity.recipesList;
import static com.artamonov.bakingapp.MainActivity.responseJSON;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetProvider extends AppWidgetProvider {

    final String ACTION_ON_CLICK = "com.artamonov.bakingapp.WidgetProvider.itemonclick";
    final static String ITEM_POSITION = "item_position";
    public int widgetItemClickedPosition;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //  CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        /*Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.rv_appwidget, pendingIntent);*/
        setList(views, context, appWidgetId);
        setListClick(views, context);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void setListClick(RemoteViews views, Context context) {
        Intent listClickIntent = new Intent(context, WidgetProvider.class);
        listClickIntent.setAction(ACTION_ON_CLICK);
        PendingIntent listClickPIntent = PendingIntent.getBroadcast(context, 0,
                listClickIntent, 0);
        views.setPendingIntentTemplate(R.id.lvAppWidget, listClickPIntent);
    }

    private static void setList(RemoteViews views, Context context, int appWidgetId) {
        //RecipesParser.parseJSONRecipes(responseJSON);
        //  ArrayList<Recipes> recipesList = new ArrayList<>( RecipesParser.recipesList.size());
        //   recipesList.addAll(RecipesParser.recipesList);
        Intent adapter = new Intent(context, RecipeWidgetService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //  Bundle bundle = new Bundle();
        //   bundle.putParcelableArrayList("recipesList",  recipesList);
        // adapter.putExtras(bundle);

        views.setRemoteAdapter(R.id.lvAppWidget, adapter);
    }


    private static void setIngredientsList(RemoteViews views, Context context, Integer position, List<Recipes> list) {
        //RecipesParser.parseJSONRecipes(responseJSON);
        //  ArrayList<Recipes> recipesList = new ArrayList<>( RecipesParser.recipesList.size());
        //   recipesList.addAll(RecipesParser.recipesList);
        Intent adapter = new Intent(context, IngredientsWidgetService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, position);

      /*  Bundle bundle = new Bundle();
         bundle.putParcelableArrayList("recipesList",  recipesList);
        adapter.putExtras(bundle);*/

        views.setRemoteAdapter(R.id.lvAppWidget, adapter);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equalsIgnoreCase(ACTION_ON_CLICK)) {
            widgetItemClickedPosition = intent.getIntExtra(ITEM_POSITION, -1);
            Log.w(MainActivity.TAG, "onReceive: itemPos = " + widgetItemClickedPosition);
            if (widgetItemClickedPosition != -1) {
                //  Bundle bundle = new Bundle();
                // bundle.putParcelableArrayList("recipesList",  recipesList);
                // adapter.putExtras(bundle);


                RecipesParser.parseJSONIngredientsSteps(responseJSON, widgetItemClickedPosition);
                Intent ingredientsIntent = new Intent(context, StepListActivity.class);
                ingredientsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetItemClickedPosition);
                context.startActivity(ingredientsIntent);

              /*  RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);
                AppWidgetManager.getInstance(context).updateAppWidget(
                        new ComponentName(context, WidgetProvider.class),views);*/
                // setIngredientsList(views, context, itemPos, recipesList);
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

