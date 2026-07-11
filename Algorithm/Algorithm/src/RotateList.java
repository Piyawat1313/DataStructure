public class RotateList {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }

        // หาความยาวของ List หา Node ตัวสุดท้าย
        int length = 1;
        ListNode dummy = head;
        while (dummy.next != null) {
            dummy = dummy.next;
            length++;
        }

        // ปรับค่า K ให้เหมาะสม
        k = k % length;
        if(k == 0){
            return head;
        }

        // เชื่อม List ให้กลายเป็นวงกลม Circular Linked List
        dummy.next = head;

        // หาจุดตัดและ Head ตัวใหม่
        int stepToNewNode = length - k;
        ListNode newDummy = head;

        // วนลูปเดินนตามไปจำนวนก้าวเพื่อไปหยุดที่ newDummy
        for (int i = 1; i < stepToNewNode; i++) {
            newDummy = newDummy.next;
        }

        // ตั้ง Node ถัดไปเป็น Head ตัวใหม่
        ListNode newHead = newDummy.next;

        // ตัดความสัมมพันธ์ช่วงวงกลมทิ้ง ให้จุดสิ้นสุดกลับมาเป็น null
         newDummy.next = null;

         return newHead;
    }

    public static void main(String[] args) {
        RotateList r = new RotateList();
        System.out.println(r.rotateRight(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2));
    }
}
