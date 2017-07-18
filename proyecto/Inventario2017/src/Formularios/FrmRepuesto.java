package Formularios;

import ModeloTabla.CmbZonal;
import ModeloTabla.CmbSistema;
import ModeloTabla.CmbUnidad;
import ModeloTabla.CmbPrioridad;
import ModeloTabla.cmbCondicionSistema;
import ModeloTabla.ModeloTablaProducto;
import Entidades.View_ProductosSolicitados;
import Logico.ProductoLog;

import Entidades.SolicitudRepuesto;
import Logico.SolicitudRepuestoLog;

import ModeloTabla.ModeloTablaView_ProductosSolicitados;
import Logico.View_ProductosSolicitadosLog;
import Entidades.View_ProductosSolicitados;

import ModeloTabla.ModeloTablaRepuestoDetalle;

import Entidades.SolicitudRepuestoDetalle;
import Logico.SolicitudProgramadaDetalleLog;
import Logico.SolicitudRepuestoDetalleLog;

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

public class FrmRepuesto extends javax.swing.JInternalFrame {

    SolicitudRepuestoLog repuesto;
    SolicitudRepuesto srp;

    SolicitudRepuestoDetalleLog repuestodetalle;
    SolicitudRepuestoDetalle spd;
    ModeloTablaRepuestoDetalle mtrd;
    
    
    View_ProductosSolicitados viewp;
    View_ProductosSolicitadosLog view_producto;
    ModeloTablaView_ProductosSolicitados  view_tabla;
    
    public FrmRepuesto() {
        
        initComponents();
        repuestodetalle = new SolicitudRepuestoDetalleLog();
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
                
                lbl_LlaveRepuesto.setText(objResultSet.getString("id_solicitudrepuesto"));
                
                lbl_numeroregistro.setText(objResultSet.getString("id_comercial"));
                txt_nombretecnico.setText(objResultSet.getString("nombre_tecnico"));
                txt_correo.setText(objResultSet.getString("correo_electronico"));
                txt_anexo.setText(objResultSet.getString("Anexo"));
                          
                if(objResultSet.getString("id_zonal") != null)
                {
                    cbxzonal.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_zonal")));

                    CargaUnidad();
                    
                    if(objResultSet.getString("id_unidad") != "0")
                    {
                        cbx_unidad.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_unidad")));
                    }
                }
                
                txt_marcaequipo.setText(objResultSet.getString("marca_equipo"));
                txt_modeloequipo.setText(objResultSet.getString("modelo_equipo"));
                txt_numeroserie.setText(objResultSet.getString("numero_Serie"));
                
                if(objResultSet.getString("id_condicionsistema") != null)
                {
                    cbx_condicionsistema.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_condicionsistema")));
                }
                
                if(objResultSet.getString("id_sistema") != null)
                {
                    cbx_sistema.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_sistema")));
                }
                
                txt_fallaComponente.setText(objResultSet.getString("marca_equipo"));
                txt_sintomafalla.setText(objResultSet.getString("marca_equipo"));
                txt_numeroorden.setText(objResultSet.getString("marca_equipo"));
                txt_fechasolicitud.setText(objResultSet.getString("marca_equipo"));

                if(objResultSet.getString("id_prioridad") != null)
                {
                    cbx_prioridad.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_prioridad")));
                }
                lbl_detalle.setText(objResultSet.getString("marca_equipo"));
                
                
                btn_nuevo.setEnabled(false);
                btn_finalizar.setEnabled(true);
                
                ListarTablaBuscar();
                ListarTabla();
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
        view_tabla = new ModeloTablaView_ProductosSolicitados(listas);
        
        jTable2.setModel(view_tabla);
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
            modeloCondicionSistema.addElement(new cmbCondicionSistema("Seleccione Conexion del Sistema", "0"));
            cbx_condicionsistema.setModel(modeloCondicionSistema);//con esto lo agregamos al objeto al jcombobox
            while (objResultSetcondicion.next())
            {
                modeloCondicionSistema.addElement(new cmbCondicionSistema(objResultSetcondicion.getString("nombre_condicionsistema") , objResultSetcondicion.getString("id_condicionsistema")));
                cbx_condicionsistema.setModel(modeloCondicionSistema);
            }
            
            DefaultComboBoxModel modeloprioridad = new DefaultComboBoxModel();
            ResultSet objResultSetprioridad;
            objResultSetprioridad = Conecciones.Coneccion.consulta(" select * from prioridad ");
            modeloprioridad.addElement(new CmbPrioridad("Seleccione Prioridad", "0"));
            cbx_prioridad.setModel(modeloprioridad);//con esto lo agregamos al objeto al jcombobox
            while (objResultSetprioridad.next())
            {
                modeloprioridad.addElement(new CmbPrioridad(objResultSetprioridad.getString("nombre_prioridad") , objResultSetprioridad.getString("id_prioridad")));
                cbx_prioridad.setModel(modeloprioridad);
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
        
        txt_nombretecnico.setText("");
        txt_correo.setText("");
        txt_anexo.setText("");
        txt_fecha.setValue(new Date());
        
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
        pnlinformacion_sistema = new javax.swing.JPanel();
        txt_fallaComponente = new javax.swing.JTextField();
        lbl_fallacomponente = new javax.swing.JLabel();
        lbl_sistomasfalla = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_sintomafalla = new javax.swing.JTextArea();
        lbl_solicitudorden = new javax.swing.JLabel();
        txt_numeroorden = new javax.swing.JTextField();
        lbl_fechaSolicitud = new javax.swing.JLabel();
        txt_fechasolicitud = new javax.swing.JFormattedTextField();
        pnlPrioridad = new javax.swing.JPanel();
        lbl_prioridad = new javax.swing.JLabel();
        cbx_prioridad = new javax.swing.JComboBox<>();
        lbl_detalle = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_detalle = new javax.swing.JTextArea();
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
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        lblllaveproducto = new javax.swing.JLabel();
        lblllaveprogramadadetalle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_numeroparte = new javax.swing.JLabel();
        txt_numeroparte = new javax.swing.JTextField();
        txt_nsn = new javax.swing.JTextField();
        lbl_nsn = new javax.swing.JLabel();
        lblproducto = new javax.swing.JLabel();
        txt_producto = new javax.swing.JTextField();
        txt_descripcion = new javax.swing.JTextField();
        lbl_descripcion = new javax.swing.JLabel();
        lbl_cantidad = new javax.swing.JLabel();
        txt_cantidad = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btn_buscarproducto = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_limpiar = new javax.swing.JButton();
        pnlgrid = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_nuevo = new javax.swing.JButton();
        btn_finalizar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setAutoscrolls(true);

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
                .addGap(18, 18, 18)
                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSistemaLayout.createSequentialGroup()
                        .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_marcaequipo, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_condicionsistema, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(80, 80, 80)
                        .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSistemaLayout.createSequentialGroup()
                                .addComponent(lbl_modeloequipo)
                                .addGap(44, 44, 44)
                                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_modeloequipo)
                                    .addComponent(txt_numeroserie, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lbl_numeroserie)))
                    .addComponent(cbx_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        pnlinformacion_sistema.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION DEL COMPONENTE DEFECTUOSO A REEMPLAZAR"));

        lbl_fallacomponente.setText("Falla de (los) Componente(s) Defectuoso(s)");

        lbl_sistomasfalla.setText("Síntoma de la(s) falla(s) encontrada(s)");

        txt_sintomafalla.setColumns(20);
        txt_sintomafalla.setRows(5);
        jScrollPane1.setViewportView(txt_sintomafalla);

        lbl_solicitudorden.setText("Solicitud según Orden de Trabajo Nº");

        lbl_fechaSolicitud.setText("Fecha");

        javax.swing.GroupLayout pnlinformacion_sistemaLayout = new javax.swing.GroupLayout(pnlinformacion_sistema);
        pnlinformacion_sistema.setLayout(pnlinformacion_sistemaLayout);
        pnlinformacion_sistemaLayout.setHorizontalGroup(
            pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlinformacion_sistemaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_fallacomponente)
                    .addGroup(pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lbl_solicitudorden)
                        .addComponent(lbl_sistomasfalla)))
                .addGap(38, 38, 38)
                .addGroup(pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlinformacion_sistemaLayout.createSequentialGroup()
                        .addComponent(txt_numeroorden, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_fechaSolicitud)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_fechasolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_fallaComponente)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE))
                .addGap(63, 63, 63))
        );
        pnlinformacion_sistemaLayout.setVerticalGroup(
            pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlinformacion_sistemaLayout.createSequentialGroup()
                .addGroup(pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_fallacomponente)
                    .addComponent(txt_fallaComponente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_sistomasfalla, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_numeroorden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_solicitudorden)
                    .addComponent(lbl_fechaSolicitud)
                    .addComponent(txt_fechasolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPrioridad.setBorder(javax.swing.BorderFactory.createTitledBorder("PRIORIDAD DEL REQUERIMIENTO"));

        lbl_prioridad.setText("El componente solicitado tiene prioridad");

        cbx_prioridad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_detalle.setText("Detalle");

        txt_detalle.setColumns(20);
        txt_detalle.setRows(5);
        jScrollPane3.setViewportView(txt_detalle);

        javax.swing.GroupLayout pnlPrioridadLayout = new javax.swing.GroupLayout(pnlPrioridad);
        pnlPrioridad.setLayout(pnlPrioridadLayout);
        pnlPrioridadLayout.setHorizontalGroup(
            pnlPrioridadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrioridadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPrioridadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_prioridad)
                    .addComponent(lbl_detalle))
                .addGap(57, 57, 57)
                .addGroup(pnlPrioridadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbx_prioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPrioridadLayout.setVerticalGroup(
            pnlPrioridadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrioridadLayout.createSequentialGroup()
                .addGroup(pnlPrioridadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_prioridad)
                    .addComponent(cbx_prioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPrioridadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_detalle, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("SOLICITUD DE REPUESTOS AYUDAS VISUALES");

        pnlUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION DE USUARIO"));

        lbl_unidad.setText("Unidad que efectúa el requerimiento");

        cbx_unidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_unidadActionPerformed(evt);
            }
        });

        lbl_nombretecnico.setText("Nombre Técnico de requerimiento");

        lbl_correo.setText("Correo electrónico");

        lbl_anexo.setText("Anexo");

        lbl_fechacreacion.setText("fecha");

        txt_fecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

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
                .addGap(18, 18, 18)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_nombretecnico, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbx_unidad, javax.swing.GroupLayout.Alignment.LEADING, 0, 287, Short.MAX_VALUE)
                    .addComponent(cbxzonal, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_correo))
                .addGap(88, 88, 88)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlUsuarioLayout.createSequentialGroup()
                        .addComponent(lbl_fechacreacion, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUsuarioLayout.createSequentialGroup()
                        .addComponent(lbl_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(txt_anexo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_LlaveRepuesto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlUsuarioLayout.setVerticalGroup(
            pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuarioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_LlaveRepuesto)
                .addGap(66, 66, 66))
            .addGroup(pnlUsuarioLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblzonal)
                    .addComponent(cbxzonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_fechacreacion)
                    .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_unidad)
                    .addComponent(cbx_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nombretecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_nombretecnico)
                    .addComponent(lbl_anexo)
                    .addComponent(txt_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_correo))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlproducto.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION PRODUCTO"));

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

        lbl_numeroparte.setText("Numero Parte");

        lbl_nsn.setText("N.S.N");

        lblproducto.setText("Producto");

        lbl_descripcion.setText("Descripción");

        lbl_cantidad.setText("Cantidad");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_producto)
                                .addGap(351, 351, 351))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_numeroparte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_numeroparte, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nsn)
                    .addComponent(txt_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_numeroparte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_numeroparte))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblproducto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_descripcion))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_cantidad)
                    .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btn_buscarproducto.setText("Buscar");
        btn_buscarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarproductoActionPerformed(evt);
            }
        });

        btn_agregar.setText("Agregar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscarproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btn_buscarproducto)
                .addGap(1, 1, 1)
                .addComponent(btn_agregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_eliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_limpiar)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout pnlproductoLayout = new javax.swing.GroupLayout(pnlproducto);
        pnlproducto.setLayout(pnlproductoLayout);
        pnlproductoLayout.setHorizontalGroup(
            pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addGap(837, 837, 837)
                .addComponent(lblllaveproducto))
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addGap(946, 946, 946)
                .addComponent(lblllaveprogramadadetalle))
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlproductoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1053, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pnlproductoLayout.setVerticalGroup(
            pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlproductoLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblllaveproducto)
                .addGap(141, 141, 141)
                .addComponent(lblllaveprogramadadetalle)
                .addGap(123, 123, 123))
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1061, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlgridLayout.setVerticalGroup(
            pnlgridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlgridLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_numeroregistro)
                        .addGap(406, 406, 406)
                        .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_finalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pnlUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlSistema, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlproducto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pnlPrioridad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlinformacion_sistema, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(pnlgrid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addComponent(pnlproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlgrid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlinformacion_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        try
        {
            if(validar_formulario()){

                int id_usuario = Integer.parseInt(Inventario.global_llaveusuario);

                int id_repuesto = 0;
                SolicitudRepuesto Srp;

                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

                Date d = f.parse(txt_fecha.getText());
                long milliseconds = d.getTime();

                java.sql.Date fecha = new java.sql.Date(milliseconds);

                Srp = new SolicitudRepuesto(0, id_usuario, fecha, 2);
                id_repuesto = repuesto.AgregarRepuesto(Srp);

                if (id_repuesto != 0) {
                    
                    lbl_LlaveRepuesto.setText(String.valueOf(id_repuesto));
                    JOptionPane.showMessageDialog(null, "Solicitud de Respuesto Creada con exito");
                    CargarDatosPendientes();
                    ActivarInfo();
                    
                } else {
                    
                    JOptionPane.showMessageDialog(null, "Dato no Agregdo");
                    
                }
            }
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getCause());
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
        
        CargaUnidad();
    }
    
    private void CargaUnidad(){
        
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
        /*String filtroNSN = txt_nsn.getText();
        String filtroParte = txt_numeroparte.getText();
        
        List<View_ProductosSolicitados> listas = productos.listado(0,filtroNSN , "", filtroParte);
        mtp = new ModeloTablaProducto(listas);
        
        
        jTable2.setModel(mtp);
        jTable2.getRowSorter();*/
    }
    
    private void ListarTabla() {
        
        int llaveRepuesto = Integer.parseInt(lbl_LlaveRepuesto.getText());
        
        List<SolicitudRepuestoDetalle> listas = repuestodetalle.listado(llaveRepuesto);
        mtrd = new ModeloTablaRepuestoDetalle(listas);
        
        jTable1.setModel(mtrd);
        jTable1.getRowSorter();
        
    }
    
    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        
        try {
        
        if(validar_formulario_producto()){

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        
            Date d = f.parse(txt_fecha.getText());
            long milliseconds = d.getTime();

            java.sql.Date fecha = new java.sql.Date(milliseconds);
            
            boolean blx_estado = true;
            boolean resp = true;
            
            SolicitudRepuestoDetalle Pgrd;

            int id_repuesto = Integer.parseInt(lbl_LlaveRepuesto.getText());
            SolicitudRepuesto Pgr;
            
            String id_comercial = lbl_numeroregistro.getText();
            
            int id_usuario = Integer.parseInt(Inventario.global_llaveusuario);

            CmbUnidad itemsU = (CmbUnidad)cbx_unidad.getSelectedItem();
            int id_unidad = Integer.parseInt(itemsU.getID());
            
            cmbCondicionSistema itemsC = (cmbCondicionSistema)cbx_condicionsistema.getSelectedItem();
            int id_condicion = Integer.parseInt(itemsU.getID());
            
            String stx_nombretecnico = txt_nombretecnico.getText();
            String stx_correo = txt_correo.getText();
            String stx_anexo = txt_anexo.getText();
            
            CmbSistema itemsS = (CmbSistema)cbx_sistema.getSelectedItem();
            int id_sistema = Integer.parseInt(itemsS.getID());
            
            String stx_marca = txt_marcaequipo.getText();
            String stx_modelo = txt_modeloequipo.getText();
            String stx_numeroserie = txt_numeroserie.getText();
            
            Pgr = new SolicitudRepuesto(id_repuesto,0,id_comercial, id_unidad, id_usuario, fecha, stx_nombretecnico,stx_correo, stx_anexo, id_sistema, stx_marca, id_condicion, stx_modelo, stx_numeroserie, 0, "", "", fecha, 0, 0, 0,1);
            
            blx_estado = repuesto.UpdateRepuesto(Pgr);

            int id_producto = Integer.parseInt(lblllaveproducto.getText());
            int cantidad = Integer.parseInt(txt_cantidad.getText());
            
            Pgrd = new SolicitudRepuestoDetalle(0,id_repuesto, id_producto, cantidad);
            resp = repuestodetalle.AgregarRepuesto(Pgrd);
          
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

    private void btn_buscarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarproductoActionPerformed
        // TODO add your handling code here:
        ListarTablaproducto();
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(true);
    }//GEN-LAST:event_btn_buscarproductoActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        viewp = ((ModeloTablaView_ProductosSolicitados) jTable2.getModel()).DameProgramadaDetalle(jTable2.getSelectedRow());

        txt_producto.setEditable(true);
        txt_nsn.setEditable(true);
        txt_numeroparte.setEditable(true);
        txt_descripcion.setEditable(true);
        txt_cantidad.setEditable(true);

        lblllaveproducto.setText(String.valueOf(viewp.getIdProducto()));

        txt_producto.setText(viewp.getProduto());
        txt_nsn.setText(viewp.getNsn());
        txt_numeroparte.setText(viewp.getNumeroparte());
        txt_descripcion.setText(viewp.getDescripcion());
        txt_cantidad.setText(String.valueOf(viewp.getCantidad()));

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

    private void cbx_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_unidadActionPerformed
        /*try
        {
            CmbUnidad items = (CmbUnidad)cbx_unidad.getSelectedItem();
            int id_unidad = Integer.parseInt(items.getID());

            if(id_unidad > 0){
                ListarTablaBuscar();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getCause());
        }*/
    }//GEN-LAST:event_cbx_unidadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_buscarproducto;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_finalizar;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JComboBox<String> cbx_condicionsistema;
    private javax.swing.JComboBox<String> cbx_prioridad;
    private javax.swing.JComboBox<String> cbx_sistema;
    private javax.swing.JComboBox<String> cbx_unidad;
    private javax.swing.JComboBox<String> cbxzonal;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lbl_LlaveRepuesto;
    private javax.swing.JLabel lbl_anexo;
    private javax.swing.JLabel lbl_cantidad;
    private javax.swing.JLabel lbl_condicionsistema;
    private javax.swing.JLabel lbl_correo;
    private javax.swing.JLabel lbl_descripcion;
    private javax.swing.JLabel lbl_detalle;
    private javax.swing.JLabel lbl_fallacomponente;
    private javax.swing.JLabel lbl_fechaSolicitud;
    private javax.swing.JLabel lbl_fechacreacion;
    private javax.swing.JLabel lbl_marcaequipo;
    private javax.swing.JLabel lbl_modeloequipo;
    private javax.swing.JLabel lbl_nombretecnico;
    private javax.swing.JLabel lbl_nsn;
    private javax.swing.JLabel lbl_numeroparte;
    private javax.swing.JLabel lbl_numeroregistro;
    private javax.swing.JLabel lbl_numeroserie;
    private javax.swing.JLabel lbl_prioridad;
    private javax.swing.JLabel lbl_sistema;
    private javax.swing.JLabel lbl_sistomasfalla;
    private javax.swing.JLabel lbl_solicitudorden;
    private javax.swing.JLabel lbl_unidad;
    private javax.swing.JLabel lblllaveproducto;
    private javax.swing.JLabel lblllaveprogramadadetalle;
    private javax.swing.JLabel lblproducto;
    private javax.swing.JLabel lblzonal;
    private javax.swing.JPanel pnlPrioridad;
    private javax.swing.JPanel pnlSistema;
    private javax.swing.JPanel pnlUsuario;
    private javax.swing.JPanel pnlgrid;
    private javax.swing.JPanel pnlinformacion_sistema;
    private javax.swing.JPanel pnlproducto;
    private javax.swing.JTextField txt_anexo;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_correo;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JTextArea txt_detalle;
    private javax.swing.JTextField txt_fallaComponente;
    private javax.swing.JFormattedTextField txt_fecha;
    private javax.swing.JFormattedTextField txt_fechasolicitud;
    private javax.swing.JTextField txt_marcaequipo;
    private javax.swing.JTextField txt_modeloequipo;
    private javax.swing.JTextField txt_nombretecnico;
    private javax.swing.JTextField txt_nsn;
    private javax.swing.JTextField txt_numeroorden;
    private javax.swing.JTextField txt_numeroparte;
    private javax.swing.JTextField txt_numeroserie;
    private javax.swing.JTextField txt_producto;
    private javax.swing.JTextArea txt_sintomafalla;
    // End of variables declaration//GEN-END:variables
}
