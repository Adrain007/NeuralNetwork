public class Main {
    public static void main(String[] args){
        double start = System.nanoTime();
        _JFrame jFrame = new _JFrame();
        jFrame.go();
        double end = System.nanoTime();
        System.out.println("Execution time: "+(end-start)*Math.pow(10,6)+" ms");
    }
}
