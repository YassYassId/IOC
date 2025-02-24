package dao;

public class DaoImpl implements IDao {

    @Override
    public double getData() {
        System.out.println("Database Version: ");
        double temp = 25;
        return temp;
    }
}
