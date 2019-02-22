package hoi.norujin.caloriescounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import data.CustomListviewAdapter;
import data.DatabaseHandler;
import model.Food;
import util.Utils;

public class DisplayFoodsActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private CustomListviewAdapter CustFoodAdpt;
    private ArrayList<Food> foodArrLst = new ArrayList<>();
    private Food myFood;

    private ListView foodlistview;
    private TextView totalfoods, totalcals;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods);

        foodlistview = (ListView) findViewById(R.id.food_listview_id);
        totalfoods = (TextView) findViewById(R.id.total_items_id);
        totalcals = (TextView) findViewById(R.id.total_cal_id);

        refreshdata();


    }

    private void refreshdata() {

        foodArrLst.clear();

        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Food> foodfromDB = dba.get_all_foods();

        int cals_values = dba.total_cals();
        int foods_amount = dba.get_total_items();

        String formatcals = Utils.formatNum(cals_values);
        String formatfoods = Utils.formatNum(foods_amount);

        totalcals.setText("Total Calories: "+ formatcals);
        totalfoods.setText("Foods Quantity: " + formatfoods);

        for (int i = 0; i < foodfromDB.size();i++)
        {


            String name = foodfromDB.get(i).getFoodname();
            int calos = foodfromDB.get(i).getFoodcals();
            String date = foodfromDB.get(i).getFoodrecdate();
            int foodid = foodfromDB.get(i).getFoodid();

            Log.v("foods id: ", String.valueOf(foodid));

            myFood = new Food();

            myFood.setFoodname(name);
            myFood.setFoodcals(calos);
            myFood.setFoodrecdate(date);
            myFood.setFoodid(foodid);

            foodArrLst.add(myFood);



        }

        dba.close();

        CustFoodAdpt = new CustomListviewAdapter(DisplayFoodsActivity.this, R.layout.list_row, foodArrLst);
        foodlistview.setAdapter(CustFoodAdpt);
        CustFoodAdpt.notifyDataSetChanged();

    }
}
