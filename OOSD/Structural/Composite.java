package OOSD.Structural;

import java.util.ArrayList;

// tree
interface FileItem{
    void show(String indent);
    int getSize();
}
// left 1
class File implements FileItem{
    String name;
    int size;
    File(String n, int s){
        this.name = n;
        this.size = s;
    }
    public void show(String i){
        System.out.println(i + "- " + name);
    }
    public int getSize(){
        return this.size;
    }
}
// left 2
class Folder implements FileItem{
    String name;
    ArrayList<FileItem>items = new ArrayList<>();
    
    void add(FileItem f){
        items.add(f);
    }
    public void show(String i){
        System.out.println(i + "["+ name +"]");
        for(FileItem ii : items){
            ii.show(i + " ");
        }
    }
    public int getSize(){
        return items.stream().mapToInt(FileItem::getSize).sum();
    }
}
public class Composite {
    public static void main(String[] args) {
        File f1 = new File("java", 20);
        File f2 = new File("cat.jpg", 100);
        File f3 = new File("OOSD.pdf", 250);

        Folder project = new Folder();
        project.name = "Project";

        Folder sub = new Folder();

        sub.add(f3);
        project.add(f1);
        project.add(sub);
        project.add(f2);

        System.out.println(" --- list File --- ");
        project.show("");

        System.out.println("--- sum Size ---");
        System.out.println("Total Size: " + project.getSize() + "KB");
    }
}
