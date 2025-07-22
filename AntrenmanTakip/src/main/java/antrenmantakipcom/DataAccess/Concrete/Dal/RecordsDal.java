package antrenmantakipcom.DataAccess.Concrete.Dal;

import antrenmantakipcom.DataAccess.Abstract.IEntityRepositoryBase;
import antrenmantakipcom.DataAccess.Abstract.IRecordsDal;
import antrenmantakipcom.Entities.Concrete.Records;
import javafx.scene.chart.NumberAxis;

public class RecordsDal extends IEntityRepositoryBase<Records> implements IRecordsDal {

    public RecordsDal(Class<Records> clazz) {
        super(clazz);
     
    }

    @Override
    public void CheckIF(String secilen_hareket,NumberAxis yEkseni) {
         if (secilen_hareket.equals("Bench Press")) {
                yEkseni.setLowerBound(60);
                yEkseni.setUpperBound(100);
                yEkseni.setTickUnit(5);
            }
            if (secilen_hareket.equals("Incline Dumbell Press")) {
                yEkseni.setLowerBound(20);
                yEkseni.setUpperBound(50);
                yEkseni.setTickUnit(2.5);
            }
            if (secilen_hareket.equals("High Cable Crossover")) {
                yEkseni.setLowerBound(15);
                yEkseni.setUpperBound(40);
                yEkseni.setTickUnit(5);
            }
            if (secilen_hareket.equals("Dumbell Shoulder Press")) {
                yEkseni.setLowerBound(15);
                yEkseni.setUpperBound(40);
                yEkseni.setTickUnit(2.5);
            }
            if (secilen_hareket.equals("Triceps Dips")) {
                yEkseni.setLowerBound(0);
                yEkseni.setUpperBound(15);
                yEkseni.setTickUnit(1);
            }
            if (secilen_hareket.equals("Lateral Raise")) {
                yEkseni.setLowerBound(0);
                yEkseni.setUpperBound(20);
                yEkseni.setTickUnit(2.5);
            }
            if (secilen_hareket.equals("Dumbell Skull Crusher")) {
                yEkseni.setLowerBound(0);
                yEkseni.setUpperBound(20);
                yEkseni.setTickUnit(2.5);
            }
       
    }
    
    
}
