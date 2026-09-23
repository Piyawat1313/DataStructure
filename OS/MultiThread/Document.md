# วิธีการรันไฟล์
1. ให้เข้าไปที่โฟลเดอร์ src โดยใช้คำสั่ง

```bash
cd src
```
2. ใช้ไฟล์ที่ชื่อว่า LoadTestJobs.java ในการรันโดยใช้คำสั่งตามนี้

```bash
java LoadTestJobs.java data/ชื่อไฟล์.csv
```
3. ถ้าข้อมูลขึ้นแบบนี้แสดงว่ารันงานได้ถูกต้อง
 
```text
Loaded 10 job(s): 
 Job{id=J01, arrival=0, priority=5, workMs=2500, resource=NONE, resourceMs=0, seq=0}
 Job{id=J02, arrival=100, priority=5, workMs=2400, resource=PRINTER, resourceMs=1200, seq=1}
 Job{id=J03, arrival=200, priority=5, workMs=2500, resource=NONE, resourceMs=0, seq=2}
 Job{id=J04, arrival=300, priority=5, workMs=2400, resource=DATABASE, resourceMs=1000, seq=3}
 Job{id=J05, arrival=400, priority=5, workMs=2500, resource=PRINTER, resourceMs=1200, seq=4}
 Job{id=J06, arrival=500, priority=4, workMs=2400, resource=DATABASE, resourceMs=1000, seq=5}
 Job{id=J07, arrival=1000, priority=1, workMs=700, resource=PRINTER, resourceMs=900, seq=6}
 Job{id=J08, arrival=1200, priority=1, workMs=600, resource=PRINTER, resourceMs=900, seq=7}
 Job{id=J09, arrival=1400, priority=1, workMs=700, resource=DATABASE, resourceMs=900, seq=8}
 Job{id=J10, arrival=1600, priority=2, workMs=600, resource=NONE, resourceMs=0, seq=9}
```
4. รันแล้วเกิดแบบนี้แสดงว่าเราใช้ Path ผิดต้องทำใหม่

```text
Exception in thread "main" java.io.FileNotFoundException: \data\jobs_single.csv (The system cannot find the path specified)
        at java.base/java.io.FileInputStream.open0(Native Method)
        at java.base/java.io.FileInputStream.open(FileInputStream.java:219)
        at java.base/java.io.FileInputStream.<init>(FileInputStream.java:159)
        at java.base/java.io.FileInputStream.<init>(FileInputStream.java:112)
        at java.base/java.io.FileReader.<init>(FileReader.java:60)
        at WorkLoader.load(WorkLoader.java:11)
        at LoadTestJobs.main(LoadTestJobs.java:10)
```

## คำถามเน้นความเข้าใจ
1. ใช้เวลาจากนาฬิกาตัวไหน (ดู ProjectLogger.now() ซึ่งให้เวลาฐานเดียวกับที่ปรากฏใน log ทำให้ค่าที่วัดกับ log ตรวจสอบกันได้)
    - ANS: ใช้ ProjectLogger.now() เพียงตัวเดียวตลอดทั้งโปรแกรม
    - เหตุผล: ถ้าใช้ System.nanoTime()ในโค้ดแต่ log พิมนาฬิกาอีกตัวตัวเลขสองชุดนี้จะเทียบกันไม่ได้โดยครงเพราะใช้นาฬิกาคนละตัว
    - สิ่งที่ต้องทำ: ทุกจุดที่ต้องบันทึกเวลาต้องเรียก ProjectLogger.now() เก็บค่านั้นไว้ ห้ามเรียกนาฬิกาของ JVM โดยตรง

2. ฟิลด์ใดถูกเขียนโดย Thread หนึ่งแล้วอ่านโดยอีก Thread หนึ่งและต้องป้องกันอย่างไร
    |Filed|Write|Read|เหตุผล|
    |-----|-----|----|-----|
    |actualArrivalTime|Dispatcher/Producer Thread thread ที่ปล่อยงานเข้าคิวตามเวลา arrivalMs|Worker Thread หยิบงานนี้ไปคำนวณ Waiting|งานที่ถูกสร้างและปล่อยเข้าคิวโดย Thread หนึ่ง แต่ถูกหยิบไปประมวลผลโดย Thread อีกตัว|
    |startTime|Worker thread|Worker thread เดิม คำนวณ waiting ทันทีและ Thread ที่มาสรุปผลรวมตอนท้ายคือ main thread หรือ stats collector|เขียนครั้งเดียวตอนเริ่ม อ่านซ้ำตอนสรุปรายงาน|
    |resourceWaitStartTime, resourceAcquiredTime|Worker Thread ตอนขอได้ resource จาก resource manager ใช้ร่วมกันหลาย Thread| Worker Thread, Thread สรุปผล|เหมือนกัน|
    |finishTime|Worker thread|Main thread, stats thread ที่รอ join แล้วมาพิมรายงาน|เป็น filed ที่สำคัญที่สุด เพะราใช้ในการคำนวณ Turnaround|

    - วิธีป้องกัน มีอยู่ 4 กรณี
        1. อ่านหลังจาก worker thread นั้นทำงานเสร็จสมบูรณ์แล้วเท่านั้น
            - ตัวอย่าง: 
                ```text
                main thread เรียก thread.join() หรือใช้ ExecutorService แล้ว awaitTermination()/Future.get() ก่อนจะไปอ่านค่าจาก Job object
                ```
            - ป้องกัน: ไม่ต้องใช้ volatile หรือ synchronized
            - สาเหตุ: 
                ```text
                    Thread.join() และ Future.get() สร้าง happens-before relationship ให้เองตาม Java Memory Model: ทุกอย่างที่ thread ลูกเขียนก่อน terminate จะถูกมองเห็นโดย thread ที่ join สำเร็จแล้ว
                ```
        
        2. อ่านค่าแบบ "สด" ระหว่างที่ worker ยังทำงานอยู่
            - ตัวอย่าง: 
                ```text
                ทำ live dashboard, มี thread ที่คอย poll สถานะงานระหว่างรัน หรือ progress bar
                ```
            - ป้องกัน: ใช้ volatile กับทุก field
            - สาเหตุ: 
                ```text
                ไม่มี synchronization point ใดๆ คอยการันตี visibility — thread อ่านอาจเห็นค่า default (0) ค้างอยู่ทั้งที่ worker เขียนไปแล้ว
                ```
        
        3. ใช้ queue ส่งต่องาน
            - ตัวอย่าง:
                ```text
                    producer ใส่ actualArrivalTime แล้ว put ลง BlockingQueue, worker take() ออกมา
                ```
            - ป้องกัน: ไม่ต้อง volatile สำหรับ actualArrivalTime
            - สาเหตุ: 
                ```text
                    BlockingQueue.put()/take() มี happens-before guarantee ในตัวอยู่แล้วแต่ field ที่ worker เขียนเองทีหลังต้องดูตามกรณี 1/2 ข้างบนแยกกัน
                ```
        4. กรณีที่ไม่มั่นใจเรื่องโครงสร้าง
            - ป้องกัน: ใส่ volatile ใส่ทุก filed ที่เราสร้างขึ้น

3. ทำไมใช้ PriorityBlockingQueue เป็นฐานเดียวรองรับทั้ง FCFS และ Priority
    - ANS: 
        ```text
            PriorityBlockingQueue คือ blocking queue ที่เรียงลำดับตาม Comparator เสมอ ถ้าให้ comparator เรียงตาม sequence อย่างเดียว ผลลัพธ์ก็คือพฤติกรรมแบบ FIFO
        ```

4. ทำไม tie-break ใช้ sequence ไม่ใช้เวลาจริง (actualArrivalTime หรือ System.nanoTime())
    - ANS: 
        ```text
            ถ้าใช้เวลาจริงในการตัดสิน ผลลัพธ์อาจสลับกันไปมาระหว่างการรันแต่ละครั้งเพราะ OS scheduling ของ Thread ไม่ deterministic
            sequence เป็นค่า final ที่ตั้งตายตัวตั้งแต่อ่านจากไฟล์ CSV จึงให้ผลเหมือนเดิมทุกครั้งที่รันด้วย workload เดียวกัน
        ```

5. ทำไม take() แค่ return queue.take() เฉยๆ โดยไม่เช็ค poison pill เอง
    - ANS:
        ```text
            ReadyQueue เป็น โครงสร้างข้อมูลทั่วไป ไม่ควรรู้จัก "ความหมาย" ของ poison pill หน้าที่คือเช็คว่า "นี่คือสัญญาณจบหรือ Job จริง" ควรอยู่ที่ Worker
        ```

6. ทำไมต้องใส่ poison pill "เท่ากับจำนวน Worker" 
    - ANS:
        ```text
            queue.take() เป็น consume แบบตัวใครตัวมัน
        ```
    - ตัวอย่าง:
        ```text
            pill 1 ตัวจะถูก Worker เพียงตัวเดียวหยิบไปแล้วหายจาก queue ถ้ามี Worker 5 ตัว ต้องมี pill 5 ตัว ไม่งั้น Worker ตัวที่ไม่ได้ pill จะ block ค้างอยู่ที่ take() ตลอดไป โปรแกรมจะไม่จบ
        ```
7. ทำไม size() ไม่ต้องเขียน synchronized เอง
    - ANS:
        ```text
            PriorityBlockingQueue.size() เป็น thread-safe อยู่แล้วในตัว internal lock จัดการให้ จึงเรียกตรงๆ ได้เลยโดยไม่ต้องเพิ่ม synchronization ชั้นนอกอีกชั้น
        ```

## วิธี redirect log ลงไฟล์
1. สร้างฌฟลเดอร์ logs ภายในฌฟลเดร์ src
2. รันคำสั่ง java Main workloads/jobs_standard.csv fcfs 3 1 2 > logs\ชื่อไฟล์.log มันจะทำการเขียนไฟล์ .log ในโฟลเดอร์ logs
3. วิธีนับ jobCompleted ใช้คำสั่ง
    ```bash
    findstr /c:"JOB_COMPLETED" logs\standard_fcfs_w3.log | find /c /v ""
    ```
4. วิธีนับ "RESOURCE_ACQUIRED" ใช้คำสั่ง
    ```bash
        findstr /c:"RESOURCE_ACQUIRED" logs\standard_fcfs_w3.log | find /c /v ""
    ```
5. วิธีนับ RESOURCE_RELEASED
    ```bash
        findstr /c:"RESOURCE_RELEASED" logs\standard_fcfs_w3.log | find /c /v ""
    ```
6. วิธีดูว่าช่วงใดที่ PRINTER ถูกถือเกินจำนวน permit ใช้คำสั่ง
    - เข้าไปที่โฟลเดอร์งาน 
    ```bash
        cd path
    ```
    - ตรวจสอบ
    ```powershell
        $log = "logs\standard_fcfs_w3.log"
        $permits = @{ PRINTER = 1; DATABASE = 2 }   # ให้ตรงกับ argument ที่รัน
        $cur = @{ PRINTER = 0; DATABASE = 0 }
        $max = @{ PRINTER = 0; DATABASE = 0 }

        Get-Content $log | ForEach-Object {
        if ($_ -match 'RESOURCE_ACQUIRED\s+job=\w+ resource=(\w+)') {
        $r = $Matches[1]; $cur[$r]++
        if ($cur[$r] -gt $max[$r]) { $max[$r] = $cur[$r] }
        }
        elseif ($_ -match 'RESOURCE_RELEASED\s+job=\w+ resource=(\w+)') {
        $cur[$Matches[1]]--
        }
    }

    foreach ($r in "PRINTER","DATABASE") {
        $ok = if ($max[$r] -le $permits[$r]) { "OK" } else { "เกิน!" }
        "$r  max พร้อมกัน=$($max[$r])  permit=$($permits[$r])  $ok"
    }
    ```


## ผลที่ได้ 
|#|Workload / Policy|Workers|Printer|DB|avg WatingTime|avg Turnaround Time|Throughput|avg RW|
|----|-----|----|-----|-----|-----|-----|-----|-----|
|1|standard / FCFS |3|1|2|2959|5466|1.14|73|
|2|standard / Priority|3|1|2|2335|4975|1.08|262|
|3|standard / Priority|1|1|2|8521|10978|0.41|1|
|4|standard / Priority|5|1|2|1130|4096|1.29|728|
|5|printer / Priority|3|1|2|1140|4250|0.80|1618|
|6|printer / Priority|3|2|2|757|2539|1.44|296|
|7|standard / Priority|3|1|2|2339|4982|1.08|264|
