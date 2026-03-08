package OOSD.Structural;

interface Coffee{
    double getCost();
    String getDesc();
}
// ทำกาแฟได้อย่างเดียว
class SimpleCoffee implements Coffee{
    public double getCost(){
        return 30;
    }
    public String getDesc(){
        return "Coffee";
    }
}
// ทำกาแฟและชงนมได้ด้วย
class MilkDecorator implements Coffee{
    Coffee c;
    MilkDecorator(Coffee c){
        this.c = c;
    }
    public double getCost(){
        return c.getCost() + 10;
    }
    public String getDesc(){
        return c.getDesc() + " + Milk";
    }
}

public class Decorator {
    public static void main(String[] args) {
        Coffee c = new MilkDecorator(new SimpleCoffee());
        System.out.println(c.getDesc());
        System.out.println(c.getCost());
    }
}
