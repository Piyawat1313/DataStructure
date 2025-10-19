package TREES;
public class Tree {
    private Object info = null;
    private Tree left = null;
    private Tree right = null;
    public Tree(Object info, Tree left, Tree right){
        this.info = info;
        this.left = left;
        this.right = right;
    }
    public Tree (Object info){
        this(info, null, null);
    }
    public Tree(){
        this(null, null, null);
    }
    public Object getInfo(){
        return info;
    }
    public void setInfo(Object newInfo){
        this.info = newInfo;
    }
    public Tree getLeft(){
        return left;
    }
    public void setLeft(Tree newLeft){
        this.left = newLeft;
    }
    public Tree getRight(){
        return right;
    }
    public void setRight(Tree newRight){
        this.right = newRight;
    }

}
