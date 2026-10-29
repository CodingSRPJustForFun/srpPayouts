package com.srpPayouts.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class MsgBox {

    public static void info(String message) {
        info(message, theNameOfTheMethodThatCalledMe());
    }
    public static void info(String message, String caller) {
        show(message, caller, JOptionPane.INFORMATION_MESSAGE);
    }

    static void error(String message) {
        error(message, theNameOfTheMethodThatCalledMe());
    }
    public static void error(String message, String caller) {
        show(message, caller, JOptionPane.ERROR_MESSAGE);
    }

    public static void show(String message, String title, int iconId) {
        setClipboard(title+":"+NEW_LINE+message);

        JOptionPane op = new JOptionPane(message,iconId);
        JDialog dialog = op.createDialog(title);
        dialog.setAlwaysOnTop(true); //<-- this line
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        //JOptionPane.showMessageDialog(null, message, title, iconId);
    }
    private static final String NEW_LINE = System.lineSeparator();

    public static int askValueInt(String message, String item){
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        String value;
        try {
            value = JOptionPane.showInputDialog(jf,message, item);
        } finally {
            jf.dispose();
        }
        int intValue = 0;
        try {
            intValue = Integer.parseInt(value);
        } catch(Exception ex) {}
        return intValue;
    }

    public static String theNameOfTheMethodThatCalledMe() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    public static void setClipboard(String message) {
        CLIPBOARD.setContents(new StringSelection(message), null);
    }
    private static final Toolkit AWT_TOOLKIT = Toolkit.getDefaultToolkit();
    private static final Clipboard CLIPBOARD = AWT_TOOLKIT.getSystemClipboard();

    public static String askValueString(String message, String stringToFill) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        String value;
        try {
            value = JOptionPane.showInputDialog(jf,message, stringToFill);
        } finally {
            jf.dispose();
        }
        if(value.equals(stringToFill)){
            value = "";
        }
        return value;
    }
}
