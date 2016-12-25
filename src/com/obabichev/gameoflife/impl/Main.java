package com.obabichev.gameoflife.impl;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("SINGLE:");
        new SingleThread().start(args);

        System.out.println("SIMPLE MULTITHREADING");
        new SimpleMultithreading().start(args);

    }
}
