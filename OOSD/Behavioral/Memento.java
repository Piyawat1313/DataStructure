package OOSD.Behavioral;

import java.util.ArrayDeque;

class Snapshot{
    final String text;
    Snapshot(String t){
        text = t;
    }
}
class Editor{
    StringBuilder text = new StringBuilder();
    void type(String s){
        text.append(s);
    }
    Snapshot save(){
        return new Snapshot(text.toString());
    }
    void restore(Snapshot s){
        text = new StringBuilder(s.text);
    }
    String getText(){
        return text.toString();
    }
}
public class Memento {
    public static void main(String[] args) {
        Editor e = new Editor();
        ArrayDeque<Snapshot>history = new ArrayDeque<>();
        e.type("Hello ");

        history.push(e.save());
        
        e.type("World!!! ");
        history.push(e.save());
        e.type("Kuay");
        System.out.println(e.getText());
        e.restore(history.pop());
        System.out.println(e.getText());
    }
}
