package server.utility.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import server.utility.map.SpawnPoint;

public class Database {
   /* public static ArrayList<SpawnPoint> getSpawnPoints(){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        String url = "jdbc:mysql://localhost:3306/foobar";
        String user = "foobar_admin";
        String password = "f008ar2016";
        
        ArrayList<SpawnPoint> spawn_points = new ArrayList<SpawnPoint>();
        int spawn_points_size = 0;
        try {
            System.out.println("Attempting to connect to " + url + "..");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Made!");
            st = con.createStatement();
            
            rs = st.executeQuery("SELECT COUNT(*) FROM spawn_points");
            
            if(rs.next()){
                spawn_points_size = rs.getInt(1); 
            }
            rs = st.executeQuery("SELECT x, y, id FROM spawn_points");
            
            System.out.println("Query executed!");
            
            for(int i = 0; i<spawn_points_size; i++){
                if(rs.next()){
                    spawn_points.add(new SpawnPoint(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
                }
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return spawn_points;
    }*/
}