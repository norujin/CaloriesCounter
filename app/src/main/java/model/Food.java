package model;

import java.io.Serializable;

public class Food implements Serializable {

    private String foodname, foodrecdate;
    private int foodcals, foodid;
    private static final long serialVersionUID = 10L;

    public Food (String food, int cals, String recdate, int id)
    {
        foodname = food;
        foodcals = cals;
        foodrecdate = recdate;
        foodid = id;
    }

    public Food()
    {

    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodrecdate() {
        return foodrecdate;
    }

    public void setFoodrecdate(String foodrecdate) {
        this.foodrecdate = foodrecdate;
    }

    public int getFoodcals() {
        return foodcals;
    }

    public void setFoodcals(int foodcals) {
        this.foodcals = foodcals;
    }

    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
