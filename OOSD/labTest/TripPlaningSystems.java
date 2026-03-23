package labTest;
import java.util.Scanner;

interface Transport {
    String getDescription();
    double getCost();
}

class Flight implements Transport {
    public String getDescription() {
        return "Flight";
    }

    public double getCost() {
        return 3000.0;
    }
}

class Train implements Transport {
    public String getDescription() {
        return "Train";
    }

    public double getCost() {
        return 3000.0;
    }
}

class Bus implements Transport {
    public String getDescription() {
        return "Bus";
    }

    public double getCost() {
        return 3000.0;
    }
}
class TransportFactory{
    public static Transport createTransport(String type){
        if(type.equals("Train"))  return new Train();
        if(type.equals("Flight")) return new Flight();
        if(type.equals("Bus")) return new Bus();

        throw new IllegalArgumentException("Unknow transport type");
    }
}

class TransportDecorator implements Transport{
    protected Transport transport;
    public TransportDecorator(Transport transport){
        this.transport = transport;
    }
    public String getDescription(){
        return transport.getDescription();
    }
    public double getCost(){
        return transport.getCost();
    }
}

class WifiDecorator extends TransportDecorator{
    public WifiDecorator(Transport transport){
        super(transport);
    }

    public String getDescription(){
        return transport.getDescription() + " + Wifi";
    }

    public double getCost(){
        return transport.getCost() + 150.0;
    }
    
}

class MealDecorator extends TransportDecorator{
    public MealDecorator(Transport transport){
        super(transport);
    }

    public String getDescription(){
        return transport.getDescription() + " +Meal";
    }
    
    public double getCost(){
        return transport.getCost() + 300.0;
    }

}
class LoungeAccessDecorator extends TransportDecorator{
    public LoungeAccessDecorator(Transport transport){
        super(transport);
    }

    public String getDescription(){
        return transport.getDescription() + " +Lounge";
    }

    public double getCost(){
        return transport.getCost() + 800;
    }
}

interface DiscountStrategy{
    double calculateFinalPrice(double basePrice);
}

class NoDiscountStrategy implements DiscountStrategy{
    
    public double calculateFinalPrice(double basePrice){
        return basePrice;
    }
}

class EarlyBirdStrategy implements DiscountStrategy{
    
    public double calculateFinalPrice(double basePrice){
        double temp = basePrice * (20 / 100);
        return basePrice - temp;
    }
}

final class StudentDiscountStrategy implements DiscountStrategy{

    public double calculateFinalPrice(double basePrice){
        double discount = basePrice * (30/100);
        return basePrice - discount;
    }
}

class FlashSaleStrategy implements DiscountStrategy{
    public double calculateFinalPrice(double basePrice){
        
        double discount = 500;
        if(basePrice < discount){
            discount = 0;
        } 
        return basePrice - discount;
    }
}

class TripModel{
    private Transport transport;
    private DiscountStrategy discountStrategy;

    public void setTransport(Transport transport){
        this.transport = transport;
    }
    
    public Transport geTransport(){
        return transport;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy){
        this.discountStrategy = discountStrategy;
    }

    public String getTripDecription(){
        return transport.getDescription();
    }

    public double getFinalPrice(){
        double basePrice = transport.getCost();
        return discountStrategy.calculateFinalPrice(basePrice);
    }

    public double getBasePrice(){
        return transport.getCost();
    }
}


class TripView{
    public void printTripDetails(String description, double basePrice ,double finalPrice){
        System.out.println("--- Trip Summary ---");
        System.out.println("Itinerary: " + description);
        System.out.println("Original Cost: " + basePrice + " THB");
        System.out.println("Final Budget (After EarlyBird Discount): " + finalPrice + " THB");
    }
}

class TripController{
    private TripModel model;
    private TripView view;

    public TripController(TripModel model, TripView view){
        this.model = model;
        this.view = view;
    }

    public void createTrip(String transportType){
        Transport transport = TransportFactory.createTransport(transportType);
        model.setTransport(transport);
    }
    
    public void showSummary(){

        String desc = model.getTripDecription();
        double base = model.getBasePrice();
        double finalPrice = model.getFinalPrice();
        view.printTripDetails(desc, base, finalPrice);
    }
    
}
public class TripPlaningSystems {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TripModel model = new TripModel();
        TripView view = new TripView();
        TripController controller = new TripController(model, view);
        
        System.out.println("Select Transport (Flight/Train/Bus): ");
        String type = sc.nextLine();
        controller.createTrip(type);

        Transport current = model.geTransport();

        System.out.println("Add Wifi: (y/n): ");
        if(sc.nextLine().equalsIgnoreCase("y")){
            current = new WifiDecorator(current);
        }

        System.out.println("Add Meal? (y/n): ");
        if(sc.nextLine().equalsIgnoreCase("y")){
            current = new MealDecorator(current);
        }

        model.setTransport(current);


        System.out.println("Select Discount (None/Early/Student/Flash): ");
        String discount = sc.nextLine();
        if(discount.equalsIgnoreCase("Early")){
            model.setDiscountStrategy(new EarlyBirdStrategy());
        }
        else if(discount.equalsIgnoreCase("Student")){
            model.setDiscountStrategy(new StudentDiscountStrategy());
        }
        else{
            model.setDiscountStrategy(new FlashSaleStrategy());
        }


        controller.showSummary();
        sc.close();
    }
}
