package c.brew;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

	@ManyToMany(mappedBy = "brewing")
	private List<BrewHop> hops = new ArrayList<BrewHop>();

	@ManyToMany(mappedBy = "brewing")
	private List<BrewMalt> malts = new ArrayList<BrewMalt>();
	
	@ManyToMany(mappedBy = "brewing")
	private List<BrewAddon> addons = new ArrayList<BrewAddon>();
	
	@ManyToMany(mappedBy = "brewing")
	private List<BrewBreak> breaks = new ArrayList<BrewBreak>();
	
	@Column
	private String description;

	@Column 
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private double ibu;
	
	private double color;
	
	public List<BrewBreak> getBreaks() {
		return breaks;
	}

	public void setBreaks(List<BrewBreak> breaks) {
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

	public List<BrewMalt> getMalts() {
		return malts;
	}

	public void setMalts(List<BrewMalt> malts) {
		this.malts = malts;
	}

	public List<BrewAddon> getAddons() {
		return addons;
	}

	public void setAddons(List<BrewAddon> addons) {
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

	public List<BrewHop> getHops() {
		return hops;
	}

	public void setHops(List<BrewHop> hops) {
		this.hops = hops;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
