public class ReverseNodesInKGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null)
            return null;

        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            if (k % 2 == 0) {
                for (int i = 0; i < k; i++) {
                    ListNode first = prev.next;
                    ListNode second = prev.next.next;

                    first.next = second.next;
                    second.next = first;
                    prev.next = second;
                    prev = first;
                }
            }
            else if(k % 2 != 0){
                for (int i = 0; i < k; i++) {
                    ListNode first = prev.next;
                    ListNode second = prev.next.next;
                    ListNode third = prev.next.next.next;

                    first.next = third.next;
                    third.next = second;

                }
            }
        }
        return dummy.next;
    }

    public ListNode reverseKGroup1(ListNode head, int k){
        if(head == null || k == 1){
            return head;
        }
        
        ListNode dummy = new ListNode();
        dummy.next = head;  //Node ตัวที่ 2 ชี้ที่ node แรกที่มีข้อมูล
        ListNode groupPrev = dummy; //node ตัวที่ 3 ชี้ที่ node ตัวที่ 2 
        
        while (true) {
            
            ListNode kth = getKthNode(groupPrev, k); // หาโนหดตัวที่ k ของกลุ่มปัจจุบัน
            // ถ้าเหลือไม่ถึง k node ให้หยุดทำ
            if(kth == null){
                break;
            }

            ListNode groupNext = kth.next;  //เก็บโหนดที่เริ่มต้นของกลุ่มถัดไปไว้ก่อน

            // ส่วนของการกลับด้าน โหนดภายในกลุ่ม
            ListNode prev = groupNext;  //ให้ตัวสุดท้ายของกลุ่ม ชี้ไปกลุ่มถัดไปเลยตั้งแต่แรก
            ListNode curr = groupPrev.next;

            // ส่วนของการเดินกลับด้านของ node
            while (curr != groupNext) {
                ListNode nextTmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextTmp;
            }
            // เชือมกลุ่มที่กลับด้านเสร็จแล้วเข้ากับส่วนที่เหลือ
            ListNode tmp = groupPrev.next;  //หางเดิมของกลุ่ม
            groupPrev.next = kth;   //หัวใหม่ของกกลุ่ม kth
            groupPrev = tmp;    //ขยับ groupPrev ไปรอทำกลุ่มถัดไป
        }
        return dummy.next;
    } 

    // สำหรับนับและหาโหนดตัวที่ k
    private ListNode getKthNode(ListNode curr, int k){
        while (curr != null && k > 0) {
            curr = curr.next;
            k--;
        }
        return curr;
    }
    public static void main(String[] args) {
        
    }
}
