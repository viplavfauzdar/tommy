/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 *
 * @author Viplav
 */
@WebServlet(name = "SendMail", urlPatterns = {"/SendMail"})
public class SendMail extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SendMail.class);
    private static final Resources res = Resources.getInstance();
    private static final Mailer mailer = new Mailer();

    public SendMail() {
        res.setResourceFile("sendmail");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet SendMail</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet SendMail at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");

            String type = request.getParameter("type");

            String toAddress = request.getParameter("toAddress"); //can be comma separated list
            String subject = null, ccAddress = null;
            if (type.equals("contact")) {
                subject = res.getResource("contactSubject");//request.getParameter("subject");
                ccAddress = res.getResource("contactFGEmail");
            } else if (type.equals("subscription")) {
                subject = res.getResource("subscriptionSubject");
                ccAddress = res.getResource("subscriptionFGEMail");
            } else {
                subject = request.getParameter("subject");
                ccAddress = request.getParameter("ccemail");
            }
            String message = request.getParameter("message");
            if (toAddress.equals("")) {
                out.println("No email address provided!"); //throw new FGException("No email address provided!");
            } else if (message.equals("")) {
                out.println("No message content provided!");
            } else {
                mailer.send(res.getResource("smtpServer"), res.getResource("fromAddress"), res.getResource("password"), toAddress, ccAddress, subject, message);
                out.println("Message sent!");
            }
        } catch (Exception ex) {
            throw new FGException(ex);
        } finally {
            //out.close(); //don't close cause it closes the response stream and I get nothing back to client
        }
    }

    /**
     * Utility method called from password reset and sign up ack 
     */
    public void send(String toAddress, String ccAddress, String subject, String message){
        try {
            mailer.send(res.getResource("smtpServer"), res.getResource("fromAddress"), res.getResource("password"), toAddress, ccAddress, subject, message);
        } catch (MessagingException ex) {
            throw new FGException(ex);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Send Mail Servlet";
    }// </editor-fold>

}
