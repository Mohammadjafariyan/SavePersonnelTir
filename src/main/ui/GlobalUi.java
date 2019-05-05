package main.ui;

import service.Callback;
import service.MyActionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class GlobalUi {

    private final JFrame saveTirJFrame;

    private SaveTir saveTir;
    private ExportTir exportTir;
    private Setting setting;

    public SaveTir getSaveTir() {
        return saveTir;
    }

    public void setSaveTir(SaveTir saveTir) {
        this.saveTir = saveTir;
    }

    public ExportTir getExportTir() {
        return exportTir;
    }

    public void setExportTir(ExportTir exportTir) {
        this.exportTir = exportTir;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public JFrame getSaveTirJFrame() {
        return saveTirJFrame;
    }

    public JFrame getExportTirJFrame() {
        return exportTirJFrame;
    }

    public JFrame getSettingFrame() {
        return settingFrame;
    }

    public MyActionListener getSettingFrameListener() {
        return settingFrameListener;
    }

    public void setSettingFrameListener(MyActionListener settingFrameListener) {
        this.settingFrameListener = settingFrameListener;
    }

    public ActionListener getSaveTirActionListener() {
        return saveTirActionListener;
    }

    public void setSaveTirActionListener(ActionListener saveTirActionListener) {
        this.saveTirActionListener = saveTirActionListener;
    }

    public ActionListener getExportTirItemActionListener() {
        return exportTirItemActionListener;
    }

    public void setExportTirItemActionListener(ActionListener exportTirItemActionListener) {
        this.exportTirItemActionListener = exportTirItemActionListener;
    }

    private final JFrame exportTirJFrame;
    private final JFrame settingFrame;
    private Callback currentCallback;
    private MyActionListener settingFrameListener = new MyActionListener() {
        @Override
        public void actionPerformed(ActionEvent e, Callback callback) {
            actionPerformed(e);

            setting.setCurrentCallback(callback);
            currentCallback=callback;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            settingFrame.setVisible(true);
            settingFrame.toFront();
            settingFrame.setJMenuBar(menubar);

            setting.initCombos();


            /*saveTirJFrame.setVisible(false);
            exportTirJFrame.setVisible(false);*/
        }
    };
    private JMenuItem settingItem;


    public GlobalUi(JFrame saveTir, JFrame exportTir, JFrame settingFrame) {
        this.saveTirJFrame = saveTir;
        this.exportTirJFrame = exportTir;
        this.settingFrame = settingFrame;
    }

    private JMenuBar menubar;
    private JMenuItem saveTirItem;
    private JMenuItem exportTirItem;
    private ActionListener saveTirActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            exportTirJFrame.setVisible(false);
            settingFrame.setVisible(false);


            saveTir.requeryTable();

            saveTirJFrame.toFront();
            saveTirJFrame.setVisible(true);
            saveTirJFrame.setJMenuBar(menubar);

        }
    };
    private ActionListener exportTirItemActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            exportTirJFrame.setVisible(true);
            exportTirJFrame.toFront();
            exportTirJFrame.setJMenuBar(menubar);


            exportTir.requeryTable(true);
            saveTirJFrame.setVisible(false);
            settingFrame.setVisible(false);
        }
    };

    public JMenuBar getMenubar() {
        return menubar;
    }

    public void setMenubar(JMenuBar menubar) {
        this.menubar = menubar;
    }

    public JMenuBar InitMenuBar() {

        JMenu menu = new JMenu("عملیات ها");
        saveTirItem = new JMenuItem("ثبت تیر");
        exportTirItem = new JMenuItem("دریافت خروجی");
        settingItem = new JMenuItem("تنظیمات");

        saveTirItem.addActionListener(saveTirActionListener);
        exportTirItem.addActionListener(exportTirItemActionListener);
        settingItem.addActionListener(settingFrameListener);


        menubar = new JMenuBar();
        menu.add(exportTirItem);
        menu.add(saveTirItem);
        menu.add(settingItem);

        menubar.add(menu);
        menubar.setVisible(true);
        return menubar;

    }


    public void repaintAll() {
    /*    getExportTirJFrame().getJMenuBar().revalidate();
        getExportTirJFrame().getJMenuBar().repaint();

*/

        if (getSaveTirJFrame().isVisible()) {
            getSaveTirJFrame().setVisible(false);
            getSaveTirJFrame().setVisible(true);
            getExportTirJFrame().toFront();
            getSaveTirJFrame().setJMenuBar(menubar);

            getSaveTir().resetDateButton();
            getSaveTir().requeryTable();
        }
        if (getExportTirJFrame().isVisible()) {
            getExportTirJFrame().setVisible(false);
            getExportTirJFrame().setVisible(true);
            getExportTirJFrame().toFront();
            getExportTirJFrame().setJMenuBar(menubar);
        }


    }
}
