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

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
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
import javax.swing.JScrollPane;
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
    
    public static final String IMG = "resources/Images/logo.jpg";
    
    public static final String DEST = "c:/Reportes/SolicitudDespacho_";
    
    public FrmRepuesto() 
    {
        initComponents();
        repuestodetalle = new SolicitudRepuestoDetalleLog();
        repuesto = new SolicitudRepuestoLog();
        view_producto = new View_ProductosSolicitadosLog();
        
        CargarCombo();
        Limpiar();
        activar_infouser();
        lbl_LlaveRepuesto.setVisible(false);
        CargarDatosPendientes();
    }
    
    private void activar_infouser()
    {
        
        txt_numerosolicitud.setEnabled(true);
        txt_correo.setEnabled(true);
        txt_anexo.setEnabled(true);
        txt_fecha.setEnabled(true);
        cbxzonal.setEnabled(true);
        cbx_unidad.setEnabled(true);
        
    }

    public void CargarDatosPendientes()
    {
        try
        {
            ResultSet objResultSet;
            objResultSet = Conecciones.Coneccion.consulta(" SELECT        u.id_zonal, sr.id_solicitudRepuesto, sr.id_temporada, sr.id_comercial, sr.id_unidad, sr.id_usuario, convert(date,sr.fecha_creacion) as fecha_creacion, " +
            "sr.nombre_tecnico, sr.correo_electronico, sr.Anexo, sr.id_sistema, sr.marca_equipo, " +
            "sr.id_condicionsistema, sr.modelo_equipo, sr.numero_Serie, sr.id_fallacomponente, sr.falla_componente, " +
            "sr.sintoma_falla, sr.solicutus_orden_trabajo, convert(varchar(10),sr.fecha_solicitud,103) as fecha_solicitud, " +
            "sr.id_prioridad, sr.detalle, sr.estado, u.nombre_unidad " +
            "FROM            solucitud_repuesto AS sr LEFT OUTER JOIN " +
            "                         unidad AS u ON sr.id_unidad = u.id_unidad " +
            "WHERE        (sr.estado = 2)");

            while (objResultSet.next())
            {
                ActivarInfo();
                
                lbl_LlaveRepuesto.setText(objResultSet.getString("id_solicitudrepuesto"));
                
                txt_numerosolicitud.setText(objResultSet.getString("id_comercial"));
                txt_nombretecnico.setText(objResultSet.getString("nombre_tecnico"));
                txt_correo.setText(objResultSet.getString("correo_electronico"));
                txt_anexo.setText(objResultSet.getString("Anexo"));
                          
                if(objResultSet.getString("id_zonal") != null)
                {
                    cbxzonal.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_zonal")));

                    CargaUnidad();
                    
                    if(objResultSet.getString("id_unidad") != "0")
                    {
                        cbx_unidad.getModel().setSelectedItem(new CmbUnidad(objResultSet.getString("nombre_unidad"), objResultSet.getString("id_unidad")));
                    }
                }
                
                txt_marcaequipo.setText(objResultSet.getString("marca_equipo"));
                txt_modeloequipo.setText(objResultSet.getString("modelo_equipo"));
                txt_numeroserie.setText(objResultSet.getString("numero_Serie"));
                
                /*
                if(objResultSet.getString("id_condicionsistema") != null)
                {
                    cbx_condicionsistema.getModel().setSelectedItem(new cmbCondicionSistema(objResultSet.getString("nombre_condicion"), objResultSet.getString("id_condicionsistema")));                   
                }
                */
                
                if(objResultSet.getString("id_sistema") != null)
                {
                    cbx_sistema.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_sistema")));
                }
                
                txt_fallaComponente.setText(objResultSet.getString("falla_componente"));
                txt_sintomafalla.setText(objResultSet.getString("sintoma_falla"));
                txt_numeroorden.setText(objResultSet.getString("solicutus_orden_trabajo"));
                
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

                Date d = f.parse(objResultSet.getString("fecha_solicitud"));
                
                txt_fechasolicitud.setValue(d);

                if(objResultSet.getString("id_prioridad") != null)
                {
                    cbx_prioridad.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_prioridad")));
                }
                txt_detalle.setText(objResultSet.getString("detalle"));
                    
                
                                
                btn_nuevo.setEnabled(false);
                btn_finalizar.setEnabled(true);
                cbxzonal.setEnabled(false);
                cbx_unidad.setEnabled(false);
                
                
                ListarTablaBuscar();
                ListarTabla();
                BloquearInfo();
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
        
        String filtroNSN = txt_nsn.getText();
        String filtroParte = txt_numeroparte.getText();
        String filtroproducto = txt_producto.getText();
        
        if(id_sistema > 0 && id_unidad > 0){
        
            List<View_ProductosSolicitados> listas = view_producto.listado(id_sistema , id_unidad,0 ,filtroNSN, filtroParte, filtroproducto);

            if(listas.size()  >0)
            {
                view_tabla = new ModeloTablaView_ProductosSolicitados(listas);
                jTable2.setModel(view_tabla);
                jTable2.getRowSorter();
            } 
            else 
            {
                JOptionPane.showMessageDialog(null, "No hay Solicitudes para la Unidad y Sistema seleccionado");
            } 
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
            objResultSetprioridad = Conecciones.Coneccion.consulta(" select * from prioridad_solicitud ");
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
        btn_nuevo.setEnabled(true);
            
        txt_numerosolicitud.setText("");
        
        lbl_LlaveRepuesto.setText("");
        lblllaveproducto.setText("");
        lblllaveprogramadadetalle.setText("");
        
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
        
        txt_fallaComponente.setText("");
        txt_sintomafalla.setText("");
        txt_numeroorden.setText("");
        txt_fechasolicitud.setValue(new Date());
        
        txt_detalle.setEnabled(false);
        
        txt_fallaComponente.setEnabled(false);
        txt_sintomafalla.setEnabled(false);
        txt_numeroorden.setEnabled(false);
        txt_fechasolicitud.setEnabled(false);
        cbx_prioridad.setEnabled(false);
        txt_detalle.setEnabled(false);
        
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

    }
    
    private void LimpiarTabla() {
    
        
        List<SolicitudRepuestoDetalle> listas = null;
        mtrd = new ModeloTablaRepuestoDetalle(listas);
        
        jTable1.setModel(mtrd);
        jTable1.getRowSorter();
    
        
        List<View_ProductosSolicitados> listas2 = null;
        view_tabla = new ModeloTablaView_ProductosSolicitados(listas2);
        
        jTable2.setModel(view_tabla);
        jTable2.getRowSorter();
    
        
       jTable1.setVisible(false);
       jTable2.setVisible(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_solicitud = new javax.swing.JPanel();
        pnlUsuario = new javax.swing.JPanel();
        lbl_unidad = new javax.swing.JLabel();
        cbx_unidad = new javax.swing.JComboBox<>();
        lbl_nombretecnico = new javax.swing.JLabel();
        txt_numerosolicitud = new javax.swing.JTextField();
        lbl_correo = new javax.swing.JLabel();
        txt_correo = new javax.swing.JTextField();
        lbl_anexo = new javax.swing.JLabel();
        txt_anexo = new javax.swing.JTextField();
        lbl_fechacreacion = new javax.swing.JLabel();
        txt_fecha = new javax.swing.JFormattedTextField();
        lbl_LlaveRepuesto = new javax.swing.JLabel();
        lblzonal = new javax.swing.JLabel();
        cbxzonal = new javax.swing.JComboBox<>();
        lbl_numerosolicitud = new javax.swing.JLabel();
        txt_nombretecnico = new javax.swing.JTextField();
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
        lbl_numeroregistro = new javax.swing.JLabel();
        pnlproducto = new javax.swing.JPanel();
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
        btn_agregar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_limpiar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btn_buscarproducto = new javax.swing.JButton();
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

        pnlUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION DE USUARIO"));

        lbl_unidad.setText("Unidad ");

        cbx_unidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_unidadActionPerformed(evt);
            }
        });

        lbl_nombretecnico.setText("Nombre del Técnico");

        lbl_correo.setText("Correo electrónico");

        lbl_anexo.setText("Anexo");

        lbl_fechacreacion.setText("fecha");

        txt_fecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txt_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fechaActionPerformed(evt);
            }
        });

        lbl_LlaveRepuesto.setToolTipText("");

        lblzonal.setText("Zonal");

        cbxzonal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxzonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxzonalActionPerformed(evt);
            }
        });

        lbl_numerosolicitud.setText("Nº Solicitud");

        txt_nombretecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombretecnicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlUsuarioLayout = new javax.swing.GroupLayout(pnlUsuario);
        pnlUsuario.setLayout(pnlUsuarioLayout);
        pnlUsuarioLayout.setHorizontalGroup(
            pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsuarioLayout.createSequentialGroup()
                        .addComponent(lbl_nombretecnico)
                        .addGap(40, 40, 40)
                        .addComponent(txt_nombretecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUsuarioLayout.createSequentialGroup()
                        .addComponent(lbl_numerosolicitud)
                        .addGap(81, 81, 81)
                        .addComponent(txt_numerosolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(lblzonal, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbxzonal, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(lbl_unidad)
                        .addGap(48, 48, 48)
                        .addComponent(cbx_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsuarioLayout.createSequentialGroup()
                        .addGap(491, 491, 491)
                        .addComponent(lbl_LlaveRepuesto))
                    .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlUsuarioLayout.createSequentialGroup()
                            .addGap(41, 41, 41)
                            .addComponent(lbl_fechacreacion, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txt_fecha))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlUsuarioLayout.createSequentialGroup()
                            .addComponent(lbl_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(32, 32, 32)
                            .addComponent(txt_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlUsuarioLayout.setVerticalGroup(
            pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuarioLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbx_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblzonal)
                        .addComponent(cbxzonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_numerosolicitud)
                        .addComponent(txt_numerosolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_unidad)
                        .addComponent(lbl_fechacreacion)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nombretecnico)
                    .addComponent(lbl_correo)
                    .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_anexo)
                    .addComponent(txt_nombretecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_LlaveRepuesto)
                .addGap(66, 66, 66))
        );

        pnlSistema.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION DEL SISTEMA"));

        lbl_sistema.setText("Sistema / Equipo");

        cbx_sistema.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_sistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_sistemaActionPerformed(evt);
            }
        });

        lbl_marcaequipo.setText("Marca Equipo");

        txt_marcaequipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_marcaequipoActionPerformed(evt);
            }
        });

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
                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSistemaLayout.createSequentialGroup()
                        .addComponent(lbl_marcaequipo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(txt_marcaequipo, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSistemaLayout.createSequentialGroup()
                        .addComponent(lbl_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbx_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSistemaLayout.createSequentialGroup()
                        .addComponent(lbl_condicionsistema, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbx_condicionsistema, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSistemaLayout.createSequentialGroup()
                        .addComponent(lbl_modeloequipo)
                        .addGap(57, 57, 57)
                        .addComponent(txt_modeloequipo, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_numeroserie)
                        .addGap(18, 18, 18)
                        .addComponent(txt_numeroserie, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSistemaLayout.setVerticalGroup(
            pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSistemaLayout.createSequentialGroup()
                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_sistema)
                    .addComponent(cbx_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_condicionsistema)
                    .addComponent(cbx_condicionsistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_marcaequipo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_marcaequipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_modeloequipo)
                    .addComponent(txt_modeloequipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_numeroserie)
                    .addComponent(txt_numeroserie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlinformacion_sistema.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION DEL COMPONENTE DEFECTUOSO A REEMPLAZAR"));

        lbl_fallacomponente.setText("Falla de (los) Componente(s) Defectuoso(s)");

        lbl_sistomasfalla.setText("Síntoma de la(s) falla(s) encontrada(s)");

        txt_sintomafalla.setColumns(20);
        txt_sintomafalla.setRows(5);
        jScrollPane1.setViewportView(txt_sintomafalla);

        lbl_solicitudorden.setText("Orden de Trabajo Nº");

        lbl_fechaSolicitud.setText("Fecha");

        txt_fechasolicitud.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        javax.swing.GroupLayout pnlinformacion_sistemaLayout = new javax.swing.GroupLayout(pnlinformacion_sistema);
        pnlinformacion_sistema.setLayout(pnlinformacion_sistemaLayout);
        pnlinformacion_sistemaLayout.setHorizontalGroup(
            pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlinformacion_sistemaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_fallacomponente)
                    .addComponent(lbl_sistomasfalla))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlinformacion_sistemaLayout.createSequentialGroup()
                        .addComponent(txt_fallaComponente, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_solicitudorden)
                        .addGap(34, 34, 34)
                        .addComponent(txt_numeroorden, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(lbl_fechaSolicitud)
                        .addGap(50, 50, 50)
                        .addComponent(txt_fechasolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        pnlinformacion_sistemaLayout.setVerticalGroup(
            pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlinformacion_sistemaLayout.createSequentialGroup()
                .addGroup(pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_fallacomponente)
                    .addComponent(txt_fallaComponente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_solicitudorden)
                    .addComponent(txt_numeroorden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_fechaSolicitud)
                    .addComponent(txt_fechasolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlinformacion_sistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_sistomasfalla)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGap(31, 31, 31)
                .addGroup(pnlPrioridadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrioridadLayout.createSequentialGroup()
                        .addComponent(cbx_prioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
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

        pnlproducto.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION PRODUCTO"));

        lbl_numeroparte.setText("Numero Parte");

        lbl_nsn.setText("N.S.N");

        lblproducto.setText("Producto");

        lbl_descripcion.setText("Descripción");

        lbl_cantidad.setText("Cantidad");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1025, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lbl_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btn_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbl_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_descripcion))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lbl_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(txt_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(lblproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(lbl_numeroparte)
                                .addGap(18, 18, 18)
                                .addComponent(txt_numeroparte, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nsn)
                    .addComponent(txt_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_numeroparte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_numeroparte)
                    .addComponent(lblproducto)
                    .addComponent(txt_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_descripcion)
                    .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_limpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_cantidad)
                        .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_agregar)
                        .addComponent(btn_eliminar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btn_buscarproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_buscar.png"))); // NOI18N
        btn_buscarproducto.setText("Buscar");
        btn_buscarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarproductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_buscarproducto, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btn_buscarproducto)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlproductoLayout = new javax.swing.GroupLayout(pnlproducto);
        pnlproducto.setLayout(pnlproductoLayout);
        pnlproductoLayout.setHorizontalGroup(
            pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlproductoLayout.createSequentialGroup()
                        .addGap(837, 837, 837)
                        .addComponent(lblllaveproducto))
                    .addGroup(pnlproductoLayout.createSequentialGroup()
                        .addGap(946, 946, 946)
                        .addComponent(lblllaveprogramadadetalle)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlproductoLayout.setVerticalGroup(
            pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addGroup(pnlproductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlproductoLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblllaveproducto)
                .addGap(138, 138, 138)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlgridLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        pnlgridLayout.setVerticalGroup(
            pnlgridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlgridLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_nuevo.png"))); // NOI18N
        btn_nuevo.setText("Nueva Soliitud");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });

        btn_finalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_finish.png"))); // NOI18N
        btn_finalizar.setText("Finalizar Soliitud");
        btn_finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_finalizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout txt_solicitudLayout = new javax.swing.GroupLayout(txt_solicitud);
        txt_solicitud.setLayout(txt_solicitudLayout);
        txt_solicitudLayout.setHorizontalGroup(
            txt_solicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txt_solicitudLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txt_solicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(txt_solicitudLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_numeroregistro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_finalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addComponent(pnlPrioridad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlinformacion_sistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlgrid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(txt_solicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pnlproducto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(pnlSistema, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        txt_solicitudLayout.setVerticalGroup(
            txt_solicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txt_solicitudLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txt_solicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lbl_numeroregistro)
                    .addComponent(btn_nuevo)
                    .addComponent(btn_finalizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlgrid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlinformacion_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_solicitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_solicitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

                CmbZonal itemsZ = (CmbZonal)cbxzonal.getSelectedItem();
                int id_zonal = Integer.parseInt(itemsZ.getID());
                
                CmbUnidad itemsU = (CmbUnidad)cbx_unidad.getSelectedItem();
                int id_unidad = Integer.parseInt(itemsU.getID());

                cmbCondicionSistema itemsC = (cmbCondicionSistema)cbx_condicionsistema.getSelectedItem();
                int id_condicion = Integer.parseInt(itemsU.getID());

                String stx_nombretecnico = txt_nombretecnico.getText();
                String stx_correo = txt_correo.getText();
                String stx_anexo = txt_anexo.getText();
                                
                Srp = new SolicitudRepuesto(0, id_unidad, id_usuario, fecha, stx_nombretecnico, stx_correo, stx_anexo, 2);
                id_repuesto = repuesto.AgregarRepuesto(Srp);

                if (id_repuesto != 0) {
                    lbl_LlaveRepuesto.setText(String.valueOf(id_repuesto));
                    JOptionPane.showMessageDialog(null, "Solicitud de Respuesto Creada con exito");
                    ActivarInfo();
                    BloquearInfo();
                    CargarDatosPendientes();
                    
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
        btn_nuevo.setEnabled(false);
        
        pnlUsuario.setEnabled(true);
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
        
        txt_fallaComponente.setEnabled(true);
        txt_sintomafalla.setEnabled(true);
        txt_numeroorden.setEnabled(true);
        txt_fechasolicitud.setEnabled(true);
        cbx_prioridad.setEnabled(true);
        txt_detalle.setEnabled(true);
        
        limpiarProducto();
        
    }
    
    private void BloquearInfo()
    {
        pnlUsuario.setEnabled(true);
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
            
            String id_comercial = txt_numerosolicitud.getText();
            
            int id_usuario = Integer.parseInt(Inventario.global_llaveusuario);

            CmbUnidad itemsU = (CmbUnidad)cbx_unidad.getSelectedItem();
            int id_unidad = Integer.parseInt(itemsU.getID());
            
            cmbCondicionSistema itemsC = (cmbCondicionSistema)cbx_condicionsistema.getSelectedItem();
            int id_condicion = Integer.parseInt(itemsC.getID());
            
            String stx_nombretecnico = txt_nombretecnico.getText();
            String stx_correo = txt_correo.getText();
            String stx_anexo = txt_anexo.getText();
            
            CmbSistema itemsS = (CmbSistema)cbx_sistema.getSelectedItem();
            int id_sistema = Integer.parseInt(itemsS.getID());
            
            String stx_marca = txt_marcaequipo.getText();
            String stx_modelo = txt_modeloequipo.getText();
            String stx_numeroserie = txt_numeroserie.getText();
            
            String stx_fallacomponente = txt_fallaComponente.getText();
            String stx_sintoma = txt_sintomafalla.getText();
            String stx_numero = txt_numeroorden.getText();
            
            Date ds = f.parse(txt_fechasolicitud.getText());
            long millisecondss = ds.getTime();

            java.sql.Date fechasolicitud = new java.sql.Date(millisecondss);
            
            CmbPrioridad itemsP = (CmbPrioridad)cbx_prioridad.getSelectedItem();
            int id_prioridad = Integer.parseInt(itemsP.getID());
            
            String stx_detalle = txt_detalle.getText();
            
            
            Pgr = new SolicitudRepuesto(id_repuesto,0,id_comercial, id_unidad, id_usuario, fecha, stx_nombretecnico,stx_correo, stx_anexo, id_sistema, stx_marca, id_condicion, stx_modelo, stx_numeroserie, 0, stx_fallacomponente, stx_sintoma, stx_numero, fechasolicitud, id_prioridad, stx_detalle, 1);
            
            blx_estado = repuesto.UpdateRepuesto(Pgr);
          
            if (blx_estado == false) {
              
                JOptionPane.showMessageDialog(null, "Solicitud de Repuesto finalizada");
                this.setVisible(false);
                /*Limpiar();
                CargarCombo();
                LimpiarTabla();
                btn_nuevo.setEnabled(true);
                */
                
            } else {
                JOptionPane.showMessageDialog(null, "Dato no Agregdo");
            }
        }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_finalizarActionPerformed
    
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
            
            String id_comercial = txt_numerosolicitud.getText();
            
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
            
            String stx_fallacomponente = txt_fallaComponente.getText();
            String stx_sintoma = txt_sintomafalla.getText();
            String stx_numero = txt_numeroorden.getText();
            
            Date ds = f.parse(txt_fechasolicitud.getText());
            long millisecondss = ds.getTime();

            java.sql.Date fechasolicitud = new java.sql.Date(millisecondss);
            
            CmbPrioridad itemsP = (CmbPrioridad)cbx_prioridad.getSelectedItem();
            int id_prioridad = Integer.parseInt(itemsP.getID());
            
            String stx_detalle = txt_detalle.getText();
            
            
            Pgr = new SolicitudRepuesto(id_repuesto,0,id_comercial, id_unidad, id_usuario, fecha, stx_nombretecnico,stx_correo, stx_anexo, id_sistema, stx_marca, id_condicion, stx_modelo, stx_numeroserie, 0, stx_fallacomponente, stx_sintoma, stx_numero, fechasolicitud, id_prioridad, stx_detalle, 2);
            
            blx_estado = repuesto.UpdateRepuesto(Pgr);

            int id_producto = Integer.parseInt(lblllaveproducto.getText());
            int cantidad = Integer.parseInt(txt_cantidad.getText());
            
            Pgrd = new SolicitudRepuestoDetalle(0,id_repuesto, id_producto, cantidad);
            resp = repuestodetalle.AgregarRepuesto(Pgrd);
          
            if (resp == false) {
                
                JOptionPane.showMessageDialog(null, "Dato Agregado");
                limpiarProducto();
                ListarTablaproducto();
                ListarTabla();
                BloquearInfo();
                        
            } else {
                JOptionPane.showMessageDialog(null, "Dato no Agregdo");
            }

        }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btn_agregarActionPerformed

    
    public void generar_pdf()
    {
        try {
            
            CmbUnidad itemsU = (CmbUnidad)cbx_unidad.getSelectedItem();
            int id_unidad = Integer.parseInt(itemsU.getID());
            String stx_unidad = itemsU.toString();
            
            Rectangle small = new Rectangle(290,100);
            Font smallfont = new Font(Font.FontFamily.HELVETICA, 6);
            Font mediumfont = new Font(Font.FontFamily.HELVETICA, 8);
            Font largefont = new Font(Font.FontFamily.HELVETICA, 10);

            FileOutputStream archivo = new FileOutputStream(DEST + txt_numerosolicitud.getText()+".pdf");
            Document doc = new Document(PageSize.LETTER);
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            Image image = Image.getInstance(IMG);
            

            Paragraph paragraph1 = new Paragraph("Despacho de Materiales SAV Nº " + txt_numerosolicitud.getText() + " " + stx_unidad, largefont);
            paragraph1.setSpacingBefore(20f);
            doc.add(paragraph1);

            paragraph1 = new Paragraph("Se realiza despacho de los siguientes materiales a la unidad de " + stx_unidad, mediumfont);
            paragraph1.setSpacingBefore(20f);
            doc.add(paragraph1);

            paragraph1 = new Paragraph("PRIORIDAD DEL DESPACHO : NORMAL", mediumfont);
            paragraph1.setSpacingBefore(20f);
            doc.add(paragraph1);

            paragraph1 = new Paragraph(" ", mediumfont);
            paragraph1.setSpacingBefore(20f);
            doc.add(paragraph1);

            PdfPTable table = new PdfPTable(5);
            table.setTotalWidth(new float[]{ 80, 100,160,100,80});
            table.setLockedWidth(true);

            PdfPCell cell = new PdfPCell(new Phrase("S.N.S", mediumfont));
            cell.setFixedHeight(20);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("PRODUCTO", mediumfont));
            cell.setFixedHeight(20);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("DESCRIPCION", mediumfont));
            cell.setFixedHeight(20);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Nº PARTE", mediumfont));
            cell.setFixedHeight(20);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("CANTIDAD DESPACHO", mediumfont));
            cell.setFixedHeight(20);
            table.addCell(cell);    

            //SECOND ROW

            int llavedespacho = Integer.parseInt(lbl_LlaveRepuesto.getText());
            List<SolicitudRepuestoDetalle> listas = repuestodetalle.listado(llavedespacho);

            for(SolicitudRepuestoDetalle despacho : listas){
                cell = new PdfPCell(new Phrase(despacho.getNsn(), smallfont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(despacho.getProduto(), smallfont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(despacho.getDescripcion(), smallfont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(despacho.getNumeroparte(), smallfont));
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(String.valueOf(despacho.getCantidad()), smallfont));
                table.addCell(cell);
            }
            doc.add(table);

            doc.close();
            JOptionPane.showMessageDialog(null, "PDF correctamente Creado");
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void btn_buscarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarproductoActionPerformed
        // TODO add your handling code here:
        ListarTablaBuscar();
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
        
        limpiarProducto();
    }
    
    private void limpiarProducto(){
        
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

    private void txt_marcaequipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_marcaequipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_marcaequipoActionPerformed

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

    private void txt_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fechaActionPerformed

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

    private void txt_nombretecnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombretecnicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombretecnicoActionPerformed


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
    private javax.swing.JLabel lbl_numerosolicitud;
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
    private javax.swing.JTextField txt_numerosolicitud;
    private javax.swing.JTextField txt_producto;
    private javax.swing.JTextArea txt_sintomafalla;
    private javax.swing.JPanel txt_solicitud;
    // End of variables declaration//GEN-END:variables
}
