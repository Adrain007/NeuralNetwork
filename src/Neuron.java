public class Neuron {
    private double [] inputs;
    private double [] weights;
    public Neuron(double[] inputs,double[] weights){
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

    public double[] getInputs() {
        return inputs;
    }

    public double[] getWeights() {
        return weights;
    }

    public double Output(double[] inputs,double[] weights){
        return Activator(inputs,weights);
    }
}
