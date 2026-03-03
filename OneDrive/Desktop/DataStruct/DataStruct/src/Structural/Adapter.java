package Structural;

interface ModernCharger{    //สายชาจแบบใหม่
    void chargeWithUSBC();
}

class OldCharger{   //วายชาจแบบเก่า
    void chargeWithMicroUSB(){
        System.out.println("Charging with MicroUSB");
    }
}

//ตัวแปลง เพื่อให้ใช้งานร่วมกันได้
public class Adapter implements ModernCharger {
    private OldCharger old = new OldCharger();
    public void chargeWithUSBC(){
        old.chargeWithMicroUSB();
    }
    public static void main(String[] args) {
        Adapter adapter = new Adapter();
        adapter.chargeWithUSBC();
    }
}
