package homemgmtsystem.dao;

import homemgmtsystem.db.DBConnection;
import homemgmtsystem.dto.DashboardDTO;
import homemgmtsystem.dto.EventMgmtDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class EventMgmtDAO {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    boolean flag = false;
    Statement stmt;

    // mapping the table of database 
    String mappedColumnNames[] = {
        "Event ID",
        "Entry Date",
        "Event Date",
        "Event Name",
        };

    public EventMgmtDAO() {
        try {
            con = new DBConnection().getConnection();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void Addevent(EventMgmtDTO edto) {
        try {
            String sql = "INSERT INTO eventmgmttbl VALUES(?,?,?,?,?)";
            pstmt = (PreparedStatement) con.prepareStatement(sql);
            pstmt.setInt(1, edto.getEventid());
            pstmt.setString(2, edto.getTodaydate());
            pstmt.setString(3, edto.getEventdate());
            pstmt.setString(4, edto.getEventname());
            pstmt.setInt(5, edto.getDiffdate());
            if (pstmt.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "Record Inserted", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public int eventId() {
        int eventid = 0;
        try {
            String query = " SELECT eventid FROM eventmgmttbl\n"
                    + "ORDER BY eventid DESC\n"
                    + "LIMIT 1 ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                eventid = rs.getInt(1);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return eventid;
    }

    public void countdays() {
        int count = 0;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT eventid, noofdays-1 FROM eventmgmttbl WHERE noofdays != 0");

            while (rs.next()) {
                int eventid = rs.getInt(1);
                count = rs.getInt(2);
                String sql = "UPDATE eventmgmttbl SET noofdays=? WHERE eventid=?";
                pstmt = (PreparedStatement) con.prepareStatement(sql);
                pstmt.setInt(1, count);
                pstmt.setInt(2, eventid);
                pstmt.executeUpdate();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public DashboardDTO editUser(JTable table) {
        DashboardDTO userEdit = new DashboardDTO();
        userEdit.setEntryid((int) table.getValueAt(table.getSelectedRow(), 0));
        userEdit.setDate((String) table.getValueAt(table.getSelectedRow(), 1));
        userEdit.setParticular((String) table.getValueAt(table.getSelectedRow(), 3));
        userEdit.setAmount(((double) table.getValueAt(table.getSelectedRow(), 4)));
        userEdit.setRemark((String) table.getValueAt(table.getSelectedRow(), 5));
        //ResultSet rs;
        return userEdit;
    }

    public void deleteRows(String[] value) throws SQLException {
        try {

            String query = " DELETE FROM eventmgmttbl WHERE eventid = ? ";
            //String reservedquery = "UPDATE particulartbl SET pname = 'reserved ' WHERE pid=?";

            pstmt = con.prepareStatement(query);

            pstmt.setString(1, value[0]);
            pstmt.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

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
        String query = "SELECT eventid, entrydate, eventdate, eventname FROM eventmgmttbl ";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return rs;
    }

    public static void main(String args[]) {
        new EventMgmtDAO().countdays();
    }
}
