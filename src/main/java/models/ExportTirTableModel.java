package main.java.models;

import javax.swing.table.AbstractTableModel;

public class ExportTirTableModel extends AbstractTableModel {


    public Object rowData[][] = {{"1", Boolean.TRUE}, {"2", Boolean.TRUE}, {"3", Boolean.FALSE},
            {"4", Boolean.TRUE}, {"5", Boolean.FALSE}};

    String[] columnNames = { "ملاحضات" , "تیر", "تاریخ"};


    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public int getRowCount() {
        return rowData.length;
    }

    public Object getValueAt(int row, int column) {
        return rowData[row][column];
    }

    public Class getColumnClass(int column) {
        return (getValueAt(0, column).getClass());
    }

    public void setValueAt(Object value, int row, int column) {
        rowData[row][column] = value;
    }

    public boolean isCellEditable(int row, int column) {
        return (column != 0);
    }
}
