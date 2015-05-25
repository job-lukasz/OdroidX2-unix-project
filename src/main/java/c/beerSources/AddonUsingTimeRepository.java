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
public class AddonUsingTimeRepository {

	// @PersistenceContext
	// private EntityManager entityManager;
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public AddonUsingTime save(AddonUsingTime addonUsingTime) {
		sessionFactory.getCurrentSession().saveOrUpdate(addonUsingTime);
		return addonUsingTime;
	}

	@SuppressWarnings("unchecked")
	public List<AddonUsingTime> getAllAddonUsingTimes() {
		try {
			List<AddonUsingTime> addonUsingTime = sessionFactory.getCurrentSession().createCriteria(AddonUsingTime.class).list();
			return addonUsingTime;
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public AddonUsingTime getAddonUsingTime(Long id) {
		return (AddonUsingTime) sessionFactory.getCurrentSession().get(AddonUsingTime.class, id);
	}
}
