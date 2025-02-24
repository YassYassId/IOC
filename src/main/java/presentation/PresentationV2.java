package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class PresentationV2 {
    public static void main(String[] args) {
        // Injection des dépendances par instanciation dynamique
        try {
            Scanner sc = new Scanner(new File("config.txt"));
            // Instanciation dynamique de la classe DAO
            String daoClassname = sc.nextLine();
            Class cDao = Class.forName(daoClassname);
            IDao dao = (IDao) cDao.getConstructor().newInstance();

            // Instanciation dynamique de la classe Metier
            String metierClassname = sc.nextLine();
            Class cMetier = Class.forName(metierClassname);

//            System.out.println("Instanciation en utilisant Constructeur avec parametre");
//            IMetier metierConstructeur = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao); // Avec constructeur avec parametre
//            System.out.println(metierConstructeur.calcul());

            System.out.println("Instanciation en utilisant Setter");
            IMetier metier = (IMetier) cMetier.getConstructor().newInstance(); // Avec constructeur sans parametre
            Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
            setDao.invoke(metier, dao);
            System.out.println(metier.calcul());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
