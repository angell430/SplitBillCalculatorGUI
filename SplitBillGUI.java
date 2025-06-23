
/**
 * This is a GUI version of Split Bill Calculator.
 *
 * @ANGEL TAN KE QIN
 * @latest on 23/6/2025
 */

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SplitBillGUI {
    public static void main(String[] args) {
        MusicPlayer.playMusic("videoplayback.wav");//Play background music

        JFrame frame = new JFrame("Split Bill Calculator");//Create new frame
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 192, 203)); //Set background colour
        panel.setLayout(null);

        JLabel title = new JLabel("Choose a way to split the bill:");
        title.setBounds(120, 50, 300, 30);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(title);

        JButton evenButton = new JButton("1. Split Evenly");//Create button for split evenly
        evenButton.setBounds(150, 120, 200, 40);
        panel.add(evenButton);

        JButton indivButton = new JButton("2. Split by Individual Orders");//Create button for split by individual order
        indivButton.setBounds(150, 180, 200, 40);
        panel.add(indivButton);

        evenButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                playClickSound();
                splitEvenly(frame);
            }
        });

        indivButton.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent e) 
        {
           playClickSound();
           splitByIndividualOrder(frame);
        }
        });
        frame.add(panel);
        frame.setVisible(true);//Set visible
    }
    public static void splitEvenly(JFrame frame) {
        try 
        {
            String totalStr = JOptionPane.showInputDialog(frame, "Enter total amount (RM):");
            double total = Double.parseDouble(totalStr);

            String peopleStr = JOptionPane.showInputDialog(frame, "Enter number of people:");
            int people = Integer.parseInt(peopleStr);

            if (people <= 0) 
            {
                JOptionPane.showMessageDialog(frame, "Invalid number of people.");
                return;
            }
            double each = total / people;
            JOptionPane.showMessageDialog(frame, String.format("Each person pays: RM %.2f", each));
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    public static void splitByIndividualOrder(JFrame frame) {
        try 
        {
            String peopleStr = JOptionPane.showInputDialog(frame, "Enter number of people:");
            int numberOfPeople = Integer.parseInt(peopleStr);
            if (numberOfPeople <= 0) 
            {
                JOptionPane.showMessageDialog(frame, "Invalid number of people.");
                return;
            }

            String[] names = new String[numberOfPeople];
            double[] bills = new double[numberOfPeople];

            for (int i = 0; i < numberOfPeople; i++) //Create a loop to read input from user
            {
                names[i] = JOptionPane.showInputDialog(frame, "Name of person " + (i + 1) + ":");
                int qty = Integer.parseInt(JOptionPane.showInputDialog(frame, "How many items did " + names[i] + " order?"));
                double total = 0;
                for (int j = 0; j < qty; j++) 
                {
                    double price = Double.parseDouble(JOptionPane.showInputDialog(frame, "Price of item " + (j + 1) + " for " + names[i]));
                    total += price;
                }
                bills[i] = total;
            }

            int extra = JOptionPane.showConfirmDialog(frame, "Is there any extra charge (like tax/service)?", "Extra Charges", JOptionPane.YES_NO_OPTION);

            if (extra == JOptionPane.YES_OPTION)
            {
                double totalAmount = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter total bill including extra charges:"));
                double originalTotal = 0;
                for (double bill : bills) originalTotal += bill;

                if (totalAmount < originalTotal) 
                {
                    JOptionPane.showMessageDialog(frame, "Total amount cannot be less than original bill.");
                    return;
                }
                double extraAmount = totalAmount - originalTotal;
                StringBuilder sb = new StringBuilder("--- Final Bill ---\n");
                for (int i = 0; i < numberOfPeople; i++) 
                {
                    double extraPerPerson = extraAmount * (bills[i] / originalTotal);
                    sb.append(names[i] + " pays: RM " + String.format("%.2f", bills[i] + extraPerPerson) + "\n");
                }
                JOptionPane.showMessageDialog(frame, sb.toString());
            } 
            else 
            {
                StringBuilder sb = new StringBuilder("--- Final Bill ---\n");
                for (int i = 0; i < numberOfPeople; i++) 
                {
                    sb.append(names[i] + " pays: RM " + String.format("%.2f", bills[i]) + "\n");
                }
                JOptionPane.showMessageDialog(frame, sb.toString());
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }
    
    public class MusicPlayer {
    public static void playMusic(String filepath) {
        try {
            File musicPath = new File(filepath);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY); 
            
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    }
    public static void playClickSound() {
    try {
        File soundFile = new File("click.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
    } 
    catch (Exception e) 
    {
        e.printStackTrace();
    }
}
}
