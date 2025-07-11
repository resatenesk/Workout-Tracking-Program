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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;

public class IEntityRepositoryBase<TEntity extends IEntity> implements IEntityRepository<TEntity> {

    private final Class<TEntity> clazz;

    public IEntityRepositoryBase(Class<TEntity> clazz) {
        this.clazz = clazz;
    }

    @Override
    public ObservableList<TEntity> GetAll(String sorgu) {
        ObservableList<TEntity> list = FXCollections.observableArrayList();
        try (Connection con = Database.connect()) {
            PreparedStatement ps = con.prepareStatement(sorgu);
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
    public void Add(TEntity entity) {
        try (Connection con = Database.connect()) {
            PreparedStatement ps = con.prepareStatement(entity.getInsertQuery());
            entity.fillInsertParameters(ps);
            int result = ps.executeUpdate();
            if (result > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("TRANSACTION SUCCESSFUL");
                alert.setHeaderText(null);
                alert.setContentText("User added succesfully");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("TRANSACTION FAILED");
                alert.setHeaderText(null);
                alert.setContentText("User could not added");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
            }
        } catch (SQLException ex) {
        }

    }

    @Override
    public void Delete(TEntity entity) {
        try (Connection con = Database.connect()) {
            PreparedStatement ps = con.prepareStatement(entity.getDeleteQuery());
            entity.fillUpdateParameters(ps);
            int result = ps.executeUpdate();
            if (result > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Başarılı!");
                alert.setHeaderText(null);
                alert.setContentText("Veriler başarıyla silindi.");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Başarısız!");
                alert.setHeaderText(null);
                alert.setContentText("Veriler silinemedi");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
            }
        } catch (SQLException ex) {
        }
    }

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
}
