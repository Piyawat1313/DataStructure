import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * รวบรวมและคำนวณค่าที่ใช้วัดผลของการรันหนึ่งครั้ง
 *
 * ===== ไฟล์นี้เป็นโครงเปล่า นักศึกษาต้องเขียนเอง =====
 *
 * ข้อกำหนดจากโจทย์ที่เกี่ยวกับคลาสนี้ (หัวข้อ 8):
 * - Waiting Time, Turnaround Time, Throughput, Resource Wait Time
 * - ต้องถูกอัปเดตจากหลาย Worker พร้อมกันได้อย่างปลอดภัย
 * - ผลต้องสอดคล้องกับสมการตรวจสอบ:
 * Turnaround = Waiting + workMs + Resource Wait + resourceMs
 * ใช้สมการนี้ตรวจงานทีละชิ้นได้ว่าค่าไหนคำนวณผิด
 *
 * ข้อควรระวัง: ค่าเฉลี่ยของ Resource Wait ให้คิดเฉพาะงานที่ใช้ resource
 * ส่วนงานที่ resource = NONE ให้ถือว่า Resource Wait เป็น 0
 */
public class Statistics {

    // TODO: เก็บข้อมูลของงานที่เสร็จแล้ว หรือเก็บผลรวมไว้คำนวณทีหลัง
    public final List<Job> completedJobs = new ArrayList<>();

    /** บันทึกว่างานชิ้นหนึ่งเสร็จแล้ว เรียกโดย Worker หลายตัวพร้อมกันได้ */
    public synchronized void recordCompletion(Job job) {
        // TODO
        completedJobs.add(job);
    }

    /** จำนวนงานที่เสร็จแล้ว ใช้โดย Monitor และใช้ตรวจว่างานครบหรือยัง */
    public synchronized int completedCount() {
        // TODO
        return completedJobs.size();
    }

    // Monitor เรียกอ่าน
    public synchronized int getCompletedCount(){
        return completedJobs.size();
    }

    /**
     * พิมพ์ตารางสรุปผลตอนจบโปรแกรม
     * อย่างน้อยต้องมี avg Waiting Time, avg Turnaround Time,
     * Throughput และ avg Resource Wait Time
     *
     * ตามหัวข้อ 14 ให้รายงานเวลาเป็นจำนวนเต็มหน่วย ms
     * และ Throughput อย่างน้อย 2 ตำแหน่งทศนิยม
     */
    public void printSummary(List<Job> allJobs, long makespanMs) {
        // TODO
        long totalWaiting = 0;
        long totalTurnaround = 0;
        long totalResourceWait = 0;
        int resourceJobCount = 0;

        for (Job job : allJobs) {
            totalWaiting += job.waitingTime();
            totalTurnaround += job.turnaroundTime();
            if (job.resource != ResourceType.NONE) {
                totalResourceWait += job.resourceWaitTime();
                resourceJobCount++;
            }
        }
        long avgWaiting = totalWaiting / allJobs.size();
        long avgTurnaround = totalTurnaround / allJobs.size();
        long avgResourceWait = 0;
        if (resourceJobCount > 0) {
            avgResourceWait = totalResourceWait / resourceJobCount;
        }
        double throughput = allJobs.size() * 1000.0 / makespanMs;
        System.out.printf("Average Waiting Time: %.2f ms%n", ((double)avgWaiting));
        System.out.printf("Average Turnaround Time: %.2f ms%n", ((double)avgTurnaround));
        System.out.printf("Throughput: %.2f jobs/sec%n", throughput);
        System.out.printf("Average Resource Wait Time: %.2f ms%n", ((double)avgResourceWait));
    }
}
