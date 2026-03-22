public class Quese {
    private int maxQueseSize = 100;
    private Node queseFront = null;
    private Node queseRear = null;
    private int count = 0;
    public void initializeQuese(){
        queseFront = null;
        queseRear = null;
        count = 0;
    }
    public boolean isEmptyQuese(){
        return queseFront == null;
    }
    public boolean isFullQuese(){
        return false;
    }
    public Object front()throws Exception{
        if(isEmptyQuese()){
            throw new Exception("QueseStackUnderflow");
        }
        return queseFront.getInfo();
    }
    public Object back() throws Exception{
        if(isEmptyQuese()){
            throw new Exception("QueseUnderFlow");
        }
        return queseRear.getInfo();
    }
    public void enQuese(Object newItem){
        Node newNode = new Node(newItem);
        if(queseFront == null){
            queseFront = newNode;
            queseRear = newNode;
        }
        else{
            queseRear.setLink(newNode);
            queseRear = queseRear.getLink();
        }
        count++;
    }
    public Object deQuese()throws Exception{
        Object itemInfo;
        if(isEmptyQuese()){
            throw new Exception("QueseUnderFlow");
        }
        itemInfo = queseFront.getInfo();
        queseFront = queseFront.getLink();
        if(queseFront == null){
            queseRear = null;
        }
        count--;
        return itemInfo;
    }
    public int getSize(){
        return count;
    }
    public int getMaxSize(){
        return maxQueseSize;
    }
    // public String toString(){
    //     String result = "";
    // }
}
