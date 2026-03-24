package Creatiional;

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

public class Builder {
    public static void main(String[] args) {
        System.out.println("============================= Builder Pattern =================================");

        // สอดใส้อะไรเข้าไปก็ได้
        Pizza p = new Pizza.Builder("Large").crust("thick").cheese().build();
        System.out.println(p.getSize() + " " + p.getCrust() + " " + p.getsauce() + " " + p.getCheese());
    }
}
