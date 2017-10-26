/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Render extends DefaultTableCellRenderer{
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasfocus, int row, int column) {
        
        if(value instanceof JButton){
            JButton btn = (JButton)value;
            return btn;
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasfocus, row, column);
            
    }
   
}
