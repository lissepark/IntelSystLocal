package controller;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.BinaryAnalysis;
import model.DataSet;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Created by Sergii on 1/2/17.
 */
public class Analysis extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList commonList = new ArrayList();
        //ArrayList sampleVectorList = new ArrayList();
        //ArrayList permitsUpperList = new ArrayList();
        //ArrayList permitsLowerList = new ArrayList();
        ArrayList binaryMatrixList = new ArrayList();
        ArrayList binaryMatrixVectorList = new ArrayList();

        if (!ServletFileUpload.isMultipartContent(request)) {
            System.out.println("Nothing to upload");
            return;
        }
        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        ArrayList pathList = new ArrayList();
        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                String contentType = item.getContentType();
                if (!contentType.equals("image/png")) {
                        System.out.println("Error. Only png or jpg format image files supported");
                        continue;
                }
                File uploadDir = new File("/home/sergii/Documents");
                File file = File.createTempFile("img", ".png", uploadDir);
                item.write(file);
                pathList.add(file.getPath());
                System.out.println(file.getPath());
            }
        } catch (FileUploadException e) {
            System.out.println("FileUpload Exception");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Other Exception in doPost of Analysis servlet");
        }
        Iterator itr = pathList.iterator();

        DataSet imageSet = new DataSet();
        commonList = imageSet.getPixelsListOriginImage(itr);
        int [] sampleVector = imageSet.getSampleMeanVector((int[][]) commonList.get(0));
        BinaryAnalysis binary = new BinaryAnalysis();
        int[] permitsUpper = imageSet.getPermitsUpper(sampleVector, 1.54);
        int[] permitsLower = imageSet.getPermitsLower(sampleVector, 0.46);
        binaryMatrixList = binary.getBinaryMatrixList(commonList, permitsUpper, permitsLower);
        binaryMatrixVectorList = binary.getBinaryMatrixVectorList(binaryMatrixList);
        int[] pairsList = binary.makePair(binaryMatrixVectorList);
        int[][][] distList = binary.makeDistVectorsToEtalon(binaryMatrixList, pairsList);

        double[][][] paramsList = new double[commonList.size()][permitsLower.length][7];
        for (int clss = 0; clss < commonList.size(); clss++) {
            for (int i = 0; i < permitsLower.length; i++) {
                double[] calculateCriterion = binary.calculateCriterion(clss, i, distList[0][0].length, distList);
                paramsList[clss][i] = calculateCriterion;
            }
        }

/*
        double permitDelta =0.02;
        for (double permit = 0.02; permit < 0.9; permit+=permitDelta) {
            permitsUpper = imageSet.getPermitsUpper(sampleVector, permit+1);
            permitsLower = imageSet.getPermitsLower(sampleVector, 1-permit);
            binaryMatrixList = binary.getBinaryMatrixList(commonList, permitsUpper, permitsLower);
            binaryMatrixVectorList = binary.getBinaryMatrixVectorList(binaryMatrixList);
            pairsList = binary.makePair(binaryMatrixVectorList);
            distList = binary.makeDistVectorsToEtalon(binaryMatrixList, pairsList);
            paramsList = new double[commonList.size()][permitsLower.length][7];
            double[] kfMax = new double[commonList.size()];
            for (int clss = 0; clss < commonList.size(); clss++) {
                kfMax[clss] = 0;
                for (int i = 0; i < permitsLower.length; i++) {
                    double[] calculateCriterion = binary.calculateCriterion(clss, i, distList[0][0].length, distList);
                    paramsList[clss][i] = calculateCriterion;

                       if ((paramsList[clss][i][2] >= 0.5) & (paramsList[clss][i][5] >= 0.5) & ((paramsList[clss][i][6]*100) > 0 )) {
                            if (paramsList[clss][i][6] > kfMax[clss]) {
                                kfMax[clss] = paramsList[clss][i][6];
                                System.out.println("kfMax["+clss+"] "+kfMax[clss]+"; permit = "+permit);
                            }
                        }
                }

            }
        }
*/

        request.setAttribute("intArr",commonList);
        request.setAttribute("sampleVector",sampleVector);
        request.setAttribute("permitUpper",permitsUpper);
        request.setAttribute("permitLower",permitsLower);
        request.setAttribute("binaryMatrixList",binaryMatrixList);
        request.setAttribute("binaryMatrixVectorList",binaryMatrixVectorList);
        request.setAttribute("pairsList",pairsList);
        request.setAttribute("paramsList",paramsList);

        request.setAttribute("works","works");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/analysis.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("attr1","text for attr1");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/analysis.jsp");
        requestDispatcher.forward(request, response);
    }
}
