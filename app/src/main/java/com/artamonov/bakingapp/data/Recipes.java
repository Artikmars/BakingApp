package com.artamonov.bakingapp.data;

import android.graphics.drawable.Drawable;

import java.util.List;


public class Recipes {

    private Integer recipeId;
    private String recipeName;
    private String recipeServings;
    private Drawable recipeImage;


    private String ingredientName;
    private Integer ingredientQuantity;
    private String ingredientMeasure;

    private Integer stepId;
    private String stepShortDescription;
    private String stepDescription;
    private String stepVideoUrl;
    private List<Ingredients> ingredientsList;

    public Recipes(Drawable recipeImage) {
        this.recipeImage = recipeImage;
    }


    public Recipes() {

    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public void setIngredientsList(String ingredientsName, String ingredientsMeasure, Integer ingredientsQuantity) {
        this.ingredientName = ingredientsName;
        this.ingredientMeasure = ingredientsMeasure;
        this.ingredientQuantity = ingredientsQuantity;
    }

    public List<Steps> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    private List<Steps> stepsList;



    public String getStepThumbnailUrl() {
        return stepThumbnailUrl;
    }

    public void setStepThumbnailUrl(String stepThumbnailUrl) {
        this.stepThumbnailUrl = stepThumbnailUrl;
    }

    private String stepThumbnailUrl;

    public Drawable getStepThumbnail() {
        return stepThumbnail;
    }

    public void setStepThumbnail(Drawable stepThumbnail) {
        this.stepThumbnail = stepThumbnail;
    }

    private Drawable stepThumbnail;

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeServings() {
        return recipeServings;
    }

    public void setRecipeServings(String recipeServings) {
        this.recipeServings = recipeServings;
    }

    public Drawable getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(Drawable recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Integer getIngredientQuantity() {
        return ingredientQuantity;
    }

    public void setIngredientQuantity(Integer ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    public String getIngredientMeasure() {
        return ingredientMeasure;
    }

    public void setIngredientMeasure(String ingredientMeasure) {
        this.ingredientMeasure = ingredientMeasure;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public String getStepShortDescription() {
        return stepShortDescription;
    }

    public void setStepShortDescription(String stepShortDescription) {
        this.stepShortDescription = stepShortDescription;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public String getStepVideoUrl() {
        return stepVideoUrl;
    }

    public void setStepVideoUrl(String stepVideoUrl) {
        this.stepVideoUrl = stepVideoUrl;
    }



}
