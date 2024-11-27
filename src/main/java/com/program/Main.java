package com.program;

import com.program.controller.WiseSayingController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        WiseSayingController wiseSayingController = new WiseSayingController();
        wiseSayingController.run(sc);
    }
}