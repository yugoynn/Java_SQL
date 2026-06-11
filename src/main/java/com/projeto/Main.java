package com.projeto;

import com.projeto.view.TelaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignorada) {
        }

        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
