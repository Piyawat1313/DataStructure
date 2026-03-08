package OOSD.Behavioral;

interface LightState{
    void handle(TrafficLight l);
}
class RedState implements LightState{
    public void handle(TrafficLight l){
        System.out.println("Red - Stop!");
        l.setState(new YellowState());
    }
}

class YellowState implements LightState{
    public void handle(TrafficLight l){
        System.out.println("YELLOW - 5 4 3 2 1...");
        l.setState(new GreenState());
    }
}
class GreenState implements LightState{
    public void handle(TrafficLight l){
        System.out.println("GREEN - Go!");
        l.setState(new YellowState());
    }
}

class TrafficLight{
    LightState state = new RedState();
    void setState(LightState s){
        state = s;
    }
    void change(){
        state.handle(this);
    }
}
public class State {
    public static void main(String[] args) {
        TrafficLight t = new TrafficLight();
        t.change();
        t.change();
        t.change();
    }
}
