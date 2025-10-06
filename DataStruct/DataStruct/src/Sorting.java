import java.util.Random;

class SArr{
    int number[];
    SArr(int n){
        number = new int[n];
    }
    public void randVal(int m){
        Random random = new Random();
        for (int i = 0; i < number.length; i++) {
            number[i] = random.nextInt(m - (-m) + 1) + (-m);
            System.out.print(number[i] + " ");
        }
        System.out.println();
    }
    public void insertionSort(){
        int temp,j;
        for (int i = 1; i < number.length; i++) {
            temp = number[i];
            for ( j = i; j > 0 && number[j - 1] > temp; j--) {
                number[j] = number[j - 1];
            }
            number[j] = temp;
        }
        
        for (int i = 0; i < number.length; i++) {
            System.out.print(number[i] + " ");
        }
        System.out.println();
    }
    public void selectionSort(){
        int minIndex, temp;
        for (int i = 0; i < number.length - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < number.length; j++) {
                if(number[j] < number[minIndex]){
                    minIndex = j;
                }
            }
            if(minIndex != i){
                temp = number[i];
                number[i] = number[minIndex];
                number[minIndex] = temp;
            }
        } 
        for (int i = 0; i < number.length; i++) {
            System.out.print(number[i] + " ");
        }
        System.out.println();
    }
    public void bubleSort(){
        boolean swapped = true;
        int j = 0;
        int temp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < number.length - j; i++) {
                if(number[i] > number[i + 1]){
                    temp = number[i];
                    number[i] = number[i + 1];
                    number[i + 1] = temp;
                    swapped = true;
                }
            }
        }
        for (int i = 0; i < number.length; i++) {
            System.out.print(number[i] + " ");
        }
        System.out.println();
    }
    public void shellSort(){
        int j;
        for (int gap = number.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < number.length; i++) {
                int temp = number[i];
                for ( j = i; j >= gap && temp < (number[j - gap]); j -= gap) {
                    number[j] = number[j - gap];
                }
                number[j] = temp;
            }
        }
        for (int i = 0; i < number.length; i++) {
            System.out.print(number[i] + " ");
        }
        System.out.println();
    }
    public void printVal(){
        System.out.println("-----------------------");
        System.out.println("insertionSort: ");
        insertionSort();
        System.out.println("-----------------------");
        System.out.println("selectionSort: ");
        selectionSort();
        System.out.println("-----------------------");
        System.out.println("BubleSort: ");
        bubleSort();
        System.out.println("-----------------------");
        System.out.println("ShellSort: ");
        shellSort();
    }
}
public class Sorting {
    public static void main(String[] args) {
        Random random = new Random();
        int n = random.nextInt(11) + 10;
        System.out.println("Random " + n + " numbers");
        int m = random.nextInt(11) + 50;
        SArr sarr = new SArr(n);
        sarr.randVal(m);
        sarr.printVal();
    }
}
