package antrenmantakipcom.DataAccess.Concrete.Dal;

import antrenmantakipcom.DataAccess.Abstract.IEntityRepositoryBase;
import antrenmantakipcom.DataAccess.Abstract.IMealDal;
import antrenmantakipcom.Entities.Concrete.Meal;

public class MealDal extends IEntityRepositoryBase<Meal> implements IMealDal  {

    public MealDal(Class<Meal> clazz) {
        super(clazz);
    
    }
    
}
