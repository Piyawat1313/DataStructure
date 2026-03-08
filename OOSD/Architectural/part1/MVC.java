package OOSD.Architectural.part1;
class UserModel{
    String name, email;
    UserModel(String n, String e){
        name = n;
        email = e;
    }
    void setName(String n){
        name = n;
    }
}

class UserView{
    void show(String name, String email){
        System.out.println("Name: " + name + "\nEmail: " + email);
    }
}

class UserController{
    UserModel m;
    UserView v;
    UserController(UserModel m, UserView v){
        this.m = m;
        this.v = v;
    }
    void show(){
        v.show(m.name, m.email);
    }
    void update(String n){
        m.setName(n);
    }
}
public class MVC {
    public static void main(String[] args) {
       UserModel m =  new UserModel("Piyawat", "Biw@gmail.com");
       UserView v =  new UserView();
       UserController controller = new UserController(m, v);
       controller.show();
       controller.update("Biw");
       controller.show();
    }
}
