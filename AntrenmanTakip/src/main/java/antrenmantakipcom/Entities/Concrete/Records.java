package antrenmantakipcom.Entities.Concrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import antrenmantakipcom.Entities.Abstract.IEntity;

public class Records implements IEntity {
    private int id;
    private int antrenman_id;
    private int user_id;
    private String username;
    private String antrenman_tipi;
    private int gun_sayisi;
    private int gun_no;
    private String hareket_adi;
    public Records(){

    }
    public Records(int id,int antrenman_id,int user_id,String username,String antrenman_tipi,int gun_sayisi,int gun_no,String hareket_adi){
        this.id = id;
        this.antrenman_id = antrenman_id;
        this.user_id = user_id;
        this.username = username;
        this.antrenman_tipi = antrenman_tipi;
        this.gun_sayisi = gun_sayisi;
        this.gun_no = gun_no;
        this.hareket_adi = hareket_adi;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAntrenman_id() {
        return this.antrenman_id;
    }

    public void setAntrenman_id(int antrenman_id) {
        this.antrenman_id = antrenman_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAntrenman_tipi() {
        return this.antrenman_tipi;
    }

    public void setAntrenman_tipi(String antrenman_tipi) {
        this.antrenman_tipi = antrenman_tipi;
    }

    public int getGun_sayisi() {
        return this.gun_sayisi;
    }

    public void setGun_sayisi(int gun_sayisi) {
        this.gun_sayisi = gun_sayisi;
    }

    public int getGun_no() {
        return this.gun_no;
    }

    public void setGun_no(int gun_no) {
        this.gun_no = gun_no;
    }

    public String getHareket_adi() {
        return this.hareket_adi;
    }

    public void setHareket_adi(String hareket_adi) {
        this.hareket_adi = hareket_adi;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", antrenman_id='" + getAntrenman_id() + "'" +
            ", user_id='" + getUser_id() + "'" +
            ", username='" + getUsername() + "'" +
            ", antrenman_tipi='" + getAntrenman_tipi() + "'" +
            ", gun_sayisi='" + getGun_sayisi() + "'" +
            ", gun_no='" + getGun_no() + "'" +
            ", hareket_adi='" + getHareket_adi() + "'" +
            "}";
    }
    @Override
    public IEntity fromResultSet(ResultSet rs) throws SQLException {
       Records records = new Records(rs.getInt("id"),rs.getInt("antrenman_id"),rs.getInt("user_id"),
       rs.getString("username"),rs.getString("antrenman_tipi"),rs.getInt("gun_sayisi"),rs.getInt("gun_no"),rs.getString("hareket_adi"));
       return records;
    }
    @Override
    public String getInsertQuery() {
        return "INSERT INTO eklenen_antrenman_programlari (id,antrenman_id,user_id,username,antrenman_tipi,gun_sayisi,gun_no,hareket_adi) VALUES (?,?,?,?,?,?,?,?)";
    }
    @Override
    public String getUpdateQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUpdateQuery'");
    }
    @Override
    public String getDeleteQuery() {
        return "DELETE FROM eklenen_antrenman_programlari WHERE antrenman_id = ?";
    }
    @Override
    public String getSelectIDQuery() {
        return "SELECT antrenman_id FROM eklenen_antrenman_programlari";
    }
    @Override
    public void fillInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);ps.setInt(2, antrenman_id);ps.setInt(3, user_id);ps.setString(4, username);
        ps.setString(5, antrenman_tipi);ps.setInt(6, gun_sayisi);ps.setInt(7, gun_no);ps.setString(8, hareket_adi);
    }
    @Override
    public void fillUpdateParameters(PreparedStatement ps) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillUpdateParameters'");
    }
    @Override
    public void fillDeleteParameters(PreparedStatement ps) throws SQLException {
        ps.setInt(1, antrenman_id);
    }
    @Override
    public void fillSelectIDParameters(PreparedStatement ps) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillSelectIDParameters'");
    }





}
