package antrenmantakipcom.DataAccess.Abstract;

import antrenmantakipcom.Entities.Concrete.Records;
import javafx.scene.chart.NumberAxis;

public interface IRecordsDal extends IEntityRepository<Records> {
    void CheckIF(String secilen_hareket,NumberAxis yEkseni);
}
