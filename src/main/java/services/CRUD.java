package services;

import models.Person;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {

    void insertOne(String FirstName,String LastName , String Username, String email,String role,String password,int is_verified) throws SQLException;
    void updateOne(String FirstName, String LastName , String Username, String role, int PhoneNumber,int UserId) throws SQLException;
    void deleteOne(T t) throws SQLException;
    List<T> selectAll() throws SQLException;
    void selectOne(T t,int id);
}
