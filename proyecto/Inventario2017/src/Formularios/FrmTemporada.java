package Formularios;

import Entidades.Temporada;
import Logico.TemporadaLog;
import ModeloTabla.ModeloTablaTemporada;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.*;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.apache.poi.hwpf.model.FileInformationBlock;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class FrmTemporada extends javax.swing.JInternalFrame {

    TemporadaLog temporadas;
    Temporada tem;
    ModeloTablaTemporada mtt;
    
    public FrmTemporada() {
        initComponents();
        temporadas = new TemporadaLog();
        ListarTabla();
        Limpiar();
        lblLlavetemporada.setVisible(false);
    }

    private void llenarexcel()
    {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet ws = wb.createSheet();
            
            //load data to treemap
            TreeMap<String,Object[]> data = new TreeMap<>();
            int pos = 0;
            data.put("0",new Object[]{mtt.getColumnName(0), mtt.getColumnName(1)});
            
            for (Temporada tem : mtt.Temporada) {
                data.put(String.valueOf(pos + 1),new Object[]{mtt.getValueAt(pos,0), mtt.getValueAt(pos,1)});
                pos++;
            }
            
            Set<String> ids = data.keySet();
            XSSFRow row;
            int rowId=0;
            
            for(String key:ids)
            {
                row=ws.createRow(rowId++);
                Object[] values = data.get(key);
                
                int cellId =0;
                for(Object o : values)
                {
                    Cell cell = row.createCell(cellId++);
                    cell.setCellValue(o.toString());
                }
            }
            try
            {
                Date dtt = new Date();
                
                FileOutputStream fos= new FileOutputStream(new File("c:/excel/temporada.xlsx"));
                wb.write(fos);
                fos.close();
                JOptionPane.showMessageDialog(null, "Datos exportados");
            } 
            catch(FileNotFoundException ex)
            {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error en " + ex);
                
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error en " + ex);
            }
        
            
            
    }
    private void ListarTabla() {
        
        List<Temporada> listas = temporadas.listado();
        mtt = new ModeloTablaTemporada(listas);
        jTable1.setModel(mtt);
        jTable1.getRowSorter();
    }

    public void Limpiar() {
        txt_temporada.setText("");
        chkactiva.setSelected(false);
        
        txt_temporada.setEnabled(false);
        chkactiva.setEnabled(false);
        
        btn_Nuevo.setEnabled(true);
        btn_grabar.setEnabled(false);
        btn_Eliminar.setEnabled(false);
        btn_Cancelar.setEnabled(false);
        btn_salir.setEnabled(true);
        btn_exportar.setEnabled(true);
        PnlIngreso.setEnabled(false);
        lblLlavetemporada.setText("0");
    }
    
    private boolean validar_formulario()
    {
        boolean validar = false;
        
        if(txt_temporada.getText().trim()=="")
        {
            JOptionPane.showMessageDialog(null, "Todos los campo en Negrita son de acesso obligatorio");
            return false;
        } 
        else 
        {
            validar = true;
        }
        return validar;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnlIngreso = new javax.swing.JPanel();
        txt_temporada = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        lbltemporada = new javax.swing.JLabel();
        lblLlavetemporada = new javax.swing.JLabel();
        lblactiva = new javax.swing.JLabel();
        chkactiva = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        btn_salir = new javax.swing.JButton();
        btn_Nuevo = new javax.swing.JButton();
        btn_Eliminar = new javax.swing.JButton();
        btn_grabar = new javax.swing.JButton();
        btn_Cancelar = new javax.swing.JButton();
        btn_exportar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblTituloFiltro = new javax.swing.JLabel();

        PnlIngreso.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        PnlIngreso.setPreferredSize(new java.awt.Dimension(522, 260));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Temporada");

        lbltemporada.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbltemporada.setText("Nombre Temporada");

        lblactiva.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblactiva.setText("Activar Temporada");

        chkactiva.setText("Activar");
        chkactiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkactivaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnlIngresoLayout = new javax.swing.GroupLayout(PnlIngreso);
        PnlIngreso.setLayout(PnlIngresoLayout);
        PnlIngresoLayout.setHorizontalGroup(
            PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlIngresoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlIngresoLayout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(59, 59, 59)
                        .addComponent(lblLlavetemporada))
                    .addGroup(PnlIngresoLayout.createSequentialGroup()
                        .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbltemporada, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblactiva, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chkactiva)
                            .addComponent(txt_temporada, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlIngresoLayout.setVerticalGroup(
            PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlIngresoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(lblLlavetemporada))
                .addGap(32, 32, 32)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbltemporada)
                    .addComponent(txt_temporada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblactiva)
                    .addComponent(chkactiva))
                .addContainerGap(145, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 260));

        btn_salir.setText("Salir");
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });

        btn_Nuevo.setText("Nuevo");
        btn_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoActionPerformed(evt);
            }
        });

        btn_Eliminar.setText("Eliminar");
        btn_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarActionPerformed(evt);
            }
        });

        btn_grabar.setText("Grabar");
        btn_grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_grabarActionPerformed(evt);
            }
        });

        btn_Cancelar.setText("Cancelar");
        btn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelarActionPerformed(evt);
            }
        });

        btn_exportar.setText("Exportar");
        btn_exportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_grabar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_Nuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_Eliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_Cancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btn_salir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_exportar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_exportar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        lblTituloFiltro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTituloFiltro.setText("Busqueda");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTituloFiltro)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloFiltro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PnlIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PnlIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void btn_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoActionPerformed

        txt_temporada.setText("");
        
        
        txt_temporada.setEnabled(true);
        chkactiva.setEnabled(true);

        btn_grabar.setEnabled(true);
        btn_Cancelar.setEnabled(true);
        PnlIngreso.setEnabled(true);
        lblLlavetemporada.setText("0");

        btn_Eliminar.setEnabled(false);
        btn_Nuevo.setEnabled(false);
    }//GEN-LAST:event_btn_NuevoActionPerformed

    private void btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarActionPerformed

        boolean resp = temporadas.DeleteTemporada(tem);
        if (resp == false) {
            JOptionPane.showMessageDialog(null, "Dato Eliminado");
            ListarTabla();
            Limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Dato no Eliminado");
        }
    }//GEN-LAST:event_btn_EliminarActionPerformed

    private void btn_grabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_grabarActionPerformed

        if(validar_formulario()){

            boolean resp = false;
            Temporada tem;
            
            int activa = 0;
            
            if(chkactiva.isSelected())
            {
                activa = 1;
            }
            
            int id_temporada = Integer.parseInt(lblLlavetemporada.getText());

            if(id_temporada ==0){
                tem = new Temporada(id_temporada, txt_temporada.getText(), activa);
                resp = temporadas.AgregarTemporada(tem);
            }
            else
            {
                tem = new Temporada(id_temporada, txt_temporada.getText(), activa);
                resp = temporadas.UpdateTemporada(tem);
            }

            if (resp == false) {
                JOptionPane.showMessageDialog(null, "Dato Agregado");
                ListarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Dato no Agregado");
            }
        }
    }//GEN-LAST:event_btn_grabarActionPerformed

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        Limpiar();
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void btn_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarActionPerformed
        llenarexcel();
    }//GEN-LAST:event_btn_exportarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        tem = ((ModeloTablaTemporada) jTable1.getModel()).DameTemporada(jTable1.getSelectedRow());

        lblLlavetemporada.setText(String.valueOf(tem.getIdTemporada()));
        txt_temporada.setText(tem.getNombreTemporada());
        int activa = tem.getActiva();
        if(activa==1){
            chkactiva.setEnabled(true);
        } 
        else 
        {
            chkactiva.setEnabled(false);
        }

        txt_temporada.setEnabled(true);
        chkactiva.setEnabled(true);
        
        PnlIngreso.setEnabled(true);
        btn_Nuevo.setEnabled(false);
        btn_Eliminar.setEnabled(true);
        btn_grabar.setEnabled(true);
        btn_Cancelar.setEnabled(true);
        btn_salir.setEnabled(false);
        btn_exportar.setEnabled(false);
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void chkactivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkactivaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkactivaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnlIngreso;
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JButton btn_Eliminar;
    private javax.swing.JButton btn_Nuevo;
    private javax.swing.JButton btn_exportar;
    private javax.swing.JButton btn_grabar;
    private javax.swing.JButton btn_salir;
    private javax.swing.JCheckBox chkactiva;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblLlavetemporada;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloFiltro;
    private javax.swing.JLabel lblactiva;
    private javax.swing.JLabel lbltemporada;
    private javax.swing.JTextField txt_temporada;
    // End of variables declaration//GEN-END:variables
}
