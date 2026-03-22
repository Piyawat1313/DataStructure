package OOSD;

class Singleton {
    private static Singleton instance;

    private Singleton() {

    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

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

// AbstractFactory
interface Button {
    void render();
}

interface Checkbox {
    void check();
}

class WinButton implements Button {
    public void render() {
        System.out.println("Windows Button");
    }
}

class WinCheckbox implements Checkbox {
    public void check() {
        System.out.println("windows Checkbox");
    }
}

interface GUIFactory {
    Button createButton();

    Checkbox createCheckbox();
}

class WindowsFactory implements GUIFactory {
    public Button createButton() {
        return new WinButton();
    }

    public Checkbox createCheckbox() {
        return new WinCheckbox();
    }
}

// Builder
class Pizza {
    private String size, crust, sauce;
    private boolean cheese;

    public String getSize() {
        return size;
    }

    public String getCrust() {
        return crust;
    }

    public String getsauce() {
        return sauce;
    }

    public boolean getCheese() {
        return cheese;
    }

    private Pizza(Builder b) {
        this.size = b.size;
        this.crust = b.crust;
        this.sauce = b.sauce;
        this.cheese = b.cheese;
    }

    static class Builder {
        String size, crust = "thin", sauce = "tomato";
        boolean cheese;

        Builder(String size) {
            this.size = size;   //ส่งคืน Builder object (ตัวมันเอง) ที่ถูกตั้งค่า size แล้ว
        }

        Builder crust(String c) {
            crust = c;
            return this;    //ส่งคืน Builder object (ตัวมันเอง) ที่ถูกตั้งค่า crust แล้ว
        }

        Builder cheese() {
            cheese = true;
            return this;    //ส่งคืน Builder object (ตัวมันเอง) ที่ถูกตั้งค่า cheese แล้ว
        }

        Pizza build() {
            return new Pizza(this); //สร้าง Object Pizza ตัวใหม่ขึ้นมาจริงๆ โดยเรียกใช้งาน Constructor ของคลาส Pizza
        }
    }
}

// Prototype Pattern Shallow copy
class Player {
    String name;

    public Player(String name) {
        this.name = name;
    }
}

// deep copy
class CPU {
    String type;

    public CPU(String type) {
        this.type = type;
    }

    public CPU(CPU other) {
        this.type = other.type;
    }
}

class Computer {
    String model;
    CPU cpu;

    public Computer(String model, CPU cpu) {
        this.model = model;
        this.cpu = cpu;
    }

    public Computer(Computer other) {
        this.model = other.model;
        this.cpu = new CPU(other.cpu);
    }
}

public class Creational {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s1 == s2);

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

        System.out.println("============================= Adstract Factory Pattern =================================");
        // สร้างโรงงานก่อน
        GUIFactory factory = new WindowsFactory();

        //ให้โรงงานสร้าง Object ให้
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();

        // เรียกใช้
        button.render();
        checkbox.check();

        System.out.println("============================= Builder Pattern =================================");

        // สอดใส้อะไรเข้าไปก็ได้
        Pizza p = new Pizza.Builder("Large").crust("thick").cheese().build();
        System.out.println(p.getSize() + " " + p.getCrust() + " " + p.getsauce() + " " + p.getCheese());

        System.out.println("============================= Prototype Pattern =================================");
        // Shallow Copy
        try {
            Player[] p1 = { new Player("Ronaldo"), new Player("Messi") };
            Player[] p2 = java.util.Arrays.copyOf(p1, p1.length);
            p2[1].name = "Neymar";
            System.out.println("P1: " + p1[1].name); // เปลี่ยนทั้งคู่
            System.out.println("P2: " + p2[1].name); // เปลี่ยนทั้งคู่
        } catch (Exception e) {

        }
        System.out.println();
        // deep copy
        Computer com1 = new Computer("Asus", new CPU("intel cor i7"));
        Computer com2 = new Computer(com1);
        com2.cpu.type = "Nvidia i9"; // เปลี่ยนแค่คอม 2
        System.out.println("com1: " + com1.cpu.type); // เหมือนเดิม
        System.out.println("com2: " + com2.cpu.type); // เปลี่ยน

    }
}
