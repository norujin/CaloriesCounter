package data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hoi.norujin.caloriescounter.FoodDetailsActivity;
import hoi.norujin.caloriescounter.R;
import model.Food;

public class CustomListviewAdapter extends ArrayAdapter<Food> {

    private int layoutrsc;
    private Activity activity;
    private ArrayList<Food> foodList = new ArrayList<>();


    public CustomListviewAdapter(Activity act, int resource, ArrayList<Food> data) {
        super(act, resource, data);

        layoutrsc = resource;
        activity = act;
        foodList = data;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Nullable
    @Override
    public Food getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public int getPosition(@Nullable Food item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View row = convertView;
        ViewHolder holder = null;

        if (row == null || (row.getTag()) == null)
        {
            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutrsc, null);
            holder = new ViewHolder();
            holder.foodname = (TextView) row.findViewById(R.id.food_name_list_id);
            holder.foodcals = (TextView) row.findViewById(R.id.food_cal_list_id);
            holder.fooddate = (TextView) row.findViewById(R.id.food_date_list_id);

            row.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }

        holder.food = getItem(position);

        holder.foodname.setText(holder.food.getFoodname());
        holder.foodcals.setText(String.valueOf(holder.food.getFoodcals()));
        holder.fooddate.setText(holder.food.getFoodrecdate());

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(activity, FoodDetailsActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("userobj",finalHolder.food);
                i.putExtras(mBundle);

                activity.startActivity(i);

            }
        });



        return row;
    }

    class ViewHolder
    {

        Food food;
        TextView foodname;
        TextView foodcals;
        TextView fooddate;


    }
}
