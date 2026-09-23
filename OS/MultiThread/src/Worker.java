

/**
 * Thread ที่ดึงงานจาก Ready Queue ไปทำจนเสร็จ
 *
 * ===== ไฟล์นี้เป็นโครงเปล่า นักศึกษาต้องเขียนเอง =====
 *
 * ลำดับการทำงานของ Job หนึ่งชิ้น บังคับตามหัวข้อ 6 ของโจทย์:
 *   1. รับงานจาก Ready Queue แล้วบันทึกเวลาเริ่ม
 *   2. จำลองงานหลักด้วย Thread.sleep(job.workMs)
 *   3. ถ้า job.resource != NONE ให้บันทึกเวลาเริ่มรอ แล้ว acquire
 *   4. จำลองการถือครองด้วย Thread.sleep(job.resourceMs)
 *   5. release แล้วบันทึกเวลาจบ
 *
 * ห้ามสลับขั้นที่ 2 กับ 3 เพราะจะทำให้ผลของทุกกลุ่มเทียบกันไม่ได้
 *
 * จุดที่มักพลาด:
 *   - ถ้า exception หรือ interrupt เกิดขึ้นหลัง acquire แต่ก่อน release
 *     permit จะค้างถาวรและระบบจะแขวน ต้องออกแบบให้คืนได้เสมอ
 *   - Worker ต้องหยุดเองได้เมื่อไม่มีงานเหลือแล้ว ไม่ใช่วนรอตลอดไป
 */
public class Worker extends Thread {

    // TODO: เก็บ ReadyQueue, ResourceManager, Statistics และ logger
   public final ReadyQueue readyQueue;
   public final ResourceManager resource;
   public final Statistics statistics;
   public final ProjectLogger logger;

    public Worker(String name, ReadyQueue readyQueue, ResourceManager resources,
                  Statistics statistics, ProjectLogger logger)throws UnsupportedOperationException {
        super(name);
        // TODO
        this.readyQueue = readyQueue;
        this.resource = resources;
        this.statistics = statistics;
        this.logger = logger;
    }

    @Override
    public void run() {
        // TODO: วนรับงานและเรียก processJob จนกว่าจะได้รับสัญญาณให้หยุด
        try {
            while (true) {
                Job job = readyQueue.take();

                // เช็คว่างานเหลือหรือไม่
                if (job == Job.POISON_PILL) {
                    readyQueue.close(); //ใส่ POISON_PILL กลับเข้าคิวส่งต่อให้ Worker ตัวอื่น
                    break;
                }

                processJob(job);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.systemEvent(getName()+ " finished, no more jobs");
    }

    /** ทำงานหนึ่งชิ้นให้จบตามลำดับ 5 ขั้นด้านบน */
    private void processJob(Job job) throws InterruptedException {
        // TODO
        
        // บันทึกเวลาเริ่มงาน
        job.startTime = logger.now();
        logger.jobStarted(job);

        //  จำลองงานหลักด้วย Thread.sleep(job.workMs)
        Thread.sleep(job.workMs);
        logger.workFinished(job);

        // ถ้า job.resource != NONE ให้บันทึกเวลาเริ่มรอ แล้ว acquire
        if (job.resource != ResourceType.NONE) {
            job.resourceWaitStartTime = logger.now();
            logger.resourceWaitStarted(job);
            resource.acquire(job.resource);

            job.resourceAcquiredTime = logger.now();
            logger.resourceAcquired(job, job.resourceWaitTime());

            //  จำลองการถือครองด้วย Thread.sleep(job.resourceMs)
            try {
                Thread.sleep(job.resourceMs);
            } finally{
                // ต้อง release เสมอ ไม่ว่า sleep จะจบปกติหรือโดน interrupt
                resource.release(job.resource);
                logger.resourceReleased(job);
            }
        }
        // บันทึกเวลาจบ
        job.finishTime = logger.now();
        statistics.recordCompletion(job);
        logger.jobCompleted(job);

    }
}
