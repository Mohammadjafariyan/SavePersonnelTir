package main;

import main.ui.ExportTir;
import main.ui.GlobalUi;
import main.ui.SaveTir;
import main.ui.Setting;
import other.FontLoader;
import service.SharedDataHolderSingeton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("ثبت تیر");
        SaveTir saveTir = new SaveTir();
        frame.setContentPane(saveTir.panel1);

        Locale.setDefault(new Locale("fa", "IR"));


        frame.setPreferredSize(new Dimension(1000, 700));


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        // export tir
        ExportTir exportTir = new ExportTir();
        JFrame exportTirFrame = exportTir.init();


        Setting setting = new Setting();
        JFrame settingFrame = setting.init();


        // menu
        GlobalUi uiMenu = new GlobalUi(frame, exportTirFrame, settingFrame);
        JMenuBar menuBar = uiMenu.InitMenuBar();


        SharedDataHolderSingeton.setGlobalUi(uiMenu);


        setting.setGlobalUi(uiMenu);
        exportTir.setGlobalUi(uiMenu);
        saveTir.setGlobalUi(uiMenu);

        uiMenu.setSetting(setting);
        uiMenu.setExportTir(exportTir);
        uiMenu.setSaveTir(saveTir);


        settingFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                uiMenu.repaintAll();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });


        // On tops
        //  exportTirFrame.setAlwaysOnTop(true);
        //  frame.setAlwaysOnTop(true);


        // set menu
        frame.setJMenuBar(menuBar);
        // visile
        frame.setVisible(true);


        FontLoader.setUIFont(FontLoader.Load());

    }
}
