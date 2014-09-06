package com.abc.restc;

public class Country {
	
	String name;
	String capital;
	String region;
	String subregion;
	double population;
	double lat;
	double lng;
	double area;
	String timezones;
	String borders;
	String nativeName;
	String callingCodes;
	String currencies;
	String languages;
	String alpha2Code;
	
	public Country(){
		
	}
	
	public Country(String name,String capital,String region,String subregion,double population,double lat,
			double lng,double area,String timezones,String borders,String nativeName,String callingCodes,
			String currencies,String languages,String alpha2Code){
		this.name = name;
		this.capital = capital;
		this.region = region;
		this.subregion = subregion;
		this.population = population;
		this.lat = lat;
		this.lng = lng;
		this.area = area;
		this.timezones = timezones;
		this.borders = borders;
		this.nativeName = nativeName;
		this.callingCodes = callingCodes;
		this.currencies = currencies;
		this.languages = languages;
		this.alpha2Code = alpha2Code;
	}
	public String getName(){
		return this.name;
	}
	public String getCapital(){
		return this.capital;
	}
	public String getRegion(){
		return this.region;
	}
	public String getSubRegion(){
		return this.subregion;
	}
	public double getPopulation(){
		return this.population;
	}
	public double getLat(){
		return this.lat;
	}
	public double getLng(){
		return this.lng;
	}
	public double getArea(){
		return this.area;
	}
	public String getTimezones(){
		return this.timezones;
	}
	public String getBorders(){
		return this.borders;
	}
	public String getnativeName(){
		return this.nativeName;
	}
	public String getcallingCodes(){
		return this.callingCodes;
	}
	public String getCurrencies(){
		return this.currencies;
	}
	public String getLanguages(){
		return this.languages;
	}
	public String getalpha2Code(){
		return this.alpha2Code;
	}
	
	//set functions
	
	public void setName(String name){
		this.name = name;
	}
	public void setCapital(String capital){
		this.capital = capital;
	}
	public void setRegion(String region){
		this.region = region;
	}
	public void setSubRegion(String subregion){
		this.subregion = subregion;
	}
	public void setPopulation(double population){
		this.population = population;
	}
	public void setLat(double lat){
		this.lat = lat;
	}
	public void setLng(double lng){
		this.lng = lng;
	}
	public void setArea(double area){
		this.area = area;
	}
	public void setTimezones(String timezones){
		this.timezones = timezones;
	}
	public void setBorders(String borders){
		this.borders = borders;
	}
	public void setnativeName(String nativeName){
		this.nativeName = nativeName;
	}
	public void setcallingCodes(String callingCodes){
		this.callingCodes = callingCodes;
	}
	public void setCurrencies(String currencies){
		this.currencies = currencies;
	}
	public void setLanguages(String languages){
		this.languages = languages;
	}
	public void setalpha2Code(String alpha2Code){
		this.alpha2Code = alpha2Code;
	}
}
