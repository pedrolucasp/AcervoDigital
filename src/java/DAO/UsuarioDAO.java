package DAO;

import DAO.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;

/**
 *
 * @author Rovena Lima
 */
public class UsuarioDAO {
    private static Connection con;
       
    public UsuarioDAO(){
    }
    
    Usuario usuario = null; // Global para uso Estático... 
    
    /**
     * Método simples para realizar o cadastro dos usuários. 
     * @param nome - Nome do usuário
     * @param email - Email do usuário
     * @param senha - Senha do usuário
     * @return boolean - true se foi bem sucedido / false se não conseguiu 
     * @throws ClassNotFoundException 
     */
    public static boolean cadastrar(String nome, String email, String senha) throws ClassNotFoundException{       
        
        PreparedStatement ps = null;
        
        try {
            
            Connection con = Conexao.getConnection();
            ps = con.prepareStatement("INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)");
            
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senha);
            
            if(ps.executeUpdate() != 0){
                
                return true;
                
            }         
                        
        } catch (SQLException ex){
        System.out.println("Ocorreu um erro durante o processamento de cadastro. \n Veja o log: " + ex.getMessage());
        ex.printStackTrace();
        } finally {
            Conexao.close(con); // fecha a conexão para previnir ataques de injeção / rastreio do banco
        }
        
        
        return false;
        
    }
    /**
     * Método estático para realizar o login. 
     * <p> Usando um PreparedStatement ele busca na base de Dados um usuário e seus <br />
     * respectivos campos (email, nome, id, etc). Se encontrado ele retorna esse usuário para 
     * ser salvo na sessão. Caso contrário, ele retorna <code>null</code>.  
     * </p>
     * @param email
     * @param senha
     * @return Usuario - no caso de existir um usuário ativo
     * @throws ClassNotFoundException 
     */
    public static Usuario login(String email, String senha) throws ClassNotFoundException{
        
        
        PreparedStatement ps;
        
        try {
        
            Connection con = Conexao.getConnection();
            ps = con.prepareStatement("SELECT id, nome, email, senha FROM usuario WHERE email = ? AND senha = ?");
            
            ps.setString(1, email);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery();
            
                if(rs.next()){
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    return usuario;
                } else {
                    return null;
                }
        
          
        } catch (SQLException ex){ 
            ex.printStackTrace();
        } finally {
            Conexao.close(con);
        }
        
        
        return null;
    }
    
    
}
