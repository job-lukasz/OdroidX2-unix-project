package c.beerSources;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import c.brew.BrewBreak;

@SuppressWarnings("serial")
@Entity
@Table(name = "Break")
public class Break implements java.io.Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private double temp_low;

	@Column
	private double temp_high;
	
	@Column
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "_break")
	private List<BrewBreak> Brewing = new ArrayList<BrewBreak>();

	
	public Long getId() {
		return id;
	}

	public void setId(Long breakId) {
		this.id = breakId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTemp_low() {
		return temp_low;
	}

	public void setTemp_low(double temp_low) {
		this.temp_low = temp_low;
	}

	public double getTemp_high() {
		return temp_high;
	}

	public void setTemp_high(double temp_high) {
		this.temp_high = temp_high;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<BrewBreak> getBrewing() {
		return Brewing;
	}

	public void setBrewing(List<BrewBreak> brewing) {
		Brewing = brewing;
	}

	public Break(String name, double temp_low, double temp_high, String description) {
		this.name = name;
		this.temp_low = temp_low;
		this.temp_high = temp_high;
		this.description = description;
	}

	protected Break() {

	}
}
