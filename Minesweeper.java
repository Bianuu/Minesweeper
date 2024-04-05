import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clasa principala a aplicatiei
 */
public class Minesweeper implements ActionListener {
    int xInitial;
    int yInitial;
    int[][] solutie;
    int[][] vectSteaguri;

    Random random;

    JFrame frame;
    JPanel textPanel;
    JPanel buttonPanel;
    JButton[][] buton;
    JLabel textfield;
    JButton buton_reset;
    JButton steag;

    JButton alege;

    int dimensiune = 0;
    int numar_bombe = 0;

    ImageIcon icon;
    File iconImage;

    ArrayList<Integer> pozitie_X;
    ArrayList<Integer> pozitie_Y;

    int semaforSteag;
    int contor = 0;
    int ultimuXverificat = 0, ultimuYverificat = 0;

    int copyNrPatratele;
    int numar_bombe_personalizare, dimensiunePersonalizare = 0;

    /**
     * Generam dimensiunea frame-ului aleatoriu ;
     * Generam bombe aleatoriu ;
     * Initializam frame-ul curent ;
     * Setam o pictograma aplicatiei ;
     * Initializam panoul de text ;
     * Initializam panoul de butoane ;
     * Initializam label-ul ;
     * Initializam butonul de reset ;
     * Initializam butonul de steag ;
     * Initializam butonul modifica ;
     * Setam dimensiunea frame-ului ;
     * Adaugam la frame-ul curent toate obiectele initializate
     *
     * @param patratele numar patratele
     * @param bombe     numar bombe
     */
    public Minesweeper(int patratele, int bombe) {

        dimensiune = patratele;
        numar_bombe = bombe;

        copyNrPatratele = patratele;

        ultimuXverificat = dimensiune + 1;
        ultimuYverificat = dimensiune + 1;

        vectSteaguri = new int[dimensiune][dimensiune];

        random = new Random();

        pozitie_X = new ArrayList<Integer>();
        pozitie_Y = new ArrayList<Integer>();

        /**
         * Generam dimensiunea frame-ului aleatoriu
         */
        for (int i = 0; i < numar_bombe; i++) {
            int X_random = random.nextInt(dimensiune);
            int Y_random = random.nextInt(dimensiune);

            pozitie_X.add(X_random);
            pozitie_Y.add(Y_random);
        }

        /**
         * Generam bombe aleatoriu
         */
        /// Pentru a preveni suprapunerea bombelor,altfel, uneori, un singur loc
        ///ar putea conține 2 numar_bombe și ar da un contor greșit

        for (int i = 0; i < numar_bombe; i++) {
            for (int j = i + 1; j < numar_bombe; j++) {
                if (pozitie_X.get(i) == pozitie_X.get(j) && pozitie_Y.get(i) == pozitie_Y.get(j)) {
                    pozitie_X.set(j, random.nextInt(dimensiune));
                    pozitie_Y.set(j, random.nextInt(dimensiune));
                    i = 0;
                    j = 0;
                }
            }
        }

        /**
         * Initializam frame-ul curent
         */
        frame = new JFrame();
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * Setam o pictograma aplicatiei
         */
        iconImage = new File("src/Minesweeper.png");
        if (iconImage.exists()) {
            icon = new ImageIcon("src/Minesweeper.png");
            frame.setIconImage(icon.getImage());
        }

        /**
         * Initializam panoul de text
         */
        textPanel = new JPanel();
        textPanel.setVisible(true);
        textPanel.setBackground(Color.WHITE);

        /**
         * Initializam panoul de butoane
         */
        buttonPanel = new JPanel();
        buttonPanel.setVisible(true);
        buttonPanel.setLayout(new GridLayout(dimensiune, dimensiune));

        /**
         * Initializam label-ul
         */
        textfield = new JLabel();
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setFont(new Font("MV Boli", Font.BOLD, 20));
        textfield.setForeground(Color.BLACK);
        textfield.setText(numar_bombe + "_bombe");

        /**
         * Initializam butonul de reset
         */
        buton_reset = new JButton();
        buton_reset.setForeground(Color.BLACK);
        buton_reset.setText("Reseteaza");
        buton_reset.setFont(new Font("MV Boli", Font.BOLD, 20));
        buton_reset.setBackground(Color.WHITE);
        buton_reset.setFocusable(false);
        buton_reset.addActionListener(this);

        /**
         * Initializam butonul de steag
         */
        steag = new JButton();
        steag.setForeground(Color.RED);
        steag.setText("¶");
        steag.setFont(new Font("MV Boli", Font.BOLD, 30));
        steag.setBackground(Color.WHITE);
        steag.setFocusable(false);
        steag.addActionListener(this);

        buton = new JButton[dimensiune][dimensiune];
        solutie = new int[dimensiune][dimensiune];
        for (int i = 0; i < buton.length; i++) {
            for (int j = 0; j < buton[0].length; j++) {
                buton[i][j] = new JButton();
                buton[i][j].setFocusable(false);
                buton[i][j].setFont(new Font("MV Boli", Font.BOLD, 20));
                buton[i][j].addActionListener(this);
                buton[i][j].setText("");
                buttonPanel.add(buton[i][j]);
            }
        }

        /**
         * Initializam butonul modifica
         */
        alege = new JButton("Modifica");
        alege.addActionListener(this);
        alege.setFont(new Font("MV Boli", Font.BOLD, 10));
        alege.setBackground(new Color(165, 183, 201));
        alege.setForeground(new Color(255, 255, 255));
        alege.setBorder(null);

        textPanel.add(textfield);
        frame.add(buttonPanel);
        frame.add(textPanel, BorderLayout.NORTH);

        /**
         * Setam dimensiunea frame-ului
         */
        if (copyNrPatratele == 9 && numar_bombe == 10) frame.setSize(570, 570);
        else if (copyNrPatratele == 16 && numar_bombe == 40) frame.setSize(900, 650);
        else if (copyNrPatratele == 22 && numar_bombe == 100) frame.setSize(1250, 700);
        else {
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

            ///Setam dimensiunea ecranului

            int height = (int) size.getHeight();
            int width = (int) size.getWidth();

            if (copyNrPatratele <= 9) frame.setSize(570, 570);
            else if (copyNrPatratele > 9 && copyNrPatratele <= 16) frame.setSize(900, 650);
            else if (copyNrPatratele > 16 && copyNrPatratele <= 25) frame.setSize(1250, 700);
            else {
                frame.setSize(width, height);
                JOptionPane.showMessageDialog(null, "Daca nu puteti vedea clar jocul,ecranul este prea mic.Incercati o grila mai mica", "Customize", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        /**
         * Adaugam la frame-ul curent toate obiectele initializate mai sus
         */
        frame.add(buton_reset, BorderLayout.SOUTH);
        frame.add(steag, BorderLayout.WEST);
        frame.add(alege, BorderLayout.EAST);
        frame.revalidate();
        frame.setLocationRelativeTo(null);

        new Solutie(solutie, pozitie_X, pozitie_Y, dimensiune);

    }

    /**
     * In functie de "input-ul" utilizatorului realizam aplicatia
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == alege) {
            {
                int success = Personalizeaza();
                if (success == 1) {
                    frame.dispose();
                    new Minesweeper(dimensiunePersonalizare, numar_bombe_personalizare);
                }
            }

        }
        if (e.getSource() == steag) {
            if (semaforSteag == 1) {
                steag.setBackground(Color.WHITE);
                semaforSteag = 0;
            } else {
                steag.setBackground(Color.RED);
                semaforSteag = 1;
            }
        }
        if (e.getSource() == buton_reset) {
            frame.dispose();
            new Minesweeper(dimensiune, numar_bombe);
        }
        /**
         * Setam butoanele cu steag sa aiba culoarea rosie
         */
        for (int i = 0; i < buton.length; i++) {
            for (int j = 0; j < buton[0].length; j++) {
                if (e.getSource() == buton[i][j]) {
                    if (semaforSteag == 1) {
                        if (vectSteaguri[i][j] == 1) {
                            buton[i][j].setText("");
                            buton[i][j].setBackground(null);
                            vectSteaguri[i][j] = 0;
                        } else {
                            if (buton[i][j].getText().equals("")) {
                                buton[i][j].setForeground(Color.RED);
                                buton[i][j].setBackground(Color.BLACK);
                                buton[i][j].setText("¶");
                                vectSteaguri[i][j] = 1;
                            }
                        }
                    } else {
                        if (vectSteaguri[i][j] == 0) {
                            buton[i][j].setBackground(null);
                            verificare(i, j);
                        }
                    }
                }
            }
        }
    }

    /**
     * Alegem dimensiunea grilei introdusa de utilizator intr-un textbox si
     * verificam daca datele sunt valide ; In cazul in care datele sunt valide afisam
     * grila ; In cazul in care datele nu sunt valide afisam un mesaj "Introducere
     * Invalida"
     *
     * @return 1 sau 0,in fuctie de indeplinirea conditiei
     */
    public int Personalizeaza() {
        int semaforSchimbari = 0;

        String size = JOptionPane.showInputDialog(null, "Alege dimensiunea Grilei (ex: 4 va fi grila 4x4)", "Customize", JOptionPane.PLAIN_MESSAGE);
        try {
            dimensiunePersonalizare = Integer.parseInt(size);

            if (dimensiunePersonalizare <= 30 && dimensiunePersonalizare > 0) semaforSchimbari = 1;
            else {
                if (dimensiunePersonalizare > 29)
                    JOptionPane.showMessageDialog(null, "Grila prea Mare!", "Customize", JOptionPane.WARNING_MESSAGE);
                else if (dimensiunePersonalizare <= 0)
                    JOptionPane.showMessageDialog(null, "Grila NU poate fi negativa sau 0!", "Customize", JOptionPane.WARNING_MESSAGE);
                semaforSchimbari = 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Introducere invalida", "Customize", JOptionPane.WARNING_MESSAGE);
            semaforSchimbari = 0;
        }

        if (semaforSchimbari == 1) {
            String numar_bombe = JOptionPane.showInputDialog(null, "Numarul de bombe", "Customize", JOptionPane.PLAIN_MESSAGE);

            try {
                numar_bombe_personalizare = Integer.parseInt(numar_bombe);

                if (numar_bombe_personalizare <= (Math.pow(dimensiunePersonalizare, 2)) && numar_bombe_personalizare >= 0)
                    semaforSchimbari = 1;
                else {
                    if (numar_bombe_personalizare > (Math.pow(dimensiunePersonalizare, 2)))
                        JOptionPane.showMessageDialog(null, "Mai multe bombe decat spatii!", "Customize", JOptionPane.WARNING_MESSAGE);
                    else if (numar_bombe_personalizare < 0)
                        JOptionPane.showMessageDialog(null, "Numar_bombe NU poate fi negativ!", "Customize", JOptionPane.WARNING_MESSAGE);
                    semaforSchimbari = 0;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Introducere invalida", "Customize", JOptionPane.WARNING_MESSAGE);
                semaforSchimbari = 0;
            }
        }

        if (semaforSchimbari == 1) return 1;
        else return 0;
    }

    /**
     * Verificam daca jocul s-a terminat
     *
     * @param y pozitie Y
     * @param x pozitie X
     */
    public void verificare(int y, int x) {
        int stop = 0;
        if (solutie[y][x] == (dimensiune + 1)) {
            new GameOver(0, steag, textfield, buton, pozitie_X, pozitie_Y);
            stop = 1;
        }

        if (stop == 0) {
            selectamCuloarea(y, x);

            if (solutie[y][x] == 0) {
                xInitial = x;
                yInitial = y;

                contor = 0;

                Afisare();
            }
            verificaCastigator();
        }
    }

    /**
     * Verificam daca jucatorul este castigator
     */
    public void verificaCastigator() {
        int butonleft = 0;

        for (int i = 0; i < buton.length; i++) {
            for (int j = 0; j < buton[0].length; j++) {
                if (buton[i][j].getText() == "" || buton[i][j].getText() == "¶") butonleft++;
            }
        }
        if (butonleft == numar_bombe) new GameOver(1, steag, textfield, buton, pozitie_X, pozitie_Y);
    }

    /**
     * Afisam culoarea fiecarei valori
     */
    public void Afisare() {
        if (contor < 1) {
            if ((xInitial - 1) >= 0) selectamCuloarea(yInitial, xInitial - 1);
            if ((xInitial + 1) < dimensiune) selectamCuloarea(yInitial, xInitial + 1);
            if ((yInitial - 1) >= 0) selectamCuloarea(yInitial - 1, xInitial);
            if ((yInitial + 1) < dimensiune) selectamCuloarea(yInitial + 1, xInitial);
            if ((yInitial + 1) < dimensiune && (xInitial + 1) < dimensiune)
                selectamCuloarea(yInitial + 1, xInitial + 1);
            if ((yInitial - 1) >= 0 && (xInitial - 1) >= 0) selectamCuloarea(yInitial - 1, xInitial - 1);
            if ((yInitial + 1) < dimensiune && (xInitial - 1) >= 0) selectamCuloarea(yInitial + 1, xInitial - 1);
            if ((yInitial - 1) >= 0 && (xInitial + 1) < dimensiune) selectamCuloarea(yInitial - 1, xInitial + 1);

            contor++;
            Afisare();
        } else {

            /**
             *Daca un buton este 0 si nu este nimic pe langa acesta functia Afisare se va
             * apela din nou pentru a dezvalui restu(in continuare) ;
             * Daca e dezvaluit totul si suntem pe 0,se verifica ce butoane se mai pot dezvalui
             * in jurul lui
             */
            for (int y = 0; y < buton.length; y++) {
                for (int x = 0; x < buton[0].length; x++) {
                    if (buton[y][x].getText().equals("0")) {
                        if (y - 1 >= 0) {
                            if (buton[y - 1][x].getText().equals("") || buton[y - 1][x].getText().equals("¶")) {
                                ultimuXverificat = x;
                                ultimuYverificat = y;
                            }
                        }
                        if (y + 1 < dimensiune) {
                            if (buton[y + 1][x].getText().equals("") || buton[y + 1][x].getText().equals("¶")) {
                                ultimuXverificat = x;
                                ultimuYverificat = y;
                            }
                        }
                        if (x - 1 >= 0) {
                            if (buton[y][x - 1].getText().equals("") || buton[y][x - 1].getText().equals("¶")) {
                                ultimuXverificat = x;
                                ultimuYverificat = y;
                            }
                        }
                        if (x + 1 < dimensiune) {
                            if (buton[y][x + 1].getText().equals("") || buton[y][x + 1].getText().equals("¶")) {
                                ultimuXverificat = x;
                                ultimuYverificat = y;
                            }
                        }
                        if (x - 1 >= 0 && y - 1 >= 0) {
                            if (buton[y - 1][x - 1].getText().equals("") || buton[y - 1][x - 1].getText().equals("¶")) {
                                ultimuXverificat = x;
                                ultimuYverificat = y;
                            }
                        }
                        if (x + 1 < dimensiune && y + 1 < dimensiune) {
                            if (buton[y + 1][x + 1].getText().equals("") || buton[y + 1][x + 1].getText().equals("¶")) {
                                ultimuXverificat = x;
                                ultimuYverificat = y;
                            }
                        }
                        if (x + 1 < dimensiune && y - 1 >= 0) {
                            if (buton[y - 1][x + 1].getText().equals("") || buton[y - 1][x + 1].getText().equals("¶")) {
                                ultimuXverificat = x;
                                ultimuYverificat = y;
                            }
                        }
                        if (x - 1 >= 0 && y + 1 < dimensiune) {
                            if (buton[y + 1][x - 1].getText().equals("") || buton[y + 1][x - 1].getText().equals("¶")) {
                                ultimuXverificat = x;
                                ultimuYverificat = y;
                            }
                        }
                    }
                }
            }
            if (ultimuXverificat < dimensiune + 1 && ultimuYverificat < dimensiune + 1) {
                xInitial = ultimuXverificat;
                yInitial = ultimuYverificat;

                contor = 0;

                ultimuXverificat = dimensiune + 1;
                ultimuYverificat = dimensiune + 1;

                Afisare();
            }
        }
    }

    /**
     * Alegem culoarea fiecarei valori
     *
     * @param y axa Y
     * @param x axa X
     */
    public void selectamCuloarea(int y, int x) {

        buton[y][x].setBackground(null);
        buton[y][x].setText(String.valueOf(solutie[y][x]));

        if (solutie[y][x] == 0) buton[y][x].setEnabled(false);
        if (solutie[y][x] == 1) buton[y][x].setForeground(Color.RED);
        if (solutie[y][x] == 2) buton[y][x].setForeground(Color.GREEN);
        if (solutie[y][x] == 3) buton[y][x].setForeground(Color.PINK);
        if (solutie[y][x] == 4) buton[y][x].setForeground(Color.MAGENTA);
        if (solutie[y][x] == 5) buton[y][x].setForeground(new Color(0, 102, 255));
        if (solutie[y][x] == 6) buton[y][x].setForeground(Color.BLUE);
        if (solutie[y][x] == 7) buton[y][x].setForeground(new Color(153, 0, 255));
        if (solutie[y][x] == 8) buton[y][x].setForeground(Color.LIGHT_GRAY);

    }
}