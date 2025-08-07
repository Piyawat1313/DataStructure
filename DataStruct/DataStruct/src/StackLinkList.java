class Stack {
    private int stackSize = 0;
    private Node stackTop = null;
    public Stack(){
        stackTop = null;
    }
    public void initializeStack(){
        stackTop = null;
    }
    public boolean isEmptyStack(){
        return stackTop == null;
    }
    public boolean isFullStack(){
        return false;
    }
    public void push(Object newItem){
        Node newNode = new Node(newItem);
        newNode.setLink(stackTop);
        stackTop = newNode;
        stackSize++;
    }
    public Object peek(){
        if(isEmptyStack()){

        }
        return stackTop.getInfo();
    }
    public Object pop()throws Exception{
        Object itemInfo;
        if(isEmptyStack()){
            
        }
        itemInfo = stackTop.getInfo();
        stackTop = stackTop.getLink();
        stackSize--;
        return itemInfo;
    }
    public int getSize(){
        return stackSize;
    }

}
