package homemgmtsystem.dao;

import homemgmtsystem.db.DBConnection;
import homemgmtsystem.dto.CategoryDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CategoryDAO {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    boolean flag = false;
    Statement stmt;

    // mapping the table of database 
    String mappedColumnNames[] = {
        "Particular Id",
        "Particular Name",};

    public CategoryDAO() {
        try {
            con = new DBConnection().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void Addcat(CategoryDTO cdto) {
        try {
            String sql = "INSERT INTO particulartbl VALUES(?,?)";
            pstmt = (PreparedStatement) con.prepareStatement(sql);
            pstmt.setInt(1, cdto.getCatid());
            pstmt.setString(2, cdto.getCatname());
            

            if (pstmt.executeUpdate() == 1) {

                JOptionPane.showMessageDialog(null, "Record Inserted", "Home Management System", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    public int CatId() {
        int catid = 0;
        try {
            String query = " SELECT pid FROM particulartbl\n"
                    + "ORDER BY pid DESC\n"
                    + "LIMIT 1 ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                catid = rs.getInt("pid");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return catid;
    }

    public void deleteRows(String[] value) throws SQLException {
        try {

            String query = " DELETE FROM  particulartbl WHERE pid = ?";
            //String reservedquery = "UPDATE particulartbl SET pname = 'reserved ' WHERE pid=?";

            pstmt = con.prepareStatement(query);

            pstmt.setString(1, value[0]);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        pstmt.executeUpdate();
    }

    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();

        for (int column = 1; column <= columnCount; column++) {

            columnNames.add(mappedColumnNames[column - 1]);
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();

        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();

            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    public ResultSet getQueryResult() {
        String query = "SELECT  *FROM particulartbl";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return rs;
    }

    public String[] getColumnNames() {
        String[] columnNames = new String[3];
        try {
            String query = "SELECT pid, pname from particulartbl";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames[column - 1] = metaData.getColumnName(column);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return columnNames;
    }
}
