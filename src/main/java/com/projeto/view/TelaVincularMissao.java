package com.projeto.view;

import com.projeto.model.Missao;
import com.projeto.model.Ninja;
import com.projeto.model.NinjaMissao;
import com.projeto.service.MissaoService;
import com.projeto.service.NinjaMissaoService;
import com.projeto.service.NinjaService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

public class TelaVincularMissao extends JFrame {

    private final NinjaService ninjaService = new NinjaService();
    private final MissaoService missaoService = new MissaoService();
    private final NinjaMissaoService vinculoService = new NinjaMissaoService();

    private final JComboBox<Ninja> comboNinja = new JComboBox<>();
    private final JComboBox<Missao> comboMissao = new JComboBox<>();
    private final JComboBox<String> comboFuncao = new JComboBox<>(
            new String[]{"Lider", "Ataque", "Suporte", "Sensorial", "Medico", "Defesa"});

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Ninja", "Missao", "Funcao", "Data"}, 0);
    private final JTable tabela = new JTable(modelo);

    public TelaVincularMissao() {
        setTitle("Vincular Ninja a Missao");
        setSize(720, 460);
        setLocationRelativeTo(null);

        JPanel formulario = new JPanel(new GridLayout(4, 2, 8, 8));
        formulario.add(new JLabel("Ninja:"));
        formulario.add(comboNinja);
        formulario.add(new JLabel("Missao:"));
        formulario.add(comboMissao);
        formulario.add(new JLabel("Funcao:"));
        formulario.add(comboFuncao);

        JButton botaoVincular = new JButton("Vincular");
        formulario.add(new JLabel());
        formulario.add(botaoVincular);

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        botaoVincular.addActionListener(e -> vincular());

        carregarCombos();
        atualizarTabela();
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
            JOptionPane.showMessageDialog(this,
                    "Cadastre ninjas e missoes antes de criar um vinculo.");
            return;
        }

        NinjaMissao vinculo = new NinjaMissao();
        vinculo.setIdNinja(ninja.getId());
        vinculo.setIdMissao(missao.getId());
        vinculo.setFuncao((String) comboFuncao.getSelectedItem());
        vinculo.setDataParticipacao(LocalDate.now());

        try {
            vinculoService.vincular(vinculo);
            JOptionPane.showMessageDialog(this, "Vinculo realizado com sucesso.");
            atualizarTabela();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Regra de negocio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        List<NinjaMissao> vinculos = vinculoService.listar();
        for (NinjaMissao nm : vinculos) {
            modelo.addRow(new Object[]{
                    nm.getId(), nm.getNomeNinja(), nm.getTituloMissao(),
                    nm.getFuncao(), nm.getDataParticipacao()
            });
        }
    }
}
