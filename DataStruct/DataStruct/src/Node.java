class Node {
    private Object Info;
    private Node link;
    public Node(Object Info, Node link){
        this.Info = Info;
        this.link = link;
    }
    public Node(Object Info){
        this(Info, null);
    }
    public Node(){
        this(null,null);
    }
    public Object getInfo(){
        return this.Info;
    }
    public Node getLink(){
        return this.link;
    }
    public void setLink(Node newNode){
        this.link = newNode;
    }
    public void setInfo(Object Info){
        this.Info = Info;
    }
}
