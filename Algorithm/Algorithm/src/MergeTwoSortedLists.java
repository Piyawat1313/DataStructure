import java.util.ArrayList;
import java.util.Collections;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
 

public class MergeTwoSortedLists {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 == null || list2 == null) return null;

        ArrayList<Integer> list = new ArrayList<>();
        while (list1 != null && list2 != null) {
            
            list.add(list1.val);
            list.add(list2.val);
            list1 = list1.next;
            list2 = list2.next;   
        }
        Collections.sort(list);
        ListNode start = list1;
        int count = 0;
        while (start != null) {
            if(start != null){
                start.val = list.get(count);
                start = start.next;
                count++;
            }
        }
        if(start == null){
            start = list2;
            while (start != null) {
                if(start != null){
                    start.val = list.get(count);
                    start = start.next;
                    count++;
                }
            }
        }
        
        return start;
    }

    public ListNode mergeTwoLists2(ListNode list1, ListNode list2){
        ListNode dummy = new ListNode();
        ListNode head = dummy;  //ListNode สำหรับสายใหมม่โดยเรียงข้อมูลแล้ว

        while (list1 != null && list2 != null) {
            // เรียงข้อมูลจากมากไปน้อยจาก node เก่า
            if(list1.val <= list2.val){ 
                head.next = list1;  //ตัวถัดไปชี้ที่ node ตัวแรก
                list1 = list1.next; // nodeตัวแรกขยับต่อไป
            }
            else{   //ถ้า node2 มีค่ามากกว่า node1
                head.next = list2;  //เอา node2 มาต่อกัน
                list2 = list2.next; //ขยับ node2 ไปตัวถัดไป
            }
            head = head.next;   //ขยับตัวชี้ไปหาตัวถัดไป
        }
        // ถ้า node1 หมดก่อน ให้เอา node ที่เหลือมาต่อท้ายได้เลยทั้งหมด
        if(list1 != null){
            head.next = list1;
        }
        else{
            head.next = list2;
        }
        return dummy.next;  //ส่งหัวแถวของ node ใหม่  จะต่อหลัง node Dummy เสมอ
    }
    public static void main(String[] args) {
        MergeTwoSortedLists m = new MergeTwoSortedLists();
        System.out.println(m.mergeTwoLists(new ListNode(1, new ListNode(2, new ListNode(4))), new ListNode(1, new ListNode(3, new ListNode(4)))));
    }
}
