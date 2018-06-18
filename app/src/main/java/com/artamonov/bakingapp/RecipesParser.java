package com.artamonov.bakingapp;

import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import com.artamonov.bakingapp.data.Recipes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.artamonov.bakingapp.MainActivity.TAG;


public class RecipesParser {

    private static Integer ingredientsQuantity;
    private static String ingredientsMeasure;
    private static String ingredientsName;
    private static Integer stepId;
    private static String stepShortDescription;
    private static String stepDescription;
    private static String stepVideoUrl;
    private static String stepThumbnailUrl;
    private static Integer recipeID;
    private static String recipeName;
    private static String recipeServings;

    public static List<Recipes> recipesList;
    public static List<Recipes> ingredientList;
    public static List<Recipes> stepsList;
    private static List<Recipes> recipesImagesList;


    public static void parseJSONRecipes(String json) {
        Log.i(TAG, "in parse function- " );
        recipesList = new ArrayList<>();
        ingredientList = new ArrayList<>();
        stepsList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            Log.i(TAG, "jsonArray.length - " + jsonArray.length() + " ");
            if (jsonArray.length() != 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonRecipeObject = jsonArray.getJSONObject(i);
                    Log.i(TAG, "onParse: recipeList " + jsonRecipeObject.toString() + " ");
                    System.out.println(jsonRecipeObject);
                    recipeID = jsonRecipeObject.optInt("id");
                    recipeName = jsonRecipeObject.optString("name");
                    recipeServings = jsonRecipeObject.optString("servings");
                    JSONArray jsonIngredientArray = jsonRecipeObject.getJSONArray("ingredients");

                    Recipes recipes = new Recipes();
                    recipes.setRecipeId(recipeID);
                    recipes.setRecipeName(recipeName);
                    recipes.setRecipeServings(recipeServings);
                    recipesList.add(recipes);
                    Log.i(TAG, "onParse: recipeList " + recipesList.get(i).getRecipeName() + " ");
                    Log.i(TAG, "onParse: recipeList " + recipesList.get(i).getRecipeId() + " ");
                    Log.i(TAG, "onParse: recipeList " + recipesList.get(i).getRecipeServings() + " ");

                  /*  for (int k = 0; k < jsonIngredientArray.length(); k++) {
                        JSONObject jsonIngredientObject = jsonIngredientArray.getJSONObject(k);
                        ingredientsQuantity = jsonIngredientObject.optInt("quantity");
                        ingredientsMeasure = jsonIngredientObject.optString("measure");
                        ingredientsName = jsonIngredientObject.optString("ingredient");

                        Recipes ingredients = new Recipes();
                        ingredients.setIngredientName(ingredientsName);
                        ingredients.setIngredientMeasure(ingredientsMeasure);
                        ingredients.setIngredientQuantity(ingredientsQuantity);
                        ingredientList.add(ingredients);

                        Log.i(TAG, "onParse: ingredientList " + ingredientList.get(k).getIngredientName() + " ");
                        Log.i(TAG, "onParse: ingredientList " + ingredientList.get(k).getIngredientMeasure() + " ");
                        Log.i(TAG, "onParse: ingredientList " + ingredientList.get(k).getIngredientQuantity() + " ");
                    }
                    JSONArray jsonStepsArray = jsonRecipeObject.getJSONArray("steps");
                    for (int j = 0; j < jsonStepsArray.length(); j++) {
                        JSONObject jsonStepsObject = jsonIngredientArray.getJSONObject(j);
                        stepId = jsonStepsObject.optInt("id");
                        stepShortDescription = jsonStepsObject.optString("shortDescription");
                        stepDescription = jsonStepsObject.optString("description");
                        stepVideoUrl = jsonStepsObject.optString("videoURL");
                        stepThumbnailUrl = jsonStepsObject.optString("thumbnailURL");

                        Recipes steps = new Recipes();
                        steps.setStepId(stepId);
                        steps.setStepShortDescription(stepShortDescription);
                        steps.setStepDescription(stepDescription);
                        steps.setStepThumbnailUrl(stepThumbnailUrl);
                        stepsList.add(steps);
                        Log.i(TAG, "onParse: ingredientList " + stepsList.get(j).getStepDescription() + " ");
                        Log.i(TAG, "onParse: ingredientList " + stepsList.get(j).getStepShortDescription() + " ");
                        Log.i(TAG, "onParse: ingredientList " + stepsList.get(j).getStepThumbnailUrl() + " ");
                        Log.i(TAG, "onParse: ingredientList " + stepsList.get(j).getStepId() + " ");
                    }*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



















/*public class RecipesParser {

    public static List<Recipes> parseJSONRecipes(String json) {

        List<Recipes> results = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            if (jsonArray.length() != 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonMovieObject = jsonArray.getJSONObject(i);
                    Integer recipeID = jsonMovieObject.optInt("id");
                    String recipeName = jsonMovieObject.optString("name");
                    String recipeServings = jsonMovieObject.optString("servings");

                    Recipes recipes = new Recipes();
                    recipes.setId(recipeID);
                    recipes.setName(recipeName);
                    recipes.setServings(recipeServings);

                    results.add(recipes);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;

    }

   */











   /* public static List<Ingredients> parseJSONIngredients(String json) {

        List<Ingredients> results = new ArrayList<>();

        try {
            JSONArray jsonRecipeArray = new JSONArray(json);
            if (jsonRecipeArray.length() != 0) {

                for (int i = 0; i < jsonRecipeArray.length(); i++) {
                    JSONArray jsonIngredientsArray = jsonRecipeArray.getJSONArray(i);
                    for (int k = 0; k < jsonIngredientsArray.length(); k++) {
                        JSONObject jsonMovieObject = jsonRecipeArray.getJSONObject(k);
                        Integer ingredientsQuantity = jsonMovieObject.optInt("quantity");
                        String ingredientsMeasure = jsonMovieObject.optString("measure");
                        String ingredientsName = jsonMovieObject.optString("ingredient");


                        Ingredients ingredients = new Ingredients();
                        ingredients.setName(ingredientsName);
                        ingredients.setMeasure(ingredientsMeasure);
                        ingredients.setQuantity(ingredientsQuantity);
                        results.add(ingredients);
                    }
                }

            } else {
                Log.i(TAG, "resultsJsonArray is empty");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }*/
}
