package antrenmantakipcom.Entities.Concrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import antrenmantakipcom.Entities.Abstract.IEntity;

public class Food implements IEntity {
    private int food_id;
    private int user_id;
    private String foodName;
    private float calorie;
    private float fat;
    private float carb;
    private float prot;

    public Food(String foodName2, float calorie2, float fat2, float carb2, float prot2) {

        this.foodName = foodName2;
        this.calorie = calorie2;
        this.fat = fat2;
        this.carb = carb2;
        this.prot = prot2;
    }
    public Food(){}

    @Override
    public String toString() {
        return String.format("%s (cal: %.2f, fat: %.2f, carb: %.2f, prot: %.2f)",
                foodName, calorie, fat, carb, prot);
    }

    public int getFoodID() {
        return food_id;
    }

    public String getFoodName() {
        return foodName;
    }

    public float getCalorie() {
        return calorie;
    }

    public float getFat() {
        return fat;
    }

    public float getCarb() {
        return carb;
    }

    public float getProt() {
        return prot;
    }

    public void setFoodID(int id) {
        this.food_id = id;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setCalorie(float cal) {
        this.calorie = cal;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public void setCarb(float carb) {
        this.carb = carb;
    }

    public void setProt(float prot) {
        this.prot = prot;
    }

    private void setUserID(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public IEntity fromResultSet(ResultSet rs) throws SQLException {
        this.foodName = rs.getString("food_name");
        this.calorie = rs.getFloat("calorie");
        this.fat = rs.getFloat("fat");
        this.carb = rs.getFloat("carb");
        this.prot = rs.getFloat("prot");
        return this;
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO saved_special_foods (food_name,calorie,fat,carb,prot) VALUES (?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE saved_special_foods SET food_name=? ,calorie=?,fat=?,carb=?,prot=? WHERE food_id =?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM saved_special_foods WHERE food_id =?";
    }

    @Override
    public void fillInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, foodName);
        ps.setFloat(2, calorie);
        ps.setFloat(3, fat);
        ps.setFloat(4, carb);
        ps.setFloat(5, prot);
    }

    @Override
    public void fillUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, foodName);
        ps.setFloat(2, calorie);
        ps.setFloat(3, fat);
        ps.setFloat(4, carb);
        ps.setFloat(5, prot);
        ps.setInt(6, food_id);
    }

    @Override
    public void fillDeleteParameters(PreparedStatement ps) throws SQLException {
        ps.setInt(1, food_id);
    }

    @Override
    public String getSelectIDQuery() {
        return "SELECT id FROM saved_special_foods WHERE user_id = ?";
    }

    @Override
    public void fillSelectIDParameters(PreparedStatement ps) throws SQLException {
        ps.setInt(1, user_id);
    }

}
