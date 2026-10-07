import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * คิวงานที่พร้อมถูกหยิบไปทำ
 *
 * ===== ไฟล์นี้เป็นโครงเปล่า นักศึกษาต้องเขียนเอง =====
 *
 * สิ่งที่คลาสนี้ต้องทำได้:
 *   - เก็บงานที่รอ Worker อยู่
 *   - หยิบงานถัดไปตามนโยบายที่เลือก (FCFS หรือ Priority)
 *   - ถูกเรียกจากหลาย Thread พร้อมกันได้อย่างปลอดภัย
 *
 * ข้อกำหนดจากโจทย์ที่เกี่ยวกับคลาสนี้:
 *   - หัวข้อ 4: priority = 1 สูงสุด เมื่อเท่ากันต้องมีกติกาตัดสินลำดับ (tie-break)
 *     ที่ตัดสินจากข้อมูลของ Job ไม่ขึ้นกับว่า Thread ใดเข้าถึงคิวก่อน
 *   - หัวข้อ 7: ห้ามวนลูปเช็กแบบกิน CPU (busy waiting) — Worker ที่ไม่มีงานทำ
 *     ต้องถูกพักไว้ ไม่ใช่วนถามซ้ำ ๆ
 *
 * จะออกแบบเป็นคลาสเดียวที่รับนโยบายเข้ามา หรือแยกเป็นสองคลาส
 * หรือใช้โครงสร้างข้อมูลสำเร็จรูปของ Java ก็ได้ ขอให้อธิบายเหตุผลได้ใน Demo
 */
public class ReadyQueue {

    // TODO: เก็บนโยบาย (Config.Policy) และโครงสร้างข้อมูลที่ใช้เก็บงาน

    public final BlockingQueue<Job> queue;

    public ReadyQueue(Config.Policy policy) {
        // TODO
        if (policy == Config.Policy.PRIORITY) {
            this.queue = new PriorityBlockingQueue<>(11, buildComparator(policy));
        }
        else{
            this.queue = new LinkedBlockingQueue<>();
        }
        
    }

    private static Comparator<Job> buildComparator(Config.Policy policy){
        Comparator<Job> base;

        // FCFS: ใครมาก่อนได้ก่อน ใช้ sequence เป็นตัวตัดสิน
        if (policy == Config.Policy.PRIORITY) {
            // Priority: priority น้อย = สำคัญมาก ถ้าเท่ากัน tie-break ด้วย sequence
            base = Comparator
                    .comparingInt((Job job) -> job.priority)
                    .thenComparingInt(job -> job.sequence);
                }
                else{
                    base = Comparator.comparing(Job -> Job.sequence);       
        }
        // pill ต้องอยู่ท้ายสุดเสมอ ไม่ว่านโยบายใด
        return (a, b) -> {
            boolean pa = (a == Job.POISON_PILL), pb = (b == Job.POISON_PILL);
            if (pa && pb) {
                return 0;
            }
            else if (pa) {
                return 1;
            }
            else if (pb) {
                return  -1;
            }
            return base.compare(a, b);
        };

    }

    /** ใส่งานเข้าคิว เรียกโดย Scheduler Thread */
    public void add(Job job) {
        // TODO
        queue.add(job);
    }

    /**
     * หยิบงานถัดไปตามนโยบาย เรียกโดย Worker Thread
     *
     * ถ้ายังไม่มีงาน ต้องรอโดยไม่กิน CPU
     * ต้องคิดด้วยว่าจะบอก Worker อย่างไรเมื่อไม่มีงานเหลือแล้วและควรหยุดทำงาน
     */
    public Job take() throws InterruptedException {
        // TODO
        return queue.take();
    }

    /** จำนวนงานที่รออยู่ตอนนี้ ใช้โดย Monitor — ต้องอ่านได้อย่างปลอดภัย */
    public int size() {
        // TODO
        return queue.size();
    }

    /* แจ้งว่า "จะไม่มีงานใหม่แล้ว" — ใส่ pill ลงท้ายคิว (Scheduler เรียก / Worker เรียกเพื่อส่งต่อ)*/
    public void close(){
        queue.add(Job.POISON_PILL);
    }
}
