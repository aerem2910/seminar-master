package notebook.util.mapper;

import notebook.model.User;

public interface Mapper<T> {
    String toInput(T entity);
    T toOutput(String str);
}