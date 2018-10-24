import java.io.Serializable;

class Neuron implements Serializable {
    private double [] inputs;
    private double [] weights;

    Neuron(double[] inputs, double[] weights){
        this.inputs = inputs;
        this.weights = weights;
    }

    private double Activator(double[] i, double[] w){
        double sum = 0;
        for(int j = 0;j<i.length;j++){
            sum+=i[j]*w[j];
        }
        return Math.pow(1+Math.exp(-sum),-1);
    }

    double[] getInputs() {
        return this.inputs;
    }

    double[] getWeights() {
        return this.weights;
    }

    double getWeight(int index) {
        return this.weights[index];
    }

    void setWeights(double[] weights) {
        this.weights = weights;
    }
    void setInputs(double[] inputs) {
        this.inputs = inputs;
    }

    double Output(double[]inputs,double[]weights){
        return Activator(inputs,weights);
    }
    private double derivative(){
        return Output(inputs,weights)*(1-Output(inputs,weights));
    }

    double errorOutNeuron(double f, double goal){
        return f*(1-f)*(goal-f);
    }

    double errorHiddenNeuron(double[] weight, double[] er){
        double sum = 0;
        for(int j = 0;j<weight.length;j++){
            sum+=weight[j]*er[j];
        }
        return derivative()*sum;
    }
}
