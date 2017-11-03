package Formularios;

import Entidades.SolicitudRepuestoDetalle;
import Entidades.View_ReporteSolicitudProgramada;
import Logico.ProductoLog;

import Entidades.View_ReporteSolicitudRepuesto;
import Logico.SolicitudRepuestoDetalleLog;
import Logico.View_ReporteSolicitudRepuestoLog;
import ModeloTabla.CmbUnidad;
import ModeloTabla.ModeloTablaRepuestoDetalle;
import ModeloTabla.ModeloTablaView_ReporteSolicitudProgramada;

import ModeloTabla.ModeloTablaView_ReporteSolicitudRepuesto;
import clases.Datos;

import clases.Inventario;
import clases.Render;

import java.io.File;
import java.io.FileInputStream;
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
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import org.apache.poi.hwpf.model.FileInformationBlock;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;

public class FrmInformeSolicitudRepuesto extends javax.swing.JInternalFrame {

    View_ReporteSolicitudRepuesto obxdatos;
    View_ReporteSolicitudRepuestoLog claselog;
    ModeloTablaView_ReporteSolicitudRepuesto modelo;
    
    SolicitudRepuestoDetalleLog repuestodetalle;
    SolicitudRepuestoDetalle spd;
    ModeloTablaRepuestoDetalle mtrd;

    public FrmInformeSolicitudRepuesto() {
        initComponents();
        int id_usuario = Integer.parseInt(Inventario.global_llaveusuario);
        
        claselog = new View_ReporteSolicitudRepuestoLog();
        repuestodetalle = new SolicitudRepuestoDetalleLog();
        
        ListarTabla();
    }
    
    private void ListarTabla() {
                
        List<View_ReporteSolicitudRepuesto> listas = claselog.listado();
        modelo = new ModeloTablaView_ReporteSolicitudRepuesto
        (
                listas
        )
        {
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        jTable1.setDefaultRenderer(Object.class, new Render());
        JButton btn1 = new JButton("Imprimir");
        
        jTable1.setModel(modelo);
        jTable1.setRowHeight(30);
        
        jTable1.getRowSorter();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlgrid = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        pnlgrid.setBorder(javax.swing.BorderFactory.createTitledBorder("SOLICITUD SOLICITUD REPUESTO"));

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1118, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlgridLayout.setVerticalGroup(
            pnlgridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlgridLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlgrid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlgrid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
        obxdatos = ((ModeloTablaView_ReporteSolicitudRepuesto) jTable1.getModel()).DameProgramadaDetalle(jTable1.getSelectedRow());
        
        int llave = obxdatos.getIdSolicitudRepuesto();
        
        int column = jTable1.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY()/jTable1.getRowHeight();
        
        if(row < jTable1.getRowCount() && row >= 0 && column < jTable1.getColumnCount() && column >= 0) {
            Object value = jTable1.getValueAt(row, column);
            if(value instanceof JButton){
                ((JButton)value).doClick();
                generar_Reporte(llave);
            }
        }
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void generar_Reporte(int llave){
        try
        {
            long datetimemlli= Datos.DatetoMilisecond(new Date());
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream("c:/excel/template_reporteresolicitud.xlsx"));
            XSSFSheet ws = wb.getSheetAt(0);
            /*
            XSSFSheet ws = wb.createSheet("Inventario_completo");
            */
            ResultSet objResultSet;
            objResultSet = Conecciones.Coneccion.consulta(" select sr.id_solicitudrepuesto, sr.id_comercial, " +
                "u.nombre_unidad, sr.fecha_creacion, sr.nombre_tecnico," +
                "z.nombre_zonal,s.nombre_sistema, " +
                "sr.correo_electronico, sr.Anexo,sr.marca_equipo, " +
                "sr.modelo_equipo, sr.numero_Serie,sr.falla_componente, sr.sintoma_falla, " +
                "sr.solicutus_orden_trabajo, sr.fecha_solicitud, " +
                "(select ps.nombre_prioridad   " +
                "from prioridad_solicitud as ps " +
                "where ps.id_prioridad = sr.id_prioridad) as prioridad_solicitud, " +
                "sr.detalle " +
                "from solucitud_repuesto as sr inner join unidad as u " +
                "on sr.id_unidad = u.id_unidad inner join zonal as z " +
                "on u.id_zonal = z.id_zonal " +
                "inner join sistema as s on s.id_sistema = sr.id_sistema" +
                " where sr.id_solicitudrepuesto = " + llave);

            String id_comercial = ""; 
            String stx_nombretecnico = "";
            String stx_correo = "";
            String stx_anexo = "";
            String stx_pista = "";
            String stx_zonal = "";
            String stx_unidad = "";
            String stx_sistema = "";
            String stx_marca = "";
            String stx_modelo = "";
            String stx_numeroserie = "";
            String stx_condicion = "";
            String stx_fallaComponente = "";
            String stx_sintomafalla = "";
            String stx_numeroorden = "";
            String fecha_creacion =  null;
            String fecha_solicitud =  null;
            String stx_prioridad = "";
            String stx_numero = "";
            
            while (objResultSet.next())
            {
                
                id_comercial = objResultSet.getString("id_comercial");
                stx_nombretecnico = objResultSet.getString("nombre_tecnico");
                stx_correo = objResultSet.getString("correo_electronico");
                stx_anexo = objResultSet.getString("Anexo");
                stx_zonal = objResultSet.getString("nombre_zonal");
                stx_unidad = objResultSet.getString("nombre_unidad");
                stx_sistema = objResultSet.getString("nombre_sistema");
                stx_numero = objResultSet.getString("id_comercial");
                                
                if(objResultSet.getString("fecha_creacion") != null)
                {
                  Date date = objResultSet.getDate("fecha_creacion");
                  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                   fecha_creacion = df.format(date);
                } 
                
                stx_marca = objResultSet.getString("marca_equipo");
                stx_modelo = objResultSet.getString("modelo_equipo");
                stx_numeroserie = objResultSet.getString("numero_Serie");
                /*
                stx_condicion = objResultSet.getString("nombre_condicionsistema");
                */
                                
                stx_fallaComponente = objResultSet.getString("falla_componente");
                stx_sintomafalla = objResultSet.getString("sintoma_falla");
                stx_numeroorden = objResultSet.getString("solicutus_orden_trabajo");
                
                if(objResultSet.getString("fecha_solicitud") != null)
                {
                  Date date = objResultSet.getDate("fecha_solicitud");
                  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                   fecha_solicitud = df.format(date);
                }
                
                stx_prioridad = objResultSet.getString("prioridad_solicitud");
                
            }
            
            XSSFCellStyle cs = wb.createCellStyle();
            XSSFDataFormat dt = wb.createDataFormat();
            
            
            XSSFRow row;
            
            cs.setDataFormat(dt.getFormat("0"));
            cs.setBorderRight(CellStyle.BORDER_THIN);
            cs.setRightBorderColor(IndexedColors.WHITE.getIndex());
            cs.setBorderBottom(CellStyle.BORDER_THIN);
            cs.setBottomBorderColor(IndexedColors.WHITE.getIndex());
            cs.setBorderLeft(CellStyle.BORDER_THIN);
            cs.setLeftBorderColor(IndexedColors.WHITE.getIndex());
            cs.setBorderTop(CellStyle.BORDER_THIN);
            cs.setTopBorderColor(IndexedColors.WHITE.getIndex());
            
            
            row=ws.createRow(6);
            
            Cell cell = row.createCell(1);
            cell.setCellValue("Nº");
            cell.setCellStyle(cs);
            
            cell = row.createCell(2);
            cell.setCellValue(stx_numero);
            cell.setCellStyle(cs);
            
            cell = row.createCell(0);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(3);
            cell.setCellValue("");
            cell.setCellStyle(cs);
  
            cell = row.createCell(4);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(5);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(6);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            /*fila 2*/
            row=ws.createRow(7);
            
            cell = row.createCell(1);
            cell.setCellValue("Zonal");
            cell.setCellStyle(cs);
            
            cell = row.createCell(2);
            cell.setCellValue(stx_zonal);
            cell.setCellStyle(cs);
            
            cell = row.createCell(3);
            cell.setCellValue("Unidad");
            cell.setCellStyle(cs);
            
            cell = row.createCell(4);
            cell.setCellValue(stx_unidad);
            cell.setCellStyle(cs);
            
            cell = row.createCell(0);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(5);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(6);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            /*fila 2*/
            row=ws.createRow(9);
            cell = row.createCell(1);
            cell.setCellValue("Pista");
            cell.setCellStyle(cs);
            
            cell = row.createCell(2);
            cell.setCellValue(stx_pista);
            cell.setCellStyle(cs);
            
            cell = row.createCell(3);
            cell.setCellValue("Fecha");
            cell.setCellStyle(cs);
            
            cell = row.createCell(4);
            cell.setCellValue(fecha_solicitud);
            cell.setCellStyle(cs);
            
            cell = row.createCell(0);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(5);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(6);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            /*fila 3*/
            row=ws.createRow(10);
            cell = row.createCell(1);
            cell.setCellValue("Sistema / Equipo");
            cell.setCellStyle(cs);
            
            cell = row.createCell(2);
            cell.setCellValue(stx_sistema);
            cell.setCellStyle(cs);
            
            cell = row.createCell(3);
            cell.setCellValue("Marca");
            cell.setCellStyle(cs);
            
            cell = row.createCell(4);
            cell.setCellValue(stx_marca);
            cell.setCellStyle(cs);
            
            cell = row.createCell(0);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(5);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(6);
            cell.setCellValue("");
            cell.setCellStyle(cs);
             /* fila 4*/
             
             row=ws.createRow(11);
             cell = row.createCell(1);
            cell.setCellValue("Modelo");
            cell.setCellStyle(cs);
            
            cell = row.createCell(2);
            cell.setCellValue(stx_modelo);
            cell.setCellStyle(cs);
            
            cell = row.createCell(3);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(4);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(0);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(5);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            cell = row.createCell(6);
            cell.setCellValue("");
            cell.setCellStyle(cs);
            
            int rowfila =1;
                        
            List<SolicitudRepuestoDetalle> listas = repuestodetalle.listado(llave);
            mtrd = new ModeloTablaRepuestoDetalle(listas);
            
            //load data to treemap
            TreeMap<Integer,Object[]> data = new TreeMap<>();
            int pos = 0;
            //data.put("0",new Object[]{mtp.getColumnName(0), mtp.getColumnName(1),mtp.getColumnName(2), mtp.getColumnName(3), mtp.getColumnName(4), mtp.getColumnName(5)});

            for (SolicitudRepuestoDetalle prd : mtrd.RepuestoDetalles) {
                data.put(pos + 1,new Object[]{mtrd.getValueAt(pos,0), mtrd.getValueAt(pos,1), mtrd.getValueAt(pos,2), mtrd.getValueAt(pos,3), mtrd.getValueAt(pos,4), mtrd.getValueAt(pos,5)});
                pos++;
            }

            Set<Integer> ids = data.keySet();
            int rowId = 14;

            for(Integer key:ids)
            {
                CellStyle numberStyle = wb.createCellStyle();
                numberStyle.setDataFormat(dt.getFormat("0"));
                numberStyle.setBorderRight(CellStyle.BORDER_THIN);
                numberStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
                numberStyle.setBorderBottom(CellStyle.BORDER_THIN);
                numberStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                numberStyle.setBorderLeft(CellStyle.BORDER_THIN);
                numberStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                numberStyle.setBorderTop(CellStyle.BORDER_THIN);
                numberStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                
                row=ws.createRow(rowId++);
                Object[] values = data.get(key);

                int cellId =1;
                
                cell = row.createCell(0);
                cell.setCellValue(rowfila);
                cell.setCellStyle(numberStyle);
                
                cell = row.createCell(1);
                cell.setCellValue(values[0].toString());
                cell.setCellStyle(numberStyle);
                
                cell = row.createCell(2);
                cell.setCellValue(values[1].toString());
                cell.setCellStyle(numberStyle);
                
                cell = row.createCell(3);
                cell.setCellValue(values[2].toString());
                cell.setCellStyle(numberStyle);
                
                cell = row.createCell(4);
                cell.setCellValue(values[3].toString());
                cell.setCellStyle(numberStyle);
                
                cell = row.createCell(5);
                cell.setCellValue(Integer.parseInt(values[4].toString()));
                cell.setCellStyle(numberStyle);
                
                
                cell = row.createCell(6);
                cell.setCellValue(Integer.parseInt(values[5].toString()));
                cell.setCellStyle(numberStyle);
                

                rowfila++;
            }
        
            FileOutputStream fos= new FileOutputStream(new File("c:/excel/ExcelSolicitudRepuesto_" + String.valueOf(datetimemlli) + ".xlsx"));
            wb.write(fos);
            fos.close();
            JOptionPane.showMessageDialog(null, "Datos exportados en c:/excel/ExcelSolicitudRepuesto_" + String.valueOf(datetimemlli) + ".xlsx" );
            
        } 
        catch(FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error en " + ex);
        } 
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error en " + ex);
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getCause());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel pnlgrid;
    // End of variables declaration//GEN-END:variables
}
