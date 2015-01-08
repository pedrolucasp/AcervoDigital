/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Classe auxiliar para manipular sessões. <br /> Usada quase que somente nos Filtros
 * @author Rovena Lima
 */
public class Util {
    /**
     * Esse método serve pra descobrir se existe uma sessão e qual sua instância
     * @return HttpSession
     */
      public static HttpSession getSession() {
        return (HttpSession)
          FacesContext.
          getCurrentInstance().
          getExternalContext().
          getSession(false);
      }
       
      /**
       * Método auxiliar para retornar uma requisição. <small><i> (utilizado depois no AuthFilter) </i></small>
       * @return HttpServletRequest
       */
      public static HttpServletRequest getRequest() {
       return (HttpServletRequest) FacesContext.
          getCurrentInstance().
          getExternalContext().getRequest();
      }
      
      /**
       * Método auxiliar para retornar o Username (email/login) dos usuários
       * @return String - Email salvo na Sessão
       */
 
      public static String getUserName()
      {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return  session.getAttribute("email").toString();
      }
       
      /**
       * Método auxiliar para retornar o id do Usuário ativo no sistema
       * @return String - ID do Usuário salvo na sessão
       */
      public static String getUserId()
      {
        HttpSession session = getSession();
        if ( session != null )
            return (String) session.getAttribute("id_usuario");
        else
            return null;
      }
}



