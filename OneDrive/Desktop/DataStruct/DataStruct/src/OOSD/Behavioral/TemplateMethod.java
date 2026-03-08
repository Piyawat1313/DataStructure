package OOSD.Behavioral;

abstract class BeverageMaker{
    // สิ่งที่ต้องทำได้
    final void make(){
        boilWater();
        brew();
        pourIncup();
        addExtras();
    }
    // ต้มน้ำได้
    void boilWater(){
        System.out.println("Boiling....");
    }
    // เทใส่ถ้วย
    void pourIncup(){
        System.out.println("Pouring.....");
    }
    abstract void brew();
    void addExtras(){}
}

// ลูกสามารถเติมสิ่งที่ แม่ ทำไม่ได้
class Teamaker extends BeverageMaker{
    // ชงได้
    void brew(){
        System.out.println("Steeping tea");
    }
    @Override
    // เติม เลม่อนได้
    void addExtras(){
        System.out.println("Adding lemon");
    }
}
public class TemplateMethod {
    public static void main(String[] args) {
        new Teamaker().make();
    }
}
