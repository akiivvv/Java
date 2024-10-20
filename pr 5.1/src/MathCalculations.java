public class MathCalculations {
    public static void main(String[] args) {
        double a = 0.5;
        double b = 2.7;
        double c = 0.4;

        double numeratorX = Math.pow(a, 2) * c + Math.exp(-c) * Math.cos(b * c);
        double denominatorX = (b * c) - Math.exp(-c) * Math.sin(b * c) + 1;
        double x = numeratorX / denominatorX;

        double y = Math.exp(2 * c) * Math.log(a + c) - Math.pow(b, 3 * c) * Math.log(b - c);

        System.out.println("x = " + x);
        System.out.println("y = " + y);
    }
}
