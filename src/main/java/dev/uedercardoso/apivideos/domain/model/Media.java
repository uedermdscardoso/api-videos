package dev.uedercardoso.apivideos.domain.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema="test")
@Getter @Setter
public class Media implements Serializable {
	
	private static final long serialVersionUID = 4216608522357474463L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false, length=512)
	private String url;
	
	private Integer duration;
	
	@Column(nullable=false) //Preenchido automaticamente
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(nullable=false) //Preenchido automaticamente
	private Boolean deleted;
	
	public Media(){
		
	}

	public Media(String name, String url, Integer duration) {
		this.name = name;
		this.url = url;
		this.duration = duration;
		this.date = Calendar.getInstance().getTime();
		this.deleted = false;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
}
