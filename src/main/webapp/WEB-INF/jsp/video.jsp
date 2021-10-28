<%@ page import="com.example.demo.Model.DTO.VideoDTO" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Video</title>
</head>
<body style="background-color: #202124">

<%!
    String getUrlVideo(String pathRelative) throws UnsupportedEncodingException {
        return "/video_service/video?pathRelative=" + URLEncoder.encode(pathRelative, "UTF-8");
    }
%>

<div>
    <video style="max-width: 1200px; width: 100%" controls muted autoplay src="<%=getUrlVideo(request.getParameter("pathRelative"))%>"></video>
</div>

</body>
</html>