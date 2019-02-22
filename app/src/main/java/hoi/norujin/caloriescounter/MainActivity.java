package hoi.norujin.caloriescounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {

    private EditText foodnametxt, foodcalstxt;
    private Button submitbtn;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(MainActivity.this);

        foodnametxt = (EditText) findViewById(R.id.food_name_id);
        foodcalstxt = (EditText) findViewById(R.id.food_cal_id);
        submitbtn = (Button) findViewById(R.id.submit_btn_id);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savefoodtoDB();

            }
        });


    }

    private void savefoodtoDB() {

        Food food = new Food();

        String food_name_m = foodnametxt.getText().toString().trim();
        String food_cals_m = foodcalstxt.getText().toString().trim();

        int cals = Integer.parseInt(food_cals_m);

        if (food_name_m.equals("") && food_cals_m.equals(""))
        {
            Toast.makeText(getApplicationContext(), "please enter food name & calories!!", Toast.LENGTH_LONG).show();
        }

        else if (food_name_m.equals(""))
        {
            Toast.makeText(getApplicationContext(), "please enter food name!!", Toast.LENGTH_LONG).show();
        }

        else  if (food_cals_m.equals(""))
        {
            Toast.makeText(getApplicationContext(), "please enter food calories!!", Toast.LENGTH_LONG).show();
        }

        else
        {

            food.setFoodname(food_name_m);
            food.setFoodcals(cals);

            dba.add_food(food);
            dba.close();

            foodnametxt.setText("");
            foodcalstxt.setText("");

            startActivity(new Intent(MainActivity.this, DisplayFoodsActivity.class));

        }

    }
}
