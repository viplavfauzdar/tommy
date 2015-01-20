/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import com.sun.mail.smtp.SMTPTransport;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Security;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 *
 * @author Viplav
 */
@WebServlet(name = "SendMail", urlPatterns = {"/SendMail"})
public class SendMail extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SendMail.class);
    private static final Resources res = Resources.getInstance();

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
                send(res.getResource("smtpServer"), res.getResource("fromAddress"), res.getResource("password"), toAddress, ccAddress, subject, message);
                out.println("Message sent!");
            }
        } catch (Exception ex) {
            throw new FGException(ex);
        } finally {
            //out.close(); //don't close cause it closes the response stream and I get nothing back to client
        }
    }
    
    public void send(String toAddress, String ccAddress, String subject, String message){
        try {
            send(res.getResource("smtpServer"), res.getResource("fromAddress"), res.getResource("password"), toAddress, ccAddress, subject, message);
        } catch (MessagingException ex) {
            throw new FGException(ex);
        }
    }

    /**
     * Send email using GMail SMTP server.
     *
     * @param smtpServer SMTP Server
     * @param username Mail username
     * @param password Mail password
     * @param recipientEmail TO recipient
     * @param title title of the message
     * @param message message to be sent
     * @throws AddressException if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the
     * connected state or if the message is not a MimeMessage
     */
    public void send(String smtpServer, String username, String password,
            String recipientEmail, String title, String message)
            throws AddressException, MessagingException {
        send(smtpServer, username, password, recipientEmail, "", title, message);
    }

    /**
     * Send email using Mail SMTP server.
     *
     * @param smtpServer
     * @param username Mail username
     * @param password Mail password
     * @param recipientEmail TO recipient
     * @param ccEmail CC recipient. Can be empty if there is no CC recipient
     * @param title title of the message
     * @param message message to be sent
     * @throws AddressException if the email address parse failed
     * @throws MessagingException if the connection is dead or not in the
     * connected state or if the message is not a MimeMessage
     */
    public void send(String smtpServer, String username, String password,
            String recipientEmail, String ccEmail, String title, String message)
            throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", smtpServer);
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        /*
         * If set to false, the QUIT command is sent and the connection is
         * immediately closed. If set to true (the default), causes the
         * transport to wait for the response to the QUIT command.
         * 
         * ref :
         * http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp
         * /package-summary.html
         * http://forum.java.sun.com/thread.jspa?threadID=5205249 smtpsend.java
         * - demo program from javamail
         */
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(username));// + "@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipientEmail, false));

        if (ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        //msg.setText(message, "utf-8");
        msg.setContent(message, "text/html; charset=utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

        //t.connect("smtp.gmail.com", username, password);
        t.connect(smtpServer, username, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
        logger.info("Success!!");
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
        return "Short description";
    }// </editor-fold>

}
