package antrenmantakipcom.DataAccess.Concrete.Dal;

import antrenmantakipcom.DataAccess.Abstract.IEntityRepositoryBase;
import antrenmantakipcom.DataAccess.Abstract.IMovementDal;
import antrenmantakipcom.Entities.Concrete.Movement;

public class MovementDal extends IEntityRepositoryBase<Movement> implements IMovementDal {

    public MovementDal(Class<Movement> clazz) {
        super(clazz);
     
    }
    
    
}
