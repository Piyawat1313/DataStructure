package Creatiional;

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

public class Prototype {
    public static void main(String[] args) {
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
