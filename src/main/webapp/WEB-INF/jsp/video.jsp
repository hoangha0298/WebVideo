<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Video</title>
</head>
<body style="background-color: black">

<div style="border: 2px darkcyan">
    <video style="max-width: 1200px; width: 100%" controls muted src="/video_service/video/<%=request.getParameter("pathRelative")%>"></video>
</div>

</body>
</html>