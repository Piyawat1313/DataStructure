public class powxn {
    public double myPow(double x, int n) {
        double result = Math.pow(x, n); 
        return result;
    }


    public static void main(String[] args) {
        powxn p = new powxn();
        System.out.println(p.myPow(2.00000, -2));
    }
}
