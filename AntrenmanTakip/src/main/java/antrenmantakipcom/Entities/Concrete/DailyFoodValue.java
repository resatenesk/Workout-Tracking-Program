package antrenmantakipcom.Entities.Concrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import antrenmantakipcom.Entities.Abstract.IEntity;

public class DailyFoodValue implements IEntity {

    private int id;
    private int user_id;
    private float cal;
    private float fat;
    private float carb;
    private float prot;
    private LocalDate date;

    public DailyFoodValue(int user_id, float cal, float fat, float carb, float prot, LocalDate date) {
        this.user_id = user_id;
        this.cal = cal;
        this.fat = fat;
        this.carb = carb;
        this.prot = prot;
        this.date = date;
    }

    public DailyFoodValue() {
        // Boş kurucu gerektiğinde kullanılabilir
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public float getCal() {
        return cal;
    }

    public void setCal(float cal) {
        this.cal = cal;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCarb() {
        return carb;
    }

    public void setCarb(float carb) {
        this.carb = carb;
    }

    public float getProt() {
        return prot;
    }

    public void setProt(float prot) {
        this.prot = prot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Calorie: " + cal + " Fat: " + fat + " Carb: " + carb + " Prot: " + prot + " Date: " + date;
    }

    @Override
    public IEntity fromResultSet(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.user_id = rs.getInt("user_id");
        this.cal = rs.getFloat("calorie");
        this.fat = rs.getFloat("fat");
        this.carb = rs.getFloat("carb");
        this.prot = rs.getFloat("prot");
        this.date = rs.getDate("date").toLocalDate(); // Güvenli dönüşüm
        return this;
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO daily_food_values (user_id, calorie, fat, carb, prot, date) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    public void fillInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setInt(1, user_id);
        ps.setFloat(2, cal);
        ps.setFloat(3, fat);
        ps.setFloat(4, carb);
        ps.setFloat(5, prot);
        ps.setDate(6, java.sql.Date.valueOf(date));
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE daily_food_values SET calorie = ?, fat = ?, carb = ?, prot = ?, date = ? WHERE id = ?";
    }

    @Override
    public void fillUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setFloat(1, cal);
        ps.setFloat(2, fat);
        ps.setFloat(3, carb);
        ps.setFloat(4, prot);
        ps.setDate(5, java.sql.Date.valueOf(date));
        ps.setInt(6, id);
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM daily_food_values WHERE id = ?";
    }

    @Override
    public void fillDeleteParameters(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

    @Override
    public String getSelectIDQuery() {
        return "SELECT * FROM daily_food_values WHERE id = ?";
    }

    @Override
    public void fillSelectIDParameters(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }
}
