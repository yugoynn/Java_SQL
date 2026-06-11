package com.projeto.view;

import com.projeto.model.Totalizador;
import com.projeto.service.TotalizadorService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaTotalizadores extends JFrame {

    private final TotalizadorService service = new TotalizadorService();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Descricao", "Quantidade", "Gerado em"}, 0);
    private final JTable tabela = new JTable(modelo);

    private final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TelaTotalizadores() {
        setTitle("Totalizadores");
        setSize(640, 460);
        setLocationRelativeTo(null);

        JButton botaoGerar = new JButton("Gerar totalizadores");
        botaoGerar.addActionListener(e -> gerar());

        add(botaoGerar, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
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
            JOptionPane.showMessageDialog(this,
                    "Totalizadores gerados e salvos no banco.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
