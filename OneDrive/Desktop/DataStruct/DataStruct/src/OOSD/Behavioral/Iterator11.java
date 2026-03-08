package OOSD.Behavioral;

import java.util.ArrayList;

// 1. ต้องมี Interface เพื่อกำหนดมาตรฐานการ "หยิบของ"
interface MyIterator<T> {
    boolean hasNext();
    T next();
}

class BookCollection {
    private ArrayList<String> books = new ArrayList<>();

    void add(String b) {
        books.add(b);
    }

    // 2. คืนค่าเป็น Interface ที่เราสร้างไว้
    MyIterator<String> createIterator() {
        // ใช้ Anonymous Class แบบที่คุณเขียน (ถูกต้องแล้ว)
        return new MyIterator<String>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < books.size();
            }

            @Override
            public String next() {
                return books.get(i++);
            }
        };
    }
}

public class Iterator11 {
    public static void main(String[] args) {
        BookCollection collection = new BookCollection();
        collection.add("Design Patterns");
        collection.add("Clean Code");
        collection.add("Java Programming");

        // 3. วิธีใช้งาน: เหมือนปุ่ม Next บนเครื่องเล่นเพลงที่คุณจำไว้
        MyIterator<String> it = collection.createIterator();
        
        while (it.hasNext()) {
            System.out.println("Reading book: " + it.next());
        }
    }
}