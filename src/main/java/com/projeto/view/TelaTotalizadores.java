package com.projeto.view;

import com.projeto.model.Totalizador;
import com.projeto.service.TotalizadorService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaTotalizadores extends JFrame {

    private static final Color FUNDO = new Color(18, 18, 24);
    private static final Color PAINEL = new Color(28, 28, 38);
    private static final Color VERMELHO = new Color(200, 40, 40);
    private static final Color VERMELHO_ESC = new Color(140, 20, 20);
    private static final Color TEXTO = new Color(230, 230, 235);
    private static final Color TEXTO_FRACO = new Color(140, 140, 160);
    private static final Color HEADER_TABLE = new Color(35, 35, 48);
    private static final Color LINHA_PAR = new Color(32, 32, 44);
    private static final Color LINHA_IMPAR = new Color(26, 26, 36);

    private final TotalizadorService service = new TotalizadorService();
    private final DefaultTableModel modelo = new DefaultTableModel(new String[]{"Descrição", "Quantidade", "Gerado em"}, 0);
    private final JTable tabela = new JTable(modelo);
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TelaTotalizadores() {
        setTitle("Totalizadores do Sistema");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(880, 630);
        setLocationRelativeTo(null);
        setBackground(FUNDO);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(FUNDO);

        JPanel header = new JPanel();
        header.setBackground(new Color(22, 22, 30));
        header.setBorder(new CompoundBorder(new MatteBorder(0, 0, 2, 0, VERMELHO), new EmptyBorder(14, 20, 14, 20)));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("📊 Totalizadores");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Estatísticas e métricas do sistema");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(TEXTO_FRACO);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(4));
        header.add(sub);

        JButton btnGerar = criarBotao("🔄 Gerar Totalizadores", VERMELHO, VERMELHO_ESC);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(FUNDO);
        btnPanel.setBorder(new EmptyBorder(12, 0, 12, 0));
        btnPanel.add(btnGerar);

        estilizarTabela();
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBackground(FUNDO);
        scroll.getViewport().setBackground(LINHA_IMPAR);
        scroll.setBorder(new CompoundBorder(new EmptyBorder(0, 20, 20, 20), new LineBorder(new Color(70, 70, 100), 1)));

        root.add(header, BorderLayout.NORTH);
        root.add(btnPanel, BorderLayout.CENTER);
        root.add(scroll, BorderLayout.SOUTH);

        btnGerar.addActionListener(e -> gerar());

        setContentPane(root);
    }

    private void estilizarTabela() {
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabela.setBackground(LINHA_PAR);
        tabela.setForeground(TEXTO);
        tabela.setRowHeight(28);
        tabela.setShowVerticalLines(false);
        tabela.setSelectionBackground(new Color(200, 40, 40, 70));
        tabela.setSelectionForeground(TEXTO);
        tabela.setFillsViewportHeight(true);

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setBackground(sel ? new Color(200, 40, 40, 70) : (r % 2 == 0 ? LINHA_IMPAR : LINHA_PAR));
                setForeground(TEXTO);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                return this;
            }
        });

        JTableHeader header = tabela.getTableHeader();
        header.setBackground(HEADER_TABLE);
        header.setForeground(VERMELHO);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setBorder(new MatteBorder(0, 0, 2, 0, VERMELHO));

        tabela.getColumnModel().getColumn(0).setPreferredWidth(350);
        tabela.getColumnModel().getColumn(1).setMaxWidth(100);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(160);
    }

    private JButton criarBotao(String texto, Color bg, Color bgHover) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bgHover : bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setForeground(TEXTO);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 25, 10, 25));
        return btn;
    }

    private void gerar() {
        try {
            List<Totalizador> totalizadores = service.gerarEsalvar();
            modelo.setRowCount(0);
            for (Totalizador t : totalizadores) {
                modelo.addRow(new Object[]{
                        t.getDescricao(),
                        t.getQuantidade(),
                        t.getDataGeracao().format(formato)
                });
            }
            JOptionPane.showMessageDialog(this, "Totalizadores gerados e salvos no banco!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}