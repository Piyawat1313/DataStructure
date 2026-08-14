package OS.Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Shutdown {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Task is Running");
            }
        });

        executor.shutdown();    //ไม่รับงานใหม่
        try {
            // รอให้จบภายใน 5 วินาที
            boolean finished = executor.awaitTermination(5, TimeUnit.SECONDS);

            if(!finished){
                executor.shutdownNow(); //ถ้าไม่จบบังคับใหหยุด
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt(); //จัดการ exception
        }
    }
}
