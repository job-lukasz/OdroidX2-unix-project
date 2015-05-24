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

import c.brew.BrewMalt;

@SuppressWarnings("serial")
@Entity
@Table(name = "Malt")
public class Malt implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private double color;
	
	@Column
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "malt")
	private List<BrewMalt> Brewing = new ArrayList<BrewMalt>();

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Malt(String name, double color){
		this.name=name;
		this.color = color;
		description = "";
	}
	
	protected Malt(){
		
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getColor() {
		return color;
	}

	public void setColor(double color) {
		this.color = color;
	}
}
