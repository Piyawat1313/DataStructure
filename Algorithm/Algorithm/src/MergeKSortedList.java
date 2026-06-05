import java.util.PriorityQueue;

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class MergeKSortedList {
    // สิ่งที่คิดได้
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode();
        ListNode head = dummy;
        for (int i = 0; i < lists.length && lists[i] != null; i++) {
            if (head.val <= lists[i].val) {
                head.next = lists[i];
                lists[i] = lists[i].next;
            }
            head = head.next;
        }
        return dummy.next;
    }

    public ListNode mergeKLists2(ListNode[] lists){
        // ถ้าค่าข้างใน Node เป็น null 
        if(lists == null || lists.length == 0) return null;

        //  สร้าง PriorityQueue โดยบอกให้มันเปรียบเทียบค่าจาก val (node ไหนค่าน้อยสุดให้อยู่หน้าสุด)
        // PriorityQueue เป็นโครงสร้างข้อมูลประเภท Queue ที่ข้อมูลแต่ละตัวจะมีระดับความสำคัญที่เรากำหนดไว้ เช่น ในนี้เรากำหนดไว้ว่า ถ้า node ตัวไหนมีค่าน้อยที่สุดให้ไปอยู่หน้าสุดเป็นต้น
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);

        // นำโนหดหัวแถวของทุกลิสต์ ใส่เข้าไปในคิว
        for (ListNode node : lists) {
            if(node != null){
                pq.add(node);
            }
        }

        // สร้าง  Listnode ใหม่
        ListNode dummy = new ListNode();
        ListNode curr = dummy;  //ตัวชี้ ชี้ที่หัวก่อน

        // ดึงตัวที่น้อยสุดออกจากคิวมาผูกต่อกัน
        while (!pq.isEmpty()) {
            ListNode minNode = pq.poll();   //ดึงค่าน้อยสุดบนยอดคิวออกมา
            curr.next = minNode;    //เอามาต่อท้ายในสายใหม่
            curr = curr.next;   //ขยับตัวชี้ไปตัวถัดไป

            // ถ้าโหนดที่ดึงออกมายังมีตัวถัดไปติดอยู่ ให้ส่งตัวถัดไปเข้าไปในคิวต่อ
            if(minNode.next != null){
                pq.add(minNode.next);
            }
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        
    }
}
