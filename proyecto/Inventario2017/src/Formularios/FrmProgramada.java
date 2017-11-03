package Formularios;

import ModeloTabla.CmbZonal;
import ModeloTabla.CmbSistema;
import ModeloTabla.CmbUnidad;
import ModeloTabla.ModeloTablaView_Producto;
import Entidades.Producto;
import Logico.ProductoLog;

import Entidades.SolicitudProgramada;
import Logico.SolicitudProgramadaLog;

import Entidades.SolicitudProgramadaDetalle;
import Logico.SolicitudProgramadaDetalleLog;
import ModeloTabla.ModeloTablaProramadaDetalle;

import clases.Inventario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import org.apache.poi.hwpf.model.FileInformationBlock;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class FrmProgramada extends javax.swing.JInternalFrame {

    ProductoLog productos;
    Producto prd;
    ModeloTablaView_Producto mtp;
    
    SolicitudProgramadaLog programadas;
    SolicitudProgramada pgr;

    SolicitudProgramadaDetalleLog programadasdetalle;
    SolicitudProgramadaDetalle pgrd;
    ModeloTablaProramadaDetalle mtpd;
    
    String id_usuario;
    
    public FrmProgramada() {
        initComponents();
        int id_usuario = Integer.parseInt(Inventario.global_llaveusuario);
        productos = new ProductoLog();
        programadas = new SolicitudProgramadaLog();
        programadasdetalle = new SolicitudProgramadaDetalleLog();
        CargarCombo();
        Limpiar();
        CargarProgramaPendiente();
        
        lblllaveproducto.setVisible(false);
        lblllaveprogramada.setVisible(false);
        lblllaveprogramadadetalle.setVisible(false);
    }
    
    private void CargarProgramaPendiente()
    {
        try
        {
            String llave = Inventario.global_llaveusuario;
            int llave_usuario = Integer.parseInt(Inventario.global_llaveusuario);
            
            ResultSet objResultSet;
            objResultSet = Conecciones.Coneccion.consulta(" SELECT solicitud_programada.id_programada, solicitud_programada.id_sistema, solicitud_programada.id_unidad, solicitud_programada.pista, solicitud_programada.marca, solicitud_programada.modelo, " +
                                                            " solicitud_programada.fecha_solicitud, solicitud_programada.estado, solicitud_programada.id_usuario, isnull(unidad.id_zonal,0) as id_zonal, unidad.nombre_unidad " +
                                                            " FROM solicitud_programada left JOIN " +
                                                            " unidad ON solicitud_programada.id_unidad = unidad.id_unidad " +
                                                            " WHERE (solicitud_programada.id_usuario = "+ llave_usuario +") AND (solicitud_programada.estado = 2) ");
                    

            while (objResultSet.next())
            {
                
                lblllaveprogramada.setText(objResultSet.getString("id_programada"));
                pnlproducto.setEnabled(true);
                pnlgrid.setEnabled(true);
                pnlinfo.setEnabled(true);

                cbxsistema.setEnabled(true);
                cbxzonal.setEnabled(true);
                cbxunidad.setEnabled(true);
                txtfecha.setEnabled(true);
                txtpista.setEnabled(true);
                txtmarca.setEnabled(true);
                txtmodelo.setEnabled(true);
                txtfecha.setEnabled(true);

                txtpista.setText(objResultSet.getString("pista"));
                txtmarca.setText(objResultSet.getString("marca"));
                txtmodelo.setText(objResultSet.getString("modelo"));
                
                if(objResultSet.getString("fecha_solicitud") != null)
                {
                  Date date = objResultSet.getDate("fecha_solicitud");
                  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                  String reportDate = df.format(date);
                  txtfecha.setText(reportDate);    
                  
                } else {
                    txtfecha.setValue(new Date());
                }

                btn_nuevo.setEnabled(false);
                btn_buscarproducto.setEnabled(true);
                btn_agregar.setEnabled(false);
                btn_eliminar.setEnabled(false);
                btn_limpiar.setEnabled(true);
                btn_finalizar.setEnabled(true);

                txt_nsn.setText("");
                txt_producto.setText("");
                txt_cantidad.setText("");
                txt_descripcion.setText("");
                txt_numeroparte.setText("");

                txt_nsn.setEnabled(true);
                txt_producto.setEnabled(true);
                txt_cantidad.setEnabled(true);
                txt_descripcion.setEnabled(true);
                txt_numeroparte.setEnabled(true);
                
                
                if(objResultSet.getString("id_sistema") != "0")
                {
                    cbxsistema.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_sistema")));
                }
                
                if(objResultSet.getString("id_zonal") != "0")
                {
                    cbxzonal.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_zonal")));
                    
                    cargaUnidad();
                    
                    if(objResultSet.getString("id_unidad") != "0")
                    {
                        cbxunidad.getModel().setSelectedItem(new CmbUnidad(objResultSet.getString("nombre_unidad"), objResultSet.getString("id_unidad")));
                        /*cbxunidad.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_unidad")));*/
                    }
                }

                ListarTabla();
                ListarTablaproducto();
            }
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getCause());
        }
    }
    
    public void CargarCombo()
    {
        try
        {
            DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
            ResultSet objResultSet;
            objResultSet = Conecciones.Coneccion.consulta(" select * from Zonal ");
            modeloCombo.addElement(new CmbZonal("Seleccione Zona", "0"));
            cbxzonal.setModel(modeloCombo);//con esto lo agregamos al objeto al jcombobox
            while (objResultSet.next())
            {
                modeloCombo.addElement(new CmbZonal(objResultSet.getString("nombre_zonal") , objResultSet.getString("id_zonal")));
                cbxzonal.setModel(modeloCombo);
            }

            DefaultComboBoxModel modeloSistema = new DefaultComboBoxModel();
            ResultSet objResultSetsistema;
            objResultSetsistema = Conecciones.Coneccion.consulta(" select * from sistema ");
            modeloSistema.addElement(new CmbSistema("Seleccione Sistema", "0"));
            cbxsistema.setModel(modeloSistema);//con esto lo agregamos al objeto al jcombobox
            while (objResultSetsistema.next())
            {
                modeloSistema.addElement(new CmbSistema(objResultSetsistema.getString("nombre_sistema") , objResultSetsistema.getString("id_sistema")));
                cbxsistema.setModel(modeloSistema);
            }
            
            DefaultComboBoxModel modeloUnidad = new DefaultComboBoxModel();
            modeloUnidad.addElement(new CmbUnidad("Seleccione Unidad", "0"));
            cbxunidad.setModel(modeloUnidad);//con esto lo agregamos al objeto al jcombobox
            cbxunidad.setEnabled(false);
         } 
        catch (Exception ex) 
        {
            System.out.println(ex.getCause());
        }
    }
    
    public void Limpiar()
    {
        pnlproducto.setEnabled(false);
        pnlgrid.setEnabled(false);
        
        cbxsistema.setEnabled(false);
        cbxzonal.setEnabled(false);
        cbxunidad.setEnabled(false);
        txtfecha.setEnabled(false);
        txtpista.setEnabled(false);
        txtmarca.setEnabled(false);
        txtmodelo.setEnabled(false);
        txtfecha.setEnabled(false);
        
        txtfecha.setText("");
        txtpista.setText("");
        txtmarca.setText("");
        txtmodelo.setText("");
        txtfecha.setValue(new Date());
        
        btn_nuevo.setEnabled(true);
        btn_buscarproducto.setEnabled(false);
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(false);
        btn_finalizar.setEnabled(false);
        
        txt_nsn.setText("");
        txt_producto.setText("");
        txt_cantidad.setText("");
        txt_descripcion.setText("");
        txt_numeroparte.setText("");
        
        txt_nsn.setEnabled(false);
        txt_producto.setEnabled(false);
        txt_cantidad.setEnabled(false);
        txt_descripcion.setEnabled(false);
        txt_numeroparte.setEnabled(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_titulo = new javax.swing.JLabel();
        pnlinfo = new javax.swing.JPanel();
        lblfecha = new javax.swing.JLabel();
        txtfecha = new javax.swing.JFormattedTextField();
        lblsistema = new javax.swing.JLabel();
        cbxsistema = new javax.swing.JComboBox<>();
        lblzonal = new javax.swing.JLabel();
        cbxzonal = new javax.swing.JComboBox<>();
        lblunidad = new javax.swing.JLabel();
        cbxunidad = new javax.swing.JComboBox<>();
        lblpista = new javax.swing.JLabel();
        lblmarca = new javax.swing.JLabel();
        lblmodelo = new javax.swing.JLabel();
        txtpista = new javax.swing.JTextField();
        txtmarca = new javax.swing.JTextField();
        txtmodelo = new javax.swing.JTextField();
        lblllaveprogramada = new javax.swing.JLabel();
        pnlproducto = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        lblllaveproducto = new javax.swing.JLabel();
        lblllaveprogramadadetalle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txt_nsn = new javax.swing.JTextField();
        lbl_nsn = new javax.swing.JLabel();
        lblproducto = new javax.swing.JLabel();
        txt_producto = new javax.swing.JTextField();
        txt_descripcion = new javax.swing.JTextField();
        lbl_descripcion = new javax.swing.JLabel();
        lbl_cantidad = new javax.swing.JLabel();
        txt_cantidad = new javax.swing.JTextField();
        lbl_numeroparte = new javax.swing.JLabel();
        txt_numeroparte = new javax.swing.JTextField();
        btn_agregar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_limpiar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btn_buscarproducto = new javax.swing.JButton();
        btn_refrescarProducto = new javax.swing.JButton();
        pnlgrid = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_nuevo = new javax.swing.JButton();
        btn_finalizar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        lbl_titulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_titulo.setText("PROGRAMACION ANUAL");

        pnlinfo.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION DE PEDIDO"));

        lblfecha.setText("Fecha");

        txtfecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        lblsistema.setText("Sistema / Equipo");

        cbxsistema.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblzonal.setText("Zonal");

        cbxzonal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxzonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxzonalActionPerformed(evt);
            }
        });

        lblunidad.setText("Unidad");

        cbxunidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxunidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxunidadActionPerformed(evt);
            }
        });

        lblpista.setText("Pista");

        lblmarca.setText("Marca");

        lblmodelo.setText("Modelo");

        txtpista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpistaActionPerformed(evt);
            }
        });

        txtmodelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmodeloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlinfoLayout = new javax.swing.GroupLayout(pnlinfo);
        pnlinfo.setLayout(pnlinfoLayout);
        pnlinfoLayout.setHorizontalGroup(
            pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlinfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlinfoLayout.createSequentialGroup()
                        .addComponent(lblmarca, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtmarca, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblpista, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlinfoLayout.createSequentialGroup()
                        .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblfecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlinfoLayout.createSequentialGroup()
                                .addComponent(lblzonal, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 3, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlinfoLayout.createSequentialGroup()
                                .addComponent(cbxzonal, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(lblunidad, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxunidad, 0, 210, Short.MAX_VALUE)
                    .addComponent(txtpista))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlinfoLayout.createSequentialGroup()
                        .addComponent(lblmodelo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlinfoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblllaveprogramada)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlinfoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtmodelo, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(pnlinfoLayout.createSequentialGroup()
                        .addComponent(lblsistema, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxsistema, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        pnlinfoLayout.setVerticalGroup(
            pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlinfoLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblfecha)
                    .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblllaveprogramada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlinfoLayout.createSequentialGroup()
                        .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblzonal)
                            .addComponent(cbxzonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblunidad)
                            .addComponent(cbxunidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblpista)
                            .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblmarca)
                                .addComponent(txtpista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblmodelo)
                                .addComponent(txtmodelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtmarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblsistema)
                        .addComponent(cbxsistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlproducto.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION REPUESTO"));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable2);

        lbl_nsn.setText("N.S.N");

        lblproducto.setText("Repuesto");

        lbl_descripcion.setText("Descripción");

        lbl_cantidad.setText("Cantidad");

        lbl_numeroparte.setText("Numero Parte");

        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_agregar.png"))); // NOI18N
        btn_agregar.setText("Agregar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_eliminar.png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        btn_limpiar.setText("Limpiar");
        btn_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lbl_nsn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblproducto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_producto, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                            .addComponent(txt_nsn))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_numeroparte, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_numeroparte, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nsn)
                    .addComponent(txt_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_numeroparte)
                    .addComponent(txt_numeroparte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblproducto)
                    .addComponent(txt_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_descripcion)
                    .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_cantidad)
                                .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btn_agregar)
                                .addComponent(btn_eliminar)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btn_limpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_buscarproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_buscar.png"))); // NOI18N
        btn_buscarproducto.setText("Buscar");
        btn_buscarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarproductoActionPerformed(evt);
            }
        });

        btn_refrescarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_refresh.png"))); // NOI18N
        btn_refrescarProducto.setText("Refrescar");
        btn_refrescarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refrescarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_refrescarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_buscarproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btn_buscarproducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_refrescarProducto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlproductoLayout = new javax.swing.GroupLayout(pnlproducto);
        pnlproducto.setLayout(pnlproductoLayout);
        pnlproductoLayout.setHorizontalGroup(
            pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlproductoLayout.createSequentialGroup()
                        .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlproductoLayout.createSequentialGroup()
                                .addGap(463, 463, 463)
                                .addComponent(lblllaveprogramadadetalle))
                            .addGroup(pnlproductoLayout.createSequentialGroup()
                                .addGap(983, 983, 983)
                                .addComponent(lblllaveproducto))
                            .addGroup(pnlproductoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlproductoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 988, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlproductoLayout.setVerticalGroup(
            pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblllaveproducto)
                .addGap(297, 297, 297)
                .addComponent(lblllaveprogramadadetalle)
                .addGap(225, 225, 225))
        );

        pnlgrid.setBorder(javax.swing.BorderFactory.createTitledBorder("REPUESTOS SOLICITADOS"));

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

        javax.swing.GroupLayout pnlgridLayout = new javax.swing.GroupLayout(pnlgrid);
        pnlgrid.setLayout(pnlgridLayout);
        pnlgridLayout.setHorizontalGroup(
            pnlgridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlgridLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        pnlgridLayout.setVerticalGroup(
            pnlgridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlgridLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_nuevo.png"))); // NOI18N
        btn_nuevo.setText("Nuevo Registro");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });

        btn_finalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_finish.png"))); // NOI18N
        btn_finalizar.setText("Finalizar");
        btn_finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_finalizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlgrid, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbl_titulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_finalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addComponent(pnlinfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_titulo)
                    .addComponent(btn_nuevo)
                    .addComponent(btn_finalizar))
                .addGap(1, 1, 1)
                .addComponent(pnlinfo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlgrid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        pgrd = ((ModeloTablaProramadaDetalle) jTable1.getModel()).DameProgramadaDetalle(jTable1.getSelectedRow());

        lblllaveprogramadadetalle.setText(String.valueOf(pgrd.getIdProgramadaDetalle()));
        
        txt_nsn.setText(pgrd.getNsn());
        txt_numeroparte.setText(pgrd.getNumeroparte());
        txt_descripcion.setText(pgrd.getDescripcion());
        txt_producto.setText(pgrd.getProduto());
        txt_cantidad.setText(String.valueOf(pgrd.getCantidad()));
        
        btn_buscarproducto.setEnabled(false);
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(true);
        btn_limpiar.setEnabled(true);
        
    }//GEN-LAST:event_jTable1MouseClicked

    
    
    private void cbxzonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxzonalActionPerformed
            cargaUnidad();
    }
    
    private void cargaUnidad()
    {
        try
        {
            CmbZonal items = (CmbZonal)cbxzonal.getSelectedItem();
            int id_zonal = Integer.parseInt(items.getID());

            if(id_zonal>0){


                DefaultComboBoxModel modelo = new DefaultComboBoxModel();
                ResultSet objResultSet;
                objResultSet = Conecciones.Coneccion.consulta(" select * from unidad where id_zonal = "+ id_zonal + "" );
                modelo.addElement(new CmbUnidad("Seleccione Unidad", "0"));
                cbxunidad.setModel(modelo);//con esto lo agregamos al objeto al jcombobox
                while (objResultSet.next())
                {
                    modelo.addElement(new CmbUnidad(objResultSet.getString("nombre_unidad") , objResultSet.getString("id_unidad")));
                    cbxunidad.setModel(modelo);
                }
                
                cbxunidad.setEnabled(true);
            }
        } 
        catch (Exception ex) 
        {
            System.out.println(ex.getCause());
        }
        
    }//GEN-LAST:event_cbxzonalActionPerformed

    private void btn_buscarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarproductoActionPerformed
        // TODO add your handling code here:
        ListarTablaproducto();
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(true);
    }//GEN-LAST:event_btn_buscarproductoActionPerformed

    private boolean validar_formulario()
    {
        boolean validar = false;
        
        CmbSistema itemsS = (CmbSistema)cbxsistema.getSelectedItem();
        int id_sistema = Integer.parseInt(itemsS.getID());
            
        CmbUnidad itemsU = (CmbUnidad)cbxunidad.getSelectedItem();
        int id_unidad = Integer.parseInt(itemsU.getID());
        
        if(txtfecha.getText().trim()==""
            && id_sistema > 0
            && id_unidad > 0)
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
    
    
    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        try {
        
        int id_usuario = Integer.parseInt(Inventario.global_llaveusuario);
        
        int id_programada = 0;
        SolicitudProgramada Pgr;
        
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        
        Date d = f.parse(txtfecha.getText());
        long milliseconds = d.getTime();
        
        java.sql.Date fecha = new java.sql.Date(milliseconds);
        
        Pgr = new SolicitudProgramada(0,id_usuario, fecha, 2);
        id_programada = programadas.AgregarProgramada(Pgr);
                
        if (id_programada != 0) {
               
                lblllaveprogramada.setText(String.valueOf(id_programada));
                JOptionPane.showMessageDialog(null, "Dato Agregado, favor continuar seleccionado producto");
                ListarTabla();
                ListarTablaproducto();
                ActivarInfo();
                activarProducto();
                btn_nuevo.setEnabled(false);
                btn_finalizar.setEnabled(true);
                
            } else {
                JOptionPane.showMessageDialog(null, "Dato no Agregdo");
            }
        
       } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void activarProducto()
    {
        pnlgrid.setEnabled(true);
        pnlproducto.setEnabled(true);
    
        lblllaveproducto.setText("");
        txt_nsn.setText("");
        txt_producto.setText("");
        txt_cantidad.setText("");
        txt_descripcion.setText("");
        txt_numeroparte.setText("");
        
        txt_nsn.setEnabled(true);
        txt_producto.setEnabled(true);
        txt_numeroparte.setEnabled(true);
        txt_descripcion.setEnabled(false);
        txt_cantidad.setEnabled(false);
        
        btn_buscarproducto.setEnabled(true);
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(false);
        
    }
    
    private void ActivarInfo()
    {
        txtfecha.setEnabled(true);
        txtpista.setEnabled(true);
        txtmarca.setEnabled(true);
        txtmodelo.setEnabled(true);
        cbxsistema.setEnabled(true);
        cbxunidad.setEnabled(true);
        btn_agregar.setEnabled(true);
        
        txtfecha.setEditable(true);
        txtpista.setEditable(true);
        txtmarca.setEditable(true);
        txtmodelo.setEditable(true);
        cbxsistema.setEditable(true);
        cbxunidad.setEditable(true);
        cbxzonal.setEnabled(true);
        
    }
    
    private void txtpistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpistaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpistaActionPerformed

    private void txtmodeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmodeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmodeloActionPerformed

    private void btn_finalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_finalizarActionPerformed

        try {
        
        if(validar_formulario()){

            boolean blx_estado = true;
            
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        
            Date d = f.parse(txtfecha.getText());
            long milliseconds = d.getTime();
        
            java.sql.Date fecha = new java.sql.Date(milliseconds);
            
            int id_programada = Integer.parseInt(lblllaveprogramada.getText());
            SolicitudProgramada Pgr;
            
            int id_usuario = Integer.parseInt(Inventario.global_llaveusuario);

            CmbSistema itemsS = (CmbSistema)cbxsistema.getSelectedItem();
            int id_sistema = Integer.parseInt(itemsS.getID());
            
            CmbUnidad itemsU = (CmbUnidad)cbxunidad.getSelectedItem();
            int id_unidad = Integer.parseInt(itemsU.getID());
            

            String pista = txtpista.getText();
            String marca = txtmarca.getText();
            String modelo = txtmodelo.getText();

            Pgr = new SolicitudProgramada(id_programada,id_sistema, id_unidad, id_usuario, pista, marca, modelo, fecha, 1);
            blx_estado = programadas.UpdateProgramada(Pgr);

            if (blx_estado == false) {
                lblllaveprogramada.setText(String.valueOf(id_programada));
                JOptionPane.showMessageDialog(null, "Solicitud Anual Finalizada con Exito");
                /*Limpiar();
                lblllaveproducto.setText("");
                lblllaveprogramada.setText("");
                lblllaveprogramadadetalle.setText("");
                
                pnlgrid.setEnabled(false);
                pnlinfo.setEnabled(false);
                pnlproducto.setEnabled(false);
                
                LimpiarTabla();
                */
                this.setVisible(false);

                
            } else {
                JOptionPane.showMessageDialog(null, "Dato no Agregdo");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Debe Ingresar toda la informacion solicitada para continuar");
        }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_finalizarActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        
        prd = ((ModeloTablaView_Producto) jTable2.getModel()).DameProducto(jTable2.getSelectedRow());
        activarProducto();
        lblllaveproducto.setText(String.valueOf(prd.getIdproducto()));
        
        txt_producto.setText(prd.getNombre());
        txt_nsn.setText(prd.getNsn());
        txt_numeroparte.setText(prd.getN_parte());
        txt_descripcion.setText(prd.getDescripcion());
        txt_cantidad.setText("");
        
        txt_producto.setEnabled(true);
        txt_nsn.setEnabled(true);   
        txt_numeroparte.setEnabled(true);
        txt_descripcion.setEnabled(true);
        txt_cantidad.setEnabled(true);
        
        btn_agregar.setEnabled(true);
        btn_buscarproducto.setEnabled(true);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(true);

    }//GEN-LAST:event_jTable2MouseClicked

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed

        limpiarproducto();
    }
    
    private void limpiarproducto()
    {
        lblllaveproducto.setText("");
        lblllaveprogramadadetalle.setText("");
        
        txt_producto.setText("");
        txt_nsn.setText("");
        txt_numeroparte.setText("");
        txt_descripcion.setText("");
        txt_cantidad.setText("");
        
        txt_producto.setEnabled(true);
        txt_nsn.setEnabled(true);   
        txt_numeroparte.setEnabled(true);
        
        txt_descripcion.setEnabled(false);
        txt_cantidad.setEnabled(false);
        
        btn_buscarproducto.setEnabled(true);
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(false);
    }//GEN-LAST:event_btn_limpiarActionPerformed
    
    private boolean validar_formulario_producto()
    {
        boolean validar = false;
        
        if(txt_nsn.getText().trim()=="")
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
    
    
    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed

        try {
        
        if(validar_formulario_producto()){

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        
            Date d = f.parse(txtfecha.getText());
            long milliseconds = d.getTime();

            java.sql.Date fecha = new java.sql.Date(milliseconds);
            
            boolean blx_estado = true;
            boolean resp = true;
            SolicitudProgramadaDetalle Pgrd;

            int id_programada = Integer.parseInt(lblllaveprogramada.getText());
            SolicitudProgramada Pgr;
            
            int id_usuario = Integer.parseInt(Inventario.global_llaveusuario);

            CmbSistema itemsS = (CmbSistema)cbxsistema.getSelectedItem();
            int id_sistema = Integer.parseInt(itemsS.getID());
            
            CmbUnidad itemsU = (CmbUnidad)cbxunidad.getSelectedItem();
            int id_unidad = Integer.parseInt(itemsU.getID());
            
            String pista = txtpista.getText();
            String marca = txtmarca.getText();
            String modelo = txtmodelo.getText();

            Pgr = new SolicitudProgramada(id_programada,id_sistema, id_unidad, id_usuario, pista, marca, modelo, fecha, 2);
            blx_estado = programadas.UpdateProgramada(Pgr);
            
            int id_programado = Integer.parseInt(lblllaveprogramada.getText());
            int id_producto = Integer.parseInt(lblllaveproducto.getText());
            int cantidad = Integer.parseInt(txt_cantidad.getText());
            
            Pgrd = new SolicitudProgramadaDetalle(0,id_programado, id_producto, cantidad);
            resp = programadasdetalle.AgregarProgramada(Pgrd);
          

            if (resp == false) {
                JOptionPane.showMessageDialog(null, "Dato Agregado");
                activarProducto();
                ListarTablaproducto();
                ListarTabla();
                
            } else {
                JOptionPane.showMessageDialog(null, "Dato no Agregdo");
            }

        }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        
        boolean resp = programadasdetalle.DeleteProgramada(pgrd);
        if (resp == false) {
            JOptionPane.showMessageDialog(null, "Dato Eliminado");
            
            limpiarproducto();
            ListarTablaproducto();
            ListarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "Dato no Eliminado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_refrescarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refrescarProductoActionPerformed
        limpiarproducto();
        lblllaveproducto.setText("");
        ListarTablaproducto();
       
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_refrescarProductoActionPerformed

    private void cbxunidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxunidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxunidadActionPerformed

    private void ListarTabla() {
        int llaveprogramada = Integer.parseInt(lblllaveprogramada.getText());
        
        List<SolicitudProgramadaDetalle> listas = programadasdetalle.listado(llaveprogramada);
        mtpd = new ModeloTablaProramadaDetalle(listas);
        
        jTable1.setModel(mtpd);
        jTable1.getRowSorter();
    }
    
    private void LimpiarTabla() {
    
        
        List<SolicitudProgramadaDetalle> listas = null;
        mtpd = new ModeloTablaProramadaDetalle(listas);
        
        jTable1.setModel(mtpd);
        jTable1.getRowSorter();
    
        
        List<Producto> lista = null;
        mtp = new ModeloTablaView_Producto(lista);
        
        jTable2.setModel(mtp);
        jTable2.getRowSorter();
    
        
       jTable1.setVisible(false);
       jTable2.setVisible(false);
    }
    
    private void ListarTablaproducto() {
        
        String filtroNSN = txt_nsn.getText();
        String filtroParte = txt_numeroparte.getText();
        String producto = txt_producto.getText();
        
        List<Producto> listas = productos.listado(0, filtroNSN , filtroParte, producto);
        mtp = new ModeloTablaView_Producto(listas);
        
        jTable2.setModel(mtp);
        jTable2.getRowSorter();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_buscarproducto;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_finalizar;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_refrescarProducto;
    private javax.swing.JComboBox<String> cbxsistema;
    private javax.swing.JComboBox<String> cbxunidad;
    private javax.swing.JComboBox<String> cbxzonal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lbl_cantidad;
    private javax.swing.JLabel lbl_descripcion;
    private javax.swing.JLabel lbl_nsn;
    private javax.swing.JLabel lbl_numeroparte;
    private javax.swing.JLabel lbl_titulo;
    private javax.swing.JLabel lblfecha;
    private javax.swing.JLabel lblllaveproducto;
    private javax.swing.JLabel lblllaveprogramada;
    private javax.swing.JLabel lblllaveprogramadadetalle;
    private javax.swing.JLabel lblmarca;
    private javax.swing.JLabel lblmodelo;
    private javax.swing.JLabel lblpista;
    private javax.swing.JLabel lblproducto;
    private javax.swing.JLabel lblsistema;
    private javax.swing.JLabel lblunidad;
    private javax.swing.JLabel lblzonal;
    private javax.swing.JPanel pnlgrid;
    private javax.swing.JPanel pnlinfo;
    private javax.swing.JPanel pnlproducto;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JTextField txt_nsn;
    private javax.swing.JTextField txt_numeroparte;
    private javax.swing.JTextField txt_producto;
    private javax.swing.JFormattedTextField txtfecha;
    private javax.swing.JTextField txtmarca;
    private javax.swing.JTextField txtmodelo;
    private javax.swing.JTextField txtpista;
    // End of variables declaration//GEN-END:variables
}
