package localBanco;

import localObjeto.Paciente;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PacienteBD {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<Paciente> lista = new ArrayList<>();
    public void cadastrarPaciente(Paciente objPaciente){
        String sql = "insert into atividades(leito, cuidador, paciente, atividade, alarme) values (?, ?, ?, ?, ?)";
        conn = new ConexaoBanco().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, objPaciente.getLeito());
            pstm.setString(2, objPaciente.getNome_cuidador());
            pstm.setString(3, objPaciente.getNome_paciente());
            pstm.setString(4, objPaciente.getDescricaoAtividade());
            //pstm.setString(4, String.valueOf(objPaciente.getHoraAtual()));
            pstm.setString(5, objPaciente.getAlarme());
            pstm.execute();
            pstm.close();
        } catch (Exception erro){
            JOptionPane.showMessageDialog(null, "PacienteBD cadastro" + erro);
        }
    }
    public ArrayList<Paciente> PesquisarPacientes(){
        String sql = "select * from atividades";
        conn = new ConexaoBanco().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()){
                Paciente objPaciente = new Paciente();
                objPaciente.setNum_id(rs.getInt("id"));
                objPaciente.setLeito(rs.getInt("leito"));
                objPaciente.setNome_cuidador(rs.getString("cuidador"));
                objPaciente.setNome_paciente(rs.getString("paciente"));
                objPaciente.setDescricaoAtividade(rs.getString("atividade"));
                //hora atual não vai para o banco
                objPaciente.setAlarme(rs.getString("ALARME"));
                lista.add(objPaciente);
            }
        }catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "pesquisar pacientes erro" + erro);
        }
        return lista;
    }
    public Paciente pesquisarPacientePorID(int id){
        String sql = "SELECT * FROM atividades WHERE id = ?";
        conn = new ConexaoBanco().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            if (rs.next()){
                Paciente objPaciente =  new Paciente();
                objPaciente.setNum_id(rs.getInt("id"));
                objPaciente.setLeito(rs.getInt("leito"));
                objPaciente.setNome_cuidador(rs.getString("cuidador"));
                objPaciente.setNome_paciente(rs.getString("paciente"));
                objPaciente.setDescricaoAtividade(rs.getString("atividade"));
                objPaciente.setAlarme(rs.getString("ALARME"));
                return objPaciente;
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro pesqusar por id BD" + erro);
        } finally {

        }
        return null;
    }
    public void alterarAtivdade(Paciente objPaciente){
        String sql = "update atividades set leito = ?, cuidador = ?, paciente = ?, atividade = ?, ALARME = ? WHERE id = ?";
        conn = new ConexaoBanco().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, objPaciente.getLeito());
            pstm.setString(2, objPaciente.getNome_cuidador());
            pstm.setString(3, objPaciente.getNome_paciente());
            pstm.setString(4, objPaciente.getDescricaoAtividade());
            pstm.setString(5, objPaciente.getAlarme());
            pstm.setInt(6, objPaciente.getNum_id());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ERRO no alterar do PacienteBD");

        }
    }
    public void excluirPorID(int id) {
        String sql = "DELETE FROM atividades WHERE id = ?";
        conn = new ConexaoBanco().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atividade excluída com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir atividade: " + erro);
        } finally {
            // Código para fechar a conexão com o banco de dados, se necessário
        }
    }
}
