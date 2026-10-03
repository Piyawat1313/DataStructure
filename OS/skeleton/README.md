## การรันโปรแกรม
1. compiler code
```bash
javac JioChannel.java

```
2. run traditional
```bash
java JioChannel bigfile.bin out-trad.bin traditional
```

3. run zero copy
```bash
java JioChannel bigfile.bin out-zero.bin zerocopy
```

4. สร้างไฟล์ .bin
```bash
fsutil file createnew <ชื่อไฟล์>.bin 524288000  
```

## ตารางบันทึกผล
||Run 1|Run 2|Run 3|
|-------|-----|-----|-----|
|Traditional Copy Time|368.16|338.98|358.36|
|Zero Copy Time|373.56|356.97|365.83|