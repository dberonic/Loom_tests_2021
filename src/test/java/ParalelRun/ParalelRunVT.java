package ParalelRun;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class ParalelRunVT {

    Vector<Thread> threads = new Vector<>();

    private final int NUM_THREADS = 1_000;
    private final int ROUNDS = 10;

    public void run(){

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter("PRunTestVT.csv", false));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder("Sequential Run");
        stringBuilder.append("Creation, Starting, Task, Response");

        System.out.printf("Starting the task with %d Threads\n", NUM_THREADS);



        for(int j = 0; j < ROUNDS; j++) {

            long startTime = System.currentTimeMillis();


            threads = new Vector<>();

            for(int i = 0; i < NUM_THREADS; i++){
                threads.add( Thread.startVirtualThread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
            }

            // measure response


            long endTime = System.currentTimeMillis();

            for (int i = 0; i < NUM_THREADS; i++) {
                try {
                    (threads.get(i)).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // end task
            System.out.printf("Thread response finished in: %7d ms\n\n", endTime - startTime);
            stringBuilder.append("\n").append(endTime - startTime);
        }

        try {
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
        }catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        ParalelRunVT pr = new ParalelRunVT();
        pr.run();
    }
}
