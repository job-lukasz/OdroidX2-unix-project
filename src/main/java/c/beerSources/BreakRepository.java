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
public class BreakRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Break save(Break _break) {
		sessionFactory.getCurrentSession().saveOrUpdate(_break);
		return _break;
	}

	@SuppressWarnings("unchecked")
	public List<Break> getAllBreaks(){
		try {
			List<Break> _break = sessionFactory.getCurrentSession().createCriteria(Break.class).list();	
			return _break;
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public Break findById(String name) {
		try {
			Break _break = (Break) sessionFactory.getCurrentSession().createQuery("From Break Where name = :name")
					.setParameter("name", name).uniqueResult();
			return _break;
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	@Transactional
	public void delete(Long id) {
		Break _break = (Break) sessionFactory.getCurrentSession().get(Break.class, id);
		sessionFactory.getCurrentSession().delete(_break);
	}
}

