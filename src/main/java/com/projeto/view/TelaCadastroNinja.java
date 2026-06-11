package com.projeto.view;

import com.projeto.model.Ninja;
import com.projeto.service.NinjaService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

public class TelaCadastroNinja extends JFrame {

    private final NinjaService service = new NinjaService();

    private final JTextField campoNome = new JTextField();
    private final JTextField campoCla = new JTextField();
    private final JComboBox<String> comboVila =
            new JComboBox<>(new String[]{"Konoha", "Suna", "Kiri", "Iwa", "Kumo"});
    private final JComboBox<String> comboRank =
            new JComboBox<>(new String[]{"Genin", "Chunin", "Jounin", "Kage"});
    private final JComboBox<String> comboChakra =
            new JComboBox<>(new String[]{"Vento", "Raio", "Terra", "Agua", "Fogo", "Areia"});
    private final JComboBox<String> comboStatus =
            new JComboBox<>(new String[]{"Ativo", "Inativo"});

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Nome", "Vila", "Cla", "Rank", "Chakra", "Status"}, 0);
    private final JTable tabela = new JTable(modelo);

    private Integer idSelecionado = null;

    public TelaCadastroNinja() {
        setTitle("Cadastro de Ninjas");
        setSize(720, 480);
        setLocationRelativeTo(null);

        JPanel formulario = new JPanel(new GridLayout(7, 2, 8, 8));
        formulario.add(new JLabel("Nome:"));
        formulario.add(campoNome);
        formulario.add(new JLabel("Vila:"));
        formulario.add(comboVila);
        formulario.add(new JLabel("Cla:"));
        formulario.add(campoCla);
        formulario.add(new JLabel("Rank:"));
        formulario.add(comboRank);
        formulario.add(new JLabel("Natureza do Chakra:"));
        formulario.add(comboChakra);
        formulario.add(new JLabel("Status:"));
        formulario.add(comboStatus);

        JButton botaoSalvar = new JButton("Salvar");
        JButton botaoLimpar = new JButton("Limpar");
        formulario.add(botaoSalvar);
        formulario.add(botaoLimpar);

        JButton botaoExcluir = new JButton("Excluir selecionado");

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(e -> preencherSelecionado());

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(formulario, BorderLayout.CENTER);
        topo.add(botaoExcluir, BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        botaoSalvar.addActionListener(e -> salvar());
        botaoLimpar.addActionListener(e -> limpar());
        botaoExcluir.addActionListener(e -> excluir());

        atualizarTabela();
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
                JOptionPane.showMessageDialog(this, "Ninja cadastrado com sucesso.");
            } else {
                ninja.setId(idSelecionado);
                service.atualizar(ninja);
                JOptionPane.showMessageDialog(this, "Ninja atualizado com sucesso.");
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
            JOptionPane.showMessageDialog(this, "Selecione um ninja na tabela.");
            return;
        }
        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir este ninja?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                service.excluir(idSelecionado);
                limpar();
                atualizarTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Nao foi possivel excluir. Verifique se ele esta vinculado a missoes.",
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
        List<Ninja> ninjas = service.listar();
        for (Ninja n : ninjas) {
            modelo.addRow(new Object[]{
                    n.getId(), n.getNome(), n.getVila(), n.getCla(),
                    n.getRankNinja(), n.getNaturezaChakra(), n.getStatus()
            });
        }
    }
}
