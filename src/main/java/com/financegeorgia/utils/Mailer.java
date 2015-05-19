/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import com.sun.mail.smtp.SMTPTransport;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;

/**
 *
 * @author viplav
 */
public class Mailer {

    private static final Logger logger = Logger.getLogger(Mailer.class);

    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\viplav\\Google Drive\\NetBeansProjects\\tommy\\src\\main\\webapp\\test\\emailtemplate.html"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            logger.info(everything);

            Mailer m = new Mailer();
            m.send("smtpout.secureserver.net", "info@financegeorgia.com", "9ChoncholGupta2014",
                    "viplav_fauzdar@homedepot.com,viplav.fauzdar@gmail.com", "HTML Template", everything);
        } catch (Exception ex) {
            logger.fatal(ex);
        } finally {
            try {
                if(br!=null) br.close();
            } catch (IOException ex) {
                logger.fatal(ex);
            }
        }
    }

//    public void send(String toAddress, String ccAddress, String subject, String message){
//        try {
//            send(res.getResource("smtpServer"), res.getResource("fromAddress"), res.getResource("password"), toAddress, ccAddress, subject, message);
//        } catch (MessagingException ex) {
//            throw new FGException(ex);
//        }
//    }
    /**
     * Send email using SMTP server.
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
        //Multipart multiPart = new MimeMultipart("alternative");
        //MimeBodyPart htmlPart = new MimeBodyPart();
        //htmlPart.setContent(message, "text/html; charset=utf-8");
        //multiPart.addBodyPart(htmlPart);
        //msg.setContent(multiPart);//, "multipart/alternative");
        msg.setContent(message, "text/html; charset=utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

        //t.connect("smtp.gmail.com", username, password);
        t.connect(smtpServer, username, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
        logger.info("Success!!");
    }

}
