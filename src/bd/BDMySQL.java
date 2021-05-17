package bd;

import bd.core.*;
import bd.daos.*;

public class BDMySQL
{
    public static final MeuPreparedStatement COMANDO;

    static
    {
    	MeuPreparedStatement comando = null;

    	try
        {
            comando = new MeuPreparedStatement (
                      "com.mysql.jdbc.Driver",
                      "jdbc:mysql://localhost:3306/bdescola",
                      "root", "pwdpwd");
        }
        catch (Exception erro)
        {
            System.err.println ("Problemas de conexao com o BD");
            System.exit(0); // aborta o programa
        }
        
        COMANDO = comando;
    }
}