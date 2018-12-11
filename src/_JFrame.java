import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;


public class _JFrame implements ActionListener {
    private JTextArea textArea;
    private JTextArea textAreaFile;

    void go() {
        JFrame frame = new JFrame("NeuralNetwork 0.0.1");
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JButton solve = new JButton("Solve");
        JButton save = new JButton("Save ");
        JButton load = new JButton("Load ");
        solve.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
        textArea = new JTextArea(6,5);
        textAreaFile = new JTextArea(10, 5);
        textAreaFile.setLineWrap(true);
        textArea.setLineWrap(true);
        textAreaFile.setEditable(true);

        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        frame.getContentPane().add(BorderLayout.EAST, panel1);
        frame.getContentPane().add(BorderLayout.WEST, panel);

        panel1.add(solve);
        panel1.add(save);
        panel1.add(load);
        panel.add(textAreaFile);
        panel.add(textArea);
        frame.setSize(200, 320);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case ("Solve"):
                textArea.setText("Вероятность: \n\r");
                NeuralNet net;
                String inputLine = null;
                try {
                    ObjectInputStream is = new ObjectInputStream(new FileInputStream("Net.ser"));
                    net = (NeuralNet) is.readObject();
                } catch (Exception e1) {
                    net = new NeuralNet();
                    net.study();
                    try {
                        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Net.ser"));
                        os.writeObject(net);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                try {
                    inputLine = new String(Files.readAllBytes(Paths.get("source.txt")), "UTF-8");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                net.initInputLayer(inputLine);
                HashMap<Integer,String> map = net.check(net.inputLayer);
                for(int num: map.keySet()){
                    String s = Integer.toString(num)+" : "+map.get(num)+"%\n\r";
                    textArea.append(s);
                }
                break;
            case ("Save "):
                try {
                    FileWriter writer = new FileWriter("source.txt");
                    BufferedWriter bufWriter  = new BufferedWriter(writer);
                    bufWriter.write(textAreaFile.getText());
                    bufWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case ("Load "):
                textAreaFile.setText("");
                try {
                    Scanner scan = new Scanner(new File("source.txt"));
                    while (scan.hasNext()){
                        textAreaFile.append(scan.nextLine()+"\n");
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                break;
        }
    }
}