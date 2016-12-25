package com.obabichev.gameoflife.impl;

import com.obabichev.gameoflife.BaseClass;
import com.obabichev.gameoflife.Util;

import java.io.IOException;

public class SingleThread extends BaseClass{

    public void start(String[] args) throws IOException, InterruptedException {
        parseArgs(args);

        int[][] data = Util.readInputMatrix(inputPath);
        fieldSize = data.length;

        data = addSupportFieldsOnMap(data);

        data = play(data, numberOfIterations);

        Util.writeToFile(data, outputPath);

        Util.printMatrix(data);
    }

    private int[][] play(int[][] data, int iterates) throws InterruptedException {
        int[][] countingData = new int[fieldSize + 2][fieldSize + 2];
        fillAdditionalFields(data);

        for (int i = 0; i < iterates; i++) {
            fillAdditionalFields(data);
            countNextStep(data, countingData);

            int[][] tmp = data;
            data = countingData;
            countingData = tmp;
        }
        return data;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new SingleThread().start(args);
    }

}
