## วิธี Compile / Run
```bash
cd src
javac *.java
java Main <workload.csv> <fcfs|priority> <workers> <printerPermits> <databasePermits>
```

## ตัวอย่างการรัน
java Main workloads/jobs_standard.csv priority 3 1 2

## Argument ที่ใช้
- workload.csv: ไฟล์ชุดงาน
- policy: fcfs | priority
- workers: จำนวน Worker Thread
- printerPermits / databasePermits: จำนวนสิทธิ์ resource

## ข้อจำกัดที่ควรรู้
- Monitor อ่าน ready/running/completed แยกกัน 3 จุด เป็น thread-safe แต่ละตัว ไม่ใช่ atomic snapshot ทั้งชุดพร้อมกัน 100% คลาดเคลื่อนได้เล็กน้อยระดับ ms
