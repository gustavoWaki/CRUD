package bd.daos;

import java.sql.*;
import bd.dbos.Matricula;
import bd.BDMySQL;
import bd.core.*;

public class Matriculas
{
    public static boolean cadastrado (int ra) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM MATRICULA " +
                  "WHERE RA = ?";

            BDMySQL.COMANDO.prepareStatement (sql);

            BDMySQL.COMANDO.setInt (1, ra);

            MeuResultSet resultado = (MeuResultSet)BDMySQL.COMANDO.executeQuery ();

            retorno = resultado.first();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar matricula");
        }

        return retorno;
    }

    public static void incluir (Matricula matricula) throws Exception
    {
        if (matricula == null)
            throw new Exception ("Matricula nao fornecida");

        try
        {
            String sql;

            sql = "INSERT INTO MATRICULA " +
                  "(RA,NOME,CEP,CPF) " +
                  "VALUES " +
                  "(?,?,?,?)";

            BDMySQL.COMANDO.prepareStatement (sql);

            BDMySQL.COMANDO.setInt   (1, matricula.getRa ());
            BDMySQL.COMANDO.setString(2, matricula.getNome ());
            BDMySQL.COMANDO.setInt   (3, matricula.getCep ());
            BDMySQL.COMANDO.setString   (4, matricula.getCpf ());

            BDMySQL.COMANDO.executeUpdate ();
            BDMySQL.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
			BDMySQL.COMANDO.rollback();
            throw new Exception ("Erro ao inserir matricula");
        }
    }

    public static void excluir (int ra) throws Exception
    {
        if (!cadastrado (ra))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "DELETE FROM MATRICULA " +
                  "WHERE RA=?";

            BDMySQL.COMANDO.prepareStatement (sql);

            BDMySQL.COMANDO.setInt (1, ra);

            BDMySQL.COMANDO.executeUpdate ();
            BDMySQL.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
			BDMySQL.COMANDO.rollback();
            throw new Exception ("Erro ao excluir a matricula");
        }
    }

    public static void alterar (Matricula matricula) throws Exception
    {
        if (matricula == null)
            throw new Exception ("Matricula nao fornecida");

        if (!cadastrado (matricula.getRa()))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "UPDATE MATRICULA " +
                  "SET NOME=? ," +
                  "CEP=? ," +
                  "CPF=? " +
                  "WHERE RA=?";

            BDMySQL.COMANDO.prepareStatement (sql);

            BDMySQL.COMANDO.setString(1, matricula.getNome ());
            BDMySQL.COMANDO.setInt   (2, matricula.getCep ());
            BDMySQL.COMANDO.setString   (3, matricula.getCpf ());
            BDMySQL.COMANDO.setInt   (4, matricula.getRa ());


            BDMySQL.COMANDO.executeUpdate ();
            BDMySQL.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
			BDMySQL.COMANDO.rollback();
            throw new Exception ("Erro ao atualizar dados de matricula");
        }
    }

    public static Matricula getMatricula (int ra) throws Exception
    {
        Matricula matricula = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM MATRICULA " +
                  "WHERE RA = ?";

            BDMySQL.COMANDO.prepareStatement (sql);

            BDMySQL.COMANDO.setInt (1, ra);

            MeuResultSet resultado = (MeuResultSet)BDMySQL.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Nao cadastrado");

            matricula = new Matricula(resultado.getInt   ("RA"),
                                      resultado.getString("NOME"),
                                      resultado.getInt ("CEP"),
                                      resultado.getString("CPF"));
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar matricula");
        }

        return matricula;
    }
}
