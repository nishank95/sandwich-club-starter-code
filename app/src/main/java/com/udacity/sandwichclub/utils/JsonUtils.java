package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENT = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        List<String> ingredients = new ArrayList<String>();
        List<String> alsoKnownAsArray = new ArrayList<String>();

        try {
            //Creating Sandwich object from JSON string
            JSONObject sandwichObj = new JSONObject(json);

            //Getting JSON name Obj
            JSONObject nameObj = sandwichObj.getJSONObject(KEY_NAME);

            //Fetch mainName and Alias of Name Object
            String mainName = nameObj.optString(KEY_MAIN_NAME);
            //Fetch alsoKnownAs Array of Name Object
            JSONArray alsoKnownAs = nameObj.getJSONArray(KEY_ALSO_KNOW_AS);
            for (int size = 0; size < alsoKnownAs.length(); size++) {
                String alsoKnownAsString = alsoKnownAs.getString(size);

                //Add items in alsoKnownAs JSON Array into ArrayList<String>
                alsoKnownAsArray.add(alsoKnownAsString);
            }

            //Fetch placeOfOrigin of Sandwich Object
            String placeOfOrigin = sandwichObj.optString(KEY_PLACE_OF_ORIGIN);
            //Fetch description of Sandwich Object
            String description = sandwichObj.optString(KEY_DESCRIPTION);
            //Fetch image of Sandwich Object
            String image = sandwichObj.optString(KEY_IMAGE);

            //Fetch ingredientsArray of Sandwich Object
            JSONArray ingredientsArray = sandwichObj.getJSONArray(KEY_INGREDIENT);
            for (int size= 0; size < ingredientsArray.length(); size++){
                String ingredient_item = ingredientsArray.getString(size);

                //Add items in ingredients JSON Array into ArrayList<String>
                ingredients.add(ingredient_item);
            }

            //Return and Initialise newly created Sandwich object
            return new Sandwich(mainName,alsoKnownAsArray,placeOfOrigin,description,image,ingredients);
        }

        catch (JSONException e) {
           Log.d("JSON Parse Error","Error parsing the JSON data");
           e.printStackTrace();
        }

        return null;
    }
}
