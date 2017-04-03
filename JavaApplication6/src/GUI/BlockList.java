/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.DefaultListModel;
import network.ChatClient;

/**
 *
 * @author Lenovo
 */
public class BlockList extends javax.swing.JDialog {

    public static DefaultListModel<String> blocked;
    /**
     * Creates new form BlockList
     */
    public BlockList(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setList();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public BlockList(){
        blocked = new DefaultListModel<String>();
    }
    
    public void setList(){
        jBlockedList.setModel(blocked);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jBlockedList = new javax.swing.JList<>();
        bttnUnblock = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jBlockedList.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        jBlockedList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jBlockedList.setToolTipText("");
        jScrollPane1.setViewportView(jBlockedList);

        bttnUnblock.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        bttnUnblock.setText("Unblock");
        bttnUnblock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnUnblockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(bttnUnblock)
                .addContainerGap(108, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bttnUnblock)
                .addGap(9, 9, 9))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bttnUnblockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnUnblockActionPerformed
        // TODO add your handling code here:
        ChatClient.unblockUser(jBlockedList.getSelectedValue());
        FriendsForm.addFriend(jBlockedList.getSelectedValue());
        blocked.removeElement(jBlockedList.getSelectedValue());
        jBlockedList.setModel(blocked);
    }//GEN-LAST:event_bttnUnblockActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttnUnblock;
    public static javax.swing.JList<String> jBlockedList;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
