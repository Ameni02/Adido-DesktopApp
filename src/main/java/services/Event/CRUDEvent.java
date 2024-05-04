
package services.Event;

import java.sql.SQLException;
import java.util.List;

public interface CRUDEvent<T> {
    void insertOne(T var1) throws SQLException;

    void updateOne(T var1) throws SQLException;

    void deleteOne(T var1) throws SQLException;

    List<T> selectAll() throws SQLException;
}
