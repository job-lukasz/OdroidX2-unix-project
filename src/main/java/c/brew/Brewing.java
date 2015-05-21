package c.brew;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Brewing")
public class Brewing implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long brewingId;

	@Column
	private String name;

	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BrewHops_Brewing", joinColumns = { @JoinColumn(name = "brewingId") }, inverseJoinColumns = { @JoinColumn(name = "brewHopId") })
	private Set<BrewHop> hops;

	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BrewMalts_Brewing", joinColumns = { @JoinColumn(name = "brewingId") }, inverseJoinColumns = { @JoinColumn(name = "brewMaltId") })
	private Set<BrewMalt> malts;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BrewAddons_Brewing", joinColumns = { @JoinColumn(name = "brewingId") }, inverseJoinColumns = { @JoinColumn(name = "brewAddonId") })
	private Set<BrewAddon> addons;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BrewBreaks_Brewing", joinColumns = { @JoinColumn(name = "brewingId") }, inverseJoinColumns = { @JoinColumn(name = "brewBreakId") })
	private Set<BrewBreak> breaks;
	
	@Column
	private String description;

	@Column 
	private String type;
	
	public Brewing(String name, String type, String description){
		this.name = name;
		this.type = type;
		this.description = description;
		hops = new HashSet<BrewHop>();
		malts = new HashSet<BrewMalt>();
		addons = new HashSet<BrewAddon>();
		breaks = new HashSet<BrewBreak>();		
	}
	protected Brewing(){
		
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private double ibu;
	
	private double color;
	
	public Set<BrewBreak> getBreaks() {
		return breaks;
	}

	public void setBreaks(Set<BrewBreak> breaks) {
		this.breaks = breaks;
	}

	public double getIbu() {
		return ibu;
	}

	public void setIbu(double iBU) {
		ibu = iBU;
	}

	public double getColor() {
		return color;
	}

	public void setColor(double color) {
		this.color = color;
	}

	public Set<BrewMalt> getMalts() {
		return malts;
	}

	public void setMalts(Set<BrewMalt> malts) {
		this.malts = malts;
	}

	public Set<BrewAddon> getAddons() {
		return addons;
	}

	public void setAddons(Set<BrewAddon> addons) {
		this.addons = addons;
	}

	public Long getBrewingId() {
		return brewingId;
	}

	public void setBrewingId(Long brewingId) {
		this.brewingId = brewingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<BrewHop> getHops() {
		return hops;
	}

	public void setHops(Set<BrewHop> hops) {
		this.hops = hops;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
