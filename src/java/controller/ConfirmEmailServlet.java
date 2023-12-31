/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import model.User;

/**
 *
 * @author admin
 */
public class ConfirmEmailServlet extends HttpServlet {
   private String confirmEmail;
   private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ConfirmEmailServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConfirmEmailServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String maskedEmail = request.getParameter("maskedEmail");
        if(confirmEmail == null){
            confirmEmail = request.getParameter("confirmEmail");
        }
        if(email.equals(confirmEmail)){
            System.out.println("ok");
            UserDAO udb = new UserDAO();
            User x = udb.getUserByUsername(username);
            x.setPassword("123");
            udb.changePass(x);
            
            request.setAttribute("mk", "123");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        } else {
            request.setAttribute("confirmEmail", confirmEmail);
            request.setAttribute("username", username);
            request.setAttribute("maskedEmail", maskedEmail);
            request.setAttribute("error", "Email invalid");
            request.getRequestDispatcher("confirmEmail.jsp").forward(request, response);
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String username = (String)request.getAttribute("username");
        UserDAO udb = new UserDAO();
        String email = udb.getEmailByUsername(username);
        confirmEmail = email;
        String masked = maskEmail(email);
        
        request.setAttribute("email", masked);
        request.setAttribute("username", username);
        request.getRequestDispatcher("confirmEmail.jsp").forward(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    private String maskEmail(String email){
        int atIndex = email.indexOf("@");
        
        if(atIndex > 0) {
            String frefix = email.substring(0, atIndex);
            String maskedFrefix = maskString(frefix);
            return maskedFrefix + email.substring(atIndex);
        }
        return email;
    }
    
    private String maskString(String input) {
        int length = input.length();
        if (length > 3) {
            int maskedLength = Math.max(1, length - 3);
            String masked = "*".repeat(maskedLength);
            return masked + input.substring(length - 3);
        } else {
            return "*".repeat(length);
        }
    }
    
    private String generateRandomString(){
        StringBuilder randomString = new StringBuilder(6);
        SecureRandom secureRandom = new SecureRandom();
        
        for(int i = 0; i < 6; i++){
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            randomString.append(randomChar);
        }
        
        return randomString.toString();
    }
}
