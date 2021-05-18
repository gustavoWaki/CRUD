import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.concurrent.ExecutionException;

import bd.daos.Matriculas;
import bd.dbos.Matricula;
import com.mysql.jdbc.log.Log;

public class App {
    private JPanel panelMain;
    private JPanel PanelTop;
    private JPanel PanelLeft;
    private JPanel PanelRight;
    private JTextField txtRA;
    private JTextField txtNome;
    private JTextField txtCEP;
    private JTextField txtCPF;
    private JButton btnConsultar;
    private JPanel panelRdBtn;
    private JTextField txtRua;
    private JTextField txtCidade;
    private JTextField txtEstado;
    private JPanel panelEspacamento1;
    private JPanel panelBtn;
    private JLabel lbTitle;
    private JTextField txtRARecebido;
    private JTextField txtNomeRecebido;
    private JTextField txtCPFRecebido;
    private JRadioButton rdBtnDeletar;
    private JButton btnConfirmar;
    private JRadioButton rdBtnInserir;
    private JRadioButton rdBtnAtualizar;
    private JButton btnLimpar;
    private JPanel panelEspacamento2;
    private JPanel panelEspacamento3;
    private JTextField txtBairro;

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void infoCep(Matricula mat)
    {
        Logradouro log = (Logradouro) ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", String.valueOf(mat.getCep()));
        txtRua.setText(log.getLogradouro());
        txtBairro.setText(log.getBairro());
        txtCidade.setText(log.getCidade());
        txtEstado.setText(log.getEstado());
    }

    public App()
    {
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    if(!rdBtnInserir.isSelected() && !rdBtnAtualizar.isSelected() && !rdBtnDeletar.isSelected())
                        JOptionPane.showMessageDialog(null, "Selecione uma operacao antes de Confirmar!");
                    else if(!Matriculas.cadastrado(Integer.parseInt(txtRA.getText())) && !rdBtnInserir.isSelected())
                        JOptionPane.showMessageDialog(null, "O RA digitado nao esta cadastrado!");
                    else if(Matriculas.cadastrado(Integer.parseInt(txtRA.getText())) && rdBtnInserir.isSelected())
                        JOptionPane.showMessageDialog(null, "O RA digitado ja existe!");
                    else
                    {
                        Matricula mat = new Matricula(Integer.parseInt(txtRA.getText()), txtNome.getText(), Integer.parseInt(txtCEP.getText()), txtCPF.getText());

                        if (rdBtnInserir.isSelected())
                        {
                            Matriculas.incluir(mat); // inclui os dados digitados no BD

                            // Demonstra os dados digitados na tela
                            txtRARecebido.setText(String.valueOf(mat.getRa()));
                            txtNomeRecebido.setText(mat.getNome());
                            txtCPFRecebido.setText(mat.getCpf());
                            infoCep(mat);
                        }
                        else if (rdBtnAtualizar.isSelected())
                        {
                            Matriculas.alterar(mat); // altera os dados do BD

                            // Demonstra os dados digitados na tela
                            txtRARecebido.setText(String.valueOf(mat.getRa()));
                            txtNomeRecebido.setText(mat.getNome());
                            txtCPFRecebido.setText(mat.getCpf());
                            infoCep(mat);
                        }
                        else if(rdBtnDeletar.isSelected())
                        {
                            Matricula matDelete = new Matricula(Matriculas.getMatricula(Integer.parseInt(txtRARecebido.getText())));
                            Matriculas.excluir(Integer.parseInt(txtRARecebido.getText())); // Deleta os dados do BD

                            txtRARecebido.setText("");
                            txtNomeRecebido.setText("");
                            txtCPFRecebido.setText("");
                            txtRua.setText("");
                            txtBairro.setText("");
                            txtCidade.setText("");
                            txtEstado.setText("");
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
                    }
                }
                catch (Exception err)
                {
                    JOptionPane.showMessageDialog(null, "Dados invalidos");
                    err.printStackTrace();
                }
            }
        });
        rdBtnInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rdBtnAtualizar.setSelected(false);
                rdBtnDeletar.setSelected(false);

                //permitir entrada de dados
                txtNome.setEnabled(true);
                txtCEP.setEnabled(true);
                txtCPF.setEnabled(true);
            }
        });
        rdBtnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rdBtnInserir.setSelected(false);
                rdBtnDeletar.setSelected(false);

                try
                {
                    if(txtRA.getText().equals(""))
                    {
                        rdBtnAtualizar.setSelected(false);
                        JOptionPane.showMessageDialog(null, "Preencha o campo do RA antes de Atualizar!");
                    }
                    else if(!Matriculas.cadastrado(Integer.parseInt(txtRA.getText())))
                    {
                        rdBtnAtualizar.setSelected(false);
                        JOptionPane.showMessageDialog(null, "O RA digitado nao esta cadastrado");
                    }
                    else
                    {
                        Matricula mat = new Matricula(Matriculas.getMatricula(Integer.parseInt(txtRA.getText())));

                        txtNome.setText(mat.getNome());
                        txtCEP.setText(String.valueOf(mat.getCep()));
                        txtCPF.setText(mat.getCpf());
                        txtNome.setEnabled(true);
                        txtCEP.setEnabled(true);
                        txtCPF.setEnabled(true);

                        txtRARecebido.setText(String.valueOf(mat.getRa()));
                        txtNomeRecebido.setText(mat.getNome());
                        txtCPFRecebido.setText(mat.getCpf());
                        infoCep(mat);
                    }
                }
                catch(Exception err)
                {
                    err.printStackTrace();
                }
            }
        });

        rdBtnDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rdBtnInserir.setSelected(false);
                rdBtnAtualizar.setSelected(false);

                txtNome.setEnabled(false);
                txtCEP.setEnabled(false);
                txtCPF.setEnabled(false);
                txtNome.setText("");
                txtCEP.setText("");
                txtCPF.setText("");
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // limpa os campos
                txtRA.setText("");
                txtNome.setText("");
                txtCEP.setText("");
                txtCPF.setText("");
                txtRARecebido.setText("");
                txtNomeRecebido.setText("");
                txtCPFRecebido.setText("");
                txtRua.setText("");
                txtBairro.setText("");
                txtCidade.setText("");
                txtEstado.setText("");

                // Remove a seleção da operação
                rdBtnInserir.setSelected(false);
                rdBtnAtualizar.setSelected(false);
                rdBtnDeletar.setSelected(false);

                // Desabilita os campos
                txtNome.setEnabled(false);
                txtCEP.setEnabled(false);
                txtCPF.setEnabled(false);
            }
        });

        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    if(txtRA.getText().equals(""))
                        JOptionPane.showMessageDialog(null, "Preencha os campos antes de Consultar!");
                    else if(!Matriculas.cadastrado(Integer.parseInt(txtRA.getText())))
                        JOptionPane.showMessageDialog(null, "O RA digitado nao esta cadastrado!");
                    else
                    {
                        Matricula mat = new Matricula(Matriculas.getMatricula(Integer.parseInt(txtRA.getText())));

                        txtRARecebido.setText(String.valueOf(mat.getRa()));
                        txtNomeRecebido.setText(mat.getNome());
                        txtCPFRecebido.setText(mat.getCpf());
                        infoCep(mat);
                    }
                }
                catch (Exception err)
                {
                    JOptionPane.showMessageDialog(null, "Dados invalidos");
                    err.printStackTrace();
                }
            }
        });
        rdBtnInserir.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(rdBtnInserir.isSelected() == false && rdBtnAtualizar.isSelected())
                {
                    txtNome.setEnabled(false);
                    txtCEP.setEnabled(false);
                    txtCPF.setEnabled(false);
                    txtNome.setText("");
                    txtCEP.setText("");
                    txtCPF.setText("");
                }
            }
        });
    }
}
