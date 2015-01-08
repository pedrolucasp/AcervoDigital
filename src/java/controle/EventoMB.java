package controle;

import DAO.EventoDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import modelo.Evento;
import modelo.Upload;

/**
 *
 * @author Rovena Lima
 */
@Named(value = "eventoMB")
@SessionScoped

public class EventoMB implements Serializable {
    
    // Walkin' Blues <3
    
    Evento evento = new Evento();
    EventoDAO dao = new EventoDAO();
    FacesContext context = FacesContext.getCurrentInstance();
    // Uploaded File
    private Part uploadedFile;
    private ArrayList<Evento> resultados;
    private String texto;
    
    /**
     * Creates a new instance of EventoMB
     */
    public EventoMB() {
    }
    
    
    
    /**
     * Retorna o evento atual que estaremos lidando aqui
     * @return evento
     */    
    public Evento getEvento(){
        return evento;
    }
    
    public ArrayList<Evento> getEventos() throws SQLException, ClassNotFoundException{
       return dao.listar();
    }
    
    public void consultar() throws SQLException, ClassNotFoundException {
//        resultados.clear();
        setResultados(dao.consultar(getTexto()));
    }
    
    
    
    public String cadastrar() throws ClassNotFoundException{
        
            FacesContext context = FacesContext.getCurrentInstance();
        
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            
        int idUsuario = (int) session.getAttribute("id");
        
        
        
        try {
            Upload upload = Upload.getInstance();

            
            
            evento.setUrl_foto(upload.upload(uploadedFile));
            
           // evento.setUrl_foto(upload.extractFileName(uploadedFile));
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        evento.setAutor(idUsuario);
        
        int id_evento = dao.inserirEvento(evento);
        // Inseriu evento, agora tem que inserir as hashtags
                
        String hashtags[] = evento.getHashtag().replace(" ", "").trim().split("(\\#)|(\\,)");
        
      //  System.out.println(hashtags[1]);
        ArrayList<Integer> keys = new ArrayList<>();
        
        for (String hashtag : hashtags) {
            
            int id = dao.inserirHashtag(hashtag);
            System.out.println(hashtag + " foi inserida com o id: " + id);
            System.out.println("Nova Hashtag");
            
            keys.add(id); // pega todos ids das hashtags inseridas
        }
        
        boolean inseriu = dao.inserirMapa(keys, id_evento);
        
        if(inseriu == true){
        
        return "index";           
       
        } 
        
        return "index";
            
        
        
    }
  
    /*
     * M�todos auxiliares 
     */
    
    /**
     * @return the uploadedFile
     */
    public Part getUploadedFile() {
        return uploadedFile;
    }

    /**
     * @param uploadedFile the uploadedFile to set
     */
    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    /**
     * Resultados do consultar
     * @return the resultados
     */
    public ArrayList<Evento> getResultados() {
        return resultados;
    }

    /**
     * Método auxiliar para setar os resultados dentro da variável Resultados
     * @param resultados the resultados to set
     */
    public void setResultados(ArrayList<Evento> resultados) {
        this.resultados = resultados;
    }

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
}
