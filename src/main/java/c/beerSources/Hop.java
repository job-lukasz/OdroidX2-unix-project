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

import c.brew.BrewHop;

@SuppressWarnings("serial")
@Entity
@Table(name = "Hop")
public class Hop implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private double acid;
	
	@Column
	private String description;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "hop")
	private List<BrewHop> Brewing = new ArrayList<BrewHop>();

	public Hop(String name, double acid){
		this.name=name;
		this.acid = acid;
		this.description = "";
	}
	
	protected Hop(){
		
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

	public double getAcid() {
		return acid;
	}

	public void setAcid(double acid) {
		this.acid = acid;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
