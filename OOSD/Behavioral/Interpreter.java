package OOSD.Behavioral;

interface Expr{
    int eval();
}
class Num implements Expr{
    int val;
    Num(int v){
        val = v;
    }
    public int eval(){
        return val;
    }
}

class Add implements Expr{
    Expr left, right;
    Add(Expr l, Expr r){
        this.right = r;
        this.left = l;
    }
    public int eval(){
        return left.eval() + right.eval();
    }
}

class Multiply implements Expr{
    Expr left, right;
    Multiply(Expr l, Expr r){
        this.right = r;
        this.left = l;
    }
    public int eval(){
        return left.eval() * right.eval();
    }
}
public class Interpreter {
    public static void main(String[] args) {
        Num n = new Num(10);
        Num n2 = new Num(20);
        System.out.println(new Add(n, n2).eval());
        System.out.println(new Multiply(n, n2).eval());
    }
}
