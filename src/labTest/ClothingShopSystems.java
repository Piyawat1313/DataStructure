package labTest;

import java.util.ArrayList;

//Factory
interface Garment {
    String getDescription();

    double getPrice();
}

class MilitaryJacket implements Garment {

    @Override
    public String getDescription() {
        return "Military Jacket";
    }

    @Override
    public double getPrice() {
        return 1500.0;
    }
}

class IvyBlazer implements Garment {

    @Override
    public String getDescription() {
        return "Ivy Blazer";
    }

    @Override
    public double getPrice() {
        return 2200.0;
    }
}

class WorkwearDenim implements Garment {

    @Override
    public String getDescription() {
        return "Workwear Denim";
    }

    @Override
    public double getPrice() {
        return 1200.0;
    }
}

class FactoryGarment {
    public static Garment create(String type) {
        if (type.equals("Military"))
            return new MilitaryJacket();
        else if (type.equals("Ivy"))
            return new IvyBlazer();
        else if (type.equals("Workwear"))
            return new WorkwearDenim();
        throw new IllegalArgumentException("Unknow");
    }
}

// Decorator
abstract class DecoratorGarment implements Garment {
    private Garment garment;

    public DecoratorGarment(Garment garment) {
        this.garment = garment;
    }

    public String getDescription() {
        return garment.getDescription();
    }

    public double getPrice() {
        return garment.getPrice();
    }
}

class CustomPatch extends DecoratorGarment {
    public CustomPatch(Garment garment) {
        super(garment);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Vintage Patch";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 150.0;
    }
}

class ChainStitchHem extends DecoratorGarment {
    public ChainStitchHem(Garment garment) {
        super(garment);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Chain Stitch Hem";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 250.0;
    }
}

class RepairService extends DecoratorGarment {
    public RepairService(Garment garment) {
        super(garment);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Sashiko Repair";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 200.0;
    }
}

// Startegy Pattern
interface ShippingStartegy {
    double calculateShippingCost(double orderTotal);

    String getShippingMethodName();
}

class StandardShipping implements ShippingStartegy {

    @Override
    public double calculateShippingCost(double orderTotal) {
        if (orderTotal >= 2000) {
            return 0;
        }
        return 50;
    }

    @Override
    public String getShippingMethodName() {
        return "Standard Shipping ";
    }
}

class ExpressShipping implements ShippingStartegy {

    @Override
    public double calculateShippingCost(double orderTotal) {
        return 100;
    }

    @Override
    public String getShippingMethodName() {
        return "จัดส่งแบบ Express";
    }
}

class StorePickup implements ShippingStartegy {

    @Override
    public double calculateShippingCost(double orderTotal) {
        return 0;
    }

    @Override
    public String getShippingMethodName() {
        return "รับที่หน้าร้าน ";
    }
}

//set วิธีการจ่ายเงิน
class Transport {
    private ShippingStartegy shippingStartegy;

    public void setTransport(ShippingStartegy shippingStartegy) {
        this.shippingStartegy = shippingStartegy;
    }

    public double calculateShipping(double orderTotal) {
        if (shippingStartegy == null)
            return 0;
        return shippingStartegy.calculateShippingCost(orderTotal);
    }

    public String getTransportMethodName() {
        if (shippingStartegy == null)
            return "ยังไม่ได้เลือก";

        return shippingStartegy.getShippingMethodName();
    }
}

//model
class CartItem {
    private Garment garment;
    private int quantity;

    public CartItem(Garment garment, int quantity) {
        this.garment = garment;
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return garment.getPrice() * quantity;
    }

    public Garment getGarment() {
        return garment;
    }
}

//model
class Cart {
    private ArrayList<CartItem> cart;
    private ShippingStartegy shippingStartegy;

    public Cart() {
        this.cart = new ArrayList<>();
    }

    public void addItem(Garment garment, int quantity) {
        cart.add(new CartItem(garment, quantity));
    }

    public double getTotalItemsCost() {
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            total += cart.get(i).getSubtotal();
        }
        return total;
    }

    public double getFinalTotal() {
        return getTotalItemsCost() + shippingStartegy.calculateShippingCost(getTotalItemsCost());
    }

    public ArrayList<CartItem> getItems() {
        return cart;
    }

    public ShippingStartegy getShippingStartegy() {
        return shippingStartegy;
    }

    public void setShippingMethod(ShippingStartegy shippingStartegy){
        this.shippingStartegy = shippingStartegy;
    }
}

class CheckoutView {

    public void displayCart(Cart cart) {
        System.out.println("===== ตะกร้าสินค้า Padfoon Vintage =====");
        for (int i = 0; i < cart.getItems().size(); i++) {
            System.out.println((i + 1) + "." + cart.getItems().get(i).getGarment().getDescription() + " | "
                    + cart.getItems().get(i).getGarment().getPrice() + " บาท");
        }
        System.out.println("--------------------------------------");
        System.out.println("รวมค่าสินค้า: " + cart.getTotalItemsCost());
        System.out.println("วิธีจัดส่ง: " + cart.getShippingStartegy().getShippingMethodName());
        System.out.println("ยอดชำระสุทธิ: " + cart.getFinalTotal());
        System.out.println("======================================");
    }

    public void displayInvoice(Cart cart) {
        System.out.println("===== ใบเสร็จจัดส่ง =====");
        System.out.println("ชำระแล้ว: " + cart.getFinalTotal());
        System.out.println("จัดส่งผ่าน: " + cart.getShippingStartegy().getShippingMethodName());
        System.out.println("ขอบคุณที่อุดหนุนสินค้าวินเทจของเรา!");
    }
}

class StoreController {
    private Cart model;
    private CheckoutView view;

    public StoreController(){
        model = new Cart();
        view = new CheckoutView();
    }

    public void addItem(String style, String[] alterations, int qty) {
        Garment shirt = FactoryGarment.create(style);

        for (int i = 0; i < alterations.length; i++) {
            if (alterations[i].equals("Vintage")){
                shirt = new CustomPatch(shirt);
            }

            else if (alterations[i].equals("Chain")){
               shirt = new ChainStitchHem(shirt);
            }

            else if (alterations[i].equals("Sashiko")){
                shirt = new RepairService(shirt);
            }
        }
        
        model.addItem(shirt, qty);
    }

    public void setShippingMethod(ShippingStartegy shippingStartegy) {
        model.setShippingMethod(shippingStartegy);
    }

    public void checkout() {
        view.displayCart(model);
        view.displayInvoice(model);
    }
}

public class ClothingShopSystems {
    public static void main(String[] args) {
        Garment g = FactoryGarment.create("Military");
        Garment g2 = FactoryGarment.create("Ivy");
        Garment g3 = FactoryGarment.create("Workwear");
        System.out.println("Item: " + g.getDescription() + " | " + g.getPrice());
        System.out.println("Item: " + g2.getDescription() + " | " + g2.getPrice());
        System.out.println("Item: " + g3.getDescription() + " | " + g3.getPrice());
        System.out.println();

        g3 = new ChainStitchHem(g3);
        g3 = new RepairService(g3);
        g = new CustomPatch(g);
        System.out.println("Item: " + g3.getDescription() + " | " + g3.getPrice());
        System.out.println("Item: " + g.getDescription() + " | " + g.getPrice());
        System.out.println();

        System.out.println("สินค้า: " + g2.getDescription() + " | " + g2.getPrice());
        System.out.println(new StandardShipping().getShippingMethodName()
                + new StandardShipping().calculateShippingCost(g2.getPrice()));
        System.out.println("สินค้า: " + g3.getDescription() + " | " + g3.getPrice());
        System.out.println(new StandardShipping().getShippingMethodName()
                + new StandardShipping().calculateShippingCost(g3.getPrice()));
        System.out.println();


        StoreController storeController = new StoreController();
        storeController.addItem("Military", new String[] { "Vintage" }, 1);
        storeController.addItem("Workwear", new String[] {"Chain"} , 2);
        storeController.setShippingMethod(new StandardShipping());
        storeController.checkout();
    }
}
