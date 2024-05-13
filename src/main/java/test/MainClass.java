package test;

import utils.DBConnection;

import java.sql.SQLException;

class MainClass {
    private static Object Stage;

    public static void main(String[] args) throws SQLException {
       /* product p1 = new product("aaaa", "aaaaaaaa", 25, 50,50,true);

        Serviceproduct p = new Serviceproduct();
        p.insertOne(p1);
        System.out.println(p.selectAll());
        p.deleteOne(p1);
        p.updateOne(p1);
*/
        DBConnection c =DBConnection.getInstance();
    }
}



