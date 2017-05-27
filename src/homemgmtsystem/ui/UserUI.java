package homemgmtsystem.ui;

import homemgmtsystem.dao.UserDAO;
import homemgmtsystem.dto.UserDTO;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class UserUI extends javax.swing.JFrame {

    UserDAO udao = new UserDAO();
    UserDTO udto = new UserDTO();

    public UserUI() {
        initComponents();
        useridtxtfield.setEditable(false);
        setuserid();
        loadDatas();
        this.setResizable(false);
        this.setTitle("UserManagement");

    }

    @SuppressWarnings("unchecked")

    String getusertype() {
        if (adminradiobtn.isSelected()) {
            return "admin";
        } else if (userradiobtn.isSelected()) {
            return "user";
        } else {
            return null;
        }
    }

    public void setuserid() {
        int userid = udao.getuserid() + 1;
        useridtxtfield.setText(Integer.toString(userid));
    }

    public void reset() {
        firstnametxtfield.setText("");
        lastnametxtfield.setText("");
        contactnotxtfield.setText("");
        usernametxtfield.setText("");
        passwordtxtfield.setText("");
        setuserid();
        loadDatas();
        count();
    }

    public void insert() {
        udto.setUserid(Integer.parseInt(useridtxtfield.getText()));

        udto.setFirstname(firstnametxtfield.getText().substring(0, 1).toUpperCase() + firstnametxtfield.getText().substring(1));
        udto.setLastname(lastnametxtfield.getText().substring(0, 1).toUpperCase() + lastnametxtfield.getText().substring(1));
        udto.setContactno(contactnotxtfield.getText());
        udto.setUsername(usernametxtfield.getText());
        udto.setPassword(String.valueOf(passwordtxtfield.getPassword()));
        udto.setUsertype(getusertype());

        if (firstnametxtfield.getText().length() != 0 && lastnametxtfield.getText().length() != 0 && contactnotxtfield.getText().length() != 0
                && usernametxtfield.getText().length() != 0 && getusertype() != null) {
            udao.insertRecord(udto);
        }
        reset();
    }

    public void count() {
        noofrecordlab.setText("No Of Records:- " + usertbl.getRowCount());
    }

    public void check() {
        if (firstnametxtfield.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Please Enter The First Name", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            firstnametxtfield.requestFocus(true);
        }

        if (lastnametxtfield.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Please Enter The Last Name", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            lastnametxtfield.requestFocus(true);
        }

        if (contactnotxtfield.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Please Enter The Contact No", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            contactnotxtfield.requestFocus(true);
        }

        if (usernametxtfield.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Please Enter The User Name", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            usernametxtfield.requestFocus(true);
        }

        if (Arrays.toString(passwordtxtfield.getPassword()).length() == 0) {
            JOptionPane.showMessageDialog(null, "Please Enter The Password", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            passwordtxtfield.requestFocus(true);
        }
        if (getusertype() == null) {
            JOptionPane.showMessageDialog(null, "Please Specify The User Type", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void update() {

        int rowupdate = usertbl.getSelectedRowCount(); // return the no of selected row

        if (rowupdate < 2) {
            if (rowupdate != 0) {
                if (editbtn.getText().equalsIgnoreCase("Edit")) {
                    editbtn.setText("Update");
                    insertbtn.setEnabled(false);

                    UserDTO udto = new UserDAO().editUser(usertbl);

                    useridtxtfield.setEditable(false);
                    useridtxtfield.setText(Integer.toString(udto.getUserid()));
                    firstnametxtfield.setText(udto.getFirstname());
                    lastnametxtfield.setText(udto.getLastname());
                    contactnotxtfield.setText(udto.getContactno());
                    usernametxtfield.setText(udto.getUsername());
                    passwordtxtfield.setText(udto.getPassword());

                } else {
                    insertbtn.setEnabled(true);
                    editbtn.setText("Edit");

                    udto.setUserid(Integer.parseInt(useridtxtfield.getText()));
                    udto.setFirstname(firstnametxtfield.getText().substring(0, 1).toUpperCase() + firstnametxtfield.getText().substring(1));
                    udto.setLastname(lastnametxtfield.getText().substring(0, 1).toUpperCase() + lastnametxtfield.getText().substring(1));
                    udto.setContactno(contactnotxtfield.getText());
                    udto.setUsername(usernametxtfield.getText());
                    udto.setPassword(String.valueOf(passwordtxtfield.getPassword()));
                    udto.setUsertype(getusertype());

                    new UserDAO().updateUser(udto);

                    reset();
                    editbtn.setText("Edit");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Select The One Row To Update", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                reset();
            }

        } else {
            JOptionPane.showMessageDialog(null, "You Can Only Edit Or Update One Row At A Time", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            reset();
        }
    }

    public void DeleteMethodUsingArray() {
        try {
            // Connection con = new DBConnection().getConnection();
            int[] rows = usertbl.getSelectedRows();
            int getselectedrow = usertbl.getSelectedRow();
            if (getselectedrow == 0) {
                deletebtn.setEnabled(false);
            } else {
                if (rows.length != 0) { // return the no of selected rows in transtable
                    int clicked = JOptionPane.showConfirmDialog(null, "Are you Suren You want to delete " + rows.length + " Record ?", "Home Management System",
                            JOptionPane.YES_NO_OPTION);

                    if (clicked == 0) {
                        for (int i = 0; i < rows.length; i++) {
                            String userid = Integer.toString((int) usertbl.getValueAt(rows[i], 0));

                            String[] strArray = new String[]{userid};
                            udao.deleteRows(strArray);
                        }
                    }
                }
            }

            loadDatas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Manamgement System", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(e);
        }
        reset();
    }

    public void loadDatas() {
        try {
            for (int c = 0; c < usertbl.getColumnCount(); c++) {
                Class<?> col_class = usertbl.getColumnClass(c);
                usertbl.setDefaultEditor(col_class, null);
            }
            usertbl.setModel(udao.buildTableModel(udao.getQueryResult()));

            usertbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            usertbl.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            usertbl.setRowSelectionAllowed(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Manamgement System", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        mainpanel = new javax.swing.JPanel();
        recordpanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        useridtxtfield = new javax.swing.JTextField();
        firstnametxtfield = new javax.swing.JTextField();
        lastnametxtfield = new javax.swing.JTextField();
        usernametxtfield = new javax.swing.JTextField();
        contactnotxtfield = new javax.swing.JTextField();
        insertbtn = new javax.swing.JButton();
        editbtn = new javax.swing.JButton();
        deletebtn = new javax.swing.JButton();
        passwordtxtfield = new javax.swing.JPasswordField();
        jLabel13 = new javax.swing.JLabel();
        adminradiobtn = new javax.swing.JRadioButton();
        userradiobtn = new javax.swing.JRadioButton();
        tablepanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        usertbl = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        noofrecordlab = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel7.setText("User ID:-");

        jLabel8.setText("First Name :-");

        jLabel9.setText("Last Name:-");

        jLabel10.setText("Username :-");

        jLabel11.setText("Password");

        jLabel12.setText("Contact No:-");

        useridtxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        firstnametxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lastnametxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        usernametxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        contactnotxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);

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

        deletebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/bin-3.png"))); // NOI18N
        deletebtn.setText("Delete");
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
            }
        });

        passwordtxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel13.setText("User Type :-");

        buttonGroup1.add(adminradiobtn);
        adminradiobtn.setText("Admin");

        buttonGroup1.add(userradiobtn);
        userradiobtn.setText("User ");

        javax.swing.GroupLayout recordpanelLayout = new javax.swing.GroupLayout(recordpanel);
        recordpanel.setLayout(recordpanelLayout);
        recordpanelLayout.setHorizontalGroup(
            recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, recordpanelLayout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userradiobtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(adminradiobtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, recordpanelLayout.createSequentialGroup()
                        .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(recordpanelLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(useridtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(recordpanelLayout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(firstnametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(recordpanelLayout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lastnametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(recordpanelLayout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(contactnotxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(recordpanelLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(usernametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, recordpanelLayout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(passwordtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, recordpanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(insertbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deletebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        recordpanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deletebtn, editbtn, insertbtn});

        recordpanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {contactnotxtfield, firstnametxtfield, lastnametxtfield, passwordtxtfield, useridtxtfield, usernametxtfield});

        recordpanelLayout.setVerticalGroup(
            recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(useridtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstnametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastnametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactnotxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminradiobtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userradiobtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(recordpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deletebtn)
                    .addComponent(editbtn)
                    .addComponent(insertbtn))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        recordpanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {deletebtn, editbtn, insertbtn});

        recordpanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {adminradiobtn, contactnotxtfield, firstnametxtfield, jLabel13, lastnametxtfield, passwordtxtfield, useridtxtfield, usernametxtfield, userradiobtn});

        recordpanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel7, jLabel8, jLabel9});

        usertbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        usertbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usertblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(usertbl);

        javax.swing.GroupLayout tablepanelLayout = new javax.swing.GroupLayout(tablepanel);
        tablepanel.setLayout(tablepanelLayout);
        tablepanelLayout.setHorizontalGroup(
            tablepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablepanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                .addContainerGap())
        );
        tablepanelLayout.setVerticalGroup(
            tablepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablepanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        noofrecordlab.setText("No Of Records :- ");

        javax.swing.GroupLayout mainpanelLayout = new javax.swing.GroupLayout(mainpanel);
        mainpanel.setLayout(mainpanelLayout);
        mainpanelLayout.setHorizontalGroup(
            mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(mainpanelLayout.createSequentialGroup()
                        .addComponent(recordpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tablepanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(mainpanelLayout.createSequentialGroup()
                        .addComponent(noofrecordlab, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 757, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainpanelLayout.setVerticalGroup(
            mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(recordpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tablepanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noofrecordlab, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void insertbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertbtnActionPerformed
        // TODO add your handling code here:
        check();
        insert();
    }//GEN-LAST:event_insertbtnActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        // TODO add your handling code here:
        DeleteMethodUsingArray();
    }//GEN-LAST:event_deletebtnActionPerformed

    private void editbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbtnActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_editbtnActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        DashboardUI dui = new DashboardUI();
        dui.visible();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.setIconImage(new ImageIcon(getClass().getResource("home.png")).getImage());
        reset();
    }//GEN-LAST:event_formWindowOpened

    private void usertblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usertblMouseClicked
        // TODO add your handling code here:
        int row = usertbl.rowAtPoint(evt.getPoint());
        if (row == 0) {
            deletebtn.setEnabled(false);
            editbtn.setEnabled(false);
        } else {
            deletebtn.setEnabled(true);
            editbtn.setEnabled(true);
        }
    }//GEN-LAST:event_usertblMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton adminradiobtn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField contactnotxtfield;
    private javax.swing.JButton deletebtn;
    private javax.swing.JButton editbtn;
    private javax.swing.JTextField firstnametxtfield;
    private javax.swing.JButton insertbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField lastnametxtfield;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JLabel noofrecordlab;
    private javax.swing.JPasswordField passwordtxtfield;
    private javax.swing.JPanel recordpanel;
    private javax.swing.JPanel tablepanel;
    private javax.swing.JTextField useridtxtfield;
    private javax.swing.JTextField usernametxtfield;
    private javax.swing.JRadioButton userradiobtn;
    private javax.swing.JTable usertbl;
    // End of variables declaration//GEN-END:variables
}
