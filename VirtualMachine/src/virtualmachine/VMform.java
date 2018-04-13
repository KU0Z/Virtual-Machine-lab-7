/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualmachine;

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author keviu
 */
public class VMform extends javax.swing.JFrame {

    /**
     * Creates new form VMform
     */
    LinkedList<String> lista= new LinkedList<String>();
    LinkedList<String> resultado= new LinkedList<String>();
    public VMform() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        TALeido = new javax.swing.JTextArea();
        BtnLeer = new javax.swing.JButton();
        Btnrun = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TAresultado = new javax.swing.JTextArea();
        BTnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TALeido.setColumns(20);
        TALeido.setRows(5);
        jScrollPane2.setViewportView(TALeido);

        BtnLeer.setText("Leer Archivo");
        BtnLeer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLeerActionPerformed(evt);
            }
        });

        Btnrun.setText("Correr");
        Btnrun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnrunActionPerformed(evt);
            }
        });

        TAresultado.setColumns(20);
        TAresultado.setRows(5);
        jScrollPane3.setViewportView(TAresultado);

        BTnGuardar.setText("Guardar Archivo");
        BTnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtnLeer)
                        .addGap(18, 18, 18)
                        .addComponent(Btnrun)
                        .addGap(18, 18, 18)
                        .addComponent(BTnGuardar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnLeer)
                    .addComponent(Btnrun)
                    .addComponent(BTnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        public String ObtenerRuta(Component a)
    {
     String nuevaRuta = "";
     JFileChooser dialog = new JFileChooser();
     FileNameExtensionFilter filtro = new FileNameExtensionFilter("VitualMachine", "vm");
     File archivo;
     dialog.setFileFilter(filtro);
     if(dialog.showOpenDialog(a) == JFileChooser.APPROVE_OPTION)
     {
        Btnrun.setEnabled(true);
        archivo = dialog.getSelectedFile();
        nuevaRuta = archivo.getPath();
     }
     return nuevaRuta;
    }
    public void GuardarRuta(Component a) throws IOException
    {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(a) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            FileWriter fw = new FileWriter(file.getPath());
            fw.write(TAresultado.getText());
            fw.close();
            
  // save to file
        }
    }
    public void LeerArchivo()
    {
        String ruta=ObtenerRuta(this);
        String Linea ="";
        lista= new LinkedList<String>();
        resultado=new LinkedList<String>();
        TALeido.setText(null);
        TAresultado.setText(null);
        try
        {
            //Si el archivo existe, lo leerá una linea a la vez
            File Archivo = new File(ruta);
            if(Archivo.exists())
            {
            int númLinea = 0;
            RandomAccessFile LeerArchivo = new RandomAccessFile(Archivo,"rw");
            Linea = LeerArchivo.readLine();
            String[] values;
            while(Linea != null)  //Leemos linea por linea hasta el final.
            {            
                if(!"".equals(Linea)){
                    if(!Linea.substring(0,2).equals("//")){
                        if(Linea.contains("//")){
                            values=Linea.split("//");
                            lista.add(values[0].trim());
                            TALeido.append(values[0].trim()+"\n");
                        }
                        else{
                            lista.add(Linea.trim());                 
                            TALeido.append(Linea.trim()+"\n");   
                        }
                    }                                        
                }
                númLinea=(int)LeerArchivo.getFilePointer();
                Linea=LeerArchivo.readLine();
            }
            
            //Cerrar el lector utilizado
            LeerArchivo.close();
            }
        }
        catch(Exception ex){}
    }
    public void correr()
    {   Parse parseo=new Parse(lista);
        for (int i = 0; i < parseo.resultado.size(); i++) {
        TAresultado.append(parseo.resultado.get(i));
        }
    
    }
    private void BtnLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLeerActionPerformed
        // TODO add your handling code here:
        LeerArchivo();
    }//GEN-LAST:event_BtnLeerActionPerformed

    private void BtnrunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnrunActionPerformed
        // TODO add your handling code here:
        correr();
        BTnGuardar.setEnabled(true);
    }//GEN-LAST:event_BtnrunActionPerformed

    private void BTnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTnGuardarActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Assembler", "asm");
        fileChooser.setFileFilter(filtro);
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileWriter fw = new FileWriter(file.getPath()+".asm");
             
                    fw.write(TAresultado.getText());
                
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(VMform.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_BTnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(VMform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VMform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VMform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VMform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VMform().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTnGuardar;
    private javax.swing.JButton BtnLeer;
    private javax.swing.JButton Btnrun;
    private javax.swing.JTextArea TALeido;
    private javax.swing.JTextArea TAresultado;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
