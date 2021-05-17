import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import bd.daos.Matriculas;
import bd.dbos.Matricula;

public class App {
    private JPanel panelMain;
    private JPanel PanelTop;
    private JPanel PanelLeft;
    private JPanel PanelRight;
    private JTextField txtRA;
    private JTextField txtNome;
    private JTextField txtCEP;
    private JTextField txtCPF;
    private JButton btnInserir;
    private JButton btnAtualizar;
    private JButton btnConsultar;
    private JPanel panelButton;
    private JButton btnDeletar;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JPanel panelEspacamento1;
    private JPanel panelEspacamento2;
    private JLabel lbTitle;
    private JTextField txtRARecebido;
    private JTextField txtNomeRecebido;
    private JTextField txtCPFRecebido;
    private JButton btnConfirmar;

    private String operacaoEscolhida;

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public App()
    {
        btnInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtRA.setEnabled(true);
                txtNome.setEnabled(true);
                txtCEP.setEnabled(true);
                txtCPF.setEnabled(true);

                operacaoEscolhida = btnInserir.getText();
            }
        });

        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(txtRA.getText().equals(""))
                    JOptionPane.showMessageDialog(null, "Preencha o campo do RA antes de Atualizar!");
                else
                {
                    try
                    {
                        Matricula mat = new Matricula(Matriculas.getMatricula(Integer.parseInt(txtRA.getText())));

                        // Coloca na tela os dados do BD
                        txtRA.setText(String.valueOf(mat.getRa()));
                        txtNome.setText(mat.getNome());
                        txtCEP.setText(String.valueOf(mat.getCep()));
                        txtCPF.setText(mat.getCpf());

                        // Libera os campos para digitação
                        txtNome.setEnabled(true);
                        txtCEP.setEnabled(true);
                        txtCPF.setEnabled(true);
                        operacaoEscolhida = btnAtualizar.getText();
                    }
                    catch (Exception err)
                    {
                        JOptionPane.showMessageDialog(null, "RA invalido");
                        err.printStackTrace();
                    }
                }
            }
        });

        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtRA.getText().equals(""))
                    JOptionPane.showMessageDialog(null, "Preencha o campo do RA antes de Consultar!");
                operacaoEscolhida = btnConsultar.getText();
            }
        });

        btnDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtRA.getText().equals(""))
                    JOptionPane.showMessageDialog(null, "Preencha o campo do RA antes de Deletar!");
                else
                {
                    try
                    {
                        Matricula mat = new Matricula(Matriculas.getMatricula(Integer.parseInt(txtRA.getText())));

                        // Coloca na tela os dados do BD
                        txtRARecebido.setText(String.valueOf(mat.getRa()));
                        txtNomeRecebido.setText(mat.getNome());
                        txtCPFRecebido.setText(mat.getCpf());

                        // Libera os campos para digitação
                        txtRA.setText("");
                        operacaoEscolhida = btnDeletar.getText();
                    }
                    catch (Exception err)
                    {
                        JOptionPane.showMessageDialog(null, "RA invalido");
                        err.printStackTrace();
                    }
                }
            }
        });

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    if (operacaoEscolhida == "Inserir")
                    {
                        Matricula mat = new Matricula(Integer.parseInt(txtRA.getText()), txtNome.getText(), Integer.parseInt(txtCEP.getText()), txtCPF.getText());
                        Matriculas.incluir(mat); // inclui os dados digitados no BD

                        // Demonstra os dados digitados na tela
                        txtRARecebido.setText(String.valueOf(mat.getRa()));
                        txtNomeRecebido.setText(mat.getNome());
                        txtCPFRecebido.setText(mat.getCpf());
                    }
                    else if (operacaoEscolhida == "Atualizar")
                    {
                        Matricula mat = new Matricula(Integer.parseInt(txtRA.getText()), txtNome.getText(), Integer.parseInt(txtCEP.getText()), txtCPF.getText());
                        Matriculas.alterar(mat); // altera os dados do BD

                        // Demonstra os dados digitados na tela
                        txtRARecebido.setText(String.valueOf(mat.getRa()));
                        txtNomeRecebido.setText(mat.getNome());
                        txtCPFRecebido.setText(mat.getCpf());
                    }
                    else if (operacaoEscolhida == "Consultar")
                    {
                        System.out.println("tefadsdasd");
                        Matricula mat = new Matricula(Matriculas.getMatricula(Integer.parseInt(txtRA.getText())));

                        // Demonstra os dados digitados na tela
                        txtRARecebido.setText(String.valueOf(mat.getRa()));
                        txtNomeRecebido.setText(mat.getNome());
                        txtCPFRecebido.setText(mat.getCpf());
                    }
                    else if(operacaoEscolhida == "Deletar")
                    {
                        Matricula mat = new Matricula(Matriculas.getMatricula(Integer.parseInt(txtRARecebido.getText())));
                        Matriculas.excluir(Integer.parseInt(txtRARecebido.getText())); // Deleta os dados do BD
                    }
                }
                catch (Exception err)
                {
                    JOptionPane.showMessageDialog(null, "Dados invalidos");
                    err.printStackTrace();
                }

                // Deixa os campos inalteráveis
                txtNome.setEnabled(false);
                txtCEP.setEnabled(false);
                txtCPF.setEnabled(false);

                // Limpa os campos de texto
                txtRA.setText("");
                txtNome.setText("");
                txtCEP.setText("");
                txtCPF.setText("");
                txtRARecebido.setText("");
                txtNomeRecebido.setText("");
                txtCPFRecebido.setText("");
            }
        });
    }
}
