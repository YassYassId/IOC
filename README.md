# Dependency Injection in Java using Various Approaches

## Introduction
This project demonstrates different ways to implement dependency injection in Java, including:
- **Static Instantiation**
- **Dynamic Instantiation using Reflection**
- **Spring Framework (XML Configuration & Annotations)**

The project follows an Inversion of Control (IoC) principle to decouple components, making the application more modular and testable.

## Project Structure
```
IOC/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── dao/          # Data Access Object implementations
│   │   │   ├── extension/    # Alternative DAO implementations
│   │   │   ├── metier/       # Business logic layer
│   │   │   ├── presentation/ # Main application logic
│   │   ├── resources/        # Configuration files (e.g., XML for Spring)
│   ├── test/                 # Unit tests (if applicable)
├── pom.xml                   # Maven dependencies
├── config.txt                 # Configuration file for dynamic instantiation
└── .gitignore                 # Ignored files and directories
```

## Implementation Details

### 1. DAO Layer (Data Access Object)
#### Interface: `IDao`
```java
package dao;

public interface IDao {
    double getData();
}
```

#### Implementations:
- **Database-based implementation**
```java
package dao;

public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Database Version: ");
        return 25;
    }
}
```
- **Web service-based implementation**
```java
package extension;

import dao.IDao;

public class DaoImplV2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Web service version");
        return 1 + (Math.random() * 44);
    }
}
```

### 2. Business Logic Layer (Metier)
#### Interface: `IMetier`
```java
package metier;

public interface IMetier {
    double calcul();
}
```

#### Implementation:
```java
package metier;

import dao.IDao;

public class MetierImpl implements IMetier {
    private IDao dao;

    public MetierImpl() {}

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        return dao.getData() * 60;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
```

### 3. Dependency Injection Methods

#### a) Static Instantiation
```java
package presentation;

import dao.DaoImpl;
import metier.MetierImpl;

public class PresentationV1 {
    public static void main(String[] args) {
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao);
        System.out.println("Injection via Setter: " + metier.calcul());

        MetierImpl metierConstruct = new MetierImpl(dao);
        System.out.println("Injection via Constructor: " + metierConstruct.calcul());
    }
}
```

#### b) Dynamic Instantiation
```java
package presentation;

import dao.IDao;
import metier.IMetier;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class PresentationV2 {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("config.txt"));
            String daoClassname = sc.nextLine();
            Class cDao = Class.forName(daoClassname);
            IDao dao = (IDao) cDao.getConstructor().newInstance();

            String metierClassname = sc.nextLine();
            Class cMetier = Class.forName(metierClassname);
            IMetier metier = (IMetier) cMetier.getConstructor().newInstance();
            Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
            setDao.invoke(metier, dao);

            System.out.println(metier.calcul());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

#### c) Spring Framework
##### XML Configuration:
`config.xml`
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dao" class="dao.DaoImpl"/>
    <bean id="metier" class="metier.MetierImpl">
        <constructor-arg ref="dao"/>
    </bean>
</beans>
```
##### Spring XML-based Presentation
```java
package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresentationSpringXML {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        IMetier metier = (IMetier) context.getBean("metier");
        System.out.println(metier.calcul());
    }
}
```
##### Spring Annotations
DAO Implementation with Annotations:
```java
package dao;

import org.springframework.stereotype.Component;

@Component("dao")
public class DaoImplAnnotation implements IDao {
    @Override
    public double getData() {
        return 25;
    }
}
```
Metier Implementation with Annotations:
```java
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
        return dao.getData() * 60;
    }
}
```
Presentation with Annotation Configuration:
```java
package presentation;

import metier.IMetier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresentationSpringAnnot {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("dao", "metier");
        IMetier metier = (IMetier) context.getBean("metier");
        System.out.println(metier.calcul());
    }
}
```

## Conclusion
This project illustrates multiple approaches to dependency injection in Java, from manual instantiation to dynamic reflection and using Spring Framework. It highlights the importance of loose coupling in software design.
