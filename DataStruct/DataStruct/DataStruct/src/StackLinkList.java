class Stack {
    private int stackSize = 0;
    private Node stackTop = null;
    public Stack(){
        stackTop = null;
    }
    public void initializeStack(){  //กำหนดตัวชี้เป็น null
        stackTop = null;
    }
    public boolean isEmptyStack(){  //ใน stack ว่างมั้ย
        return stackTop == null;
    }
    public boolean isFullStack(){   //เต็มหรือยัง
        return false;   //ที่เป็นเพราะว่า LinkList มันจะใส่จนกว่า memory จะเต็ม
    }
    public void push(Object newItem){   //เพิ่ม Node ลง Stack
        Node newNode = new Node(newItem);
        newNode.setLink(stackTop);  //set link ให้ตัวชี้ชี้ไปที่ข้อมูลที่เพิ่มเข้ามา
        stackTop = newNode;
        stackSize++;    //เพิ่มขนาด
    }
    public Object peek()throws Exception{   //ดูข้อมูลที่อยู่บนสุดของ linklist
        if(isEmptyStack()){
            throw new Exception("Stack Error");
        }
        return stackTop.getInfo();
    }
    public Object pop()throws Exception{    //ลบข้อมูลออกจาก StackLinklist
        Object itemInfo;
        if(isEmptyStack()){
            throw new Exception("Some thing wrong");
        }
        itemInfo = stackTop.getInfo();  //เก็บข้อมูล Object เข้า object ใหม่
        stackTop = stackTop.getLink();  //อัปเดตตัวชี้ให้ไปตัวถัดไป
        stackSize--;    //ลดขนาดลง
        //ส่วนนี้มันจะทำการดึงข้อมูลมาแสดงผลและลบข้อมูลออกจาก StackLink
        return itemInfo;
    }
    public int getSize(){
        return stackSize;
    }

}
