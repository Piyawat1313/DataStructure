import java.util.concurrent.BlockingQueue;

/**
 * รับงานจาก JobGenerator แล้วจัดเข้า Ready Queue
 *
 * ===== ไฟล์นี้เป็นโครงเปล่า นักศึกษาต้องเขียนเอง =====
 *
 * ข้อกำหนดจากโจทย์ (หัวข้อ 2 และ 4):
 *   - Scheduler เป็น Thread บังคับ ห้ามให้ JobGenerator ใส่งานลง Ready Queue โดยตรง
 *   - รับผิดชอบการจัดลำดับตามนโยบาย FCFS หรือ Priority
 *
 * ข้อควรคิด:
 *   - Scheduler รับงานจาก JobGenerator ผ่านอะไร และรอโดยไม่กิน CPU อย่างไร
 *   - เมื่อ JobGenerator ปล่อยงานครบแล้ว Scheduler รู้ได้อย่างไรว่าควรหยุด
 */
public class Scheduler extends Thread {

    // TODO: เก็บช่องทางรับงานจาก JobGenerator, ReadyQueue ปลายทาง และ logger
    //
    // หมายเหตุ: constructor ด้านล่างยังไม่มี parameter สำหรับ "ช่องทางรับงาน"
    // ให้เพิ่มเข้าไปให้ตรงกับที่ออกแบบไว้ใน JobGenerator
    // เพิ่ม parameter ได้ แต่อย่าเปลี่ยนชื่อคลาส

    public final ReadyQueue readyQueue; //ตัวเอางานเข้าคิว
    public final BlockingQueue<Job> arrivalQueue;   
    public final ProjectLogger logger;
    public Scheduler(ReadyQueue readyQueue,ProjectLogger logger, BlockingQueue<Job> arrivalQueue){
        super("scheduler");
        // TODO
        this.readyQueue = readyQueue;
        this.logger = logger;
        this.arrivalQueue = arrivalQueue;
    }

    @Override
    public void run() {
        // TODO: วนรับงานเข้ามาแล้วใส่ ReadyQueue จนกว่าจะได้รับสัญญาณให้หยุด
        try {
            while (true) {
                Job job = arrivalQueue.take(); // block รอโดยไม่กิน CPU

                // JobGenerator ปล่อยงานครบแล้ว ไม่มีงานใหม่เข้ามาอีก
                if(job == Job.POISON_PILL){
                    break;
                }
                readyQueue.add(job);    //เอางานเข้า Queue
                logger.systemEvent("job=" + job.id + " READY"); //log แสดงงานตามเหตุการณ์ที่ทำงาน
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }finally{
            
        // ส่งงานครบแล้ว ต้องบอก ReadyQueue/Worker ต่อว่า "จะไม่มีงานใหม่อีก"
            readyQueue.close();
        }
    }
}
