package homemgmtsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DBConnection {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    boolean flag = false;
    Statement stmt;

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/homemgmt", "root", "nischal");

            System.out.println(con);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return con;
    }

    public DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/homemgmt", "root", "nischal");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean checkLogin(String username, String password, String usertype) {
        try {
            String query = "SELECT *FROM logintbl WHERE username = ? AND password=? AND usertype = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, usertype);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                flag = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return flag;
    }

    public boolean verifyLogin(String username, String password) {
        try {
            String query = "SELECT *FROM logintbl WHERE username = ? AND password=? ";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                flag = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return flag;
    }

    public void updateLogin(String username, String password, String currentusername) {
        try {
            String query = "UPDATE  userinfotbl  SET userinfousername = '" + username + "' , userinfopassword='" + password + "' "
                    + "WHERE userinfousername = '" + currentusername + "'";
            stmt = con.createStatement();

            if (stmt.executeUpdate(query) >= 1) {
                JOptionPane.showMessageDialog(null, "Username And Password Change Sucessfully", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Please Login Again To Make Changes", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public static void main(String[] args) throws SQLException {
        new DBConnection().getConnection();

    }
}
