package OOSD.Behavioral;

import java.util.ArrayList;

interface Observer{
    void update(String msg);
}

class Stock{
    ArrayList<Observer> subs = new ArrayList<>();
    double price;
    void subscriber(Observer o){
        subs.add(o);
    }
    void unSubscriber(Observer o){
        subs.remove(o);
    }
    void setPrice(double p){
        price = p;
        subs.forEach(o -> o.update("Price: " + p));
    }
}
public class Observer1 {
    public static void main(String[] args) {
        Stock apple = new Stock();
        apple.subscriber(msg -> System.out.println("App1: " + msg));
        apple.subscriber(msg -> System.out.println("App2: " + msg));
        apple.setPrice(150.5);
    }
}
