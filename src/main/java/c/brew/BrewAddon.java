package c.brew;

import java.util.Date;
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

import c.beerSources.Addons;

@SuppressWarnings("serial")
@Entity
@Table(name = "BrewAddon")
public class BrewAddon implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long brewAddonId;

	@Column
	private String name;
	
	@ManyToMany(mappedBy = "addons")
	private Set<Brewing> brewing = new HashSet<Brewing>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "addonId", nullable = false)
	private Addons addon;
	
	@Column
	private Date start;
	
	@Column
	private Date end;
	
	@Column
	private String description;
	
	@Column
	private double quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "addonUsingId", nullable = false)
	private AddonUsingTime usingTime;
	
	public AddonUsingTime getUsingTime() {
		return usingTime;
	}

	public void setUsingTime(AddonUsingTime usingTime) {
		this.usingTime = usingTime;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Long getBrewAddonId() {
		return brewAddonId;
	}

	public void setBrewAddonId(Long brewAddonId) {
		this.brewAddonId = brewAddonId;
	}

	public Addons getAddon() {
		return addon;
	}

	public void setAddon(Addons addon) {
		this.addon = addon;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Brewing> getBrewing() {
		return brewing;
	}

	public void setBrewing(Set<Brewing> brewing) {
		this.brewing = brewing;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
