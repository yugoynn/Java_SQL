package com.projeto.view;

import com.projeto.dao.ViewDAO;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class TelaConsultaViews extends JFrame {

    private static final Color FUNDO = new Color(18, 18, 24);
    private static final Color PAINEL = new Color(28, 28, 38);
    private static final Color CAMPO = new Color(38, 38, 52);
    private static final Color BORDA_CAMPO = new Color(70, 70, 100);
    private static final Color VERMELHO = new Color(200, 40, 40);
    private static final Color VERMELHO_ESC = new Color(140, 20, 20);
    private static final Color TEXTO = new Color(230, 230, 235);
    private static final Color TEXTO_FRACO = new Color(140, 140, 160);
    private static final Color HEADER_TABLE = new Color(35, 35, 48);
    private static final Color LINHA_PAR = new Color(32, 32, 44);
    private static final Color LINHA_IMPAR = new Color(26, 26, 36);

    private final ViewDAO viewDAO = new ViewDAO();
    private final JComboBox<String> comboView = new JComboBox<>(new String[]{
            "vw_ninja_missoes",
            "vw_total_ninjas_por_vila",
            "vw_total_missoes_por_rank",
            "vw_missoes_sem_ninjas"
    });
    private final DefaultTableModel modelo = new DefaultTableModel();
    private final JTable tabela = new JTable(modelo);

    public TelaConsultaViews() {
        setTitle("Consulta de Views");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(940, 610);
        setLocationRelativeTo(null);
        setBackground(FUNDO);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(FUNDO);

        JPanel header = new JPanel();
        header.setBackground(new Color(22, 22, 30));
        header.setBorder(new CompoundBorder(new MatteBorder(0, 0, 2, 0, VERMELHO), new EmptyBorder(14, 20, 14, 20)));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("👁️ Consulta de Views");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sub = new JLabel("Visualização de dados consolidados");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(TEXTO_FRACO);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(4));
        header.add(sub);

        JPanel selecao = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        selecao.setBackground(FUNDO);
        selecao.setBorder(new EmptyBorder(8, 0, 8, 0));

        JLabel lblView = new JLabel("Selecione a view:");
        lblView.setForeground(TEXTO_FRACO);
        lblView.setFont(new Font("SansSerif", Font.BOLD, 12));

        estilizarCombo(comboView);
        comboView.setPreferredSize(new Dimension(240, 32));

        JButton btnConsultar = criarBotao("🔍 Consultar", VERMELHO, VERMELHO_ESC);

        selecao.add(lblView);
        selecao.add(comboView);
        selecao.add(btnConsultar);

        estilizarTabela();
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBackground(FUNDO);
        scroll.getViewport().setBackground(LINHA_IMPAR);
        scroll.setBorder(new CompoundBorder(new EmptyBorder(0, 20, 20, 20), new LineBorder(BORDA_CAMPO, 1)));

        root.add(header, BorderLayout.NORTH);
        root.add(selecao, BorderLayout.CENTER);
        root.add(scroll, BorderLayout.SOUTH);

        btnConsultar.addActionListener(e -> consultar());

        setContentPane(root);
    }

    private void estilizarTabela() {
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabela.setBackground(LINHA_PAR);
        tabela.setForeground(TEXTO);
        tabela.setRowHeight(26);
        tabela.setShowVerticalLines(false);
        tabela.setSelectionBackground(new Color(200, 40, 40, 70));
        tabela.setSelectionForeground(TEXTO);
        tabela.setFillsViewportHeight(true);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

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
    }

    private void estilizarCombo(JComboBox<String> cb) {
        cb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cb.setBackground(CAMPO);
        cb.setForeground(TEXTO);
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? VERMELHO : CAMPO);
                setForeground(TEXTO);
                setBorder(new EmptyBorder(6, 12, 6, 12));
                return this;
            }
        });
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
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(TEXTO);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
        return btn;
    }

    private void consultar() {
        String nomeView = (String) comboView.getSelectedItem();
        try {
            List<String> colunas = viewDAO.colunasDaView(nomeView);
            List<Object[]> dados = viewDAO.dadosDaView(nomeView);

            modelo.setRowCount(0);
            modelo.setColumnCount(0);
            for (String coluna : colunas) {
                modelo.addColumn(coluna);
            }
            for (Object[] linha : dados) {
                modelo.addRow(linha);
            }

            if (dados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A view não retornou registros.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}