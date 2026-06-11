package com.projeto.view;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema de Gerenciamento Ninja");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 480);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("Vila Oculta", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Painel de controle dos ninjas", SwingConstants.CENTER);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setBorder(BorderFactory.createEmptyBorder(4, 0, 24, 0));

        painel.add(titulo);
        painel.add(subtitulo);

        adicionar(painel, "Cadastro de Ninjas",
                e -> new TelaCadastroNinja().setVisible(true));
        adicionar(painel, "Cadastro de Missoes",
                e -> new TelaCadastroMissao().setVisible(true));
        adicionar(painel, "Vincular Ninja e Missao",
                e -> new TelaVincularMissao().setVisible(true));
        adicionar(painel, "Totalizadores",
                e -> new TelaTotalizadores().setVisible(true));
        adicionar(painel, "Consultar Views",
                e -> new TelaConsultaViews().setVisible(true));

        add(painel, BorderLayout.CENTER);
    }

    private void adicionar(JPanel painel, String texto, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(new Dimension(280, 44));
        botao.setFont(new Font("SansSerif", Font.PLAIN, 15));
        botao.addActionListener(acao);
        painel.add(botao);
        painel.add(javax.swing.Box.createVerticalStrut(12));
    }
}
