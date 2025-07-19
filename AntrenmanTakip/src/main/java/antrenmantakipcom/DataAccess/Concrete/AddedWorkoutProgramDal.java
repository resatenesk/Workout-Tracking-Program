package antrenmantakipcom.DataAccess.Concrete;

import antrenmantakipcom.DataAccess.Abstract.IAddedWorkoutProgramDal;
import antrenmantakipcom.DataAccess.Abstract.IEntityRepositoryBase;
import antrenmantakipcom.Entities.Concrete.AddedWorkoutProgram;

public class AddedWorkoutProgramDal extends IEntityRepositoryBase<AddedWorkoutProgram> implements IAddedWorkoutProgramDal {

    public AddedWorkoutProgramDal(Class<AddedWorkoutProgram> clazz) {
        super(clazz);
        //TODO Auto-generated constructor stub
    }
    
}
