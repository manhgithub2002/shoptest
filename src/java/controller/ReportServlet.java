/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import com.google.gson.Gson;
import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.ChartData;
import org.json.JSONObject;

/**
 *
 * @author admin
 */
public class ReportServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ReportServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportServlet at " + request.getContextPath () + "</h1>");
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
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        OrderDAO od = new OrderDAO();
        
        //Get chart's data
        List<ChartData> orderStats = od.getDailyTotal(1);
   
        List<ChartData> allDays = new ArrayList<>();
        for (int day = 1; day <= 31; day++) {
            boolean dayExists = false;
            for (ChartData orderStat : orderStats) {
                if (orderStat.getDay() == day) {
                    allDays.add(orderStat);
                    dayExists = true;
                    break;
                }
            }
            if (!dayExists) {
                allDays.add(new ChartData(day, 0));
            }
        }
        
        //Get total revenue in month
        double totalMonthRevenue = od.getTotalRevenueInCurrentMonth();
        
        //Get total item sold
        int totalItemSold = od.getTotalItemsSoldInMonth();
        
        //Get total revenue
        double totalRevenue = od.getRevenue();
        
        
        JSONObject resultObject = new JSONObject();
        resultObject.put("monthTotal", totalMonthRevenue);
        resultObject.put("chartData", allDays);
        resultObject.put("totalItemSold", totalItemSold);
        resultObject.put("totalRevenue", totalRevenue);
        
        out.println(resultObject.toString());
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
