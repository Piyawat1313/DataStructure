package OOSD.Behavioral;

interface PayStrategy{
    void pay(int amount);
}
class CreditCard implements PayStrategy{
    public void pay(int amt){
        System.out.println("Pay " + amt + " by Credit Card");
    }
}

class Prompay implements PayStrategy{
    public void pay(int amt){
        System.out.println("Pay " + amt + " by Prompay");
    }
}

class Cart {
    PayStrategy strategy;
    void setStrategy(PayStrategy s){
        strategy = s;
    }
    void checkout(int amt){
        strategy.pay(amt);
    }
}
public class Strategy {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.setStrategy(new CreditCard());
        cart.checkout(150);
    }
}
