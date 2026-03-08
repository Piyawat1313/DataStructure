package OOSD.Structural;

import java.util.HashMap;

//รูปร่างต้นไม้
class TreeType{
    String name, color;
    TreeType(String n, String c){
        this.name = n;
        this.color = c;
    }
    void draw(int x, int y){
        System.out.println(name + " at " + x + "," + y);
    }
}

//ปลูกต้นไม้
class TreeFactory{
    static HashMap<String, TreeType> pool = new HashMap<>();
    static TreeType get(String name, String color){
        String key = name + color;
        return pool.computeIfAbsent(key, k -> new TreeType(name, color));
    }
}
public class Flyweight {
        int x, y;
        TreeType type;
        public Flyweight(int x, int y, TreeType type){
            this.type = type;
            this.x = x;
            this.y = y;
        }
        void draw(){
            type.draw(x, y);
        }
        public static void main(String[] args) {
            TreeType oak = TreeFactory.get("Oak", "Green");
            TreeType mango = TreeFactory.get("mango", "yellow");
            Flyweight t1 = new Flyweight(10, 20, oak);
            Flyweight t2 = new Flyweight(50, 60, mango);
            t1.draw();
            t2.draw();
            System.out.println(t1.type == t2.type);
        }
}
