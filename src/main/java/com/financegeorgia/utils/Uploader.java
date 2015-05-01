/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

/**
 *
 * @author Viplav
 *
 * Servlet implementation class Uploader
 */
public class Uploader extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(Uploader.class);

    private static final String userhomedir = Path.getUserHomeDir();//System.getProperty("user.home");
    private static final String filesep = Path.fileSep, fgDataDir = Path.fgDataDir;// System.getProperty("file.separator");
    private String action = null, path = null, filename = null; //folder = null,

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Uploader() {
        super();
        // TODO Auto-generated constructor stub
    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().println("READY");
//    }
    /**
     * @param request
     * @param response
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub

        //folder is the uid
        String folder = request.getParameter("folder");
        //filename needs to come from client as its fixed
        String filename = request.getParameter("filename");

        if (folder == null || folder.equals("")) {
            folder = request.getSession().getAttribute("userId").toString();
        }
        File dir = new File(userhomedir + filesep + fgDataDir + filesep + folder);
        dir.mkdirs();
        String path = dir.getPath();
        logger.info("File Path in Uploader: " + path);
        this.path = path;
        this.filename = filename;

        action = request.getParameter("action");

        if (action.equals("upload")) {
            upload(request, response);
        } else if (action.equals("stream")) {
            stream(response);
        } else if (action.equals("delete")) {
            delete(response);
        }

    }

    private void upload(HttpServletRequest request, HttpServletResponse response) {
        boolean isMultipartContent = ServletFileUpload
                .isMultipartContent(request);
        if (!isMultipartContent) {
            logger.info("Nothing to upload!!");
        } else {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            InputStream filecontent = null;
            OutputStream out1 = null;
            try {
                List<FileItem> fields = upload.parseRequest(request);
                logger.info("Number of fields: " + fields.size());
                Iterator<FileItem> it = fields.iterator();
                if (!it.hasNext()) {
                    logger.info("No fields found");
                    return;
                }
                boolean allowedFileTypes = false;
                while (it.hasNext()) {
                    FileItem fileItem = it.next();
                    boolean isFormField = fileItem.isFormField();
                    if (isFormField) {
                        logger.info("Regular form field - FIELD NAME: "
                                + fileItem.getFieldName()
                                + " STRING: "
                                + fileItem.getString());
                    } else {
                        logger.info("file form field - FIELD NAME: "
                                + fileItem.getFieldName() + "\nNAME: "
                                + fileItem.getName() + "\nCONTENT TYPE: "
                                + fileItem.getContentType()
                                + "\nSIZE (BYTES): " + fileItem.getSize()
                                + "\nTO STRING: " + fileItem.toString());
                        if (fileItem.getContentType().equals("image/jpeg")
                                || fileItem.getContentType().equals("image/jpg")
                                || fileItem.getContentType().equals("image/gif")
                                || fileItem.getContentType().equals("image/png")
                                || fileItem.getContentType().equals("application/pdf")
                                && fileItem.getSize() < 5000000000L) {
                            allowedFileTypes = true;
                            filecontent = fileItem.getInputStream();
                        }
                        if (filename == null) {
                            filename = fileItem.getName();
                        }
                    }

                    if (allowedFileTypes) {

                        out1 = new FileOutputStream(path + filesep + filename);
                        logger.debug("Saving file to: " + path + filesep + filename);
                        int read = 0;
                        final byte[] bytes = new byte[1024];
                        while ((read = filecontent.read(bytes)) != -1) {
                            out1.write(bytes, 0, read);
                        }
                        out1.close();
                        filecontent.close();
                        response.getWriter().println(filename + " uploaded!");
                    } else {
                        logger.info("File " + filename + " not allowed. Type or size is incorrect!");
                        response.getWriter().println("Not allowed. Please check the file type and size. Only images and pdfs less than 5MB are allowed!");
                    }
                }

            } catch (FileUploadException e) {
                throw new FGException(e);
            } catch (IOException e) {
                throw new FGException(e);
            } finally {
                try {
                    if (filecontent != null) {
                        filecontent.close();
                    }
                    if (out1 != null) {
                        out1.close();
                    }
                } catch (Exception ex) {
                    throw new FGException(ex);
                }
            }

        }//end of if multipart
    }

    private void stream(HttpServletResponse response) {
        response.setContentType("application/pdf");
        FileInputStream fin = null;
        OutputStream out = null;
        File file = new File(path + filesep + filename);
        if (!file.exists()) {
            logger.info("Sending placeholder image");
            try {
                //file = new File("/img/placeholder.png");
                response.sendRedirect("/img/placeholder.png");
            } catch (IOException ex) {
                throw new FGException(ex);
            }
        } else {
            try {
                fin = new FileInputStream(file);
                out = response.getOutputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = fin.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            } catch (IOException ex) {
                throw new FGException(ex);
            } finally {
                try {
                    if (fin != null) {
                        fin.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException ex) {
                    throw new FGException(ex);
                }
            }
        }
    }

    private void delete(HttpServletResponse response) {
        try {
            File file = new File(path + filesep + filename);
            file.delete();
            response.getWriter().println(filename + " deleted!");
        } catch (IOException ex) {
            throw new FGException(ex);
        }
    }
}
