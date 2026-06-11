package com.projeto.view;

import com.projeto.model.Missao;
import com.projeto.service.MissaoService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

public class TelaCadastroMissao extends JFrame {

    private final MissaoService service = new MissaoService();

    private final JTextField campoTitulo = new JTextField();
    private final JTextArea areaDescricao = new JTextArea(4, 20);
    private final JComboBox<String> comboRank =
            new JComboBox<>(new String[]{"D", "C", "B", "A", "S"});
    private final JComboBox<String> comboVila =
            new JComboBox<>(new String[]{"Konoha", "Suna", "Kiri", "Iwa", "Kumo"});
    private final JComboBox<String> comboStatus =
            new JComboBox<>(new String[]{"Aberta", "Em andamento", "Concluida", "Cancelada"});

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Titulo", "Rank", "Vila", "Status"}, 0);
    private final JTable tabela = new JTable(modelo);

    private Integer idSelecionado = null;

    public TelaCadastroMissao() {
        setTitle("Cadastro de Missoes");
        setSize(760, 520);
        setLocationRelativeTo(null);

        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int linha = 0;
        adicionarCampo(formulario, gbc, "Titulo:", campoTitulo, linha++);

        gbc.gridx = 0;
        gbc.gridy = linha;
        formulario.add(new JLabel("Descricao:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        areaDescricao.setLineWrap(true);
        areaDescricao.setWrapStyleWord(true);
        formulario.add(new JScrollPane(areaDescricao), gbc);
        linha++;

        adicionarCampo(formulario, gbc, "Rank:", comboRank, linha++);
        adicionarCampo(formulario, gbc, "Vila de Origem:", comboVila, linha++);
        adicionarCampo(formulario, gbc, "Status:", comboStatus, linha++);

        JPanel botoes = new JPanel();
        JButton botaoSalvar = new JButton("Salvar");
        JButton botaoLimpar = new JButton("Limpar");
        JButton botaoExcluir = new JButton("Excluir");
        botoes.add(botaoSalvar);
        botoes.add(botaoLimpar);
        botoes.add(botaoExcluir);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(e -> preencherSelecionado());

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(formulario, BorderLayout.CENTER);
        topo.add(botoes, BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        botaoSalvar.addActionListener(e -> salvar());
        botaoLimpar.addActionListener(e -> limpar());
        botaoExcluir.addActionListener(e -> excluir());

        atualizarTabela();
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc,
                                String rotulo, java.awt.Component campo, int linha) {
        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.weightx = 0;
        painel.add(new JLabel(rotulo), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        painel.add(campo, gbc);
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
                JOptionPane.showMessageDialog(this, "Missao cadastrada com sucesso.");
            } else {
                missao.setId(idSelecionado);
                service.atualizar(missao);
                JOptionPane.showMessageDialog(this, "Missao atualizada com sucesso.");
            }
            limpar();
            atualizarTabela();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Atencao", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma missao na tabela.");
            return;
        }
        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir esta missao?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                service.excluir(idSelecionado);
                limpar();
                atualizarTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Nao foi possivel excluir. Verifique se ela possui ninjas vinculados.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void preencherSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            return;
        }
        idSelecionado = (Integer) modelo.getValueAt(linha, 0);
        campoTitulo.setText((String) modelo.getValueAt(linha, 1));
        comboRank.setSelectedItem(modelo.getValueAt(linha, 2));
        comboVila.setSelectedItem(modelo.getValueAt(linha, 3));
        comboStatus.setSelectedItem(modelo.getValueAt(linha, 4));
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
        List<Missao> missoes = service.listar();
        for (Missao m : missoes) {
            modelo.addRow(new Object[]{
                    m.getId(), m.getTitulo(), m.getRankMissao(),
                    m.getVilaOrigem(), m.getStatus()
            });
        }
    }
}
