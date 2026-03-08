// shallow copy แบบมาตรฐาน
class Wallet {
    double money;

    public Wallet(double money) {
        this.money = money;
    }
}

class Person implements Cloneable {
    String name;
    Wallet wallet;

    public Person(String name, double money) {
        this.name = name;
        this.wallet = new Wallet(money);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // shallow copy
    }
}

// shallow copy constructor
class Document {
    StringBuilder contents;

    public Document(String text) {
        this.contents = new StringBuilder(text);
    }

    public Document(Document other) { // shallow copy constructor
        this.contents = other.contents;
    }
}

// shallow copy ใช้method clone() || Array.copyOf()
class Football {
    String name;

    public Football(String name) {
        this.name = name;
    }
}

// deep copy constructor
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

    public Computer(Computer com) {
        this.model = com.model;
        this.cpu = new CPU(com.cpu);
    }
}

// ใช้การ clone()
class District implements Cloneable {
    String bound;

    public District(String bound) {
        this.bound = bound;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Candidate implements Cloneable {
    String name;
    District district;

    public Candidate(String name, District district) {
        this.name = name;
        this.district = district;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Candidate cloned = (Candidate) super.clone(); // shallow copy ก่อน

        cloned.district = (District) this.district.clone(); // deep copy ในส่วนที่เป็น Object
        return cloned;
    }
}

public class Main {
    public static void main(String[] args) {
        Computer com1 = new Computer("Asus", new CPU("intel cor i7"));
        Computer com2 = new Computer(com1); // deep copy constructor
        com2.cpu.type = "Nvidia i9";
        System.out.println("com1: " + com1.cpu.type);
        System.out.println("com2: " + com2.cpu.type);
        System.out.println();

        try {
            Candidate c1 = new Candidate("Nattapong", new District("46"));
            Candidate c2 = (Candidate) c1.clone();
            c2.district.bound = "25";
            System.out.println("c1: " + c1.district.bound);
            System.out.println("c2: " + c2.district.bound);
        } catch (CloneNotSupportedException e) {
            // System.out.println(e.getMessage());
        }

        try {
            Person p1 = new Person("bag", 1000.0);
            Person p2 = (Person) p1.clone();
            p1.wallet.money -= 500.0;
            System.out.println("old: " + p1.wallet.money);
            System.out.println("new: " + p2.wallet.money);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Document d1 = new Document("SaWatDee\n");
            Document d2 = new Document(d1); // shallow copy
            d2.contents.append("Alsamaraigum");
            System.out.println("d1: \n" + d1.contents);
            System.out.println();
            System.out.println("d2: \n" + d2.contents);
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Football[] footballs1 = { new Football("Ronaldo"), new Football("Messi") };
            Football[] football2 = java.util.Arrays.copyOf(footballs1, footballs1.length);
            // shallow copy Array
            football2[0].name = "Neymar";
            System.out.println("Football1: " + footballs1[0].name);
            System.out.println("Football2: " + football2[0].name);
        } catch (Exception e) {

        }

    }
}
