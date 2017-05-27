package homemgmtsystem.ui;

import homemgmtsystem.dao.DashboardDAO;
import homemgmtsystem.dao.SearchDAO;
import homemgmtsystem.dao.UserDAO;
import homemgmtsystem.db.DBBck;
import homemgmtsystem.db.DBConnection;
import homemgmtsystem.db.DBRestore;
import homemgmtsystem.dto.BckRestoreDTO;
import homemgmtsystem.dto.DashboardDTO;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class DashboardUI extends javax.swing.JFrame {

    Statement stmt;
    Connection con;
    ResultSet rs;
    PreparedStatement pstmt;

    public static boolean flag = true;
    public static boolean count = false;

    int timerun;

    public DashboardUI() {
        this.setTitle("Dashboard");
        initComponents();
        comboBoxSetValue();
        loadDatas();
        entrytxtfield.setEditable(false);
        setEntryid();
        gettotalamountWithoutSearch();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setResizable(true);
        setDay();
    }

    public void noofdays() {
        Calendar calendar = Calendar.getInstance();
        int numDays = calendar.getActualMaximum(Calendar.DATE);
        System.out.println(numDays);
    }

    public void timer() {
        new Thread() {
            public void run() {
                while (timerun == 0) {
                    Date dNow = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("E yyyy/MM/dd 'Time :-' hh:mm:ss a");
//                    dateandtimelab.setText("Today is :- " + ft.format(dNow));
                }
            }

        }.start();
    }

    public void disableUserManagement() {
        usermanagementmenuitem.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // this method check if any txtfield in dashboard is empty or not
    // if yes then it will give the error
    public void check() {
        String entryid = (entrytxtfield.getText());
        String amount = (amounttxtfield.getText());
        String date = ((JTextField) datetxtfield.getDateEditor().getUiComponent()).getText();
        if (entryid.equals("")) {
            JOptionPane.showMessageDialog(null, "Entry Id Is Empty", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        if (date.length() == 0) {
            JOptionPane.showMessageDialog(null, "Enter The Valid Date", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        if (amount.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter The Valid Amount", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    //end 

    // this method count the no of rows in the jtable
    public void count() {
        noofrecordlab.setText("No of Records:- " + Integer.toString(transtable.getRowCount()));
    }
    //end

    // this method will reset all the textfield in the dashboard
    public void reset() {
        particularcombobox.setSelectedItem("Select Particular");
        searchcombobox.setSelectedItem("Entry Id");
        insertbtn.setEnabled(true);
        editbtn.setText("Edit");

        amounttxtfield.setText("");
        remarktextarea.setText("");
        searchtxtfield.setText("");
        setEntryid();
        quicksearchtxtfield.setText("");
        editmenupopup.setEnabled(true);
        deletemenupopup.setEnabled(true);
        exporttocsv.setEnabled(true);
        importfromcsv.setEnabled(true);
        generatereportmenuitem.setEnabled(true);
        ((JTextField) datetxtfield.getDateEditor().getUiComponent()).setText("");

        loadDatas();

    }
    //end

    public void setDay() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("EEEEE, dd MMMMM yyyy");
        daylab.setText(ft.format(dNow));
    }

    // this method will set the value in the particular combobox
    // the particular value is retrived from the database
    public void comboBoxSetValue() {
        String pname;
        particularcombobox.removeAllItems();
        particularcombobox.addItem("Select Particular");
        try {
            con = new DBConnection().getConnection();
            String query = "SELECT *FROM particulartbl WHERE pname != 'reserved'";

            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                pname = rs.getString("pname");
                particularcombobox.addItem(pname);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    //end

    // set the entryid in the dashboard 
    public void setEntryid() {
        DashboardDAO ddao = new DashboardDAO();
        int entryid = ddao.getEntryid() + 1;
        entrytxtfield.setText(Integer.toString(entryid));
    }
    //end 

    // set username start
    public void setUsername(String username) {
        String uname = username.substring(0, 1).toUpperCase() + username.substring(1);
        usernamelab.setText(uname);
    }
    // set username end

    // insert the record in the database
    public void insert() {
        DashboardDTO ddto = new DashboardDTO();
        int entryid = Integer.parseInt(entrytxtfield.getText());
        ddto.setEntryid(entryid);

        String date = ((JTextField) datetxtfield.getDateEditor().getUiComponent()).getText();
        ddto.setDate(date);

        String particular = (String) particularcombobox.getSelectedItem();
        ddto.setParticular(particular);

        double amount = Double.valueOf(amounttxtfield.getText());

        ddto.setAmount(amount);

        String remark = remarktextarea.getText();
        ddto.setRemark(remark);

        DashboardDAO ddao = new DashboardDAO();
        ddao.insertRecord(ddto);

        loadDatas();
        reset();

    }
    //end

    //update the record in the database
    public void update() {

        int rowupdate = transtable.getSelectedRowCount(); // return the no of selected row

        if (rowupdate < 2) {
            if (rowupdate != 0) {
                if (editbtn.getText().equalsIgnoreCase("Edit")) {
                    editbtn.setText("Update");
                    insertbtn.setEnabled(false);

                    DashboardDTO ddto = new DashboardDAO().editUser(transtable);

                    entrytxtfield.setEditable(false);
                    entrytxtfield.setText(Integer.toString(ddto.getEntryid()));
                    ((JTextField) datetxtfield.getDateEditor().getUiComponent()).setText(ddto.getDate());
                    particularcombobox.setSelectedItem(ddto.getParticular());
                    amounttxtfield.setText(Double.toString(ddto.getAmount()));
                    remarktextarea.setText(ddto.getRemark());
                    editmenupopup.setEnabled(false);
                    deletemenupopup.setEnabled(false);

                } else {
                    insertbtn.setEnabled(true);
                    editbtn.setText("Edit");
                    DashboardDTO ddto = new DashboardDTO();
                    ddto.setEntryid(Integer.parseInt(entrytxtfield.getText()));
                    ddto.setDate(((JTextField) datetxtfield.getDateEditor().getUiComponent()).getText());
                    ddto.setParticular((String) particularcombobox.getSelectedItem());
                    ddto.setAmount(Double.valueOf(amounttxtfield.getText()));
                    ddto.setRemark(remarktextarea.getText());
                    new DashboardDAO().updateUser(ddto);
                    reset();
                    loadDatas();
                    editbtn.setText("Edit");
                    editmenupopup.setEnabled(true);
                    deletemenupopup.setEnabled(true);

                }
            } else {
                JOptionPane.showMessageDialog(null, "Select the one row to update", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                reset();
            }

        } else {
            JOptionPane.showMessageDialog(null, "You can only edit or update one row at a time", "Home Management System",
                    JOptionPane.INFORMATION_MESSAGE);
            reset();
        }
    }
    //end

    // delete the multiple selected row in the jtable
    public void DeleteMethodUsingArray() {
        try {

            int[] rows = transtable.getSelectedRows();

            if (rows.length != 0) { // return the no of selected rows in transtable
                int clicked = JOptionPane.showConfirmDialog(null, "Are you Suren You want to delete " + rows.length + " Record ?", "Home Management System",
                        JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {
                    for (int i = 0; i < rows.length; i++) {
                        String catid = Integer.toString((int) transtable.getValueAt(rows[i], 0));

                        String[] strArray = new String[]{catid};
                        new DashboardDAO().deleteRows(strArray);
                    }
                }
            }
            loadDatas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed To Delete The Selected Rows?", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        reset();
    }
    //end

    // return the selected value from the search combobox
    public String search() {
        String value = (String) searchcombobox.getSelectedItem();
        return value;
    }
    //end

    // search the record in the jtable with the value get from the searchcombobox
    public void searchData() {
        if (searchtxtfield.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Search Field Is Empty?", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        } else {
            gettotalamountWithSearch();
            loadDatasSearch();
        }
    }
    //end

    // get the total amount of all the record in the dashboard
    public void gettotalamountWithSearch() {
        DashboardDAO ddao = new DashboardDAO();

        if (search().equalsIgnoreCase("date")) {
            totaltxtfield.setText(Double.toString(ddao.returntotalwithsearch(searchtxtfield.getText(), null, null, null)));
            loadDatasSearch();
            count();

        } else if (search().equalsIgnoreCase("particular")) {
            totaltxtfield.setText(Double.toString(ddao.returntotalwithsearch(null, searchtxtfield.getText(), null, null)));
            loadDatasSearch();
            count();

        } else if (search().equalsIgnoreCase("entry id")) {
            totaltxtfield.setText(Double.toString(ddao.returntotalwithsearch(null, null, searchtxtfield.getText(), null)));
            loadDatasSearch();
            count();

        } else if (search().equalsIgnoreCase("amount")) {
            totaltxtfield.setText(Double.toString(ddao.returntotalwithsearch(null, null, null, searchtxtfield.getText())));
            loadDatasSearch();
            count();
        }
    }
    //end

    //calculate the total amount without search in the dashboard
    public void gettotalamountWithoutSearch() {
        DashboardDAO ddao = new DashboardDAO();
        totaltxtfield.setText(Double.toString(ddao.returntotalWithoutSearch()));
    }
    //end

    // calculate the total amount with the quick search
    public void gettotalamountwithquicksearch() {
        DashboardDAO ddao = new DashboardDAO();
        totaltxtfield.setText(Double.toString(ddao.returntotalwithquicksearch(quicksearchtxtfield.getText())));
    }
    //end

    // load the jtable record on the basics of the search
    public void loadDatasSearch() {
        try {
            SearchDAO sdao = new SearchDAO();

            for (int c = 0; c < transtable.getColumnCount(); c++) {
                Class<?> col_class = transtable.getColumnClass(c);
                transtable.setDefaultEditor(col_class, null);
            }
            if (search().equalsIgnoreCase("date")) {
                transtable.setModel(sdao.buildTableModel(sdao.searchDate(searchtxtfield.getText(), null, null, null)));
            } else if (search().equalsIgnoreCase("particular")) {
                transtable.setModel(sdao.buildTableModel(sdao.searchDate(null, searchtxtfield.getText(), null, null)));
            } else if (search().equalsIgnoreCase("amount")) {
                transtable.setModel(sdao.buildTableModel(sdao.searchDate(null, null, searchtxtfield.getText(), null)));
            } else if (search().equalsIgnoreCase("entry id")) {
                transtable.setModel(sdao.buildTableModel(sdao.searchDate(null, null, null, searchtxtfield.getText())));
            } else if (search().equals("")) {
                JOptionPane.showMessageDialog(null, "Search Field Is Empty?");
            }
            transtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            transtable.getColumnModel().getColumn(0).setPreferredWidth(27);
            transtable.getColumnModel().getColumn(1).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(2).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(3).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(4).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(5).setPreferredWidth(60);
            transtable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            transtable.setRowSelectionAllowed(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed To Retrive The Data From Database?");
        }
    }
    //end

    //load all the record in the jtable retrived from the database
    public void loadDatas() {
        try {
            DashboardDAO ddao = new DashboardDAO();

            for (int c = 0; c < transtable.getColumnCount(); c++) {
                Class<?> col_class = transtable.getColumnClass(c);
                transtable.setDefaultEditor(col_class, null);
            }
            transtable.setModel(ddao.buildTableModel(ddao.getQueryResult()));

            transtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            transtable.getColumnModel().getColumn(0).setPreferredWidth(27);
            transtable.getColumnModel().getColumn(1).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(2).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(3).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(4).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(5).setPreferredWidth(60);
            transtable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            transtable.setRowSelectionAllowed(true);
            count();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    //end

    // load all the record on the basics of the quick search txt field
    public void loadDataQuickSearch() {
        try {
            DashboardDAO ddao = new DashboardDAO();
            for (int c = 0; c < transtable.getColumnCount(); c++) {
                Class<?> col_class = transtable.getColumnClass(c);
                transtable.setDefaultEditor(col_class, null);
            }
            transtable.setModel(ddao.buildTableModel(ddao.getQueryResultQuickSearch(quicksearchtxtfield.getText())));

            count();

            transtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            transtable.getColumnModel().getColumn(0).setPreferredWidth(27);
            transtable.getColumnModel().getColumn(1).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(2).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(3).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(4).setPreferredWidth(60);
            transtable.getColumnModel().getColumn(5).setPreferredWidth(60);
            transtable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            transtable.setRowSelectionAllowed(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //count
    // backup the existing record in the jtable into the excel file
    public void backup() {
        DBBck dbbck = new DBBck();
        BckRestoreDTO brd = new BckRestoreDTO();
        JFileChooserSave jfcs = new JFileChooserSave(brd);
        if (transtable.getRowCount() != 0) {
            if (searchtxtfield.getText().equals("")) {
                dbbck.transactiontblBck(brd);
            } else if (search().equalsIgnoreCase("date")) {
                dbbck.transactiontblbckdate(brd, searchtxtfield.getText());
            } else if (search().equalsIgnoreCase("amount")) {
                dbbck.transactiontblbckamount(brd, searchtxtfield.getText());
            } else if (search().equalsIgnoreCase("particular")) {
                dbbck.transactiontblbckparticular(brd, searchtxtfield.getText());
            } else if (search().equalsIgnoreCase("entry id")) {
                dbbck.transactiontblbckentryid(brd, searchtxtfield.getText());
            }

        } else {
            JOptionPane.showMessageDialog(null, "No Record To Backup", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        count();
    }
    //end

    // restore the backup excel file into the jtable
    public void restore() {
        DBRestore dbrest = new DBRestore();
        BckRestoreDTO brd = new BckRestoreDTO();
        JFileChooserOpen jfco = new JFileChooserOpen();
        jfco.open(brd);
        dbrest.transactiontblRestore(brd);
        loadDatas();
        count();
    }

    //end
    public void generatePDF() {
        MessageFormat header = new MessageFormat("Transaction Information");
        MessageFormat footer = new MessageFormat("page{0, number, integer}");
        try {

            transtable.print(JTable.PrintMode.FIT_WIDTH, header, footer);

        } catch (java.awt.print.PrinterException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void generateReport() {
        try {
            String query = null;
            JasperDesign jd = JRXmlLoader.load("c:/homemgmtsystem/report/Transaction.jrxml");
        //    JasperDesign jde = JRXmlLoader.load("D:/JavaProgramOngoing/Ongoing Project/HomeMgmtSystemV4.0/src/homemgmt/report/TransactionTbl.jrxml");

            if (transtable.getRowCount() != 0) {
                if (searchtxtfield.getText().equals("")) {
                    query = "SELECT *FROM transactiontbl where pid != 'null'";

                } else if (search().equalsIgnoreCase("date")) {
                    query = "SELECT *FROM transactiontbl where pid != 'null' and date = '" + searchtxtfield.getText() + "'";

                } else if (search().equalsIgnoreCase("amount")) {
                    query = "SELECT *FROM transactiontbl where pid != 'null' and amount = '" + searchtxtfield.getText() + "'";

                } else if (search().equalsIgnoreCase("particular")) {
                    query = "SELECT *FROM transactiontbl where pid != 'null' and particular = '" + searchtxtfield.getText() + "'";

                } else if (search().equalsIgnoreCase("entry id")) {
                    query = "SELECT *FROM transactiontbl where pid != 'null' and entryid = '" + searchtxtfield.getText() + "'";
                }

                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(query);
                jd.setQuery(newQuery);

                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, new DBConnection().getConnection());

                JasperViewer jv = new JasperViewer(jp, false);
                jv.setTitle("Transaction Information");
                jv.setIconImage(new ImageIcon(getClass().getResource("home.png")).getImage());
                jv.setVisible(true);
                jv.pack();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                jv.setSize(screenSize.width, screenSize.height);
                jv.setExtendedState(jv.MAXIMIZED_BOTH);
                jv.setZoomRatio((float) 0.75);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jDialog1 = new javax.swing.JDialog();
        jMenu1 = new javax.swing.JMenu();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        tablepopupmenu = new javax.swing.JPopupMenu();
        editmenupopup = new javax.swing.JMenuItem();
        deletemenupopup = new javax.swing.JMenuItem();
        refreshmenupopup = new javax.swing.JMenuItem();
        seperator1 = new javax.swing.JPopupMenu.Separator();
        exporttocsv = new javax.swing.JMenuItem();
        importfromcsv = new javax.swing.JMenuItem();
        seperator2 = new javax.swing.JPopupMenu.Separator();
        generatereportmenuitem = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        remarktextarea = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        insertbtn = new javax.swing.JButton();
        editbtn = new javax.swing.JButton();
        deletebtn = new javax.swing.JButton();
        entrytxtfield = new javax.swing.JTextField();
        amounttxtfield = new javax.swing.JTextField();
        particularcombobox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        totaltxtfield = new javax.swing.JTextField();
        datetxtfield = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        transtable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        searchlab = new javax.swing.JLabel();
        searchcombobox = new javax.swing.JComboBox();
        SearchBtn = new javax.swing.JButton();
        refreshbtn = new javax.swing.JButton();
        searchtxtfield = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        quicksearchtxtfield = new javax.swing.JTextField();
        welcomelab = new javax.swing.JLabel();
        logoutlab = new javax.swing.JLabel();
        visitprofilelab = new javax.swing.JLabel();
        usernamelab = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        noofrecordlab = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        daylab = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        menubar = new javax.swing.JMenuBar();
        filemenu = new javax.swing.JMenu();
        openmenuitem = new javax.swing.JMenuItem();
        savemenuitem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        logoutmenuitem = new javax.swing.JMenuItem();
        editmenu = new javax.swing.JMenu();
        addcategorymenuitem = new javax.swing.JMenuItem();
        eventmanagermenuitem = new javax.swing.JMenuItem();
        contactmanagermenuitem = new javax.swing.JMenuItem();
        usermanagementmenuitem = new javax.swing.JMenuItem();
        aboutmenu = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu1.setText("jMenu1");

        jMenu2.setText("File");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar1.add(jMenu3);

        jMenuItem2.setText("jMenuItem2");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList1);

        jMenuItem4.setText("jMenuItem4");

        jMenu4.setText("jMenu4");

        jMenu5.setText("File");
        jMenuBar2.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar2.add(jMenu6);

        jMenu7.setText("jMenu7");

        tablepopupmenu.setFont(tablepopupmenu.getFont().deriveFont(tablepopupmenu.getFont().getStyle() & ~java.awt.Font.BOLD, tablepopupmenu.getFont().getSize()+2));
        tablepopupmenu.setAutoscrolls(true);
        tablepopupmenu.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 2, true));
        tablepopupmenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablepopupmenu.setInheritsPopupMenu(true);
        tablepopupmenu.setMaximumSize(new java.awt.Dimension(32773, 196608));
        tablepopupmenu.setPreferredSize(new java.awt.Dimension(153, 140));

        editmenupopup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/pen.png"))); // NOI18N
        editmenupopup.setText("Edit");
        editmenupopup.setBorderPainted(true);
        editmenupopup.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        editmenupopup.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        editmenupopup.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        editmenupopup.setPreferredSize(new java.awt.Dimension(187, 30));
        editmenupopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editmenupopupActionPerformed(evt);
            }
        });
        tablepopupmenu.add(editmenupopup);

        deletemenupopup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/bin.png"))); // NOI18N
        deletemenupopup.setText("Delete");
        deletemenupopup.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        deletemenupopup.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        deletemenupopup.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        deletemenupopup.setPreferredSize(new java.awt.Dimension(187, 30));
        deletemenupopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletemenupopupActionPerformed(evt);
            }
        });
        tablepopupmenu.add(deletemenupopup);

        refreshmenupopup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/refresh.png"))); // NOI18N
        refreshmenupopup.setText("Refresh");
        refreshmenupopup.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        refreshmenupopup.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        refreshmenupopup.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        refreshmenupopup.setPreferredSize(new java.awt.Dimension(187, 30));
        refreshmenupopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshmenupopupActionPerformed(evt);
            }
        });
        tablepopupmenu.add(refreshmenupopup);
        tablepopupmenu.add(seperator1);

        exporttocsv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/exporttoexcel.png"))); // NOI18N
        exporttocsv.setText("Export To Excel");
        exporttocsv.setContentAreaFilled(false);
        exporttocsv.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        exporttocsv.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        exporttocsv.setPreferredSize(new java.awt.Dimension(187, 30));
        exporttocsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exporttocsvActionPerformed(evt);
            }
        });
        tablepopupmenu.add(exporttocsv);

        importfromcsv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/importfromexcel.png"))); // NOI18N
        importfromcsv.setText("Import From Excel");
        importfromcsv.setContentAreaFilled(false);
        importfromcsv.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        importfromcsv.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        importfromcsv.setPreferredSize(new java.awt.Dimension(187, 30));
        importfromcsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importfromcsvActionPerformed(evt);
            }
        });
        tablepopupmenu.add(importfromcsv);
        tablepopupmenu.add(seperator2);

        generatereportmenuitem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/document.png"))); // NOI18N
        generatereportmenuitem.setText("Generate Report");
        generatereportmenuitem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        generatereportmenuitem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        generatereportmenuitem.setPreferredSize(new java.awt.Dimension(129, 30));
        generatereportmenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatereportmenuitemActionPerformed(evt);
            }
        });
        tablepopupmenu.add(generatereportmenuitem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Dashboard");
        setBackground(java.awt.Color.gray);
        setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel1.setText("   Entry ID :-");

        jLabel2.setText("   Date :-");

        jLabel3.setText("   Particular :-");

        jLabel4.setText("   Amount :-");

        remarktextarea.setColumns(20);
        remarktextarea.setRows(5);
        jScrollPane1.setViewportView(remarktextarea);

        jLabel5.setText("   Remark :-");

        insertbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/add.png"))); // NOI18N
        insertbtn.setText("Insert");
        insertbtn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        insertbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertbtnActionPerformed(evt);
            }
        });

        editbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/pen.png"))); // NOI18N
        editbtn.setText("Edit");
        editbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editbtnActionPerformed(evt);
            }
        });
        editbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                editbtnKeyPressed(evt);
            }
        });

        deletebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/bin.png"))); // NOI18N
        deletebtn.setText("Delete");
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
            }
        });

        entrytxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        amounttxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        amounttxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                amounttxtfieldKeyPressed(evt);
            }
        });

        particularcombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Particular" }));
        particularcombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                particularcomboboxActionPerformed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Total Amount : -");

        totaltxtfield.setEditable(false);
        totaltxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        datetxtfield.setDateFormatString("MMM d yyyy");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(totaltxtfield))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(datetxtfield, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(particularcombobox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(entrytxtfield)
                            .addComponent(amounttxtfield, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(insertbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deletebtn)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deletebtn, editbtn, insertbtn});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entrytxtfield, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datetxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(particularcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amounttxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertbtn)
                    .addComponent(editbtn)
                    .addComponent(deletebtn))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totaltxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {deletebtn, editbtn, insertbtn});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {amounttxtfield, datetxtfield, entrytxtfield, particularcombobox});

        transtable.setForeground(java.awt.Color.darkGray);
        transtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        transtable.setComponentPopupMenu(tablepopupmenu);
        transtable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        transtable.setEditingRow(1);
        transtable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                transtableKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(transtable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setForeground(new java.awt.Color(153, 153, 153));

        searchlab.setText("Backup By");

        searchcombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entry Id", "Date", "Particular", "Amount" }));

        SearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/search.png"))); // NOI18N
        SearchBtn.setText("Search");
        SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBtnActionPerformed(evt);
            }
        });

        refreshbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/refresh.png"))); // NOI18N
        refreshbtn.setText("Refresh");
        refreshbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshbtnActionPerformed(evt);
            }
        });

        searchtxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchtxtfield.setFocusCycleRoot(true);
        searchtxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchtxtfieldActionPerformed(evt);
            }
        });
        searchtxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchtxtfieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchtxtfieldKeyReleased(evt);
            }
        });

        jLabel7.setText("Quick Search");

        quicksearchtxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        quicksearchtxtfield.setFocusCycleRoot(true);
        quicksearchtxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quicksearchtxtfieldActionPerformed(evt);
            }
        });
        quicksearchtxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                quicksearchtxtfieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                quicksearchtxtfieldKeyReleased(evt);
            }
        });

        welcomelab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        welcomelab.setText("Welcome");

        logoutlab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        logoutlab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoutlab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/out.png"))); // NOI18N
        logoutlab.setText("    Logout");
        logoutlab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutlab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        logoutlab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                logoutlabMouseReleased(evt);
            }
        });

        visitprofilelab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        visitprofilelab.setText("Visit Profile :-");
        visitprofilelab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        visitprofilelab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                visitprofilelabMouseReleased(evt);
            }
        });

        usernamelab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchlab, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SearchBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refreshbtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(welcomelab)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(usernamelab, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(visitprofilelab, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(quicksearchtxtfield, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addComponent(logoutlab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {SearchBtn, refreshbtn});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {logoutlab, quicksearchtxtfield});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(welcomelab, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoutlab)
                    .addComponent(visitprofilelab, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernamelab))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(quicksearchtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchlab, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SearchBtn)
                        .addComponent(refreshbtn)
                        .addComponent(searchtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {SearchBtn, logoutlab, quicksearchtxtfield, refreshbtn, searchcombobox, searchlab, searchtxtfield});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel7, usernamelab, welcomelab});

        noofrecordlab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        noofrecordlab.setText("No of Records :-");

        daylab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        daylab.setText("daylab");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(noofrecordlab, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(daylab, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(noofrecordlab, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(daylab, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {daylab, noofrecordlab});

        filemenu.setText("File");

        openmenuitem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openmenuitem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/importfromexcel.png"))); // NOI18N
        openmenuitem.setText("  Open");
        openmenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openmenuitemActionPerformed(evt);
            }
        });
        filemenu.add(openmenuitem);

        savemenuitem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        savemenuitem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/exporttoexcel.png"))); // NOI18N
        savemenuitem.setText("  Save");
        savemenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savemenuitemActionPerformed(evt);
            }
        });
        filemenu.add(savemenuitem);
        filemenu.add(jSeparator3);

        logoutmenuitem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        logoutmenuitem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/out.png"))); // NOI18N
        logoutmenuitem.setText("  Logout");
        logoutmenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutmenuitemActionPerformed(evt);
            }
        });
        filemenu.add(logoutmenuitem);

        menubar.add(filemenu);

        editmenu.setText("Edit");
        editmenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editmenuMouseClicked(evt);
            }
        });
        editmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editmenuActionPerformed(evt);
            }
        });

        addcategorymenuitem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        addcategorymenuitem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/add.png"))); // NOI18N
        addcategorymenuitem.setText("  Add Particular");
        addcategorymenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addcategorymenuitemActionPerformed(evt);
            }
        });
        editmenu.add(addcategorymenuitem);

        eventmanagermenuitem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        eventmanagermenuitem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/speech-bubble-center-2.png"))); // NOI18N
        eventmanagermenuitem.setText("  Event Manager");
        eventmanagermenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventmanagermenuitemActionPerformed(evt);
            }
        });
        editmenu.add(eventmanagermenuitem);

        contactmanagermenuitem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        contactmanagermenuitem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/user-4-add.png"))); // NOI18N
        contactmanagermenuitem.setText("  Contact Manager");
        contactmanagermenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactmanagermenuitemActionPerformed(evt);
            }
        });
        editmenu.add(contactmanagermenuitem);

        usermanagementmenuitem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        usermanagementmenuitem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/users.png"))); // NOI18N
        usermanagementmenuitem.setText("  User Management");
        usermanagementmenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usermanagementmenuitemActionPerformed(evt);
            }
        });
        editmenu.add(usermanagementmenuitem);

        menubar.add(editmenu);

        aboutmenu.setText("About");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/home.png"))); // NOI18N
        jMenuItem3.setText("  Home Mgmt Info");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        aboutmenu.add(jMenuItem3);

        menubar.add(aboutmenu);

        setJMenuBar(menubar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openmenuitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openmenuitemActionPerformed
        // TODO add your handling code here:
        restore();
    }//GEN-LAST:event_openmenuitemActionPerformed

    private void savemenuitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savemenuitemActionPerformed
        // TODO add your handling code here:
        backup();
    }//GEN-LAST:event_savemenuitemActionPerformed

    private void logoutmenuitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutmenuitemActionPerformed
        // TODO add your handling code here:
        dispose();
        LoginUI lu = new LoginUI();
        lu.setVisible(true);
        lu.pack();
        lu.setLocationRelativeTo(null);
    }//GEN-LAST:event_logoutmenuitemActionPerformed

    private void insertbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertbtnActionPerformed
        // TODO add your handling code here:
        check();
        insert();
        gettotalamountWithoutSearch();
        count();
    }//GEN-LAST:event_insertbtnActionPerformed


    private void refreshbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshbtnActionPerformed
        // TODO add your handling code here:
        comboBoxSetValue();
        loadDatas();
        reset();
        gettotalamountWithoutSearch();
        count();
    }//GEN-LAST:event_refreshbtnActionPerformed

    private void particularcomboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_particularcomboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_particularcomboboxActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        // TODO add your handling code here:
        DeleteMethodUsingArray();
        gettotalamountWithoutSearch();
        count();
    }//GEN-LAST:event_deletebtnActionPerformed

    private void transtableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_transtableKeyPressed
        // TODO add your handling code here:
        int keycode = evt.getKeyCode();
        if (evt.getSource() == transtable) {
            if (keycode == KeyEvent.VK_DELETE) {
                DeleteMethodUsingArray();
                gettotalamountWithoutSearch();
                count();
            }
        }
    }//GEN-LAST:event_transtableKeyPressed

    private void editbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbtnActionPerformed
        // TODO add your handling code here:
        update();
        gettotalamountWithoutSearch();
    }//GEN-LAST:event_editbtnActionPerformed

    private void SearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBtnActionPerformed
        // TODO add your handling code here:
        searchData();
    }//GEN-LAST:event_SearchBtnActionPerformed

    private void searchtxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtxtfieldKeyPressed
        // TODO add your handling code here:
        int key = evt.getKeyCode();
        if (evt.getSource() == searchtxtfield) {
            if (key == KeyEvent.VK_ENTER) {
                searchData();
                gettotalamountWithSearch();
            }
        }
    }//GEN-LAST:event_searchtxtfieldKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void addcategorymenuitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addcategorymenuitemActionPerformed
        // TODO add your handling code here:
        if (flag == true) {
            AddCategory ac = new AddCategory();
            ac.pack();
            ac.setVisible(true);
            ac.setLocationRelativeTo(null);
        }
        flag = false;

    }//GEN-LAST:event_addcategorymenuitemActionPerformed

    public void visible() {
        flag = true;
    }


    private void amounttxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amounttxtfieldKeyPressed
        // TODO add your handling code here:
        int key = evt.getKeyCode();
        if (evt.getSource() == amounttxtfield) {
            if (key == KeyEvent.VK_ENTER) {
                if (insertbtn.isEnabled()) {
                    check();
                    insert();
                    gettotalamountWithoutSearch();
                    count();
                } else {
                    update();
                    reset();
                    gettotalamountWithoutSearch();
                    count();
                }
            }
        }
    }//GEN-LAST:event_amounttxtfieldKeyPressed

    private void editbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editbtnKeyPressed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_editbtnKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.setIconImage(new ImageIcon(getClass().getResource("home.png")).getImage());
        count();
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        if (flag == true) {
            Info info = new Info();
            info.setVisible(true);
        }
        flag = false;

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void searchtxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchtxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchtxtfieldActionPerformed

    private void editmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editmenuActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_editmenuActionPerformed

    private void editmenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editmenuMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_editmenuMouseClicked

    private void quicksearchtxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quicksearchtxtfieldActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_quicksearchtxtfieldActionPerformed

    private void quicksearchtxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quicksearchtxtfieldKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_quicksearchtxtfieldKeyPressed

    private void quicksearchtxtfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quicksearchtxtfieldKeyReleased
        // TODO add your handling code here:
        if (quicksearchtxtfield.getText().length() != 0) {
            loadDataQuickSearch();
            gettotalamountwithquicksearch();
            importfromcsv.setEnabled(false);
            exporttocsv.setEnabled(false);
            generatereportmenuitem.setEnabled(false);
        } else {
            importfromcsv.setEnabled(true);
            exporttocsv.setEnabled(true);
            generatereportmenuitem.setEnabled(true);
            gettotalamountWithoutSearch();
            loadDatas();
            reset();

        }

    }//GEN-LAST:event_quicksearchtxtfieldKeyReleased

    private void eventmanagermenuitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventmanagermenuitemActionPerformed
        // TODO add your handling code here:
        EventManager em = new EventManager();
        if (flag == true) {
            em.setLocationRelativeTo(null);
            em.setVisible(true);
            em.pack();
        }
        flag = false;
    }//GEN-LAST:event_eventmanagermenuitemActionPerformed

    private void contactmanagermenuitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactmanagermenuitemActionPerformed
        // TODO add your handling code here:
        ContactUI cui = new ContactUI();
        if (flag == true) {
            cui.setLocationRelativeTo(null);
            cui.setVisible(true);
            cui.pack();
        }
        flag = false;

    }//GEN-LAST:event_contactmanagermenuitemActionPerformed

    private void editmenupopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editmenupopupActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_editmenupopupActionPerformed

    private void deletemenupopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletemenupopupActionPerformed
        // TODO add your handling code here:
        DeleteMethodUsingArray();
        loadDatas();
        count();
    }//GEN-LAST:event_deletemenupopupActionPerformed

    private void refreshmenupopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshmenupopupActionPerformed
        // TODO add your handling code here:
        reset();
        loadDatas();
        count();
    }//GEN-LAST:event_refreshmenupopupActionPerformed

    private void usermanagementmenuitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usermanagementmenuitemActionPerformed
        // TODO add your handling code here:
        UserUI uui = new UserUI();
        if (flag == true) {
            uui.setLocationRelativeTo(null);
            uui.pack();
            uui.setVisible(true);
        }
        flag = false;
    }//GEN-LAST:event_usermanagementmenuitemActionPerformed

    private void importfromcsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importfromcsvActionPerformed
        // TODO add your handling code here:
        restore();
    }//GEN-LAST:event_importfromcsvActionPerformed

    private void exporttocsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exporttocsvActionPerformed
        // TODO add your handling code here:
        backup();
    }//GEN-LAST:event_exporttocsvActionPerformed

    private void logoutlabMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutlabMouseReleased
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            dispose();
            LoginUI lu = new LoginUI();
            lu.setVisible(true);
            lu.pack();
            lu.setLocationRelativeTo(null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_logoutlabMouseReleased
    public void getUserInfo() {
        try {
            rs = new UserDAO().getUserProfile(usernamelab.getText());
            while (rs.next()) {
                String userinfofname = rs.getString(1);
                String userinfolname = rs.getString(2);
                String userinfocontact = rs.getString(3);
                String userinfousername = rs.getString(4);
                String usertype = rs.getString(6);
                System.out.println(userinfofname);
                System.out.println(userinfolname);
                new UserProfileUI().setUserInfo(userinfofname, userinfolname, userinfocontact, userinfousername, usertype);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void visitprofilelabMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_visitprofilelabMouseReleased
        // TODO add your handling code here:
        if (flag == true) {
            UserProfileUI uui = new UserProfileUI();
            uui.userinfo(usernamelab.getText());
            uui.setLocationRelativeTo(null);
            uui.setVisible(true);
            uui.userinfo(usernamelab.getText());
            getUserInfo();
        }
        flag = false;

    }//GEN-LAST:event_visitprofilelabMouseReleased

    private void searchtxtfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtxtfieldKeyReleased
        // TODO add your handling code here:
        int keycode = evt.getKeyCode();
        if (evt.getSource() == searchtxtfield) {
            if (keycode == KeyEvent.VK_BACK_SPACE && searchtxtfield.getText().length() == 0) {
                reset();
            }
        }
    }//GEN-LAST:event_searchtxtfieldKeyReleased

    private void generatereportmenuitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatereportmenuitemActionPerformed
        // TODO add your handling code here:
        generateReport();
    }//GEN-LAST:event_generatereportmenuitemActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Please Logout To Exit", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardUI().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SearchBtn;
    private javax.swing.JMenu aboutmenu;
    private javax.swing.JMenuItem addcategorymenuitem;
    private javax.swing.JTextField amounttxtfield;
    private javax.swing.JMenuItem contactmanagermenuitem;
    private com.toedter.calendar.JDateChooser datetxtfield;
    private javax.swing.JLabel daylab;
    private javax.swing.JButton deletebtn;
    private javax.swing.JMenuItem deletemenupopup;
    private javax.swing.JButton editbtn;
    private javax.swing.JMenu editmenu;
    private javax.swing.JMenuItem editmenupopup;
    private javax.swing.JTextField entrytxtfield;
    private javax.swing.JMenuItem eventmanagermenuitem;
    private javax.swing.JMenuItem exporttocsv;
    private javax.swing.JMenu filemenu;
    private javax.swing.JMenuItem generatereportmenuitem;
    private javax.swing.JMenuItem importfromcsv;
    private javax.swing.JButton insertbtn;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JLabel logoutlab;
    private javax.swing.JMenuItem logoutmenuitem;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JLabel noofrecordlab;
    private javax.swing.JMenuItem openmenuitem;
    private javax.swing.JComboBox particularcombobox;
    private javax.swing.JTextField quicksearchtxtfield;
    private javax.swing.JButton refreshbtn;
    private javax.swing.JMenuItem refreshmenupopup;
    private javax.swing.JTextArea remarktextarea;
    private javax.swing.JMenuItem savemenuitem;
    private javax.swing.JComboBox searchcombobox;
    private javax.swing.JLabel searchlab;
    private javax.swing.JTextField searchtxtfield;
    private javax.swing.JPopupMenu.Separator seperator1;
    private javax.swing.JPopupMenu.Separator seperator2;
    private javax.swing.JPopupMenu tablepopupmenu;
    private javax.swing.JTextField totaltxtfield;
    private javax.swing.JTable transtable;
    private javax.swing.JMenuItem usermanagementmenuitem;
    private javax.swing.JLabel usernamelab;
    private javax.swing.JLabel visitprofilelab;
    private javax.swing.JLabel welcomelab;
    // End of variables declaration//GEN-END:variables
}
