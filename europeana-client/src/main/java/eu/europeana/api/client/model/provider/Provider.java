package eu.europeana.api.client.model.provider;

import com.google.gson.Gson;

/**
 * Class used to represent the provider objects returned by the providers API
 * see http://labs.europeana.eu/api/provider/#response
 * @author Sergiu Gordea 
 *
 */
public class Provider {
    private String identifier, country, name,
    acronym, altname, scope, domain, geolevel, role, website;
    
    protected String getIdentifier() {
		return identifier;
	}

	protected void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	protected String getCountry() {
		return country;
	}

	protected void setCountry(String country) {
		this.country = country;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected String getAcronym() {
		return acronym;
	}

	protected void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	protected String getAltname() {
		return altname;
	}

	protected void setAltname(String altname) {
		this.altname = altname;
	}

	protected String getScope() {
		return scope;
	}

	protected void setScope(String scope) {
		this.scope = scope;
	}

	protected String getGeolevel() {
		return geolevel;
	}

	protected void setGeolevel(String geolevel) {
		this.geolevel = geolevel;
	}

	protected String getRole() {
		return role;
	}

	protected void setRole(String role) {
		this.role = role;
	}

	protected String getWebsite() {
		return website;
	}

	protected void setWebsite(String website) {
		this.website = website;
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Provider 
			&& this.getIdentifier().equals((
					(Provider)obj).getIdentifier());
		
	}
}
