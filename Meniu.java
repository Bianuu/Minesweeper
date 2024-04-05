import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

/**
 * Clasa Meniu,unde porneste jocul
 */
public class Meniu implements ActionListener {

    JFrame frame;
    JButton buton;
    JPanel panou;
    JLabel label;

    ImageIcon icon;
    File iconImage;

    /**
     * Cream frame-ul ;
     * Setam aplicatiei o pictograma ;
     * Setam frame-ul ;
     * Initializam butonul ;
     * Initializam label-ul ;
     * Initializam panoul ;
     * Adaugam label-ul la panoul curent ;
     * Adaugam panoul la frame-ul curent
     */
    public Meniu() {

        /**
         * Cream frame-ul
         */
        frame = new JFrame();

        /**
         * Setam aplicatiei o pictograma
         */
        iconImage = new File("src/Minesweeper.png");
        if (iconImage.exists()) {
            icon = new ImageIcon("src/Minesweeper.png");
            frame.setIconImage(icon.getImage());
        }
        /**
         * Setam frame-ul
         */
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setSize(350, 170);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        /**
         * Initializam butonul
         */
        buton = new JButton();
        buton.setForeground(Color.BLACK);
        buton.setText("Start");
        buton.setFont(new Font("MV Boli", Font.BOLD, 40));
        buton.setLocation(130, 150);
        buton.setBackground(Color.GREEN);
        buton.setFocusable(false);
        buton.addActionListener(this);

        /**
         * Initializam label-ul
         */
        label = new JLabel();
        label.setText("Minesweeper by Fabișor");
        label.setLocation(250, 250);

        /**
         * Initializam panoul
         */
        panou = new JPanel();


        /**
         * Adaugam label-ul la panoul curent
         */
        panou.add(label);
        panou.add(buton);


        /**
         * Adaugam panoul la frame-ul curent
         */
        frame.add(panou);

    }

    /**
     * La apasarea butonului START se ascunde frame-ul meniului si se porneste
     * frame-ul jocul
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buton) {
            {

                frame.setVisible(false);
                Random random = new Random();
                int randomCasute = random.nextInt(24) + 2;
                int randomBombe = random.nextInt(randomCasute - 1) + 1;
                new Minesweeper(randomCasute, randomBombe);
            }
        }

    }

}