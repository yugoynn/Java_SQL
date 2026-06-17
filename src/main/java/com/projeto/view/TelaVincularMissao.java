package com.projeto.view;

import com.projeto.model.Missao;
import com.projeto.model.Ninja;
import com.projeto.model.NinjaMissao;
import com.projeto.service.MissaoService;
import com.projeto.service.NinjaMissaoService;
import com.projeto.service.NinjaService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaVincularMissao extends JFrame {

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

    private final NinjaService ninjaService = new NinjaService();
    private final MissaoService missaoService = new MissaoService();
    private final NinjaMissaoService vinculoService = new NinjaMissaoService();

    private final JComboBox<Ninja> comboNinja = new JComboBox<>();
    private final JComboBox<Missao> comboMissao = new JComboBox<>();
    private final JComboBox<String> comboFuncao = new JComboBox<>(new String[]{"Líder", "Ataque", "Suporte", "Sensorial", "Médico", "Defesa"});

    private final DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Ninja", "Missão", "Função", "Data"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(modelo);

    public TelaVincularMissao() {
        setTitle("Vincular Ninja à Missão");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 540);
        setLocationRelativeTo(null);
        setBackground(FUNDO);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(FUNDO);
        root.add(criarCabecalho(), BorderLayout.NORTH);
        root.add(criarFormulario(), BorderLayout.CENTER);
        root.add(criarTabela(), BorderLayout.SOUTH);

        setContentPane(root);
        carregarCombos();
        atualizarTabela();
    }

    private JPanel criarCabecalho() {
        JPanel p = new JPanel(new BorderLayout(12, 0));
        p.setBackground(new Color(22, 22, 30));
        p.setBorder(new CompoundBorder(new MatteBorder(0, 0, 2, 0, VERMELHO), new EmptyBorder(14, 20, 14, 20)));

        JLabel titulo = new JLabel("Vínculo Ninja ↔ Missão");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(TEXTO);
        JLabel sub = new JLabel("Atribua funções e registre participações");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(TEXTO_FRACO);

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(Box.createVerticalStrut(3));
        textos.add(sub);

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

        gl.gridx = 0; gl.gridy = 0; gc.gridx = 1; gc.gridy = 0;
        grid.add(label("Ninja:"), gl);
        estilizarCombo(comboNinja);
        grid.add(comboNinja, gc);

        gl.gridy = 1; gc.gridy = 1;
        grid.add(label("Missão:"), gl);
        estilizarCombo(comboMissao);
        grid.add(comboMissao, gc);

        gl.gridy = 2; gc.gridy = 2;
        grid.add(label("Função:"), gl);
        estilizarCombo(comboFuncao);
        grid.add(comboFuncao, gc);

        JButton btnVincular = criarBotao("Vincular Ninja à Missão", VERMELHO, VERMELHO_ESC);
        GridBagConstraints gcBtn = new GridBagConstraints();
        gcBtn.gridx = 0; gcBtn.gridy = 3;
        gcBtn.gridwidth = 2;
        gcBtn.fill = GridBagConstraints.HORIZONTAL;
        gcBtn.insets = new Insets(16, 0, 0, 0);
        grid.add(btnVincular, gcBtn);

        btnVincular.addActionListener(e -> vincular());

        externo.add(grid, BorderLayout.CENTER);
        return externo;
    }

    private JScrollPane criarTabela() {
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabela.setBackground(LINHA_PAR);
        tabela.setForeground(TEXTO);
        tabela.setRowHeight(26);
        tabela.setSelectionBackground(SEL_TABLE);
        tabela.setFillsViewportHeight(true);

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
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

    private void estilizarCombo(JComboBox<?> cb) {
        cb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cb.setBackground(CAMPO);
        cb.setForeground(TEXTO);
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
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
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private void carregarCombos() {
        comboNinja.removeAllItems();
        for (Ninja n : ninjaService.listar()) {
            comboNinja.addItem(n);
        }
        comboMissao.removeAllItems();
        for (Missao m : missaoService.listar()) {
            comboMissao.addItem(m);
        }
    }

    private void vincular() {
        Ninja ninja = (Ninja) comboNinja.getSelectedItem();
        Missao missao = (Missao) comboMissao.getSelectedItem();

        if (ninja == null || missao == null) {
            JOptionPane.showMessageDialog(this, "Cadastre ninjas e missões primeiro.");
            return;
        }

        NinjaMissao vinculo = new NinjaMissao();
        vinculo.setIdNinja(ninja.getId());
        vinculo.setIdMissao(missao.getId());
        vinculo.setFuncao((String) comboFuncao.getSelectedItem());
        vinculo.setDataParticipacao(LocalDate.now());

        try {
            vinculoService.vincular(vinculo);
            JOptionPane.showMessageDialog(this, "Vínculo realizado com sucesso!");
            atualizarTabela();
            carregarCombos();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Regra de Negócio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (NinjaMissao nm : vinculoService.listar()) {
            modelo.addRow(new Object[]{
                    nm.getId(), nm.getNomeNinja(), nm.getTituloMissao(),
                    nm.getFuncao(), nm.getDataParticipacao().format(fmt)
            });
        }
    }
}