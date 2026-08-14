package OS.Thread;

public class Client {
    public static void main(String[] args) {
        TaskClass task = new TaskClass();
        Thread thread = new Thread(task);

        thread.start(); //ทำงานใน Thread ใหม่
        
        try {
            thread.join();
        } catch (Exception e) {
            
        }

        thread.run();   // ทำงานใน main Thread
        System.out.println();
        System.out.println("=======================");
        System.out.println("Thread Print Char");
        System.out.println("=======================");

        try {
            thread.join();
        } catch (Exception e) {
            
        }

        ThreadPrinterChar t1 = new ThreadPrinterChar('a', 5);
        thread = new Thread(t1);
        thread.start();
        System.out.println();


        System.out.println("=======================");
        System.out.println("Multi Thread");
        System.out.println("=======================");

        // ถ้าไม่ได้ใช้ join() พอรันออกมา Thread จะทำงานแบบสุ่ม
        ThreadPrinterChar th1 = new ThreadPrinterChar('a', 15);
        ThreadPrinterChar th2 = new ThreadPrinterChar('b', 15);
        ThreadPrintNum th3 = new ThreadPrintNum(5);
        
        thread = new Thread(th1);
        thread.start();

        thread = new Thread(th2);
        thread.start();

        thread = new Thread(th3);
        thread.start();

    }
}
