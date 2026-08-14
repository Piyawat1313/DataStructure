package OS.Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3); //สร้าง pool จำกัดการทำงานไว้ที่  3 ตัว

        // ทำงานแบบสุ่ม
        //ถ้ามีงานที่ 4 เพิ่มเข้ามา ต้องรอจนกว่าคนงานตัวหนึ่งจะว่าง
        executor.execute(new ThreadPrinterChar('a', 5));
        executor.execute(new ThreadPrinterChar('b', 5));
        executor.execute(new ThreadPrintNum(15));

        executor.shutdown();    //ปิดการรับงานใหม่เมื่อ submit เสร็จ
    }
}
