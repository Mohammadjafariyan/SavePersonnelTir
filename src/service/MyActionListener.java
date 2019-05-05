package service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface MyActionListener extends ActionListener {
    public void actionPerformed(ActionEvent e,Callback callback) ;
}
