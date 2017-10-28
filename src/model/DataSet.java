package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by sergii on 1/5/17.
 */
public class DataSet {

    public ArrayList getPixelsListOriginImage(Iterator itr) throws IOException {
        ArrayList commonList = new ArrayList();
        /* polar coordinates */
        while(itr.hasNext()) {
            Object pathElement = itr.next();
            System.out.print(pathElement.toString());

            BufferedWriter bw = null;
            FileWriter fw = null;
            fw = new FileWriter("/home/sergii/Documents/idealogs.txt");
            bw = new BufferedWriter(fw);

            File file = new File((String) pathElement);
            BufferedImage image = ImageIO.read(file);
            int red1 = 0, green1 = 0, blue1 = 0;
            Color actualColour;

            double diagonal1Sqrt = (Math.ceil(Math.pow(image.getWidth(), 2))) + (Math.ceil(Math.pow(image.getHeight(), 2)));
            double radiusDurty = (Math.sqrt(diagonal1Sqrt))/2;
            int radius1 = (int) Math.ceil(radiusDurty);
            int[][] pixArrQ = new int[360][radius1];
            int xAngle = image.getWidth()/2;
            int yAngle = image.getHeight()/2;
            int xAngle2 = 0;
            int yAngle2 = 0;
            double qRect = 0;
            try {
                for (int q=0; q < 360; q++) {
                    for (int x = 0; x < radius1; x++) {
                        qRect = Math.toRadians(q);
                        xAngle2 = xAngle + ((int) Math.ceil((x) * Math.cos(qRect)));
                        yAngle2 = yAngle + ((int) (Math.ceil((x) * Math.sin(qRect)))*(-1));
                        bw.write("new column in the image\n");
                        try {
                            actualColour = new Color(image.getRGB(xAngle2, yAngle2));
                            red1 = actualColour.getRed();
                            green1 = actualColour.getGreen();
                            blue1 = actualColour.getBlue();
                            pixArrQ[q][x] = (red1 + green1 + blue1) / 3;
                            bw.write("red = " + red1 + "; green = " + green1 + "; blue = " + blue1);
                            bw.write("\n");
                        } catch (ArrayIndexOutOfBoundsException e) {
                            pixArrQ[q][x] = 0;
                            bw.write("red = " + red1 + "; green = " + green1 + "; blue = " + blue1);
                            bw.write("\n");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                bw.write("sww in 'getPixelsListOriginImage'...");
            } finally {
                try {

                    if (bw != null)
                        bw.close();
                    if (fw != null)
                        fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            commonList.add(pixArrQ);
        }
        /* rectangular coordinates
        while(itr.hasNext()) {
            Object element = itr.next();
            System.out.print(element.toString());

            BufferedWriter bw = null;
            FileWriter fw = null;
            fw = new FileWriter("/home/sergii/Documents/idealogs.txt");
            bw = new BufferedWriter(fw);

            File file = new File((String) element);
            BufferedImage image = ImageIO.read(file);
            int red1 = 0, green1 = 0, blue1 = 0;
            Color actualColour;
            int total_pixels = 0;

            System.out.println("width = " + image.getWidth() + "height = " + image.getHeight());
            total_pixels = image.getWidth() * image.getHeight();
            Color[] colorsArray = new Color[total_pixels];

            int[][] pixArr = new int[image.getWidth()][image.getHeight()];
            int i = 0;
            try {
                for (int x = 0; x < image.getWidth(); x++) {
                    bw.write("new column in the image\n");
                    for (int y = 0; y < image.getHeight(); y++) {
                        actualColour = new Color(image.getRGB(x, y));
                        colorsArray[i] = actualColour;
                        i++;
                        red1 = actualColour.getRed();
                        green1 = actualColour.getGreen();
                        blue1 = actualColour.getBlue();
                        pixArr[x][y] = (red1 + green1 + blue1) / 3;
                        bw.write("red = " + red1 + "; green = " + green1 + "; blue = " + blue1);
                        bw.write("\n");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                bw.write("sww in 'getPixelsListOriginImage'...");
            } finally {
                try {

                    if (bw != null)
                        bw.close();
                    if (fw != null)
                        fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            commonList.add(pixArr);
        }
        */
        return commonList;
    }

    public ArrayList getSampleMeanVector(ArrayList commonList){
        ArrayList sampleVectorList = new ArrayList();
        Iterator<int[][]> itr = commonList.iterator();
        while(itr.hasNext()) {
            int[][] element = itr.next();
            int arrCount = element[0].length;
            int[] arrSampleMeanVector = new int[element.length];
            for (int i = 0; i < element.length; i++) {
                arrSampleMeanVector[i] = 0;
                for (int j = 0; j < arrCount; j++) {
                    arrSampleMeanVector[i] = arrSampleMeanVector[i] + element[i][j];
                };
                arrSampleMeanVector[i] = Math.round(arrSampleMeanVector[i] / arrCount);
            };
            sampleVectorList.add(arrSampleMeanVector);
        }
        return sampleVectorList;
    }

    public int[] getSampleMeanVector(int[][] commonList){
        int[] arrSampleMeanVector = new int[commonList.length];
        int arrCount = commonList[0].length;
            for (int i = 0; i < commonList.length; i++) {
                arrSampleMeanVector[i] = 0;
                for (int j = 0; j < commonList[0].length; j++) {
                    arrSampleMeanVector[i] = arrSampleMeanVector[i] + commonList[i][j];
                };
                arrSampleMeanVector[i] = Math.round(arrSampleMeanVector[i] / arrCount);
            };
        return arrSampleMeanVector;
    }

    public ArrayList getPermitsUpper(ArrayList sampleVectorList,double permission){
        ArrayList permitsUpperList = new ArrayList();
        Iterator<int[]> itr = sampleVectorList.iterator();
        while(itr.hasNext()) {
            int[] element = itr.next();
            int[] arrPermitsUpperList = new int[element.length];
            for (int i = 0; i < element.length; i++) {
                if(element[i]*permission >= 255) {
                    arrPermitsUpperList[i] = 255;
                }else{
                    arrPermitsUpperList[i] = (int) Math.round(element[i]*permission);
                }
            };
            permitsUpperList.add(arrPermitsUpperList);
        }
        return permitsUpperList;
    }

    public int[] getPermitsUpper(int[] sampleVector,double permission){
        int[] permitsUpper = new int[sampleVector.length];
            for (int i = 0; i < sampleVector.length; i++) {
                if(sampleVector[i]*permission >= 255) {
                    permitsUpper[i] = 255;
                }else{
                    permitsUpper[i] = (int) Math.round(sampleVector[i]*permission);
                }
            };
        return permitsUpper;
    }

    public ArrayList getPermitsLower(ArrayList sampleVectorList,double permission){
        ArrayList permitsLowerList = new ArrayList();
        Iterator<int[]> itr = sampleVectorList.iterator();
        while(itr.hasNext()) {
            int[] element = itr.next();
            int[] arrPermitsLowerList = new int[element.length];
            for (int i = 0; i < element.length; i++) {
                if(element[i]*permission <= 0) {
                    arrPermitsLowerList[i] = 0;
                }else{
                    arrPermitsLowerList[i] = (int) Math.round(element[i]*permission);
                }
            };
            permitsLowerList.add(arrPermitsLowerList);
        }
        return permitsLowerList;
    }

    public int[] getPermitsLower(int[] sampleVector,double permission){
        int[] permitsLower = new int[sampleVector.length];
        for (int i = 0; i < sampleVector.length; i++) {
            if(sampleVector[i]*permission <= 0) {
                permitsLower[i] = 0;
            }else{
                permitsLower[i] = (int) Math.round(sampleVector[i]*permission);
            }
        };
        return permitsLower;
    }

}
