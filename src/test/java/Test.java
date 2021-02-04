public class Test {

    public static void main(String[] args) {


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hi");
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hi there");
            }
        };


        Thread t = Thread.startVirtualThread(runnable);
        Thread thread = new Thread(runnable2);
        thread.start();

    }
}
