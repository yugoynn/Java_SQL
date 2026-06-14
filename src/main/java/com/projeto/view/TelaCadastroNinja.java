package com.projeto.view;

import com.projeto.model.Ninja;
import com.projeto.service.NinjaService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class TelaCadastroNinja extends JFrame {

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
    private static final Color SEL_TABLE = new Color(200, 40, 40, 70);

    private final NinjaService service = new NinjaService();

    private final JTextField campoNome = criarTextField();
    private final JTextField campoCla = criarTextField();
    private final JComboBox<String> comboVila = new JComboBox<>(new String[]{"Konoha", "Suna", "Kiri", "Iwa", "Kumo"});
    private final JComboBox<String> comboRank = new JComboBox<>(new String[]{"Genin", "Chunin", "Jounin", "Kage"});
    private final JComboBox<String> comboChakra = new JComboBox<>(new String[]{"Vento", "Raio", "Terra", "Agua", "Fogo", "Areia"});
    private final JComboBox<String> comboStatus = new JComboBox<>(new String[]{"Ativo", "Inativo"});

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Nome", "Vila", "Cla", "Rank", "Chakra", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private final JTable tabela = new JTable(modelo);
    private Integer idSelecionado = null;

    public TelaCadastroNinja() {
        setTitle("Cadastro de Ninjas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(880, 640);
        setLocationRelativeTo(null);
        setBackground(FUNDO);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(FUNDO);
        root.add(criarCabecalho(), BorderLayout.NORTH);
        root.add(criarFormulario(), BorderLayout.CENTER);
        root.add(criarTabela(), BorderLayout.SOUTH);

        setContentPane(root);
        tabela.getSelectionModel().addListSelectionListener(e -> preencherSelecionado());
        atualizarTabela();
    }

    private JPanel criarCabecalho() {
        JPanel p = new JPanel(new BorderLayout(12, 0));
        p.setBackground(new Color(22, 22, 30));
        p.setBorder(new CompoundBorder(new MatteBorder(0, 0, 2, 0, VERMELHO), new EmptyBorder(14, 20, 14, 20)));

        JPanel simbolo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth() / 2, cy = getHeight() / 2;
                g2.setColor(VERMELHO);
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawOval(cx - 15, cy - 15, 30, 30);
                g2.fillOval(cx - 4, cy - 4, 8, 8);
                g2.drawLine(cx, cy - 15, cx, cy - 5);
                g2.drawLine(cx, cy + 5, cx, cy + 15);
            }
        };
        simbolo.setPreferredSize(new Dimension(50, 50));
        simbolo.setOpaque(false);

        JLabel titulo = new JLabel("Cadastro de Ninjas");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(TEXTO);
        JLabel sub = new JLabel("Gerencie os shinobis da vila");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(TEXTO_FRACO);

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(Box.createVerticalStrut(3));
        textos.add(sub);

        p.add(simbolo, BorderLayout.WEST);
        p.add(textos, BorderLayout.CENTER);
        return p;
    }

    private JPanel criarFormulario() {
        JPanel externo = new JPanel(new BorderLayout());
        externo.setBackground(FUNDO);
        externo.setBorder(new EmptyBorder(16, 20, 8, 20));

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setBackground(PAINEL);
        grid.setBorder(new CompoundBorder(new LineBorder(BORDA_CAMPO, 1, true), new EmptyBorder(16, 18, 16, 18)));

        GridBagConstraints gl = new GridBagConstraints();
        gl.anchor = GridBagConstraints.WEST;
        gl.insets = new Insets(4, 0, 4, 12);

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        gc.insets = new Insets(4, 0, 4, 0);

        gl.gridx = 0;
        gl.gridy = 0;
        gc.gridx = 1;
        gc.gridy = 0;
        grid.add(label("Nome:"), gl);
        grid.add(campoNome, gc);

        gl.gridy = 1;
        gc.gridy = 1;
        grid.add(label("Vila:"), gl);
        estilizarCombo(comboVila);
        grid.add(comboVila, gc);

        gl.gridy = 2;
        gc.gridy = 2;
        grid.add(label("Clã:"), gl);
        grid.add(campoCla, gc);

        gl.gridy = 3;
        gc.gridy = 3;
        grid.add(label("Rank:"), gl);
        estilizarCombo(comboRank);
        grid.add(comboRank, gc);

        gl.gridy = 4;
        gc.gridy = 4;
        grid.add(label("Natureza do Chakra:"), gl);
        estilizarCombo(comboChakra);
        grid.add(comboChakra, gc);

        gl.gridy = 5;
        gc.gridy = 5;
        grid.add(label("Status:"), gl);
        estilizarCombo(comboStatus);
        grid.add(comboStatus, gc);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.setOpaque(false);
        JButton btnSalvar = criarBotao("Salvar", VERMELHO, VERMELHO_ESC);
        JButton btnLimpar = criarBotao("Limpar", CAMPO, BORDA_CAMPO);
        JButton btnExcluir = criarBotao("Excluir", new Color(80, 30, 30), new Color(60, 15, 15));
        botoes.add(btnLimpar);
        botoes.add(btnExcluir);
        botoes.add(btnSalvar);

        GridBagConstraints gcBotoes = new GridBagConstraints();
        gcBotoes.gridx = 0;
        gcBotoes.gridy = 6;
        gcBotoes.gridwidth = 2;
        gcBotoes.fill = GridBagConstraints.HORIZONTAL;
        gcBotoes.insets = new Insets(8, 0, 0, 0);
        grid.add(botoes, gcBotoes);

        btnSalvar.addActionListener(e -> salvar());
        btnLimpar.addActionListener(e -> limpar());
        btnExcluir.addActionListener(e -> excluir());

        externo.add(grid, BorderLayout.CENTER);
        return externo;
    }

    private JScrollPane criarTabela() {
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabela.setBackground(LINHA_PAR);
        tabela.setForeground(TEXTO);
        tabela.setGridColor(new Color(45, 45, 60));
        tabela.setRowHeight(26);
        tabela.setShowVerticalLines(false);
        tabela.setSelectionBackground(SEL_TABLE);
        tabela.setFillsViewportHeight(true);

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setBackground(sel ? SEL_TABLE : (r % 2 == 0 ? LINHA_IMPAR : LINHA_PAR));
                setForeground(TEXTO);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return this;
            }
        });

        JTableHeader header = tabela.getTableHeader();
        header.setBackground(HEADER_TABLE);
        header.setForeground(VERMELHO);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setBorder(new MatteBorder(0, 0, 2, 0, VERMELHO));

        tabela.getColumnModel().getColumn(0).setMaxWidth(45);

        JScrollPane sp = new JScrollPane(tabela);
        sp.setBackground(FUNDO);
        sp.getViewport().setBackground(LINHA_IMPAR);
        sp.setBorder(new CompoundBorder(new EmptyBorder(0, 20, 16, 20), new LineBorder(BORDA_CAMPO, 1)));
        sp.setPreferredSize(new Dimension(0, 200));
        return sp;
    }

    private JLabel label(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setForeground(TEXTO_FRACO);
        return l;
    }

    private JTextField criarTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBackground(CAMPO);
        tf.setForeground(TEXTO);
        tf.setCaretColor(TEXTO);
        tf.setBorder(new CompoundBorder(new LineBorder(BORDA_CAMPO), new EmptyBorder(5, 8, 5, 8)));
        return tf;
    }

    private void estilizarCombo(JComboBox<String> cb) {
        cb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cb.setBackground(CAMPO);
        cb.setForeground(TEXTO);
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? VERMELHO : CAMPO);
                setForeground(TEXTO);
                setBorder(new EmptyBorder(4, 8, 4, 8));
                return this;
            }
        });
    }

    private JButton criarBotao(String texto, Color bg, Color bgHover) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
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
        btn.setBorder(new EmptyBorder(7, 20, 7, 20));
        return btn;
    }

    private void salvar() {
        try {
            Ninja ninja = new Ninja();
            ninja.setNome(campoNome.getText().trim());
            ninja.setVila((String) comboVila.getSelectedItem());
            ninja.setCla(campoCla.getText().trim());
            ninja.setRankNinja((String) comboRank.getSelectedItem());
            ninja.setNaturezaChakra((String) comboChakra.getSelectedItem());
            ninja.setStatus((String) comboStatus.getSelectedItem());

            if (idSelecionado == null) {
                service.cadastrar(ninja);
                JOptionPane.showMessageDialog(this, "Ninja cadastrado com sucesso!");
            } else {
                ninja.setId(idSelecionado);
                service.atualizar(ninja);
                JOptionPane.showMessageDialog(this, "Ninja atualizado com sucesso!");
            }
            limpar();
            atualizarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um ninja na tabela.");
            return;
        }
        int op = JOptionPane.showConfirmDialog(this, "Excluir este ninja?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            try {
                service.excluir(idSelecionado);
                limpar();
                atualizarTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Não foi possível excluir. Verifique se está vinculado a missões.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void preencherSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) return;
        idSelecionado = (Integer) modelo.getValueAt(linha, 0);
        campoNome.setText((String) modelo.getValueAt(linha, 1));
        comboVila.setSelectedItem(modelo.getValueAt(linha, 2));
        campoCla.setText((String) modelo.getValueAt(linha, 3));
        comboRank.setSelectedItem(modelo.getValueAt(linha, 4));
        comboChakra.setSelectedItem(modelo.getValueAt(linha, 5));
        comboStatus.setSelectedItem(modelo.getValueAt(linha, 6));
    }

    private void limpar() {
        idSelecionado = null;
        campoNome.setText("");
        campoCla.setText("");
        comboVila.setSelectedIndex(0);
        comboRank.setSelectedIndex(0);
        comboChakra.setSelectedIndex(0);
        comboStatus.setSelectedIndex(0);
        tabela.clearSelection();
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        for (Ninja n : service.listar()) {
            modelo.addRow(new Object[]{n.getId(), n.getNome(), n.getVila(), n.getCla(), n.getRankNinja(), n.getNaturezaChakra(), n.getStatus()});
        }
    }
}