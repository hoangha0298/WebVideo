<%@ page import="com.example.demo.model.response.FolderResponse" %>
<%@ page import="com.example.demo.model.response.VideoResponse" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="com.example.demo.model.response.FolderResponse" %>
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
            max-width: 1200px;
            width: 100vw;
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
            width: calc(46vw - (20px * 2));
            height: calc((46vw - (20px * 2)) / 16 * 9);
            border-radius: 5px;
            /*width: 330px;*/
            /*height: 190px;*/
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
            color: #bdc1c6;
            word-break: break-all;
        }

    </style>
    <script>

        function viewVideo(pathRelative) {
            let url = '/video?pathRelative=' + pathRelative;
            window.location = url;
        }
    </script>
</head>
<body style="background-color: #202124">

<%!
    String getUrlImage(VideoResponse videoResponse) throws UnsupportedEncodingException {
        return "/video_service/image?pathRelative=" + URLEncoder.encode(videoResponse.getPathRelative(), "UTF-8");
    }
%>

<%
    FolderResponse folder = (FolderResponse) request.getAttribute("folderResponse");
%>

<div class="container">
    <div class="row">

        <%for (int i = 0; i < folder.getFiles().size(); i++) {%>

        <div class="item">
            <div onclick="viewVideo('<%=URLEncoder.encode(folder.getFiles().get(i).getPathRelative(), "UTF-8")%>')" style="cursor: pointer">
                <div class="video-image"
                     style="background-image: url('<%=getUrlImage(folder.getFiles().get(i))%>');">
                    <div class="video-time"><%=folder.getFiles().get(i).getLengthSecond() / 60%>
                        :<%=folder.getFiles().get(i).getLengthSecond() % 60%>
                    </div>
                </div>
            </div>
            <div class="video-information">
                <h3 class="name-video"><%=folder.getFiles().get(i).getPathRelative()%>
                </h3>
            </div>
        </div>

        <%}%>

        <div style="width: 100%; height: 40px; background-color: darkcyan"></div>

        <%
            for (int j = 0; j < folder.getSubFolders().size(); j++) {
                FolderResponse folderSub = folder.getSubFolders().get(j);
                for (int i = 0; i < folderSub.getFiles().size(); i++) {
                    VideoResponse video = folderSub.getFiles().get(i);
        %>

        <div class="item">
            <div onclick="viewVideo('<%=URLEncoder.encode(video.getPathRelative(), "UTF-8")%>')" style="cursor: pointer">
                <div class="video-image"
                     style="background-image: url('<%=getUrlImage(video)%>');">
                    <div class="video-time"><%=video.getLengthSecond() / 60%>
                        :<%=video.getLengthSecond() % 60%>
                    </div>
                </div>
            </div>
            <div class="video-information">
                <h3 class="name-video"><%=video.getPathRelative()%>
                </h3>
            </div>
        </div>

        <%
                }
            }
        %>

    </div>
</div>

</body>
</html>