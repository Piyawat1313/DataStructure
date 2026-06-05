
public class SwapNodesInPairs {
    public ListNode swapPairs(ListNode head) {
        if (head == null) return null;

        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode prev = dummy;  //ทำหน้าที่เป็นตัวแทนของโหนดที่อยู่ก่อนหน้าที่จะสลับ

        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = prev.next.next;

            // วีธีการสลับเส้นชี้
            first.next = second.next;   //ให้ตัวแรกข้ามไปชี้ node ถัดไปของคู่ถัดไป
            second.next = first;    //ให้ตัวที่สองชี้กลับมาหาตัวแรก
            prev.next = second;     // ให้โหนดก่อนหน้าชี้มาตัวที่สองแทน

            prev = first;   //ขยับตัวชี้ ข้ามไปสองตำแหน่ง เพื่อเตรียมสลับคู่ถัดไป
        }
        return dummy.next;
        
    }
    public static void main(String[] args) {
        SwapNodesInPairs s = new SwapNodesInPairs();
        System.out.println(s.swapPairs(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))))));
    }
}
