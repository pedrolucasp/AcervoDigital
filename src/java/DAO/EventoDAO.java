package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Evento;


/**
 *
 * @author pedro
 */
public class EventoDAO {
        private static Connection con;

    /**
     * Método para inserir um evento no Banco de Dados
     * @param evento
     * @return id_evento - ID do último evento inserido
     * @throws ClassNotFoundException 
     */
    public int inserirEvento(Evento evento) throws ClassNotFoundException {
        
        PreparedStatement ps = null;
        int id_evento = 0;
        try {
            Connection con = Conexao.getConnection();
            ps  = con.prepareStatement("INSERT INTO eventos (titulo, descricao, local, autor, id_categoria, url_foto) VALUES "
                    + "(?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, evento.getTitulo());
            ps.setString(2, evento.getDescricao());
            ps.setString(3, evento.getLocal());
            ps.setInt(4, evento.getAutor());
            ps.setInt(5, evento.getId_categoria());
            ps.setString(6, evento.getUrl_foto());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs != null && rs.next()){
                id_evento = rs.getInt(1); // mesmo o primeiro INSERT vai ter o id = 1
                System.out.println(id_evento);
            }
            
            return id_evento;
            
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        
        return id_evento;
    }
    
    /**
     * Método para inserir as Hashtag
     * @param hashtag - Uma hashtag por vez
     * @return idTag - ID da Hashtag inserida (deverá ser armazenado em um array no caso de ser muitas)
     * @throws ClassNotFoundException 
     */
    public int inserirHashtag(String hashtag) throws ClassNotFoundException{
        int idTag = 0;
        
        PreparedStatement ps = null;
        
        try {
            Connection con = Conexao.getConnection();
            ps = con.prepareStatement("INSERT INTO hashtag (tag) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, hashtag);
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs != null &&  rs.next()){
                idTag = rs.getInt(1);
            }
            
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        
        return idTag;
    }

    public boolean inserirMapa(ArrayList<Integer> keys, int idEvento) throws ClassNotFoundException{
          PreparedStatement ps = null;
        
        try {
            Connection con = Conexao.getConnection();
            ps = con.prepareStatement("INSERT INTO mapa (id_hashtag, id_evento ) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            
            for(int key : keys){
            ps.setInt(1, key);
            ps.setInt(2, idEvento);
            ps.executeUpdate();

            }
            
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs != null &&  rs.next()){
                return true;
            }
            
        } catch (SQLException ex){
            ex.printStackTrace();
        }
                
        return false;
    }
    
    
    
    
    /**
     * Método para listar todos os eventos
     * @return eventos - Um ArrayList de Eventos
     * @throws SQLException 
     */
    public ArrayList<Evento> listar() throws SQLException, ClassNotFoundException {
        Evento evento;
        ArrayList<Evento> eventos = new ArrayList<>();
        
        PreparedStatement ps = null;
        try {
            Connection con = Conexao.getConnection();
        ps = con.prepareStatement("SELECT id, titulo, descricao, local, url_foto, autor, id_categoria, STRING_AGG(tag, ', ') "
        + "AS hashtags FROM eventos, hashtag, mapa WHERE eventos.id = mapa.id_evento AND mapa.id_hashtag = hashtag.id_hashtag GROUP BY eventos.id");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
                       
            
            evento = new Evento();
            evento.setId(rs.getInt("id"));
            evento.setTitulo(rs.getString("titulo"));
            evento.setDescricao(rs.getString("descricao"));
            evento.setLocal(rs.getString("local"));
            evento.setUrl_foto(rs.getString("url_foto"));
            evento.setAutor(rs.getInt("autor"));
            evento.setId_categoria(rs.getInt("id_categoria"));
            evento.setHashtag(rs.getString("hashtags"));
            eventos.add(evento);
        }
        return eventos;   
        
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        
        return eventos;
    
}
    
    /**
     * Método para consultar um Evento específico.
     * O método irá buscar qualquer similaridades encontrados no título ou no local
     * @param data - Inglês para Dados (não data)
     * @return ArrayList com todos os Eventos encontrados
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public ArrayList<Evento> consultar(String data) throws SQLException, ClassNotFoundException {
        Evento evento;
        ArrayList<Evento> eventos = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            Connection con = Conexao.getConnection();
            ps = con.prepareStatement("SELECT id, titulo, descricao, local, url_foto, autor, id_categoria, STRING_AGG(tag, ', ') AS hashtags"
                    + " FROM eventos, hashtag, mapa WHERE (local ILIKE '%" + data + "%' OR titulo ILIKE  '%" + data + "%') AND "
                    + " eventos.id = mapa.id_evento AND mapa.id_hashtag = hashtag.id_hashtag GROUP BY eventos.id");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setTitulo(rs.getString("titulo"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setLocal(rs.getString("local"));
                evento.setUrl_foto(rs.getString("url_foto"));
                evento.setAutor(rs.getInt("autor"));
                evento.setHashtag(rs.getString("hashtags"));
                evento.setId_categoria(rs.getInt("id_categoria"));
                eventos.add(evento);
            }
            return eventos;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return eventos;

    }

    

}
