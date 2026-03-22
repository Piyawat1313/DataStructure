package OOSD.Behavioral;
import java.util.ArrayDeque;

interface Commands{
    void excute(); 
    void undo();
}

class TextEditor{
    StringBuilder text = new StringBuilder();
    void insert(String s){
        text.append(s);
    }
    void delete(int n){
        text.delete(text.length() - n, text.length());
    }
}

class InsertCmd implements Commands{
    TextEditor e;
    String text;
    InsertCmd(TextEditor e, String t){
        this.e = e;
        this.text = t;
    }
    public void excute(){
        e.insert(text);
    }
    public void undo(){
        e.delete(text.length());
    }
}
public class Command {
    public static void main(String[] args) {
        ArrayDeque<Commands> history = new ArrayDeque<>();
        TextEditor t = new TextEditor();
        Commands cmds = new InsertCmd(t, "Hello");
        Commands cmds2 = new InsertCmd(t, " B2");
        cmds.excute();
        cmds2.excute();

        history.push(cmds);
        history.push(cmds2);

        System.out.println("Current: " + t.text);
        
        if(!history.isEmpty()) history.pop().undo();

        System.out.println("Back Undo: " + t.text);
    }
}
