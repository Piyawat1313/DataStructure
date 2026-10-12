# ขั้นตอนการรันโปรแกรม
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

4. สร้างไฟล์ขนาด 1 GB ใน Terminal
    ```bash
        fsutil file createnew 1g.test 1073741824
    ``` 

### Client
1. run Client
    ```bash
        java Client localhost <port> <ชื่อไฟล์> <worker> <trad/nio> <folder>
    ```