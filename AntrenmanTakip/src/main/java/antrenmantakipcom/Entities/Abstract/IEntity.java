package antrenmantakipcom.Entities.Abstract;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IEntity {
    IEntity fromResultSet(ResultSet rs) throws SQLException;

    String getInsertQuery();

    String getUpdateQuery();

    String getDeleteQuery();

    void fillInsertParameters(PreparedStatement ps) throws SQLException;

    void fillUpdateParameters(PreparedStatement ps) throws SQLException;

    void fillDeleteParameters(PreparedStatement ps) throws SQLException;
}
