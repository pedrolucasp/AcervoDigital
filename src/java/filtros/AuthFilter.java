/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;
import modelo.Util;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/protected/*"})
public class AuthFilter implements Filter {

    public AuthFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        //System.out.println(session.getAttribute("email"));
        System.out.println(req.getRequestURI());
        System.out.println(req.getContextPath());
        System.out.println("Antes do If");
        
        if (session == null || session.getAttribute("email") == null) {
            res.sendRedirect(req.getContextPath() + "/login.xhtml"); // No logged-in user found, so redirect to login page.
        } else {
            request.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response); // Logged-in user found, so just continue request.
            System.out.println("Depois do If");
            System.out.println(response.toString());

//            System.out.println(session.getAttribute("email"));

            System.out.println(req.getRequestURI());
            System.out.println(req.getContextPath());
        }


//
//    
//        try {
//
//            // check whether session variable is set
//            HttpServletRequest req = (HttpServletRequest) request;
//            HttpServletResponse res = (HttpServletResponse) response;
//            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
//            res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
//            res.setDateHeader("Expires", 0); // Proxies.
//            //HttpSession ses = req.getSession(false);
//            Usuario usu = (Usuario) req.getSession().getAttribute("usuarioMB");
//            //  allow user to proccede if url is login.xhtml or user logged in or user is accessing any page in //public folder
//            String reqURI = req.getRequestURI();
//            System.out.println(reqURI);
//            
//
//            
//            if (usu.getId() == 0) {
//                res.sendRedirect(req.getContextPath() + "/login.xhtml");  // Anonymous user. Redirect to login page
//                System.out.println("Redirecionando usuário" + usu.getNome());
//            } else // user didn't log in but asking for a page that is not allowed so take user to login page
//            {
//                chain.doFilter(request, response);
//                
//                System.out.println("Usuário passou");
//            }
//        } catch (Throwable t) {
//            System.out.println(t.getMessage());
//        }
    } //doFilter

    @Override
    public void destroy() {
    }
}
