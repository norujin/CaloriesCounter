package hoi.norujin.caloriescounter;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class FoodDetailsActivity extends AppCompatActivity {

    private TextView food_dets_name, food_dets_date, food_dets_cals;
    private Button share,delete;
    private int food_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);


        food_dets_name = (TextView) findViewById(R.id.food_name_dets_id);
        food_dets_cals = (TextView) findViewById(R.id.food_cal_dets_id);
        food_dets_date = (TextView) findViewById(R.id.food_date_dets_id);

        share = (Button) findViewById(R.id.share_btn_id);
        delete = (Button) findViewById(R.id.delete_food_id);


        Food food = (Food) getIntent().getSerializableExtra("userobj");

        food_dets_name.setText(food.getFoodname());
        food_dets_cals.setText(String.valueOf(food.getFoodcals()));
        food_dets_date.setText(food.getFoodrecdate());
        food_id = food.getFoodid();


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                share_food_cal();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(FoodDetailsActivity.this);

                alert.setTitle("Delete!!");
                alert.setMessage("Are you sure you want to delete this item?");
                alert.setNegativeButton("No",null);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                        dba.delete_row(food_id);
                        Toast.makeText(getApplicationContext(),"Food item Deleted", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(FoodDetailsActivity.this, DisplayFoodsActivity.class));
                        FoodDetailsActivity.this.finish();

                    }
                });
                alert.show();
            }
        });

    }


    public void share_food_cal()
    {


        StringBuilder stringBuilder = new StringBuilder();

        String name = food_dets_name.getText().toString();
        String cals = food_dets_cals.getText().toString();
        String date = food_dets_date.getText().toString();

        stringBuilder.append(" Food: " + name + "\n");
        stringBuilder.append(" Calories: " + cals + "\n");
        stringBuilder.append(" Eaten on: " + date + "\n" );

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "My Caloric Intake");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());

        try {


            startActivity(Intent.createChooser(i, "Send Mail"));

        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(getApplicationContext(),"please install email client befor sending",Toast.LENGTH_LONG).show();
        }


    }

}
