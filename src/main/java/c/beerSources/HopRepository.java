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
public class HopRepository {

	// @PersistenceContext
	// private EntityManager entityManager;
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Hop save(Hop hop) {
		sessionFactory.getCurrentSession().saveOrUpdate(hop);
		return hop;
	}

	@SuppressWarnings("unchecked")
	public List<Hop> getAllHops(){
		try {
			List<Hop> hop = sessionFactory.getCurrentSession().createCriteria(Hop.class).list();	
			return hop;
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public Hop findById(String name) {
		try {
			Hop hop = (Hop) sessionFactory.getCurrentSession().createQuery("From Hop Where name = :name")
					.setParameter("name", name).uniqueResult();
			return hop;
		} catch (PersistenceException e) {
			return null;
		}
	}

	public void delete(Long id) {
		Hop hop = (Hop) sessionFactory.getCurrentSession().get(Hop.class, id);
		sessionFactory.getCurrentSession().delete(hop);
	}
}

