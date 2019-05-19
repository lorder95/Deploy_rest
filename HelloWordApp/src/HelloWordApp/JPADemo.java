package HelloWordApp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
 
 

public class JPADemo
{
        public static void main(String[] args)
    {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("jcg-JPA");
                EntityManager em = emf.createEntityManager();
                 
                em.getTransaction().begin();
                Evento evento = new Evento("21-11-19","Meeting","Prova");
                System.out.println("COMIITING");
                em.persist(evento);
                em.getTransaction().commit();
                 
    }
}