import java.util.ArrayList;

/**
 * Clasa solutie verifica daca conditiile jocului sunt indeplinite
 */
public class Solutie {

    int[][] solutie;
    ArrayList<Integer> pozitie_X;
    ArrayList<Integer> pozitie_Y;

    int dimensiune;

    /**
     * Parcurgem axele X si Y,verificand conditiile cerute ;
     * Numaram cate bombe sunt imprejur
     *
     * @param solutie    matrice cu numarul de bombe
     * @param pozitie_X  axa X
     * @param pozitie_Y  axa Y
     * @param dimensiune lungime grid
     */
    public Solutie(int[][] solutie, ArrayList<Integer> pozitie_X, ArrayList<Integer> pozitie_Y, int dimensiune) {
        /**
         * Parcurgem axele X si Y,verificand conditiile cerute
         */
        for (int y = 0; y < solutie.length; y++) {

            for (int x = 0; x < solutie[0].length; x++) {
                ///verificam daca pozitia are bomba
                int ok_modificare = 0;
                int numar_bombe_imprejur = 0;

                for (int i = 0; i < pozitie_X.size(); i++) {
                    if (x == pozitie_X.get(i) && y == pozitie_Y.get(i)) {
                        ///"indicam" bombele
                        solutie[y][x] = dimensiune + 1;
                        ok_modificare = 1;
                    }
                }

                if (ok_modificare == 0) {
                    /**
                     * Numaram cate bombe sunt imprejur(8 coordonate)
                     */
                    for (int i = 0; i < pozitie_X.size(); i++) {
                        if (x - 1 == pozitie_X.get(i) && y == pozitie_Y.get(i))
                            numar_bombe_imprejur++;
                        if (x + 1 == pozitie_X.get(i) && y == pozitie_Y.get(i))
                            numar_bombe_imprejur++;
                        if (x == pozitie_X.get(i) && y - 1 == pozitie_Y.get(i))
                            numar_bombe_imprejur++;
                        if (x == pozitie_X.get(i) && y + 1 == pozitie_Y.get(i))
                            numar_bombe_imprejur++;
                        if (x - 1 == pozitie_X.get(i) && y - 1 == pozitie_Y.get(i))
                            numar_bombe_imprejur++;
                        if (x + 1 == pozitie_X.get(i) && y + 1 == pozitie_Y.get(i))
                            numar_bombe_imprejur++;
                        if (x + 1 == pozitie_X.get(i) && y - 1 == pozitie_Y.get(i))
                            numar_bombe_imprejur++;
                        if (x - 1 == pozitie_X.get(i) && y + 1 == pozitie_Y.get(i))
                            numar_bombe_imprejur++;
                    }

                    solutie[y][x] = numar_bombe_imprejur;
                }
            }
        }

    }
}