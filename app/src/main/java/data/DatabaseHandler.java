package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Food;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Food> foodList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table = "CREATE TABLE " + Constants.TABLE_NAME + " (" + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                +  Constants.FOOD_NAME + " TEXT, " + Constants.FOOD_CALs + " INTEGER, " + Constants.FOOD_RECDATE +" LONG);";

        db.execSQL(create_table);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);
    }

    public int get_total_items() {

        int items = 0;

        String query = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.rawQuery(query, null);
        items = cursor.getCount();
        cursor.close();

        return items;


    }

    public int total_cals()
    {
        int total_cals = 0;


        String query = "SELECT SUM ( " + Constants.FOOD_CALs + ") FROM " + Constants.TABLE_NAME;
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor= dba.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            total_cals=cursor.getInt(0);
        }

        cursor.close();
        dba.close();

        return total_cals;
    }

    public void delete_row (int row_id)
    {

        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME, Constants.KEY_ID + " =?",
                new String[]{String.valueOf(row_id)});

        dba.close();

    }

    public void add_food(Food food)
    {

        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.FOOD_NAME, food.getFoodname());
        values.put(Constants.FOOD_CALs, food.getFoodcals());
        values.put(Constants.FOOD_RECDATE, System.currentTimeMillis());

        dba.insert(Constants.TABLE_NAME,null, values);

        Log.v("Added food item", "yes!!");

        dba.close();


    }


    public ArrayList<Food> get_all_foods()
    {

        foodList.clear();


        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query(Constants.TABLE_NAME,
                new String[]{ Constants.KEY_ID, Constants.FOOD_NAME, Constants.FOOD_CALs, Constants.FOOD_RECDATE }
                , null, null, null, null, Constants.FOOD_RECDATE + " DESC ");

        if (cursor.moveToFirst())
        {
            do {

                Food food = new Food();
                food.setFoodname(cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                food.setFoodcals(cursor.getInt(cursor.getColumnIndex(Constants.FOOD_CALs)));
                food.setFoodid(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.FOOD_RECDATE))).getTime());

                food.setFoodrecdate(date);

                foodList.add(food);

            }
            while (cursor.moveToNext());
        }

        cursor.close();
        dba.close();


        return foodList;
    }


}

