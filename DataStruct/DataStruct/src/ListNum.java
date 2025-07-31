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
    public int getlength(){
        Node trav1;
        trav1 = head;
        int length = 0;
        while (trav1 != null) {
                trav1 = trav1.getLink();
                length++;
        }
        return length;
    }
    public void addLast(int newInfo){
        Node newNode = new Node(newInfo);
        Node trav1, trav2;
        trav1 = trav2 = head;
        while (trav1 != null) {
            trav2 = trav1;
            trav1 = trav1.getLink();
        }
        if(trav1 != trav2){
            trav2.setLink(newNode);
        }
        else{
            head = newNode;
        }
    }
 
    public void Sort(int newInfo){
        Node newNode = new Node(newInfo);
        Node trav1, trav2;
        trav1 = trav2 = head;
        while (trav1 != null && trav1.getInfo() < newNode.getInfo()) {
            trav2 = trav1;
            trav1 = trav1.getLink();
        }
        newNode.setLink(trav1);
        if(trav1 != trav2){
            trav2.setLink(newNode);
        }
        else{
            head = newNode;
        }
    }
    public void removeHead(){
        if(head != null){
            head = head.getLink();
        }
 
    }
    public void removeAfter(){
        Node delNode = new Node();
        Node trav1, trav2;
        trav1 = trav2 = head;
        while (trav1 != null && trav1.getLink() != null) { 
            trav2 = trav1;
            trav1 = trav1.getLink();
            delNode = trav1;
        }
        if (trav2 != null) {
            trav2.setLink(null);
            if(delNode == head){
                head = null;
            }
        }
    }
    public void searching(int newInfo){
        Node trav1 = head;
        while (trav1 != null && trav1.getInfo() != newInfo) {
            trav1 = trav1.getLink();
        }
        if(trav1 != null && trav1.getInfo() == newInfo){
            System.out.println(trav1.getInfo());
        }
    }
    public Node wantRemove(int newInfo){
        Node trav1, trav2;
        trav1 = trav2 = head;
        Node delNode = new Node();
        while (trav1 != null) {
            if(trav1.getInfo() == newInfo){
                delNode = trav1;
                if(trav1 != head){
                    trav2 = trav2.getLink();
                    trav1 = trav1.getLink();
                    trav2 = trav1;
                }
                else{
                    head = trav1.getLink();
                }
            }
            else{
                trav2 = trav1;
                trav1 = trav1.getLink();
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