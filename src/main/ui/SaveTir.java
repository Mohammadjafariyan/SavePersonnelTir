package main.ui;

import com.intellij.uiDesigner.core.Spacer;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import main.java.models.SettingEntity;
import main.java.models.TirEntity;
import service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;

public class SaveTir {

    private TirEntity tir = new TirEntity();
    public JPanel panel1;
    private JTextField textField2;
    private JButton saveButton;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField3;
    private JButton حذفازجدولButton;
    private JTextField currentDateTextField;
    private JMenuBar menubar;
    private String selectRowId;

    private SettingService settingService = new SettingService();

    private GlobalUi globalUi;


    private SaveTirService saveTirService = new SaveTirService();


    public void resetDateButton() {
        int currentDay = 0;
        int currentYear = 0;
        int currentMonth = 0;
        try {
            currentDay = settingService.initIfNotExists().getSday();
            currentMonth = settingService.initIfNotExists().getSmonth();
            currentYear = settingService.initIfNotExists().getSyear();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        String dateStr = currentDay + "/" + MyUtility.months[currentMonth - 1] + "/" + currentYear;

        currentDateTextField.setText(dateStr);
        currentDateTextField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        currentDateTextField.setHorizontalAlignment(JTextField.LEFT);

    }


    public void requeryTable() {

        String dateStr = null;
        int currentDay = 0;
        int currentYear = 0;
        int currentMonth = 0;
        try {
            currentDay = settingService.initIfNotExists().getSday();
            currentMonth = settingService.initIfNotExists().getSmonth();
            currentYear = settingService.initIfNotExists().getSyear();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        dateStr = currentDay + "/" + MyUtility.months[currentMonth - 1] + "/" + currentYear;

        currentDateTextField.setText(dateStr);
        currentDateTextField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        currentDateTextField.setHorizontalAlignment(JTextField.LEFT);

        //   currentDateTextField.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

        String[] columnNames = {"کد پرسنل", "تیر", "توضیحات", "تاریخ", "کد دیتا"};

        //
        String[][] data = new String[0][];
        long code = saveTirService.GetLastInsertedCode();
        if (code != -1) {
            try {
                data = saveTirService.GetPerMonthByPersonnel(code, currentYear, currentMonth, false);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            try {
                data = saveTirService.GetAllDataTable(5, false);
            } catch (MyServiceException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }


        TableModel dataModel = new DefaultTableModel(data, columnNames);
        table1.setModel(dataModel);

        table1.setDefaultEditor(Object.class, null);

    }


    public void SaveAction() {
        if (!getData(tir))
            return;

        TirEntity exits = saveTirService.FindByTir(tir);

        if (exits != null) {

            int dialog = JOptionPane.showConfirmDialog
                    (SharedDataHolderSingeton.getGlobalUi().getSaveTirJFrame()
                            , "برای امروز و این کد پرسنل قبلا تیر ثبت کرده اید ، آیا مایل به ثبت دوباره می باشید ؟ در این صورت تیر قبلی پاک خواهد شد"
                            , "توجه", JOptionPane.YES_NO_OPTION);

            if (dialog == JOptionPane.YES_OPTION) {

                try {
                    saveTirService.DeleteById(exits.getId() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                            null, e.getMessage() + "خطا در حذف ");
                    return;
                }
            }

        }


        saveTirService.Save(tir);
        this.requeryTable();

        tir = new TirEntity();
        setData(tir);

        textField1.requestFocus();
    }

    public void Init() {
        this.requeryTable();


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveAction();
            }


        });


        panel1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    SaveAction();
                }
            }
        });
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    SaveAction();
                }
            }
        });
        textField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    SaveAction();
                }
            }
        });
        textField3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    SaveAction();
                }
            }
        });

        // table1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        table1.addMouseListener(new MouseAdapter() {
        });
        table1.addComponentListener(new ComponentAdapter() {
        });
        table1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                int row = table.getSelectedRow();

                selectRowId = table.getModel().getValueAt(row, table.getColumnCount() - 1).toString();
            }
        });
        حذفازجدولButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectRowId != null) {
                    int dialogResult = JOptionPane.showConfirmDialog
                            (null, "آیا از حذف این مورد اطمینان دارید ؟", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        // Saving code here
                        try {
                            saveTirService.DeleteById(selectRowId);
                            requeryTable();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(
                                    null, e1.getMessage() + "خطا در حذف ");
                        }
                        selectRowId = null;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "هیچ رکوردی انتخاب نشده است");
                }


            }
        });


    }


    public SaveTir() {
        Init();

        currentDateTextField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SharedDataHolderSingeton.getGlobalUi().getSettingFrameListener().actionPerformed(null
                        , new Callback() {

                            @Override
                            public void onResultBack(Object o) {
                                resetDateButton();
                                requeryTable();
                            }
                        });

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
    }

    public void setData(TirEntity data) {
        textField1.setText(data.getCode() == 0 ? "" : data.getCode() + "");
        textField2.setText(data.getCount() == 0 ? "" : data.getCount() + "");
        textField3.setText(data.getDescription());

    }

    public boolean getData(TirEntity data) {
        try {
            long v1 = Long.parseLong(textField1.getText());
            data.setCode(v1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "لطفا عدد وارد نمایید");
            //  textField1.setText(data.getCode()==0? "" :data.getCode() + "");
            textField1.requestFocus();
            textField1.selectAll();
            return false;
        }

        try {
            long v2 = Long.parseLong(textField2.getText());
            data.setCount(v2);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "لطفا عدد وارد نمایید");
            //textField2.setText(data.getCount()==0 ? "" :data.getCount() +"");
            textField2.requestFocus();
            textField2.selectAll();
            return false;
        }

        data.setDescription(textField3.getText());


        try {
            SettingEntity settingEntity = settingService.initIfNotExists();

            data.setDate(MyUtility.ConvertToGaregoiran(settingEntity.getSyear(), settingEntity.getSmonth(), settingEntity.getSday()));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }


        return true;

    }

    public boolean isModified(TirEntity data) {
        if (textField1.getText() != null ? !textField1.getText().equals(data.getCode()) : data.getCode() + "" != null)
            return true;
        if (textField2.getText() != null ? !textField2.getText().equals(data.getCount()) : data.getCount() + "" != null)
            return true;
        return false;
    }

    public String getSelectRowId() {
        return selectRowId;
    }

    public void setSelectRowId(String selectRowId) {
        this.selectRowId = selectRowId;
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
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FormLayout("fill:d:grow", "center:d:grow,top:4dlu:noGrow,center:max(d;4px):noGrow"));
        panel1 = new JPanel();
        panel1.setLayout(new FormLayout("fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:d:grow,left:4dlu:noGrow,fill:d:grow,left:4dlu:noGrow,fill:d:noGrow,left:4dlu:noGrow,fill:d:grow,left:4dlu:noGrow,fill:d:grow", "center:d:noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:d:grow"));
        panel1.setBackground(new Color(-2236963));
        panel1.setEnabled(true);
        panel1.setMinimumSize(new Dimension(400, 500));
        CellConstraints cc = new CellConstraints();
        panel2.add(panel1, cc.xy(1, 1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 24, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setMinimumSize(new Dimension(100, 50));
        label1.setText("تیر");
        panel1.add(label1, cc.xy(7, 1));
        textField2 = new JTextField();
        Font textField2Font = this.$$$getFont$$$(null, -1, 24, textField2.getFont());
        if (textField2Font != null) textField2.setFont(textField2Font);
        textField2.setMinimumSize(new Dimension(100, 40));
        textField2.setPreferredSize(new Dimension(100, 50));
        panel1.add(textField2, cc.xy(9, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(true);
        scrollPane1.setBackground(new Color(-65539));
        scrollPane1.setFocusCycleRoot(false);
        scrollPane1.setFocusTraversalPolicyProvider(false);
        Font scrollPane1Font = this.$$$getFont$$$(null, -1, 20, scrollPane1.getFont());
        if (scrollPane1Font != null) scrollPane1.setFont(scrollPane1Font);
        scrollPane1.setInheritsPopupMenu(false);
        panel1.add(scrollPane1, cc.xyw(3, 9, 7, CellConstraints.FILL, CellConstraints.FILL));
        table1 = new JTable();
        table1.setAlignmentX(0.5f);
        table1.setAutoCreateRowSorter(false);
        table1.setAutoResizeMode(2);
        table1.setCellSelectionEnabled(false);
        table1.setColumnSelectionAllowed(false);
        table1.setDragEnabled(false);
        table1.setEditingColumn(-1);
        table1.setEditingRow(-1);
        table1.setFillsViewportHeight(true);
        table1.setFocusCycleRoot(false);
        table1.setFocusTraversalPolicyProvider(false);
        Font table1Font = this.$$$getFont$$$("Tahoma", -1, 18, table1.getFont());
        if (table1Font != null) table1.setFont(table1Font);
        table1.setInheritsPopupMenu(false);
        table1.setIntercellSpacing(new Dimension(1, 1));
        table1.setRowHeight(30);
        table1.setRowMargin(1);
        table1.setRowSelectionAllowed(true);
        table1.setShowHorizontalLines(true);
        table1.setShowVerticalLines(true);
        table1.setSurrendersFocusOnKeystroke(false);
        table1.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
        table1.putClientProperty("Table.isFileList", Boolean.FALSE);
        scrollPane1.setViewportView(table1);
        حذفازجدولButton = new JButton();
        Font حذفازجدولButtonFont = this.$$$getFont$$$(null, -1, 24, حذفازجدولButton.getFont());
        if (حذفازجدولButtonFont != null) حذفازجدولButton.setFont(حذفازجدولButtonFont);
        حذفازجدولButton.setText("حذف از جدول");
        panel1.add(حذفازجدولButton, cc.xy(9, 7));
        textField3 = new JTextField();
        Font textField3Font = this.$$$getFont$$$(null, -1, 24, textField3.getFont());
        if (textField3Font != null) textField3.setFont(textField3Font);
        textField3.setPreferredSize(new Dimension(100, 50));
        panel1.add(textField3, cc.xyw(4, 3, 2, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label2 = new JLabel();
        label2.setFocusable(true);
        Font label2Font = this.$$$getFont$$$(null, -1, 24, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setPreferredSize(new Dimension(100, 50));
        label2.setText("توضیحات");
        panel1.add(label2, cc.xy(3, 3, CellConstraints.CENTER, CellConstraints.DEFAULT));
        textField1 = new JTextField();
        textField1.setFocusCycleRoot(false);
        Font textField1Font = this.$$$getFont$$$(null, -1, 24, textField1.getFont());
        if (textField1Font != null) textField1.setFont(textField1Font);
        textField1.setPreferredSize(new Dimension(10, 50));
        panel1.add(textField1, cc.xyw(4, 1, 2, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 24, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setMinimumSize(new Dimension(46, 25));
        label3.setPreferredSize(new Dimension(100, 50));
        label3.setText("کد پرسنل");
        panel1.add(label3, cc.xy(3, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));
        saveButton = new JButton();
        Font saveButtonFont = this.$$$getFont$$$(null, -1, 24, saveButton.getFont());
        if (saveButtonFont != null) saveButton.setFont(saveButtonFont);
        saveButton.setText("ذخیره");
        panel1.add(saveButton, cc.xy(5, 7));
        currentDateTextField = new JTextField();
        currentDateTextField.setEditable(false);
        currentDateTextField.setEnabled(true);
        Font currentDateTextFieldFont = this.$$$getFont$$$(null, -1, 24, currentDateTextField.getFont());
        if (currentDateTextFieldFont != null) currentDateTextField.setFont(currentDateTextFieldFont);
        currentDateTextField.setPreferredSize(new Dimension(100, 50));
        currentDateTextField.setText("");
        panel1.add(currentDateTextField, cc.xy(9, 3, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label4 = new JLabel();
        label4.setText("");
        panel1.add(label4, cc.xy(1, 8));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, cc.xy(11, 3, CellConstraints.FILL, CellConstraints.DEFAULT));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, cc.xy(1, 3, CellConstraints.FILL, CellConstraints.DEFAULT));
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
}
