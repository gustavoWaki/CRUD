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

    public App()
    {
        AppMethods app = new AppMethods();

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
                        if (rdBtnInserir.isSelected())
                        {
                            Matricula mat = new Matricula(Integer.parseInt(txtRA.getText()), txtNome.getText(), Integer.parseInt(txtCEP.getText()), txtCPF.getText());
                            Matriculas.incluir(mat); // inclui os dados digitados no BD

                            // Demonstra os dados digitados na tela
                            app.alterarTexto(txtRARecebido, String.valueOf(mat.getRa()),
                                    txtNomeRecebido, mat.getNome(),
                                    txtCPFRecebido, mat.getCpf());

                            app.infoCep(mat, txtRua, txtBairro, txtCidade, txtEstado);
                        }
                        else if (rdBtnAtualizar.isSelected())
                        {
                            Matricula mat = new Matricula(Integer.parseInt(txtRA.getText()), txtNome.getText(), Integer.parseInt(txtCEP.getText()), txtCPF.getText());
                            Matriculas.alterar(mat); // altera os dados do BD

                            // Demonstra os dados digitados na tela
                            app.alterarTexto(txtRARecebido, String.valueOf(mat.getRa()),
                                    txtNomeRecebido, mat.getNome(),
                                    txtCPFRecebido, mat.getCpf());

                            app.infoCep(mat, txtRua, txtBairro, txtCidade, txtEstado);
                        }
                        else if(rdBtnDeletar.isSelected())
                        {
                            JOptionPane.showMessageDialog(null, "vamo");
                            Matriculas.excluir(Integer.parseInt(txtRARecebido.getText())); // Deleta os dados do BD

                            app.limparCampos(txtRARecebido, txtNomeRecebido, txtCPFRecebido,
                                             txtRua, txtBairro, txtCidade, txtEstado);
                        }

                        app.desabilitarCampos(txtNome, txtCEP, txtCPF); // Desabilita os campos

                        app.limparCampos(txtRA, txtNome, txtCEP, txtCPF); // Limpa os campos de texto
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
                app.desabilitarRdBtn(rdBtnAtualizar, rdBtnDeletar);
                app.habilitarCampos(txtNome, txtCEP, txtCPF); // permitir entrada de dados
            }
        });
        rdBtnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.desabilitarRdBtn(rdBtnInserir, rdBtnDeletar);

                try
                {
                    if(txtRA.getText().equals(""))
                    {
                        app.desabilitarRdBtn(rdBtnAtualizar);
                        JOptionPane.showMessageDialog(null, "Preencha o campo do RA antes de Atualizar!");
                    }
                    else if(!Matriculas.cadastrado(Integer.parseInt(txtRA.getText())))
                    {
                        app.desabilitarRdBtn(rdBtnAtualizar);
                        JOptionPane.showMessageDialog(null, "O RA digitado nao esta cadastrado");
                    }
                    else
                    {
                        Matricula mat = new Matricula(Matriculas.getMatricula(Integer.parseInt(txtRA.getText())));

                        app.alterarTexto(txtNome, mat.getNome(), txtCEP, String.valueOf(mat.getCep()), txtCPF, mat.getCpf());
                        app.habilitarCampos(txtNome, txtCEP, txtCPF);

                        app.alterarTexto(txtRARecebido, String.valueOf(mat.getRa()), txtNomeRecebido,
                                         mat.getNome(), txtCPFRecebido, mat.getCpf());

                        app.infoCep(mat, txtRua, txtBairro, txtCidade, txtEstado);
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
                app.desabilitarRdBtn(rdBtnInserir, rdBtnAtualizar);
                app.limparCampos(txtNome, txtCEP, txtCPF);
                app.desabilitarCampos(txtNome, txtCEP, txtCPF);
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.limparCampos(txtRA, txtNome, txtCEP, txtCPF, txtRARecebido,
                                 txtNomeRecebido, txtCPFRecebido,
                                 txtRua, txtBairro, txtCidade, txtEstado);

                app.desabilitarRdBtn(rdBtnInserir, rdBtnAtualizar, rdBtnDeletar);
                app.desabilitarCampos(txtNome, txtCEP, txtCPF);
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

                        app.alterarTexto(txtRARecebido, String.valueOf(mat.getRa()), txtNomeRecebido, mat.getNome(), txtCPFRecebido, mat.getCpf());
                        app.infoCep(mat, txtRua, txtBairro, txtCidade, txtEstado);
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
                    app.desabilitarCampos(txtNome, txtCEP, txtCPF);
                    app. limparCampos(txtNome, txtCPF, txtCPF);
                }
            }
        });
    }
}
