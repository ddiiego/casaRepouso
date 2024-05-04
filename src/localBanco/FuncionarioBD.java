package localBanco;

import localObjeto.Funcionario;
import localObjeto.Paciente;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioBD {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    public ResultSet autenticacaoLogin(Funcionario funcionario){
        conn = new ConexaoBanco().conectaBD();

        try {
            String sql = "select * from usuario where id = ? and login = ? and senha = ?";
            PreparedStatement pstm = conn .prepareStatement(sql);
            pstm.setInt(1, funcionario.getId_funcionario());
            pstm.setString(2, funcionario.getNome_funcionario());
            pstm.setString(3, funcionario.getSenha_funcionario());

            ResultSet rs = pstm.executeQuery();
            return rs;
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "FuncionarioBD: " + erro);
            return null;
        }
    }
    public Funcionario pesquisarUsuarioPorID(int id){
        String sql = "SELECT * FROM usuario WHERE id = ?";
        conn = new ConexaoBanco().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            if (rs.next()){
                Funcionario objFuncionario  = new Funcionario();
                objFuncionario.setId_funcionario(rs.getInt("id"));
                objFuncionario.setNome_funcionario(rs.getString("login"));
                objFuncionario.setSenha_funcionario(rs.getString("senha"));

                return objFuncionario;
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro pesqusar por id BD" + erro);
        } finally {

        }
        return null;
    }
    public void alterarUsuario(Funcionario objFuncionario){
        String sql = "update usuario set login = ?, senha = ? WHERE id = ?";
        conn = new ConexaoBanco().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objFuncionario.getNome_funcionario());
            pstm.setString(2, objFuncionario.getSenha_funcionario());
            pstm.setInt(3, objFuncionario.getId_funcionario());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ERRO no alterar do UsuarioBD");

        }
    }
}
