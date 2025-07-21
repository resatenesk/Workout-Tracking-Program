package antrenmantakipcom.Entities.Concrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import antrenmantakipcom.Entities.Abstract.IEntity;

public class Movement implements IEntity {
    private int movementId;
    private String antrenman_tipi;
    private String movementCategory;
    private String hareket_adi;

    public Movement(int id,String tip,String category,String name){
        this.antrenman_tipi = tip;
        this.movementId = id;
        this.movementCategory = category;
        this.hareket_adi = hareket_adi;
    }
    public Movement(){}

    public String getAntrenman_tipi() {
        return this.antrenman_tipi;
    }

    public void setAntrenman_tipi(String antrenman_tipi) {
        this.antrenman_tipi = antrenman_tipi;
    }

    public int getMovementId() {
        return this.movementId;
    }

    public void setMovementId(int movementId) {
        this.movementId = movementId;
    }

    public String getMovementCategory() {
        return this.movementCategory;
    }

    public void setMovementCategory(String movementCategory) {
        this.movementCategory = movementCategory;
    }

    public String getHareket_adi() {
        return this.hareket_adi;
    }

    public void setHareket_adi(String hareket_adi) {
        this.hareket_adi = hareket_adi;
    }

    @Override
    public IEntity fromResultSet(ResultSet rs) throws SQLException {
        this.hareket_adi = rs.getString("hareket_adi");
        this.antrenman_tipi = rs.getString("antrenman_tipi");
        this.movementCategory = rs.getString("antrenman_tur_kategori");
        this.movementId = rs.getInt("id");
        return this;
    }

    @Override
    public String getInsertQuery() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInsertQuery'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillInsertParameters'");
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
