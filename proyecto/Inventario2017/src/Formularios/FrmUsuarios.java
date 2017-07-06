/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Entidades.Usuario;
import ModeloTabla.CmbRoles;
import Logico.UsuarioLog;
import ModeloTabla.ModeloTablaUsuario;
import static Formularios.frmLogin.res;
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

public class FrmUsuarios extends javax.swing.JInternalFrame {

    UsuarioLog usuarios;
    Usuario usr;
    ModeloTablaUsuario mtu;
    
    public FrmUsuarios() {
        initComponents();
        usuarios = new UsuarioLog();
        ListarTabla();
        CargarCombo();
        Limpiar();
        lblLlaveusuario.setVisible(false);
    }

    private void llenarexcel()
    {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet ws = wb.createSheet();
            
            //load data to treemap
            TreeMap<String,Object[]> data = new TreeMap<>();
            int pos = 0;
            data.put("0",new Object[]{mtu.getColumnName(0), mtu.getColumnName(1),mtu.getColumnName(2), mtu.getColumnName(3)});
            
            for (Usuario usr : mtu.usuarios) {
                data.put(String.valueOf(pos + 1),new Object[]{mtu.getValueAt(pos,0), mtu.getValueAt(pos,1),mtu.getValueAt(pos,2), mtu.getValueAt(pos,3)});
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
                FileOutputStream fos= new FileOutputStream(new File("c:/excel/exwcel.xlsx"));
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
        String Filtro = txt_filtrobusqueda.getText();
        List<Usuario> listas = usuarios.listado(Filtro);
        mtu = new ModeloTablaUsuario(listas);
        jTable1.setModel(mtu);
        jTable1.getRowSorter();
    }

    public void Limpiar() {
        txt_username.setText("");
        txt_password.setText("");
        txt_Confirmacion.setText("");
        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_filtrobusqueda.setText("");
        cmb_Perfil.setSelectedIndex(0);
        cmb_Perfil.setEnabled(false);
        txt_username.setEnabled(false);
        txt_password.setEnabled(false);
        txt_Confirmacion.setEnabled(false);
        txt_nombre.setEnabled(false);
        txt_apellido.setEnabled(false);
        btn_Nuevo.setEnabled(true);
        btn_grabar.setEnabled(false);
        btn_Eliminar.setEnabled(false);
        btn_Cancelar.setEnabled(false);
        btn_salir.setEnabled(true);
        btn_exportar.setEnabled(true);
        PnlIngreso.setEnabled(false);
        lblLlaveusuario.setText("0");
    }
    
    public void CargarCombo()
    {
        try
        {
            DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
            ResultSet objResultSet;
            objResultSet = Conecciones.Coneccion.consulta(" select * from db_owner.Roles ");
            modeloCombo.addElement(new CmbRoles("Seleccione Rol", "0"));
            cmb_Perfil.setModel(modeloCombo);//con esto lo agregamos al objeto al jcombobox
            while (objResultSet.next())
            {
                modeloCombo.addElement(new CmbRoles(objResultSet.getString("Nombre_rol") , objResultSet.getString("id_rol")));
                cmb_Perfil.setModel(modeloCombo);
            }
         } 
        catch (Exception ex) 
        {
            System.out.println(ex.getCause());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnlIngreso = new javax.swing.JPanel();
        lblApellido = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        txt_apellido = new javax.swing.JTextField();
        txt_password = new javax.swing.JPasswordField();
        lblClave = new javax.swing.JLabel();
        lblperfil = new javax.swing.JLabel();
        txt_Confirmacion = new javax.swing.JPasswordField();
        cmb_Perfil = new javax.swing.JComboBox<>();
        lblUsername = new javax.swing.JLabel();
        lblConfirmacion = new javax.swing.JLabel();
        lblnombre = new javax.swing.JLabel();
        lblLlaveusuario = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btn_buscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblTituloFiltro = new javax.swing.JLabel();
        txt_filtrobusqueda = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btn_salir = new javax.swing.JButton();
        btn_Nuevo = new javax.swing.JButton();
        btn_Eliminar = new javax.swing.JButton();
        btn_grabar = new javax.swing.JButton();
        btn_Cancelar = new javax.swing.JButton();
        btn_exportar = new javax.swing.JButton();

        setForeground(java.awt.Color.white);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Usuarios");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        PnlIngreso.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        PnlIngreso.setPreferredSize(new java.awt.Dimension(522, 260));

        lblApellido.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblApellido.setText("Apellido");

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Usuarios");

        lblClave.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblClave.setText("Clave");

        lblperfil.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblperfil.setText("Rol de Usuario");

        cmb_Perfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblUsername.setText("Nombre Usuario");

        lblConfirmacion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblConfirmacion.setText("Confirmación");

        lblnombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblnombre.setText("Nombre");

        javax.swing.GroupLayout PnlIngresoLayout = new javax.swing.GroupLayout(PnlIngreso);
        PnlIngreso.setLayout(PnlIngresoLayout);
        PnlIngresoLayout.setHorizontalGroup(
            PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlIngresoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlIngresoLayout.createSequentialGroup()
                        .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(lblperfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblnombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_apellido)
                            .addComponent(txt_nombre)
                            .addGroup(PnlIngresoLayout.createSequentialGroup()
                                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_username)
                                    .addComponent(cmb_Perfil, 0, 361, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(PnlIngresoLayout.createSequentialGroup()
                        .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PnlIngresoLayout.createSequentialGroup()
                                .addComponent(lblTitulo)
                                .addGap(59, 59, 59)
                                .addComponent(lblLlaveusuario))
                            .addGroup(PnlIngresoLayout.createSequentialGroup()
                                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblClave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblConfirmacion, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_Confirmacion, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PnlIngresoLayout.setVerticalGroup(
            PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlIngresoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(lblLlaveusuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblperfil)
                    .addComponent(cmb_Perfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsername)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblnombre)
                    .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblApellido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClave)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblConfirmacion)
                    .addComponent(txt_Confirmacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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
        lblTituloFiltro.setText("Filtro de Busqueda");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTituloFiltro)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_filtrobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloFiltro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_filtrobusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PnlIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(107, 107, 107))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PnlIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        Limpiar();
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
        usr = ((ModeloTablaUsuario) jTable1.getModel()).DameUsuario(jTable1.getSelectedRow());
        
        lblLlaveusuario.setText(String.valueOf(usr.getIdusuario()));
        txt_username.setText(usr.getUserName());
        txt_nombre.setText(usr.getNombre());
        txt_apellido.setText(usr.getApellido());
        cmb_Perfil.setSelectedIndex(usr.getid_rol());
        
        txt_username.setEnabled(true);
        txt_nombre.setEnabled(true);
        txt_apellido.setEnabled(true);
        cmb_Perfil.setEnabled(true);
        txt_password.setEnabled(true);
        txt_Confirmacion.setEnabled(true);
        PnlIngreso.setEnabled(true);
        btn_Nuevo.setEnabled(false);
        btn_Eliminar.setEnabled(true);
        btn_grabar.setEnabled(true);
        btn_Cancelar.setEnabled(true);
        btn_salir.setEnabled(false);
        btn_exportar.setEnabled(false);
    }//GEN-LAST:event_jTable1MouseClicked

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        ListarTabla();
    }//GEN-LAST:event_btn_buscarActionPerformed

    private boolean validar_formulario()
    {
        boolean validar = false;
        
        if(txt_username.getText().trim()=="" 
                || new String(txt_password.getPassword()).trim()==""
                || new String(txt_Confirmacion.getPassword()).trim()==""
                || txt_apellido.getText().trim()==""
                || txt_nombre.getText().trim()==""
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
    
    private void btn_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoActionPerformed
        txt_username.setText("");
        txt_password.setText("");
        txt_Confirmacion.setText("");
        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_filtrobusqueda.setText("");
        cmb_Perfil.setSelectedIndex(0);
        cmb_Perfil.setEnabled(true);
        txt_username.setEnabled(true);
        txt_password.setEnabled(true);
        txt_Confirmacion.setEnabled(true);
        txt_nombre.setEnabled(true);
        txt_apellido.setEnabled(true);
        btn_grabar.setEnabled(true);
        btn_Cancelar.setEnabled(true);
        PnlIngreso.setEnabled(true);
        lblLlaveusuario.setText("0");
        
        btn_Eliminar.setEnabled(false);
        btn_Nuevo.setEnabled(false);
        
    }//GEN-LAST:event_btn_NuevoActionPerformed

    private void btn_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarActionPerformed
        boolean resp = usuarios.DeleteUsuario(usr);
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
                Usuario usr;
                
                String password = new String(txt_password.getPassword());
                Object item = cmb_Perfil.getSelectedItem();
                CmbRoles items = (CmbRoles)cmb_Perfil.getSelectedItem();
                int id_rol = Integer.parseInt(items.getID());
                int id_usuario = Integer.parseInt(lblLlaveusuario.getText());
                        
                if(id_usuario ==0){
                    usr = new Usuario(id_usuario, txt_username.getText(), password, txt_nombre.getText(), txt_apellido.getText(),  id_rol, "rolname");
                    resp = usuarios.AgregarUsuario(usr);
                } 
                else 
                {
                    usr = new Usuario(id_usuario, txt_username.getText(), password, txt_nombre.getText(), txt_apellido.getText(),  id_rol, "rolname");
                    resp = usuarios.UpdateUsuario(usr);
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

    private void btn_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarActionPerformed
        llenarexcel();
    }//GEN-LAST:event_btn_exportarActionPerformed

    public void CargarTable()
    {
        
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnlIngreso;
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JButton btn_Eliminar;
    private javax.swing.JButton btn_Nuevo;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_exportar;
    private javax.swing.JButton btn_grabar;
    private javax.swing.JButton btn_salir;
    private javax.swing.JComboBox<String> cmb_Perfil;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblClave;
    private javax.swing.JLabel lblConfirmacion;
    private javax.swing.JLabel lblLlaveusuario;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloFiltro;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblnombre;
    private javax.swing.JLabel lblperfil;
    private javax.swing.JPasswordField txt_Confirmacion;
    private javax.swing.JTextField txt_apellido;
    private javax.swing.JTextField txt_filtrobusqueda;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
