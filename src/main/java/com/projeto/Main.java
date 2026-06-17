package com.projeto;

import com.projeto.view.TelaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Color;

public class Main {

    public static void main(String[] args) {
        configurarModoDark();
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }

    private static void configurarModoDark() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            Color fundoPrincipal = new Color(18, 18, 24);
            Color painel = new Color(28, 28, 38);
            Color campo = new Color(38, 38, 52);
            Color texto = new Color(230, 230, 235);
            Color vermelho = new Color(200, 40, 40);

            UIManager.put("Panel.background", fundoPrincipal);
            UIManager.put("OptionPane.background", fundoPrincipal);
            UIManager.put("OptionPane.messageForeground", texto);

            UIManager.put("Button.background", painel);
            UIManager.put("Button.foreground", texto);

            UIManager.put("Label.foreground", texto);
            UIManager.put("Label.background", fundoPrincipal);

            UIManager.put("TextField.background", campo);
            UIManager.put("TextField.foreground", texto);
            UIManager.put("TextField.caretForeground", texto);

            UIManager.put("TextArea.background", campo);
            UIManager.put("TextArea.foreground", texto);

            UIManager.put("ComboBox.background", campo);
            UIManager.put("ComboBox.foreground", texto);

            UIManager.put("Table.background", new Color(32, 32, 44));
            UIManager.put("Table.foreground", texto);
            UIManager.put("Table.gridColor", new Color(45, 45, 60));

            UIManager.put("TableHeader.background", new Color(35, 35, 48));
            UIManager.put("TableHeader.foreground", vermelho);

            UIManager.put("ScrollPane.background", fundoPrincipal);
            UIManager.put("Viewport.background", new Color(32, 32, 44));

            UIManager.put("TitledBorder.titleColor", vermelho);
            UIManager.put("TitledBorder.border", new javax.swing.border.LineBorder(new Color(70, 70, 100)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}