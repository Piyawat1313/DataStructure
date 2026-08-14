package OS.Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Feature {
    public static void main(String[] args)throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum = 0;
                for (int i = 1; i <= 100; i++) {
                    sum += i;
                }
                return sum;
            }
        }; 
        
        Future<Integer> future = executor.submit(task);
        Integer result = future.get();
        System.out.println("Result = " + result);
        executor.shutdown();
    }
}
