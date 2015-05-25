package c.brew;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import c.beerSources.AddonUsingTime;
import c.beerSources.Addons;

@SuppressWarnings("serial")
@Entity
@Table(name = "BrewAddon")
public class BrewAddon implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@ManyToMany(mappedBy = "addons")
	private Set<Brewing> brewing = new HashSet<Brewing>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "addonId", nullable = false)
	private Addons addon;
	
	@Column
	private int startMinute;
	
	@Column
	private double quantity;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "addonUsingId", nullable = false)
	private AddonUsingTime addonUsingTime;
	
	
	public BrewAddon(Addons addon, int startMinute, double quantity, AddonUsingTime usingTime){
		this.addon = addon;
		this.startMinute = startMinute;
		this.quantity = quantity;
		this.addonUsingTime = usingTime;
	}
	
	protected BrewAddon(){};
	
	public AddonUsingTime getAddonUsingTime() {
		return addonUsingTime;
	}

	public void setAddonUsingTime(AddonUsingTime usingTime) {
		this.addonUsingTime = usingTime;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long brewAddonId) {
		this.id = brewAddonId;
	}

	public Addons getAddon() {
		return addon;
	}

	public void setAddon(Addons addon) {
		this.addon = addon;
	}
	
	public Set<Brewing> getBrewing() {
		return brewing;
	}

	public void setBrewing(Set<Brewing> brewing) {
		this.brewing = brewing;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}
}
