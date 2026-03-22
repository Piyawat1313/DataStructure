public class Quese {
    private int maxQueseSize = 100;
    private int queseFront = 0;
    private int queseRear = maxQueseSize - 1;
    private int count = 0;
    private Object[]list;
    public Quese(){
        list = new Object[maxQueseSize];
    }
    public Quese(int size){
        list = new Object[size];
        maxQueseSize = size;
        queseRear = maxQueseSize - 1;
    }
    public void innitializeQuese(){
        for (int i = queseFront; i < queseRear; i = (i + 1) % maxQueseSize) {
            list[i] = null;
            queseFront = 0;
            queseRear = maxQueseSize - 1;
            count = 0;
        }
    }
    public boolean isEmptyQuese(){
        return count == 0;
    }
    public boolean isFullQuese(){
        return count == maxQueseSize;
    }
    public void enQuese(Object newItem)throws Exception{
        if(isFullQuese()){
            throw new Exception("QueseUnderFlow");
        }
        queseRear = (queseRear + 1) % maxQueseSize;
        count++;
        list[queseRear] = newItem;
    }
    public void deQuese()throws Exception{
        if(isFullQuese()){
            throw new Exception("QueseUnderFlow");
        }
        count--;
        list[queseFront] = null;
        queseFront = (queseFront + 1) % maxQueseSize;
    }
    public Object front()throws Exception{
        if(isEmptyQuese()){
            throw new Exception("QueseUnderFlow");
        }
        return list[queseFront];
    }
    public Object back()throws Exception{
        if(isEmptyQuese()){
            throw new Exception("QueseUnderFlow");
        }
        return list[queseRear];
    }
    public int getSize(){
        return count;
    }
    public int getMaxSize(){
        return maxQueseSize;
    }
}

