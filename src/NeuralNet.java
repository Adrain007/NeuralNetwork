import java.io.*;
import java.util.ArrayList;

class NeuralNet implements Serializable {
    double [] inputLayer;
    private double [] hiddenLayerOut;
    private double [] outputLayerOut;
    private double [] hiddenLayerErr;
    private double [] outputLayerErr;
    private ArrayList<Neuron> hiddenLayer;
    private ArrayList<Neuron> outputLayer;
    double [] [] patterns = {
            {1,1,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,1,1},
            {0,0,0,0,1,1,0,0,1,1,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1},
            {1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1},
            {1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1},
            {1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0,1,1},
            {1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1},
            {1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1}
    };
    private double [][] answers = {
            {0,0,0,0},
            {0,0,0,1},
            {0,0,1,0},
            {0,0,1,1},
            {0,1,0,0},
            {0,1,0,1},
            {0,1,1,0},
            {0,1,1,1},
            {1,0,0,0},
            {1,0,0,1}
    };

    NeuralNet(){
        inputLayer = new double[patterns[0].length];
        hiddenLayerOut = new double[30];
        outputLayerOut = new double[answers[0].length];
        outputLayerErr = new double[answers[0].length];
        hiddenLayerErr = new double[30];
    }

    void initInputLayer(String input){
        char[] buf = input.toCharArray();
        int[] mas = new int[inputLayer.length];
        int index = 0;
        for (char aBuf : buf) {
            if (aBuf == '0' || aBuf == '1') {
                mas[index] = aBuf - 48;
                index++;
            }
        }
        for(int i = 0; i < mas.length; i++){
            inputLayer[i] = mas[i];
        }
    }

    private double[] initWeight(int numOfEl){
        double [] weight = new double[numOfEl];
        for(int i = 0; i < weight.length; i++){
            weight [i] = Math.random()*(-2)+1;
        }
        return weight;
    }

    void initHidden(double[] input){
        hiddenLayer = new ArrayList<>();
        for(int i = 0; i<30; i++) {
            hiddenLayer.add(new Neuron(input, initWeight(inputLayer.length)));
        }
    }
    void initOutputLayer(){
        outputLayer = new ArrayList<>();
        for(int i = 0; i<4; i++) {
            outputLayer.add(new Neuron(hiddenLayerOut, initWeight(hiddenLayerOut.length)));
        }
    }
    void counthiddenLayerOut(){
        int index = 0;
        for(Neuron neuron: hiddenLayer){
            hiddenLayerOut[index] = neuron.Output(neuron.getInputs(),neuron.getWeights());
            //System.out.println("h: "+hiddenLayerOut[index]);
            index++;
        }
    }
    private void counOutput(){
        int index = 0;
        for(Neuron neuron: outputLayer){
            outputLayerOut[index] = neuron.Output(neuron.getInputs(),neuron.getWeights());
            //System.out.println("o: "+outputLayerOut[index]);
            index++;
        }
    }
    
    private double ERROR(double[] answer){
        double sum = 0;
        for(int i = 0; i < answer.length; i++){
            sum+=Math.pow(answer[i]-outputLayerOut[i],2);
        }
        return 0.5*sum;
    }

    private double[] getOutWeight(int index){
        double [] weight = new double[answers[0].length];
        int j = 0;
        for (Neuron neuron: outputLayer){
            weight[j] = neuron.getWeight(index);
            j++;
        }
        return weight;
    }

    private void setHiddenLayerInputs(double[] hiddenLayerInputs) {
        for (Neuron hiddenNeuron: hiddenLayer){
            hiddenNeuron.setInputs(hiddenLayerInputs);
        }
    }

    private void setOutputLayerInputs() {
        for (Neuron outputNeuron: outputLayer){
            outputNeuron.setInputs(hiddenLayerOut);
        }
    }

    void study(){
        //int n = 0;
        do {
            for (int numOfPatterns = 0; numOfPatterns < 10; numOfPatterns++) {
                setHiddenLayerInputs(patterns[numOfPatterns]);
                counthiddenLayerOut();
                setOutputLayerInputs();
                counOutput();
                do {
                    for (int i = 0; i < outputLayer.size(); i++) {
                        Neuron neuron = outputLayer.get(i);
                        outputLayerErr[i] = neuron.errorOutNeuron(neuron.Output(neuron.getInputs(), neuron.getWeights()), answers[numOfPatterns][i]);
                        //System.out.println("o -- " + outputLayerErr[i]);
                    }
                    for (int i = 0; i < hiddenLayer.size(); i++) {
                        Neuron neuron = hiddenLayer.get(i);
                        hiddenLayerErr[i] = neuron.errorHiddenNeuron(getOutWeight(i), outputLayerErr);
                        //System.out.println("h -- " + hiddenLayerErr[i]);
                    }

                    for (int i = 0; i < outputLayer.size(); i++) {
                        Neuron neuron = outputLayer.get(i);
                        double[] outputWeight = neuron.getWeights();
                        double[] newWeight = new double[outputWeight.length];
                        for (int j = 0; j < outputWeight.length; j++) {
                            Neuron hiddenNeuron = hiddenLayer.get(j);
                            newWeight[j] = outputWeight[j] + 0.5 * outputLayerErr[i] * hiddenNeuron.Output(hiddenNeuron.getInputs(), hiddenNeuron.getWeights());
                        }
                        neuron.setWeights(newWeight);
                    }
                    for (int i = 0; i < hiddenLayer.size(); i++) {
                        Neuron neuron = hiddenLayer.get(i);
                        double[] hiddenWeight = neuron.getWeights();
                        double[] newWeight = new double[hiddenWeight.length];
                        double[] inputNeuron = patterns[numOfPatterns];
                        for (int j = 0; j < hiddenWeight.length; j++) {
                            newWeight[j] = hiddenWeight[j] + 0.5 * hiddenLayerErr[i] * inputNeuron[j];
                        }
                        neuron.setWeights(newWeight);
                    }
                    counthiddenLayerOut();
                    setOutputLayerInputs();
                    counOutput();
                } while (ERROR(answers[numOfPatterns]) > 0.00001);
            }
            //n++;
            setHiddenLayerInputs(patterns[8]);
            counthiddenLayerOut();
            setOutputLayerInputs();
            counOutput();
        }while (ERROR(answers[8])>0.00001);

    }

    void check(double[] input){
        for (Neuron hiddenNeuron: hiddenLayer){
            hiddenNeuron.setInputs(input);
        }
        counthiddenLayerOut();
        for (Neuron outputNeuron: outputLayer){
            outputNeuron.setInputs(hiddenLayerOut);
        }
        counOutput();
        StringBuilder out = new StringBuilder();
        for (double outputNeuron: outputLayerOut){
            System.out.println("out: "+outputNeuron);
            if(outputNeuron>0.6){
                out.append('1');
            }else{
                out.append('0');
            }
        }
        System.out.println(Integer.parseInt(out.toString(),2));
    }
}
