/**
 * Copyright (c) 2016, William Hill Online. All rights reserved
 */
package com.ohadr.c3p0.leak_use_case;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang3.StringUtils;
import com.ohadr.c3p0.leak_use_case.affiliate.AffiliateCampaignEntity;
import com.ohadr.c3p0.leak_use_case.affiliate.AffiliatePropertyEntity;
import com.ohadr.c3p0.leak_use_case.filter.data.CampaignEntity;

/**
 * Holds general data about an affiliate.
 * 
 * @author Yuriy Stul
 *
 */
@Entity
@Table(name = "AFFILIATES", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME" }) )
@NamedQueries(value = { @NamedQuery(name = "affiliateByName", query = "select a from AffiliateEntity a where a.name = :affiliateName") })
public class AffiliateEntity implements Serializable
{
	private static final long serialVersionUID = -20991134679077561L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AFFILIATE_ID", unique = true, nullable = false)
	private Long affiliateId;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION", nullable = true)
	private String description;

	@Column(name = "INSERT_DATE")
	private Date insertDate;

	@Column(name = "UPDATE_DATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "affiliate")
	@MapKey(name = "name")
	private Map<String, AffiliatePropertyEntity> affiliateProperties = new HashMap<String, AffiliatePropertyEntity>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "affiliate")
	private Set<AffiliateCampaignEntity> affiliateCampaigns = new HashSet<AffiliateCampaignEntity>();

	/**
	 * Initializes a new instance of the AffiliateEntity class.
	 */
	public AffiliateEntity()
	{
	}

	/**
	 * Initializes a new instance of the AffiliateEntity class.
	 * 
	 * @param name
	 *            specifies the name.
	 * @param description
	 *            specifies the description; optional.
	 */
	public AffiliateEntity(final String name, final String description)
	{
		if (StringUtils.isEmpty(name))
		{
			throw new IllegalArgumentException("name is null or empty.");
		}

		this.name = name;
		this.description = description;

		insertDate = new Date();
	}

	/**
	 * @return the affiliateId
	 */
	public Long getAffiliateId()
	{
		return affiliateId;
	}

	/**
	 * @param affiliateId
	 *            the affiliateId to set
	 */
	public void setAffiliateId(Long affiliateId)
	{
		this.affiliateId = affiliateId;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the insertDate
	 */
	public Date getInsertDate()
	{
		return insertDate;
	}

	/**
	 * @param insertDate
	 *            the insertDate to set
	 */
	public void setInsertDate(Date insertDate)
	{
		this.insertDate = insertDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate()
	{
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	/**
	 * @return the affiliateProperties
	 */
	public Map<String, AffiliatePropertyEntity> getAffiliateProperties()
	{
		return affiliateProperties;
	}

	/**
	 * @param affiliateProperties
	 *            the affiliateProperties to set
	 */
	public void setAffiliateProperties(Map<String, AffiliatePropertyEntity> affiliateProperties)
	{
		this.affiliateProperties = affiliateProperties;
	}

	/**
	 * @return the collections of the affiliate campaigns
	 */
	public Set<AffiliateCampaignEntity> getAffiliateCampaigns()
	{
		return affiliateCampaigns;
	}

	/**
	 * @param affiliateCampaigns
	 *            the collections of the affiliate campaigns to set
	 */
	public void setAffiliateCampaigns(Set<AffiliateCampaignEntity> affiliateCampaigns)
	{
		this.affiliateCampaigns = affiliateCampaigns;
	}

	/**
	 * Adds a new or updates existing Affiliate Property.
	 * 
	 * @param affiliatePropertyEntity
	 *            specifies the new Affiliate Property.
	 */
	public void addAffiliateProperty(final AffiliatePropertyEntity affiliatePropertyEntity)
	{
		if (affiliatePropertyEntity == null)
		{
			throw new IllegalArgumentException("affiliatePropertyEntity is null.");
		}

		// Check, if a property with same name exists already.
		if (getAffiliateProperties().get(affiliatePropertyEntity.getName()) != null)
		{
			throw new IllegalArgumentException("Property with name " + affiliatePropertyEntity.getName() + " exists already.");
		}

		if (!this.equals(affiliatePropertyEntity.getAffiliate()))
		{
			throw new IllegalArgumentException("Property is connected with other AffiliateEntity.");
		}
		getAffiliateProperties().put(affiliatePropertyEntity.getName(), affiliatePropertyEntity);
	}

	/**
	 * Adds a new campaign to the collection of the campaigns.
	 * 
	 * <p>
	 * <strong> Only 'parent' campaign (campaign with parent ID equal to null)
	 * may be added! </strong>
	 * 
	 * @param campaignEntity
	 *            the campaign to add or update.
	 */
	public void addCampaign(final CampaignEntity campaignEntity)
	{
		if (campaignEntity == null)
		{
			throw new IllegalArgumentException("campaignEntity is null.");
		}

		if (campaignEntity.getParentId() != null)
		{
			final String message = String.format("The campaign with name %s is not parent campaign!", campaignEntity.getName());
			throw new IllegalArgumentException(message);
		}

		// Check, if a property with same name exists already.
		if (getAffiliateCampaigns().stream().filter(x -> x.getCampaign().getName().equals(campaignEntity.getName())).findAny().isPresent())
		{
			throw new IllegalArgumentException("Campaign with name " + campaignEntity.getName() + " exists already.");
		}

		getAffiliateCampaigns().add(new AffiliateCampaignEntity(this, campaignEntity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AffiliateEntity other = (AffiliateEntity) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String affiliateCampaignsText = "null";
		affiliateCampaignsText = affiliateCampaigns.stream().map(AffiliateCampaignEntity::getCampaign).map(CampaignEntity::getName).collect(Collectors.toList()).toString();

		return "AffiliateEntity [affiliateId=" + affiliateId + ", name=" + name + ", description=" + description + ", insertDate=" + insertDate + ", updateDate=" + updateDate + ", affiliateProperties=" + affiliateProperties + ", affiliateCampaigns="
				+ affiliateCampaignsText + "]";
	}
}
