package antrenmantakipcom.Entities.Concrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import antrenmantakipcom.Entities.Abstract.IEntity;

public class WorkoutTemplate implements IEntity {
    private int antrenman_id;
    private int user_id;
    private String username;
    private String antrenman_tipi;
    private int gun_sayisi;

    public WorkoutTemplate(int antrenman_id2, int user_id2, String username2, String antrenman_tipi2, int gun_sayisi2) {
        // TODO Auto-generated constructor stub
    }

    public int getAntrenmanID() {
        return this.antrenman_id;
    }

    public void setAntrenmanID(int antrenman_id) {
        this.antrenman_id = antrenman_id;
    }

    public int getUserID() {
        return this.user_id;
    }

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAntrenmanTipi() {
        return this.antrenman_tipi;
    }

    public void setAntrenmanTipi(String antrenman_tipi) {
        this.antrenman_tipi = antrenman_tipi;
    }

    public int getGunSayisi() {
        return this.gun_sayisi;
    }

    public void setGunSayisi(int gun_sayisi) {
        this.gun_sayisi = gun_sayisi;
    }

    @Override
    public IEntity fromResultSet(ResultSet rs) throws SQLException {
        WorkoutTemplate workout = new WorkoutTemplate(rs.getInt("antrenman_id"), rs.getInt("user_id"),
                rs.getString("username"), rs.getString("antrenman_tipi"), rs.getInt("gun_sayisi"));
        return workout;

    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO eklenen_antrenman_sablonlari (id,user_id,username,antrenman_tipi,gun_sayisi)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE eklenen_antrenman_sablonlari SET id=?,user_id=?,username=?,antrenman_tipi=?,gun_sayisi=?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM eklenen_antrenman_sablonlari WHERE id=?";
    }

    @Override
    public void fillInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setInt(1, antrenman_id);
        ps.setInt(2, user_id);
        ps.setString(3, username);
        ps.setString(4, antrenman_tipi);
        ps.setInt(5, gun_sayisi);
    }

    @Override
    public void fillUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setInt(1, antrenman_id);
    }

    @Override
    public void fillDeleteParameters(PreparedStatement ps) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillDeleteParameters'");
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
