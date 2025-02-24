package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("metier")
public class MetierImplAnnotation implements IMetier {

    private IDao dao;

    public MetierImplAnnotation(@Qualifier("dao2") IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        System.out.println("Version Framework Spring avec Annotation");
        double t = dao.getData();
        double result = t * 60;
        return result;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }

}
