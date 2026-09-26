import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * ปล่อยงานเข้าสู่ระบบตามเวลา arrivalMs ของแต่ละ Job
 *
 * ===== ไฟล์นี้เป็นโครงเปล่า นักศึกษาต้องเขียนเอง =====
 *
 * หน้าที่ (หัวข้อ 3 ของโจทย์):
 * - รอจนถึงเวลา arrivalMs ของแต่ละงาน แล้วส่งงานต่อไปยัง Scheduler
 * - บันทึกเวลาที่งานเข้าสู่ระบบ "จริง" ลงใน Job
 * (อาจไม่ตรงกับ arrivalMs เป๊ะ เพราะ Thread ถูกปลุกช้าได้)
 * - เรียก logger.jobArrived(job) ทุกครั้งที่ปล่อยงาน
 *
 * ข้อควรคิด:
 * - รายการงานที่ได้จาก WorkloadLoader เรียงตามลำดับในไฟล์ ไม่ได้เรียงตามเวลา
 * - เมื่อปล่อยงานครบทุกชิ้นแล้ว ต้องมีวิธีบอกระบบว่า "จะไม่มีงานเข้ามาอีก"
 * ดู TODO เรื่องการปิดระบบใน Main
 */
public class JobGenerator extends Thread {

    // TODO: เก็บรายการงาน, ช่องทางส่งงานไปยัง Scheduler และ logger
    //
    // หมายเหตุ: constructor ด้านล่างยังไม่มี parameter สำหรับ "ช่องทางส่งงาน"
    // เพราะเป็นสิ่งที่กลุ่มต้องออกแบบเอง (หัวข้อ 2 ห้ามให้ JobGenerator
    // ใส่งานลง ReadyQueue โดยตรง ต้องผ่าน Scheduler เสมอ)
    // ให้เพิ่ม parameter เข้าไปตามที่ออกแบบ เช่น BlockingQueue<Job>
    // หรือคลาสของกลุ่มเอง — เพิ่ม parameter ได้ แต่อย่าเปลี่ยนชื่อคลาส

    public final List<Job> jobs;
    public final BlockingQueue<Job> arrivalQueue;
    public final ProjectLogger logger;
    public final long simulationStart;
  
    
    public JobGenerator(List<Job> jobs, ProjectLogger logger, BlockingQueue<Job> arrivaQueue, long simulationStart) {
        super("generator");
        // TODO
        this.jobs = jobs;
        this.arrivalQueue = arrivaQueue;
        this.logger = logger;
        this.simulationStart = simulationStart;
    }

    @Override
    public void run() {
        // TODO: วนปล่อยงานตามเวลา แล้วแจ้งเมื่อปล่อยครบ

        try {
            // jobs คือลำดับตามไฟล์ CSV  ไม่ใช่ลำดับเวลา
            // เรียง jobs ตาม arrivalMs ก่อนเริ่ม loop 
            List<Job> sorted = new ArrayList<>(jobs);
            sorted.sort(Comparator.comparingLong(j -> j.arrivalMs));
            for (Job job : sorted) {
                // คำนวณเวลาที่ต้องรอจนถึง arrivalMs
                long targetTime = simulationStart + job.arrivalMs;
                long currentTime = logger.now();
                long waitTime = targetTime - currentTime;

                if (waitTime > 0) {
                    Thread.sleep(waitTime);
                }

                // บันทึกเวลาที่มาถึงจริง และแจ้ง Logger
                long actualArrival = logger.now();
                job.actualArrivalTime = actualArrival;
                logger.jobArrived(job);

                // ส่ง Job เข้า arrivalQueue ให้ Scheduler มารับไป
                arrivalQueue.put(job);

            }
            // ส่งงานครบแล้ว ส่ง Poison Pill บอก Scheduler ว่าจบแล้ว
            arrivalQueue.put(Job.POISON_PILL);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
