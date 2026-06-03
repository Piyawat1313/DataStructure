
class ListNode{
    int val;
    ListNode next;
    ListNode(){

    }
    
    ListNode(int val){
        this.val = val;
    }

    ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
    }
}

public class AddToNumber {
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2){
        ListNode dumyHead = new ListNode();
        
        ListNode current = dumyHead;    //ตัวชี้ชี้ไปที่หัวก่อน

        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if(l1 != null){
                sum += l1.val;
                l1 = l1.next;
            }
            if(l2 != null){
                sum += l2.val;
                l2 = l2.next;
            }
            carry = sum / 10;   //ปัดเศษทิ้ง
            current.next = new ListNode(sum % 10); //ใส่เชขได้แค่ 0 - 9

            current = current.next; //ไป node ถัดไป
        }
        return dumyHead.next;   //return ค่าออกมาโดยที่ไม่เป็น 0
    }

    public static void test(){
        ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(3)));
        ListNode l2 = new ListNode(1, new ListNode(2, new ListNode(3)));

        ListNode result = addTwoNumbers(l1, l2);
        while (result != null) {
                System.out.print(result.val + (result.next != null ? " -> " : ""));
            result = result.next;
        }
    }
    public static void main(String[] args) {
        test();
    }
}
