/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class ConexionBD {

    final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
    public String db = "mydb";
    public String url = "jdbc:mysql://192.168.0.2:3306/" + db;
    public String user = "admin";
    public String pass = "admin";

    public Connection Conectar() {
        Connection cn = null;
        try {
            Class.forName(JDBC_DRIVER);
            cn = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return cn;
    }

    public void close(Connection cn) {
        if (!cn.equals(null)) {
            try {
                cn.close();
            } catch (SQLException ex) {
                System.out.println("Error al desconectar " + ex);
            }
        }
    }

    public Object[][] select(Connection cn) {
        Statement s;
        ResultSet rs;

        /*try {
            s = cn.createStatement();
            rs = s.executeQuery("select * from practica");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getDate(2) + " " + rs.getDouble(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        Object obj[][] = null;
        try {
            s = cn.createStatement();
            rs = s.executeQuery("select * from practica");

            rs.last();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            int numFils = rs.getRow();
            obj = new Object[numFils][numCols];
            int j = 0;
            rs.beforeFirst();
            while (rs.next()) {
                for (int i = 0; i < numCols; i++) {
                    obj[j][i] = rs.getObject(i + 1);
                    System.out.println(obj[j][i]);
                }
                j++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

    public void createTable(Object[][] t, Connection cn, String sheet) throws SQLException {
        Statement s;
        ResultSet rs;
        PreparedStatement stmt = null;
        String sql;
        try {
            sql = "CREATE TABLE " + sheet + "(id INTEGER not NULL, ";
            for (int e = 0; e < t[0].length; e++) {
                sql += t[0][e] + " VARCHAR(255), ";
            }
            sql += " PRIMARY KEY ( id ))";
            System.out.println(sql);
            s = cn.createStatement();
            s.executeUpdate(sql);
            System.out.println("creada tabla");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            for (int i = 1; i < t.length; i++) {
                sql = "insert into " + db + "." + sheet + " values(" + i + ",";
                for (int j = 0; j < t[0].length; j++) {
                    if (j == t[0].length - 1) {
                        sql += "'" + (String) t[i][j] + "'";
                    } else {
                        sql += "'" + (String) t[i][j] + "',";
                    }
                    //stmt.setString(1, (String) t[i][j]);
                }
                sql += ")";
                System.out.println(sql);
                stmt = cn.prepareStatement(sql);
                int nRows = stmt.executeUpdate();
                System.out.println("record inserted");
            }
            rs = stmt.executeQuery("select * from emp");
            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }
            //cn.close();
        } catch (Exception e) {
            System.out.println("excepcion");
            System.out.println(e);
        }
    }
}
