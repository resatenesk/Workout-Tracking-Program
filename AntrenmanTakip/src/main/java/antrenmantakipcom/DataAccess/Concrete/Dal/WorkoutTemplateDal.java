package antrenmantakipcom.DataAccess.Concrete.Dal;

import antrenmantakipcom.DataAccess.Abstract.IEntityRepositoryBase;
import antrenmantakipcom.DataAccess.Abstract.IWorkoutDal;
import antrenmantakipcom.Entities.Concrete.WorkoutTemplate;

public class WorkoutTemplateDal extends IEntityRepositoryBase<WorkoutTemplate> implements IWorkoutDal {

    public WorkoutTemplateDal(Class<WorkoutTemplate> clazz) {
        super(clazz);
    }
    
    
}
