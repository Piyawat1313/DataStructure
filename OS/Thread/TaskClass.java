package OS.Thread;

public class TaskClass implements Runnable{
    public TaskClass(){

    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("Running in --> " + name);
    }
}
