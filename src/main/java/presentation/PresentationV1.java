package presentation;

import dao.DaoImpl;
import metier.MetierImpl;


public class PresentationV1 {
    public static void main(String[] args) {

        // Injection des dépendances par instanciation statique
        DaoImpl dao = new DaoImpl();

        // Injection en utilisant un Setter
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao);
        System.out.println("Injection via Setter: ");
        System.out.println(metier.calcul());

        // Injection en utilisant le constructeur
        MetierImpl metierConstruct = new MetierImpl(dao);
        System.out.println("Injection via Constructor: ");
        System.out.println(metierConstruct.calcul());
    }
}
