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

import c.beerSources.Malt;

@SuppressWarnings("serial")
@Entity
@Table(name = "BrewMalt")
public class BrewMalt implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long brewMaltId;

	@ManyToMany(mappedBy = "malts")
	private Set<Brewing> brewing = new HashSet<Brewing>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "maltId", nullable = false)
	private Malt malt;
	
	@Column
	private double quantity;
	
	public BrewMalt(Malt malt, double quantity){
		this.malt = malt;
		this.quantity = quantity;
	}
	
	protected BrewMalt(){}
	
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

	public Set<Brewing> getBrewing() {
		return brewing;
	}

	public void setBrewing(Set<Brewing> brewing) {
		this.brewing = brewing;
	}
	
}
