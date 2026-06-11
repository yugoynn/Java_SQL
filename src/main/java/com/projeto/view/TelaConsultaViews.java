package com.projeto.view;

import com.projeto.dao.ViewDAO;

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
import java.util.List;

public class TelaConsultaViews extends JFrame {

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
        setSize(820, 480);
        setLocationRelativeTo(null);

        JPanel topo = new JPanel();
        topo.add(new JLabel("Selecione a view:"));
        topo.add(comboView);
        JButton botaoConsultar = new JButton("Consultar");
        topo.add(botaoConsultar);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        botaoConsultar.addActionListener(e -> consultar());
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
                JOptionPane.showMessageDialog(this,
                        "A view nao retornou registros.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
