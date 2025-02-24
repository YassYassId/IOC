package extension;

import dao.IDao;

public class DaoImplV2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Web service version");
        double temp = 1 + (Math.random() * 44);
        return temp;
    }
}
