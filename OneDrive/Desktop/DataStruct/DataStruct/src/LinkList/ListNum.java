package LinkList;
public class ListNum {
    private Node head = null;

    public ListNum() {
    }

    public void addNode(int newInfo) {
        Node newNode = new Node(newInfo);
        newNode.setLink(head);
        head = newNode;
    }

    public String toString() {
        Node trav = head;
        String str = "";
        while (trav != null) {
            str += trav.toString();
            // str=str.concat(""+trav.getInfo());
            trav = trav.getLink();
            if (trav != null)
                str = str.concat(" | ");
        }
        return str;
    }

    // size
    public int size() {
        int legth = 0;
        Node trav = head;
        while (trav != null) {
            trav = trav.getLink();
            legth++;
        }
        return legth;
    }

    public void addLast(int newInfo) {
        Node newNode = new Node(newInfo);
        Node trav1 = head;
        Node trav2 = head;
        while (trav1 != null) {
            trav2 = trav1;
            trav1 = trav1.getLink();
        }
        if (trav1 != trav2) {
            trav2.setLink(newNode);
        } else {
            head = newNode;
        }
    }
    public void addSort(int number){
	Node newNode = new Node(number);
	Node trav1 = head;
	Node trav2 = head;
	while(trav1 != null && trav1.getInfo() <= newNode.getInfo()){
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

    public void delFront(){
        // Node delNode = new Node();
        if(head != null) head = head.getLink();
    }

    public void delBack(){
        // Node delNode;
        Node trav1 = head;
        Node trav2 = head;
        while (trav1.getLink() != null) {
            trav2 = trav1;
            trav1 = trav1.getLink();
        }
        if(trav2 != trav1){
            trav2.setLink(null);
        }
    }

    public int search(int info){
        Node newNode = new Node(info);
        Node trav1 = head;
        int count=1;
        while (trav1 != null && trav1.getInfo() != newNode.getInfo()) {
            count++;
            trav1 = trav1.getLink();
        }
        if(trav1 == null) return 0;
        return count;
    }

    public void delInfo(int info){
        Node trav1;
        Node trav2;
        trav1 = trav2 = head;
        while (trav1 != null) {
            if(trav1.getInfo() == info){
                if(trav1 != head){
                    trav2.setLink(trav1.getLink());
                }else head = trav1.getLink();
            }else{
                trav2 = trav1;
            }

            trav1 = trav1.getLink();
        }

    }

    public static void main(String[] args) {
        ListNum list = new ListNum();
        list.addNode(1);
        list.addNode(2);
        list.addNode(3);
        // String nameLink = list.toString();
        //System.out.println(nameLink);
        System.out.println("Size: " + list.size());
        // list.addLast(54);
        // list.addLast(150);
        //System.out.println(list);
        list.addSort(5);
        list.addSort(3);
        list.addSort(1);

        System.out.println(list);
        System.out.println("del");
        list.delFront();
        list.delBack();
        System.out.println(list);
        System.out.println("index = " + list.search(100));

        System.out.println("del");
        list.delInfo(2);
        System.out.println(list);

    }
}
