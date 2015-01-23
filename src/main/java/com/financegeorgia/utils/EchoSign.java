/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import com.financegeorgia.echosign.app.CreateNewWidgetWithCounerSigners;
import com.financegeorgia.echosign.app.DownloadDocumentsOfAgreement;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author VXF4071
 */
@WebServlet(name = "EchoSign", urlPatterns = {"/EchoSign"})
public class EchoSign extends HttpServlet {

    private static final Logger logger = org.apache.log4j.Logger.getLogger(EchoSign.class);

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
            String action = request.getParameter("action");

            /* TODO output your page here. You may use following sample code. */
            if (action.equals("echo")) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet EchoSign</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet EchoSign at " + request.getContextPath() + "</h1>");
                out.println("</body>");
                out.println("</html>");
            } else if (action.equals("widget")) {
                String fileToBeUploaded = request.getParameter("fileToBeUploaded");
                logger.debug("File to be uploaded: " + fileToBeUploaded);
                String userId = request.getSession().getAttribute("userId").toString();
                File file = new File(Path.getUserHomeDir() + Path.fileSep + Path.fgDataDir + Path.fileSep + userId + Path.fileSep + fileToBeUploaded);
                if (!file.exists()) {
                    CreateNewWidgetWithCounerSigners cw = new CreateNewWidgetWithCounerSigners();
                    cw.setUserId(userId);
                    cw.setDocName(fileToBeUploaded);
                    cw.setCallbackURL(request.getRequestURL().toString());
                    if (fileToBeUploaded != null) {
                        cw.setfileToBeUploaded(fileToBeUploaded);
                    }
                    cw.run();
                    if (request.getParameter("jsorurl").equals("js")) {
                        logger.debug("Getting Widget JavaScript");
                        out.print(cw.getWidgetJS());
                    } else {
                        logger.debug("Getting Widget URL");
                        out.print(cw.getWidgetURL());
                    }
                } else {
                    response.sendRedirect("/Uploader?action=stream&folder=" + userId + "&filename=" + fileToBeUploaded);
                }
            } else if (action.equals("redirect")) {
                logger.debug("EchoSign redirected here!");
                String documentKey = request.getParameter("documentKey");
                logger.debug("Document Key: " + documentKey);
                //out.print("Document Key: " + documentKey);
                DownloadDocumentsOfAgreement da = new DownloadDocumentsOfAgreement();
                da.setAgreementId(documentKey);
                da.setDocumentName(request.getParameter("docName"));
                //user id will come from echosign call back hence can't come from session
                da.setFileDownloadPath(Path.getUserHomeDir() + Path.fileSep + Path.fgDataDir + Path.fileSep + request.getParameter("userId") + Path.fileSep);
                da.run();
                out.print("<h3>Thank you! We have received a copy of the document.</h3><h4>* A copy has also been emailed to you.</h4>");
            }
        } catch (Exception ex) {
            throw new FGException(ex);
        } finally {
            //out.close(); //cause error in error.jsp
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
        return "EchoSign Servlet for generating widget and for echosign redirection for downloading the signed document!";
    }// </editor-fold>

}
