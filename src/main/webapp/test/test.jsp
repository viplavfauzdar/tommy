<%
    String paramName = "RREVW_RQST_DAYS_TO_DEL";
    if (paramName.contains("RQST_DAYS_TO_DEL")) {
        String codeName = paramName.substring(0, paramName.indexOf("RQST_DAYS_TO_DEL")-1);
        out.println(codeName);
    }
    String key = "RQST_TO_DEL_5";
    key = key.replace("RQST_TO_DEL_", "");
    short rqstcode = Short.parseShort(key);//.substring(key.indexOf("RQST_TO_DEL_"),key.length()));
    out.println(rqstcode);
%>