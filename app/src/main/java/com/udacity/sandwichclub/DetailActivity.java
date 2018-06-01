package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.get()
                .load(sandwich.getImage())
                .placeholder(R.drawable.load_image_placeholder)
                .error(R.drawable.load_image_error)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Binding TextViews in Detail Activity
        TextView alsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        TextView placeOfOrigin = (TextView) findViewById(R.id.origin_tv);
        TextView descriptionDetail = (TextView) findViewById(R.id.description_tv);
        TextView ingredientsDetail = (TextView) findViewById(R.id.ingredients_tv);
        String noDataLabel = "Sorry No Info Available !!";

        //Populate TextView in DetailActivity with Data from Sandwich Object
        if(sandwich.getPlaceOfOrigin().isEmpty())
            placeOfOrigin.setText(noDataLabel);
        else
            placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        if (sandwich.getDescription().isEmpty())
            descriptionDetail.setText(noDataLabel);
        else
            descriptionDetail.setText(sandwich.getDescription());

        if(sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnownAs.setText(noDataLabel);
        }
        else{
            StringBuilder alias = new StringBuilder();
            for( String value : sandwich.getAlsoKnownAs()){
                alias.append(value).append(", ");
            }
            alsoKnownAs.setText(alias);
        }

        if(sandwich.getIngredients().isEmpty()){
            ingredientsDetail.setText(noDataLabel);
        }
        else{
            StringBuilder ingredients = new StringBuilder();
            for( String value : sandwich.getIngredients()){
                ingredients.append(" -> ").append(value).append("\n\n");
            }
            ingredientsDetail.setText(ingredients);
        }

    }

}
