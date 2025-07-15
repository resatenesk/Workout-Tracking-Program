package antrenmantakipcom.Entities.Concrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import antrenmantakipcom.Entities.Abstract.IEntity;

public class Meal implements IEntity {
    private int meal_id;
    private int user_id;
    private String meal_name;
    private float total_cal;
    private float total_fat;
    private float total_carb;
    private float total_prot;

    public Meal(String meal_name2, float totalCal, float totalFat, float totalCarb, float totalProt) {
        this.meal_name = meal_name2;
        this.total_cal = totalCal;
        this.total_fat = totalFat;
        this.total_carb = totalCarb;
        this.total_prot = totalProt;
    }

    public Meal() {
    }

    public int getMealID() {
        return this.meal_id;
    }

    public String getMealName() {
        return meal_name;
    }

    public float getTotalCal() {
        return total_cal;
    }

    public float getTotalFat() {
        return total_fat;
    }

    public float getTotalCarb() {
        return total_carb;
    }

    public float getTotalProt() {
        return total_prot;
    }

    public void setMealName(String mealname) {
        this.meal_name = mealname;
    }

    public void setTotalCal(float total_cal) {
        this.total_cal = total_cal;
    }

    public void setTotalFat(float total_fat) {
        this.total_fat = total_fat;
    }

    public void setTotalCarb(float total_carb) {
        this.total_carb = total_carb;
    }

    public void setTotalProt(float total_prot) {
        this.total_prot = total_prot;
    }

    @Override
    public IEntity fromResultSet(ResultSet rs) throws SQLException {
        this.meal_id = rs.getInt("id");
        this.meal_name = rs.getString("meal_name");
        this.total_cal = rs.getFloat("total_cal");
        this.total_fat = rs.getFloat("total_fat");
        this.total_carb = rs.getFloat("total_carb");
        this.total_prot = rs.getFloat("total_prot");
        return this;

    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO saved_meals (meal_name,user_id,total_cal,total_fat,total_carb,total_prot) VALUES (?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE saved_meals SET meal_name = ?,total_cal = ?,total_fat = ? total_carb =?,total_prot= ? WHERE meal_id = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM saved_meals where id=?";
    }

    @Override
    public void fillInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, meal_name);
        ps.setFloat(2, total_cal);
        ps.setFloat(3, total_fat);
        ps.setFloat(4, total_carb);
        ps.setFloat(5, total_prot);
    }

    @Override
    public void fillUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, meal_name);
        ps.setFloat(2, total_cal);
        ps.setFloat(3, total_fat);
        ps.setFloat(4, total_carb);
        ps.setFloat(5, total_prot);
        ps.setInt(6, meal_id);
    }

    @Override
    public void fillDeleteParameters(PreparedStatement ps) throws SQLException {
        ps.setInt(1, meal_id);
    }

    @Override
    public String getSelectIDQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSelectIDQuery'");
    }

    @Override
    public void fillSelectIDParameters(PreparedStatement ps) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillSelectIDParameters'");
    }

}