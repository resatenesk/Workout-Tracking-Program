package antrenmantakipcom.DataAccess.Concrete.Dal;

import antrenmantakipcom.DataAccess.Abstract.IDailyFoodValueDal;
import antrenmantakipcom.DataAccess.Abstract.IEntityRepositoryBase;
import antrenmantakipcom.Entities.Concrete.DailyFoodValue;

public class DailyFoodValueDal extends IEntityRepositoryBase<DailyFoodValue> implements IDailyFoodValueDal {

    public DailyFoodValueDal(Class<DailyFoodValue> clazz) {
        super(clazz);
     
    }
    
    
}
