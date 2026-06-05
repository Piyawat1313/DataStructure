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

public class RemoveNode {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummyHead = new ListNode();    //ตัวชี้นำหน้า Head ไป 1 ก้าว
        dummyHead.next = head;
        ListNode fast = dummyHead;
        ListNode slow = dummyHead;
        // fast เดินไปก่อน
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        // เดิน fast slow ไปพร้อมๆกัน
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        // slow อยู่ก่อนหน้าโหนดที่ต้องการลบพอดี
        // สั่งให้ตัดลิ้งก์ข้ามโหนดนั้นไปเลย
        slow.next = slow.next.next;
        return dummyHead.next;  //คืนค่าที่ยังไม่ได้ลบออกไป
    }
    public void test(){
        RemoveNode r = new RemoveNode();
        ListNode h = r.removeNthFromEnd(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2);
        ListNode result = r.removeNthFromEnd(h, 2); //สั่งให้ลบตัวท้าย
        ListNode curr = result;

        // การปริ้นผลลัพธิ์
        System.out.print("[");
        while (curr != null) {
            System.out.print(curr.val);
            if(curr.next != null) System.out.print(", ");
            curr = curr.next;
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        RemoveNode re = new RemoveNode();
        re.test();
    }
}
