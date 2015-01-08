package controle;

import DAO.UsuarioDAO;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import modelo.Usuario;
import modelo.Util;

// Filtro

/**
 *
 * @author pedro
 */
@Named(value = "usuarioMB")
@SessionScoped
public class UsuarioMB implements Serializable {

    
    Usuario usuario = new Usuario();
    UsuarioDAO dao = new UsuarioDAO();
    
    /**
     * Creates a new instance of UsuarioMB
     */
    public UsuarioMB() {

    }
    
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public String cadastro() throws ClassNotFoundException {
        
        boolean retorno = UsuarioDAO.cadastrar(usuario.getNome(), usuario.getEmail(), usuario.getSenha());
        
        if(retorno == true){
            return "protected/cadastrado";
        }
        
        return "cadastro";
    }
    
    public String login() throws ClassNotFoundException{
        
        Usuario resultado = UsuarioDAO.login(usuario.getEmail(), usuario.getSenha());
        
        if(resultado != null){
            
           
            //HttpSession session = Util.getSession();
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("email", resultado.getEmail());
            context.getExternalContext().getSessionMap().put("id", resultado.getId());
            context.getExternalContext().getSessionMap().put("nome", resultado.getNome());
//
//            HttpSession session = new HttpSession();
//            session.setAttribute("email", resultado.getEmail());
//            session.setAttribute("id", resultado.getId());
//            session.setAttribute("nome", resultado.getNome());
            
            return "/protected/index?faces-redirect=true";
            
        } else {
        
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Login inválido, erro na senha ou no email!",
                    "Tente novamente"));
    
            return "login";
        }
        
        
    }
    
    public String logout(){
        
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("email", null);
        context.getExternalContext().getSessionMap().put("id", null);
        context.getExternalContext().getSessionMap().put("nome", null);
        
        context.getExternalContext().invalidateSession();
        
        
        return "acd/login?faces-redirect=true";
    }

    
}