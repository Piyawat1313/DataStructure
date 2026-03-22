package labTest;

import java.util.ArrayList;

interface Coffee{
    String getDescription();
    double getCost();
}

class Espresso implements Coffee{
    public String getDescription(){
        return "Espresso";
    }
    public double getCost(){
        return 60;
    }
}

class Latte implements Coffee{
    public String getDescription(){
        return "Latte";
    }
    public double getCost(){
        return 80;
    }
}

class Cappuccino implements Coffee{
    public String getDescription(){
        return "Cappuccino";
    }
    public double getCost(){
        return 75;
    }
}

class CoffeeFactory{
    public static Coffee createCoffee(String type){
        if(type.equals("espresso")) return new Espresso();
        
        if(type.equals("cappuccino")) return new Cappuccino();

        if(type.equals("latte")) return new Latte();

        throw new IllegalArgumentException("Unknow ");
    }
}

abstract class CoffeeDecorator implements Coffee{
    private Coffee coffee;
    
    public CoffeeDecorator(Coffee coffee){
        this.coffee = coffee;
    }

    public String getDescription(){
        return coffee.getDescription();
    }
    public double getCost(){
        return coffee.getCost();
    }
}

class MilkDecorator extends CoffeeDecorator{
    public MilkDecorator(Coffee coffee){
        super(coffee);
    }

    public String getDescription(){
        return super.getDescription() + " + Milk";
    }

    public double getCost(){
        return super.getCost() + 15.0;
    }
}

class SugarDecorator extends CoffeeDecorator{
    
    public SugarDecorator(Coffee coffee){
        super(coffee);
    }

    public String getDescription(){
        return super.getDescription() + " + Sugar";
    }

    public double getCost(){
        return super.getCost() + 10.0;
    }

}

class WhipCreamDecorator extends CoffeeDecorator{

    public WhipCreamDecorator(Coffee coffee){
        super(coffee);
    }

    public String getDescription(){
        return super.getDescription() + " + Whip Cream";
    }

    public double getCost(){
        return super.getCost() + 20.0;
    }
}

interface PaymentStrategy{
    void pay(double amount);
    String getMethodName();
}

class CashPayment implements PaymentStrategy{
    public void pay(double amount){
        System.out.println(getMethodName() + amount + " Baht");
    }

    public String getMethodName(){
        return "Payment by cash: ";
    }
}

class CreditCardPayment implements PaymentStrategy{
    private String cardNumber;

    public CreditCardPayment(String cardNumber){
        this.cardNumber = cardNumber;
    }

    public void pay(double amount){
        System.out.println(getMethodName() + amount + " Baht");
    }

    public String getMethodName(){
        return "Pay by card [XXXX-XXXX-XXXX-{" + cardNumber.substring(cardNumber.length() - 4) + " last digit}]: ";
    }
}

class QRCodePayment implements PaymentStrategy{
    public void pay(double amount){
        System.out.println(getMethodName() + amount + " Baht");
    }   

    public String getMethodName(){
        return "Scan QR code pay: ";
    }
}

class CashRegister{
    PaymentStrategy pay;
    public void setPaymentStrategy(PaymentStrategy pay){
        this.pay = pay;
    }

    public void checkout(double amount){
        setPaymentStrategy(pay);
    }
}

class OrderItem{
    private Coffee coffee;
    private int quantity;
    
    public OrderItem(Coffee coffee, int quantity){
        this.coffee = coffee;
        this.quantity = quantity;
    }

    public Coffee getCoffee(){
        return coffee;
    }

    public int getQuantity(){
        return quantity;
    }

    public double getSubtotal(){
        return coffee.getCost() * quantity;
    }
}

class Order{
    private ArrayList<OrderItem> orderItem;
    private String paymentMethod;

    public Order(){ 
        this.orderItem = new ArrayList<>();
    }

    public void addItem(Coffee coffee, int quantity){
        orderItem.add(new OrderItem(coffee, quantity));
    }

    public ArrayList<OrderItem> getItems(){
        return orderItem;
    }

    public double getTotalCost(){
        double total = 0;
        for(OrderItem item : orderItem){
            total += item.getSubtotal();
        }
        return total;
    }

    public void setPaymentMethod(String method){
        this.paymentMethod = method;
    }

    public String getPaymentMethod(){
        return paymentMethod;
    }
}

class OrderView{
    public void displayOrder(Order order){
        System.out.println("===== Order list =====");
        int i = 1;
        for(OrderItem item: order.getItems()){
            System.out.println(i + ". " + item.getCoffee().getDescription() + " x " + item.getQuantity() + " | " + item.getSubtotal() + " Baht");
            i++;
        }
        System.out.println("Payment Method: " + order.getPaymentMethod());
        System.out.println("Total: " + order.getTotalCost() + " Baht");
        System.out.println("====================");
    }

    public void displayReceipt(Order order){
        System.out.println("===== receipt =====");
        System.out.println("Payment amount: " + order.getTotalCost() + " Baht");
        System.out.println("Thank you for Service!");
        System.out.println("====================");
    }
}

class OrderController{
    private Order model;
    private OrderView view;
    private CashRegister cashRegister;

    public OrderController(){
        this.model = new Order();
        this.view = new OrderView();
        this.cashRegister = new CashRegister();
    }

    public void addItem(String type, String[] toppings, int qty){
        Coffee coffee = CoffeeFactory.createCoffee(type);
        for(String top : toppings){
            if(top.equals("milk")) coffee = new MilkDecorator(coffee);
            else if(top.equals("sugar")) coffee = new SugarDecorator(coffee);
            else if(top.equals("whip")) coffee = new WhipCreamDecorator(coffee);
        }
        model.addItem(coffee, qty);
        view.displayOrder(model);
    }

    public void setPayment(String method){
        model.setPaymentMethod(method);

        if(method.equals("cash"))         cashRegister.setPaymentStrategy(new CashPayment());
        else if(method.equals("card"))    cashRegister.setPaymentStrategy(new CreditCardPayment(method.split(":")[1]));
        else if(method.equals("qr"))      cashRegister.setPaymentStrategy(new QRCodePayment());

    }

    public void submitOrder(){
        cashRegister.checkout(model.getTotalCost());
        view.displayReceipt(model);
    }
}
public class CoffeeSystems {
    public static void main(String[] args) {
        Coffee c = CoffeeFactory.createCoffee("espresso");
        Coffee c2 = CoffeeFactory.createCoffee("latte");
        Coffee c3 = CoffeeFactory.createCoffee("cappuccino");

        System.out.println("======= Test Part 1 =========");
        System.out.println("Coffee: " + c.getDescription() + " | " + c.getCost());
        System.out.println("Coffee: " + c2.getDescription() + " | " + c2.getCost());
        System.out.println("Coffee: " + c3.getDescription() + " | " + c3.getCost());
        System.out.println("=============================");
        System.out.println();
        

        System.out.println("============= Test Part 2 ===================");
        Coffee c4 = CoffeeFactory.createCoffee("latte");
        Coffee c5 = CoffeeFactory.createCoffee("espresso");

        c4 = new MilkDecorator(c4);
        c4 = new SugarDecorator(c4);
        c5 = new WhipCreamDecorator(c5);
        c5 = new MilkDecorator(c5);

        System.out.println("Coffee: " + c4.getDescription() + " | " + c4.getCost());

        System.out.println("Coffee: " + c5.getDescription() + " | " + c5.getCost());
        System.out.println("=============================================");
        System.out.println();



        System.out.println("=============== Test Part 3 ==============================");
        CashRegister cash = new CashRegister();
        cash.setPaymentStrategy(new CreditCardPayment("1234"));
        // cash.checkout(c5);

        cash.setPaymentStrategy(new CashPayment());
        // cash.checkout(c5);

        System.out.println("==========================================================");
        System.out.println();




        System.out.println("=============== Test Part 4 ==============================");
        OrderController controller = new OrderController();
        controller.addItem("espresso", new String[]{"milk"}, 2);
        controller.addItem("cappuccino", new String[]{"whip"}, 1);

        controller.setPayment("card:123456789");
        controller.submitOrder();
        System.out.println("==========================================================");
        System.out.println();

    }
}
