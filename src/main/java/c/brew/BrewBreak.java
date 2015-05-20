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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import c.beerSources.Break;

@SuppressWarnings("serial")
@Entity
@Table(name = "BrewBreak")
public class BrewBreak implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long brewBreakId;

	@Column
	private String name;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BrewBreak_Brewing", joinColumns = { @JoinColumn(name = "brewingId") }, inverseJoinColumns = { @JoinColumn(name = "brewBreakId") })
	private Set<Brewing> brewing = new HashSet<Brewing>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "breakId", nullable = false)
	private Break _break;

	@Column
	private String description;
	
	public Long getBrewBreakId() {
		return brewBreakId;
	}

	public void setBrewBreakId(Long brewBreakId) {
		this.brewBreakId = brewBreakId;
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

	public Break get_break() {
		return _break;
	}

	public void set_break(Break _break) {
		this._break = _break;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
