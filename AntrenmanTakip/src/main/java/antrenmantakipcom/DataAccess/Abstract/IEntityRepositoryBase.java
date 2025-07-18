package antrenmantakipcom.DataAccess.Abstract;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import antrenmantakipcom.DataAccess.Concrete.Database;
import antrenmantakipcom.Entities.Abstract.IEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IEntityRepositoryBase<TEntity extends IEntity> implements IEntityRepository<TEntity> {

    private final Class<TEntity> clazz;

    public IEntityRepositoryBase(Class<TEntity> clazz) {
        this.clazz = clazz;
    }

    @Override
    public ObservableList<TEntity> GetAll(String sorgu, Object... params) {
        ObservableList<TEntity> list = FXCollections.observableArrayList();
        try (Connection con = Database.connect()) {
            PreparedStatement ps = con.prepareStatement(sorgu);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try {
                    TEntity entity = clazz.getDeclaredConstructor().newInstance();
                    entity = (TEntity) entity.fromResultSet(rs);
                    list.add(entity);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public int Add(TEntity entity) {
        int result = 0;
        try (Connection con = Database.connect()) {
            PreparedStatement ps = con.prepareStatement(entity.getInsertQuery());
            entity.fillInsertParameters(ps);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
        }
        return result;

    }

    @Override
    public int Delete(TEntity entity,Object... params) {
        int result = 0;
        try (Connection con = Database.connect()) {
            PreparedStatement ps = con.prepareStatement(entity.getDeleteQuery());
          
             for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            result = ps.executeUpdate();

        } catch (SQLException ex) {
        }
        return result;
    }
/*
  @Override
    public void Update(TEntity entity) {

        try (Connection con = Database.connect()) {
            PreparedStatement ps = con.prepareStatement(entity.getUpdateQuery());
            entity.fillUpdateParameters(ps);
            int result = ps.executeUpdate();
            if (result > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Başarılı!");
                alert.setHeaderText(null);
                alert.setContentText("Veriler başarıyla Güncellendi.");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Başarısız!");
                alert.setHeaderText(null);
                alert.setContentText("Veriler güncellenemedi");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
 */
   

   

}
