package com.obabichev.gameoflife;


public class BaseClass {

    protected String inputPath;
    protected int numberOfIterations;
    protected String outputPath;

    protected int fieldSize = 0;

    protected void parseArgs(String[] args) {
        inputPath = args.length > 0 ? args[0] : "/Users/olegchuikin/Documents/GameOfLifeParprog/input";
        numberOfIterations = args.length > 0 ? Integer.parseInt(args[1]) : 1000;
        outputPath = args.length > 0 ? args[2] : "/Users/olegchuikin/Documents/GameOfLifeParprog/output";
    }

    protected int[][] addSupportFieldsOnMap(int[][] data) {

        int[][] result = new int[fieldSize + 2][fieldSize + 2];

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                result[i + 1][j + 1] = data[i][j];
            }
        }

        return result;
    }

    protected void fillAdditionalFields(int[][] data) {
        for (int i = 1; i < fieldSize + 1; i++) {
            data[0][i] = data[fieldSize][i];
            data[fieldSize + 1][i] = data[1][i];
            data[i][0] = data[i][fieldSize];
            data[i][fieldSize + 1] = data[i][1];

        }
        data[0][0] = data[fieldSize][fieldSize];
        data[fieldSize + 1][fieldSize + 1] = data[1][1];
        data[0][fieldSize + 1] = data[fieldSize][1];
        data[fieldSize + 1][0] = data[1][fieldSize];
    }

    protected void countNextStep(int[][] data, int[][] countingData) throws InterruptedException {
        for (int i = 1; i < fieldSize + 1; i++) {
            for (int j = 1; j < fieldSize + 1; j++) {
                countingData[i][j] = checkField(data, i, j);
            }
        }
    }

    protected int checkField(int[][] data, int row, int column) {
        int sum = -data[row][column];
        for (int i : new int[]{-1, 0, 1}) {
            for (int j : new int[]{-1, 0, 1}) {
                sum += data[row + i][column + j];
            }
        }
        if (data[row][column] == 0) {
            return sum == 3 ? 1 : 0;
        }
        return sum == 2 || sum == 3 ? 1 : 0;
    }
}
