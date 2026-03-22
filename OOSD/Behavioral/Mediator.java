package OOSD.Behavioral;

import java.util.ArrayList;

interface Mediator1{
    void send(String msg, User from);
}
class ChatRoom implements Mediator1{
        ArrayList<User> users = new ArrayList<>();
    void join(User u){
        users.add(u);
    }
    public void send(String msg, User from){
        users.stream().filter(u -> u != from).forEach(u -> u.receive(from.name + ": " + msg));
    }
}

class User{
    String name;
    Mediator1 m;
    User(String n, Mediator1 m){
        this.name = n;
        this.m = m;
    }
    void say(String msg){
        m.send(msg, this);
    }
    void receive(String msg){
        System.out.println(name + "<- " + msg);
    }
}

public class Mediator {
    public static void main(String[] args) {
        ChatRoom room = new ChatRoom();
        User u1 = new User("B1", room);
        User u2 = new User("B2", room);
        User u3 = new User("B3", room);

        room.join(u1);
        room.join(u2);
        room.join(u3);

        u1.say("Hello");
        room.send("kuay ri i sus everyone", u3);
        
    }
}
