<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<%--
  Created by IntelliJ IDEA.
  User: sergii
  Date: 12/26/16
  Time: 11:41 PM
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <style type="text/css">
        <%@include file="css/style.css" %>
        <%@include file="bootstrap/css/bootstrap.css" %>
    </style>
    <title>Intelligent Systems</title>
</head>
<body>
    <div class="container">
        <div class="header clearfix">
            <nav>
                <ul class="nav nav-pills pull-right">
                    <li role="presentation" class="active"><a href="#">Home</a></li>
                    <li role="presentation"><a href="#">About</a></li>
                    <li role="presentation"><a href="#">Contact</a></li>
                </ul>
            </nav>
            <h3 class="text-muted">Intelligent Systems</h3>
        </div>
        <div class="jumbotron">
            <h2>vehicle recognition on the ground at an angle</h2>
        </div>
        <div class="row marketing">
            <h4>Please, fill the image path</h4>
            <form action="analysis" method="post" enctype="multipart/form-data">
                <h3 style="color:blue">Select image to upload:</h3>
                <br/>
                <input type="file" name="file"><br/>
                <input type="file" name="file"><br/>
                <input type="file" name="file"><br/>
                <input class="btn btn-primary btn-lg" type="submit" value="Upload Image">
            </form>
        </div>
        <footer class="footer">
            <p>&copy; 2017 S.Varenyk, Inc.</p>
        </footer>

    </div>
</body>
</html>
