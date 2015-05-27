package c.brew;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import c.beerSources.Yeast;

@SuppressWarnings("serial")
@Entity
@Table(name = "Brewing")
public class Brewing implements java.io.Serializable {
	private static DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long brewingId;

	@Column
	private String name;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BrewHops_Brewing", joinColumns = { @JoinColumn(name = "brewingId") }, inverseJoinColumns = { @JoinColumn(name = "brewHopId") })
	private Set<BrewHop> hops;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BrewMalts_Brewing", joinColumns = { @JoinColumn(name = "brewingId") }, inverseJoinColumns = { @JoinColumn(name = "brewMaltId") })
	private Set<BrewMalt> malts;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BrewAddons_Brewing", joinColumns = { @JoinColumn(name = "brewingId") }, inverseJoinColumns = { @JoinColumn(name = "brewAddonId") })
	private Set<BrewAddon> addons;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BrewBreaks_Brewing", joinColumns = { @JoinColumn(name = "brewingId") }, inverseJoinColumns = { @JoinColumn(name = "brewBreakId") })
	private Set<BrewBreak> breaks;

	@Column
	private String description;

	@Column
	private String type;

	@Column
	private Date date;

	@Column
	private double startDensity;

	@Column
	private double endDensity;

	private double alkohol;

	private double efficiency;

	private double ibu;

	private double color;
	// Fermentacja

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "YeastId", nullable = true)
	private Yeast yeast;

	@Column
	private Date yeastAddDate;

	@Column
	private String yeastOrigin;

	@Column
	private double fermentationStartVolume;

	@Column
	private double fermantationTemperature;

	@Column
	private Date silentFermantationDate;

	@Column
	private double silentFemrantationTemperature;

	// Rozlew
	@Column
	private Date bottlingDate;

	@Column
	private String referementationSource;

	@Column
	private double referemntationSourceVolume;

	@Column
	private double endVolume;

	public Brewing(String name, String type, String description) {
		this.name = name;
		this.type = type;
		this.description = description;
		hops = new HashSet<BrewHop>();
		malts = new HashSet<BrewMalt>();
		addons = new HashSet<BrewAddon>();
		breaks = new HashSet<BrewBreak>();
	}

	protected Brewing() {

	}

	public double getAlkohol() {
		return alkohol;
	}

	public void setAlkohol(double alkohol) {
		this.alkohol = alkohol;
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}

	public String getDate() {
		if(date!=null){
			return df.format(date);
		}
		return "";
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getStartDensity() {
		return startDensity;
	}

	public void setStartDensity(double startDensity) {
		this.startDensity = startDensity;
	}

	public double getEndDensity() {
		return endDensity;
	}

	public void setEndDensity(double endDensity) {
		this.endDensity = endDensity;
	}

	public Yeast getYeast() {
		return yeast;
	}

	public void setYeast(Yeast yeast) {
		this.yeast = yeast;
	}

	public String getYeastAddDate() {
		if(yeastAddDate!=null){
			return df.format(yeastAddDate);
		}
		return "";
	}

	public void setYeastAddDate(Date yeastAddDate) {
		this.yeastAddDate = yeastAddDate;
	}

	public String getYeastOrigin() {
		return yeastOrigin;
	}

	public void setYeastOrigin(String yeastOrigin) {
		this.yeastOrigin = yeastOrigin;
	}

	public double getFermentationStartVolume() {
		return fermentationStartVolume;
	}

	public void setFermentationStartVolume(double fermentationStartVolume) {
		this.fermentationStartVolume = fermentationStartVolume;
	}

	public double getFermantationTemperature() {
		return fermantationTemperature;
	}

	public void setFermantationTemperature(double fermantationTemperature) {
		this.fermantationTemperature = fermantationTemperature;
	}

	public String getSilentFermantationDate() {
		if(silentFermantationDate!=null){
			return df.format(silentFermantationDate);
		}
		return "";
	}

	public void setSilentFermantationDate(Date silentFermantationDate) {
		this.silentFermantationDate = silentFermantationDate;
	}

	public double getSilentFemrantationTemperature() {
		return silentFemrantationTemperature;
	}

	public void setSilentFemrantationTemperature(double silentFemrantationTemperature) {
		this.silentFemrantationTemperature = silentFemrantationTemperature;
	}

	public String getBottlingDate() {
		if(bottlingDate!=null){
			return df.format(bottlingDate);
		}
		return "";
	}

	public void setBottlingDate(Date bottlingDate) {
		this.bottlingDate = bottlingDate;
	}

	public String getReferementationSource() {
		return referementationSource;
	}

	public void setReferementationSource(String referementationSource) {
		this.referementationSource = referementationSource;
	}

	public double getReferemntationSourceVolume() {
		return referemntationSourceVolume;
	}

	public void setReferemntationSourceVolume(double referemntationSourceVolume) {
		this.referemntationSourceVolume = referemntationSourceVolume;
	}

	public double getEndVolume() {
		return endVolume;
	}

	public void setEndVolume(double endVolume) {
		this.endVolume = endVolume;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
