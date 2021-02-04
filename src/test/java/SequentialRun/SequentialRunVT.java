package SequentialRun;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class SequentialRunVT {

    private static final int NUM_THREADS = 1_000_000;
    private static final int ROUNDS = 10;


    public static void run(){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        AtomicInteger counter = new AtomicInteger(0);


        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter("SRunTestVT.csv", false));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder("Sequential Run");



        long startTimeFinal = System.currentTimeMillis();


        long endTimeFinal;

        System.out.printf("Starting the task: 1 000 000\n\n");


        for(int j = 0; j < ROUNDS; j++) {

            long startTimeTemp = System.currentTimeMillis();

            for (int i = 0; i < NUM_THREADS; i++) {

                Thread runner = Thread.startVirtualThread(new Runnable() {
                    @Override
                    public void run() {
                        counter.incrementAndGet();
                    }
                });
            }

            endTimeFinal = System.currentTimeMillis();

            System.out.printf("Round %d value: %15s\n",j, decimalFormat.format(counter.get()));
            System.out.printf("Task finished in: %7d ms\n\n", endTimeFinal - startTimeTemp);
            stringBuilder.append("\n").append(endTimeFinal - startTimeTemp);

        }

        endTimeFinal = System.currentTimeMillis();


        System.out.printf("Final value: %15s\n", decimalFormat.format(counter.get()));
        System.out.printf("Task finished in: %7d ms\n", endTimeFinal - startTimeFinal);

        try {
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        run();
    }
}
