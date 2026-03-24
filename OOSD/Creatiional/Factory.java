package Creatiional;

// Factory
interface Animal {
    void speak();
}

class Dog implements Animal {
    public void speak() {
        System.out.println("I am Dog");
    }
}

class Cat implements Animal {
    public void speak() {
        System.out.println("I am Cat");
    }
}

// โรงงานผลิต object
class AnimalFactory {
    public static Animal create(String types) {
        if (types.equals("dog"))
            return new Dog();
        if (types.equals("cat"))
            return new Cat();
        throw new IllegalArgumentException("Unknow: " + types);
    }
}


public class Factory {
    public static void main(String[] args) {
        System.out.println("=============================" + "Factory Pattern"
                + "=========================================");
        try {
            // บอกประเภทที่ต้องการ
            Animal a = AnimalFactory.create("dog"); // เลือกสิ่งที่จะทำได้แค่อย่างเดียว
            Animal b = AnimalFactory.create("cat");
            Animal c = AnimalFactory.create("Tiger");
            a.speak();
            b.speak();
            c.speak();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
