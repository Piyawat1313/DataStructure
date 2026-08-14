package OS.Thread;

public class Interrupt {
    public static void main(String[] args) throws InterruptedException{
        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // เช็คสถานะ interrupt เป็นเงื่อนไขวนลูป
                    //เกิด Exception ทันทีเมื่อ sleep ถูกขัดจังหวะ
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println("Working...");
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); //เรียก interrupt ซ้ำเพื่อคืนสถานะให้ระบบรับรู้
                    System.out.println("worker interrupted");
                }
            }
        });
        worker.start();
        Thread.sleep(3000);
        worker.interrupt();
    }
}
