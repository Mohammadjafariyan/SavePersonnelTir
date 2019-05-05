package main.ui;

import com.intellij.uiDesigner.core.Spacer;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.sun.org.apache.xpath.internal.operations.Bool;
import main.java.models.SettingEntity;
import main.java.models.TirEntity;
import service.Callback;
import service.MyUtility;
import service.SettingService;
import service.SharedDataHolderSingeton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Setting {
    private GlobalUi globalUi;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JButton تغییرمسیرButton;
    private JButton ثبتButton;
    private JTextField textField1;
    private JMenuBar menubar;
    private SettingService settingService = new SettingService();
    private JFrame frame;

    private JFileChooser chooser = new JFileChooser();
    private Callback currentCallback;

    public Setting() {
        تغییرمسیرButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.setDialogTitle("انتخاب مسیر خروجی فایل های اکسل");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getCurrentDirectory(): "
                            + chooser.getSelectedFile().getPath());

                    settingEntity.setExcelOutputPath(chooser.getSelectedFile().getPath());
                    textField1.setText(settingEntity.getExcelOutputPath());


                    System.out.println("getSelectedFile() : "
                            + chooser.getSelectedFile());
                } else {
                    System.out.println("No Selection ");
                }
            }
        });
        ثبتButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setData(settingEntity);
                    settingService.save(settingEntity);
                    frame.setVisible(false);

                    globalUi.repaintAll();

                    if (currentCallback != null)
                        currentCallback.onResultBack(true);

                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(
                            null, e2.getMessage() + "خطا در آپدیت ");
                }
            }
        });
    }

    public JFrame init() {
        initCombos();


        frame = new JFrame("تنظیمات");
        panel1.setSize(400, 250);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(400, 250);
        frame.pack();

        return frame;
    }

    private SettingEntity settingEntity;

    public void initCombos() {

        settingEntity = null;
        try {
            settingEntity = settingService.initIfNotExists();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, e.getMessage());

        }

        comboBox1.setModel(MyUtility.GetYearList());
        comboBox1.setSelectedItem(settingEntity.getSyear());

        comboBox2.setModel(MyUtility.GetMonthList());
        comboBox2.setSelectedIndex(settingEntity.getSmonth() - 1);

        initDaysOfMonth();

        comboBox3.setSelectedItem(settingEntity.getSday());

        textField1.setText(settingEntity.getExcelOutputPath());

    }

    private void initDaysOfMonth() {
        comboBox3.setModel(new DefaultComboBoxModel(MyUtility.GetMonthDays(
                MyUtility.GetCurrentYear(), MyUtility.GetCurrentMonthNumber())));


    }


    public GlobalUi getGlobalUi() {
        return globalUi;
    }

    public void setGlobalUi(GlobalUi globalUi) {
        this.menubar = globalUi.getMenubar();
        this.globalUi = globalUi;
    }

    public void setData(SettingEntity data) {
        String name = "";
        try {
            name = "سال";
            data.setSyear(Integer.parseInt(comboBox1.getSelectedItem().toString()));
            name = "ماه";
            data.setSmonth(comboBox2.getSelectedIndex() + 1);
            name = "روز";
            data.setSday(Integer.parseInt(comboBox3.getSelectedItem().toString()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null, " مقدار اشتباه است " + name);

        }

    }

    public void setCurrentCallback(Callback currentCallback) {
        this.currentCallback = currentCallback;
    }

    public Callback getCurrentCallback() {
        return currentCallback;
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
        panel1.setLayout(new FormLayout("fill:d:noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow", "center:max(d;4px):noGrow,top:4dlu:noGrow,center:d:noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow"));
        comboBox1 = new JComboBox();
        Font comboBox1Font = this.$$$getFont$$$(null, -1, 26, comboBox1.getFont());
        if (comboBox1Font != null) comboBox1.setFont(comboBox1Font);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        comboBox1.setModel(defaultComboBoxModel1);
        CellConstraints cc = new CellConstraints();
        panel1.add(comboBox1, cc.xy(5, 3));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 26, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("ماه");
        panel1.add(label1, cc.xy(7, 3));
        comboBox2 = new JComboBox();
        Font comboBox2Font = this.$$$getFont$$$(null, -1, 26, comboBox2.getFont());
        if (comboBox2Font != null) comboBox2.setFont(comboBox2Font);
        panel1.add(comboBox2, cc.xy(9, 3));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 26, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("روز");
        panel1.add(label2, cc.xy(11, 3));
        comboBox3 = new JComboBox();
        Font comboBox3Font = this.$$$getFont$$$(null, -1, 26, comboBox3.getFont());
        if (comboBox3Font != null) comboBox3.setFont(comboBox3Font);
        panel1.add(comboBox3, cc.xy(13, 3));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 26, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("مسیر خروجی فایل اسکل");
        panel1.add(label3, cc.xy(1, 5));
        تغییرمسیرButton = new JButton();
        تغییرمسیرButton.setText("تغییر مسیر");
        panel1.add(تغییرمسیرButton, cc.xy(13, 5));
        textField1 = new JTextField();
        textField1.setEditable(false);
        textField1.setEnabled(false);
        Font textField1Font = this.$$$getFont$$$(null, -1, 26, textField1.getFont());
        if (textField1Font != null) textField1.setFont(textField1Font);
        panel1.add(textField1, cc.xyw(5, 5, 7, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, -1, 26, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("سال");
        panel1.add(label4, cc.xy(3, 3));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$(null, -1, 26, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("تاریخ ");
        panel1.add(label5, cc.xy(1, 3));
        ثبتButton = new JButton();
        Font ثبتButtonFont = this.$$$getFont$$$(null, -1, 26, ثبتButton.getFont());
        if (ثبتButtonFont != null) ثبتButton.setFont(ثبتButtonFont);
        ثبتButton.setText("ثبت");
        panel1.add(ثبتButton, cc.xy(5, 7));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, cc.xy(5, 9, CellConstraints.FILL, CellConstraints.DEFAULT));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, cc.xy(5, 11, CellConstraints.FILL, CellConstraints.DEFAULT));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, cc.xy(5, 13, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JToolBar toolBar1 = new JToolBar();
        panel1.add(toolBar1, cc.xy(1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
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
