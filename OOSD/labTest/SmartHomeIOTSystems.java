package labTest;

import java.util.ArrayList;

interface SmartDevice{
    String getName();
    double getBasePower();
}

class SmartLight implements SmartDevice{

    @Override
    public String getName() {
        return "SmartLight";
    }

    @Override
    public double getBasePower() {
        return 10.0;
    }
}

class SmartAC implements SmartDevice{
    
    @Override
    public String getName() {
        return "SmartAC";
    }

    @Override
    public double getBasePower() {
        return 1500.0;
    }
}

class SmartSpeaker implements SmartDevice{

    @Override
    public String getName() {
        return "SmartSpeaker";
    }

    @Override
    public double getBasePower() {
        return 25.0;
    }
}

class FactorySmart{
    public static SmartDevice create(String type){
        if(type.equals("SmartLight")) return new SmartLight();
        
        else if(type.equals("SmartAC")) return new SmartAC();
        
        else if(type.equals("SmartSpeaker")) return new SmartSpeaker();

        throw new IllegalArgumentException("Unknow");
    }
}

abstract class DecoratorSmartDevice implements SmartDevice{
    private SmartDevice smartDevice;

    public DecoratorSmartDevice(SmartDevice smartDevice){
        this.smartDevice = smartDevice;
    }

    @Override
    public String getName() {
        return smartDevice.getName();
    }

    @Override
    public double getBasePower() {
        return smartDevice.getBasePower();
    }
}

class MotionSensor extends DecoratorSmartDevice{

    public MotionSensor(SmartDevice smartDevice){
        super(smartDevice);
    }

    @Override
    public String getName() {
        return super.getName() + " + Motion Sensor";
    }

    @Override
    public double getBasePower() {
        return super.getBasePower() + 5.0;
    }
}

class VoiceControl extends DecoratorSmartDevice{

    public VoiceControl(SmartDevice smartDevice){
        super(smartDevice);
    }

    @Override
    public String getName() {
        return super.getName() + " + Voice Control";
    }

    @Override
    public double getBasePower() {
        return super.getBasePower() + 10.0;
    }
}

class AutoTimer extends DecoratorSmartDevice{

    public AutoTimer(SmartDevice smartDevice){
        super(smartDevice);
    }

    @Override
    public String getName() {
        return super.getName() + " + Auto Timer";
    }

    @Override
    public double getBasePower() {
        return super.getBasePower() + 2.0;
    }
}

interface PowerModeStrategy{
    double calculateActualPower(double totalBasePower);
    String getModeName();
}

class NormalMode implements PowerModeStrategy{

    @Override
    public String getModeName() {
        return "Normal mode";
    }

    @Override
    public double calculateActualPower(double totalBasePower) {
        return totalBasePower * 1.0;
    }
}

class EcoMode implements PowerModeStrategy{

    @Override
    public String getModeName() {
        return "Eco Mode";
    }

    @Override
    public double calculateActualPower(double totalBasePower) {
        return totalBasePower * 0.7;
    }
}

class BoostMode implements PowerModeStrategy{

    @Override
    public String getModeName() {
        return "Boost Mode";
    }

    @Override
    public double calculateActualPower(double totalBasePower) {
        return totalBasePower * 1.2;
    }
}

class ContextPowerMode{
    private PowerModeStrategy powerModeStrategy;

    public void setPowerMode(PowerModeStrategy powerModeStrategy){
        this.powerModeStrategy = powerModeStrategy;
    }

    public double calculateActualPowerConText(double totalBasePower){
        return this.powerModeStrategy.calculateActualPower(totalBasePower);
    }

    public PowerModeStrategy getPowerModeStrategy(){
        return powerModeStrategy;
    }
}


class Room{
    private ArrayList<SmartDevice> smartDevices;
    private PowerModeStrategy powerMode;

    public Room(){
        smartDevices = new ArrayList<>();
    }

    public void addDevice(SmartDevice device){
        smartDevices.add(device);
    }

    public double getTotalBasePower(){
        double basePower = 0;
       
        for (int i = 0; i < smartDevices.size(); i++) {
            basePower += smartDevices.get(i).getBasePower();
        }

        return basePower;
    }

    public double getFinalPowerConsumption(){
        return powerMode.calculateActualPower(getTotalBasePower());
    }

    public ArrayList<SmartDevice> getSmartDevices(){
        return smartDevices;
    }

    public PowerModeStrategy getPowerModeStrategy(){
        return powerMode;
    }

    public void setRoomMode(PowerModeStrategy powerModeStrategy){
        this.powerMode = powerModeStrategy;
    }
}

class DashboardView{

    public void displayDevices(Room room){
        System.out.println("===== 🏠 Smart Room Dashboard =====");
        
        for (int i = 0; i < room.getSmartDevices().size(); i++) {
            System.out.println((i + 1) + "." + room.getSmartDevices().get(i).getName() + "(" + room.getSmartDevices().get(i).getBasePower() + " W)");
        }

        System.out.println("--------------------------------------");
        System.out.println("Total basic energy: " + room.getTotalBasePower());
        System.out.println("Curren Mode: " + room.getPowerModeStrategy().getModeName());
        System.out.println("Actual power consumption rate: " + room.getFinalPowerConsumption());
    }

    public void displayPowerAlert(Room room){
        System.out.println("===== System Status =====");
        System.out.println("⚠️ WARNING: High Power Consumption!");
        System.out.println("=========================");
    }
}

class SmartHomeController{
    private Room model;
    private DashboardView view;

    public SmartHomeController(){
        this.model = new Room();
        this.view = new DashboardView();
    }

    public void installDevice(String type, String[] addons){
        SmartDevice smartDevice = FactorySmart.create(type);
        SmartDevice smartDevice2 = FactorySmart.create(type);
        SmartDevice smartDevice3 = FactorySmart.create(type);

        for (int i = 0; i < addons.length; i++) {
            if(addons[i].equals("MotionSensor")){
                smartDevice = new MotionSensor(smartDevice);
                smartDevice2 = new MotionSensor(smartDevice2);
                smartDevice3 = new MotionSensor(smartDevice3);
            }
            else if(addons[i].equals("VoiceControl")){
                smartDevice = new VoiceControl(smartDevice);
                smartDevice2 = new VoiceControl(smartDevice2);
                smartDevice3 = new VoiceControl(smartDevice3);
            }
            else if(addons[i].equals("AutoTimer")){
                smartDevice = new AutoTimer(smartDevice);
                smartDevice2 = new AutoTimer(smartDevice2);
                smartDevice3 = new AutoTimer(smartDevice3);
            }
        }
        model.addDevice(smartDevice3);
    }

    public void setRoomMode(PowerModeStrategy mode){
        this.model.setRoomMode(mode);
    }

    public void showDashboard(){
        view.displayDevices(model);
        view.displayPowerAlert(model);
    }
}

public class SmartHomeIOTSystems {
    public static void main(String[] args) {
        SmartDevice s = FactorySmart.create("SmartLight");
        SmartDevice s2 = FactorySmart.create("SmartAC");
        SmartDevice s3 = FactorySmart.create("SmartSpeaker");

        System.out.println("Device: " + s.getName() + " | " + "Base Power: " + s.getBasePower() + " W");
        System.out.println("Device: " + s2.getName() + " | " + "Base Power: " + s2.getBasePower() + " W");
        System.out.println("Device: " + s3.getName() + " | " + "Base Power: " + s3.getBasePower() + " W");
        System.out.println();


        s = new MotionSensor(s);
        s = new AutoTimer(s);
        s2 = new VoiceControl(s2);

        System.out.println("Installed: " + s.getName() + " | " + "Total Power: " + s.getBasePower() + " W");
        System.out.println("Installed: " + s2.getName() + " | " + "Total Power: " + s2.getBasePower() + " W");
        System.out.println();


        ContextPowerMode contextPowerMode = new ContextPowerMode();
        SmartDevice s4 = FactorySmart.create("SmartAC");
        contextPowerMode.setPowerMode(new EcoMode());
        
        System.out.println("Device: " + s4.getName() + " | " + "Basic power consumption: " + s4.getBasePower() + " W");
        System.out.println("Used in " + contextPowerMode.getPowerModeStrategy().getModeName() + ": " + "It actually consumes a lot of electricity: " + contextPowerMode.calculateActualPowerConText(s4.getBasePower()));
        System.out.println();

        SmartDevice s5 = FactorySmart.create("SmartSpeaker");
        s5 = new VoiceControl(s5);
        contextPowerMode.setPowerMode(new BoostMode());

        System.out.println("Device: " + s5.getName() + " | " + "Basic power consumption: " + s5.getBasePower() + " W");
        System.out.println("Used in " + contextPowerMode.getPowerModeStrategy().getModeName() + ": " + "It actually consumes a lot of electricity: " + contextPowerMode.calculateActualPowerConText(s5.getBasePower()));
        System.out.println();



        SmartHomeController smartHomeController = new SmartHomeController();
        smartHomeController.installDevice("SmartLight", new String[]{"MotionSensor"});
        smartHomeController.installDevice("SmartAC", new String[]{"AutoTimer"});
        smartHomeController.installDevice("SmartAC", new String[]{"VoiceControl"});
        smartHomeController.setRoomMode(new EcoMode());
        smartHomeController.showDashboard();
    }
}
