#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <unistd.h>

int main(void){
    const char *name = "/OS";   //สร้าง Object ชื่อว่า /OS  พร้อมกับสิทธิ์แบบ read/write
    const size_t SIZE = 4096;   //มีขนาดพื้นที่ 4096 bytes
    int shm_fd = shm_open(name, O_CREAT | O_RDWR, 0666);

    if(shm_fd == -1){
        perror("shm_open");
        return 1;
    }

    if(ftruncate(shm_fd, SIZE) == -1){
        perror("ftruncate");
        return 1;
    }

    //=================================
    // mmap เขียนข้อมูล
    //=================================

    //PROT_READ | PROT_WRITE อนุญาตให้อ่านและเขียนใน mapping
    //MAP_SHARED คอยยืนยันว่าการเปลี่ยนแปลงจะมีผลต่อ object ส่วนรวม
    void *ptr = mmap(NULL, SIZE, PROT_READ | PROT_WRITE, MAP_SHARED, shm_fd, 0);

    if(ptr == MAP_FAILED){
        perror("mmap");
        return 1;
    }

    const char *message = "Hello World!";
    snprintf((char *)ptr, SIZE, "%s", message);  //เขียนข้ความลงในพื้นที่ของตัวที่ชี้อยู่

    munmap(ptr, SIZE);
    close(shm_fd);

    return 0;
}
