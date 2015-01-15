<%@page isErrorPage="true"%>

  <html><body>

  <h1 style="color: red">Error</h1>

  <pre>
  <%
      //out.println(exception);
      //exception.printStackTrace();
      exception.printStackTrace(response.getWriter());
//  // unwrap ServletExceptions.
//  while (exception instanceof ServletException)
//    exception = ((ServletException) exception).getRootCause();
//
//  // print stack trace.
//  exception.printStackTrace(new PrintWriter(out));
  %>
  </pre>

  </body></html>