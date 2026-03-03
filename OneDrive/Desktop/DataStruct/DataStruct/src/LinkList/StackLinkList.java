package LinkList;
class Stack {
    private int maxStackSize = 20;
    private Node stackTop;
    public Stack(){
        stackTop = null;
    }
    public void initializeStack(){
        stackTop = null;
        maxStackSize = 100;
    }
    public boolean isEmptyStack(){
        return stackTop == null;
    }
    public boolean isFullStack(){
        return false;
    }
    public void push(int item){
        Node newNode = new Node(item);
        newNode.setLink(newNode);
        stackTop = newNode;
        maxStackSize++;
    }
    public Object peek()throws Exception{
        if(isEmptyStack()){
            throw new Exception("5555++");
        }
        return stackTop.getInfo();
    }
    public Object pop()throws Exception{
        Object newItem;
        if(isEmptyStack()){
            throw new Exception("555+++");
        }
        newItem = stackTop.getInfo();
        stackTop = stackTop.getLink();
        maxStackSize--;
        return newItem;
    }
    public int maxStackSize(){
        return maxStackSize;
    }
    
}
