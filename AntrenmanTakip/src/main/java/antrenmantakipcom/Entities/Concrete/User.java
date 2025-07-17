package antrenmantakipcom.Entities.Concrete;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import antrenmantakipcom.Entities.Abstract.IEntity;

public class User implements IEntity {
    private int user_id;
    private String username;
    private String password;

    public User() {

    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public IEntity fromResultSet(ResultSet rs) {
        User user = new User();
        try {
            user.setUserId(rs.getInt("user_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            user.setUsername(rs.getString("username"));
        } catch (SQLException e) {
          
        }

        return user;
    }

    @Override
    public String toString() {
        return "{" +
                " user_id='" + getUserId() + "'" +
                ", username='" + getUsername() + "'" +
                ", password='" + getPassword() + "'" +
                "}";
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO users (username,password) VALUES (?,?)";
    }

    @Override
    public String getUpdateQuery() {

        return "UPDATE users SET username = ?,password = ? WHERE user_id=?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM users WHERE user_id = ?";
    }

    @Override
    public String getSelectIDQuery() {
        return "SELECT user_id FROM users WHERE username = ?";
    }

    @Override
    public void fillInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, username);
        ps.setString(2, password);
    }

    @Override
    public void fillUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setInt(3, user_id);
    }

    @Override
    public void fillDeleteParameters(PreparedStatement ps) throws SQLException {
        ps.setInt(1, user_id);
    }

    @Override
    public void fillSelectIDParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, username);
    }

}
