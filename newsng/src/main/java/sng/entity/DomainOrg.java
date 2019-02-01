package sng.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(catalog = "newsng", name = "domain_org")
public class DomainOrg {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String domain_name;

	private Integer org_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDomain_name() {
		return domain_name;
	}

	public void setDomain_name(String domain_name) {
		this.domain_name = domain_name;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

}