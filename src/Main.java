import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args){
        double start = System.nanoTime();
        NeuralNet net = new NeuralNet();
        String inputLine = null;
        try{
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("Net.ser"));
            net =(NeuralNet) is.readObject();
        }catch (Exception e){
            net.initHidden(net.patterns[9]);
            net.counthiddenLayerOut();
            net.initOutputLayer();
            net.study();
            try {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Net.ser"));
                os.writeObject(net);
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        try{
            inputLine = new String(Files.readAllBytes(Paths.get(Main.class.getResource("source.txt").toURI())),"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        net.initInputLayer(inputLine);
        net.check(net.inputLayer);
        double end = System.nanoTime();
        System.out.println("Execution time: "+(end-start)*Math.pow(10,6)+" ms");
    }
}
