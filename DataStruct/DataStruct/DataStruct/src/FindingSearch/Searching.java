import java.util.Random;
import java.util.ArrayList;
//บอกสถานะว่า ในตารางว่างหรือ โดนลบ

enum Status {
    DELETED, EMPTY
}

class HashEntry {
    int value;
    Status status;

    public HashEntry(int value) {
        this.value = value;
        this.status = Status.EMPTY;
    }

    public void delete() {
        this.status = Status.DELETED; 
    }

    public String toString() {
        return String.valueOf(value);
    }
}

class HashTable {
    private HashEntry[] table;
    private int size; 
    private final int capacity;

    public HashTable(int capacity) {
        this.capacity = capacity;
        this.table = new HashEntry[capacity];
        this.size = 0;
    }

    private int hash(int key) {
        return key % capacity;
    }

    // ใส่ข้อมูล
    public boolean insert(int value) {
        if (size == capacity) {
            return false; // ตารางเต็ม
        }

        int index = hash(value);
        int startIndex = index;

        // Linear Probing
        while (table[index] != null && table[index].status == Status.EMPTY) {
            if (table[index].value == value) {
                return false; // ข้อมูลซ้ำ ไม่ต้องเพิ่ม
            }

            index = (index + 1) % capacity;

            if (index == startIndex) {
                return false;
            }
        }
        table[index] = new HashEntry(value);
        size++;
        return true;
    }

    public int findAndRemove(int targetValue) {
        int index = hash(targetValue);
        int startIndex = index;
        int foundIndex = -1;

        // Linear Probing
        while (table[index] != null) {
            if (table[index].status == Status.EMPTY && table[index].value == targetValue) { //ในตารางมีช่องว่างและข้อมูลตรงกัน
                foundIndex = index;
                break;
            }

            index = (index + 1) % capacity; //ไปช่องถัดไป

            if (index == startIndex) {  //index ตรงกัน 
                break;
            }
        }

        if (foundIndex != -1) {     //ไม่เจอ
            //ลบทิ้ง
            int removedValue = table[foundIndex].value;
            table[foundIndex].delete();
            size--;
            return removedValue;
        }

        return -1; 
    }

    public void printTable() {
        System.out.println(String.format(" --- Hash Table [%d] ---", size));
        ArrayList<String> entries = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && table[i].status == Status.EMPTY) {  //table ไม่เป็นnull และ ว่าง
                entries.add(i + ":" + table[i].value); //เพิ่ม index กับตัวเลขเขาไปอยู่ในอาเรย์ลิส
            } else if (table[i] != null && table[i].status == Status.DELETED) {     //ไมเป็น null แต่โดน ลบ
                entries.add(i + ":" + table[i].value + "*"); // ทำเครื่องหมายว่าโดยลบ
            }
        }
        System.out.println(String.join(" ", entries));  //เพิ่มช่องว่าง
    }
}

public class Searching {
    public static void main(String[] args) {
        Random random = new Random();
        int n = random.nextInt(10, 21);
        int cap = 2 * n;
        System.out.println("--- Random Numbers[" + n + "] ---");
        int arr[] = new int[n];
        int number = 0;
        for (int i = 0; i < n; i++) {
            boolean found = true;   //reset every round
            number = random.nextInt(0, 101);
            if (i == 0) {   //ช่องแรกเก็บ number
                arr[i] = number;
                continue;
            }
            for (int j = 0; j < i; j++) {
                if (arr[j] == number) {     //เจอเลขซ้ำ
                    found = false;      //เจอตัวเลขซ้ำ
                    System.out.print("!" + number + " ");
                    i--;    //ไปช่องถัดไป
                }
            }
            if (found) {    //ไม่เจอ
                arr[i] = number;    //เก็บเข้าอาเรย์
                System.out.print(arr[i] + " ");
            }
        }
        System.out.println();
        HashTable hashTable = new HashTable(cap);
        for (int i = 0; i < arr.length; i++) {
            hashTable.insert(arr[i]);
        }
        hashTable.printTable();

        // search
        int count = 0;
        int fiding = 0;
        System.out.println("--- Finding Match ---");
        while (count < 3) {
            int m = random.nextInt(0 , 101);
            fiding++;
            int value_hash = 100 - m;
            int found = hashTable.findAndRemove(value_hash);
            if(found != -1){
                count++;
                System.out.print(" " + fiding + " ");   //ข้อมูลที่ต้องการ
                System.out.print("!" + found + " ");    // ข้อมูลที่ไม่ต้องการ
            }
        }
        System.out.println();
        System.out.println("Total Finding: " + fiding);
        hashTable.printTable();
    }
}
