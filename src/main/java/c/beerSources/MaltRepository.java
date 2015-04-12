package c.beerSources;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class MaltRepository {

	// @PersistenceContext
	// private EntityManager entityManager;
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Malt save(Malt malt) {
		sessionFactory.getCurrentSession().saveOrUpdate(malt);
		return malt;
	}

	@SuppressWarnings("unchecked")
	public List<Malt> getAllMalts(){
		try {
			List<Malt> malt = sessionFactory.getCurrentSession().createCriteria(Malt.class).list();	
			return malt;
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public Malt findByName(String name) {
		try {
			Malt malt = (Malt) sessionFactory.getCurrentSession().createQuery("From Malt Where name = :name")
					.setParameter("name", name).uniqueResult();
			return malt;
		} catch (PersistenceException e) {
			return null;
		}
	}

	public void delete(Long id) {
		Malt malt = (Malt) sessionFactory.getCurrentSession().get(Malt.class, id);
		sessionFactory.getCurrentSession().delete(malt);
	}
}
