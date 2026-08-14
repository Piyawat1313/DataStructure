package OS.Thread;

public class ThreadPrintNum implements Runnable {
    private int lastNum;

    public ThreadPrintNum(int n) {
        lastNum = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < lastNum; i++) {
            System.out.print(i + " ");
        }

    }

}
