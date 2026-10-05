## ขั้นตอนการ Run บนเครื่องตัวเอง
### Server
1. compile java
    ```bash
        javac *.java
    ```
2. run Server.java
    ```bash
        java Server port <trad/nio> <folder/file>
    ```
3. port --> 3000

### Client
1. run Client
    ```bash
        java Client localhost <port> <ชื่อไฟล์> <worker> <trad/nio> <folder>
    ```

## ขั้นตอนการ Run คนละเครื่อง
1. ต้องเชื่อม internet หรือ hotspot ให้ server/client อยู่ในวงเน็ตเดียวกัน
2. เครื่อง server ต้องเพิ่มกฎ firewall บน powershell Admin โดยใช้คำสั่ง
    ```bash
        netsh advfirewall firewall add rule name="FileServer3000" dir=in action=allow protocol=TCP localport=3000 profile=any
    ```
    - ปิด firewall ชั่วคราว
    ```bash
        netsh advfirewall set allprofiles state off
    ```
    - ทดสอบเสร็จให้ทำการเปิด firewall 
    ```bash
        netsh advfirewall set allprofiles state on
    ```
3. run Client
    ```bash
        java Client <ip> <port> <fileName> <worker> <trad/nio> <folder>
    ```
## ตารางบันทึกผลโดยรวม
|mode|worker|time|Throughput|size_ok|sha256|
|-----|-----|----|----------|-------|---|
|nio|1|3.859|265.33|true|49bc20df15e412a64472421e13fe86ff1c5165e18b2afccf160d4dc19fe68a14|
|nio|10|2.417|423.72|true|49bc20df15e412a64472421e13fe86ff1c5165e18b2afccf160d4dc19fe68a14|
|trad|1|1.678|610.07|true|49bc20df15e412a64472421e13fe86ff1c5165e18b2afccf160d4dc19fe68a14|
|trad|10|1.325|772.96|true|49bc20df15e412a64472421e13fe86ff1c5165e18b2afccf160d4dc19fe68a14|


## ตารางบันทึกผล Worker 10 ตัวในโหมด Traditional
|workers|offset|
|-------|------|
|0|0|
|1|107374182|
|2|214748364|
|3|322122546|
|4|429496728|
|5|536870910|
|6|644245092|
|7|751619274|
|8|858993456|
|9|966367638|


## ตารางบันทึกผล Worker 10 ตัวในโหมด NIO
|workers|offset|
|-------|------|
|0|0|
|1|107374182|
|2|214748364|
|3|322122546|
|4|429496728|
|5|536870910|
|6|644245092|
|7|751619274|
|8|858993456|
|9|966367638|

## ตารางผลรวมทั้ง 3 รอบ
|mode|workers|รอบที่ 1|รอบที่ 2|รอบที่ 3|เวลาเฉลี่ย|Throughput เฉลี่ย|
|----|-------|----|------|----|------|-----|
|Traditional|1|1.726|1.739|1.678|1.714|597.49|
|Traditional|10|1.498|1.472|1.325|1.432|717.48|
|NIO|1|5.160|3.557|3.859|4.192|250.55|
|NIO|10|2.095|2.038|2.417|1.183|471.59|

- Speedup ของ 10 worker เทียบ 1 worker
    - Traditional: 1.714/1.432 = 1.20
    - NIO: 4.192/2.183 = 1.92

## คำถาม Demo
1. อธิบายเหตุผลที่ 10 workers อาจไม่เร็วขึ้น 10 เท่า
- ANS:
    ```text
        จากการรันทั้ง 3 รอบ Traditional เร็วขึ้น 1.20 เท่า และ NIO เร็วขึ้น 1.92 เท่า ซึ่งน้อยกว่า 10 เท่ามากๆ เพราะ
        ทุก worker ใช้ทรัพยากรเดียวกันเช่น loopback disk pagecash CPU memory bandwidth พอทำแค่ 1 worker ก็ใช้ได้เกือบเต็มแล้ว

        เวลารวมเท่ากับ worker ที่ช้าทีี่สุด และมีงานที่ขนานกันไม่ได้

        การสร้าง 10 connection/thread การสลับ Thread มีต้นทุนของมันอยู่

        ไฟล์แค่ 1 GB ใช้เวลาแค่ 1-2 วินาที ต้นทุนคงที่จึงมีสัดส่วนสูง
    ```

2.  NIO อาจไม่ชนะทุกครั้ง
- ANS:
    ```text
        ในการทดลองนี้ NIO ช้ากว่า Traditional ทุกกรณี (1 worker: 4.192s vs 1.714s,
        10 workers: 2.183s vs 1.432s) เพราะ


        - NIO ให้ประโยชน์เมื่อการคัดลอกข้อมูลระหว่าง kernel กับ user space เป็นคอขวด
        ซึ่ง zero-copy (transferTo) อยู่ฝั่ง Server แต่ฝั่ง Client ที่ใช้ transferFrom
        จาก socket ไม่ได้เป็น zero-copy จริง และ JDK อ่านเป็นก้อนเล็ก
        ขณะที่ Traditional ใช้ buffer 64 KB จึงเรียก system call น้อยกว่า
        
        
        - บน localhost คอขวดไม่ใช่การคัดลอกข้อมูลเครือข่าย ข้อได้เปรียบของ NIO จึงไม่เด่น
        
        
        - ผลแกว่งระหว่างรอบ เช่น NIO 1 worker: 5.160, 3.557, 3.859 วินาที
        จึงต้องรันหลายรอบและเฉลี่ย
        
        ดังนั้น NIO ไม่ได้ดีกว่าเสมอ ขึ้นกับว่าคอขวดอยู่ที่ไหน ขนาด buffer และ OS/JVM
    ```
3. ข้อจำกัดของการทดลองผ่าน localhost
- ANS: 
       - Page Cache: ไฟล์ที่อ่านซ้ำจะถูกเก็บใน RAM ทำให้เร็วกว่าการอ่านดิสก์จริง

       - Loopback: ถ้า Server กับ Client อยู่เครื่องเดียวกัน ข้อมูลไม่ผ่านเครือข่ายจริง ตัวเลขเลยสูงกว่าปกติมาก

       - Storage cache: ดิสก์และ OS มี cache ช่วยอยู่ด้วย

       - Throughput คำนวณจากเวลาดาวน์โหลดเท่านั้น (ไม่รวมเวลาคำนวณ SHA-256)

## คำถาม Protocol และการออกแบบ
1. ทำไมต้องมี length ใน OK <"length"> ก่อนส่ง payload?
    - ANS: 
        ```text
            TCP เป็น byte stream ไม่มีขอบเขตของ message client จึงต้องรู้ว่าต้องอ่านกี่ byte ถึงจะครบ และจะรู้ว่าถูกตัดกลางทางหรือไม่
        ```
2. ทำไม readLine ฝั่ง client ต้องอ่านทีละ byte และห้ามใช้ BufferedReader?
    - ANS: 
        ```text
            BufferedReader อ่านล่วงหน้าเต็ม buffer จะกลืน byte ของ payload ที่ตามหลัง header ไปด้วย ข้อมูลที่ได้จะขาดหาย
        ```
3. ทำไมใช้ ISO_8859_1
    - ANS:
        ```text
            1 byte = 1 char พอดี ไม่เกิดการแปลงที่ทำให้ byte เพี้ยน ข้อเสียคือชื่อไฟล์ภาษาไทยหรือ UTF-8 จะใช้ไม่ได้ ถ้าจะรองรับต้องใช้ UTF-8 ใน header
        ```
4. ชื่อไฟล์มีเว้นวรรคจะเกิดอะไร?
    - ANS: 
        ```text
            split("\\s+") จะแตก token ผิดและ p.length != 4 จะ error วิธีแก้คือ encode ชื่อไฟล์หรือใช้ length-prefix
        ```
5. error code 400/404/416 ใช้ตอนไหน?
    - ANS:
        ```text
            400 คือ command หรือ argument ผิด
            404 คือไม่พบไฟล์
            416 คือ range ไม่ถูกต้อง
        ```
6. ทำไมเช็ก offset > size - length แทน offset + length > size?
    - ANS:
        ```text
            กัน long overflow ถ้า offset กับ length ใหญ่มากแล้วบวกกันจะล้นเป็นค่าลบ ทำให้ผ่านเช็กทั้งที่ผิด
        ```
7. resolve() กัน path traversal ยังไง?
    - ANS:
        ```text
            ปฏิเสธชื่อที่มี /, \, .. แล้ว normalize() และเช็ก startsWith(dir) ซ้ำอีกชั้น 
        ```
8. ถ้า client ส่ง GET ในบรรทัดเดียวกันหลายคำสั่ง (pipelining)จะเป็นยังไง?
    - ANS:
        ```text
            Server วนอ่านทีละบรรทัดได้ แต่ client ของเราส่ง 1 request ต่อ 1 connection
        ```

## คำถามเกี่ยวกับ Concurrency
1. Virtual thread ต่างจาก platform thread ยังไง ทำไมเลือกใช้?
    - ANS:
        ```text
            เบากว่ามาก สร้างได้เป็นหมื่นตัว เหมาะกับงาน blocking I/O แบบ 1 connection ต่อ 1 thread
        ```
2. Virtual thread มีข้อจำกัดอะไร (pinning)?
    - ANS:
        ```text
            File I/O แบบ blocking อย่าง RandomAccessFile อาจ pin carrier thread ได้ แต่ในงานนี้จำนวน connection น้อยจึงไม่เป็นปัญหา
        ```
3. หลาย worker เขียนไฟล์เดียวกันพร้อมกัน ปลอดภัยไหม?
    - ANS:
        ```text
            ปลอดภัย เพราะแต่ละ worker เปิด FileChannel ของตัวเอง และใช้ positional write fc.write(bb, pos) ซึ่งไม่ขยับ position ร่วมกัน ช่วง byte ก็ไม่ซ้อนกัน
        ```
4. ทำไมต้อง raf.setLength(size) ก่อน?
    - ANS:
        ```text
            จองขนาดไฟล์เต็มไว้ก่อน เพื่อให้ worker เขียนที่ offset ใดก็ได้โดยไม่ต้องรอ worker ก่อนหน้า
        ```
5. ถ้า worker ตัวใดตัวหนึ่ง fail จะเกิดอะไร?
    - ANS:
        ```text
            f.get() จะโยน ExecutionException และ pool ไม่ถูก shutdown() ไม่อยู่ใน finally โปรแกรมอาจค้าง
            
            แก้ด้วย try/finally หรือ retry เฉพาะ chunk ที่พัง
        ```

## คำถามเกี่ยวกับ Traditional VS NIO
1. Zero-copy คืออะไร transferTo ทำงานยังไง?
    - ANS:
        ```text
            Traditional: disk → page cache → user buffer → socket buffer → NIC

            copy ผ่าน CPU 2 ครั้ง และ context switch หลายรอบ

            transferTo ใช้ sendfile() ให้ kernel ส่งจาก page cache ไป socket ตรง ๆ ไม่ผ่าน user space
        ```
2. ฝั่ง client transferFrom เป็น zero-copy จริงไหม?
    - ANS:
        ```text
            ไม่แน่ใจเสมอไป ใน JDK ส่วนใหญ่ การ transferFrom จาก SocketChannel เข้า FileChannel ไม่ได้ใช้ syscall zero-copy แบบเดียวกับ sendfile แต่อ่านผ่าน buffer ชั่วคราวภายใน ดังนั้นประโยชน์หลักของ NIO ในงานนี้อยู่ที่ฝั่ง server
        ```
3. ทำไม transferTo/transferFrom ต้องใส่ loop?
    - ANS:
        ```txt
            ส่งหรือรับได้น้อยกว่าที่ขอได้ Linux จำกัดต่อครั้งประมาณ 2GB และ socket buffer อาจเต็ม ต้องวนจน remaining == 0
        ```
4. ทำไม NIO mode ฝั่ง server ต้องใช้ SocketChannel แทน ServerSocket?
    - ANS:
        ```text
            transferTo ต้องการ WritableByteChannel ปลายทาง จึงจะ optimize เป็น zero-copy ได้
        ```
5. ทำไม flush header ก่อนส่ง payload?
    - ANS:
        ```text
            Header เขียนผ่าน OutputStream (มี buffer) ส่วน payload ใน NIO ไปผ่าน channel ตรง ถ้าไม่ flush ก่อน header อาจไปถึง client หลัง payload ทำให้ protocol พัง
        ```