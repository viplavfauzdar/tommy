Sending...
<%
response.setHeader("Access-Control-Allow-Origin", "*");
response.sendRedirect("../webresources/rest.user");
javax.servlet.FilterChain chain;
chain.doFilter(response, response);
filter 
%>