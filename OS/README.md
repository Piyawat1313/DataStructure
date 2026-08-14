# Run Pipe
1. เข้าไปที่ folder ที่เราได้บันทึกไฟล์ pipe.c ไว้ โดยใช้ Ubuntu ในการเข้าถึงและรันโปรแกรม
```bash
cd /mnt/c/piyaw/Downloads/การเรียนมหาลัย/operating Systems/lab_OS
```
2. ใช้คำสั่งนี้ในการ Run file ภาษา C
```bash
gcc ตามด้วยชื่อไฟล์.c -o name_file
./nameFile
```
- -o --> มันย่อมาจาก Output เป็นการตั้งชื่อ output ของ file ที่เราสร้างขึ้นมา

3. กรณีที่เราไม่ได้ลง gcc ใน Ubuntu สามารถทำได้ดังนี้
```
sudo apt update
sudo apt install build-essential
```
- ถ้าเกิดมันขึ้นแบบนี้ [sudo] password for biw: --> ให้ใส่ password ที่เราตั้งขึ้นมาตอนเราโหลด Ubuntu มาใหม่ๆ
- sudo --> ย่อมาจาก Superuser DO ทำงานด้วยสิทธิ์ผู้ดูและระบบสูงสุด(Admintator)


# Run POSIX
1. เข้าไปที่ folder ที่เราได้บันทึกไฟล์ pipe.c ไว้ โดยใช้ Ubuntu ในการเข้าถึงและรันโปรแกรม
```bash
cd /mnt/c/piyaw/Downloads/การเรียนมหาลัย/operating Systems/lab_OS
```
2. ใช้คำสั่งนี้ในการ Run file ภาษา C
```bash
gcc POSIX.c -o POSIX -lrt
./POSIX
```
- -lrt --> ย่อมาจาก Link Real-Time library เป็นการบอกให้ gcc เชื่อมกับ library ตัวนี้

3. กรณีที่เราอยากเช็คว่าโปรแกรมของเรามีการสร้างอ็อบเจคจริงๆมั้ย
```bash
ls -l /dev/shm
```
4. เช็คข้อความข้างในไฟล์
```bash
cat /dev/shm/OS 
```


# คำสั่ง Ununtu command line
1. ตรวจสอบ shell ด้วย ps
```bash
ps -p $$ -o pid,ppid,stat,cmd
```
- PID --> process ID
- PPID --> parent process ID
- STAT --> process state
- CMD --> command
- -o --> กำหนด column ที่ต้องการแสดง

2. สร้าง  background process
```bash
sleep 300 &
```
- สร้าง process ที่รอ 300 วินาที

3. การจำ PID ของ Process ที่พึ่งสร้างไว้
```bash
pid=$!  --> เก็บค่าไว้ในตัวแปร pid

echo "sleep PID = $pid"  --> ตรวจสอบค่าที่เก็บไว้
```
- $! --> pid ของ background process ล่าสุด

4. ดู process ที่สร้างขึ้น
```bash
ps - p "$pid" -o pid,ppid,stat,cmd  --> เลือก process ที่พึ่งสร้าง

jobs -l --> แสดงการทำงานเบื้องหลังของ shell พร้อมกับ PID
```

5. ค้นหา process
```bash
pgrep -a sleep --> ค้นหา process และแสดงผลใน command line

ps aux | grep "[s]leep"
```
- ps aux --> แสดง process เป็นจำนวนมาก
- grep --> กรองบรรทัด 
- [s]leep --> ป้องกันไม่ให้ grep แสดงบรรทัดของตัวเอง

6. ดูความสัมพันธ์ของ Parent-Child
```bash
pstree -p $$ --> แสดงความสัมพันธ์แบบต้นไม้
```
7. signal ข้อความควบคุม Process
```bash
kill -STOP "$pid" --> ส่ง STOP  

ps -p "$pid" -o pid,stat,cmd --> ตรวจดู stat

kill -CONT "$pid" --> ส่ง CONT

ps -p "$pid" -o pid,stat,cmd --> ตรวจดู stat

kill -TERM "$pid" --> ส่ง TERM เพื่อยุติ

ps -p "$pid" --> ps ไม่พบ PID หลังยุติ
```
- kill --> ให้ส่งสัญญาณไปยัง PID
- STOP --> หยุด process ชั่วคราว
- CONT --> ให้ process ทำงานต่อ
- TERM --> ให้ process ยุติอย่างเป็นระเบียบ
- !!!!!!! Signal ไม่สามารถใช้ส่งข้อมูลขนาดใหญ่ได้

8. Anonymous Pipe ต่อ Output ไปยัง Input
```bash
printf "Hello IPC\n" | wc-l
^                       ^
|                       |
product               Consumer
```
- wc -l --> นับจำนวนบรรทัดที่ได้รับ

9. ใช้ Pipe นับจำนวน sleep Process
```bash
sleep 300 &
sleep 300 &
sleep 300 &
pgrep -u "$USER" -x sleep | wc -l
```

10. Named Pipe ที่มีชื่อในไฟล์ System
```bash
pipe=/tmp/oslab_pipe_$USER --> กำหนด Path โดยใช้ USER

rm -f "$pipe" --> ลบ pipe ของเดิมออก

mkfifo "$pipe" --> สร้าง Named Pipe

ls -l "$pipe" --> แสดงชนิดไฟล์ที่ขึ้นต้นด้วย p
```

11. Parent รอ Child process
```bash
sleep 5 & --> สร้าง child process ทำงานเบื้องหลัง

child=$! --> เก็บ PID ด้วย $!

wait "$child" --> รอ PID ทำให้ shell รอ child ที่ระบุ
```

12. ทำความสะอาดหลังจากทดลองเสร็จ
```bash
pkill -u "$USER" -x sleep --> ยุติ sleep ของผู้ใช้ปัจจุบัน

rm -f "/tmp/oslab_pipe_$USER" --> ลบ named pipe ใน /tmp

pgrep -u "$USER" -a sleep --> ตรวจสอบว่ามี sleep หรือ FIFO ค้างหรือไม่
```
