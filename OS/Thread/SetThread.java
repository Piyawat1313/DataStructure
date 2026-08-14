package OS.Thread;

public class SetThread {
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Thread current = Thread.currentThread();
                System.out.println("Thread name --> " + current.getName());
            }
        };
        Thread thread = new Thread(task);
        thread.setName("Sex Worker -B");
        thread.start();
    }
}
