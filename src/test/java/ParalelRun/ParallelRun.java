package ParalelRun;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class ParallelRun {

    Vector threads = new Vector();

    private final int NUM_THREADS = 1000;
    private final int ROUNDS = 10;

    public void run(){

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter("PRunTest.csv", false));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder("Sequential Run");
        stringBuilder.append("Creation, Starting, Task, Response");

        System.out.printf("Starting the task with %d Threads\n", NUM_THREADS);



        for(int j = 0; j < ROUNDS; j++) {

            long startTime = System.currentTimeMillis();


            threads = new Vector();

            for(int i = 0; i < NUM_THREADS; i++){
                Thread runner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                threads.add(runner);
            }
            long endTime = System.currentTimeMillis();
            System.out.printf("Thread creation finished in: %7d ms\n", endTime - startTime );
            stringBuilder.append("\n").append(endTime - startTime);

            long actionStart  = System.currentTimeMillis();
            for (int i = 0; i < NUM_THREADS; i++) {
                ((Thread) threads.get(i)).start();
            }
            // measure response

//            for (int i = 0; i < NUM_THREADS; i++) {
//                try {
//                    ((Thread) threads.get(i)).join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

            endTime = System.currentTimeMillis();
            // end task
            System.out.printf("Thread response finished in: %7d ms\n\n", endTime - actionStart);
            stringBuilder.append(",").append(endTime - actionStart);
        }

        try {
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        ParallelRun pr = new ParallelRun();
        pr.run();
    }
}
