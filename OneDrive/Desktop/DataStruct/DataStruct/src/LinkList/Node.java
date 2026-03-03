package LinkList;
public class Node {
    private int info;
    private Node link;
    public Node(int info, Node link){
        this.info = info;
        this.link = link;
    }
    public Node(int newInfo){
        this(newInfo, null);
    }
    public Node(){
        this(0,null);
    }
    public void setInfo(int newInfo){
        this.info = newInfo;
    }
    public int getInfo(){
        return this.info;
    }
    public void setLink(Node newNode){
        this.link = newNode;
    }
    public Node getLink(){
        return link;
    }
    public String toString() {
    return ""+this.info;
    }
}