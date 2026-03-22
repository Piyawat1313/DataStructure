package OOSD.Architectural;

interface LoginView{
    String getUserName();
    String getPassword();
    void showError(String msg);
    void showSuccess(String msg);
}

class UserModel{
    public boolean verify(String u, String p){
       return "admin".equals(u) && "1234".equals(p);
    }
}
class LoginPresenter{
    LoginView view;
    UserModel model;
    LoginPresenter(LoginView view, UserModel model){
        this.view = view;
        this.model = model;
    }
    void onLogin(){
        String u = view.getUserName();
        String p = view.getPassword();
        if(model.verify(u,p)) view.showSuccess("Welcome");
        else view.showError("Wrong username or password");
    }
}
class LoginScreen implements LoginView {
    private String username;
    private String password;

    public void setInput(String u, String p) { this.username = u; this.password = p; }

    @Override public String getUserName() { return username; }
    @Override public String getPassword() { return password; }
    
    @Override 
    public void showError(String msg) { System.out.println("[ERROR]: " + msg); }
    
    @Override 
    public void showSuccess(String msg) { System.out.println("[SUCCESS]: " + msg); }
}
public class MVP {
    public static void main(String[] args) {
        UserModel model = new UserModel();
        LoginScreen view = new LoginScreen();
        LoginPresenter presenter = new LoginPresenter(view, model);

        view.setInput("admin", "1234");
        presenter.onLogin();
        System.out.println(view.getUserName() + " " + view.getPassword());

    }
}
