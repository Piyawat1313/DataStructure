package OOSD.Structural;

class TV{
    void on(){
        System.out.println("TV ON");
    }
}

class Speaker{
    void on(){
        System.out.println("Sound ON");
    }
}
class Lights{
    void dim(){
        System.out.println("Lights dimmed");
    }
}

//เอาการทำงานต่างๆมารวมกันให้อยู่ใน class เดียว
class HomeTheater{
    TV tv = new TV();
    Speaker spk = new Speaker();
    Lights lights = new Lights();

    void watchMovie(){
        lights.dim();
        spk.on();
        tv.on();
    }
}
public class Facade {
    public static void main(String[] args) {
         new HomeTheater().watchMovie();
    }
}
