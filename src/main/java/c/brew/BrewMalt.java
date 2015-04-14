package c.brew;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import c.beerSources.Malt;

@SuppressWarnings("serial")
@Entity
@Table(name = "BrewMalt")
public class BrewMalt implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long brewMaltId;

	@Column
	private String name;
	

	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BrewMalts_Brewing", joinColumns = { @JoinColumn(name = "brewingId") }, inverseJoinColumns = { @JoinColumn(name = "brewMaltId") })
	private Set<Brewing> brewing = new HashSet<Brewing>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maltId", nullable = false)
	private Malt malt;
	
	@Column
	private Date start;
	
	@Column
	private Date end;
	
	@Column
	private String description;
	
	@Column
	private double quantity;
	
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public Long getBrewMaltId() {
		return brewMaltId;
	}

	public void setBrewMaltId(Long brewMaltId) {
		this.brewMaltId = brewMaltId;
	}

	public Malt getMalt() {
		return malt;
	}

	public void setMalt(Malt malt) {
		this.malt = malt;
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
