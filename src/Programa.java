import bd.daos.Matriculas;
import bd.dbos.Matricula;

import javax.swing.*;
import java.math.BigInteger;

public class Programa
{
    public static void main (String[] args) throws Exception
    {
        Matricula mat = new Matricula(20128, "Daniel", 13081030, "23611420824");
        Matriculas.incluir(mat);
    }
}
