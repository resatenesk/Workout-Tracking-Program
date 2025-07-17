package antrenmantakipcom.DataAccess.Abstract;

import antrenmantakipcom.Entities.Abstract.IEntity;
import antrenmantakipcom.Entities.Concrete.User;

public interface IUserDal extends IEntityRepository<User> {
    public int selectUserID(IEntity entity);
}
