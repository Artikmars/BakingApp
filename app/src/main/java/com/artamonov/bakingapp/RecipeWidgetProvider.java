package com.artamonov.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import static com.artamonov.bakingapp.MainActivity.responseJSON;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    final String ACTION_ON_CLICK = "com.artamonov.bakingapp.RecipeWidgetProvider.itemonclick";
    final static String ITEM_POSITION = "item_position";
    public int widgetItemClickedPosition;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);
        setList(views, context, appWidgetId);
        setListClick(views, context);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void setListClick(RemoteViews views, Context context) {
        Intent listClickIntent = new Intent(context, RecipeWidgetProvider.class);
        listClickIntent.setAction(ACTION_ON_CLICK);
        PendingIntent listClickPIntent = PendingIntent.getBroadcast(context, 0,
                listClickIntent, 0);
        views.setPendingIntentTemplate(R.id.lvAppWidget, listClickPIntent);
    }

    private static void setList(RemoteViews views, Context context, int appWidgetId) {
        Intent adapter = new Intent(context, RecipeWidgetService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.lvAppWidget, adapter);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equalsIgnoreCase(ACTION_ON_CLICK)) {
            widgetItemClickedPosition = intent.getIntExtra(ITEM_POSITION, -1);
            Log.w(MainActivity.TAG, "onReceive: itemPos = " + widgetItemClickedPosition);
            if (widgetItemClickedPosition != -1) {
                RecipesParser.parseJSONIngredientsSteps(responseJSON, widgetItemClickedPosition);
                Intent ingredientsIntent = new Intent(context, StepListActivity.class);
                ingredientsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetItemClickedPosition);
                context.startActivity(ingredientsIntent);
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

