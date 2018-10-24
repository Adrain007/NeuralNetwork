import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args){
        NeuralNet net = new NeuralNet();
        String inputLine = null;
        try {
            inputLine = new String(Files.readAllBytes(Paths.get("C:/Users/Адриан/IdeaProjects/NeuralNetwork/src", "source.txt")),"UTF-8");
        } catch (IOException e){
            e.printStackTrace();
        }
        /*net.initHidden(net.patterns[9]);
        net.counthiddenLayerOut();
        net.initOutputLayer();
        net.study();
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Net.ser"));
            os.writeObject(net);
        }catch (Exception e){
            e.printStackTrace();
        }*/
        try{
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("Net.ser"));
            net =(NeuralNet) is.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        net.initInputLayer(inputLine);
        net.check(net.inputLayer);
    }
}
