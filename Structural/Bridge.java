package Structural;

//อยากให้วาดรูปวงกลม
interface DrawAPI{
    void drawCircle(int x, int y, int r);
}
//เอาสีแดง
class RedDraw implements DrawAPI{
    public void drawCircle(int x, int y, int r){
        System.out.println("Red circle at " + x + ", " + y + ", " + r);
    }
}

//เอาสีน้ำเงิน
class BlueDraw implements DrawAPI{
    public void drawCircle(int x, int y, int r){
        System.out.println("Blue circle at " + x + ", " + y + ", " + r);
    }
}

//ลงมือวาดวงกลม
class Circle{
    DrawAPI api;
    int x;
    int y;
    int r;
    Circle(int x, int y, int r, DrawAPI api){
        this.x = x;
        this.y = y;
        this.r = r;
        this.api = api;
    }
    void draw(){
        api.drawCircle(x, y, r);
    }
}
public class Bridge {
    public static void main(String[] args) {
        new Circle(5, 10, 15, new RedDraw()).draw();
        new Circle(20, 30, 500, new BlueDraw()).draw();
    }   
}
