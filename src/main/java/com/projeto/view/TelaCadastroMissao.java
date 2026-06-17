package com.projeto.view;

import com.projeto.model.Missao;
import com.projeto.service.MissaoService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class TelaCadastroMissao extends JFrame {

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

    private final MissaoService service = new MissaoService();

    private final JTextField campoTitulo = criarTextField();
    private final JTextArea areaDescricao = new JTextArea(3, 20);
    private final JComboBox<String> comboRank = new JComboBox<>(new String[]{"D", "C", "B", "A", "S"});
    private final JComboBox<String> comboVila = new JComboBox<>(new String[]{"Konoha", "Suna", "Kiri", "Iwa", "Kumo"});
    private final JComboBox<String> comboStatus = new JComboBox<>(new String[]{"Aberta", "Em andamento", "Concluida", "Cancelada"});

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Titulo", "Rank", "Vila", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private final JTable tabela = new JTable(modelo);
    private Integer idSelecionado = null;

    public TelaCadastroMissao() {
        setTitle("Cadastro de Missões");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(880, 680);
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
                for (int i = 0; i < 3; i++) {
                    double ang = Math.toRadians(i * 120 - 90);
                    int px = (int) (cx + Math.cos(ang) * 10);
                    int py = (int) (cy + Math.sin(ang) * 10);
                    g2.fillOval(px - 3, py - 3, 6, 6);
                }
            }
        };
        simbolo.setPreferredSize(new Dimension(50, 50));
        simbolo.setOpaque(false);

        JLabel titulo = new JLabel("Cadastro de Missões");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(TEXTO);
        JLabel sub = new JLabel("Gerencie as missões disponíveis na vila");
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

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(PAINEL);
        painelPrincipal.setBorder(new CompoundBorder(new LineBorder(BORDA_CAMPO, 1, true), new EmptyBorder(16, 18, 16, 18)));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 10, 5, 10);
        gc.fill = GridBagConstraints.BOTH;

        JPanel ladoEsquerdo = new JPanel(new GridBagLayout());
        ladoEsquerdo.setOpaque(false);
        GridBagConstraints gcEsq = new GridBagConstraints();
        gcEsq.fill = GridBagConstraints.HORIZONTAL;
        gcEsq.weightx = 1;
        gcEsq.insets = new Insets(5, 0, 5, 0);

        gcEsq.gridx = 0;
        gcEsq.gridy = 0;
        ladoEsquerdo.add(label("Título:"), gcEsq);

        gcEsq.gridy = 1;
        ladoEsquerdo.add(campoTitulo, gcEsq);

        gcEsq.gridy = 2;
        gcEsq.insets = new Insets(15, 0, 5, 0);
        ladoEsquerdo.add(label("Descrição:"), gcEsq);

        gcEsq.gridy = 3;
        gcEsq.insets = new Insets(0, 0, 5, 0);
        gcEsq.weighty = 1;
        gcEsq.fill = GridBagConstraints.BOTH;

        areaDescricao.setLineWrap(true);
        areaDescricao.setWrapStyleWord(true);
        areaDescricao.setFont(new Font("SansSerif", Font.PLAIN, 13));
        areaDescricao.setBackground(CAMPO);
        areaDescricao.setForeground(TEXTO);
        areaDescricao.setCaretColor(TEXTO);
        areaDescricao.setBorder(new CompoundBorder(new LineBorder(BORDA_CAMPO), new EmptyBorder(5, 8, 5, 8)));
        JScrollPane scrollDesc = new JScrollPane(areaDescricao);
        scrollDesc.setBorder(new LineBorder(BORDA_CAMPO));
        scrollDesc.getViewport().setBackground(CAMPO);
        ladoEsquerdo.add(scrollDesc, gcEsq);

        JPanel ladoDireito = new JPanel(new GridBagLayout());
        ladoDireito.setOpaque(false);
        GridBagConstraints gcDir = new GridBagConstraints();
        gcDir.fill = GridBagConstraints.HORIZONTAL;
        gcDir.weightx = 1;
        gcDir.insets = new Insets(5, 0, 5, 0);

        gcDir.gridx = 0;
        gcDir.gridy = 0;
        ladoDireito.add(label("Rank:"), gcDir);

        gcDir.gridy = 1;
        estilizarCombo(comboRank);
        comboRank.setPreferredSize(new Dimension(180, 32));
        ladoDireito.add(comboRank, gcDir);

        gcDir.gridy = 2;
        gcDir.insets = new Insets(15, 0, 5, 0);
        ladoDireito.add(label("Vila de Origem:"), gcDir);

        gcDir.gridy = 3;
        gcDir.insets = new Insets(0, 0, 5, 0);
        estilizarCombo(comboVila);
        comboVila.setPreferredSize(new Dimension(180, 32));
        ladoDireito.add(comboVila, gcDir);

        gcDir.gridy = 4;
        gcDir.insets = new Insets(15, 0, 5, 0);
        ladoDireito.add(label("Status:"), gcDir);

        gcDir.gridy = 5;
        gcDir.insets = new Insets(0, 0, 5, 0);
        estilizarCombo(comboStatus);
        comboStatus.setPreferredSize(new Dimension(180, 32));
        ladoDireito.add(comboStatus, gcDir);

        gcDir.gridy = 6;
        gcDir.weighty = 1;
        ladoDireito.add(Box.createVerticalGlue(), gcDir);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 0.5;
        gc.weighty = 1;
        painelPrincipal.add(ladoEsquerdo, gc);

        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setForeground(BORDA_CAMPO);
        separator.setPreferredSize(new Dimension(1, 0));
        gc.gridx = 1;
        gc.gridy = 0;
        gc.weightx = 0;
        painelPrincipal.add(separator, gc);

        gc.gridx = 2;
        gc.gridy = 0;
        gc.weightx = 0.3;
        painelPrincipal.add(ladoDireito, gc);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botoes.setOpaque(false);
        botoes.setBorder(new EmptyBorder(15, 0, 0, 0));

        JButton btnSalvar = criarBotao("Salvar", VERMELHO, VERMELHO_ESC);
        JButton btnLimpar = criarBotao("Limpar", CAMPO, BORDA_CAMPO);
        JButton btnExcluir = criarBotao("Excluir", new Color(80, 30, 30), new Color(60, 15, 15));

        botoes.add(btnLimpar);
        botoes.add(btnExcluir);
        botoes.add(btnSalvar);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.gridwidth = 3;
        gc.weighty = 0;
        gc.insets = new Insets(10, 0, 0, 0);
        painelPrincipal.add(botoes, gc);

        btnSalvar.addActionListener(e -> salvar());
        btnLimpar.addActionListener(e -> limpar());
        btnExcluir.addActionListener(e -> excluir());

        externo.add(painelPrincipal, BorderLayout.CENTER);
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
        tabela.getColumnModel().getColumn(2).setMaxWidth(55);

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
            Missao missao = new Missao();
            missao.setTitulo(campoTitulo.getText().trim());
            missao.setDescricao(areaDescricao.getText().trim());
            missao.setRankMissao((String) comboRank.getSelectedItem());
            missao.setVilaOrigem((String) comboVila.getSelectedItem());
            missao.setStatus((String) comboStatus.getSelectedItem());

            if (idSelecionado == null) {
                service.cadastrar(missao);
                JOptionPane.showMessageDialog(this, "Missão cadastrada com sucesso!");
            } else {
                missao.setId(idSelecionado);
                service.atualizar(missao);
                JOptionPane.showMessageDialog(this, "Missão atualizada com sucesso!");
            }
            limpar();
            atualizarTabela();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma missão na tabela.");
            return;
        }
        int op = JOptionPane.showConfirmDialog(this, "Excluir esta missão?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            try {
                service.excluir(idSelecionado);
                limpar();
                atualizarTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Não foi possível excluir. Verifique se possui ninjas vinculados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void preencherSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) return;
        idSelecionado = (Integer) modelo.getValueAt(linha, 0);
        campoTitulo.setText((String) modelo.getValueAt(linha, 1));
        comboRank.setSelectedItem(modelo.getValueAt(linha, 2));
        comboVila.setSelectedItem(modelo.getValueAt(linha, 3));
        comboStatus.setSelectedItem(modelo.getValueAt(linha, 4));

        service.listar().stream()
                .filter(m -> m.getId() == idSelecionado)
                .findFirst()
                .ifPresent(m -> areaDescricao.setText(m.getDescricao()));
    }

    private void limpar() {
        idSelecionado = null;
        campoTitulo.setText("");
        areaDescricao.setText("");
        comboRank.setSelectedIndex(0);
        comboVila.setSelectedIndex(0);
        comboStatus.setSelectedIndex(0);
        tabela.clearSelection();
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        for (Missao m : service.listar()) {
            modelo.addRow(new Object[]{
                    m.getId(), m.getTitulo(), m.getRankMissao(),
                    m.getVilaOrigem(), m.getStatus()
            });
        }
    }
}