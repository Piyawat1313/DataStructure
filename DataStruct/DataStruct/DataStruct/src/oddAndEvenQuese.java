import java.util.Random;


public class oddAndEven {
    public static void main(String[] args) {
        try {
            Random random = new Random();
            Quese Odd = new Quese();
            Quese Even = new Quese();
            int number = random.nextInt(11) + 10;
            System.out.println("--- Random Numbers[" + number + "] ---");
            for (int i = 0; i < number; i++) {
                int r = random.nextInt(101);
                System.out.print(r + " ");
                if(r % 2 == 0){
                    Even.enQuese(r);    //เอาข้อมูลเข้าคิว
                }
                else{
                    Odd.enQuese(r);
                }
            }
            System.out.println();
            System.out.println("---Even Quese[" + Even.getSize() + "]---");

            int sizeEven = Even.getSize();
            for (int i = 0; i < sizeEven; i++) {    //ปริ้นข้อมูลคิวออกมาทั้งหมด
                Object d = Even.deQuese();      //ข้อมูลเก็บลงใน Object ตัวใหม่
                System.out.print((int)d + " ");
                Even.enQuese(d);    //เอาไปใส่ไว้ใน คิวต่อ  
            }
            System.out.println();
            System.out.println("---Odd Quese[" + Odd.getSize() + "]---");
            
            int sizeOdd = Odd.getSize();
            for (int i = 0; i < sizeOdd; i++) {     //ปริ้นข้อมูลคิวออกมาทั้งหมด
                Object d = Odd.deQuese();       //ข้อมูลเก็บลงใน Object ตัวใหม่
                System.out.print((int)d + " ");
                Odd.enQuese(d);     //เอาไปใส่ไว้ใน คิวต่อ
            }
            System.out.println();
            int countOdd = 0;
            int countEven = 0;
            while (!Even.isEmptyQuese() && !Odd.isEmptyQuese()) {
                int frontEven = (int)Even.front();      //ตัวหน้าของ Even
                int frontOdd = (int)Odd.front();        //ตัวหน้าของ Odd
                if(frontEven > frontOdd){       //ถ้า Even ชนะ
                    Even.deQuese();     //ลบข้อมูลตัวหน้าออกจาก คิว
                    Odd.enQuese(Odd.front());   //เอาข้อมูลของ Odd ตัวหน้าเข้าไปอยู่ในคิวใหม่
                    Odd.deQuese();  //ลบข้อมูลของ Odd ออกไป
                    countEven++;    
                }
                else if(frontOdd > frontEven){      //ถ้า Odd win
                    Odd.deQuese();      //ลบข้อมูลตัวหน้าของ Odd ออกไป
                    Even.enQuese(Even.front());     //เอาข้อมูลตัวหน้าของ Even เข้าไปอยู่ในคิว
                    Even.deQuese(); //ลบข้อมูลของ Even ออกไป
                    countOdd++;
                }
            }
            if(countEven > countOdd){
                System.out.println("===> Winner is Even!");
                System.out.println("Round = " + (countEven + countOdd) + ", Win=(" + "E:" + countEven + ",O:" + countOdd + "), Odd Left = " + Odd.getSize());
            }
            else if(countOdd > countEven){
                System.out.println("===> Winner is Odd!");
                System.out.println("Round = " + (countOdd + countEven) + ", Win=(E:" + countEven + ",O:" + countOdd + "), Even Left = " + Even.getSize());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }  
}
