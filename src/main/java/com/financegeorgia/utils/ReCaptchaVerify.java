/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
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
@WebServlet(name = "ReCaptchaVerify", urlPatterns = {"/ReCaptchaVerify"})
public class ReCaptchaVerify extends HttpServlet {

    private static Logger logger = Logger.getLogger(ReCaptchaVerify.class);
            /**
             * Processes requests for both HTTP <code>GET</code> and
             * <code>POST</code> methods.
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
        BufferedReader buf = null;
        try {
            //verify reacaptcha. Look for success response
            //{ "success": true|false }        
            String secret = "6Lf-Yf8SAAAAAMLJaq-Go11KFu5Szh27ExkDodNr"; //need to read from resource bundle
            String recaptchaResponse = request.getParameter("recaptchaResponse");
            if(logger.isDebugEnabled()) logger.debug("g-recaptcha-response: " + recaptchaResponse);
            URL url = new URL("https://www.google.com/recaptcha/api/siteverify?secret=" + secret + "&response=" + recaptchaResponse);
            if(logger.isDebugEnabled()) logger.debug(url.toString());
            buf = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = buf.readLine();
            StringBuilder sbuf = new StringBuilder();            
            while (line != null) {
                sbuf.append(line);                                
                line = buf.readLine();
            }            
            if(logger.isDebugEnabled()) logger.debug("ReCaptcha Response: " + sbuf.toString());
            //out.print("ReCaptcha Response: " + sbuf.toString());
            if(sbuf.toString().contains("true"))
                request.getSession().setAttribute("ReCaptchaVerifed", true);
            else
                request.getSession().setAttribute("ReCaptchaVerifed", false);
            if(logger.isDebugEnabled()) logger.debug("ReCaptchaVerifed: " + request.getSession().getAttribute("ReCaptchaVerifed"));
            out.print("ReCaptchaVerifed: " + request.getSession().getAttribute("ReCaptchaVerifed"));
        } catch (Exception e) {
            throw new FGException(e);
        } finally {
            buf.close();
            out.close();            
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
        return "Google Recapctcha Verification Servlet!";
    }// </editor-fold>

}
