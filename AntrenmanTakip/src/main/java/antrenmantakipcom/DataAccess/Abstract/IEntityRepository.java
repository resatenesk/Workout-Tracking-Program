package antrenmantakipcom.DataAccess.Abstract;

import javafx.collections.ObservableList;

public interface IEntityRepository<TEntity> {

    ObservableList<TEntity> GetAll(String sorgu,Object... params);

    int Add(TEntity entity);

    int Delete(TEntity entity,Object... params);

    void Update(TEntity entity);

    int SelectUserID(TEntity entity);

}
