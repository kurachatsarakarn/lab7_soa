package model;
// Generated Feb 20, 2024, 12:43:43 AM by Hibernate Tools 6.3.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Customer generated by hbm2java
 */
public class Customer implements java.io.Serializable {

	private Integer cusId;
	private String name;
	private String sername;
	private String username;
	private String pwd;
	private String userroles;
	private Set phonenumbers = new HashSet(0);

	public Customer() {
	}

	public Customer(String name, String sername, String username, String pwd, String userroles, Set phonenumbers) {
		this.name = name;
		this.sername = sername;
		this.username = username;
		this.pwd = pwd;
		this.userroles = userroles;
		this.phonenumbers = phonenumbers;
	}

	public Integer getCusId() {
		return this.cusId;
	}

	public void setCusId(Integer cusId) {
		this.cusId = cusId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSername() {
		return this.sername;
	}

	public void setSername(String sername) {
		this.sername = sername;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserroles() {
		return this.userroles;
	}

	public void setUserroles(String userroles) {
		this.userroles = userroles;
	}

	public Set getPhonenumbers() {
		return this.phonenumbers;
	}

	public void setPhonenumbers(Set phonenumbers) {
		this.phonenumbers = phonenumbers;
	}

}
