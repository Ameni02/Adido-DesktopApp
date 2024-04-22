package services;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Person;
import utils.DBConnection;

import java.sql.*;

public class ServicePerson implements CRUD<Person>{

    private Connection cnx ;

    public ServicePerson() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(String FirstName, String LastName, String Username, String email, String role, String password, int is_verified) throws SQLException {
        Person user = new Person();
        String req = "INSERT INTO `user`(`first_Name`, `last_Name`, `username`,`email`, `roles`, `password`, `is_verified`) VALUES " +
                "('"+user.getFirst_name()+"','"+user.getLast_name()+"','"+user.getUsername()+"','"+user.getEmail()+"','"+user.getRole()+"','"+user.getPassword()+"','"+user.getIs_verified()+"')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Person Added !");
    }

    public void insertOne1(Person user) throws SQLException {
        String req = "INSERT INTO `user`(`first_Name`, `last_Name`, `username`,`email`, `roles`,`address`,`phone_number`,`password`,`is_verified`) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, user.getFirst_name());
        ps.setString(2, user.getLast_name());
        ps.setString(3, user.getUsername());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getRole());
        ps.setString(6, user.getAdress());
        ps.setInt(7, user.getPhone_number());
        ps.setString(8, user.getPassword());
        ps.setInt(9, user.getIs_verified());
        ps.executeUpdate();
    }

    @Override
    public void updateOne(String FirstName,String LastName , String Username, String role,int PhoneNumber,int UserId) throws SQLException {
        String query = "UPDATE user SET first_name = '"+FirstName+"',last_name = '"+LastName+"',username ='"+Username+"',roles = '"+role+"',phone_number = '"+PhoneNumber+"' WHERE iduser='"+UserId+"' ";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.executeUpdate(query);
    }

    @Override
    public void deleteOne(Person user) throws SQLException {
        String query = "DELETE FROM user WHERE iduser = ?";
try(
        PreparedStatement ps = cnx.prepareStatement(query);)
        {
        ps.setInt(1, user.getIduser());
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("No user found with ID: " + user.getIduser());
        }

    } catch (SQLException e) {
        System.err.println("Error deleting user: " + e.getMessage());
    }
    }

    @Override
    public ObservableList<Person> selectAll() {
        ObservableList<Person> personList = FXCollections.observableArrayList();

        String req = "SELECT * FROM `user`";
        try{
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            Person p = new Person();

            p.setIduser(rs.getInt(("iduser")));
            p.setFirst_name(rs.getString(("first_name")));
            p.setLast_name(rs.getString(("last_name")));
            p.setUsername(rs.getString(("username")));
            p.setEmail(rs.getString(("email")));
            p.setRole(rs.getString(("roles")));
            p.setAdress(rs.getString(("address")));
            p.setPhone_number(rs.getInt(("phone_number")));

            personList.add(p);
        }}catch (Exception e)
        {
            e.printStackTrace();
        }

        return personList;
    }
    @Override
    public void selectOne(Person user,int id){
        String checkData = "SELECT * FROM employee_info WHERE employee_id = '"
                + id+ "'";

        Statement st = null;
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(checkData);
            while (rs.next()) {

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
