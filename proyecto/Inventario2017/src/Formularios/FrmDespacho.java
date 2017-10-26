package Formularios;

import ModeloTabla.CmbZonal;
import ModeloTabla.CmbSistema;
import ModeloTabla.CmbUnidad;
import ModeloTabla.CmbPrioridad;

import Entidades.Despacho;
import Logico.DespachoLog;

import ModeloTabla.ModeloTablaView_ProductoDespachoSolicitado;
import Logico.View_ProductoDespachoSolicitadoLog;
import Entidades.View_ProductoDespachoSolicitado;

import ModeloTabla.ModeloTablaRepuestoDetalle;

import Entidades.DespachoDetalle;
import Logico.DespachoDetalleLog;
import ModeloTabla.ModeloTablaDespachoDetalle;

import java.lang.Object;
import java.awt.Desktop;

import clases.Inventario;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
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
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import org.apache.poi.hwpf.model.FileInformationBlock;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class FrmDespacho extends javax.swing.JInternalFrame {

    DespachoLog cl_despacho_log;
    Despacho cl_despacho;

    DespachoDetalleLog cl_despachodetalle_log;
    DespachoDetalle cl_despachodetalle;
    ModeloTablaDespachoDetalle mt_dd;
    
    View_ProductoDespachoSolicitado viewp;
    View_ProductoDespachoSolicitadoLog view_producto;
    ModeloTablaView_ProductoDespachoSolicitado  view_tabla;
    
    public static final String DEST = "c:/Reportes/SolicitudDespacho_";
    
    public FrmDespacho() {
        
        initComponents();
        cl_despacho_log = new DespachoLog();
        
        cl_despachodetalle_log = new DespachoDetalleLog();
        view_producto = new View_ProductoDespachoSolicitadoLog();
        
        CargarCombo();
        Limpiar();
        lbl_LlaveDespacho.setVisible(false);
        CargarDatosPendientes();
        
    }
    
    public void CargarDatosPendientes()
    {
        
        try
        {
            ResultSet objResultSet;
            objResultSet = Conecciones.Coneccion.consulta(" SELECT Despacho.id_despacho, Despacho.id_temporada, Despacho.id_comercial, Despacho.id_usuario, Despacho.id_unidad, Despacho.id_sistema, convert(varchar(10),Despacho.fecha,103) as fecha, Despacho.id_prioridad, Despacho.nombre_recibe, " +
                                                            " Despacho.correo_recibe, Despacho.anexo_recibe, Despacho.estado, unidad.id_zonal " +
                                                            " FROM            Despacho left JOIN " +
                                                            " unidad ON Despacho.id_unidad = unidad.id_unidad " +
                                                            " WHERE (Despacho.estado = 2) ");

            while (objResultSet.next())
            {
                ActivarInfo();
                
                lbl_LlaveDespacho.setText(objResultSet.getString("id_despacho"));
                
                lbl_idcomercio.setText(objResultSet.getString("id_comercial"));
                txt_nombretecnico.setText(objResultSet.getString("nombre_recibe"));
                txt_correo.setText(objResultSet.getString("correo_recibe"));
                txt_anexo.setText(objResultSet.getString("anexo_recibe"));
                          
                if(objResultSet.getString("id_zonal") != null)
                {
                    cbxzonal.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_zonal")));

                    CargaUnidad();
                    
                    if(objResultSet.getString("id_unidad") != "0")
                    {
                        cbx_unidad.getModel().setSelectedItem(new CmbUnidad(objResultSet.getString("nombre_unidad"), objResultSet.getString("id_unidad")));
                        
                    }
                }
                
                if(objResultSet.getString("id_sistema") != null)
                {
                    cbxsistema.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_sistema")));
                }
                
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

                Date d = f.parse(objResultSet.getString("fecha"));
                
                txt_fecha.setValue(d);

                if(objResultSet.getString("id_prioridad") != null)
                {
                    cbx_prioridad.setSelectedIndex(Integer.parseInt(objResultSet.getString("id_prioridad")));
                }
                                
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
        
        CmbSistema itemsis = (CmbSistema)cbxsistema.getSelectedItem();
        int id_sistema = Integer.parseInt(itemsis.getID());
        
        String filtroNSN = txt_nsn.getText();
        String filtroParte = txt_numeroparte.getText();
        String filtroproducto = txt_producto.getText();
        
        if(id_sistema > 0 && id_unidad > 0){
        
            List<View_ProductoDespachoSolicitado> listas = view_producto.listado(id_sistema , id_unidad,0, filtroNSN, filtroParte, filtroproducto);

            if(listas.size()  >0){
                view_tabla = new ModeloTablaView_ProductoDespachoSolicitado(listas);
                jTable2.setModel(view_tabla);
                jTable2.getRowSorter();
            } 
            else 
            {
                JOptionPane.showMessageDialog(null, "No hay Solicitudes para la Unidad y Sistema seleccionado");
            } 
        }
    }
    
    private void ListarTabla() {
        
        int llavedespacho = Integer.parseInt(lbl_LlaveDespacho.getText());
        
        List<DespachoDetalle> listas = cl_despachodetalle_log.listado(llavedespacho);
        mt_dd = new ModeloTablaDespachoDetalle(listas);
        
        jTable1.setModel(mt_dd);
        jTable1.getRowSorter();
        
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
            cbxsistema.setModel(modeloSistema);//con esto lo agregamos al objeto al jcombobox
            while (objResultSetsistema.next())
            {
                modeloSistema.addElement(new CmbSistema(objResultSetsistema.getString("nombre_sistema") , objResultSetsistema.getString("id_sistema")));
                cbxsistema.setModel(modeloSistema);
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
            
        lbl_LlaveDespacho.setText("");
        lblllaveproducto.setText("");
        lblllavedespachodetalle.setText("");
        
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

        cbxsistema.setEnabled(false);
        
        txt_nombretecnico.setText("");
        txt_correo.setText("");
        txt_anexo.setText("");
        txt_fecha.setValue(new Date());
        
        txt_nsn.setText("");
        txt_numeroparte.setText("");
        txt_producto.setText("");
        txt_descripcion.setText("");
        txt_cantidad.setText("");
        
        cbx_prioridad.setEnabled(false);
        
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
    
        
        List<DespachoDetalle> listas = null;
        mt_dd = new ModeloTablaDespachoDetalle(listas);
        
        jTable1.setModel(mt_dd);
        jTable1.getRowSorter();
    
        
        List<View_ProductoDespachoSolicitado> listas2 = null;
        view_tabla = new ModeloTablaView_ProductoDespachoSolicitado(listas2);
        
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

        jLabel18 = new javax.swing.JLabel();
        btn_nuevo = new javax.swing.JButton();
        btn_finalizar = new javax.swing.JButton();
        pnlDespacho = new javax.swing.JPanel();
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
        lbl_LlaveDespacho = new javax.swing.JLabel();
        lblzonal = new javax.swing.JLabel();
        cbxzonal = new javax.swing.JComboBox<>();
        lbl_sistema = new javax.swing.JLabel();
        cbxsistema = new javax.swing.JComboBox<>();
        lbl_Prioridad = new javax.swing.JLabel();
        cbx_prioridad = new javax.swing.JComboBox<>();
        pnlproducto = new javax.swing.JPanel();
        lblllaveproducto = new javax.swing.JLabel();
        lblllavedespachodetalle = new javax.swing.JLabel();
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
        btn_refrescar = new javax.swing.JButton();
        pnlgrid = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lbl_idcomercio = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("SOLICITUD DE DESPACHO");

        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_nuevo.png"))); // NOI18N
        btn_nuevo.setText("Nuevo Despacho");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });

        btn_finalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_finish.png"))); // NOI18N
        btn_finalizar.setText("Finalizar Despacho");
        btn_finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_finalizarActionPerformed(evt);
            }
        });

        pnlDespacho.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION DEL DESPACHO"));

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

        lbl_LlaveDespacho.setToolTipText("");

        lblzonal.setText("Zonal");

        cbxzonal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxzonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxzonalActionPerformed(evt);
            }
        });

        lbl_sistema.setText("Sistema / Equipo");

        cbxsistema.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxsistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxsistemaActionPerformed(evt);
            }
        });

        lbl_Prioridad.setText("Prioridad");

        cbx_prioridad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_prioridad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_prioridadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDespachoLayout = new javax.swing.GroupLayout(pnlDespacho);
        pnlDespacho.setLayout(pnlDespachoLayout);
        pnlDespachoLayout.setHorizontalGroup(
            pnlDespachoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDespachoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDespachoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDespachoLayout.createSequentialGroup()
                        .addComponent(lblzonal, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(cbxzonal, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_unidad)
                        .addGroup(pnlDespachoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDespachoLayout.createSequentialGroup()
                                .addGap(654, 654, 654)
                                .addComponent(lbl_LlaveDespacho))
                            .addGroup(pnlDespachoLayout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addGroup(pnlDespachoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbx_prioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlDespachoLayout.createSequentialGroup()
                                        .addComponent(cbx_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbl_fechacreacion, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(44, 44, 44)
                                        .addGroup(pnlDespachoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(pnlDespachoLayout.createSequentialGroup()
                        .addComponent(lbl_sistema, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDespachoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDespachoLayout.createSequentialGroup()
                                .addComponent(cbxsistema, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_Prioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDespachoLayout.createSequentialGroup()
                                .addComponent(txt_nombretecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lbl_nombretecnico))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        pnlDespachoLayout.setVerticalGroup(
            pnlDespachoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDespachoLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(pnlDespachoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblzonal)
                    .addComponent(cbxzonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_fechacreacion)
                    .addComponent(lbl_unidad)
                    .addComponent(cbx_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDespachoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_sistema)
                    .addComponent(cbxsistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Prioridad)
                    .addComponent(cbx_prioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(pnlDespachoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nombretecnico)
                    .addComponent(txt_nombretecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_correo)
                    .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_anexo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_LlaveDespacho)
                .addGap(66, 66, 66))
        );

        pnlproducto.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMACION PRODUCTO SOLICITADO"));

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
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(txt_nsn, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(lbl_numeroparte)
                        .addGap(18, 18, 18)
                        .addComponent(txt_numeroparte, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(btn_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_descripcion)))
                .addContainerGap())
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_cantidad))
                    .addComponent(btn_agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_limpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        btn_refrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btn_refresh.png"))); // NOI18N
        btn_refrescar.setText("Refrescar");
        btn_refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_refrescar, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(btn_buscarproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btn_buscarproducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_refrescar)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addComponent(lblllavedespachodetalle))
            .addGroup(pnlproductoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                .addComponent(lblllavedespachodetalle)
                .addGap(123, 123, 123))
        );

        pnlgrid.setBorder(javax.swing.BorderFactory.createTitledBorder("PRODUCTOS PARA DESPACHO"));

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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1199, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlgridLayout.setVerticalGroup(
            pnlgridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlgridLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addContainerGap())
        );

        lbl_idcomercio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDespacho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlgrid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_idcomercio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_finalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_nuevo)
                            .addComponent(btn_finalizar)
                            .addComponent(lbl_idcomercio, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDespacho, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlgrid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                int id_despacho = 0;
                Despacho dsp;
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                Date d = f.parse(txt_fecha.getText());
                long milliseconds = d.getTime();
                java.sql.Date fecha = new java.sql.Date(milliseconds);

                dsp = new Despacho(0, id_usuario, fecha, 2);
                id_despacho = cl_despacho_log.AgregarDespacho(dsp);

                if (id_despacho != 0) {

                    lbl_LlaveDespacho.setText(String.valueOf(id_despacho));
                    JOptionPane.showMessageDialog(null, "Despacho creado con éxito, complete la información para finalizar ");
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
        btn_nuevo.setEnabled(false);
        
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
        cbxsistema.setEnabled(true);
        
        txt_nsn.setText("");
        txt_numeroparte.setText("");
        txt_producto.setText("");
        txt_descripcion.setText("");
        txt_cantidad.setText("");
        
        cbx_prioridad.setEnabled(true);
        
        activarProducto();
        
    }
    
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
        txt_producto.setEnabled(true);
        txt_cantidad.setEnabled(false);
        txt_descripcion.setEnabled(false);
        txt_numeroparte.setEnabled(true);

        btn_buscarproducto.setEnabled(true);
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(false);

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

                DespachoDetalle cl_despachodetalle;

                int id_despacho = Integer.parseInt(lbl_LlaveDespacho.getText());
                Despacho cl_despacho;

                String id_comercial = lbl_idcomercio.getText();

                int id_usuario = Integer.parseInt(Inventario.global_llaveusuario);

                CmbUnidad itemsU = (CmbUnidad)cbx_unidad.getSelectedItem();
                int id_unidad = Integer.parseInt(itemsU.getID());

                String stx_nombrerecibe = txt_nombretecnico.getText();
                String stx_correorecibe = txt_correo.getText();
                String stx_anexorecibe = txt_anexo.getText();

                CmbSistema itemsS = (CmbSistema)cbxsistema.getSelectedItem();
                int id_sistema = Integer.parseInt(itemsS.getID());

                CmbPrioridad itemsP = (CmbPrioridad)cbx_prioridad.getSelectedItem();
                int id_prioridad = Integer.parseInt(itemsP.getID());


                cl_despacho = new Despacho(id_despacho,0,id_comercial, id_unidad, id_usuario, fecha, stx_nombrerecibe, stx_correorecibe , stx_anexorecibe , id_sistema, id_prioridad, 1);

                blx_estado = cl_despacho_log.UpdateDespacho(cl_despacho);

                if (blx_estado == false) {

                    descontar_producto();
                    
                    JOptionPane.showMessageDialog(null, "Despacho de Productos finalizados");
                    
                    generar_pdf(); 
                    
                    /*Limpiar();
                    CargarCombo();
                    LimpiarTabla();
                    btn_nuevo.setEnabled(true);
                    lbl_idcomercio.setText("");
                   */
                    this.setVisible(false);

                } else {
                    JOptionPane.showMessageDialog(null, "Dato no Agregdo");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_btn_finalizarActionPerformed

    private void descontar_producto(){
        
        int id_despacho = Integer.parseInt(lbl_LlaveDespacho.getText());
        Despacho cl_despacho;
        
        boolean resp = cl_despacho_log.DescontarProducto(id_despacho);
        
    }
    
    private void cbx_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_unidadActionPerformed
        try
        {
            CmbUnidad items = (CmbUnidad)cbx_unidad.getSelectedItem();
            int id_unidad = Integer.parseInt(items.getID());

            if(id_unidad>0){
                ListarTablaBuscar();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getCause());
        }
    }//GEN-LAST:event_cbx_unidadActionPerformed

    private void cbxzonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxzonalActionPerformed

        CargaUnidad();
    }

    private void CargaUnidad()
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

    private void cbxsistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxsistemaActionPerformed
        try
        {
            CmbSistema items = (CmbSistema)cbxsistema.getSelectedItem();
            int id_sistema = Integer.parseInt(items.getID());

            if(id_sistema>0){
                ListarTablaBuscar();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getCause());
        }
    }//GEN-LAST:event_cbxsistemaActionPerformed

    private void cbx_prioridadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_prioridadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_prioridadActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        viewp = ((ModeloTablaView_ProductoDespachoSolicitado) jTable2.getModel()).DameDespachoSolicitado(jTable2.getSelectedRow());

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

                Date d = f.parse(txt_fecha.getText());
                long milliseconds = d.getTime();

                java.sql.Date fecha = new java.sql.Date(milliseconds);

                boolean blx_estado = true;
                boolean resp = true;

                DespachoDetalle cl_despachodetalle;

                int id_despacho = Integer.parseInt(lbl_LlaveDespacho.getText());
                Despacho cl_despacho;

                String id_comercial = lbl_idcomercio.getText();

                int id_usuario = Integer.parseInt(Inventario.global_llaveusuario);

                CmbUnidad itemsU = (CmbUnidad)cbx_unidad.getSelectedItem();
                int id_unidad = Integer.parseInt(itemsU.getID());
                String stx_unidad = itemsU.toString();

                String stx_nombrerecibe = txt_nombretecnico.getText();
                String stx_correorecibe = txt_correo.getText();
                String stx_anexorecibe = txt_anexo.getText();

                CmbSistema itemsS = (CmbSistema)cbxsistema.getSelectedItem();
                int id_sistema = Integer.parseInt(itemsS.getID());

                CmbPrioridad itemsP = (CmbPrioridad)cbx_prioridad.getSelectedItem();
                int id_prioridad = Integer.parseInt(itemsP.getID());


                cl_despacho = new Despacho(id_despacho,0,id_comercial, id_unidad, id_usuario, fecha, stx_nombrerecibe, stx_correorecibe , stx_anexorecibe , id_sistema, id_prioridad, 2);

                blx_estado = cl_despacho_log.UpdateDespacho(cl_despacho);


                int id_producto = Integer.parseInt(lblllaveproducto.getText());
                int cantidad = Integer.parseInt(txt_cantidad.getText());

                cl_despachodetalle = new DespachoDetalle(0,id_despacho, id_producto, cantidad, 0);
                resp = cl_despachodetalle_log.AgregarDespachoDetalle(cl_despachodetalle);

                if (resp == false) {

                    JOptionPane.showMessageDialog(null, "Dato Agregado");
                    activarProducto();
                    ListarTablaBuscar();
                    ListarTabla();
                   

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

            FileOutputStream archivo = new FileOutputStream(DEST + lbl_idcomercio.getText()+".pdf");
            Document doc = new Document(PageSize.LETTER);
            PdfWriter.getInstance(doc, archivo);
            doc.open();


            Paragraph paragraph1 = new Paragraph("Despacho de Materiales SAV Nº " + lbl_idcomercio.getText() + " " + stx_unidad, largefont);
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

            int llavedespacho = Integer.parseInt(lbl_LlaveDespacho.getText());
            List<DespachoDetalle> listas = cl_despachodetalle_log.listado(llavedespacho);

            for(DespachoDetalle despacho : listas){
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
            /*JOptionPane.showMessageDialog(null, "PDF correctamente Creado");*/
            
            File path = new File (DEST + lbl_idcomercio.getText()+".pdf");
            Desktop.getDesktop().open(path);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        
        boolean resp = cl_despachodetalle_log.DeleteDespachoDetalle(cl_despachodetalle);
        if (resp == false) {
            JOptionPane.showMessageDialog(null, "Dato Eliminado");
            ListarTabla();
            ListarTablaBuscar();
            activarProducto();
        } else {
            JOptionPane.showMessageDialog(null, "Dato no Eliminado");
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        txt_producto.setEditable(true);
        txt_nsn.setEditable(true);
        txt_numeroparte.setEditable(true);
        txt_descripcion.setEditable(true);
        txt_cantidad.setEditable(true);

        lblllaveproducto.setText("");
        lblllavedespachodetalle.setText("");

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

    private void btn_buscarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarproductoActionPerformed
        // TODO add your handling code here:
       
        ListarTablaBuscar();
        
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_limpiar.setEnabled(true);
    }//GEN-LAST:event_btn_buscarproductoActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        cl_despachodetalle = ((ModeloTablaDespachoDetalle) jTable1.getModel()).DameDespachoDetalle(jTable1.getSelectedRow());

        lblllavedespachodetalle.setText(String.valueOf(cl_despachodetalle.getIdDetalleDespacho()));

        txt_nsn.setText(cl_despachodetalle.getNsn());
        txt_numeroparte.setText(cl_despachodetalle.getNumeroparte());
        txt_descripcion.setText(cl_despachodetalle.getDescripcion());
        txt_producto.setText(cl_despachodetalle.getProduto());
        txt_cantidad.setText(String.valueOf(cl_despachodetalle.getCantidad()));

        btn_buscarproducto.setEnabled(false);
        btn_agregar.setEnabled(false);
        btn_eliminar.setEnabled(true);
        btn_limpiar.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked

    private void btn_refrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refrescarActionPerformed

        
        ListarTabla();

    }//GEN-LAST:event_btn_refrescarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_buscarproducto;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_finalizar;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_refrescar;
    private javax.swing.JComboBox<String> cbx_prioridad;
    private javax.swing.JComboBox<String> cbx_unidad;
    private javax.swing.JComboBox<String> cbxsistema;
    private javax.swing.JComboBox<String> cbxzonal;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lbl_LlaveDespacho;
    private javax.swing.JLabel lbl_Prioridad;
    private javax.swing.JLabel lbl_anexo;
    private javax.swing.JLabel lbl_cantidad;
    private javax.swing.JLabel lbl_correo;
    private javax.swing.JLabel lbl_descripcion;
    private javax.swing.JLabel lbl_fechacreacion;
    private javax.swing.JLabel lbl_idcomercio;
    private javax.swing.JLabel lbl_nombretecnico;
    private javax.swing.JLabel lbl_nsn;
    private javax.swing.JLabel lbl_numeroparte;
    private javax.swing.JLabel lbl_sistema;
    private javax.swing.JLabel lbl_unidad;
    private javax.swing.JLabel lblllavedespachodetalle;
    private javax.swing.JLabel lblllaveproducto;
    private javax.swing.JLabel lblproducto;
    private javax.swing.JLabel lblzonal;
    private javax.swing.JPanel pnlDespacho;
    private javax.swing.JPanel pnlgrid;
    private javax.swing.JPanel pnlproducto;
    private javax.swing.JTextField txt_anexo;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_correo;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JFormattedTextField txt_fecha;
    private javax.swing.JTextField txt_nombretecnico;
    private javax.swing.JTextField txt_nsn;
    private javax.swing.JTextField txt_numeroparte;
    private javax.swing.JTextField txt_producto;
    // End of variables declaration//GEN-END:variables
}
