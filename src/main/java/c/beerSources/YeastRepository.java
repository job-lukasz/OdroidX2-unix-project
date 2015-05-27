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
public class YeastRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Yeast save(Yeast yeast) {
		sessionFactory.getCurrentSession().saveOrUpdate(yeast);
		return yeast;
	}

	@SuppressWarnings("unchecked")
	public List<Yeast> getAllYeast() {
		try {
			List<Yeast> yeast = sessionFactory.getCurrentSession().createCriteria(Yeast.class).list();
			return yeast;
		} catch (PersistenceException e) {
			return null;
		}
	}

	@Transactional
	public void delete(Long id) {
		Yeast yeast = (Yeast) sessionFactory.getCurrentSession().get(Yeast.class, id);
		sessionFactory.getCurrentSession().delete(yeast);
	}

	public Yeast getYeast(Long yeastId) {
		return (Yeast) sessionFactory.getCurrentSession().get(Yeast.class, yeastId);
	}
}
