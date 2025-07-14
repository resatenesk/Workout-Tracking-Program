package antrenmantakipcom.DataAccess.Abstract;

import javafx.collections.ObservableList;

public interface IEntityRepository<TEntity> {

    ObservableList<TEntity> GetAll(String sorgu,Object... params);

    int Add(TEntity entity);

    void Delete(TEntity entity);

    void Update(TEntity entity);

    int SelectUserID(TEntity entity);

}
