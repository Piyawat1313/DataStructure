import java.util.List;

/**
 * รวบรวมและคำนวณค่าที่ใช้วัดผลของการรันหนึ่งครั้ง
 *
 * ===== ไฟล์นี้เป็นโครงเปล่า นักศึกษาต้องเขียนเอง =====
 *
 * ข้อกำหนดจากโจทย์ที่เกี่ยวกับคลาสนี้ (หัวข้อ 8):
 *   - Waiting Time, Turnaround Time, Throughput, Resource Wait Time
 *   - ต้องถูกอัปเดตจากหลาย Worker พร้อมกันได้อย่างปลอดภัย
 *   - ผลต้องสอดคล้องกับสมการตรวจสอบ:
 *       Turnaround = Waiting + workMs + Resource Wait + resourceMs
 *     ใช้สมการนี้ตรวจงานทีละชิ้นได้ว่าค่าไหนคำนวณผิด
 *
 * ข้อควรระวัง: ค่าเฉลี่ยของ Resource Wait ให้คิดเฉพาะงานที่ใช้ resource
 * ส่วนงานที่ resource = NONE ให้ถือว่า Resource Wait เป็น 0
 */
public class Statistics {

    // ใช้ตัวแปรสำหรับผลรวม Thread-Safe โดยใช้ Block Synchronized
    private int completedJobs = 0;
    private long totalWaitingTime = 0;
    private long totalTurnaroundTime = 0;

    private int resourceJobCount = 0;
    private long totalResourceWaitTime = 0;

    /** บันทึกว่างานชิ้นหนึ่งเสร็จแล้ว เรียกโดย Worker หลายตัวพร้อมกันได้ */
    public synchronized void recordCompletion(Job job) {
        completedJobs++;

        // เรียกใช้ method จากคลาส Job ที่ผูกสูตรคำนวณไว้แล้ว
        totalWaitingTime += job.waitingTime();
        totalTurnaroundTime += job.turnaroundTime();

        // ค่าเฉลี่ยของ Resource Wait ให้คิดเฉพาะงานที่ใช้ resource
        if (job.resource != ResourceType.NONE) {
            resourceJobCount++;
            totalResourceWaitTime += job.resourceWaitTime();
        }

        // ตรวจสอบความถูกต้องของสมการตรวจสอบ (Turnaround = Waiting + workMs + Resource Wait + resourceMs)
        if (!job.verifyEquation()) {
            System.err.printf("Error: Turnaround time mismatch for job %s.%n", job.id);
        }
    }

    /** จำนวนงานที่เสร็จแล้ว ใช้โดย Monitor และใช้ตรวจว่างานครบหรือยัง */
    public synchronized int completedCount() {
        return completedJobs;
    }

    /**
     * พิมพ์ตารางสรุปผลตอนจบโปรแกรม
     * อย่างน้อยต้องมี avg Waiting Time, avg Turnaround Time,
     * Throughput และ avg Resource Wait Time
     *
     * ตามหัวข้อ 14 ให้รายงานเวลาเป็นจำนวนเต็มหน่วย ms
     * และ Throughput อย่างน้อย 2 ตำแหน่งทศนิยม
     */
    public synchronized void printSummary(List<Job> allJobs, long makespanMs) {
        if (completedJobs == 0) {
            System.out.println("No jobs completed. Cannot compute statistics.");
            return;
        }

        // คำนวณค่าเฉลี่ยหน่วย ms
        long avgWaitingTime = totalWaitingTime / completedJobs;
        long avgTurnaroundTime = totalTurnaroundTime / completedJobs;
        long avgResourceWaitTime = resourceJobCount > 0 ? totalResourceWaitTime / resourceJobCount : 0;

        // คำนวณ Throughput (jobs/sec)
        double makespanSeconds = makespanMs / 1000.0;
        double throughput = completedJobs / makespanSeconds;

        System.out.println("===== Summary =====");
        System.out.printf("Completed Jobs: %d%n", completedJobs);
        System.out.printf("Average Waiting Time: %d ms%n", avgWaitingTime);
        System.out.printf("Average Turnaround Time: %d ms%n", avgTurnaroundTime);
        System.out.printf("Throughput: %.2f jobs/sec%n", throughput);
        System.out.printf("Average Resource Wait Time: %d ms%n", avgResourceWaitTime);
    }
}