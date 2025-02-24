package extension;


import dao.IDao;
import org.springframework.stereotype.Component;

@Component("dao2")
public class DaoImplV2Annotation implements IDao {
    @Override
    public double getData() {
        System.out.println("Web service version");
        double temp = 1 + (Math.random() * 44);
        return temp;
    }
}
