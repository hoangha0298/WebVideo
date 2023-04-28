<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Video</title>
    <link rel="stylesheet" href="https://cdn.plyr.io/3.6.12/plyr.css">
    <style>
        /* This is purely for the demo */
        .container {
            max-width: 100vw;
            margin: 0 auto;
        }

        .plyr {
            border-radius: 4px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body style="background-color: #202124">

<%!
    String getUrlVideo(String pathRelative) throws UnsupportedEncodingException {
        return "/video_service/video?pathRelative=" + URLEncoder.encode(pathRelative, "UTF-8");
    }
%>

<div class="container">
    <video controls muted crossorigin playsinline id="player">
        <source src="<%=getUrlVideo(request.getParameter("pathRelative"))%>" type="video/mp4" size="720">
        <a href="<%=getUrlVideo(request.getParameter("pathRelative"))%>" download>Download</a>
    </video>
</div>

<script src="https://cdn.plyr.io/3.6.12/plyr.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        // This is the bare minimum JavaScript. You can opt to pass no arguments to setup.
        const player = new Plyr('#player');

        // Expose
        window.player = player;

        // Bind event listener
        function on(selector, type, callback) {
            document.querySelector(selector).addEventListener(type, callback, false);
        }
    });
</script>

</body>
</html>