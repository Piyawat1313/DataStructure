#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>

int main(void){
    int pipefd[2];
    pid_t pid;
    char message[] = "Hello from parent";
    char buffer[100];

    //สร้าง pipe
    if(pipe(pipefd) == -1){
        perror("pipe");
        return 1;
    }

    pid = fork();   //สร้าง Process ลูก

    if(pid == -1){
        perror("fork");
        return 1;
    }

    //Child --> reader
    if(pid == 0){
        close(pipefd[1]);
        ssize_t n = read(pipefd[0], buffer, sizeof(buffer) - 1);
        if(n > 0){
            buffer[n] = '\0';
            printf("Child received: %s\n", buffer);
        }
        close(pipefd[0]);
    }

    //parent --> writer
    else{
        close(pipefd[0]);
        write(pipefd[1], message, strlen(message));
        close(pipefd[1]);
        wait(NULL);
    }

    return 0;
}

