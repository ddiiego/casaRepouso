package tela;

import localBanco.FuncionarioBD;
import localBanco.PacienteBD;
import localObjeto.Funcionario;
import localObjeto.Paciente;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Tela {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args){
        logar();
    }

    private static void menu(){
        System.out.println("|---------------------Bem Vindo----------------|");
        System.out.println("|----------------------------------------------|");
        System.out.println("|------------------------MENU------------------|");
        System.out.println("|   Opção 1 - Cadastrar     Atividade          |");
        System.out.println("|   Opção 2 - Listar        Atividades         |");
        System.out.println("|   Opção 3 - Pesquisar     Atividade por ID   |");
        System.out.println("|   Opção 4 - Alterar       Atividade por ID   |");
        System.out.println("|   Opção 5 - Excluir       Atividade por ID   |");
        System.out.println("|   Opção 6 - Sair                             |");
        System.out.println("|----------------------------------------------|");
        System.out.println("|----------------------------------------------|");
        System.out.println("|   Opção 9 - alterar Login e senha de Usuário |");
        System.out.println("------------------------------------------------");

        int opcao = input.nextInt();
        switch (opcao){
            case 1:
                cadastrar();
                break;
            case 2:
                listar();
                break;
            case 3:
                pesquisarPorID();
                break;
            case 4:
                alterarAtivdadePorID();
                break;
            case 9:
                alterarUsuario();
                break;
            case 5:
                excluirAtivdadePorID();
                break;
            case 6:
                System.out.println("|-----------------Volte Sempre!----------------|");
                System.exit(0);
            default:
                System.out.println("|---------------Opção Incalida!----------------|");
                menu();
                break;
        }
    }
    private static void logar(){
        try{
            System.out.println("|-----------------Digite seu ID: --------------|");
            int id_login = input.nextInt();

            System.out.println("|---------------------Login: ------------------|");
            input.nextLine(); // Limpar o buffer do teclado
            String login = input.nextLine();

            System.out.println("|---------------------Senha: ------------------|");
            String senha = input.nextLine();

            Funcionario objFuncionario = new Funcionario();
            objFuncionario.setId_funcionario(id_login);
            objFuncionario.setNome_funcionario(login);
            objFuncionario.setSenha_funcionario(senha);

            FuncionarioBD objfuncionarioBD = new FuncionarioBD();
            ResultSet rsfuncionarioBD = objfuncionarioBD.autenticacaoLogin(objFuncionario);
            if (rsfuncionarioBD.next()){
                System.out.println("Login Válido!!");
                JOptionPane.showMessageDialog(null, "Login válido!");
                alarme();
                menu();
            }else {
                System.out.println("Login ou senha Inválida!\nTente novamente.");
                JOptionPane.showMessageDialog(null, "Login ou senha Inválida!\nTente novamente.");
                logar();
            }

        }catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "erro tela" + erro);
        }
    }
    private static void cadastrar(){
        //num_id; automatico
        System.out.println("|---------Digite o número do leito: -----------|");
        int leito = input.nextInt();

        System.out.println("|--------Digite o nome do Cuidador: -----------|");
        input.nextLine(); // Limpar o buffer do teclado
        String nomeCuidador = input.nextLine();

        System.out.println("|---------Digite o nome do Paciente: ----------|");
        String nomePaciente = input.nextLine();

        System.out.println("|----Descreva a atividade à exercer: ----------|");
        String descricaoAtividade = input.nextLine();

        LocalDateTime dataHoraAtual = LocalDateTime.now();

        System.out.println("|-Digite a data e hora do alarme (dd/MM/yyyy HH:mm)-|");
        //input.nextLine(); // Limpar o buffer do teclado
        String alarmeStr = input.nextLine();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        LocalDateTime alarme = LocalDateTime.parse(alarmeStr, dateFormatter);

        System.out.println("|------Data e Hora atual: " + dataHoraAtual.format(dateFormatter));
        System.out.println("|------Data e Hora do alarme: " + alarme.format(dateFormatter));


        Paciente objPaciente = new Paciente();
        objPaciente.setLeito(leito);
        objPaciente.setNome_cuidador(nomeCuidador);
        objPaciente.setNome_paciente(nomePaciente);
        objPaciente.setDescricaoAtividade(descricaoAtividade);
        objPaciente.setHoraAtual(dataHoraAtual);
        objPaciente.setAlarme(alarmeStr);

        PacienteBD objPacienteBD = new PacienteBD();
        objPacienteBD.cadastrarPaciente(objPaciente);

        alarme();
        menu();
    }
    private static void listar(){
        try {
            PacienteBD objPaciente = new PacienteBD();
            ArrayList<Paciente> lista = objPaciente.PesquisarPacientes();
            for (Paciente paciente : lista){
                System.out.println(
                        "ID: " + paciente.getNum_id() +
                        ", Leito: " + paciente.getLeito() +
                        ", Cuidador: " + paciente.getNome_cuidador() +
                        ", Paciente: " + paciente.getNome_paciente() +
                        ", Atividade: " + paciente.getDescricaoAtividade() +
                        ", Data e Hora do ALARME: " + paciente.getAlarme()+"\n"
                );
            }
        }catch (Exception erro){
            JOptionPane.showMessageDialog(null, " listar atividades " + erro);
        }
        menu();
    }
    private static void alarme(){

                PacienteBD objPaciente = new PacienteBD();
                ArrayList<Paciente> lista = objPaciente.PesquisarPacientes();
                LocalDateTime dataHoraAtual = LocalDateTime.now();

                for (Paciente paciente : lista) {

                    //int leitoo = paciente.getLeito();
                    //String cuidadoor = paciente.getNome_cuidador();
                    //String pacientee = paciente.getNome_paciente();
                    //String atividaade = paciente.getDescricaoAtividade();
                    String alarmeStr = paciente.getAlarme();

                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime alarmee = LocalDateTime.parse(alarmeStr, dateFormatter);


                    if(dataHoraAtual.isBefore(alarmee) || alarmee.isEqual(dataHoraAtual)){
                        long delay = java.time.Duration.between(dataHoraAtual, alarmee).toMillis();
                        if (delay < 0) {
                            delay = 0;
                        }
                        int novo1 = paciente.getNum_id();
                        int leitoo1 = paciente.getLeito();
                        String cuidadoor1 = paciente.getNome_cuidador();
                        String pacientee1 = paciente.getNome_paciente();
                        String atividaade1 = paciente.getDescricaoAtividade();
                        String aalarme = paciente.getAlarme();
                        TimerTask task = new TimerTask() {
                            public void run() {
                                Toolkit.getDefaultToolkit().beep();
                                int novo2 = novo1;
                                int leitoo2 = leitoo1;
                                String cuidadoor2 = cuidadoor1;
                                String pacientee2 = pacientee1;
                                String ativiadade = atividaade1;
                                String aalarme1 =  aalarme;
                                //LocalDateTime alarmee1 = alarmee;
                                System.out.println("|------------------------------------------------|");
                                System.out.println("|--------------------ATENÇÃO!!-------------------|");
                                System.out.println("|--!!ALARME!!------------------------------------|");
                                System.out.println("|    *  ID: " + novo2 + "                         ");
                                System.out.println("|    *  Leito: " + leitoo2 + "                    ");
                                System.out.println("|    *  Cuidador: " + cuidadoor2 + "              ");
                                System.out.println("|    *  Paciente: " + pacientee2 + "              ");
                                System.out.println("|    *  Atividade: " + ativiadade + "             ");
                                System.out.println("|    *  Alarme: " + aalarme1 + "                  ");
                                System.out.println("|------------------------------------------------|");
                                System.out.println("|------------------------------------------------|");
                            }
                        };

                        java.util.Timer timer = new Timer();
                        timer.schedule(task, delay);
                    }
                }


    }
    private static void pesquisarPorID(){
        int idPaciente;
        System.out.println("|-------------Digite o ID: ---------------------|");
        idPaciente = input.nextInt();

        PacienteBD objPacienteBD = new PacienteBD();
        Paciente objPaciente = objPacienteBD.pesquisarPacientePorID(idPaciente);

        if(objPaciente != null){
            System.out.println("|-----------------AGENDAMENTO------------------|");
            System.out.println("|---------Digite o nome do Paciente: ----------|");
            System.out.println("|------ID do Paciente: " + objPaciente.getNum_id());
            System.out.println("|------Leito do Paciente: " + objPaciente.getLeito());
            System.out.println("|------Cuidador: " + objPaciente.getNome_cuidador());
            System.out.println("|------Paciente: " + objPaciente.getNome_paciente());
            System.out.println("|------Atividades: " + objPaciente.getDescricaoAtividade());
            //System.out.println("Data e Hora Atual: " + objPaciente.getHoraAtual());
            System.out.println("|------Data e Hora do ALARME: " + objPaciente.getAlarme());
        }else {
            System.out.println("Paciente não encontrado.\nTente novamente!");
            pesquisarPorID();
        }
        menu();
    }
    private static void alterarAtivdadePorID(){
        try {
            int idPaciente;
            System.out.println("|-------------Digite o ID: ---------------------|");
            idPaciente = input.nextInt();

            PacienteBD objPacineteBD = new PacienteBD();
            Paciente objPaciente = objPacineteBD.pesquisarPacientePorID(idPaciente);
            if (objPaciente != null){
                System.out.println("|---------------***AGENDAMENTO**---------------|");
                System.out.println("|---------ID do Paciente: " + objPaciente.getNum_id());
                System.out.println("|---------Leito do Paciente: " + objPaciente.getLeito());
                System.out.println("|---------Cuidador: " + objPaciente.getNome_cuidador());
                System.out.println("|---------Paciente: " + objPaciente.getNome_paciente());
                System.out.println("|---------Atividades: " + objPaciente.getDescricaoAtividade());
                //System.out.println("Data e Hora Atual: " + objPaciente.getHoraAtual());
                System.out.println("|---------Data e Hora do ALARME: " + objPaciente.getAlarme());
            }else {
                System.out.println("Paciente não encontrado.\nTente novamente!");
                alterarAtivdadePorID();
            }

            int novoLeito;
            System.out.println("|-------------Digite novo leito: --------------|");
            novoLeito = input.nextInt();

            String novoCuidador;
            System.out.println("|-------------Digite novo Cuidador: -----------|");
            input.nextLine(); // Limpar o buffer do teclado
            novoCuidador = input.nextLine();

            String novoPaciente;
            System.out.println("|-------------Digite novo Paciente: -----------|");
            novoPaciente = input.nextLine();

            String novaAtividade;
            System.out.println("|-------------Digite nova Atividade: -----------|");
            novaAtividade = input.nextLine();

            String novoAlarme;
            System.out.println("|----Digite novo Alarme (dd/MM/yyyy HH:mm): ----|");
            //input.nextLine(); // Limpar o buffer do teclado
            novoAlarme = input.nextLine();


            Paciente objPacientes = new Paciente();
            objPacientes.setLeito(novoLeito);
            objPacientes.setNome_cuidador(novoCuidador);
            objPacientes.setNome_paciente(novoPaciente);
            objPacientes.setDescricaoAtividade(novaAtividade);
            objPacientes.setAlarme(novoAlarme);
            objPacientes.setNum_id(idPaciente);

            objPacineteBD.alterarAtivdade(objPacientes);

            JOptionPane.showMessageDialog(null, "|-------------Alterado com Sucesso! ------------|");
            alarme();
            menu();

        }catch (Exception erro){
            JOptionPane.showMessageDialog(null, "alterar por id - erro" + erro);
        }

    }
    private static void alterarUsuario(){
        try {
            //--
            try {
                System.out.println("|-------------Digite seu ID: -------------------|");
                int id_login = input.nextInt();

                System.out.println("|-------------Digite Login: --------------------|");
                input.nextLine(); // Limpar o buffer do teclado
                String login = input.nextLine();

                System.out.println("|-------------Digite Senha: --------------------|");
                String senha = input.nextLine();

                Funcionario objFuncionario = new Funcionario();
                objFuncionario.setId_funcionario(id_login);
                objFuncionario.setNome_funcionario(login);
                objFuncionario.setSenha_funcionario(senha);

                FuncionarioBD objfuncionarioBD = new FuncionarioBD();
                ResultSet rsfuncionarioBD = objfuncionarioBD.autenticacaoLogin(objFuncionario);
                if (rsfuncionarioBD.next()) {
                    System.out.println("|-------------Login Válido! --------------------|");
                    JOptionPane.showMessageDialog(null, "Login válido!");
                } else {
                    System.out.println("|-------------Login ou senha invalido! ---------|\nTente novamente.");
                    JOptionPane.showMessageDialog(null, "Login ou senha Inválida!\nTente novamente.");
                    alterarUsuario();
                }
            }catch (Exception erro){
                System.out.println("!!!Erro login, mudança de usuário!!!");
            }
            //--


            int idUsuario;
            System.out.println("|-------------Digite o ID: ---------------------|");
            idUsuario = input.nextInt();

            FuncionarioBD objFuncionarioBD = new FuncionarioBD();
            Funcionario objFuncionario = objFuncionarioBD.pesquisarUsuarioPorID(idUsuario);
            if (objFuncionario != null) {
                System.out.println("|-----------------***USUÁRIO***----------------|");
                System.out.println("|---------ID do Funcionario: " + objFuncionario.getId_funcionario());
                System.out.println("|---------LOGIN do Funcionario: " + objFuncionario.getNome_funcionario());
                System.out.println("|---------SENHA do Funcionario: " + objFuncionario.getSenha_funcionario());
            } else {
                System.out.println("Funcionario não encontrado.\nTente novamente!");
                alterarUsuario();
            }

            String novoLogin;
            System.out.println("|-------------Digite novo Login: ---------------|");
            input.nextLine(); // Limpar o buffer do teclado
            novoLogin = input.nextLine();

            String novaSenha;
            System.out.println("|-------------Digite nova Senha: ---------------|");
            novaSenha = input.nextLine();

            Funcionario objfuncionario  = new Funcionario();
            objfuncionario.setNome_funcionario(novoLogin);
            objfuncionario.setSenha_funcionario(novaSenha);
            objfuncionario.setId_funcionario(idUsuario);
            objFuncionarioBD.alterarUsuario(objfuncionario);

            System.out.println("|----------USUÁRIO ALTERADO COM SUCESSO! -------|");
            menu();

        }catch (Exception erro){
            System.out.println("|-------------USUÁRIO NÃO ALTERADO ---------------------|");
            System.out.println("|----------------TENTE NOVAMENTE -----------------------|");
        }
    }
    private static void excluirAtivdadePorID(){
        try {
            int idPaciente;
            System.out.println("|-------------Digite o ID: ---------------------|");
            idPaciente = input.nextInt();

            PacienteBD objPacineteBD = new PacienteBD();
            Paciente objPaciente = objPacineteBD.pesquisarPacientePorID(idPaciente);
            if (objPaciente != null){
                System.out.println("|---------------***AGENDAMENTO**---------------|");
                System.out.println("|---------ID do Paciente: " + objPaciente.getNum_id());
                System.out.println("|---------Leito do Paciente: " + objPaciente.getLeito());
                System.out.println("|---------Cuidador: " + objPaciente.getNome_cuidador());
                System.out.println("|---------Paciente: " + objPaciente.getNome_paciente());
                System.out.println("|---------Atividades: " + objPaciente.getDescricaoAtividade());
                //System.out.println("Data e Hora Atual: " + objPaciente.getHoraAtual());
                System.out.println("|---------Data e Hora do ALARME: " + objPaciente.getAlarme());
            }else {
                System.out.println("Paciente não encontrado.\nTente novamente!");
                alterarAtivdadePorID();
            }
            int confirmacao;
            System.out.println("|-------------Confirme digitando 1 ------------|");
            System.out.println("|----Cancelar digite qualquer outro número ----|");
            confirmacao = input.nextInt();
            if (confirmacao == 1){
                Paciente objPacientes = new Paciente();
                objPacientes.setNum_id(idPaciente);

                objPacineteBD.excluirPorID(idPaciente);
                JOptionPane.showMessageDialog(null, "|-------------Exclido com Sucesso! -------------|");
                alarme();
                menu();
            }else {
                System.out.println("|----------------CANCELADO!----------------|");
                alarme();
                menu();
            }


        }catch (Exception erro){
            JOptionPane.showMessageDialog(null, "alterar por id - erro" + erro);
        }

    }


}
