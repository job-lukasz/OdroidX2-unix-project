package c.brew;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class BrewRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public BrewHop save(BrewHop brewHop) {
		sessionFactory.getCurrentSession().saveOrUpdate(brewHop);
		return brewHop;
	}

	@Transactional
	public BrewMalt save(BrewMalt brewMalt) {
		sessionFactory.getCurrentSession().saveOrUpdate(brewMalt);
		return brewMalt;
	}
	
	@Transactional
	public BrewAddon save(BrewAddon brewAddon) {
		sessionFactory.getCurrentSession().saveOrUpdate(brewAddon);
		return brewAddon;
	}
	
	@SuppressWarnings("unchecked")
	public List<BrewHop> getAllBrewHops(){
		try {
			List<BrewHop> brewHop = sessionFactory.getCurrentSession().createCriteria(BrewHop.class).list();	
			return brewHop;
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public BrewHop findByName(String name) {
		try {
			BrewHop brewHop = (BrewHop) sessionFactory.getCurrentSession().createQuery("From BrewHop Where name = :name")
					.setParameter("name", name).uniqueResult();
			return brewHop;
		} catch (PersistenceException e) {
			return null;
		}
	}

	@Transactional
	public void deleteHop(Long id) {
		BrewHop brewHop = (BrewHop) sessionFactory.getCurrentSession().get(BrewHop.class, id);
		sessionFactory.getCurrentSession().delete(brewHop);
	}
	@Transactional
	public void deleteMalt(Long id) {
		BrewMalt brewMalt = (BrewMalt) sessionFactory.getCurrentSession().get(BrewMalt.class, id);
		sessionFactory.getCurrentSession().delete(brewMalt);
	}
	@Transactional
	public void deleteAddon(Long id) {
		BrewAddon brewAddon = (BrewAddon) sessionFactory.getCurrentSession().get(BrewAddon.class, id);
		sessionFactory.getCurrentSession().delete(brewAddon);
	}
}