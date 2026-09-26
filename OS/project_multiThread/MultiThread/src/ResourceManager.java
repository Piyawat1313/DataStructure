import java.util.concurrent.Semaphore;

/**
 * ควบคุมสิทธิ์การใช้ทรัพยากรร่วมของทั้งระบบ
 *
 * ===== ไฟล์นี้เป็นโครงเปล่า นักศึกษาต้องเขียนเอง =====
 *
 * ข้อกำหนดจากโจทย์ที่เกี่ยวกับคลาสนี้:
 *   - หัวข้อ 5: ใช้ Semaphore ควบคุม PRINTER และ DATABASE
 *     จำนวน permit มาจาก command line (Config)
 *     ในส่วนบังคับให้สร้าง Semaphore แบบ fair = true
 *   - Worker ทุกตัวต้องใช้ ResourceManager object เดียวกัน
 *   - หัวข้อ 7: permit ต้องไม่สูญหายหรือค้าง แม้เกิด exception
 *     หรือถูก interrupt ระหว่างถือ resource
 *
 * คำถามที่จะถูกถามใน Demo:
 *   - ทำไมต้อง fair = true และถ้าเปลี่ยนเป็น false จะเกิดอะไรขึ้น
 *   - ถ้า Thread ถูก interrupt หลัง acquire สำเร็จแต่ก่อน release
 *     โค้ดของกลุ่มยังคืน permit ได้หรือไม่
 */
public class ResourceManager {

    // TODO: เก็บ Semaphore ของ PRINTER และ DATABASE
    public final Semaphore printerSemaphore;
    public final Semaphore databaseSemaphore;

    /*เก็บค่า totalpermit แยกเป็นอีก filed */
    public final int printerTotal;
    public final int databaseTotal;

    public ResourceManager(int printerPermits, int databasePermits) {
        // TODO
        // ใช้ Semaphore ควบคุม PRINTER และ DATABASE
        printerSemaphore = new Semaphore(printerPermits, true);
        databaseSemaphore = new Semaphore(databasePermits, true);
        this.printerTotal = printerPermits;
        this.databaseTotal = databasePermits;
    }

    /** ขอสิทธิ์ใช้ทรัพยากร จะรอจนกว่าจะได้ */
    public void acquire(ResourceType type) throws InterruptedException {
        // TODO
        getSemaphore(type).acquire();
    }

    /*เช็คสถานะของ Semaphore */
    private Semaphore getSemaphore(ResourceType type){
        switch (type) {
            case PRINTER:
                return printerSemaphore;
            case DATABASE:
                return databaseSemaphore;
            default:
                    throw new IllegalArgumentException("resource NONE ไม่มี semaphore ให้ acquire");
        }
    }

    /** คืนสิทธิ์ใช้ทรัพยากร */
    public void release(ResourceType type) {
        // TODO
        getSemaphore(type).release();
    }

    /**
     * ข้อความสั้น ๆ บอกสถานะการใช้ทรัพยากร สำหรับส่งให้ ProjectLogger.monitor()
     * เช่น "printer=1/1 database=0/2"
     */
    public String status() {
        // TODO
        int printerInUse = printerTotal - printerSemaphore.availablePermits();
        int databaseInuse = databaseTotal - databaseSemaphore.availablePermits();
        return  String.format("printer=%d/%d  database=%d/%d", printerInUse,printerTotal, databaseInuse, databaseTotal);
    }
}
