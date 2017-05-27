package homemgmtsystem.db;

import homemgmtsystem.dto.BckRestoreDTO;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DBBck {

    Connection con;
    Statement stmt;
    ResultSet rs;

    public void transactiontblBck(BckRestoreDTO brd) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();
            String ext = ".csv";

            String query = "(SELECT 'entryid','date','pid','particular','amount','remark') \n"
                    + "UNION(SELECT entryid,date,pid,particular,amount,remark FROM transactiontbl WHERE pid != 'null' INTO OUTFILE"
                    + " '" + directory + "/" + fname + "" + ext + " ' \n"
                    + "FIELDS TERMINATED BY ',' ESCAPED BY '\' LINES TERMINATED BY '\r\n');";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {
                JOptionPane.showMessageDialog(null, "Backup Created Sucessfully " + fname + ext, "Home Management System", JOptionPane.INFORMATION_MESSAGE);

                int clicked = JOptionPane.showConfirmDialog(null, "View The Content?", "Home Management System", JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {

                    Runtime.getRuntime().exec("cmd /c start " + directory + "\\" + fname + "" + ext + "");

                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void transactiontblbckdate(BckRestoreDTO brd, String date) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();
            String ext = ".csv";

            String query = "(SELECT 'entryid','date','pid','particular','amount','remark') \n"
                    + "UNION(SELECT entryid,date,pid,particular,amount,remark FROM transactiontbl WHERE date = '" + date + "' AND pid != 'null'"
                    + " INTO OUTFILE"
                    + " '" + directory + "/" + fname + "" + ext + " ' \n"
                    + "FIELDS TERMINATED BY ',' ESCAPED BY '\' LINES TERMINATED BY '\r\n');";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {
                JOptionPane.showMessageDialog(null, "Backup Created Sucessfully " + fname + ext, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                int clicked = JOptionPane.showConfirmDialog(null, "View The Content?", "Home Management System", JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {
                    Runtime.getRuntime().exec("cmd /c start " + directory + "\\" + fname + "" + ext + " ");

                }
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void transactiontblbckparticular(BckRestoreDTO brd, String particular) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();
            String ext = ".csv";

            String query = "(SELECT 'entryid','date','pid','particular','amount','remark') \n"
                    + "UNION(SELECT entryid,date,pid,particular,amount,remark FROM transactiontbl WHERE particular = '" + particular + "' AND pid != 'null' "
                    + "INTO OUTFILE"
                    + " '" + directory + "/" + fname + "" + ext + " ' \n"
                    + "FIELDS TERMINATED BY ',' ESCAPED BY '\' LINES TERMINATED BY '\r\n');";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {
                JOptionPane.showMessageDialog(null, "Backup Created Sucessfully " + fname + ext, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                int clicked = JOptionPane.showConfirmDialog(null, "View The Content?", "Home Management System", JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {
                    Runtime.getRuntime().exec("cmd /c start " + directory + "\\" + fname + "" + ext + " ");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed To Create Backup " + fname + ext);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void transactiontblbckamount(BckRestoreDTO brd, String amount) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();
            String ext = ".csv";

            String query = "(SELECT 'entryid','date','pid','particular','amount','remark') \n"
                    + "UNION(SELECT entryid,date,pid,particular,amount,remark FROM transactiontbl WHERE amount = '" + amount + "'  AND pid != 'null' "
                    + "INTO OUTFILE"
                    + " '" + directory + "/" + fname + "" + ext + " ' \n"
                    + "FIELDS TERMINATED BY ',' ESCAPED BY '\' LINES TERMINATED BY '\r\n');";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {
                JOptionPane.showMessageDialog(null, "Backup Created Sucessfully " + fname + ext, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                int clicked = JOptionPane.showConfirmDialog(null, "View The Content?", "Home Management System", JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {
                    Runtime.getRuntime().exec("cmd /c start " + directory + "\\" + fname + "" + ext + " ");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed To Create Backup " + fname + ext);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void transactiontblbckentryid(BckRestoreDTO brd, String entryid) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();
            String ext = ".csv";

            String query = "(SELECT 'entryid','date','pid','particular','amount','remark') \n"
                    + "UNION(SELECT entryid,date,pid,particular,amount,remark FROM transactiontbl WHERE entryid = '" + entryid + "' AND pid != 'null' "
                    + "INTO OUTFILE"
                    + " '" + directory + "/" + fname + "" + ext + " ' \n"
                    + "FIELDS TERMINATED BY ',' ESCAPED BY '\' LINES TERMINATED BY '\r\n');";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {
                JOptionPane.showMessageDialog(null, "Backup Created Sucessfully " + fname + ext, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                int clicked = JOptionPane.showConfirmDialog(null, "View The Content?", "Home Management System", JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {
                    Runtime.getRuntime().exec("cmd /c start " + directory + "\\" + fname + "" + ext + " ");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed To Create Backup " + fname + ext);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void transactiontblbckquicksearch(BckRestoreDTO brd, String quicksearch) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();
            String ext = ".csv";

            String query = "(SELECT 'entryid','date','pid','particular','amount','remark') \n"
                    + "UNION(SELECT entryid,date,pid,particular,amount,remark FROM transactiontbl WHERE entryid = '" + quicksearch + "%' AND pid != 'null' "
                    + "INTO OUTFILE"
                    + " '" + directory + "/" + fname + "" + ext + " ' \n"
                    + "FIELDS TERMINATED BY ',' ESCAPED BY '\' LINES TERMINATED BY '\r\n');";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {
                JOptionPane.showMessageDialog(null, "Backup Created Sucessfully " + fname + ext, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                int clicked = JOptionPane.showConfirmDialog(null, "View The Content?", "Home Management System", JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {
                    Runtime.getRuntime().exec("cmd /c start " + directory + "\\" + fname + "" + ext + " ");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed To Create Backup " + fname + ext);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void particulartblbck(BckRestoreDTO brd) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();
            String ext = ".csv";

            String query = "(SELECT 'pid','pname') \n"
                    + "UNION(SELECT pid, pname FROM particulartbl INTO OUTFILE"
                    + " '" + directory + "/" + fname + "" + ext + " ' \n"
                    + "FIELDS TERMINATED BY ',' ESCAPED BY '\' LINES TERMINATED BY '\r\n');";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {
                JOptionPane.showMessageDialog(null, "Backup Created Sucessfully " + fname + ext, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                int clicked = JOptionPane.showConfirmDialog(null, "View The Content?", "Home Management System", JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {
                    Runtime.getRuntime().exec("cmd /c start " + directory + "\\" + fname + "" + ext + " ");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed To Create Backup " + fname + ext);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void eventmgmtbck(BckRestoreDTO brd) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();
            String ext = ".csv";

            String query = "(SELECT 'eventid','entrydate','eventdate','eventname','noofdays') \n"
                    + "UNION(SELECT eventid,entrydate,eventdate,eventname,noofdays FROM eventmgmttbl "
                    + "INTO OUTFILE"
                    + " '" + directory + "/" + fname + "" + ext + " ' \n"
                    + "FIELDS TERMINATED BY ',' ESCAPED BY '\' LINES TERMINATED BY '\r\n');";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {
                JOptionPane.showMessageDialog(null, "Backup Created Sucessfully " + fname + ext, "Home Management System", JOptionPane.INFORMATION_MESSAGE);

                int clicked = JOptionPane.showConfirmDialog(null, "View The Content?", "Home Management System", JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {
                    Runtime.getRuntime().exec("cmd /c start " + directory + "\\" + fname + "" + ext + " ");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed To Create Backup " + fname + ext);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void contactmgmtbck(BckRestoreDTO brd) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();
            String ext = ".csv";

            String query = "(SELECT 'contactid','contactfname','contactlname','contactrelation','contactaddress','contactno','contactgroup' ) \n"
                    + "UNION(SELECT contactid,contactfname,contactlname,contactrelation,contactaddress,contactno,contactgroup FROM contactmgmttbl "
                    + "INTO OUTFILE"
                    + " '" + directory + "/" + fname + "" + ext + " ' \n"
                    + "FIELDS TERMINATED BY ',' ESCAPED BY '\' LINES TERMINATED BY '\r\n');";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {
                JOptionPane.showMessageDialog(null, "Backup Created Sucessfully " + fname + ext, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                int clicked = JOptionPane.showConfirmDialog(null, "View The Content?", "View Conformation", JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {
                    Runtime.getRuntime().exec("cmd /c start " + directory + "\\" + fname + "" + ext + " ");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed To Create Backup " + fname + ext);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
