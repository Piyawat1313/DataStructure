public class Quese {
    private int maxQueseSize = 100;
    private int quesefront = 0;
    private int queseRear = maxQueseSize - 1;
    private int count = 0;
    private Object[]list;
    public Quese(){
        list = new Object[maxQueseSize];
    }
    public Quese(int size){
        list = new Object[size];
        maxQueseSize = size;
    }
    public void initializeStack(){
        for (int i = quesefront; i < queseRear; i = (i + 1) % maxQueseSize) {
            quesefront = 0;
            queseRear = 0;
            count = 0;
        }
    }
    public boolean isEmptyQuese(){
        return count == 0;
    }
    public boolean isFullQuese(){
        return count == maxQueseSize;
    }
    public Object front()throws Exception{
        if(isEmptyQuese()){
            throw new Exception("555++");
        }
        return list[quesefront - 1];
    }
    public Object back()throws Exception{
        if(isEmptyQuese()){
            throw new Exception("555+++");
        }
        return list[queseRear - 1];
    }
    public void enQuese(Object item)throws Exception{
        if(isFullQuese()){
            throw new Exception("555++");
        }
        queseRear = (queseRear + 1) % maxQueseSize;
        count++;
        list[queseRear] = item;
    }
    public void deQuese()throws Exception{
        if(isFullQuese()){
            throw new Exception("555++");
        }
        count--;
        list[quesefront] = null;
        quesefront = (quesefront + 1) % maxQueseSize;
    }
    public int getSize(){
        return count;
    }
    public int maxQueseSize(){
        return maxQueseSize;
    }
}
