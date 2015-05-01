<%@page import="java.io.IOException"%>
<%@page import="java.io.File"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%
        FileInputStream fin = null;
        OutputStream out1 = null;
        File file = new File("/var/lib/openshift/549e56b25973cab96c000042/app-root/data/FG/42/execSumm.pdf");
        if (!file.exists()) {
            System.out.println("Sending placeholder image");
            try {
                //file = new File("/img/placeholder.png");
                response.sendRedirect("/img/placeholder.png");
            } catch (IOException ex) {
                System.out.println(ex);
            }
        } else {
            try {
                fin = new FileInputStream(file);
                out1 = response.getOutputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = fin.read(bytes)) != -1) {
                    out1.write(bytes, 0, read);
                }
            } catch (IOException ex) {
                System.out.println(ex);
            } finally {
                try {
                    if (fin != null) {
                        fin.close();
                    }
                    if (out1 != null) {
                        out1.close();
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }
%>