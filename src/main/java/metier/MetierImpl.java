package metier;

import dao.DaoImpl;
import dao.IDao;

public class MetierImpl implements IMetier {

    private IDao dao;

    public MetierImpl() {
    }

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double t = dao.getData();
        double result = t * 60;
        return result;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
