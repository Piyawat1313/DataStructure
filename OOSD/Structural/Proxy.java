package OOSD.Structural;

//รูปภาพ
interface Image {
    void display();
}
// รูปภาพจริงๆ
class RealImage implements Image {
    RealImage(String f) {
        System.out.println("Loading " + f);
    }

    public void display() {
        System.out.println("Displaying");
    }
}

// คอยควบคุมการเข้าถึง
public class Proxy implements Image {
    String file;
    RealImage real;

    Proxy(String f) {
        this.file = f;
    }
    public void display(){
        if(real == null) real = new RealImage(file);
        real.display();
    }

    public static void main(String[] args) {
        Image img = new Proxy("photo.jpg");
        img.display();
        img.display();
    }
}
