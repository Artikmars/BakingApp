package com.artamonov.bakingapp;

import com.artamonov.bakingapp.data.Recipes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


class RecipesParser {

    public static List<Recipes> recipesList;
    public static List<Recipes> ingredientList;
    public static List<Recipes> stepsList;

    public static void parseJSONRecipes(String json) {
        recipesList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            if (jsonArray.length() != 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonRecipeObject = jsonArray.getJSONObject(i);
                    System.out.println(jsonRecipeObject);
                    Integer recipeID = jsonRecipeObject.optInt("id");
                    String recipeName = jsonRecipeObject.optString("name");
                    String recipeServings = jsonRecipeObject.optString("servings");
                    String recipeThumbnailUrl = jsonRecipeObject.optString("image");

                    Recipes recipes = new Recipes();
                    recipes.setRecipeId(recipeID);
                    recipes.setRecipeName(recipeName);
                    recipes.setRecipeServings(recipeServings);
                    recipes.setRecipeThumbnailUrl(recipeThumbnailUrl);
                    recipesList.add(recipes);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void parseJSONIngredientsSteps(String json, Integer position) {

        stepsList = new ArrayList<>();
        ingredientList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonRecipeObject = jsonArray.getJSONObject(position);
            //Parsing ingredients array
            JSONArray jsonIngredientArray = jsonRecipeObject.getJSONArray("ingredients");
            for (int k = 0; k < jsonIngredientArray.length(); k++) {
                JSONObject jsonIngredientObject = jsonIngredientArray.getJSONObject(k);
                Integer ingredientsQuantity = jsonIngredientObject.optInt("quantity");
                String ingredientsMeasure = jsonIngredientObject.optString("measure");
                String ingredientsName = jsonIngredientObject.optString("ingredient");

                Recipes ingredients = new Recipes();
                ingredients.setIngredientName(ingredientsName);
                ingredients.setIngredientMeasure(ingredientsMeasure);
                ingredients.setIngredientQuantity(ingredientsQuantity);
                ingredientList.add(ingredients);
            }
            JSONArray jsonStepsArray = jsonRecipeObject.getJSONArray("steps");

            for (int j = 0; j < jsonStepsArray.length(); j++) {
                JSONObject jsonStepsObject = jsonStepsArray.getJSONObject(j);
                Integer stepId = jsonStepsObject.optInt("id");
                String stepShortDescription = jsonStepsObject.optString("shortDescription");
                String stepDescription = jsonStepsObject.optString("description");
                String stepVideoUrl = jsonStepsObject.optString("videoURL");
                String stepThumbnailUrl = jsonStepsObject.optString("thumbnailURL");

                Recipes steps = new Recipes();
                steps.setStepId(stepId);
                steps.setStepShortDescription(stepShortDescription);
                steps.setStepDescription(stepDescription);
                steps.setStepThumbnailUrl(stepThumbnailUrl);
                steps.setStepVideoUrl(stepVideoUrl);
                stepsList.add(steps);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
