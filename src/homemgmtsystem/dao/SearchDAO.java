package homemgmtsystem.dao;

import homemgmtsystem.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SearchDAO {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    boolean flag = false;
    Statement stmt;

    public SearchDAO() {
        try {
            con = new DBConnection().getConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    String mappedColumnNames[] = {
        "Entry Id",
        "Date",
        "Particular Id",
        "Particular",
        "Amount",
        "Remark"};

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

    public ResultSet searchDate(String date, String category, String amount, String entryid) {
        if (date != null) {
            String query = " SELECT entryid, date, pid, particular, amount, remark FROM transactiontbl WHERE date = '" + date + "' "
                    + "AND pid != 'null'";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (category != null) {
            String query = " SELECT entryid, date, pid, particular, amount, remark FROM transactiontbl WHERE particular = '" + category + "' "
                    + "AND pid != 'null' ";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (amount != null) {

            String query = " SELECT entryid, date, pid, particular, amount, remark FROM transactiontbl WHERE amount = '" + amount + "' "
                    + "AND pid != 'null'";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (entryid != null) {
            String query = " SELECT entryid, date, pid, particular, amount, remark FROM transactiontbl WHERE entryid = '" + entryid + "' "
                    + "AND pid != 'null' ";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No Record For The Inputted Value", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return rs;
    }
}
