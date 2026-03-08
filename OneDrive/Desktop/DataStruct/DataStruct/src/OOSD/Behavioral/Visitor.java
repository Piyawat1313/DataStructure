package OOSD.Behavioral;
interface Visitor1{
    void visit(Circle c);
    void visit(Rect r);
}
interface Shape {
    void accept(Visitor1 v);
}

class Circle implements Shape{
    double radius;
    Circle(double r){
        this.radius = r;
    }
    public void accept(Visitor1 v){
        v.visit(this);
    }
}
class Rect implements Shape{
    int w;
    int h;
    Rect(int w, int h){
        this.w = w;
        this.h = h;
    }
    public void accept(Visitor1 v){
        v.visit(this);
    }
}
class AreaCalc implements Visitor1{
    public void visit(Circle c){
        System.out.printf("Area: %.2f%n", Math.PI * c.radius * c.radius);
    }
    public void visit(Rect r){
        System.out.printf("Area: %d%n", r.h * r.w);
    }
}
public class Visitor {
    public static void main(String[] args) {
        Circle c = new Circle(15);
        Rect r = new Rect(15, 52);

        Visitor1 v = new AreaCalc();
        c.accept(v);
        r.accept(v);

    }
}
