/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import java.beans.PropertyVetoException;

/**
 *
 * @author matygonzo
 */
public class frmPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    public frmPrincipal() {
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

        dpnEscritorio = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuBodega = new javax.swing.JMenu();
        mnuproductos = new javax.swing.JMenuItem();
        mnuDespacho = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        mnusolicitadprog = new javax.swing.JMenu();
        menuprogramada = new javax.swing.JMenuItem();
        mnurespuesto = new javax.swing.JMenuItem();
        mnucompras = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        mnuinformes = new javax.swing.JMenu();
        mnuConfiguracion = new javax.swing.JMenu();
        Temporada = new javax.swing.JMenuItem();
        mnuSistema = new javax.swing.JMenuItem();
        mnuZonal = new javax.swing.JMenuItem();
        mnuUnidad = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuUsuarios = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuClave = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dpnEscritorio.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout dpnEscritorioLayout = new javax.swing.GroupLayout(dpnEscritorio);
        dpnEscritorio.setLayout(dpnEscritorioLayout);
        dpnEscritorioLayout.setHorizontalGroup(
            dpnEscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 827, Short.MAX_VALUE)
        );
        dpnEscritorioLayout.setVerticalGroup(
            dpnEscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 407, Short.MAX_VALUE)
        );

        mnuBodega.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/file.png"))); // NOI18N
        mnuBodega.setText("Bodega");
        mnuBodega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBodegaActionPerformed(evt);
            }
        });

        mnuproductos.setText("Productos");
        mnuproductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuproductosActionPerformed(evt);
            }
        });
        mnuBodega.add(mnuproductos);

        jMenuBar1.add(mnuBodega);

        mnuDespacho.setText("Despacho");

        jMenuItem1.setText("Despacho de Productos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mnuDespacho.add(jMenuItem1);

        jMenuBar1.add(mnuDespacho);

        mnusolicitadprog.setText("Solicitudes");

        menuprogramada.setText("Programación Anual");
        menuprogramada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuprogramadaActionPerformed(evt);
            }
        });
        mnusolicitadprog.add(menuprogramada);

        mnurespuesto.setText("Solicitud de Repuesto");
        mnurespuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnurespuestoActionPerformed(evt);
            }
        });
        mnusolicitadprog.add(mnurespuesto);

        jMenuBar1.add(mnusolicitadprog);

        mnucompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/comprar.png"))); // NOI18N
        mnucompras.setText("Compras");

        jMenuItem2.setText("Generar Compras");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        mnucompras.add(jMenuItem2);

        jMenuBar1.add(mnucompras);

        mnuinformes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/informes.png"))); // NOI18N
        mnuinformes.setText("Informes");
        jMenuBar1.add(mnuinformes);

        mnuConfiguracion.setText("Configuración");

        Temporada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/producto.png"))); // NOI18N
        Temporada.setText("Temporada");
        Temporada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TemporadaActionPerformed(evt);
            }
        });
        mnuConfiguracion.add(Temporada);

        mnuSistema.setText("Sistema");
        mnuSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSistemaActionPerformed(evt);
            }
        });
        mnuConfiguracion.add(mnuSistema);

        mnuZonal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/zonal.png"))); // NOI18N
        mnuZonal.setText("Zonal");
        mnuZonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuZonalActionPerformed(evt);
            }
        });
        mnuConfiguracion.add(mnuZonal);

        mnuUnidad.setText("Unidad");
        mnuUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUnidadActionPerformed(evt);
            }
        });
        mnuConfiguracion.add(mnuUnidad);
        mnuConfiguracion.add(jSeparator1);

        mnuUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/usuario.png"))); // NOI18N
        mnuUsuarios.setText("Usuarios");
        mnuUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUsuariosActionPerformed(evt);
            }
        });
        mnuConfiguracion.add(mnuUsuarios);
        mnuConfiguracion.add(jSeparator2);

        mnuClave.setText("Usuarios");
        mnuConfiguracion.add(mnuClave);
        mnuConfiguracion.add(jSeparator3);

        mnuSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cerrar.png"))); // NOI18N
        mnuSalir.setText("Salir");
        mnuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalirActionPerformed(evt);
            }
        });
        mnuConfiguracion.add(mnuSalir);

        jMenuBar1.add(mnuConfiguracion);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dpnEscritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dpnEscritorio)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void mnuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnuSalirActionPerformed

    private void mnuUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUsuariosActionPerformed
        FrmUsuarios misUsuarios = new FrmUsuarios();
        dpnEscritorio.add(misUsuarios);
        misUsuarios.show();
    }//GEN-LAST:event_mnuUsuariosActionPerformed

    private void mnuUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUnidadActionPerformed
        FrmUnidad misUnidades = new FrmUnidad();
        dpnEscritorio.add(misUnidades);
        
        try {
            misUnidades.setMaximum(true);
        } catch (PropertyVetoException e) {
            // Vetoed by internalFrame
            // ... possibly add some handling for this case
        }
        
        misUnidades.show();
        
        
    }//GEN-LAST:event_mnuUnidadActionPerformed

    private void mnuSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSistemaActionPerformed
        FrmSistema misSistemas = new FrmSistema();
        dpnEscritorio.add(misSistemas);
        
        try {
            misSistemas.setMaximum(true);
        } catch (PropertyVetoException e) {
            // Vetoed by internalFrame
            // ... possibly add some handling for this case
        }
        
        misSistemas.show();
    }//GEN-LAST:event_mnuSistemaActionPerformed

    private void TemporadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TemporadaActionPerformed
        FrmTemporada misTemporadas = new FrmTemporada();
        dpnEscritorio.add(misTemporadas);
        misTemporadas.show();
    }//GEN-LAST:event_TemporadaActionPerformed

    private void mnuBodegaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuBodegaActionPerformed
        FrmUsuarios misUsuarios = new FrmUsuarios();
        dpnEscritorio.add(misUsuarios);
        misUsuarios.show();
    }//GEN-LAST:event_mnuBodegaActionPerformed

    private void mnuproductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuproductosActionPerformed
        FrmProducto misProductos = new FrmProducto();
        dpnEscritorio.add(misProductos);
        misProductos.show();
    }//GEN-LAST:event_mnuproductosActionPerformed

    private void mnuZonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuZonalActionPerformed
        FrmZonal misZonal = new FrmZonal();
        dpnEscritorio.add(misZonal);
        misZonal.show();
    }//GEN-LAST:event_mnuZonalActionPerformed

    private void mnurespuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnurespuestoActionPerformed
        // TODO add your handling code here:
        FrmRepuesto misRepuesto = new FrmRepuesto();
        dpnEscritorio.add(misRepuesto);
        
        try {
            misRepuesto.setMaximum(true);
        } catch (PropertyVetoException e) {
            // Vetoed by internalFrame
            // ... possibly add some handling for this case
        }
        
        misRepuesto.show();
    }//GEN-LAST:event_mnurespuestoActionPerformed

    private void menuprogramadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuprogramadaActionPerformed
        // TODO add your handling code here:
        FrmProgramada misprogramas = new FrmProgramada();
        dpnEscritorio.add(misprogramas);
        misprogramas.show();
    }//GEN-LAST:event_menuprogramadaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Temporada;
    private javax.swing.JDesktopPane dpnEscritorio;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuItem menuprogramada;
    private javax.swing.JMenu mnuBodega;
    private javax.swing.JMenuItem mnuClave;
    private javax.swing.JMenu mnuConfiguracion;
    private javax.swing.JMenu mnuDespacho;
    private javax.swing.JMenuItem mnuSalir;
    private javax.swing.JMenuItem mnuSistema;
    private javax.swing.JMenuItem mnuUnidad;
    private javax.swing.JMenuItem mnuUsuarios;
    private javax.swing.JMenuItem mnuZonal;
    private javax.swing.JMenu mnucompras;
    private javax.swing.JMenu mnuinformes;
    private javax.swing.JMenuItem mnuproductos;
    private javax.swing.JMenuItem mnurespuesto;
    private javax.swing.JMenu mnusolicitadprog;
    // End of variables declaration//GEN-END:variables
}
