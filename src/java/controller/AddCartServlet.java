/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.CartItemDAO;
import dal.ItemDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Random;
import model.CartItem;
import model.Item;
import model.User;

/**
 *
 * @author lap
 */
public class AddCartServlet extends HttpServlet {
   
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
            out.println("<title>Servlet AddCartServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCartServlet at " + request.getContextPath () + "</h1>");
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
        String itemID = request.getParameter("id");
        String num = request.getParameter("num");
        String action = request.getParameter("action");
                
        CartItemDAO cidb = new CartItemDAO();
        ItemDAO idb = new ItemDAO();
        int sl = (num == null) ? 1 : Integer.valueOf(num);
        
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("account");
        Item it = idb.getItemByID(itemID);
        CartItem cit = cidb.getItemByUser(u, itemID);
        if(cit == null){
            CartItem newCit = new CartItem();
            String cartID = createCartItemID();
            newCit.setId(cartID);
            newCit.setItem(it);
            newCit.setUser(u);
            newCit.setQuantity(sl);
            cidb.insertCartItem(newCit);
        }
        else{
            cidb.updateQuantity(cit, sl);
        }
        ArrayList<CartItem> list = cidb.getListItemsByUser(u);
        session.setAttribute("numInCart", list.size());
        if(action.equals("add")){
            response.sendRedirect("products");
        }
        else{
            response.sendRedirect("showCart");
        }
    } 

    private String createCartItemID(){
        int length = 10;
        String digits = "0123456789";
        String res = "";

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(digits.length());
            res += digits.charAt(index);
        }
        res = "CI" + res;
        return res;
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
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
