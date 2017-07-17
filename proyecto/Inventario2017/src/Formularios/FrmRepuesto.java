package Formularios;

import ModeloTabla.CmbZonal;
import ModeloTabla.CmbSistema;
import ModeloTabla.CmbUnidad;
import ModeloTabla.cmbCondicionSistema;
import ModeloTabla.ModeloTablaProducto;
import Entidades.Producto;
import Logico.ProductoLog;

import Entidades.SolicitudRepuesto;
import Logico.SolicitudRepuestoLog;

import ModeloTabla.ModeloTablaView_ProductosSolicitados;
import Logico.View_ProductosSolicitadosLog;
import Entidades.View_ProductosSolicitados;

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

public class FrmRepuesto extends javax.swing.JInternalFrame {

    ProductoLog productos;
    Producto prd;
    ModeloTablaProducto mtp;
    
    SolicitudRepuestoLog repuesto;
    SolicitudRepuesto srp;
    
    View_ProductosSolicitados viewp;
    View_ProductosSolicitadosLog view_producto;
    ModeloTablaView_ProductosSolicitados vewtabla;
    
    public FrmRepuesto() {
        
        initComponents();
        productos = new ProductoLog();
        repuesto = new SolicitudRepuestoLog();
        view_producto = new View_ProductosSolicitadosLog();
        
        CargarCombo();
        Limpiar();
        lbl_LlaveRepuesto.setVisible(false);
        CargarDatosPendientes();
        
    }

    public void CargarDatosPendientes()
    {
        try
        {
            ResultSet objResultSet;
            objResultSet = Conecciones.Coneccion.consulta(" SELECT unidad.id_zonal, solucitud_repuesto.* FROM solucitud_repuesto  left JOIN  unidad ON solucitud_repuesto.id_unidad = unidad.id_unidad where solucitud_repuesto.estado = 2 ");

            while (objResultSet.next())
            {
                
                ActivarInfo();
                
                lbl_numeroregistro.setText(objResultSet.getString("id_comercial"));
                txt_nombretecnico.setText(objResultSet.getString("nombre_tecnico"));
                txt_correo.setText(objResultSet.getString("correo_electronico"));
                txt_anexo.setText(objResultSet.getString("Anexo"));
                          
                if(objResultSet.getString("id_zonal") != null)
                {
                    cbxzonal.setSelectedItem(objResultSet.getString("id_zonal"));
                    cbx_unidad.setSelectedItem(objResultSet.getString("id_unidad"));
                }
                
                txt_marcaequipo.setText(objResultSet.getString("marca_equipo"));
                txt_modeloequipo.setText(objResultSet.getString("modelo_equipo"));
                txt_numeroserie.setText(objResultSet.getString("numero_Serie"));
                
                if(objResultSet.getString("id_condicionsistema") != null)
                {
                    cbx_condicionsistema.setSelectedItem(objResultSet.getString("id_condicionsistema"));
                }
                
                if(objResultSet.getString("id_sistema") != null)
                {
                    cbx_condicionsistema.setSelectedItem(objResultSet.getString("id_sistema"));
                }
                
                btn_nuevo.setEnabled(false);
                btn_finalizar.setEnabled(true);
                
                ListarTablaBuscar();
                
            }
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getCause());
        }
    }
    
    private void ListarTablaBuscar() {
        
        CmbUnidad items = (CmbUnidad)cbx_unidad.getSelectedItem();
        int id_unidad = Integer.parseInt(items.getID());
        
        CmbSistema itemsis = (CmbSistema)cbx_sistema.getSelectedItem();
        int id_sistema = Integer.parseInt(itemsis.getID());
        
        List<View_ProductosSolicitados> listas = view_producto.listado(id_sistema , id_unidad);
        vewtabla = new ModeloTablaView_ProductosSolicitados(listas);
        
        jTable2.setModel(vewtabla);
        jTable2.getRowSorter();
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
            
            DefaultComboBoxModel modeloUnidad = new DefaultComboBoxModel();
            modeloUnidad.addElement(new CmbSistema("Seleccione Unidad", "0"));
            cbx_unidad.setModel(modeloUnidad);//con esto lo agregamos al objeto al jcombobox
            cbx_unidad.setEnabled(false);
            
            DefaultComboBoxModel modeloSistema = new DefaultComboBoxModel();
            ResultSet objResultSetsistema;
            objResultSetsistema = Conecciones.Coneccion.consulta(" select * from sistema ");
            modeloSistema.addElement(new CmbSistema("Seleccione Sistema", "0"));
            cbx_sistema.setModel(modeloSistema);//con esto lo agregamos al objeto al jcombobox
            while (objResultSetsistema.next())
            {
                modeloSistema.addElement(new CmbSistema(objResultSetsistema.getString("nombre_sistema") , objResultSetsistema.getString("id_sistema")));
                cbx_sistema.setModel(modeloSistema);
            }
            
            DefaultComboBoxModel modeloCondicionSistema = new DefaultComboBoxModel();
            ResultSet objResultSetcondicion;
            objResultSetcondicion = Conecciones.Coneccion.consulta(" select * from condicionsistema ");
            modeloCondicionSistema.addElement(new cmbCondicionSistema("Seleccione Condicion Del Sistema", "0"));
            cbx_condicionsistema.setModel(modeloCondicionSistema);//con esto lo agregamos al objeto al jcombobox
            while (objResultSetcondicion.next())
            {
                modeloCondicionSistema.addElement(new cmbCondicionSistema(objResultSetcondicion.getString("nombre_sistema") , objResultSetcondicion.getString("id_sistema")));
                cbx_condicionsistema.setModel(modeloCondicionSistema);
            }
         } 
        catch (Exception ex) 
        {
            System.out.println(ex.getCause());
        }
    }
    
    public void Limpiar()
    {
        
        btn_finalizar.setEnabled(false);
        
        pnlUsuario.setEnabled(true);
        txt_nombretecnico.setText("");
        txt_correo.setText("");
        txt_anexo.setText("");
        txt_fecha.setValue(new Date());
        
        txt_nombretecnico.setEnabled(false);
        txt_correo.setEnabled(false);
        txt_anexo.setEnabled(false);
        txt_fecha.setEnabled(false);
        cbxzonal.setEnabled(false);
        cbx_unidad.setEnabled(false);

        pnlSistema.setEnabled(false);
        cbx_sistema.setEnabled(false);
        txt_marcaequipo.setEnabled(false);
        txt_modeloequipo.setEnabled(false);
        cbx_condicionsistema.setEnabled(false);
        txt_numeroserie.setEnabled(false);
        
        txt_marcaequipo.setText("");
        txt_modeloequipo.setText("");
        txt_numeroserie.setText("");
        
        txt_nsn.setText("");
        txt_numeroparte.setText("");
        txt_producto.setText("");
        txt_descripcion.setText("");
        txt_cantidad.setText("");
        
        pnlproducto.setEnabled(false);
        txt_nsn.setEnabled(false);
        txt_numeroparte.setEnabled(false);
        txt_producto.setEnabled(false);
        txt_descripcion.setEnabled(false);
        txt_cantidad.setEnabled(false);
        btn_agregar.setEnabled(false);
        btn_buscarproducto.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(false);
        
        /*
        pnlproducto.setEnabled(false);
        pnlgrid.setEnabled(false);
        
        cbxsistema.setEnabled(true);
        cbxzonal.setEnabled(true);
        cbxunidad.setEnabled(false);
        
        txtfecha.setText("");
        txtpista.setText("");
        txtmarca.setText("");
        txtmodelo.setText("");
        txtfecha.setValue(new Date());
        
        btn_buscarproducto.setEnabled(false);
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(false);
        
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
        */
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSistema = new javax.swing.JPanel();
        lbl_sistema = new javax.swing.JLabel();
        cbx_sistema = new javax.swing.JComboBox<>();
        lbl_marcaequipo = new javax.swing.JLabel();
        txt_marcaequipo = new javax.swing.JTextField();
        lbl_condicionsistema = new javax.swing.JLabel();
        cbx_condicionsistema = new javax.swing.JComboBox<>();
        lbl_modeloequipo = new javax.swing.JLabel();
        txt_modeloequipo = new javax.swing.JTextField();
        lbl_numeroserie = new javax.swing.JLabel();
        txt_numeroserie = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        lblzonal1 = new javax.swing.JLabel();
        cbxzonal1 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        pnlUsuario = new javax.swing.JPanel();
        lbl_unidad = new javax.swing.JLabel();
        cbx_unidad = new javax.swing.JComboBox<>();
        lbl_nombretecnico = new javax.swing.JLabel();
        txt_nombretecnico = new javax.swing.JTextField();
        lbl_correo = new javax.swing.JLabel();
        txt_correo = new javax.swing.JTextField();
        lbl_anexo = new javax.swing.JLabel();
        txt_anexo = new javax.swing.JTextField();
        lbl_fechacreacion = new javax.swing.JLabel();
        txt_fecha = new javax.swing.JFormattedTextField();
        lbl_LlaveRepuesto = new javax.swing.JLabel();
        lblzonal = new javax.swing.JLabel();
        cbxzonal = new javax.swing.JComboBox<>();
        lbl_numeroregistro = new javax.swing.JLabel();
        pnlproducto = new javax.swing.JPanel();
        lbl_nsn = new javax.swing.JLabel();
        lbl_numeroparte = new javax.swing.JLabel();
        lbl_descripcion = new javax.swing.JLabel();
        lbl_cantidad = new javax.swing.JLabel();
        txt_numeroparte = new javax.swing.JTextField();
        txt_nsn = new javax.swing.JTextField();
        txt_producto = new javax.swing.JTextField();
        txt_descripcion = new javax.swing.JTextField();
        txt_cantidad = new javax.swing.JTextField();
        btn_agregar = new javax.swing.JButton();
        btn_buscarproducto = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        lblproducto = new javax.swing.JLabel();
        lblllaveproducto = new javax.swing.JLabel();
        btn_limpiar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        lblllaveprogramadadetalle = new javax.swing.JLabel();
        pnlgrid = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_nuevo = new javax.swing.JButton();
        btn_finalizar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        pnlSistema.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION DEL SISTEMA"));

        lbl_sistema.setText("Sistema");

        cbx_sistema.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_sistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_sistemaActionPerformed(evt);
            }
        });

        lbl_marcaequipo.setText("Marca Equipo");

        lbl_condicionsistema.setText("Condición del Sistema");

        cbx_condicionsistema.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_modeloequipo.setText("Modelo Equipo");

        lbl_numeroserie.setText("Número de Serie");

        javax.swing.GroupLayout pnlSistemaLayout = new javax.swing.GroupLayout(pnlSistema);
        pnlSistema.setLayout(pnlSistemaLayout);
        pnlSistemaLayout.setHorizontalGroup(
            pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSistemaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_sistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_marcaequipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_condicionsistema, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_marcaequipo)
                    .addComponent(cbx_condicionsistema, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbx_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSistemaLayout.createSequentialGroup()
                        .addComponent(lbl_modeloequipo)
                        .addGap(44, 44, 44)
                        .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_modeloequipo)
                            .addComponent(txt_numeroserie, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbl_numeroserie))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSistemaLayout.setVerticalGroup(
            pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSistemaLayout.createSequentialGroup()
                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_sistema)
                    .addComponent(cbx_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_marcaequipo)
                    .addComponent(txt_marcaequipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_modeloequipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_modeloequipo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_condicionsistema)
                    .addComponent(cbx_condicionsistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_numeroserie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_numeroserie)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION DEL COMPONENTE DEFECTUOSO A REEMPLAZAR"));

        jLabel11.setText("Falla de (los) Componente(s) Defectuoso(s)");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setText("Síntoma de la(s) falla(s) encontrada(s)");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel13.setText("Solicitud según Orden de Trabajo Nº");

        jLabel14.setText("Fecha");

        lblzonal1.setText("Zonal");

        cbxzonal1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxzonal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxzonal1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(74, 74, 74)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(258, 258, 258)
                    .addComponent(lblzonal1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(cbxzonal1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(364, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(313, 313, 313)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(221, 221, 221)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblzonal1)
                        .addComponent(cbxzonal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(222, Short.MAX_VALUE)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("PRIORIDAD DEL REQUERIMIENTO"));

        jLabel15.setText("El componente solicitado tiene prioridad");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel16.setText("Si la Prioridad es Normal, indique");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel17.setText("Si la Prioridad es Urgente, indique");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(57, 57, 57)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 70, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("SOLICITUD DE REPUESTOS AYUDAS VISUALES");

        pnlUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION DE USUARIO"));

        lbl_unidad.setText("Unidad que efectúa el requerimiento");

        cbx_unidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_nombretecnico.setText("Nombre Técnico de requerimiento");

        lbl_correo.setText("Correo electrónico");

        lbl_anexo.setText("Anexo");

        lbl_fechacreacion.setText("fecha");

        txt_fecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));

        lbl_LlaveRepuesto.setToolTipText("");

        lblzonal.setText("Zonal");

        cbxzonal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxzonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxzonalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlUsuarioLayout = new javax.swing.GroupLayout(pnlUsuario);
        pnlUsuario.setLayout(pnlUsuarioLayout);
        pnlUsuarioLayout.setHorizontalGroup(
            pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_correo, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(lbl_nombretecnico)
                    .addComponent(lbl_unidad)
                    .addComponent(lblzonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_correo)
                    .addComponent(txt_nombretecnico)
                    .addComponent(cbx_unidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxzonal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(178, 178, 178)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_fechacreacion, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(lbl_LlaveRepuesto)
                .addContainerGap())
        );
        pnlUsuarioLayout.setVerticalGroup(
            pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuarioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_LlaveRepuesto)
                .addGap(66, 66, 66))
            .addGroup(pnlUsuarioLayout.createSequentialGroup()
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsuarioLayout.createSequentialGroup()
                        .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_fechacreacion)
                            .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(80, 80, 80))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUsuarioLayout.createSequentialGroup()
                        .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblzonal)
                            .addComponent(cbxzonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_unidad)
                            .addComponent(cbx_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nombretecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_nombretecnico))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_correo)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUsuarioLayout.createSequentialGroup()
                        .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_anexo))
                        .addGap(16, 16, 16)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlproducto.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION PRODUCTO"));

        lbl_nsn.setText("N.S.N");

        lbl_numeroparte.setText("Numero Parte");

        lbl_descripcion.setText("Descripción");

        lbl_cantidad.setText("Cantidad");

        btn_agregar.setText("Agregar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });

        btn_buscarproducto.setText("Buscar");
        btn_buscarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarproductoActionPerformed(evt);
            }
        });

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

        lblproducto.setText("Producto");

        btn_limpiar.setText("Limpiar");
        btn_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpiarActionPerformed(evt);
            }
        });

        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlproductoLayout = new javax.swing.GroupLayout(pnlproducto);
        pnlproducto.setLayout(pnlproductoLayout);
        pnlproductoLayout.setHorizontalGroup(
            pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlproductoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4))
                    .addGroup(pnlproductoLayout.createSequentialGroup()
                        .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlproductoLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlproductoLayout.createSequentialGroup()
                                        .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lbl_descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                            .addComponent(lblproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lbl_cantidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(10, 10, 10)
                                        .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlproductoLayout.createSequentialGroup()
                                                .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(307, 307, 307)
                                                .addComponent(lblllaveproducto)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(txt_producto)
                                            .addComponent(txt_descripcion)))
                                    .addGroup(pnlproductoLayout.createSequentialGroup()
                                        .addComponent(lbl_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(72, 72, 72)
                                        .addComponent(lbl_numeroparte)
                                        .addGap(51, 51, 51)
                                        .addComponent(txt_numeroparte, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_buscarproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_limpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_eliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlproductoLayout.createSequentialGroup()
                                .addGap(463, 463, 463)
                                .addComponent(lblllaveprogramadadetalle)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlproductoLayout.setVerticalGroup(
            pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlproductoLayout.createSequentialGroup()
                        .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_nsn)
                                .addComponent(txt_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_numeroparte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_numeroparte))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblproducto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlproductoLayout.createSequentialGroup()
                                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_descripcion))
                                .addGap(6, 6, 6)
                                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_cantidad)
                                    .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlproductoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblllaveproducto)
                                .addGap(35, 35, 35))))
                    .addGroup(pnlproductoLayout.createSequentialGroup()
                        .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_buscarproducto)
                            .addGroup(pnlproductoLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(btn_agregar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlproductoLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(btn_limpiar))
                            .addComponent(btn_eliminar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(lblllaveprogramadadetalle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlgrid.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION DEL REQUERIMIENTO"));

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
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout pnlgridLayout = new javax.swing.GroupLayout(pnlgrid);
        pnlgrid.setLayout(pnlgridLayout);
        pnlgridLayout.setHorizontalGroup(
            pnlgridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlgridLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        pnlgridLayout.setVerticalGroup(
            pnlgridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlgridLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_nuevo.setText("Nueva Soliitud");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });

        btn_finalizar.setText("Finalizar Soliitud");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlgrid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_numeroregistro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_finalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addComponent(pnlSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lbl_numeroregistro)
                    .addComponent(btn_nuevo)
                    .addComponent(btn_finalizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlgrid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean validar_formulario()
    {
        boolean validar = false;
        
        if(txt_fecha.getValue() =="")
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

        if(validar_formulario()){

            int id_repuesto = 0;
            SolicitudRepuesto Srp;

            String fecha_creacion = txt_fecha.getText();

            Srp = new SolicitudRepuesto(fecha_creacion);
            id_repuesto = repuesto.AgregarRepuesto(Srp);

            if (id_repuesto != 0) {

                lbl_LlaveRepuesto.setText(String.valueOf(id_repuesto));
                JOptionPane.showMessageDialog(null, "Solicitud de Respuesto Creada con exito");
                ActivarInfo();
            } else {
                JOptionPane.showMessageDialog(null, "Dato no Agregdo");
            }
        }
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void ActivarInfo()
    {
        btn_finalizar.setEnabled(true);
        btn_nuevo.setEnabled(true);
        
        pnlUsuario.setEnabled(true);
        txt_nombretecnico.setText("");
        txt_correo.setText("");
        txt_anexo.setText("");
        txt_fecha.setValue(new Date());
        
        txt_nombretecnico.setEnabled(true);
        txt_correo.setEnabled(true);
        txt_anexo.setEnabled(true);
        txt_fecha.setEnabled(true);
        cbxzonal.setEnabled(true);
        cbx_unidad.setEnabled(true);

        pnlSistema.setEnabled(true);
        cbx_sistema.setEnabled(true);
        txt_marcaequipo.setEnabled(true);
        txt_modeloequipo.setEnabled(true);
        cbx_condicionsistema.setEnabled(true);
        txt_numeroserie.setEnabled(true);
        
        txt_marcaequipo.setText("");
        txt_modeloequipo.setText("");
        txt_numeroserie.setText("");
        
        txt_nsn.setText("");
        txt_numeroparte.setText("");
        txt_producto.setText("");
        txt_descripcion.setText("");
        txt_cantidad.setText("");
        
        activarProducto();
        
    }
    
    private void BloquearInfo()
    {
        pnlUsuario.setEnabled(true);
        txt_nombretecnico.setEnabled(false);
        txt_correo.setEnabled(false);
        txt_anexo.setEnabled(false);
        txt_fecha.setEnabled(false);
        cbxzonal.setEnabled(false);
        cbx_unidad.setEnabled(false);
        btn_nuevo.setEnabled(false);
        
        btn_finalizar.setEnabled(true);
        
        
    }
    
    private void ActivarRestoInfo()
    {
        cbx_sistema.setEnabled(true);
        txt_marcaequipo.setEnabled(true);
        txt_modeloequipo.setEnabled(true);
        cbx_condicionsistema.setEnabled(true);
        txt_numeroserie.setEnabled(true);
        
        activarProducto();
    }
    
    private void btn_finalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_finalizarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btn_finalizarActionPerformed

    private void cbxzonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxzonalActionPerformed
        // TODO add your handling code here:
        try
        {
            CmbZonal items = (CmbZonal)cbxzonal.getSelectedItem();
            int id_zonal = Integer.parseInt(items.getID());

            if(id_zonal>0){

                DefaultComboBoxModel modelo = new DefaultComboBoxModel();
                ResultSet objResultSet;
                objResultSet = Conecciones.Coneccion.consulta(" select * from unidad where id_zonal = "+ id_zonal + "" );
                modelo.addElement(new CmbUnidad("Seleccione Unidad", "0"));
                cbx_unidad.setModel(modelo);//con esto lo agregamos al objeto al jcombobox
                while (objResultSet.next())
                {
                    modelo.addElement(new CmbUnidad(objResultSet.getString("nombre_unidad") , objResultSet.getString("id_unidad")));
                    cbx_unidad.setModel(modelo);
                }

                cbx_unidad.setEnabled(true);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getCause());
        }

    }//GEN-LAST:event_cbxzonalActionPerformed

    private void cbxzonal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxzonal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxzonal1ActionPerformed
    
    private void activarProducto()
    {
        pnlgrid.setEnabled(true);
        pnlproducto.setEnabled(true);

        txt_nsn.setText("");
        txt_producto.setText("");
        txt_cantidad.setText("");
        txt_descripcion.setText("");
        txt_numeroparte.setText("");

        txt_nsn.setEnabled(true);
        txt_producto.setEnabled(false);
        txt_cantidad.setEnabled(false);
        txt_descripcion.setEnabled(false);
        txt_numeroparte.setEnabled(false);

        btn_buscarproducto.setEnabled(true);
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(false);


    }
    
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
    
    private void ListarTablaproducto() {
        String filtroNSN = txt_nsn.getText();
        String filtroParte = txt_numeroparte.getText();
        
        List<Producto> listas = productos.listado(0,filtroNSN , "", filtroParte);
        mtp = new ModeloTablaProducto(listas);
        
        /*List<Usuario> listas = usuarios.listado(filtroNSN);
        mtp = new ModeloTablaUsuario(listas);*/
        
        jTable2.setModel(mtp);
        jTable2.getRowSorter();
    }
    
    private void ListarTabla() {
        /*
        int llaveprogramada = Integer.parseInt(lbl_LlaveRepuesto.getText());
        
        List<SolicitudProgramadaDetalle> listas = programadasdetalle.listado(llaveprogramada);
        mtpd = new ModeloTablaProramadaDetalle(listas);
        
        jTable1.setModel(mtpd);
        jTable1.getRowSorter();
        */
    }
    
    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        if(validar_formulario_producto()){

            /*
            boolean resp = false;
            SolicitudProgramadaDetalle Pgrd;

            int id_programado = Integer.parseInt(lbl_LlaveRepuesto.getText());
            int id_producto = Integer.parseInt(lblllaveproducto.getText());
            int cantidad = Integer.parseInt(txt_cantidad.getText());

            Pgrd = new SolicitudProgramadaDetalle(0,id_programado, id_producto, cantidad);
            resp = programadasdetalle.AgregarProgramada(Pgrd);

            if (resp == false) {
                JOptionPane.showMessageDialog(null, "Dato Agregado");
                ListarTablaproducto();
                ListarTabla();
                activarProducto();
            } else {
                JOptionPane.showMessageDialog(null, "Dato no Agregdo");
            }
            */
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_buscarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarproductoActionPerformed
        // TODO add your handling code here:
        ListarTablaproducto();
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(true);
    }//GEN-LAST:event_btn_buscarproductoActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        prd = ((ModeloTablaProducto) jTable2.getModel()).DameProducto(jTable2.getSelectedRow());

        txt_producto.setEditable(true);
        txt_nsn.setEditable(true);
        txt_numeroparte.setEditable(true);
        txt_descripcion.setEditable(true);
        txt_cantidad.setEditable(true);

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
        txt_producto.setEditable(true);
        txt_nsn.setEditable(true);
        txt_numeroparte.setEditable(true);
        txt_descripcion.setEditable(true);
        txt_cantidad.setEditable(true);

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
        txt_descripcion.setEnabled(true);
        txt_cantidad.setEnabled(true);

        btn_agregar.setEnabled(false);
        btn_nuevo.setEnabled(true);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(true);
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed

        /*boolean resp = programadasdetalle.DeleteProgramada(pgrd);
        if (resp == false) {
            JOptionPane.showMessageDialog(null, "Dato Eliminado");
            ListarTabla();
            ListarTablaproducto();
            
            activarProducto();
        } else {
            JOptionPane.showMessageDialog(null, "Dato no Eliminado");
        }*/
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        viewp = ((ModeloTablaView_ProductosSolicitados) jTable1.getModel()).DameProgramadaDetalle(jTable1.getSelectedRow());

        lblllaveprogramadadetalle.setText(String.valueOf(viewp.getidProductoSolicitado()));

        txt_nsn.setText(viewp.getNsn());
        txt_numeroparte.setText(viewp.getNumeroparte());
        txt_descripcion.setText(viewp.getDescripcion());
        txt_producto.setText(viewp.getProduto());
        txt_cantidad.setText(String.valueOf(viewp.getCantidad()));

        btn_buscarproducto.setEnabled(false);
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(true);
        btn_limpiar.setEnabled(true);

    }//GEN-LAST:event_jTable1MouseClicked

    private void cbx_sistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_sistemaActionPerformed
        try
        {
            CmbSistema items = (CmbSistema)cbx_sistema.getSelectedItem();
            int id_sistema = Integer.parseInt(items.getID());

            if(id_sistema>0){
                ListarTablaBuscar();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getCause());
        }
// TODO add your handling code here:
    }//GEN-LAST:event_cbx_sistemaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_buscarproducto;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_finalizar;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JComboBox<String> cbx_condicionsistema;
    private javax.swing.JComboBox<String> cbx_sistema;
    private javax.swing.JComboBox<String> cbx_unidad;
    private javax.swing.JComboBox<String> cbxzonal;
    private javax.swing.JComboBox<String> cbxzonal1;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JLabel lbl_LlaveRepuesto;
    private javax.swing.JLabel lbl_anexo;
    private javax.swing.JLabel lbl_cantidad;
    private javax.swing.JLabel lbl_condicionsistema;
    private javax.swing.JLabel lbl_correo;
    private javax.swing.JLabel lbl_descripcion;
    private javax.swing.JLabel lbl_fechacreacion;
    private javax.swing.JLabel lbl_marcaequipo;
    private javax.swing.JLabel lbl_modeloequipo;
    private javax.swing.JLabel lbl_nombretecnico;
    private javax.swing.JLabel lbl_nsn;
    private javax.swing.JLabel lbl_numeroparte;
    private javax.swing.JLabel lbl_numeroregistro;
    private javax.swing.JLabel lbl_numeroserie;
    private javax.swing.JLabel lbl_sistema;
    private javax.swing.JLabel lbl_unidad;
    private javax.swing.JLabel lblllaveproducto;
    private javax.swing.JLabel lblllaveprogramadadetalle;
    private javax.swing.JLabel lblproducto;
    private javax.swing.JLabel lblzonal;
    private javax.swing.JLabel lblzonal1;
    private javax.swing.JPanel pnlSistema;
    private javax.swing.JPanel pnlUsuario;
    private javax.swing.JPanel pnlgrid;
    private javax.swing.JPanel pnlproducto;
    private javax.swing.JTextField txt_anexo;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_correo;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JFormattedTextField txt_fecha;
    private javax.swing.JTextField txt_marcaequipo;
    private javax.swing.JTextField txt_modeloequipo;
    private javax.swing.JTextField txt_nombretecnico;
    private javax.swing.JTextField txt_nsn;
    private javax.swing.JTextField txt_numeroparte;
    private javax.swing.JTextField txt_numeroserie;
    private javax.swing.JTextField txt_producto;
    // End of variables declaration//GEN-END:variables
}
