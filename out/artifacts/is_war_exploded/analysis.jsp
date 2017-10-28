<%@ page import="java.lang.*" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.io.File" %>
<%@ page import="java.awt.*" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="org.json.JSONArray" %>

<%--
  Created by IntelliJ IDEA.
  User: sergii
  Date: 1/2/17
  Time: 9:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style type="text/css">
        <%@include file="css/style.css" %>
        <%@include file="bootstrap/css/bootstrap.css" %>
    </style>
    <script type="text/javascript">
        <%@include file="js/TrainingSet.js" %>
        <%@include file="js/Binary.js" %>
    </script>
    <title>Title start</title>
</head>
<body>
<script type="text/javascript">
    var set = new TrainingSet();
    var binary = new Binary();
</script>

<div class="container">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-right">
                <li role="presentation" class="active"><a href="http://localhost:2000">Home</a></li>
                <li role="presentation"><a href="#">About</a></li>
                <li role="presentation"><a href="#">Contact</a></li>
            </ul>
        </nav>
        <h3 class="text-muted">Intelligent Systems</h3>
    </div>

    <% String aa = (String) request.getAttribute("works");%>
    <%= aa %>

    <%
        List<int[][]> arrList = (List<int[][]>) request.getAttribute("intArr");
        Iterator<int[][]> iterator1 = arrList.iterator();
        int imgCount = 1;
        while (iterator1.hasNext()) {
            int[][] pixArr = (int[][]) iterator1.next();%>
    <div><br/><h3>image <%=imgCount%></h3><br/></div>
    <%
        BufferedImage img = new BufferedImage(pixArr.length,pixArr[0].length,BufferedImage.TYPE_INT_RGB);
        for (int e=0;e<pixArr.length;e++){
            for (int r=0;r<pixArr[0].length;r++){
                int red=pixArr[e][r];
                int green=pixArr[e][r];
                int blue=pixArr[e][r];
                int rgb = (red << 16) | (green << 8) | blue;
                img.setRGB(e, r, rgb);
            %>
                <div style="float: left"><%= pixArr[e][r] %>
                </div>
            <%}%>
            <div style="clear: both"></div>
        <%}

        for (int e=0;e<pixArr[0].length;e++){
            for (int r=0;r<pixArr.length;r++) {
            %><div style="float: left"><%= pixArr[r][e] %></div>
            <%}%>
            <div style="clear: both"></div>
        <%}

            ImageIO.write(img, "png", new File("/home/sergii/Documents/yeah"+imgCount+".png"));
            Gson gson = new Gson();
            String json = gson.toJson(pixArr);
            JSONArray jArray = new JSONArray(json);
        %>

    <canvas id="square<%=imgCount%>"></canvas>
    <script>
        var aarr = <%=jArray%>;
        var width = <%=jArray.length()%>;
        var height = <%=jArray.getJSONArray(imgCount).length()%>
        //alert(width);
        //alert(height);

        var canvas = document.getElementById("square<%=imgCount%>");
        var context = canvas.getContext('2d');
        canvas.height = height;
        canvas.width = width;
        var i2 = 0;
        var imgData=context.createImageData(canvas.width,canvas.height);
        for(var row5 = 0; row5<height; row5++){
            for(var col5 = 0; col5<width; col5++){
                imgData.data[i2+0]=aarr[col5][row5];
                imgData.data[i2+1]=aarr[col5][row5];
                imgData.data[i2+2]=aarr[col5][row5];
                imgData.data[i2+3]=255;
                i2=i2+4;
            }
        }
        context.putImageData(imgData,0,0);
    </script>
    <%imgCount++;%>
    <%}%>

    <%
        int[] sampleVector = (int[]) request.getAttribute("sampleVector");
        int[] permitUpper = (int[]) request.getAttribute("permitUpper");
        int[] permitLower = (int[]) request.getAttribute("permitLower");
    %>
        <div><h5>Upper permit</h5></div><br/>
    <%
        for (int p=0;p<permitUpper.length;p++){%>
            <div style="float: left; color: blueviolet"><%= permitUpper[p] %>; </div>
        <%}%>
            <div style="clear: both"></div>
            <div><h5>vector</h5></div>
        <%
        for (int e=0;e<sampleVector.length;e++){%>
            <div style="float: left; color: darkred"><%= sampleVector[e] %>; </div>
        <%}%>
            <div style="clear: both"></div>
            <div><h5>Lower permit</h5></div>
        <%
        for (int k=0;k<permitLower.length;k++){%>
            <div style="float: left; color: blueviolet"><%= permitLower[k] %>; </div>
        <%}%>
        <div style="clear: both"></div>

    <%
        List<int[][]> binaryMatrixList = (List<int[][]>) request.getAttribute("binaryMatrixList");
        Iterator<int[][]> binaryMatrixIter = binaryMatrixList.iterator();
        int imgMatrixCount = imgCount+1;
        int binaryMatrCount = 1;
        while (binaryMatrixIter.hasNext()) {
            int[][] pixArrMatrix = (int[][]) binaryMatrixIter.next();%>
    <div><br/><h3>binary matrix <%=binaryMatrCount%></h3><br/></div>
    <%
        BufferedImage img2 = new BufferedImage(pixArrMatrix.length,pixArrMatrix[0].length,BufferedImage.TYPE_INT_RGB);
        for (int e=0;e<pixArrMatrix.length;e++){
            for (int r=0;r<pixArrMatrix[0].length;r++) {
                int rgbM = 0;
                if (pixArrMatrix[e][r] == 1) {
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    rgbM = (red << 16) | (green << 8) | blue;
                } else {
                    int red = 255;
                    int green = 255;
                    int blue = 255;
                    rgbM = (red << 16) | (green << 8) | blue;
                }
                img2.setRGB(e, r, rgbM);
            }
        }

        for (int e=0;e<pixArrMatrix[0].length;e++){
            for (int r=0;r<pixArrMatrix.length;r++){
                %>
                <div style="float: left"><%= pixArrMatrix[r][e] %>
                </div>
            <%}%>
            <div style="clear: both"></div>
        <%}

        ImageIO.write(img2, "png", new File("/home/sergii/Documents/yeah"+imgMatrixCount+".png"));
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(pixArrMatrix);
        JSONArray jArray2 = new JSONArray(json2);
    %>

    <canvas id="square<%=imgMatrixCount%>"></canvas>
    <script>
        var aarr2 = <%=jArray2%>;
        var width2 = <%=jArray2.length()%>;
        var height2 = <%=jArray2.getJSONArray(imgMatrixCount).length()%>;
        //alert(width2);
        //alert(height2);
        var canvas2 = document.getElementById("square<%=imgMatrixCount%>");
        var context2 = canvas2.getContext('2d');
        canvas2.height = height2;
        canvas2.width = width2;
        var i3 = 0;
        var imgData2=context2.createImageData(canvas2.width,canvas2.height);
        for(var row5 = 0; row5<height2; row5++){
            for(var col5 = 0; col5<width2; col5++){
                if (aarr2[col5][row5] == 1) {
                    imgData2.data[i3 + 0] = 0;
                    imgData2.data[i3 + 1] = 0;
                    imgData2.data[i3 + 2] = 0;
                    imgData2.data[i3 + 3] = 255;
                    i3 = i3 + 4;
                }else {
                    imgData2.data[i3 + 0] = 255;
                    imgData2.data[i3 + 1] = 255;
                    imgData2.data[i3 + 2] = 255;
                    imgData2.data[i3 + 3] = 255;
                    i3 = i3 + 4;
                }
            }
        }
        context2.putImageData(imgData2,0,0);
    </script>
    <%imgMatrixCount++;binaryMatrCount++;%>
    <%}%>


    <%
        List<int[]> binaryVectorList = (List<int[]>) request.getAttribute("binaryMatrixVectorList");
        Iterator<int[]> binaryVectorIter = binaryVectorList.iterator();
        int binaryVectorCount = 1;
        while (binaryVectorIter.hasNext()) {
            int[] binVect = (int[]) binaryVectorIter.next();%>
    <div><br/><h3>binary vector <%=binaryVectorCount%></h3><br/></div>
    <%
        for (int e=0;e<binVect.length;e++){%>
            <div style="float: left"><%= binVect[e] %>
            </div>
        <%}%>
        <div style="clear: both"></div>
    <%binaryVectorCount++;}%>




    <%
        double[][][] paramsList = (double[][][]) request.getAttribute("paramsList");
        int imgCharCount = imgMatrixCount+1;
        double chartHeight = 0;
        for (int clss = 0; clss < paramsList.length; clss++) {
            for (int ch = 0; ch < paramsList[0].length; ch++) {
                if (paramsList[clss][ch][6] > chartHeight) {
                    chartHeight = paramsList[clss][ch][6];
                }
            }
        }
        int chartHeightInt = (int) Math.round(chartHeight*200)+1;
        for (int f = 0; f < paramsList.length; f++) {%>
            <div><br/><h3>Chart <%=f%></h3><br/></div>
            <%
            BufferedImage img3 = new BufferedImage(paramsList[0].length,chartHeightInt,BufferedImage.TYPE_INT_RGB);
            for (int e = 0; e < paramsList[0].length; e++){
                for (int r=0; r < chartHeightInt; r++) {
                    int rgbM = 0;
                    if (((paramsList[f][e][2] <= 0.5) || (paramsList[f][e][5] <= 0.5)) & ((paramsList[f][e][6]*200)-r > 0 )) {
                        int red = 255;
                        int green = 102;
                        int blue = 0;
                        rgbM = (red << 16) | (green << 8) | blue;
                    } else if ((paramsList[f][e][2] >= 0.5) & (paramsList[f][e][5] >= 0.5) & ((paramsList[f][e][6]*200)-r > 0 )) {
                        int red = 0;
                        int green = 139;
                        int blue = 234;
                        rgbM = (red << 16) | (green << 8) | blue;
                    } else {
                        int red = 255;
                        int green = 255;
                        int blue = 255;
                        rgbM = (red << 16) | (green << 8) | blue;
                    }
                    img3.setRGB(e, (chartHeightInt-1)-r, rgbM);
                }
            }

            for (int e=0;e<paramsList[f].length;e++){ %>
                <div style="float: left">//<%= paramsList[f][e][0] %>
                </div>
                <div style="float: left">//<%= paramsList[f][e][1] %>
                </div>
                <div style="float: left">//<%= paramsList[f][e][2] %>
                </div>
                <div style="float: left">//<%= paramsList[f][e][3] %>
                </div>
                <div style="float: left">//<%= paramsList[f][e][4] %>
                </div>
                <div style="float: left">//<%= paramsList[f][e][5] %>
                </div>
                <div style="float: left">//<%= paramsList[f][e][6] %>//
                </div>
                <div style="clear: both"></div>
            <%}

            ImageIO.write(img3, "png", new File("/home/sergii/Documents/yeah"+imgCharCount+".png"));
            Gson gson3 = new Gson();
            String json3 = gson3.toJson(paramsList[f]);
            JSONArray jArray3 = new JSONArray(json3);
            %>

            <canvas id="square<%=imgCharCount%>"></canvas>
            <script>
                var aarr3 = <%=jArray3%>;
                var width3 = <%=jArray3.length()%>;
                var height3 = <%=jArray3.getJSONArray(imgCharCount).length()%>;
                //alert(width2);
                //alert(height2);
                var canvas3 = document.getElementById("square<%=imgCharCount%>");
                var context3 = canvas3.getContext('2d');
                canvas3.height = height3;
                canvas3.width = width3;
                var i4 = 0;
                var imgData3=context3.createImageData(canvas3.width,canvas3.height);
                for(var row5 = 0; row5<height3; row5++){
                    for(var col5 = 0; col5<width3; col5++){
                        if (aarr3[col5][row5] == 1) {
                            imgData3.data[i4 + 0] = 0;
                            imgData3.data[i4 + 1] = 0;
                            imgData3.data[i4 + 2] = 0;
                            imgData3.data[i4 + 3] = 255;
                            i3 = i3 + 4;
                        }else {
                            imgData3.data[i4 + 0] = 255;
                            imgData3.data[i4 + 1] = 255;
                            imgData3.data[i4 + 2] = 255;
                            imgData3.data[i4 + 3] = 255;
                            i4 = i4 + 4;
                        }
                    }
                }
                context2.putImageData(imgData3,0,0);
            </script>
            <%imgCharCount++;%>
    <%}%>


</div>
</body>
</html>