package c.brew;

import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.hibernate.FetchMode;
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

	@Transactional
	public Brewing save(Brewing brewing) {
		sessionFactory.getCurrentSession().saveOrUpdate(brewing);
		return brewing;
	}

	@Transactional
	public BrewBreak save(BrewBreak brewBreak) {
		sessionFactory.getCurrentSession().saveOrUpdate(brewBreak);
		return brewBreak;
	}
	
	@SuppressWarnings("unchecked")
	public List<BrewHop> getAllBrewHops() {
		try {
			List<BrewHop> brewHop = sessionFactory.getCurrentSession()
					.createCriteria(BrewHop.class).list();
			return brewHop;
		} catch (PersistenceException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<BrewAddon> getAllBrewAddons() {
		try {
			List<BrewAddon> brewAddon = sessionFactory.getCurrentSession()
					.createCriteria(BrewAddon.class).list();
			return brewAddon;
		} catch (PersistenceException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<BrewMalt> getAllBrewMalts() {
		try {
			List<BrewMalt> brewMalt = sessionFactory.getCurrentSession()
					.createCriteria(BrewMalt.class).list();
			return brewMalt;
		} catch (PersistenceException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Brewing> getAllBrewings() {
		try {
			List<Brewing> brewing = sessionFactory.getCurrentSession().createCriteria(Brewing.class)
					.setFetchMode("malts", FetchMode.SELECT)
					.setFetchMode("hops", FetchMode.SELECT)
					.setFetchMode("addons", FetchMode.SELECT)
					.setFetchMode("breaks", FetchMode.SELECT)
					.list();
			return brewing;
		} catch (PersistenceException e) {
			return null;
		}
	}

	public Brewing getBrewing(Long id) {
		Brewing brewing = (Brewing) sessionFactory.getCurrentSession().get(Brewing.class, id);
		return brewing;
	}

	public BrewHop findByName(String name) {
		try {
			BrewHop brewHop = (BrewHop) sessionFactory.getCurrentSession()
					.createQuery("From BrewHop Where name = :name")
					.setParameter("name", name).uniqueResult();
			return brewHop;
		} catch (PersistenceException e) {
			return null;
		}
	}

	@Transactional
	public void deleteHop(Long id, Long hopId) {
		Brewing brewing = (Brewing) sessionFactory.getCurrentSession().get(Brewing.class, id);
		BrewHop brewHop = (BrewHop) sessionFactory.getCurrentSession().get(BrewHop.class, hopId);
		Set<BrewHop> hops = brewing.getHops();
		hops.remove(brewHop);
		brewing.setHops(hops);
		sessionFactory.getCurrentSession().saveOrUpdate(brewing);
		sessionFactory.getCurrentSession().delete(brewHop);
	}

	@Transactional
	public void deleteMalt(Long id, Long maltId) {
		Brewing brewing = (Brewing) sessionFactory.getCurrentSession().get(Brewing.class, id);
		BrewMalt brewMalt = (BrewMalt) sessionFactory.getCurrentSession().get(BrewMalt.class, maltId);
		Set<BrewMalt> malts = brewing.getMalts();
		malts.remove(brewMalt);
		brewing.setMalts(malts);
		sessionFactory.getCurrentSession().saveOrUpdate(brewing);
		sessionFactory.getCurrentSession().delete(brewMalt);
	}
	@Transactional
	public void deleteBreak(Long id, Long breakId) {
		Brewing brewing = (Brewing) sessionFactory.getCurrentSession().get(Brewing.class, id);
		BrewBreak brewBreak = (BrewBreak) sessionFactory.getCurrentSession().get(BrewBreak.class, breakId);
		Set<BrewBreak> breaks = brewing.getBreaks();
		breaks.remove(brewBreak);
		brewing.setBreaks(breaks);
		sessionFactory.getCurrentSession().saveOrUpdate(brewing);
		sessionFactory.getCurrentSession().delete(brewBreak);
	}
	
	@Transactional
	public void deleteAddon(Long id, Long addonId) {
		Brewing brewing = (Brewing) sessionFactory.getCurrentSession().get(Brewing.class, id);
		BrewAddon brewAddon = (BrewAddon) sessionFactory.getCurrentSession().get(BrewAddon.class, addonId);
		Set<BrewAddon> addons = brewing.getAddons();
		addons.remove(brewAddon);
		brewing.setAddons(addons);
		sessionFactory.getCurrentSession().saveOrUpdate(brewing);
		sessionFactory.getCurrentSession().delete(brewAddon);
	}

	@Transactional
	public void deleteBrewing(Long id) {
		Brewing brewing = (Brewing) sessionFactory.getCurrentSession().get(Brewing.class, id);
		sessionFactory.getCurrentSession().delete(brewing);
	}
	
	@Transactional
	public void addMalt(Long brewId, BrewMalt brewMalt) {
		Brewing brewing = (Brewing) sessionFactory.getCurrentSession().get(Brewing.class, brewId);
		Set<BrewMalt> malts = brewing.getMalts();
		malts.add(brewMalt);
		brewing.setMalts(malts);
		sessionFactory.getCurrentSession().saveOrUpdate(brewing);
	}
	
	@Transactional
	public void addBreak(Long brewId, BrewBreak brewBreak) {
		Brewing brewing = (Brewing) sessionFactory.getCurrentSession().get(Brewing.class, brewId);
		Set<BrewBreak> breaks = brewing.getBreaks();
		breaks.add(brewBreak);
		brewing.setBreaks(breaks);
		sessionFactory.getCurrentSession().saveOrUpdate(brewing);
	}
	
	@Transactional
	public void addAddon(Long brewId, BrewAddon brewAddon) {
		Brewing brewing = (Brewing) sessionFactory.getCurrentSession().get(Brewing.class, brewId);
		Set<BrewAddon> breaks = brewing.getAddons();
		breaks.add(brewAddon);
		brewing.setAddons(breaks);
		sessionFactory.getCurrentSession().saveOrUpdate(brewing);
	}
	
	@Transactional
	public void addHop(Long brewId, BrewHop brewHop) {
		Brewing brewing = (Brewing) sessionFactory.getCurrentSession().get(Brewing.class, brewId);
		Set<BrewHop> hops = brewing.getHops();
		hops.add(brewHop);
		brewing.setHops(hops);
		sessionFactory.getCurrentSession().saveOrUpdate(brewing);
	}
}