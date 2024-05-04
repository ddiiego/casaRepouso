package localObjeto;

public class Funcionario {
    //public static int count = 1;
    private int id_funcionario;
    private String nome_funcionario;
    private String senha_funcionario;

    public int getId_funcionario(){return id_funcionario;}
    public void setId_funcionario(int id_funcionario) {this.id_funcionario = id_funcionario;}

    public String getNome_funcionario(){return this.nome_funcionario;}
    public void setNome_funcionario(String nome_funcionario){this.nome_funcionario = nome_funcionario;}

    public String getSenha_funcionario(){return senha_funcionario;}
    public void setSenha_funcionario(String senha_funcionario){this.senha_funcionario = senha_funcionario;}


}
