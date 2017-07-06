package Formularios;

import Entidades.Producto;
import Logico.ProductoLog;
import ModeloTabla.ModeloTablaProducto;
import static Formularios.frmLogin.res;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
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

public class FrmProducto extends javax.swing.JInternalFrame {

    ProductoLog productos;
    Producto prd;
    ModeloTablaProducto mtp;
    /*ModeloTablaUsuario mtp;*/
    
    public FrmProducto() {
        initComponents();
        productos = new ProductoLog();
        ListarTabla();
        Limpiar();
        lblllaveproducto.setVisible(false);
    }

    private void llenarexcel()
    {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet ws = wb.createSheet();
            
            //load data to treemap
            TreeMap<String,Object[]> data = new TreeMap<>();
            int pos = 0;
            data.put("0",new Object[]{mtp.getColumnName(0), mtp.getColumnName(1),mtp.getColumnName(2), mtp.getColumnName(3), mtp.getColumnName(4), mtp.getColumnName(5)});
            
            for (Producto prd : mtp.productos) {
                data.put(String.valueOf(pos + 1),new Object[]{mtp.getValueAt(pos,0), mtp.getValueAt(pos,1),mtp.getValueAt(pos,2), mtp.getValueAt(pos,3), mtp.getValueAt(pos,4), mtp.getValueAt(pos,5)});
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
                
                FileOutputStream fos= new FileOutputStream(new File("c:/excel/ExcelProducto.xlsx"));
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
        String filtroNSN = txt_filtroNSN.getText();
        String filtroParte = txt_filtroParte.getText();
        
        List<Producto> listas = productos.listado(0,filtroNSN , "", filtroParte);
        mtp = new ModeloTablaProducto(listas);
        
        /*List<Usuario> listas = usuarios.listado(filtroNSN);
        mtp = new ModeloTablaUsuario(listas);*/
        
        jTable1.setModel(mtp);
        jTable1.getRowSorter();
    }

    public void Limpiar() {
        
        txt_nsn.setText("");
        txt_descripcion.setText("");
        txtnombre.setText("");
        txt_parte.setText("");
        txt_valor.setText("");
        txt_stock.setText("");
        txt_ubicacion.setText("");
        txt_filtroNSN.setText("");
        txt_filtroParte.setText("");
        
        txt_nsn.setEnabled(false);
        txtnombre.setEnabled(false);
        txt_descripcion.setEnabled(false);
        txt_parte.setEnabled(false);
        txt_valor.setEnabled(false);
        txt_stock.setEnabled(false);
        txt_ubicacion.setEnabled(false);
        txt_filtroNSN.setEnabled(true);
        txt_filtroParte.setEnabled(true);
        
        btn_Nuevo.setEnabled(true);
        btn_grabar.setEnabled(false);
        btn_Eliminar.setEnabled(false);
        btn_Cancelar.setEnabled(false);
        btn_salir.setEnabled(true);
        btn_exportar.setEnabled(true);
        PnlIngreso.setEnabled(false);
        lblllaveproducto.setText("0");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnlIngreso = new javax.swing.JPanel();
        lblvallor = new javax.swing.JLabel();
        lblnombre = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblstock = new javax.swing.JLabel();
        lblnsn = new javax.swing.JLabel();
        lbldescripcion = new javax.swing.JLabel();
        lblubicacion = new javax.swing.JLabel();
        lblParte = new javax.swing.JLabel();
        lblllaveproducto = new javax.swing.JLabel();
        txt_nsn = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        txt_descripcion = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_parte = new javax.swing.JTextArea();
        txt_valor = new javax.swing.JTextField();
        txt_stock = new javax.swing.JTextField();
        txt_ubicacion = new javax.swing.JTextField();
        pnlBotones = new javax.swing.JPanel();
        btn_salir = new javax.swing.JButton();
        btn_Nuevo = new javax.swing.JButton();
        btn_Eliminar = new javax.swing.JButton();
        btn_grabar = new javax.swing.JButton();
        btn_Cancelar = new javax.swing.JButton();
        btn_exportar = new javax.swing.JButton();
        pnlbusqueda = new javax.swing.JPanel();
        btn_buscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblTituloFiltro = new javax.swing.JLabel();
        txt_filtroNSN = new javax.swing.JTextField();
        lblfiltrolbl = new javax.swing.JLabel();
        lblfiltroparte = new javax.swing.JLabel();
        txt_filtroParte = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        PnlIngreso.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        PnlIngreso.setPreferredSize(new java.awt.Dimension(522, 260));

        lblvallor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblvallor.setText("Valor");

        lblnombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblnombre.setText("Nombre Producto");

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Productos");

        lblstock.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblstock.setText("Q stock");

        lblnsn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblnsn.setText("N.S.N");

        lbldescripcion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbldescripcion.setText("Descripción");

        lblubicacion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblubicacion.setText("Ubicación");

        lblParte.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblParte.setText("Nº Parte");

        txt_parte.setColumns(20);
        txt_parte.setRows(5);
        jScrollPane2.setViewportView(txt_parte);

        javax.swing.GroupLayout PnlIngresoLayout = new javax.swing.GroupLayout(PnlIngreso);
        PnlIngreso.setLayout(PnlIngresoLayout);
        PnlIngresoLayout.setHorizontalGroup(
            PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlIngresoLayout.createSequentialGroup()
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlIngresoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PnlIngresoLayout.createSequentialGroup()
                                .addComponent(lblTitulo)
                                .addGap(59, 59, 59)
                                .addComponent(lblllaveproducto)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(PnlIngresoLayout.createSequentialGroup()
                                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblParte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblnsn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbldescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                    .addComponent(lblvallor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblstock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblubicacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_valor)
                                    .addComponent(txt_descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                                    .addComponent(txt_ubicacion)))))
                    .addGroup(PnlIngresoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblnombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        PnlIngresoLayout.setVerticalGroup(
            PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlIngresoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(lblllaveproducto))
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblnsn)
                    .addComponent(txt_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbldescripcion)
                    .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlIngresoLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblvallor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblstock)))
                    .addComponent(lblParte))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblubicacion)
                    .addComponent(txt_ubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        pnlBotones.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlBotones.setPreferredSize(new java.awt.Dimension(200, 260));

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

        javax.swing.GroupLayout pnlBotonesLayout = new javax.swing.GroupLayout(pnlBotones);
        pnlBotones.setLayout(pnlBotonesLayout);
        pnlBotonesLayout.setHorizontalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBotonesLayout.createSequentialGroup()
                        .addGroup(pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_grabar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_Nuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_Eliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_Cancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btn_salir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_exportar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlBotonesLayout.setVerticalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesLayout.createSequentialGroup()
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlbusqueda.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });

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
        lblTituloFiltro.setText("Filtro de Bodega");

        lblfiltrolbl.setText("N.S.N");

        lblfiltroparte.setText("Nº Parte");

        javax.swing.GroupLayout pnlbusquedaLayout = new javax.swing.GroupLayout(pnlbusqueda);
        pnlbusqueda.setLayout(pnlbusquedaLayout);
        pnlbusquedaLayout.setHorizontalGroup(
            pnlbusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlbusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlbusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(pnlbusquedaLayout.createSequentialGroup()
                        .addGroup(pnlbusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTituloFiltro)
                            .addGroup(pnlbusquedaLayout.createSequentialGroup()
                                .addComponent(lblfiltrolbl)
                                .addGap(35, 35, 35)
                                .addComponent(txt_filtroNSN, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblfiltroparte)
                                .addGap(44, 44, 44)
                                .addComponent(txt_filtroParte, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlbusquedaLayout.setVerticalGroup(
            pnlbusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlbusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloFiltro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlbusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_buscar)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlbusquedaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlbusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblfiltrolbl)
                            .addComponent(txt_filtroNSN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblfiltroparte)
                            .addComponent(txt_filtroParte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlbusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PnlIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(137, 137, 137))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PnlIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlbusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void btn_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoActionPerformed

        txt_nsn.setText("");
        txtnombre.setText("");
        txt_descripcion.setText("");
        txt_stock.setText("");
        txt_ubicacion.setText("");
        txt_parte.setText("");
        txt_valor.setText("");
        txt_filtroNSN.setText("");
        txt_filtroParte.setText("");
        
        txt_nsn.setEnabled(true);
        txtnombre.setEnabled(true);
        txt_descripcion.setEnabled(true);
        txt_stock.setEnabled(true);
        txt_ubicacion.setEnabled(true);
        txt_parte.setEnabled(true);
        txt_valor.setEnabled(true);
        
        
        
        btn_grabar.setEnabled(true);
        btn_Cancelar.setEnabled(true);
        PnlIngreso.setEnabled(true);
        lblllaveproducto.setText("0");

        btn_Eliminar.setEnabled(false);
        btn_Nuevo.setEnabled(false);
        btn_salir.setEnabled(false);
        btn_exportar.setEnabled(false);
        
        btn_grabar.setEnabled(true);
        btn_Cancelar.setEnabled(true);
        btn_Eliminar.setEnabled(true);

    }//GEN-LAST:event_btn_NuevoActionPerformed

    private void btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarActionPerformed
        boolean resp = productos.DeleteProducto(prd);
        if (resp == false) {
            JOptionPane.showMessageDialog(null, "Dato Eliminado");
            ListarTabla();
            Limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Dato no Eliminado");
        }
    }//GEN-LAST:event_btn_EliminarActionPerformed

    private boolean validar_formulario()
    {
        boolean validar = false;
        
        if(txt_stock.getText().trim()=="" 
                || new String(txt_descripcion.getText()).trim()==""
                
                )
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
    
    private void btn_grabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_grabarActionPerformed

        if(validar_formulario()){

            boolean resp = false;
            Producto prd;

            int id_producto = Integer.parseInt(lblllaveproducto.getText());

            if(id_producto ==0){
                prd = new Producto(id_producto, txt_nsn.getText(),txtnombre.getText(), txt_descripcion.getText(), txt_parte.getText()
                        , Integer.parseInt(txt_stock.getText()), Integer.parseInt(txt_valor.getText()), txt_ubicacion.getText(),"","","",1);
                resp = productos.AgregarProducto(prd);
            }
            else
            {
                prd = new Producto(id_producto, txt_nsn.getText(),txtnombre.getText(),txt_descripcion.getText(),txt_parte.getText()
                        ,Integer.parseInt(txt_stock.getText()), Integer.parseInt(txt_valor.getText()), txt_ubicacion.getText(),"","","",1);
                resp = productos.UpdateProducto(prd);
            }

            if (resp == false) {
                JOptionPane.showMessageDialog(null, "Dato Agregdo");
                ListarTabla();
                Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Dato no Agregdo");
            }

        }

    }//GEN-LAST:event_btn_grabarActionPerformed

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        Limpiar();
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void btn_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarActionPerformed
        llenarexcel();
    }//GEN-LAST:event_btn_exportarActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        ListarTabla();
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        prd = ((ModeloTablaProducto) jTable1.getModel()).DameProducto(jTable1.getSelectedRow());

        lblllaveproducto.setText(String.valueOf(prd.getIdproducto()));
        txt_nsn.setText(prd.getNsn());
        txtnombre.setText(prd.getNombre());
        txt_descripcion.setText(prd.getDescripcion());
        txt_parte.setText(prd.getN_parte());
        txt_valor.setText(String.valueOf(prd.getValor_unitario()));
        txt_stock.setText(String.valueOf(prd.getCantidad()));
        txt_ubicacion.setText(prd.getUbicacion());
        
        txt_nsn.setEnabled(true);
        txtnombre.setEnabled(true);
        txt_descripcion.setEnabled(true);
        txt_parte.setEnabled(true);
        txt_valor.setEnabled(true);
        txt_stock.setEnabled(true);
        txt_ubicacion.setEnabled(true);
        PnlIngreso.setEnabled(true);
        btn_Nuevo.setEnabled(false);
        btn_Eliminar.setEnabled(true);
        btn_grabar.setEnabled(true);
        btn_Cancelar.setEnabled(true);
        btn_salir.setEnabled(false);
        btn_exportar.setEnabled(false);
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnlIngreso;
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JButton btn_Eliminar;
    private javax.swing.JButton btn_Nuevo;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_exportar;
    private javax.swing.JButton btn_grabar;
    private javax.swing.JButton btn_salir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblParte;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloFiltro;
    private javax.swing.JLabel lbldescripcion;
    private javax.swing.JLabel lblfiltrolbl;
    private javax.swing.JLabel lblfiltroparte;
    private javax.swing.JLabel lblllaveproducto;
    private javax.swing.JLabel lblnombre;
    private javax.swing.JLabel lblnsn;
    private javax.swing.JLabel lblstock;
    private javax.swing.JLabel lblubicacion;
    private javax.swing.JLabel lblvallor;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlbusqueda;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JTextField txt_filtroNSN;
    private javax.swing.JTextField txt_filtroParte;
    private javax.swing.JTextField txt_nsn;
    private javax.swing.JTextArea txt_parte;
    private javax.swing.JTextField txt_stock;
    private javax.swing.JTextField txt_ubicacion;
    private javax.swing.JTextField txt_valor;
    private javax.swing.JTextField txtnombre;
    // End of variables declaration//GEN-END:variables
}