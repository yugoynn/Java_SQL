package com.projeto.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TelaPrincipal extends JFrame {

    private static final Color FUNDO = new Color(18, 18, 24);
    private static final Color PAINEL = new Color(28, 28, 38);
    private static final Color VERMELHO = new Color(200, 40, 40);
    private static final Color VERMELHO_ESC = new Color(140, 20, 20);
    private static final Color TEXTO = new Color(230, 230, 235);
    private static final Color TEXTO_FRACO = new Color(140, 140, 160);

    public TelaPrincipal() {
        setTitle("Sistema de Gerenciamento Ninja");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 580);
        setLocationRelativeTo(null);
        setBackground(FUNDO);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(FUNDO);

        JPanel header = new JPanel();
        header.setBackground(new Color(22, 22, 30));
        header.setBorder(new CompoundBorder(new MatteBorder(0, 0, 2, 0, VERMELHO), new EmptyBorder(20, 20, 20, 20)));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("⚔️ Vila Oculta da Folha ⚔️");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(VERMELHO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Sistema de Gerenciamento Ninja");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitulo.setForeground(TEXTO_FRACO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitulo);

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(FUNDO);
        menu.setBorder(new EmptyBorder(30, 50, 30, 50));

        String[] botoes = {
                "📋 Cadastro de Ninjas",
                "📜 Cadastro de Missões",
                "🔗 Vincular Ninja e Missão",
                "📊 Totalizadores",
                "👁️ Consultar Views"
        };

        Runnable[] acoes = {
                () -> new TelaCadastroNinja().setVisible(true),
                () -> new TelaCadastroMissao().setVisible(true),
                () -> new TelaVincularMissao().setVisible(true),
                () -> new TelaTotalizadores().setVisible(true),
                () -> new TelaConsultaViews().setVisible(true)
        };

        for (int i = 0; i < botoes.length; i++) {
            JButton btn = criarBotaoMenu(botoes[i]);
            final Runnable acao = acoes[i];
            btn.addActionListener(e -> acao.run());
            menu.add(btn);
            menu.add(Box.createVerticalStrut(12));
        }

        root.add(header, BorderLayout.NORTH);
        root.add(menu, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? VERMELHO_ESC : PAINEL);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(TEXTO);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(12, 20, 12, 20));
        btn.setMaximumSize(new Dimension(280, 48));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }
}