package antrenmantakipcom.Entities.Concrete;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import antrenmantakipcom.Entities.Abstract.IEntity;

public class Records implements IEntity {
    private int id;
    private String username;
    private int antrenman_id;
    private int gun_no;
    private String hareket_adi;
    private int set_no;
    private double agirlik;
    private int tekrar;
    private Date tarih;

    public Records(int id, String username, int antrenman_id, int gun_no, String hareket_adi, int set_no,
            double agirlik,
            int tekrar, Date tarih) {
        this.id = id;
        this.username = username;
        this.antrenman_id = antrenman_id;
        this.gun_no = gun_no;
        this.hareket_adi = hareket_adi;
        this.set_no = set_no;
        this.agirlik = agirlik;
        this.tekrar = tekrar;
        this.tarih = tarih;
    }

    @Override
    public IEntity fromResultSet(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.username = rs.getString("username");
        this.antrenman_id = rs.getInt("antrenman_id");
        this.gun_no = rs.getInt("gun_no");
        this.hareket_adi = rs.getString("hareket_adi");
        this.set_no = rs.getInt("set_no");
        this.agirlik = rs.getDouble("agirlik");
        this.tekrar = rs.getInt("tekrar");
        this.tarih = rs.getDate("tarih");
        return this;

    }


    @Override
    public String getInsertQuery() {
        return "INSERT INTO kayitlar (username,antrenman_id,gun_no,hareket_adi,set_no,agirlik,tekrar,tarih) VALUES (?,?,?,?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUpdateQuery'");
    }

    @Override
    public String getDeleteQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeleteQuery'");
    }

    @Override
    public String getSelectIDQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSelectIDQuery'");
    }

    @Override
    public void fillInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, username);
        ps.setInt(2, antrenman_id);
        ps.setInt(3, gun_no);
        ps.setString(4, hareket_adi);
        ps.setInt(5, set_no);
        ps.setDouble(6, agirlik);
        ps.setInt(7, tekrar);
        ps.setDate(8, tarih);

    }


    @Override
    public void fillUpdateParameters(PreparedStatement ps) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillUpdateParameters'");
    }

    @Override
    public void fillDeleteParameters(PreparedStatement ps) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillDeleteParameters'");
    }

    @Override
    public void fillSelectIDParameters(PreparedStatement ps) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillSelectIDParameters'");
    }

}
