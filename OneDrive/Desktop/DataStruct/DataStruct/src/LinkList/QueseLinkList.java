package LinkList;
public class QueseLinkList {
    private int maxQueseSize = 50;
    private Node quesefront = null;
    private Node queseRear = null;
    private int count;
    public void initializeQuese(){
        quesefront = null;
        queseRear = null;
        count = 0;
    }
    public boolean isEmptyQuese(){
        return quesefront == null;
    }
    public boolean isFullQuese(){
        return false;
    }
    public Object front()throws Exception{
        if(isEmptyQuese()){
            throw new Exception("55555");
        }
        return quesefront.getInfo();
    }
    public Object back()throws Exception{
        if(isEmptyQuese()){
            throw new Exception("5555");
        }
        return queseRear.getInfo();
    }
    public void enQuese(int newItem){
        Node newNode = new Node(newItem);
        if(quesefront == null){
            quesefront = newNode;
            queseRear = newNode;
        }
        else{
            queseRear.setLink(newNode);
            queseRear = queseRear.getLink();
        }
        count++;
    }
    public Object deQuese()throws Exception{
        Object Iteminfo;
        if(isEmptyQuese()){
            throw new Exception("5555");
        }
        Iteminfo = quesefront.getInfo();
        quesefront = quesefront.getLink();
        if(quesefront == null){
            queseRear = null;
        }
        count--;
        return Iteminfo;
    }
    public int getSize(){
        return count;
    }
    public int maxQueseSize(){
        return maxQueseSize;
    }
}
