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

import c.beerSources.Break;

@SuppressWarnings("serial")
@Entity
@Table(name = "BrewBreak")
public class BrewBreak implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@ManyToMany(mappedBy = "breaks")
	private Set<Brewing> brewing = new HashSet<Brewing>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "breakId", nullable = false)
	private Break _break;
	
	@Column
	private int startMinute;
	
	@Column
	private int duration;
	
	public int getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(int startTime) {
		this.startMinute = startTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long brewBreakId) {
		this.id = brewBreakId;
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
	
	public BrewBreak(Break _break, int startMinute, int duration){
		this._break=_break;
		this.startMinute=startMinute;
		this.duration=duration;
	}
	
	protected BrewBreak(){
		
	}
}
