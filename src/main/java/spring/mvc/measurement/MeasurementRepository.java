package spring.mvc.measurement;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = false)
public class MeasurementRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public Measurement save(Measurement measure) {
		sessionFactory.getCurrentSession().saveOrUpdate(measure);
		return measure;
	}
	
}
