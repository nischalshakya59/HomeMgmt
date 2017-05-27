package homemgmtsystem.ui;

import homemgmtsystem.dao.CategoryDAO;
import homemgmtsystem.db.DBBck;
import homemgmtsystem.db.DBConnection;
import homemgmtsystem.db.DBRestore;
import homemgmtsystem.dto.BckRestoreDTO;
import homemgmtsystem.dto.CategoryDTO;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class AddCategory extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pstmt;
    DashboardUI dui = new DashboardUI();

    //public boolean rtn = false;
    public AddCategory() {
        initComponents();

        this.setTitle("Add Particular");
        loadDatas();
        setCatid();
        categoryidtxtfield.setEnabled(false);

    }

    @SuppressWarnings("unchecked")

    public void DeleteMethodUsingArray() {
        try {
            con = new DBConnection().getConnection();
            int[] rows = cattable.getSelectedRows();

            if (rows.length != 0) {
                int clicked = JOptionPane.showConfirmDialog(null, "Are you Sure You want to delete " + rows.length + " Record ?", "Home Management System",
                        JOptionPane.YES_NO_OPTION);

                if (clicked == 0) {
                    int clickedSecond = JOptionPane.showConfirmDialog(null, "Warning!!! This Will Delete All The Releated Record From The Main Table As Well", "Home Management System", JOptionPane.YES_NO_OPTION);
                    if (clickedSecond == 0) {
                        for (int i = 0; i < rows.length; i++) {
                            String catid = Integer.toString((int) cattable.getValueAt(rows[i], 0));
                            String[] strArray = new String[]{catid};
                            new CategoryDAO().deleteRows(strArray);
                        }
                    }

                }
            }
            loadDatas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void addCategory() {
        CategoryDTO cdto = new CategoryDTO();
        if (categorynametxtfield.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Particular Name Should Not Be Empty?","Home Management System",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {

            cdto.setCatid(Integer.parseInt(categoryidtxtfield.getText()));

            String catname = categorynametxtfield.getText();

            String catnameUpperCase = catname.substring(0, 1).toUpperCase() + catname.substring(1);
            cdto.setCatname(catnameUpperCase);

            new CategoryDAO().Addcat(cdto);

            loadDatas();

            setCatid();
            categorynametxtfield.setText("");

        }
    }

    public void setCatid() {
        CategoryDAO CatDAO = new CategoryDAO();
        int catid = CatDAO.CatId() + 1;
        categoryidtxtfield.setText(String.valueOf(catid));
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        recordpnl = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        categoryidtxtfield = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        categorynametxtfield = new javax.swing.JTextField();
        savebttn = new javax.swing.JButton();
        deletebtn = new javax.swing.JButton();
        savebtn = new javax.swing.JButton();
        openbtn = new javax.swing.JButton();
        tablepnl = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cattable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Category Frame"); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Particular Id :-");

        categoryidtxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        categoryidtxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryidtxtfieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Particular Name :-");

        categorynametxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        categorynametxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                categorynametxtfieldKeyPressed(evt);
            }
        });

        savebttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/add.png"))); // NOI18N
        savebttn.setText("Add");
        savebttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebttnActionPerformed(evt);
            }
        });

        deletebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/bin-3.png"))); // NOI18N
        deletebtn.setText("Delete");
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
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
        openbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout recordpnlLayout = new javax.swing.GroupLayout(recordpnl);
        recordpnl.setLayout(recordpnlLayout);
        recordpnlLayout.setHorizontalGroup(
            recordpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordpnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recordpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(recordpnlLayout.createSequentialGroup()
                        .addGroup(recordpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(savebttn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(savebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(recordpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deletebtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(openbtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(recordpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(recordpnlLayout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(categorynametxtfield))
                        .addGroup(recordpnlLayout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(categoryidtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        recordpnlLayout.setVerticalGroup(
            recordpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordpnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recordpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(recordpnlLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(categoryidtxtfield)))
                .addGap(18, 18, 18)
                .addGroup(recordpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categorynametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(recordpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(savebttn)
                    .addComponent(deletebtn))
                .addGap(18, 18, 18)
                .addGroup(recordpnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(savebtn)
                    .addComponent(openbtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        recordpnlLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {categoryidtxtfield, categorynametxtfield});

        cattable.setModel(new javax.swing.table.DefaultTableModel(
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
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cattable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cattableMouseClicked(evt);
            }
        });
        cattable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cattableKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(cattable);

        javax.swing.GroupLayout tablepnlLayout = new javax.swing.GroupLayout(tablepnl);
        tablepnl.setLayout(tablepnlLayout);
        tablepnlLayout.setHorizontalGroup(
            tablepnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablepnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                .addContainerGap())
        );
        tablepnlLayout.setVerticalGroup(
            tablepnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablepnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(recordpnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tablepnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tablepnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(recordpnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void savebttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebttnActionPerformed
        // TODO add your handling code here:
        addCategory();
    }//GEN-LAST:event_savebttnActionPerformed

    private void categoryidtxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryidtxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoryidtxtfieldActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        DeleteMethodUsingArray();
    }//GEN-LAST:event_deletebtnActionPerformed


    private void categorynametxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_categorynametxtfieldKeyPressed
        // TODO add your handling code here:
        int key = evt.getKeyCode();
        if (evt.getSource() == categorynametxtfield) {
            if (key == KeyEvent.VK_ENTER) {
                addCategory();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Particular Name" + categorynametxtfield.getText() + "Already Exist", "Home Management System",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_categorynametxtfieldKeyPressed

    private void cattableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cattableKeyPressed
        // TODO add your handling code here:
        int key = evt.getKeyCode();
        int[] rows = cattable.getSelectedRows();
        if (evt.getSource() == cattable && rows.length != 0) {
            if (key == KeyEvent.VK_DELETE) {
                DeleteMethodUsingArray();
            }
        }
    }//GEN-LAST:event_cattableKeyPressed

    private void cattableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cattableMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cattableMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.setIconImage(new ImageIcon(getClass().getResource("home.png")).getImage());
        //FrameDTO fdto = new FrameDTO();
        //fdto.setOpen(false);
    }//GEN-LAST:event_formWindowOpened

    private void savebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebtnActionPerformed
        // TODO add your handling code here:
        DBBck dbbck = new DBBck();
        BckRestoreDTO brd = new BckRestoreDTO();
        JFileChooserSave jfcs = new JFileChooserSave(brd);
        dbbck.particulartblbck(brd);

    }//GEN-LAST:event_savebtnActionPerformed

    private void openbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openbtnActionPerformed
        // TODO add your handling code here:
        DBRestore dbrest = new DBRestore();
        BckRestoreDTO brd = new BckRestoreDTO();
        JFileChooserOpen jfco = new JFileChooserOpen();
        jfco.open(brd);
        dbrest.particulartblRestore(brd);
        loadDatas();
    }//GEN-LAST:event_openbtnActionPerformed

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:

    }//GEN-LAST:event_formComponentHidden

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        dui.visible();
    }//GEN-LAST:event_formWindowClosed

    public void loadDatas() {
        try {
            CategoryDAO catdao = new CategoryDAO();
            cattable.setModel(catdao.buildTableModel(catdao.getQueryResult()));
            for (int c = 0; c < cattable.getColumnCount(); c++) {
                Class<?> col_class = cattable.getColumnClass(c);
                cattable.setDefaultEditor(col_class, null);
            }
            cattable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            cattable.getColumnModel().getColumn(0).setPreferredWidth(27);
            cattable.getColumnModel().getColumn(1).setPreferredWidth(60);
            cattable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

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
            java.util.logging.Logger.getLogger(AddCategory.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddCategory.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddCategory.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddCategory.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddCategory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField categoryidtxtfield;
    private javax.swing.JTextField categorynametxtfield;
    private javax.swing.JTable cattable;
    private javax.swing.JButton deletebtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton openbtn;
    private javax.swing.JPanel recordpnl;
    private javax.swing.JButton savebtn;
    private javax.swing.JButton savebttn;
    private javax.swing.JPanel tablepnl;
    // End of variables declaration//GEN-END:variables
}
