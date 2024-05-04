package localObjeto;

import java.time.LocalDateTime;

public class Paciente {
    //public static int count = 1;
    private int num_id;
    private int leito;

    private String nome_cuidador;
    private String nome_paciente;
    private String descricaoAtividade;
    private LocalDateTime horaAtual;
    private String alarme;

    public int getNum_id(){return num_id;}
    public void setNum_id(int num_id){this.num_id = num_id;}

    public int getLeito(){return leito;}
    public void setLeito(int leito){this.leito = leito;}

    public String getNome_cuidador(){return this.nome_cuidador;}
    public void setNome_cuidador(String nome_cuidador){this.nome_cuidador = nome_cuidador;}

    public String getNome_paciente(){return  this.nome_paciente;}
    public void setNome_paciente(String nome_paciente){this.nome_paciente = nome_paciente;}

    public String getDescricaoAtividade(){return  this.descricaoAtividade;}
    public void setDescricaoAtividade(String descricaoAtividade){this.descricaoAtividade = descricaoAtividade;}

    public LocalDateTime getHoraAtual(){return  this.horaAtual;}
    public void setHoraAtual(LocalDateTime horaAtual){this.horaAtual = horaAtual;}

    public String getAlarme(){return this.alarme;}
    public void setAlarme(String alarme){this.alarme = alarme;}



}
