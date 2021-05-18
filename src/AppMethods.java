import javax.swing.*;
import bd.daos.Matriculas;
import bd.dbos.Matricula;

public class AppMethods {
    public void limparCampos(JTextField... campoTxt)
    {
        for (JTextField campo : campoTxt) {
            campo.setText("");
        }
    }
    public void desabilitarCampos(JTextField... campoTxt)
    {
        for (JTextField campo : campoTxt) {
            campo.setEnabled(false);
        }
    }
    public void habilitarCampos(JTextField... campoTxt)
    {
        for (JTextField campo : campoTxt) {
            campo.setEnabled(true);
        }
    }
    public void infoCep(Matricula mat, JTextField rua, JTextField bairro, JTextField cidade, JTextField estado)
    {
        Logradouro log = (Logradouro) ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", String.valueOf(mat.getCep()));
        rua.setText(log.getLogradouro());
        bairro.setText(log.getBairro());
        cidade.setText(log.getCidade());
        estado.setText(log.getEstado());
    }
    public void alterarTexto(JTextField campo1, String txt1,
                             JTextField campo2, String txt2,
                             JTextField campo3, String txt3)
    {
        campo1.setText(txt1);
        campo2.setText(txt2);
        campo3.setText(txt3);
    }

    public void desabilitarRdBtn(JRadioButton... rdBtn)
    {
        for (JRadioButton btn : rdBtn) {
            btn.setSelected(false);
        }
    }

    public void habilitarRdBtn(JRadioButton... rdBtn)
    {
        for (JRadioButton btn : rdBtn) {
            btn.setSelected(true);
        }
    }
}
