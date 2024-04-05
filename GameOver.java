import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Clasa GameOver verifica daca jocul a ajuns la final
 */

public class GameOver {

    JLabel textfield;
    JButton[][] buton;
    ArrayList<Integer> pozitie_X;
    ArrayList<Integer> pozitie_Y;
    int semaforCastigat;

    /**
     * In cazul in care conditiile sunt indeplinite se afiseaza "AI CASTIGAT" ;
     * In cazul in care conditiile nu sunt indeplinite se afiseaza "REINCEARCA" ;
     * In cazul in care se nimereste pe o mina,o setam pe aceasta si pe restul minelor cu B si
     * setam culoarea neagra
     *
     * @param semaforCastigat variabila care ne spune daca am castigat sau nu
     * @param steag           butonul pentru steag
     * @param textfield       casuta text
     * @param buton           buton
     * @param pozitie_X       axa X
     * @param pozitie_Y       axa Y
     */
    public GameOver(int semaforCastigat, JButton steag, JLabel textfield, JButton[][] buton, ArrayList<Integer> pozitie_X, ArrayList<Integer> pozitie_Y) {

        /**
         * In cazul in care conditiile sunt indeplinite se afiseaza "AI CASTIGAT"
         */
        if (semaforCastigat == 0) {
            textfield.setForeground(Color.RED);
            textfield.setText("REINCEARCA..!");
        }
        /**
         * In cazul in care conditiile nu sunt indeplinite se afiseaza "REINCEARCA"
         */
        else {
            textfield.setForeground(Color.GREEN);
            textfield.setText("AI CASTIGAT!");
        }
        /**
         * In cazul in care se nimereste pe o mina,o setam pe aceasta si pe restul minelor cu B si
         * setam culoarea neagra
         */
        for (int i = 0; i < buton.length; i++) {
            for (int j = 0; j < buton[0].length; j++) {
                buton[i][j].setBackground(null);
                ///dezactivam toate butoanele
                buton[i][j].setEnabled(false);
                for (int contor = 0; contor < pozitie_X.size(); contor++) {
                    if (j == pozitie_X.get(contor) && i == pozitie_Y.get(contor)) {
                        buton[i][j].setBackground(Color.BLACK);
                        buton[i][j].setText("B");
                    }
                }
            }
        }
        steag.setEnabled(false);
    }

}