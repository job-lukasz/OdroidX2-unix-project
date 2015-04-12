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
public class AddonsRepository {

	// @PersistenceContext
	// private EntityManager entityManager;
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Addons save(Addons Addons) {
		sessionFactory.getCurrentSession().saveOrUpdate(Addons);
		return Addons;
	}

	@SuppressWarnings("unchecked")
	public List<Addons> getAllAddonss(){
		try {
			List<Addons> Addons = sessionFactory.getCurrentSession().createCriteria(Addons.class).list();	
			return Addons;
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public Addons findById(String name) {
		try {
			Addons addons = (Addons) sessionFactory.getCurrentSession().createQuery("From Addons Where name = :name")
					.setParameter("name", name).uniqueResult();
			return addons;
		} catch (PersistenceException e) {
			return null;
		}
	}

	public void delete(Long id) {
		Addons addons = (Addons) sessionFactory.getCurrentSession().get(Addons.class, id);
		sessionFactory.getCurrentSession().delete(addons);
	}
}

