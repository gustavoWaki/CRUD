package bd.dbos;

import java.math.BigInteger;

public class Matricula implements Cloneable
{
    private int ra, cep;
    private String nome, cpf;

    public void setRa (int ra) throws Exception
    {
        String raString = Integer.toString(ra);

        if (raString.length() != 5)
            throw new Exception ("RA inválido");

        this.ra = ra;
    }

    public void setNome (String nome) throws Exception
    {
        if (nome==null || nome.equals(""))
            throw new Exception ("Nome não fornecido");

        this.nome = nome;
    }

    public void setCep (int cep) throws Exception
    {
        String cepString = Integer.toString(cep);

        if (cepString.length() != 8)
            throw new Exception ("CEP inválido");

        this.cep = cep;
    }

    public void setCpf (String cpf) throws Exception
    {
        if (cpf.length() != 11)
            throw new Exception ("CPF inválido");


        this.cpf = cpf;
    }

    public int getRa() { return ra; }

    public int getCep() { return cep; }

    public String getCpf() { return cpf; }

    public String getNome ()
    {
        return this.nome;
    }

    public Matricula(int ra, String nome, int cep, String cpf) throws Exception
    {
        this.setRa(ra);
        this.setNome(nome);
        this.setCep(cep);
        this.setCpf(cpf);
    }

    public String toString ()
    {
        String ret="";

        ret+="RA:    "+this.ra  +"\n";
        ret+="Nome:  "+this.nome + "\n";
        ret+="CEP:   "+this.cep + "\n";
        ret+="CPF:   "+this.cpf + "\n";

        return ret;
    }

    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (!(obj instanceof Matricula))
            return false;

        Matricula mat = (Matricula)obj;

        if (this.ra != mat.ra)
            return false;

        if (this.nome != mat.nome)
            return false;

        if (this.cep != mat.cep)
            return false;

        if (this.cpf != mat.cpf)
            return false;

        return true;
    }

    public int hashCode ()
    {
        int ret=666;

        ret = 7*ret + new Integer(this.ra).hashCode();
        ret = 7*ret + this.nome.hashCode();
        ret = 7*ret + new Integer(this.cep).hashCode();
        ret = 7*ret + this.cpf.hashCode();

        return ret;
    }


    public Matricula(Matricula modelo) throws Exception
    {
        this.ra = modelo.ra;
        this.nome   = modelo.nome;
        this.cep = modelo.cep;
        this.cpf = modelo.cpf;
    }

    public Object clone ()
    {
        Matricula ret=null;

        try
        {
            ret = new Matricula(this);
        }
        catch (Exception erro)
        {}

        return ret;
    }
}