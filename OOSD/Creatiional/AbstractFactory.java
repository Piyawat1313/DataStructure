package Creatiional;

// AbstractFactory
interface Button {
    void render();
}

interface Checkbox {
    void check();
}

class WinButton implements Button {
    public void render() {
        System.out.println("Windows Button");
    }
}

class WinCheckbox implements Checkbox {
    public void check() {
        System.out.println("windows Checkbox");
    }
}

interface GUIFactory {
    Button createButton();

    Checkbox createCheckbox();
}

class WindowsFactory implements GUIFactory {
    public Button createButton() {
        return new WinButton();
    }

    public Checkbox createCheckbox() {
        return new WinCheckbox();
    }
}

public class AbstractFactory {
    public static void main(String[] args) {
        System.out.println("============================= Adstract Factory Pattern =================================");
        // สร้างโรงงานก่อน
        GUIFactory factory = new WindowsFactory();

        //ให้โรงงานสร้าง Object ให้
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();

        // เรียกใช้
        button.render();
        checkbox.check();
    }
}
