package model;

//import com.google.common.collect.Iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sergii on 10/14/17.
 */
public class BinaryAnalysis {

    public int[][] createBinaryMatrix (int[][] learnMatrix, int[] permitsUpper, int[] permitsLower) {
        int[][] binaryMatrix = new int[learnMatrix.length][learnMatrix[0].length];
            for (int col = 0; col < learnMatrix.length; col++) {
                for (int row = 0; row < learnMatrix[col].length; row++) {
                    if ((learnMatrix[col][row] >= permitsLower[col]) && (learnMatrix[col][row] <= permitsUpper[col])) {
                        binaryMatrix[col][row] = 1;
                    } else {
                        binaryMatrix[col][row] = 0;
                    }
                }
            }
        return binaryMatrix;
    }

    public double roundDoubleToTwo(double value) {
        double number = value;
        number = Math.round(value * 100);
        number = number / 100;
        return number;
    }

    public ArrayList getBinaryMatrixList(ArrayList commonList) {
        ArrayList binaryMatrixList = new ArrayList();
        DataSet imageSet = new DataSet();
        Iterator<int[][]> itr = commonList.iterator();
        while(itr.hasNext()) {
            int[][] element = itr.next();
            int [] meanVector = imageSet.getSampleMeanVector(element);
            int [] permitsUpper = imageSet.getPermitsUpper(meanVector,1.2);
            int [] permitsLower = imageSet.getPermitsLower(meanVector,0.8);
            int[][] binaryMatrix = createBinaryMatrix(element,permitsUpper,permitsLower);
            binaryMatrixList.add(binaryMatrix);
        }
        return binaryMatrixList;
    }

    public ArrayList getBinaryMatrixList(ArrayList commonList, int [] permitsUpper, int [] permitsLower) {
        ArrayList binaryMatrixList = new ArrayList();
        Iterator<int[][]> itr = commonList.iterator();
        while(itr.hasNext()) {
            int[][] element = itr.next();
            int[][] binaryMatrix = createBinaryMatrix(element,permitsUpper,permitsLower);
            binaryMatrixList.add(binaryMatrix);
        }
        return binaryMatrixList;
    }

    public int[] createBinaryVector (int[][] binaryMatrix) {
        int kol1_0;
        int kol1_1;
        int[] binaryVector = new int[binaryMatrix.length];
        for (int i = 0; i < binaryMatrix.length; i++) {
            kol1_1 = 0;
            kol1_0 = 0;
            int j;
            for (j=0; j < binaryMatrix[i].length; j++) {
                if (binaryMatrix[i][j] == 1) {
                    kol1_1 = kol1_1 + 1;
                }else{
                    kol1_0 = kol1_0 + 1;
                }
            }
            if (kol1_1 >= j/2) {
                binaryVector[i] = 1;
            }else{
                binaryVector[i] = 0;
            }
        }
        return binaryVector;
    }

    public ArrayList getBinaryMatrixVectorList(ArrayList binaryMatrixList) {
        ArrayList binaryMatrixVectorList = new ArrayList();
        Iterator<int[][]> itr = binaryMatrixList.iterator();
        while(itr.hasNext()) {
            int[][] element = itr.next();
            int[] binaryMatrixVector = createBinaryVector(element);
            binaryMatrixVectorList.add(binaryMatrixVector);
        }
        return binaryMatrixVectorList;
    }

    public int getDistance(int[] binaryVector1,int[] binaryVector2) {
        int sum = 0;
        for (int i = 0; i < binaryVector1.length; i++) {
            sum += Math.abs(binaryVector1[i] - binaryVector2[i]);
        }
        return sum;
    }

    public int[] makePair(ArrayList binaryEtalonVectorsArray){
        Iterator<int[]> itr = binaryEtalonVectorsArray.iterator();
        Iterator<int[]> itr2 = binaryEtalonVectorsArray.iterator();
        int countFeatures = 0;
        int p = 0;
        int p2 = 0;
        while(itr.hasNext()) {
            countFeatures = itr.next().length;
            //it's good to add warning if countFeatures is different
            p+=1;
        }
        int[][] binaryVectors = new int[p][countFeatures];
        while(itr2.hasNext()) {
            int[] element = itr2.next();
            binaryVectors[p2] = element;
            p2+=1;
        }
        int[] pair = new int[p];
        int[] dpair = new int[p];
        int distNeighb = 0;
        for (int i = 0; i < p; i++) {
            pair[i] = 0;
            dpair[i] = countFeatures;
            for (int j = 0; j < p; j++) {
                if (i != j) {
                    distNeighb = this.getDistance(binaryVectors[i],binaryVectors[j]);
                    if (distNeighb <= dpair[i]) {
                        pair[i] = j;
                        dpair[i] = distNeighb;
                    }
                }
            }
            System.out.println("size = "+p);
            System.out.println("countFeatures = "+countFeatures);
            System.out.println("pair["+i+"] = "+pair[i]);
            System.out.println("dpair["+i+"] = "+dpair[i]);
        }
        return pair;
    }

    public int[][][] makeDistVectorsToEtalon (ArrayList binaryMatrixList,int[] pair){
        int sizeMatrix = 0;
        int countFeatures = 0;
        int countSamples = 0;
        int p = 0;
        int p2 = 0;
        ArrayList binaryMatrixVectorList = new ArrayList();
        Iterator<int[][]> itr = binaryMatrixList.iterator();
        while(itr.hasNext()) {
            sizeMatrix+=1;
            int[][] element = itr.next();
            countFeatures = element.length;
            countSamples = element[0].length;
        }
        binaryMatrixVectorList = getBinaryMatrixVectorList(binaryMatrixList);
        Iterator<int[]> itr3 = binaryMatrixVectorList.iterator();
        int[][] binaryVectors = new int[sizeMatrix][countFeatures];
        while(itr3.hasNext()) {
            int[] element = itr3.next();
            binaryVectors[p] = element;
            p+=1;
        }
        int[][] dev = new int[sizeMatrix][countSamples];
        int[][] dePairV = new int[sizeMatrix][countSamples];
        int[][][] distVectors = new int[2][sizeMatrix][countFeatures];
        int[][][] elements = new int[sizeMatrix][countSamples][countFeatures];
        Iterator<int[][]> itr2 = binaryMatrixList.iterator();
        while(itr2.hasNext()) {
            int[][] element = itr2.next();
            elements[p2] = element;
            p2+=1;
        }
            for (int cl = 0; cl < sizeMatrix; cl++) {
                for (int i = 0; i < countFeatures; i++) {
                    for (int j = 0; j < countSamples; j++) {
                        if (binaryVectors[cl][i] != elements[cl][i][j]) {
                            dev[cl][j] += 1;
                        }
                        if (binaryVectors[cl][i] != elements[pair[cl]][i][j]) {
                            dePairV[cl][j] += 1;
                        }
                    }
                }
            }
            distVectors[0] = dev;
            distVectors[1] = dePairV;
        System.out.println("size = "+dev);
        System.out.println("size = "+dePairV);
        return distVectors;
    }

    /**
     * k - how much classes
     * dist - max dist, how much features (start from 1)
     * samples - how much instances (vectors)
     * de - dist from the center of the etalon vector
     * dePair - dist from the center of the etalon vector to the neighbour
     */
    public double[] calculateCriterion(int k,int dist,int samples,int[][][] distVectors) {
        double[] params = new double[7]; // five parameters
        double k1 = 0;
        double k2 = 0;
        double k3 = 0;
        double k4 = 0;
        int [][] de = distVectors[0];
        int[][] dePair = distVectors[1];
        for (int i = 0; i < samples; i++) {
            if (de[k][i] <= dist) {
                k1++;
            };
        };
        for (int j = 0; j < samples; j++) {
            if (dePair[k][j] <= dist) {
                k3++;
            };
        };
        double d1 = roundDoubleToTwo(k1 / samples);
        double a = roundDoubleToTwo((samples - k1) / samples);
        double b = roundDoubleToTwo(k3 / samples);
        double d2 = roundDoubleToTwo((samples - k3) / samples);
        //var kfe = this.calculateShennon(d1,a,b,d2);
        double kfe = roundDoubleToTwo(calculateKulback(d1,d2));
        params[0] = k;
        params[1] = dist;
        params[2] = d1;
        params[3] = a;
        params[4] = b;
        params[5] = d2;
        params[6] = kfe;
        return params;
    }
/*
    Binary.prototype.calculateShennon = function(d1,a,b,d2) {
        var com1 = 0;
        var com2 = 0;
        var com3 = 0;
        var com4 = 0;
        if (d1 == 0) {
            com1 = 0.0
        }else{
            com1 = (d1/(d1+b))*(Math.log(d1/(d1+b))/Math.log(2.0));
        };
        if (a == 0) {
            com2 = 0.0
        }else{
            com2 = (a/(d2+a))*(Math.log(a/(d2+a))/Math.log(2.0));
        };
        if (b == 0) {
            com3 = 0.0
        }else{
            com3 = (b/(d1+b))*(Math.log(b/(d1+b))/Math.log(2.0));
        };
        if (d2 == 0) {
            com4 = 0.0
        }else{
            com4 = (d2/(d2+a))*(Math.log(d2/(d2+a))/Math.log(2.0));
        };
        return 1.0 + (com1 + com2 + com3 + com4)/2.0;
    };
*/
    public double calculateKulback(double d1, double d2) {
        double result;
        result = (d1+d2+0.1)/(2.0-d1-d2+0.1);
        result = Math.log(result)/Math.log(2.0);
        result *= d1+d2-1.0;
        return result;
    }

}