package com.obabichev.gameoflife.impl;

import com.obabichev.gameoflife.BaseClass;
import com.obabichev.gameoflife.Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleMultithreading extends BaseClass {

    private int numberOfThreads;

    public static void main(String[] args) throws IOException, InterruptedException {
        new SimpleMultithreading().start(args);
    }

    @Override
    protected void parseArgs(String[] args) {
        super.parseArgs(args);
        numberOfThreads = args.length > 0 ? Integer.parseInt(args[3]) : 4;
    }

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

    @Override
    protected void countNextStep(int[][] data, int[][] countingData) throws InterruptedException {

        int partOfThread = fieldSize / numberOfThreads;

        List<Thread> workers = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            Thread worker = new Worker(data, countingData, i * partOfThread + 1, partOfThread);
            workers.add(worker);
            worker.start();
        }

        for (Thread worker : workers) {
            worker.join();
        }
    }

    class Worker extends Thread {

        private int[][] data;
        private int[][] countingData;
        private int startPos;
        private int count;

        public Worker(int[][] data, int[][] countingData, int startPos, int count) {
            this.data = data;
            this.countingData = countingData;
            this.startPos = startPos;
            this.count = count;
        }

        @Override
        public void run() {
            for (int i = startPos; i < startPos + count; i++) {
                for (int j = 1; j < fieldSize + 1; j++) {
                    countingData[i][j] = checkField(data, i, j);
                }
            }

        }
    }

}
