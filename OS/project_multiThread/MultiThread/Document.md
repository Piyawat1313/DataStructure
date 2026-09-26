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
    - เหตุผล: ถ้าใช้ System.nanoTime()ในโค้ดแต่ log พิมนาฬิกาอีกตัวตัวเลขสองชุดนี้จะเทียบกันไม่ได้โดยตรงเพราะใช้นาฬิกาคนละตัว
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

8. ทำไมใน ReadyQueue.buildComparator ต้องเขียนเช็ค POISON_PILL แยกออกมาต่างหาก ถ้าไม่เช็คจะเกิดอะไรขึ้น
    - ANS: 
        ```text
            pill มีค่า priority/sequence = -1 ซึ่งเป็นค่าที่ไม่ได้มีความหมายเป็น "งานสำคัญที่สุด" แต่ดันชนะทุก comparator ปกติถ้าไม่แยกเช็ค pill จะโดน sort ขึ้นไปแซงหน้า Job จริงทุกตัว ทำให้ Worker หยุดทั้งที่ยังมีงานค้าง
        ```
9. อธิบายกลไก "poison pill relay" ใน Worker.run() — ทำไม Scheduler ใส่ pill แค่ 1 ใบ แต่ Worker N ตัวหยุดได้ครบทุกตัว
    - ANS: 
        ```text
            pill ไม่ได้หยุด Worker ทุกตัวพร้อมกัน แต่แต่ละตัวที่เจอ pill จะ 'ส่งต่อ' ให้ตัวถัดไปก่อนตายเอง โดยเรียก readyQueue.close() ใส่ pill กลับเข้าคิวอีกใบทุกครั้งที่เจอ ทำให้เกิดเป็นลูกโซ่ ไปจนกว่า Worker ตัวสุดท้ายจะได้รับและหยุดตัวเอง วิธีนี้ทำให้ Scheduler ไม่ต้องรู้จำนวน Worker ล่วงหน้า
       ```

10. ทำไม Job ใช้ volatile กับ field เวลาแทนที่จะใช้ synchronized หรือ AtomicLong
    - ANS: 
        ```text
            เพราะ filed เวลาถูกเข้าถึงหรือแก้ไขโดย Thread หลายๆ Thread พร้อมกัน เพื่อป้องกันปัญหา Race Condition 
            ต้องอ่านค่าล่าสุดที่ Worker เขียนไปให้ได้แน่นอน ไม่ใช่ค่าที่ค้างอยู่ใน cache ของ core อื่น
        ```

11. ResourceManager.acquire() ไม่มี try/catch ครอบ แต่ Worker.processJob() ใช้ try/finally ครอบเฉพาะช่วง sleep หลัง acquire — ถ้า interrupt เกิดขึ้น ระหว่าง acquire() เอง (ยังไม่ได้ permit) จะเกิดอะไร ต้อง release ไหม
    - ANS:
        ```text
            ไม่ต้อง release ครับ เพราะ Semaphore.acquire() เป็น atomic operation แบบ all-or-nothing — ถ้าถูก interrupt ระหว่างรอ มันจะ throw InterruptedException ทันทีโดยไม่ได้ permit ไปเลย ไม่มีสถานะกึ่งได้กึ่งไม่ได้ เพราะฉะนั้นไม่มี permit ค้างให้ต้องคืน exception จะถูกโยนทะลุขึ้นไปจนถึง Worker.run() ซึ่งจับไว้แล้ว set interrupt flag แล้วให้ thread จบตัวเอง ส่วน try/finally ที่มีอยู่ตอนนี้ถูกวางไว้ถูกจุดแล้ว คือครอบเฉพาะช่วงหลัง acquire สำเร็จจนถึงก่อน release ซึ่งเป็นช่วงเดียวที่ permit อยู่ในมือเราจริง ๆ ถ้าไปใส่ release ครอบ acquire ด้วยจะกลายเป็นบั๊กที่ทำให้ permit เกินจำนวนจริงแทน"
        ```
    - กลไกSemaphore.acquire()
        ```text
        java.util.concurrent.Semaphore.acquire() มี contract ที่ชัดเจนมาก:

        ถ้า thread ถูก interrupt ระหว่างรอ permit มันจะ throw InterruptedException ทันที และ ไม่ได้ permit ไปเลย ไม่มีการ "ได้ permit มาแล้วแต่โยน exception ทีหลัง"
        พูดง่าย ๆ คือ acquire() เป็น operation แบบ all-or-nothing:

        สำเร็จ → ได้ permit ไป 1 หน่วยแน่นอน
        ล้มเหลว (throw exception) → ไม่ได้ permit อะไรเลย ไม่มีอะไรค้างให้ต้องคืน    
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
|1|standard / FCFS |3|1|2|2957|5462|1.14|72|
|2|standard / Priority|3|1|2|2325.00|4958.00|1.08|265.00|
|3|standard / Priority|1|1|2|8504|10955|0.41|1.00|
|4|standard / Priority|5|1|2|1130|4096|1.29|728|
|5|printer / Priority|3|1|2|1140|4250|0.80|1618|
|6|printer / Priority|3|2|2|757|2539|1.44|296|
|7|standard / Priority|3|1|2|2339|4982|1.08|264|

## priority VS FCFS (Scheduling)
- Priority WT = timestamp(JOB_STARTED) − timestamp(JOB_ARRIVED)
- FCFS WT = timestamp(JOB_STARTED) − timestamp(JOB_ARRIVED)
- ผลต่าง = Priority WT - FCFS WT
- ถ้าค่าผลต่างติดลบแปลว่า มีผลกระทบ
- ถ้าผลต่างเป็นบวกแปลว่า ได้ประโยชน์


|Job|priority|Arrived|FCFS start|FCFS WT|Priority start|Priority WT|ผลต่าง priority - FCFS|
|-----|------|-----|-------|----|----|-----|-----|
|j01|5|29|36|6|34|5|+1|
|j02|5|129|133|3|132|3|0|
|j03|5|229-230|231|1|229|0|+1|
|j04|5|329|2541|2211|5382|5053|-2842|
|j05|5|429|2734|2304|5586|5157|-2853|
|j06|4|529-535|3756|3221|5288|4759|-1538|
|j07|1|1030-1031|5952|4921|2546|1516|+3405|
|j08|1|1228-1231|6438|5207|2731|1503|+3704|
|j09|1|1431-1430|7163|5733|3773|2342|+3391|
|j10|2|1629-1630|7571|5941|4676|3047|+2894|

#### job ไหนได้ประโยชน์
- ANS: j07, j08, j09, j10
#### Job ไหนมีผลกระทบ
- ANS: j04, j05, j06
#### Job ไหนที่ไม่ต่าง
- ANS: j01, j02, j03


## Worker Count 
- หลักการเช็ค "สัดส่วน" คือถ้า worker เพิ่มเป็น N เท่า Throughput ควรเพิ่มเป็น N เท่าด้วย
- Throughput_คาดหวัง = Throughput_เดิม × (Worker_ใหม่ / Worker_เดิม)
- % ที่ได้จริง = (Throughput_จริง / Throughput_คาดหวัง) × 100

|Worker|Throughput|Average Resource Wait Time|สัดส่วน|
|-----|-----------|--------------------------|------|
|1|0.41|1.00|0.41|
|3|1.08|259.00|88%|
|5|1.29|725.00|72%|

- สรุป:
    ```text
        เมื่อจำนวน worker 3-5 มีสัดส่วนที่น้อยลงมา เพราะว่า ระบบเริ่มเข้าใกล้ resource-bound มากกว่า worker-bound — ยิ่งเพิ่ม Worker จำนวน Job ที่พร้อมทำงานพร้อมกันยิ่งมาก แต่จำนวน permit ของ PRINTER (=1) และ DATABASE (=2) ยังคงเดิม ทำให้เกิดการแย่ง resource รุนแรงขึ้น สังเกตได้จาก avg Resource Wait ที่พุ่งขึ้นจาก 259ms เป็น 725ms (เกือบ 3 เท่า)
    ```


## จุดคอขวดของ resource 
- % ที่ลดลงของ avg RW = (RW_permit1 − RW_permit2) / RW_permit1 × 100
- % ที่เพิ่มของ Throughput = (TP_permit2 − TP_permit1) / TP_permit1 × 100

|printer|avg Resource Wait Time|Throughput|
|----------|----------------------|----------|
|1|1611.00|0.80|
|2|298.00|1.43|

- % ที่ลดลงของ avg RW = 82%
- % ที่เพิ่มของ Throughput = 79%

- สรุป จุดคอขวดของระบบย้ายไปอยู่ที่ใด
    ```text
        ถ้า avg RW เข้าใกล้ 0 → แปลว่า PRINTER เลิกเป็นคอขวดแล้ว
        ถ้า avg RW ไม่เข้าใกล้ 0 → แปลว่า PRINTER ยังเป็นคอขวดอยู่บางส่วน

        permit = 2 avg Resource wait ไปที่ 298 ms ยังไม่เข้าใกล้ 0 ดังนั้นยังเป็นคอขวดอยู่บางส่วน
    ```

- สรุป avg Resource Wait and Throughput เปลี่ยนอย่างไร
    ```text
        เมื่อเพิ่มจำนวน printer permit จาก 1 เป็น 2 avg Resource Wait Time ลดลงจาก 1611.00 จนถึง 298.00 คิดเป็น % ได้ 82% ของการลดลงของ avg RW ในส่วนของ Throughput เพิ่มขึ้นจาก 0.80 ถึง 1.43 คิดเป็นเปอรืเซ็นที่เพิ่มได้ 79%
    ```

## คำถาม Demo

1. เพิ่ม Worker จาก 3 เป็น 5 แล้ว Throughput เพิ่มตามสัดส่วนหรือไม่ และ avg Resource Wait เปลี่ยนอย่างไร เพราะอะไร?
    - ANS: 
        ```text
            หลังจากเพิ่ม worker จาก 3 เป็น 5 ทำให้สัดส่วน Throughput ลดลงมาเนื่องจาก ระบบเริ่มเข้าใกล้ resource-bound มากกว่า worker bound
            ทำให้เกิดการแย่ง resource มากขึ้น สามารถดูได้จาก avg Resource Wait Time ที่พุ่งขึ้นมาเป็น 3 เท่า
        ```

2. FCFS กับ Priority ทำให้ Job กลุ่มใดได้ประโยชน์หรือเสียประโยชน์จาก jobs_standard.csv? ให้อ้างอิง Waiting Time และลำดับ START ใน log และอธิบายด้วยว่าเหตุใด Priority Scheduling ในการทดลองนี้อาจมี Throughput ต่ำกว่า FCFS แม้ค่าเฉลี่ย Waiting Time จะดีกว่า  
    - ANS:
        ```text
            j07, j08, j09, j10 กลุ่มนี้ได้ประโยชน์ WT ลดลงมาก
            กลุ่มที่เสียประโยชน์มี j04, j05, j06 WT เพิ่มมากขึ้น

            FCFS: ลำดับ JOB_STARTED เรียงตาม arrival
            Priority: ลำดับ JOB_STARTED จริงในไฟล์ log คือ J01,J02,J03 (เริ่มพร้อมกันตอน worker ว่าง)
            J07 (start ที่ 2546ms, priority=1) J08 (2731ms) J09 (3773ms) J10 (4676ms) → แล้วค่อยมาถึง J04 (5382ms) และ J06 (5288ms), J05 (5586ms)
            priority ต่ำกว่า=สำคัญกว่า ได้ไปก่อนไม่ว่าจะมาถึงก่อนหรือหลัง

            Priority ทำให้ WT ดีขึ้นในภาพรวมของการรอคิวก่อนเริ่มงาน แต่ไปสร้างปัญหาใหม่คือ resource contention ที่หนักขึ้นในช่วงที่ Job priority เดียวกันถูกจัดให้ชนกัน
        ```


3. เมื่อเพิ่ม Printer permit จาก 1 เป็น 2 แล้ว avg Resource Wait และ Throughput เปลี่ยนอย่างไร และจุดคอขวดของระบบย้ายไปอยู่ที่ใด?
    - ANS:
        ```text
            เมื่อเปลี่ยน printer จาก 1 เป็น 2 ทำให้อัตราส่วนของ avg resource wait Time ลดลงมาถึง 82 % แต่ Throughput อัตราส่วนพิ่มขึ้นมาถึง 79% นั่นหมายความว่าจุดคอขวดยังอยู่ printer เหมือนเดิม ไม่ได้ย้ายไปที่อื่น สังเกตได้จาก avg Resource Wait Time = 298 ซึ่งยังไม่เข้าใกล้ 0 แต่เบาลงกว่าเดิมถึง 82% 
        ```

4. เปรียบเทียบแถว 2 กับแถว 7: เหตุใด workloadและชุดค่าที่ใช้รันเหมือนเดิม จึงอาจให้เวลาใน logลำดับเหตุการณ์และค่าที่วัดต่างกันเล็กน้อย?
    - ANS:
        ```text
            กลไก: OS Thread Scheduling ไม่ deterministic Java thread ถูกจัดคิวและสลับ (context switch) โดย OS scheduler ซึ่งขึ้นอยู่กับปัจจัยที่ควบคุมไม่ได้จากในโค้ด เช่น ภาระงานอื่นบนเครื่องขณะนั้น (background process), จำนวน CPU core ที่ว่างจริง, และ timing ของ scheduler ภายใน OS เอง
            แม้จะรันโปรแกรมเดียวกันด้วย input เดียวกันสองครั้งติดกัน ลำดับที่ OS ปลุก Thread ให้ทำงานต่อหลัง Thread.sleep() ก็ไม่รับประกันว่าจะเป๊ะเวลาเดียวกันทุกครั้ง

            ตัว algorithm/tie-break rule ยังคง deterministic เป๊ะ — ถ้า Job สอง priority เท่ากันมาถึง Ready Queue พร้อมกัน กติกา tie-break (จาก sequence) จะเลือกตัวเดิมเสมอไม่ว่ารันกี่รอบ สิ่งที่ไม่ deterministic คือ "เวลา" ที่แต่ละ event เกิดขึ้น ไม่ใช่ "ผลลัพธ์ของกติกา"
        ```
    - ตารางเปรียบเทียบ
        | |แถว2|แถว 7|ผลต่าง|
        |----|-----|----|----|
        |avg WT|2325.00|2339|14 ms|
        |avg TAT|4958.00|4982|24 ms|
        |Throughput|1.08|1.08|0 ms|
        |avg RW|265.00|264.00|1 ms|

## Poison pill
- หลักการ:
    ```text
        สร้างค่าพิเศษใส่เข้าไปในคิวเดียวกับงานจริง เมื่อ Worker ดึงมาเจอค่านี้ แปลว่า "ไม่มีงานให้ทำอีกแล้ว หยุดได้"
    ```
- code
    ```java
        Job job = readyQueue.take();
        if (job == Job.POISON_PILL) {
            readyQueue.close();  // ส่งต่อให้ Worker ตัวถัดไป (relay)
            break;
        }
    ```

- ข้อดี:
    ```text
        ไม่ต้องมี mechanism แยกต่างหาก ใช้ queue เดิมที่มีอยู่แล้ว, Worker หยุดตัวเองตามธรรมชาติเมื่อคิวว่างพอดี
    ```
- ข้อเสีย: 
    ```text
        ต้องออกแบบ comparator ให้ pill อยู่ท้ายคิวเสมอ ต้องคิดเรื่อง "relay" ให้ทุก Worker ได้รับสัญญาณ ใส่ pill กลับเข้าคิวทุกครั้งที่เจอ
    ```

## CountDownLatch
- หลักการ:
    ```text
        สร้าง latch ด้วยตัวเลขนับถอยหลังโดย N = จำนวน Job ทั้งหมด ทุกครั้งที่ Job เสร็จให้เรียก countDown() ฝั่ง Main เรียก latch.await() ซึ่งจะ block อยู่จนกว่าตัวนับจะถึง 0
    ```
- code
    ```java
        CountDownLatch latch = new CountDownLatch(jobs.size());
        // ส่ง latch เข้า Worker ผ่าน constructor

        // ใน Worker.processJob() ท้ายสุด
        statistics.recordCompletion(job);
        logger.jobCompleted(job);
        latch.countDown(); 

        latch.await();   // block จนกว่า Job ครบทุกตัว countDown หมด
    ```

- ปัญหาที่ตามมา:
    ```text
        latch.await() รู้แค่ว่า "งานเสร็จครบหมดแล้ว" แต่ไม่ได้บอก Worker ให้หยุดทำงาน Worker ก็ยังต้องมีกลไกแยกต่างหากเพื่อรู้ว่า "ควรเลิกรอ take() แล้ว

        วิธีนี้ ต้องผสมกับอย่างอื่น เช่น flag volatile boolean shutdown หรือใช้ readyQueue.poll(timeout) แทน take() แบบ block ตลอดไป
    ```
- codeผสมกับอย่างอื่น:
    ```java
        // Worker ต้องเปลี่ยนจาก take() เป็น poll แบบมี timeout เพื่อเช็ค flag เป็นระยะ
        while (!shutdownRequested) {
            Job job = readyQueue.poll(200, TimeUnit.MILLISECONDS);
            if (job == null) continue;  // timeout ไป เช็ค flag ใหม่
            processJob(job);
        }
    ```
- ข้อเสีย: 
    ```text
        poll(timeout) แบบนี้เป็น polling แบบมี delay ไม่ใช่ pure blocking แบบ take()
    ```

## lock
- หลักการ: 
    ```text
        ใช้ AtomicInteger (หรือ synchronized counter) นับ "จำนวนงานที่ยังไม่เสร็จ" increment ตอน JobGenerator ปล่อยงานเข้าระบบ, decrement ตอน Worker ทำ Job เสร็จ เมื่อค่าถึง 0 แปลว่างานหมดจริงๆ
    ```
- code:
    ```java
    AtomicInteger pending = new AtomicInteger(0);

    // JobGenerator: ทุกครั้งที่ปล่อยงาน
    pending.incrementAndGet();
    arrivalQueue.put(job);

    // Worker: ทุกครั้งที่ processJob เสร็จ
    statistics.recordCompletion(job);
    if (pending.decrementAndGet() == 0) {
        // เป็นคนสุดท้ายที่ทำให้ตัวนับถึง 0 → signal ให้ทุกอย่างหยุด
        for (int i = 0; i < numWorkers; i++) {
            readyQueue.close();  // ยังต้องพึ่ง poison pill relay อยู่ดี เพื่อปลุก Worker ที่ block อยู่ใน take()
        }
    }
    ```

- จุดที่ควรรู้:
    ```text
        วิธีนี้ก็ ยังต้องพึ่งกลไกปลุก Worker ที่ block อยู่ใน take() อยู่ดี เพราะ Worker ตัวสุดท้ายที่กำลัง take() ค้างอยู่เฉยๆ ไม่มีทางรู้ตัวว่าตัวนับถึง 0 แล้ว ถ้าไม่มีใครไป "ปลุก"
    ```
