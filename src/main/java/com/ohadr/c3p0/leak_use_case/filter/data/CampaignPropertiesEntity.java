package com.ohadr.c3p0.leak_use_case.filter.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CAMPAIGN_PROPERTIES")
public class CampaignPropertiesEntity implements Serializable
{
	private static final long serialVersionUID = 5247313909249612439L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "PROPERTY_NAME")
	private String propertyName;

	@Column(name = "PROPERTY_VALUE")
	private String propertyValue;
	
	@Column(name = "PROPERTY_DESCRIPTION")
	private String propertyDescription;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAMPAIGN_ID")
	private CampaignEntity campaign;

	public CampaignPropertiesEntity()
	{
	}

	public CampaignPropertiesEntity(String propertyName, String propertyValue)
	{
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}

	public String getPropertyValue()
	{
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue)
	{
		this.propertyValue = propertyValue;
	}

	public CampaignEntity getCampaign()
	{
		return campaign;
	}

	public void setCampaign(CampaignEntity campaign)
	{
		this.campaign = campaign;
	}

	public String getPropertyDescription()
	{
		return propertyDescription;
	}

	public void setPropertyDescription(String propertyDescription)
	{
		this.propertyDescription = propertyDescription;
	}
	
	@Override
	public String toString()
	{
		return "CampaignPropertiesEntity [id=" + id + ", propertyName=" + propertyName + ", propertyValue=" + propertyValue + "]";
	}

}
