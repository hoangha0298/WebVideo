<%@ page import="com.example.demo.Model.DTO.FolderDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Videos</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
          rel="stylesheet">
    <style>
        * {
            box-sizing: border-box;
            font-family: Roboto;
        }

        .container {
            margin: auto;
            max-width: 700px;
        }

        .row {
            display: flex;
            flex-wrap: wrap;
            padding-right: -10px;
        }

        .item {
            padding: 10px 0 0 10px;
        }

        .video-image {
            width: 330px;
            height: 190px;
            background-size: contain;
            background-repeat: no-repeat;
            background-position: center;
            background-color: black;
            position: relative;
        }

        .video-image > .video-time {
            position: absolute;
            right: 10px;
            bottom: 10px;
            border-radius: 4px;
            background: black;
            padding: 2px 3px;
            color: white;
            font-size: 0.75rem;
            font-weight: 400;
        }

        .video-information {
            width: 330px;
            padding: 10px 5px;
            min-height: 45px;
            display: flex;
        }

        .video-information > .name-video {
            font-weight: 500;
            font-size: 0.85rem;
            margin: 0 0 3px;
            color: #030303;
        }

    </style>
    <script>
        function encodePramsUrl(params) {
            const paramsUrl = [];
            for (let nameParams in params) {
                paramsUrl.push(encodeURIComponent(nameParams) + '=' + encodeURIComponent(params[nameParams]));
            }
            return paramsUrl.join('&');
        }

        function viewVideo(pathRelative) {
            let params = {pathRelative: pathRelative};
            let url = '/video?' + encodePramsUrl(params);
            window.location = url;
        }
    </script>
</head>
<body>

<%
    FolderDTO folder = (FolderDTO) request.getAttribute("folderDTO");
%>

<div class="container">
    <div class="row">

        <%for (int i = 0; i < folder.getVideos().size(); i++) {%>

        <div class="item">
            <a onclick="viewVideo('<%=folder.getVideos().get(i).getPathRelative()%>')">
                <div class="video-image" style="background-image: url('/video_service/image/<%=folder.getVideos().get(i).getPathRelative()%>');">
                    <div class="video-time">30:10</div>
                </div>
            </a>
            <div class="video-information">
                <h3 class="name-video"><%=folder.getVideos().get(i).getPathRelative()%></h3>
            </div>
        </div>

        <%}%>

    </div>
</div>

</body>
</html>