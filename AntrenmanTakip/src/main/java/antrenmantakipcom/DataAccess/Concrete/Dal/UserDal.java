package antrenmantakipcom.DataAccess.Concrete.Dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import antrenmantakipcom.DataAccess.Abstract.IEntityRepositoryBase;
import antrenmantakipcom.DataAccess.Abstract.IUserDal;
import antrenmantakipcom.DataAccess.Concrete.Database;
import antrenmantakipcom.Entities.Abstract.IEntity;
import antrenmantakipcom.Entities.Concrete.User;

public class UserDal extends IEntityRepositoryBase<User> implements IUserDal {
    public UserDal(Class<User> clazz) {
        super(clazz);

    }

    @Override
    public int selectUserID(IEntity entity) {

        int user_id = 0;
        try (Connection con = Database.connect()) {
            PreparedStatement ps = con.prepareStatement(entity.getSelectIDQuery());
            entity.fillSelectIDParameters(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = (User) entity.fromResultSet(rs);
                user_id = user.getUserId();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user_id;
    }
}
