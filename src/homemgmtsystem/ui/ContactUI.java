package homemgmtsystem.ui;

import homemgmtsystem.dao.ContactGroupDAO;
import homemgmtsystem.dao.ContactMgmtDAO;
import homemgmtsystem.db.DBBck;
import homemgmtsystem.db.DBConnection;
import homemgmtsystem.db.DBRestore;
import homemgmtsystem.dto.BckRestoreDTO;
import homemgmtsystem.dto.ContactDTO;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ContactUI extends javax.swing.JFrame {

    Statement stmt;
    Connection con;
    ResultSet rs;
    PreparedStatement pstmt;

    ContactDTO cdto = new ContactDTO();
    ContactMgmtDAO cdao = new ContactMgmtDAO();

    public ContactUI() {
        this.setTitle("Dashboard");
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        checkcontactid();
        contactidtxtfield.setEditable(false);
        loadDatas();
        count();
        comboBoxSetValue();
    }

    public void checkcontactid() {
        ContactMgmtDAO cmdao = new ContactMgmtDAO();
        int contactid = cmdao.getContactid() + 1;
        contactidtxtfield.setText(String.valueOf(contactid));
    }

    public void count() {
        noofrecordslab.setText("No of Records:- " + Integer.toString(contacttbl.getRowCount()));
    }

    public void reset() {
        groupcombobox.setSelectedItem("Select Group");
        firstnametxtfield.setText("");
        lastnametxtfield.setText("");
        relationtxtfield.setText("");
        addresstxtfield.setText("");
        contactnotxtfield.setText("");
        count();
        loadDatas();
    }

    public void check() {
        if (firstnametxtfield.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Enter The First Name", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            firstnametxtfield.requestFocus(true);
        }

        if (lastnametxtfield.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Enter The Last Name", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            lastnametxtfield.requestFocus(true);
        }

        if (relationtxtfield.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Enter The Relation", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            relationtxtfield.requestFocus(true);
        }

        if (addresstxtfield.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Enter The Address", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            addresstxtfield.requestFocus(true);
        }
        if (contactnotxtfield.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Enter The Contact No", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            contactnotxtfield.requestFocus(true);
        }
        if (groupcombobox.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null, "Please Select The Group", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void settablewidth() {
        contacttbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        contacttbl.getColumnModel().getColumn(0).setPreferredWidth(27);
        contacttbl.getColumnModel().getColumn(1).setPreferredWidth(60);
        contacttbl.getColumnModel().getColumn(2).setPreferredWidth(60);
        contacttbl.getColumnModel().getColumn(3).setPreferredWidth(60);
        contacttbl.getColumnModel().getColumn(4).setPreferredWidth(60);
        contacttbl.getColumnModel().getColumn(5).setPreferredWidth(60);
        contacttbl.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        contacttbl.setRowSelectionAllowed(true);
    }

    public void insert() {

        cdto.setContactid(Integer.parseInt(contactidtxtfield.getText()));

        cdto.setContactfname(firstnametxtfield.getText().substring(0, 1).toUpperCase() + firstnametxtfield.getText().substring(1));
        cdto.setContactlname(lastnametxtfield.getText().substring(0, 1).toUpperCase() + lastnametxtfield.getText().substring(1));
        cdto.setContactrelation(relationtxtfield.getText().substring(0, 1).toUpperCase() + relationtxtfield.getText().substring(1));
        cdto.setContactaddress(addresstxtfield.getText().substring(0, 1).toUpperCase() + addresstxtfield.getText().substring(1));
        cdto.setContactno(contactnotxtfield.getText());
        cdto.setContactgroup((String) groupcombobox.getSelectedItem());

        if (firstnametxtfield.getText().length() != 0 && lastnametxtfield.getText().length() != 0 && contactnotxtfield.getText().length() != 0
                && addresstxtfield.getText().length() != 0 && relationtxtfield.getText().length() != 0 && groupcombobox.getSelectedIndex() != 0
                && contactidtxtfield.getText().length() !=0) {
            cdao.insertRecord(cdto);
            reset();
        }

    }

    public void update() {

        int rowupdate = contacttbl.getSelectedRowCount();

        if (rowupdate < 2) {
            if (rowupdate != 0) {
                if (editbtn.getText().equalsIgnoreCase("Edit")) {
                    editbtn.setText("Update");
                    insertbtn.setEnabled(false);

                    cdto = cdao.editUser(contacttbl);

                    contactidtxtfield.setEditable(false);
                    contactidtxtfield.setText(Integer.toString(cdto.getContactid()));
                    firstnametxtfield.setText(cdto.getContactfname());
                    lastnametxtfield.setText(cdto.getContactlname());
                    relationtxtfield.setText(cdto.getContactrelation());
                    groupcombobox.setSelectedItem(cdto.getContactgroup());
                    addresstxtfield.setText(cdto.getContactaddress());
                    contactnotxtfield.setText(cdto.getContactno());

                } else {
                    insertbtn.setEnabled(true);
                    editbtn.setText("Edit");

                    cdto.setContactid(Integer.parseInt(contactidtxtfield.getText()));
                    cdto.setContactfname(firstnametxtfield.getText());
                    cdto.setContactlname(lastnametxtfield.getText());
                    cdto.setContactrelation(relationtxtfield.getText());
                    cdto.setContactaddress(addresstxtfield.getText());
                    cdto.setContactno(contactnotxtfield.getText());
                    cdto.setContactgroup((String) groupcombobox.getSelectedItem());
                    cdao.updateUser(cdto);
                    reset();
                    loadDatas();
                    editbtn.setText("Edit");
                    checkcontactid();

                }
            } else {
                JOptionPane.showMessageDialog(null, "Select The One Row To Update", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                reset();
            }
        } else {
            JOptionPane.showMessageDialog(null, "You Can Only Edit Or Update One Row At A Time", "Home Management System",
                    JOptionPane.INFORMATION_MESSAGE);
            reset();
        }

    }

    public void DeleteMethodUsingArray() {
        try {
            con = new DBConnection().getConnection();
            int[] rows = contacttbl.getSelectedRows();

            if (rows.length != 0) { // return the no of selected rows in contacttbl
                int clicked = JOptionPane.showConfirmDialog(null, "Are you Suren You want to delete " + rows.length + " Record ?", "Home Management System",
                        JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {
                    for (int i = 0; i < rows.length; i++) {
                        String catid = Integer.toString((int) contacttbl.getValueAt(rows[i], 0));

                        String[] strArray = new String[]{catid};
                        cdao.deleteRows(strArray);
                        checkcontactid();
                    }
                }
            }
            loadDatas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void loadDatas() {
        try {
            for (int c = 0; c < contacttbl.getColumnCount(); c++) {
                Class<?> col_class = contacttbl.getColumnClass(c);
                contacttbl.setDefaultEditor(col_class, null);
            }
            contacttbl.setModel(cdao.buildTableModel(cdao.getQueryResult()));
            settablewidth();
            contacttbl.setRowSelectionAllowed(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public String search() {
        String value = (String) searchcombobox.getSelectedItem();
        return value;
    }

    public void searchdata() {
        if (searchtxtfield.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Search Field Is Empty", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        } else {
            loadDatasSearch();
            count();
        }
    }

    public void loadDatasSearch() {
        try {
            for (int c = 0; c < contacttbl.getColumnCount(); c++) {
                Class<?> col_class = contacttbl.getColumnClass(c);
                contacttbl.setDefaultEditor(col_class, null);
            }
            if (search().equalsIgnoreCase("contact id")) {
                contacttbl.setModel(cdao.buildTableModel(cdao.search(searchtxtfield.getText(), null, null, null, null, null, null)));
            } else if (search().equalsIgnoreCase("firstname")) {
                contacttbl.setModel(cdao.buildTableModel(cdao.search(null, searchtxtfield.getText(), null, null, null, null, null)));
            } else if (search().equalsIgnoreCase("lastname")) {
                contacttbl.setModel(cdao.buildTableModel(cdao.search(null, null, searchtxtfield.getText(), null, null, null, null)));
            } else if (search().equalsIgnoreCase("relation")) {
                contacttbl.setModel(cdao.buildTableModel(cdao.search(null, null, null, searchtxtfield.getText(), null, null, null)));
            } else if (search().equalsIgnoreCase("contact no")) {
                contacttbl.setModel(cdao.buildTableModel(cdao.search(null, null, null, null, searchtxtfield.getText(), null, null)));
            } else if (search().equalsIgnoreCase("address")) {
                contacttbl.setModel(cdao.buildTableModel(cdao.search(null, null, null, null, null, searchtxtfield.getText(), null)));
            } else if (search().equalsIgnoreCase("group")) {
                contacttbl.setModel(cdao.buildTableModel(cdao.search(null, null, null, null, null, null, searchtxtfield.getText())));
            } else if (search().equals("")) {
                JOptionPane.showMessageDialog(null, "Search Field Is Empty?", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
            settablewidth();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void loadDataQuickSearch() {
        try {
            for (int c = 0; c < contacttbl.getColumnCount(); c++) {
                Class<?> col_class = contacttbl.getColumnClass(c);
                contacttbl.setDefaultEditor(col_class, null);
            }
            contacttbl.setModel(cdao.buildTableModel(cdao.getQueryResultQuickSearch(quicksearchtxtfield.getText())));
            count();
            settablewidth();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void comboBoxSetValue() {
        String gname;
        groupcombobox.removeAllItems();
        groupcombobox.addItem("Select Group");
        try {
            con = new DBConnection().getConnection();
            String query = "SELECT *FROM contactgrouptbl";

            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                gname = rs.getString("contactgroupname");
                groupcombobox.addItem(gname);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void backup() {
        DBBck dbbck = new DBBck();
        BckRestoreDTO brd = new BckRestoreDTO();
        JFileChooserSave jfcs = new JFileChooserSave(brd);
        dbbck.contactmgmtbck(brd);
        loadDatas();
        count();
    }

    public void restore() {
        DBRestore dbrest = new DBRestore();
        BckRestoreDTO brd = new BckRestoreDTO();
        JFileChooserOpen jfco = new JFileChooserOpen();
        jfco.open(brd);
        dbrest.contactmgmttblRestore(brd);
        loadDatas();
        count();

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ContactPopUpMenu = new javax.swing.JPopupMenu();
        editpopupmenu = new javax.swing.JMenuItem();
        deletepopupmenu = new javax.swing.JMenuItem();
        refreshpopupmenu = new javax.swing.JMenuItem();
        seperator1 = new javax.swing.JPopupMenu.Separator();
        exporttocsvpopupmenu = new javax.swing.JMenuItem();
        importtocsvpopupmenu = new javax.swing.JMenuItem();
        recordpanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        insertbtn = new javax.swing.JButton();
        editbtn = new javax.swing.JButton();
        deletebtn = new javax.swing.JButton();
        contactidtxtfield = new javax.swing.JTextField();
        firstnametxtfield = new javax.swing.JTextField();
        lastnametxtfield = new javax.swing.JTextField();
        groupcombobox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        relationtxtfield = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        contactnotxtfield = new javax.swing.JTextField();
        addresstxtfield = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tblpanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        contacttbl = new javax.swing.JTable();
        uppanel = new javax.swing.JPanel();
        searchlab = new javax.swing.JLabel();
        searchcombobox = new javax.swing.JComboBox();
        SearchBtn = new javax.swing.JButton();
        searchtxtfield = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        quicksearchtxtfield = new javax.swing.JTextField();
        savebtn = new javax.swing.JButton();
        openbtn = new javax.swing.JButton();
        addgroupbtn = new javax.swing.JButton();
        statuspanel = new javax.swing.JPanel();
        downseperator = new javax.swing.JSeparator();
        noofrecordslab = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        editpopupmenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/pen.png"))); // NOI18N
        editpopupmenu.setText("Edit");
        editpopupmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editpopupmenuActionPerformed(evt);
            }
        });
        ContactPopUpMenu.add(editpopupmenu);

        deletepopupmenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/bin-3.png"))); // NOI18N
        deletepopupmenu.setText("Delete");
        deletepopupmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletepopupmenuActionPerformed(evt);
            }
        });
        ContactPopUpMenu.add(deletepopupmenu);

        refreshpopupmenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/refresh.png"))); // NOI18N
        refreshpopupmenu.setText("Refresh");
        refreshpopupmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshpopupmenuActionPerformed(evt);
            }
        });
        ContactPopUpMenu.add(refreshpopupmenu);
        ContactPopUpMenu.add(seperator1);

        exporttocsvpopupmenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/exporttoexcel.png"))); // NOI18N
        exporttocsvpopupmenu.setText("Export To Excel");
        exporttocsvpopupmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exporttocsvpopupmenuActionPerformed(evt);
            }
        });
        ContactPopUpMenu.add(exporttocsvpopupmenu);

        importtocsvpopupmenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/importfromexcel.png"))); // NOI18N
        importtocsvpopupmenu.setText("Import From Excel");
        importtocsvpopupmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importtocsvpopupmenuActionPerformed(evt);
            }
        });
        ContactPopUpMenu.add(importtocsvpopupmenu);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dashboard");
        setBackground(java.awt.Color.gray);
        setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
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

        recordpanel.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel1.setText("Contact ID :-");

        jLabel2.setText("First Name :-");

        jLabel3.setText("Group");

        insertbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/add.png"))); // NOI18N
        insertbtn.setText("Insert");
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

        deletebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/bin-3.png"))); // NOI18N
        deletebtn.setText("Delete");
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
            }
        });

        contactidtxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        contactidtxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactidtxtfieldActionPerformed(evt);
            }
        });

        firstnametxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        firstnametxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnametxtfieldActionPerformed(evt);
            }
        });

        lastnametxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lastnametxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lastnametxtfieldKeyPressed(evt);
            }
        });

        groupcombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Group" }));
        groupcombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupcomboboxActionPerformed(evt);
            }
        });

        jLabel5.setText("LastName :-");

        jLabel6.setText("Relation :-");

        relationtxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        relationtxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                relationtxtfieldKeyPressed(evt);
            }
        });

        jLabel8.setText("Contact No :-");

        contactnotxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        contactnotxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactnotxtfieldActionPerformed(evt);
            }
        });
        contactnotxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contactnotxtfieldKeyPressed(evt);
            }
        });

        addresstxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        addresstxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addresstxtfieldKeyPressed(evt);
            }
        });

        jLabel9.setText("Address :-");

        javax.swing.GroupLayout recordpanelLayout = new javax.swing.GroupLayout(recordpanel);
        recordpanel.setLayout(recordpanelLayout);
        recordpanelLayout.setHorizontalGroup(
            recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(recordpanelLayout.createSequentialGroup()
                        .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addresstxtfield)
                            .addComponent(contactnotxtfield)
                            .addComponent(relationtxtfield)
                            .addComponent(contactidtxtfield)
                            .addComponent(lastnametxtfield)
                            .addComponent(firstnametxtfield)
                            .addComponent(groupcombobox, 0, 149, Short.MAX_VALUE)))
                    .addGroup(recordpanelLayout.createSequentialGroup()
                        .addComponent(insertbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deletebtn)
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );

        recordpanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deletebtn, editbtn, insertbtn});

        recordpanelLayout.setVerticalGroup(
            recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactidtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstnametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastnametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(relationtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addresstxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactnotxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertbtn)
                    .addComponent(editbtn)
                    .addComponent(deletebtn))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        recordpanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addresstxtfield, contactidtxtfield, contactnotxtfield, deletebtn, firstnametxtfield, groupcombobox, jLabel1, jLabel2, jLabel3, jLabel5, jLabel6, jLabel8, jLabel9, lastnametxtfield, relationtxtfield});

        tblpanel.setInheritsPopupMenu(true);

        contacttbl.setForeground(java.awt.Color.darkGray);
        contacttbl.setModel(new javax.swing.table.DefaultTableModel(
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
        contacttbl.setComponentPopupMenu(ContactPopUpMenu);
        contacttbl.setEditingRow(1);
        contacttbl.setInheritsPopupMenu(true
        );
        contacttbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contacttblKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(contacttbl);

        javax.swing.GroupLayout tblpanelLayout = new javax.swing.GroupLayout(tblpanel);
        tblpanel.setLayout(tblpanelLayout);
        tblpanelLayout.setHorizontalGroup(
            tblpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tblpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        tblpanelLayout.setVerticalGroup(
            tblpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tblpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );

        searchlab.setText("    Search");

        searchcombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Contact Id", "Firstname", "Lastname", "Relation", "Contact No", "Address", "Group" }));
        searchcombobox.setPreferredSize(new java.awt.Dimension(6, 20));

        SearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/search.png"))); // NOI18N
        SearchBtn.setText("Search");
        SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBtnActionPerformed(evt);
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

        savebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/exporttoexcel.png"))); // NOI18N
        savebtn.setText("Save");
        savebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebtnActionPerformed(evt);
            }
        });

        openbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/importfromexcel.png"))); // NOI18N
        openbtn.setText("Open");
        openbtn.setMaximumSize(new java.awt.Dimension(83, 27));
        openbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openbtnActionPerformed(evt);
            }
        });

        addgroupbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/add.png"))); // NOI18N
        addgroupbtn.setText("Add Group");
        addgroupbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addgroupbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout uppanelLayout = new javax.swing.GroupLayout(uppanel);
        uppanel.setLayout(uppanelLayout);
        uppanelLayout.setHorizontalGroup(
            uppanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uppanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchlab, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SearchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addgroupbtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(savebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(openbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(quicksearchtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        uppanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {SearchBtn, addgroupbtn, savebtn});

        uppanelLayout.setVerticalGroup(
            uppanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uppanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(uppanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchlab, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchBtn)
                    .addComponent(searchtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(quicksearchtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(savebtn)
                    .addComponent(openbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addgroupbtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        uppanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {SearchBtn, addgroupbtn, quicksearchtxtfield, searchtxtfield});

        noofrecordslab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        noofrecordslab.setText("No Of Records :-");

        javax.swing.GroupLayout statuspanelLayout = new javax.swing.GroupLayout(statuspanel);
        statuspanel.setLayout(statuspanelLayout);
        statuspanelLayout.setHorizontalGroup(
            statuspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statuspanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statuspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statuspanelLayout.createSequentialGroup()
                        .addComponent(noofrecordslab, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(downseperator, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        statuspanelLayout.setVerticalGroup(
            statuspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statuspanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(downseperator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(noofrecordslab)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(uppanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statuspanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(recordpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tblpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(uppanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recordpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(tblpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statuspanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void firstnametxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnametxtfieldActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_firstnametxtfieldActionPerformed

    private void insertbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertbtnActionPerformed
        // TODO add your handling code here:
        check();
        insert();
        checkcontactid();
        loadDatas();
        count();
    }//GEN-LAST:event_insertbtnActionPerformed


    private void groupcomboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupcomboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_groupcomboboxActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        // TODO add your handling code here:
        DeleteMethodUsingArray();
        loadDatas();
        count();
    }//GEN-LAST:event_deletebtnActionPerformed

    private void contacttblKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contacttblKeyPressed
        // TODO add your handling code here:
        int keycode = evt.getKeyCode();
        if (evt.getSource() == contacttbl) {
            if (keycode == KeyEvent.VK_DELETE) {
                DeleteMethodUsingArray();
                count();
            }
        }

    }//GEN-LAST:event_contacttblKeyPressed

    private void editbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbtnActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_editbtnActionPerformed

    private void SearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBtnActionPerformed
        // TODO add your handling code here:
        searchdata();
    }//GEN-LAST:event_SearchBtnActionPerformed

    private void searchtxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtxtfieldKeyPressed
        // TODO add your handling code here:
        int keycode = evt.getKeyCode();
        if (evt.getSource() == searchtxtfield) {
            if (keycode == KeyEvent.VK_ENTER) {
                loadDatasSearch();
                count();
            }

        }
    }//GEN-LAST:event_searchtxtfieldKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_formKeyPressed


    private void lastnametxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lastnametxtfieldKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_lastnametxtfieldKeyPressed

    private void editbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editbtnKeyPressed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_editbtnKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.setIconImage(new ImageIcon(getClass().getResource("home.png")).getImage());

    }//GEN-LAST:event_formWindowOpened

    private void searchtxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchtxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchtxtfieldActionPerformed

    private void quicksearchtxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quicksearchtxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quicksearchtxtfieldActionPerformed

    private void quicksearchtxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quicksearchtxtfieldKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_quicksearchtxtfieldKeyPressed

    private void quicksearchtxtfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quicksearchtxtfieldKeyReleased
        // TODO add your handling code here:
        loadDataQuickSearch();
    }//GEN-LAST:event_quicksearchtxtfieldKeyReleased

    private void contactidtxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactidtxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactidtxtfieldActionPerformed

    private void relationtxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_relationtxtfieldKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_relationtxtfieldKeyPressed

    private void contactnotxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactnotxtfieldKeyPressed
        // TODO add your handling code here:
        int keycode = evt.getKeyCode();
        if (evt.getSource() == contactnotxtfield) {
            if (keycode == KeyEvent.VK_ENTER) {
                check();
                insert();
                count();
                loadDatas();
                checkcontactid();
            }
        }
    }//GEN-LAST:event_contactnotxtfieldKeyPressed

    private void addresstxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addresstxtfieldKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_addresstxtfieldKeyPressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        DashboardUI dui = new DashboardUI();
        dui.visible();
    }//GEN-LAST:event_formWindowClosed

    private void savebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebtnActionPerformed
        // TODO add your handling code here:
        backup();

    }//GEN-LAST:event_savebtnActionPerformed

    private void openbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openbtnActionPerformed
        // TODO add your handling code here:
        restore();

    }//GEN-LAST:event_openbtnActionPerformed

    private void contactnotxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactnotxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactnotxtfieldActionPerformed

    private void addgroupbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addgroupbtnActionPerformed
        // TODO add your handling code here:     
        String groupname = JOptionPane.showInputDialog(null, "Add the name of the group ", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        String uppergroupname = groupname.substring(0, 1).toUpperCase() + groupname.substring(1);
        new ContactGroupDAO().insertRecord(uppergroupname);
        comboBoxSetValue();
    }//GEN-LAST:event_addgroupbtnActionPerformed

    private void editpopupmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editpopupmenuActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_editpopupmenuActionPerformed

    private void deletepopupmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletepopupmenuActionPerformed
        // TODO add your handling code here:
        DeleteMethodUsingArray();
        reset();
    }//GEN-LAST:event_deletepopupmenuActionPerformed

    private void refreshpopupmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshpopupmenuActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_refreshpopupmenuActionPerformed

    private void exporttocsvpopupmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exporttocsvpopupmenuActionPerformed
        // TODO add your handling code here:
        backup();


    }//GEN-LAST:event_exporttocsvpopupmenuActionPerformed

    private void importtocsvpopupmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importtocsvpopupmenuActionPerformed
        // TODO add your handling code here:
        restore();
    }//GEN-LAST:event_importtocsvpopupmenuActionPerformed

    private void searchtxtfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchtxtfieldKeyReleased
        // TODO add your handling code here:
        int keycode = evt.getKeyCode();
        if (keycode == KeyEvent.VK_BACK_SPACE && searchtxtfield.getText().length() == 0) {
            reset();
            loadDatas();
            count();
        }
    }//GEN-LAST:event_searchtxtfieldKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ContactUI().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu ContactPopUpMenu;
    private javax.swing.JButton SearchBtn;
    private javax.swing.JButton addgroupbtn;
    private javax.swing.JTextField addresstxtfield;
    private javax.swing.JTextField contactidtxtfield;
    private javax.swing.JTextField contactnotxtfield;
    private javax.swing.JTable contacttbl;
    private javax.swing.JButton deletebtn;
    private javax.swing.JMenuItem deletepopupmenu;
    private javax.swing.JSeparator downseperator;
    private javax.swing.JButton editbtn;
    private javax.swing.JMenuItem editpopupmenu;
    private javax.swing.JMenuItem exporttocsvpopupmenu;
    private javax.swing.JTextField firstnametxtfield;
    private javax.swing.JComboBox groupcombobox;
    private javax.swing.JMenuItem importtocsvpopupmenu;
    private javax.swing.JButton insertbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField lastnametxtfield;
    private javax.swing.JLabel noofrecordslab;
    private javax.swing.JButton openbtn;
    private javax.swing.JTextField quicksearchtxtfield;
    private javax.swing.JPanel recordpanel;
    private javax.swing.JMenuItem refreshpopupmenu;
    private javax.swing.JTextField relationtxtfield;
    private javax.swing.JButton savebtn;
    private javax.swing.JComboBox searchcombobox;
    private javax.swing.JLabel searchlab;
    private javax.swing.JTextField searchtxtfield;
    private javax.swing.JPopupMenu.Separator seperator1;
    private javax.swing.JPanel statuspanel;
    private javax.swing.JPanel tblpanel;
    private javax.swing.JPanel uppanel;
    // End of variables declaration//GEN-END:variables
}
