package com.obabichev.gameoflife;

import java.io.*;
import java.util.Scanner;

public class Util {

    private static final boolean enableConsoleOutput = false;

    public static int[][] readInputMatrix(String path) throws FileNotFoundException {

        Scanner input = new Scanner(new File(path));
        // pre-read in the number of rows/columns
        int rows = 0;
        int columns = 0;
        while (input.hasNextLine()) {
            ++rows;
            Scanner colReader = new Scanner(input.nextLine());
            while (rows == 1 && colReader.hasNextInt()) {
                ++columns;
                colReader.nextInt();
            }
        }
        int[][] a = new int[rows][columns];

        input.close();

        // read in the data
        input = new Scanner(new File(path));
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (input.hasNextInt()) {
                    a[i][j] = input.nextInt();
                }
            }
        }

        return a;
    }

    public static void printMatrix(int[][] matrix) {
        if (!enableConsoleOutput){
            return;
        }
        int len = matrix.length;
        for (int i = 1; i < len - 1; i++) {
            for (int j = 1; j < len - 1; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printMatrix(int[][] matrix, String message) {
        if (!enableConsoleOutput){
            return;
        }
        System.out.println(message);
        printMatrix(matrix);
    }

    public static void writeToFile(int[][] data, String path) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(path));

        int len = data.length;
        for (int i = 1; i < len - 1; i++) {
            for (int j = 1; j < len - 1; j++) {
                outputWriter.write(data[i][j] + " ");
            }
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

}
