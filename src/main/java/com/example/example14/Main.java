package com.example.example14;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Main {
    private static final String INPUT_FILE = "c:\\tmp\\input.jpg";
    private static final String OUTPUT_DIR = "c:\\tmp\\";
    private static final String OUTPUT_EXT = ".jpg";

    public static void main(String[] args) throws IOException {
        var inputImage = ImageIO.read(new File(INPUT_FILE));
        var outputImage = new BufferedImage(inputImage.getWidth(),
                inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        var strategies = prepareStrategies(inputImage, outputImage);
        executeStrategies(strategies, outputImage);
    }

    private static LinkedHashMap<String, Runnable> prepareStrategies(
            BufferedImage inputImage, BufferedImage outputImage) {
        var strategies = new LinkedHashMap<String, Runnable>();
        strategies.put("main_thread", () -> singleThreadRecolor(inputImage, outputImage));
        strategies.put("1_thread", () -> multiThreadRecolor(inputImage, outputImage, 1));
        strategies.put("2_threads", () -> multiThreadRecolor(inputImage, outputImage, 2));
        strategies.put("4_threads", () -> multiThreadRecolor(inputImage, outputImage, 4));
        strategies.put("8_threads", () -> multiThreadRecolor(inputImage, outputImage, 8));
        return strategies;
    }

    private static void executeStrategies(LinkedHashMap<String, Runnable> strategies, BufferedImage outputImage) throws IOException {
        for (var strategy : strategies.entrySet()) {
            execute(strategy.getValue(), strategy.getKey());
            var filePath = OUTPUT_DIR + "output_" + strategy.getKey() + OUTPUT_EXT;
            ImageIO.write(outputImage, "jpg", new File(filePath));
        }
    }

    private static void execute(Runnable runnable, String legend) {
        long start = System.currentTimeMillis();
        runnable.run();
        System.out.println(legend + " - time: " + (System.currentTimeMillis() - start));
    }

    private static void singleThreadRecolor(BufferedImage inputImage,
                                            BufferedImage outputImage) {
        recolorRegion(inputImage, outputImage, 0, 0,
                inputImage.getWidth(), inputImage.getHeight());
    }

    private static void multiThreadRecolor(BufferedImage inputImage,
                                           BufferedImage outputImage,
                                           int numberOfThreads) {
        var width = inputImage.getWidth();
        var height = inputImage.getHeight() / numberOfThreads;
        var threads = new ArrayList<Thread>();

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadIndex = i;
            var thread = new Thread(() -> {
               int top = height * threadIndex;
               recolorRegion(inputImage, outputImage, 0, top, width, height);
            });
            thread.start();
            threads.add(thread);
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void recolorRegion(BufferedImage inputImage,
                                      BufferedImage outputImage,
                                      int left, int top,
                                      int width, int height) {
        for (int x = left; x < left + width; x++) {
            for (int y = top; y < top + height; y++) {
                recolorPixel(inputImage, outputImage, x, y);
            }
        }
    }

    private static void recolorPixel(BufferedImage inputImage,
                                     BufferedImage outputImage,
                                     int x, int y) {
        var rgb = new RGB(inputImage.getRGB(x, y));
        if (isShadedOfGray(rgb)) {
            rgb = new RGB(
                    rgb.red() + 10,
                    rgb.green() - 80,
                    rgb.blue() - 20
            );
        }
        outputImage.setRGB(x, y, rgb.value());
    }

    private static boolean isShadedOfGray(RGB rgb) {
        return Math.abs(rgb.red() - rgb.green()) < 30
                && Math.abs(rgb.red() - rgb.blue()) < 30
                && Math.abs(rgb.green() - rgb.blue()) < 30;
    }
}
