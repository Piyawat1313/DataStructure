class RuntimeException extends Exception{}
class AnimalsOutForAWalk extends RuntimeException{}
class ExhibitClosed extends RuntimeException{}
class ExhibitClosedForLunch extends ExhibitClosed{}


// single Inheritance สืบทอดแบบเครือญาติ
class Homosepien{}
class Human extends Homosepien{}
class Baby extends Human{}
class Boy extends Baby{}

// Multiple Inheritance สืบทอดแบบนำโครงสร้างไปแยกประเภทต่างๆ
class Motorcycle{}
class BigBike extends Motorcycle{}
class WaveHonda extends Motorcycle{}
class Vespa extends Motorcycle{}

// Generalization สร้างclassแม่เพื่อรวมการทำงานต่างๆเข้าด้วยกัน
class Mother{
    public Mother(){

    }
    public void eat(){

    }
    public void kind(){

    }
}

// Spacialization สร้างclassลูกเพื่อเพิ่มลักษณะเด่นที่ต่างจากclassแม่
class Child extends Mother{
    public Child(){
        super();
    }
    public void eat(){

    }
    public void kind(){

    }
    // ลักษณะเด่นของ sub class
    public void tall(){

    }
}


class Pen{
    public void write(){
        System.out.println("write");
    }
}
class Person{
    // Interaction: ทำความรู้จัก class Pen ก่อน
    public void usePen(Pen pen){
        // Object Intercoraction: class Person ใช้ class Pen ทำงานที่นี่ 
        pen.write();
    } 
}


abstract class A{
    int a;
    public void PP(){}
    public abstract void BB();
}
// Concrete class: class ที่ method พร้อมใช้งานและนำไปสร้างObjectได้ 
class concrete extends A{
    public void PP(){}
    public void BB(){}
}


abstract class Structure{
    // public || protected สามารถใช้ได้ทั้งสองรูปแบบ
    // แบบ public 
    public void hee(){} //ไม่ใช้abstract
    public abstract void kuy(); //ใช้abstract
    // แบบ protected
    protected void print1(){} //ไม่ใช้abstract
    protected abstract void print2(); //ใช้abstract
}

interface Architecture{
    int name = 15; //ตัวแปรจะเป็น public static final
    public void print(); //method ใน interface จะเป็น abstract
    public default void print2(){}//interdace สามารถมี method defaultได้ สามารถเขียนโค้ดข้างในได้
}

interface OO{}
// interfaceด้วยกันสามารถสืบทอดได้โดยตรง
interface PP extends OO{}


class A1{
    public static void print(){} //Hiding method ใช้ในกรณีที่ method เป็น Static
}
class B1 extends A1{
    public static void print(){} //ซ่อน Hiding ของ super class
}

class F1{
    public static void BB(){}
}
class F2 extends F1{
    public void change(){
        super.BB();
        F1.BB();
    }
}
public class App {
    
    public static void main(String[] args) {
        // การสร้าง Object
        
        
        // การcatch ต้องจับจากสิ่งเล็กๆ ไปหาสิ่งใหญ่ๆ
        try {
            System.out.println("See Animal");
            // โยน Exception
            throw new AnimalsOutForAWalk();
            //sub class
        } catch (AnimalsOutForAWalk e) {
            System.out.println("AnimalsOutForAWalk");
        }
        try{
            // โยน Exception
            throw new ExhibitClosedForLunch();
            //sub class
        }catch(ExhibitClosedForLunch e){
            System.out.println("ExhibitClosedForLunch");
        }
        try{
            // โยน Exception
            throw new ExhibitClosed();
            //sub class
        }catch(ExhibitClosed e){
            System.out.println("ExhibitClosed");
        }
        try{
            // โยน Exception
            throw new RuntimeException();
            //sub class
        }catch(RuntimeException e){
            System.out.println("RuntimeException");
        }
        try{
            // โยน Exception
            throw new Exception();
            // super class
        }catch(Exception e){
            System.out.println("Exception");
        }
        try{
            throw new Throwable();
        }catch(Throwable e){
            System.out.println("ThrowAble");
        }  
    }
}

