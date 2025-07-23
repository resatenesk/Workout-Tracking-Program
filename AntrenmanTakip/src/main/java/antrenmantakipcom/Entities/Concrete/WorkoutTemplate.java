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

    public WorkoutTemplate(int user_id, String username, String antrenman_tipi, int gun_sayisi) {
        this.user_id = user_id;
        this.username = username;
        this.antrenman_tipi = antrenman_tipi;
        this.gun_sayisi = gun_sayisi;
    }

    public WorkoutTemplate() {
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
    public String toString() {
        return "{" +
        ", antrenman_id :"+ getAntrenmanID() + " "+
                ", user_id='" + getUserID() + "'" +
                ", username='" + getUsername() + "'" +
                ", antrenman_tipi='" + getAntrenmanTipi() + "'" +
                ", gun_sayisi='" + getGunSayisi() + "'" +
                "}";
    }

    @Override
    public IEntity fromResultSet(ResultSet rs) throws SQLException {
       this.antrenman_id = rs.getInt("id");
       this.user_id = rs.getInt("user_id");
       this.username = rs.getString("username");
       this.antrenman_tipi = rs.getString("antrenman_tipi");
       this.gun_sayisi = rs.getInt("gun_sayisi");
       return this;

    }

   public String getInsertQuery() {
    return "INSERT INTO eklenen_antrenman_sablonlari (user_id, username, antrenman_tipi, gun_sayisi) VALUES (?, ?, ?, ?)";
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
        ps.setInt(1, user_id);
        ps.setString(2, username);
        ps.setString(3, antrenman_tipi);
        ps.setInt(4, gun_sayisi);
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
