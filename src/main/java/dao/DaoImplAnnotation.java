package dao;

import org.springframework.stereotype.Component;

@Component("dao")
public class DaoImplAnnotation implements IDao{
    @Override
    public double getData() {
        System.out.println("Database Version: ");
        double temp = 25;
        return temp;
    }
}
