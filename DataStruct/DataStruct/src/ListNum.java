 class Node {
    private int info;
    private Node link;
    public Node(int info,Node link)
   {
       this.info=info;
       this.link=link;
    }
    public Node(int info)
    {
        this(info,null);
    }
    public Node()
    {
        this(0,null);
    }
    public int getInfo() {
    return this.info;
    }
    public Node getLink() {
        return this.link;
    }
    public void setInfo(int info)
    {
    this.info=info;
    }
    public void setLink(Node link) {
        this.link = link;
    }
    public String toString() {
    return ""+this.info;
    }
}
 
public class ListNum {
    private Node head=null;
    public ListNum()
    {
    }
    public void addNode(int newInfo) {
        Node newNode = new Node(newInfo);
        newNode.setLink(head);  
        head = newNode;
    }
    public String toString() {
        Node trav=head;
    String str="";
    while (trav!=null) {            
        str=str.concat(trav.toString());
        //str=str.concat(""+trav.getInfo());
                trav=trav.getLink();
        if(trav!=null)
                    str=str.concat(" | ");      
    }
    return str;
    }
    public int getlength(){     // หาขนาดของ LinkList
        Node trav1;
        trav1 = head;   // ตัวชี้ชี้ไปที่หัว
        int length = 0;     
        while (trav1 != null) {     //ตัวชี้ไม่เป็น null
                trav1 = trav1.getLink();       // อัปเดตตัวชี้ให้เดินหน้า
                length++;
        }
        return length;
    }
    public void addLast(int newInfo){       //เพิ่มข้อมูลลงLinkList
        Node newNode = new Node(newInfo);   //ส่งข้อมูลไปหา constuctor
        Node trav1, trav2;
        trav1 = trav2 = head;       //ตัวชี้ทั้งสองชี้ไปที่ส่วนหัว
        while (trav1 != null) {     //กำหหนดให้ตัวชี้ตัวแรกไม่เท่ากับ null
            trav2 = trav1;      //อัปเดตตัวชี้ที่2 ให้เท่ากับตัวชี้ตัวแรก
            trav1 = trav1.getLink();    //อัปเดตตัวชี้ตัวแรก
        }
        if(trav1 != trav2){     //ตัวชี้ทั้งสองไม่าเท่ากัน
            trav2.setLink(newNode);     //setข้อมูลลงตัวชี้ตัวที่ 2 
        }
        else{
            head = newNode;     //ส่วนหัวเก็บไปที่ object of Node
        }
    }
 
    public void Sort(int newInfo){      //เรียงข้อมูล
        Node newNode = new Node(newInfo);   //ส่งข้อมูลไปที่ constuctor
        Node trav1, trav2;
        trav1 = trav2 = head;  //ตัวชี้ทั้งสองชี้ไปที่ส่วนหัว
        while (trav1 != null && trav1.getInfo() < newNode.getInfo()) {      //ตัวชี้ต้องไม่เท่ากับ null และ ข้อมูลของตัวชี้ตัวแรก น้อยกว่า ข้อมูลใหม่
            trav2 = trav1;      //อัปเดตตัวชี้ 2    
            //ตัวชี้ตัวที่ 2 จะชี้ตัวที่1ได้ ก็ต่อเมื่อ ตัวที่ 1 ถูกการอัปเดตไปแล้ว
            trav1 = trav1.getLink();   //อัปเดตตัวชี้ที่ 1 
        }
        newNode.setLink(trav1);     //set data ลงใน newNode
        if(trav1 != trav2){     //ตัวชี้ทั้งสองไม่เท่ากัน
            trav2.setLink(newNode);     //ตัวชี้ตัวที่ 2 จะเซ็ทค่าลงไป
        }
        else{
            head = newNode;     //ส่วนหัวจะเก็บค่าของข้อมูลใหม่ไว้
        }
    }
    public void removeHead(){       //ลบส่วนหัว
        if(head != null){       //ถ้าหัวไม่เป็น Null
            head = head.getLink();  //อัปเดตหัวต่อไป
        }
 
    }
    public void removeAfter(){      //ลบส่วนหลัง
        Node delNode = new Node();
        Node trav1, trav2;
        trav1 = trav2 = head;       //กำหนดให้ตัวชี้ทั้งสองชี้ไปที่หัว
        while (trav1 != null && trav1.getLink() != null) {  //ตัวชี้ตัวแรกต้องไม่เป็น null และ ลิ้งของตัวชี้ตัวที่1 จะไม่เป็น null ด้วย
            trav2 = trav1;  //อัปเดตตัวชี้ที่ 2 
            trav1 = trav1.getLink();    //อัปเดตตัวชี้ตัวที่ 1 
            delNode = trav1;    //เก็บข้อมูลของตัวชี้ 1 ลงใน object
        }
        if (trav2 != null) {       //ตัวชี้ตัวที่ 2 ไม่เป็น null
            trav2.setLink(null);    //ตัวชี้ที่ 2 set ค่าให้เป็น null
            if(delNode == head){    //object มีค่าเท่ากับ head
                head = null;    //เปลี่ยน head เป็น null
            }
        }
    }
    public void searching(int newInfo){     //ค้นหาข้อมูลใน LinkList
        Node trav1 = head;      //กำหนดให้ตัวชี้ตัวแรกชี้ไปที่หัว
        while (trav1 != null && trav1.getInfo() != newInfo) {   //ต้องไม่ว่างเปล่าและข้อมูลต้องไม่ตรงกัน
            trav1 = trav1.getLink();    //อัปเดตตัวชี้ตัวแรก
        }
        if(trav1 != null && trav1.getInfo() == newInfo){    //ถ้าตัวชี้ไม่เป็นค่าว่างและข้อมูลของตัวชี้เท่ากับข้อมูลที่ให้มา
            System.out.println(trav1.getInfo());    
        }
    }
    public Node wantRemove(int newInfo){       //ต้องการลบข้อมูลที่ต้องการ
        Node trav1, trav2;
        trav1 = trav2 = head;     //ตัวชี้สองตัวชี้ไปที่ head
        Node delNode = new Node();
        while (trav1 != null) {     //ตัวชี้ต้องไไม่เป็น null
            if(trav1.getInfo() == newInfo){     //ถ้าข้อมูลตัวชี้ตัวที่ 1 เท่ากับ ข้อมูลที่ให้มา
                delNode = trav1;    //object เก็บตัวชี้ตัวแรกไว้
                if(trav1 != head){   //ถ้าตัวชี้ไม่เท่ากับ หัว
                    trav2 = trav2.getLink();    //อัปเดตตัวชี้ตัวที่ 2 
                    trav1 = trav1.getLink();    //อัปเดตตัวชี้ตัวแรก
                    trav2 = trav1;  //เก็บค่าตัวชี้ตัวแรกลงตัวชี้ตัวที่ 2 
                }
                else{       //ถ้ามันเท่ากัน
                    head = trav1.getLink(); //อัปเดตส่วนหัว
                }
            }
            else{   //ข้อมูลตัวชี้อันแรกไม่เท่ากัน
                trav2 = trav1;  
                trav1 = trav1.getLink();
                //อัปเดตตัวชี้ตัวแรก เก็บค่าตัวชี้ตัวแแรกลงในตัวชี้ตัวที่ 2 
            }
        }
        return delNode;
    }
    public static void main(String[] args) {
        ListNum listNum1=new ListNum();
        listNum1.addNode(10);
        listNum1.addNode(20);
        listNum1.addNode(30); 
        listNum1.addNode(150);
        listNum1.addNode(200);
        System.out.println(listNum1);   
        System.out.println();  
        System.out.println(listNum1.getlength());
        listNum1.removeHead();
        System.out.println(listNum1);

        listNum1.removeAfter();
        System.out.println(listNum1);

        listNum1.searching(150);
        System.out.println();
        listNum1.wantRemove(150);
        System.out.println();
        listNum1.searching(150);

    }    
}
