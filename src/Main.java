import java.io.IOException;
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
        net.initHidden(net.patterns[9]);
        net.counthiddenLayerOut();
        net.initOutputLayer();
        net.study();
        net.initInputLayer(inputLine);
        net.check(net.inputLayer);
    }
}
