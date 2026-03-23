package labTest;

import java.util.ArrayList;

// Factory Pattern
interface Enemy{
    String getDescription();
    double getBasePower();
}

class Cultist implements Enemy{

    @Override
    public String getDescription() {
        return "Cultist";
    }

    @Override
    public double getBasePower() {
        return 20.0;
    }
}

class Warith implements Enemy{

    @Override
    public String getDescription() {
        return "Warith";
    }

    @Override
    public double getBasePower() {
        return 45.0;
    }
}

class Abomination implements Enemy{

    @Override
    public String getDescription() {
        return "Abomination";
    }

    @Override
    public double getBasePower() {
        return 80.0;
    }
}

class Factory {

    public static Enemy create(String enemy){
        if(enemy.equals("Cultist")) return new Cultist();
        
        else if(enemy.equals("Wraith")) return new Warith();
        
        else if(enemy.equals("Abomination")) return new Abomination();

        throw new IllegalArgumentException("Unknow");
    }
}

//Decorator Pattern
class DecoratorEnemy implements Enemy{
    private Enemy enemy;

    public DecoratorEnemy(Enemy enemy){
        this.enemy = enemy;
    }

    @Override
    public String getDescription() {
        return enemy.getDescription();
    }

    @Override
    public double getBasePower() {
        return enemy.getBasePower();
    }
}

class Armored extends DecoratorEnemy{
    
    public Armored(Enemy enemy){
        super(enemy);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + "+ Armored";
    }

    @Override
    public double getBasePower() {
        return super.getBasePower() + 30.0;
    }
}

class Frenzied extends DecoratorEnemy{

    public Frenzied(Enemy enemy){
        super(enemy);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + "+ Frenzied";
    }

    @Override
    public double getBasePower() {
        return super.getBasePower() + 50.0;
    }
}

class ShadowInfused extends DecoratorEnemy{

    public ShadowInfused(Enemy enemy){
        super(enemy);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + "+ Shadow Infused";
    }

    @Override
    public double getBasePower() {
        return super.getBasePower() + 40.0;
    }
}

interface EnvironmentTactic{
    double calculateThreat(double totalWavePower);
    String getEnvironmentName();
}

class NormalEnvironment implements EnvironmentTactic{

    @Override
    public double calculateThreat(double totalWavePower) {
        return totalWavePower;
    }

    @Override
    public String getEnvironmentName() {
        return "Normal";
    }
}

class BloodMoon implements EnvironmentTactic{

    @Override
    public double calculateThreat(double totalWavePower) {
        return totalWavePower * 1.5;
    }

    @Override
    public String getEnvironmentName() {
        return "BloodMoon";
    }
}

class NightmareRealm implements EnvironmentTactic{

    @Override
    public double calculateThreat(double totalWavePower) {
        if(totalWavePower < 150){
            return totalWavePower + 50.0;
        }
        return totalWavePower + 100.0;
    }

    @Override
    public String getEnvironmentName() {
        return "Nightmare Realm";
    }
}

//set สภาพแวดล้อมตาม Object ที่ผู้ใช้เลือก
class Context{
    private EnvironmentTactic environmentTactic;

    public void setEnvironment(EnvironmentTactic environmentTactic){
        this.environmentTactic = environmentTactic;
    }

    public EnvironmentTactic getEnvironmentTactic(){
        return environmentTactic;
    }

    public double calculateThreatPower(double totalWavePower){
        if(environmentTactic == null) return 0;

        return environmentTactic.calculateThreat(totalWavePower);
    }

    public String getEnvironmentName(){
        if(environmentTactic == null) return "Unknow";

        return environmentTactic.getEnvironmentName();
    }
}

//model คำนวณจำนวนที่ผู้ใช้สั่ง
class EnemyGroup{
    private Enemy enemy;
    private int quantity;

    public EnemyGroup(Enemy enemy, int quantity){
        this.enemy = enemy;
        this.quantity = quantity;
    }

    public double getGroupPower(){
        return enemy.getBasePower() * quantity;
    }

    public Enemy getEnemy(){
        return enemy;
    }

    public int getQuantity(){
        return quantity;
    }
}

// model คำนวณราคาทั่งหมด และ เพิ่มข้อมูลลงตะกร้า
class Wave{
    private ArrayList<EnemyGroup> enemyGroups;
    private EnvironmentTactic environment;

    public Wave(){
        this.enemyGroups = new ArrayList<>();
    }

    public void addPower(Enemy enemy, int quantity){
        enemyGroups.add(new EnemyGroup(enemy, quantity));
    }

    public double getBaseWavePower(){
        double base = 0;
        for (int i = 0; i < enemyGroups.size(); i++) {
            base += enemyGroups.get(i).getGroupPower();
        }
        return base;
    }

    public double getFinalThreatLevel(){
        return environment.calculateThreat(getBaseWavePower());
    }

    public ArrayList<EnemyGroup> getEnemyGroups(){
        return enemyGroups;
    }

    public EnvironmentTactic getEnvironmentTactic(){
        return environment;
    }

    public void setEnvironment(EnvironmentTactic environmentTactic){
        this.environment = environmentTactic;
    }
}

//View แสดงผลข้อมูลทางหน้าจอทั้งหมด
class WaveView{

    public void displatWaveDetails(Wave wave){
        System.out.println("===== Systems check Wave enemy =====");
        for (int i = 0; i < wave.getEnemyGroups().size(); i++) {
            System.out.println((i + 1) + ". " + wave.getEnemyGroups().get(i).getEnemy().getDescription() + " x" + wave.getEnemyGroups().get(i).getQuantity() + " | " + wave.getEnemyGroups().get(i).getGroupPower());
        }
        System.out.println("--------------------------------------");
        System.out.println("Total power base: " + wave.getBaseWavePower());
        System.out.println("Environment: " + wave.getEnvironmentTactic().getEnvironmentName());
        System.out.println("Level dangerous: " + wave.getFinalThreatLevel());
    }

    public void displayWarning(Wave wave){
        System.out.println("===== SYSTEM WARNING =====");
        System.out.println("The enemy is invading! Level dangerous: " + wave.getFinalThreatLevel());
        System.out.println("Environment: " + wave.getEnvironmentTactic().getEnvironmentName());
        System.out.println("Prepare for survival!");
        System.out.println("==========================");
    }
}

//controller ดึงข้อมูลจาก model และ view มาแสดงผล
class GameDirector{
    private Wave model;
    private WaveView view;
    
    public GameDirector(){
        this.model = new Wave();
        this.view = new WaveView();
    }

    public void spawnEnemy(String type, String[] mutations, int qty){
        Enemy enemy = Factory.create(type);
        for (int i = 0; i < mutations.length; i++) {
            if(mutations[i].equals("Armored")){
                enemy = new Armored(enemy);
            }
            else if(mutations[i].equals("Frenzied")){
                enemy = new Frenzied(enemy);
            }
            else if(mutations[i].equals("ShadowInfused")){
                enemy = new ShadowInfused(enemy);
            }
        }

        model.addPower(enemy, qty); 
    }

    public void setEnvironment(EnvironmentTactic environmentTactic){
        model.setEnvironment(environmentTactic);;
    }

    public void triggerWave(){
        view.displatWaveDetails(model);
        view.displayWarning(model);
    }
}

public class SurvivalHorrorGameEnemySystem {
    public static void main(String[] args) {
        Enemy e = Factory.create("Cultist");
        Enemy e2 = Factory.create("Wraith");
        Enemy e3 = Factory.create("Abomination");
        System.out.println("Enemy: " + e.getDescription() + " | Power: " + e.getBasePower());
        System.out.println("Enemy: " + e2.getDescription() + " | Power: " + e2.getBasePower());
        System.out.println("Enemy: " + e3.getDescription() + " | Power: " + e3.getBasePower());
        System.out.println();


        e2 = new ShadowInfused(e2);
        e3 = new Armored(e3);
        e3 = new Frenzied(e3);
        System.out.println("Enemy: " + e2.getDescription() + " | Power: " + e2.getBasePower());
        System.out.println("Enemy: " + e3.getDescription() + " | Power: " + e3.getBasePower());
        System.out.println();



        Context context = new Context();
        Enemy e4 = Factory.create("Wraith");
        context.setEnvironment(new NormalEnvironment());
        System.out.println("enemy: " + e4.getDescription() + " | " + e4.getBasePower());
        System.out.println("Environment: " + context.getEnvironmentName() + " level dangerous: " + context.calculateThreatPower(e4.getBasePower()));
        System.out.println();


        GameDirector gameDirector = new GameDirector();
        gameDirector.spawnEnemy("Cultist", new String[]{"ShadowInfused"}, 3);
        gameDirector.spawnEnemy("Abomination", new String[]{"Armored"}, 1);
        gameDirector.setEnvironment(new BloodMoon());
        gameDirector.triggerWave();
    }
}
