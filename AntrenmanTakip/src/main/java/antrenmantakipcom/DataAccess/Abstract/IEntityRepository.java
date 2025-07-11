package antrenmantakipcom.DataAccess.Abstract;

import javafx.collections.ObservableList;

public interface IEntityRepository<T> {
    ObservableList<T> GetAll(String sorgu);

    void Add(T entity);

    void Delete(T entity);

    void Update(T entity);
}
