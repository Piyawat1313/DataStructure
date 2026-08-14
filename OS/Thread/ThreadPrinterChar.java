package OS.Thread;

public class ThreadPrinterChar implements Runnable {
    private char charToPrint;
    private int times;

    public ThreadPrinterChar(char c, int t){
        charToPrint = c;
        times = t;
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            System.out.println("Print --> " + charToPrint);
        }
    }
}
