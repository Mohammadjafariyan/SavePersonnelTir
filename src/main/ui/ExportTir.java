package main.ui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import main.java.models.ExportTirTableModel;
import main.java.models.SettingEntity;
import service.*;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class ExportTir {
    private GlobalUi globalUi;
    private JButton پرینتButton;
    public JPanel panel1;
    private JButton excelButton;
    private JTable table1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JMenuBar menubar;
    private SaveTirService saveTirService = new SaveTirService();


    public void initComboeListeners() {
        comboBox3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    requeryTable(false);
                }

            }
        });
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    requeryTable(false);
                }
            }
        });

        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    requeryTable(false);
                }
            }
        });
    }

    public ExportTir() {
        پرینتButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requeryTable(true);
            }
        });

        پرینتButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    PrintRequestAttributeSet attribSet = new HashPrintRequestAttributeSet();
                    attribSet.add(new MediaPrintableArea(1, 1, 50, 1000, MediaPrintableArea.MM));
                    attribSet.add(MediaSizeName.ISO_A4);


                    String str = MyUtility.convertToEnglishDigits(comboBox3.getEditor().getItem() + "");

                    //     table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    //   table1.getColumnModel().getColumn(0).setPreferredWidth(120);
                    //   table1.getColumnModel().getColumn(1).setPreferredWidth(100);

                    table1.print(JTable.PrintMode.FIT_WIDTH,
                            new MessageFormat(str)
                            , new MessageFormat(""), true, attribSet, false);

                } catch (PrinterException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "عدم امکان پرینت");
                    JOptionPane.showMessageDialog(null, e1.getMessage());

                }
            }
        });
        excelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    SettingService settingService = new SettingService();
                    SettingEntity settingEntity = settingService.initIfNotExists();

                    new ExcelCustomerReport(settingEntity.getExcelOutputPath())
                            .exportTable(table1, comboBox3.getSelectedItem().toString() + ".xls");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(frame, e1.getMessage());
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(frame, e1.getMessage());

                }
            }
        });
    }

    public void requeryTable(boolean isRequeryPersonnel) {

        if (isRequeryPersonnel)
            requeryPersonnelCodes();


        ExportTirTableModel model = new ExportTirTableModel();

        int month = comboBox1.getSelectedIndex() + 1;
        int year = Integer.parseInt(comboBox2.getSelectedItem().toString());


        Object item = comboBox3.getSelectedItem();
        if (item != null && item.toString() != "") {
            long userCode = 0;

            try {
                userCode = Long.parseLong(item.toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "لطفا عدد وارد نمایید");
                return;
            }


            try {
               /* model.rowData = saveTirService.
                        GetAllDataTableWithCheckbox(month, year, userCode);
*/
                model.rowData = saveTirService.
                        GetPerMonthByPersonnel(userCode, year, month, true);
            } catch (MyServiceException e) {
                e.printStackTrace();
                JOptionPane
                        .showMessageDialog(
                                null, e.getMessage());

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane
                        .showMessageDialog(
                                null, e.getMessage());
            }


            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table1.setDefaultRenderer(String.class, centerRenderer);

            ((DefaultTableCellRenderer) table1.getTableHeader().getDefaultRenderer())
                    .setHorizontalAlignment(JLabel.CENTER);

            table1.setModel(model);
        }

    }

    private void requeryPersonnelCodes() {


        List<Long> list = saveTirService.GetAllPersonnelCodes();

        long lastCode = saveTirService.GetLastInsertedCode();


        comboBox3.setModel(new DefaultComboBoxModel(list.toArray()));
        comboBox3.setSelectedItem(lastCode);
    }

    private JFrame frame;

    public JFrame init() {

        comboBox1.setModel(MyUtility.GetMonthList());
        comboBox1.setSelectedItem(MyUtility.GetCurrentMonth());

        comboBox2.setModel(MyUtility.GetYearList());
        comboBox2.setSelectedItem(MyUtility.GetCurrentYear());
        requeryTable(true);

        frame = new JFrame("دریافت خروجی");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TableColumn column = table.getColumnModel().getColumn(3);
        // column.setCellRenderer(renderer);
        // column.setCellEditor(editor);

        frame.setSize(400, 150);


        frame.setContentPane(panel1);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();


        initComboeListeners();
        return frame;
    }


    public GlobalUi getGlobalUi() {
        return globalUi;
    }

    public void setGlobalUi(GlobalUi globalUi) {

        this.menubar = globalUi.getMenubar();
        this.globalUi = globalUi;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new FormLayout("fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:d:grow", "center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:d:grow"));
        Font panel1Font = this.$$$getFont$$$(null, -1, -1, panel1.getFont());
        if (panel1Font != null) panel1.setFont(panel1Font);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 26, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("سال");
        CellConstraints cc = new CellConstraints();
        panel1.add(label1, cc.xy(1, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));
        پرینتButton = new JButton();
        پرینتButton.setEnabled(true);
        Font پرینتButtonFont = this.$$$getFont$$$(null, -1, -1, پرینتButton.getFont());
        if (پرینتButtonFont != null) پرینتButton.setFont(پرینتButtonFont);
        پرینتButton.setText("پرینت");
        panel1.add(پرینتButton, cc.xy(3, 3));
        excelButton = new JButton();
        Font excelButtonFont = this.$$$getFont$$$(null, -1, -1, excelButton.getFont());
        if (excelButtonFont != null) excelButton.setFont(excelButtonFont);
        excelButton.setText("Excel");
        panel1.add(excelButton, cc.xy(1, 3));
        final JScrollPane scrollPane1 = new JScrollPane();
        Font scrollPane1Font = this.$$$getFont$$$(null, -1, 26, scrollPane1.getFont());
        if (scrollPane1Font != null) scrollPane1.setFont(scrollPane1Font);
        panel1.add(scrollPane1, cc.xyw(1, 5, 33, CellConstraints.FILL, CellConstraints.FILL));
        table1 = new JTable();
        table1.setAutoCreateRowSorter(false);
        table1.setAutoResizeMode(0);
        table1.setFillsViewportHeight(true);
        table1.setFocusCycleRoot(false);
        table1.setFocusTraversalPolicyProvider(false);
        table1.setIntercellSpacing(new Dimension(1, 1));
        scrollPane1.setViewportView(table1);
        comboBox1 = new JComboBox();
        comboBox1.setEditable(false);
        Font comboBox1Font = this.$$$getFont$$$(null, -1, 26, comboBox1.getFont());
        if (comboBox1Font != null) comboBox1.setFont(comboBox1Font);
        panel1.add(comboBox1, cc.xyw(8, 1, 16));
        comboBox2 = new JComboBox();
        Font comboBox2Font = this.$$$getFont$$$(null, -1, 26, comboBox2.getFont());
        if (comboBox2Font != null) comboBox2.setFont(comboBox2Font);
        panel1.add(comboBox2, cc.xy(3, 1));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 26, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("ماه");
        panel1.add(label2, cc.xy(7, 1));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 26, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("کد پرسنلی");
        panel1.add(label3, cc.xy(23, 3));
        comboBox3 = new JComboBox();
        comboBox3.setEditable(true);
        Font comboBox3Font = this.$$$getFont$$$(null, -1, 26, comboBox3.getFont());
        if (comboBox3Font != null) comboBox3.setFont(comboBox3Font);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        comboBox3.setModel(defaultComboBoxModel1);
        panel1.add(comboBox3, cc.xyw(24, 3, 10));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
