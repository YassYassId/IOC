package metier;

import dao.DaoImpl;
import dao.IDao;

public class MetierImpl implements IMetier {

    private IDao dao;

    @Override
    public double calcul() {
        double t = dao.getData();
        double result = t * 60;
        return result;
    }
}
