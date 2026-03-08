package OOSD.Behavioral;

abstract class Handler{
    Handler next;
    Handler setNex(Handler n){
        this.next = n;
        return n;
    }
    abstract void handle(int level, String issue);
}

class Level1 extends Handler{
    void handle(int level, String issue){
        if(level <= 1) System.out.println("L1: " + issue);
        else if(next != null) next.handle(level, issue);
    }
}

class Level2 extends Handler{
    void handle(int level, String issue){
        if(level == 2) System.out.println("L2: " + issue);
        else if(next != null) next.handle(level, issue);
    }
}

class Level3 extends Handler{
    void handle(int level, String issue){
        if(level == 3) System.out.println("L3: " + issue);
        else if(next != null) next.handle(level, issue);
    }
}
public class ChainOfResponsibility {
    public static void main(String[] args) {
        Handler l1 = new Level1();
        l1.setNex(new Level2()).setNex(new Level3());
        l1.handle(1, "Password reset");
        l1.handle(3, "Server cash");
    }
}
