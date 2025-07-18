package antrenmantakipcom.DataAccess.Concrete.Dal;

import antrenmantakipcom.DataAccess.Abstract.IEntityRepositoryBase;
import antrenmantakipcom.DataAccess.Abstract.IRecordsDal;
import antrenmantakipcom.Entities.Concrete.Records;

public class RecordsDal extends IEntityRepositoryBase<Records> implements IRecordsDal {

    public RecordsDal(Class<Records> clazz) {
        super(clazz);
        
    }
    
}
