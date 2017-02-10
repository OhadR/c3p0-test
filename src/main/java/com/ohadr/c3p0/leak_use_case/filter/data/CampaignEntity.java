package com.ohadr.c3p0.leak_use_case.filter.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang3.StringUtils;
import com.ohadr.c3p0.leak_use_case.service.ServiceEntity;

/**
 * Contains data about campaign.
 * 
 * @author Yuriy Stul
 * @author vladimirp
 * @author Maxim Brener
 */
@Entity
@Table(name = "CAMPAIGNS", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME" }))
public class CampaignEntity implements Serializable, Comparable<CampaignEntity>
{
	/**
	 * Unique id of the entity.
	 */
	private static final long serialVersionUID = 6861716570336575610L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CAMPAIGN_ID", nullable = false)
	private Long campaignId;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = true)
	private CampaignType type;

	@Column(name = "PARENT_ID", nullable = true)
	private Long parentId;

	@Column(name = "START_DATE", nullable = true)
	private Date startDate;

	@Column(name = "END_DATE", nullable = true)
	private Date endDate;

	@Column(name = "ACTIVE", nullable = false)
	private Boolean active;

	@ManyToOne
	@JoinColumn(name = "SERVICE_ID")
	private ServiceEntity service;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "campaign")
	private List<CampaignPropertiesEntity> campaignProperties;

	/**
	 * Initializes a new instance of the CampaignEntity class.
	 */
	public CampaignEntity()
	{

	}

	/**
	 * Initializes a new instance of the CampaignEntity class.
	 * 
	 * @param name
	 *            the campaign name.
	 * @param startDate
	 *            start date of the campaign, may be null.
	 * @param endDate
	 *            end date of the campaign, may be null.
	 * @param active
	 *            specifies whether the campaign is active.
	 * @param serviceEntity
	 */
	public CampaignEntity(String name, Date startDate, Date endDate, Boolean active, ServiceEntity serviceEntity)
	{
		this(name, startDate, endDate, active, null, serviceEntity);
	}

	/**
	 * Initializes a new instance of the CampaignEntity class.
	 * 
	 * @param name
	 *            the campaign name.
	 * @param startDate
	 *            start date of the campaign, may be null.
	 * @param endDate
	 *            end date of the campaign, may be null.
	 * @param active
	 *            specifies whether the campaign is active.
	 * @param type
	 *            campaign type {@link CampaignType}
	 * @param serviceEntity
	 */
	public CampaignEntity(String name, Date startDate, Date endDate, Boolean active, CampaignType type, ServiceEntity serviceEntity)
	{

		if (StringUtils.isEmpty(name))
		{
			throw new IllegalArgumentException("name is null or empty.");
		}

		if (active == null)
		{
			throw new IllegalArgumentException("active is null.");
		}

		if (serviceEntity == null)
		{
			throw new IllegalArgumentException("service Entity is null.");
		}

		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.active = active;
		this.type = type;
		this.service = serviceEntity;
	}

	/**
	 * Initializes a new instance of the CampaignEntity class.
	 * 
	 * @param campaignId
	 *            the campaign ID; null, if a new entity.
	 * @param name
	 *            the campaign name.
	 * @param startDate
	 *            start date of the campaign, may be null.
	 * @param endDate
	 *            end date of the campaign, may be null.
	 * @param active
	 *            specifies whether the campaign is active.
	 * @param serviceEntity
	 */
	public CampaignEntity(Long campaignId, String name, Date startDate, Date endDate, Boolean active, ServiceEntity serviceEntity)
	{
		this(name, startDate, endDate, active, serviceEntity);
		this.campaignId = campaignId;
	}

	/**
	 * @return the campaignId
	 */
	public Long getCampaignId()
	{
		return campaignId;
	}

	/**
	 * @param campaignId
	 *            the campaignId to set
	 */
	public void setCampaignId(Long campaignId)
	{
		this.campaignId = campaignId;
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
	 * @return the type
	 */
	public CampaignType getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(CampaignType type)
	{
		this.type = type;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId()
	{
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive()
	{
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(Boolean active)
	{
		this.active = active;
	}

	/**
	 * @return the service
	 */
	public ServiceEntity getService()
	{
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(ServiceEntity service)
	{
		this.service = service;
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

	public List<CampaignPropertiesEntity> getCampaignProperties()
	{
		return campaignProperties;
	}

	public void setCampaignProperties(List<CampaignPropertiesEntity> campaignProperties)
	{
		this.campaignProperties = campaignProperties;
	}

	public String getCampaignProperty(String propertyName)
	{
		String propertyValue = null;

		if (StringUtils.isEmpty(propertyName))
			return propertyValue;

		List<CampaignPropertiesEntity> campaignProperties = getCampaignProperties();
		if (campaignProperties != null)
		{
			for (CampaignPropertiesEntity campaignPropertiesEntity : campaignProperties)
			{
				if (propertyName.equals(campaignPropertiesEntity.getPropertyName()))
				{
					propertyValue = campaignPropertiesEntity.getPropertyValue();
				}
			}
		}
		return propertyValue;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("CampaignEntity [campaignId=");
		builder.append(campaignId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", type=");
		builder.append(type);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", active=");
		builder.append(active);
		builder.append(", service=");
		builder.append(service);
		// builder.append(", campaignProperties=");
		// builder.append(campaignProperties);
		builder.append("]");
		return builder.toString();
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
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((campaignId == null) ? 0 : campaignId.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		CampaignEntity other = (CampaignEntity) obj;
		if (active == null)
		{
			if (other.active != null)
				return false;
		}
		else if (!active.equals(other.active))
			return false;
		if (campaignId == null)
		{
			if (other.campaignId != null)
				return false;
		}
		else if (!campaignId.equals(other.campaignId))
			return false;
		if (description == null)
		{
			if (other.description != null)
				return false;
		}
		else if (!description.equals(other.description))
			return false;
		if (endDate == null)
		{
			if (other.endDate != null)
				return false;
		}
		else if (!endDate.equals(other.endDate))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (parentId == null)
		{
			if (other.parentId != null)
				return false;
		}
		else if (!parentId.equals(other.parentId))
			return false;
		if (service == null)
		{
			if (other.service != null)
				return false;
		}
		else if (!service.equals(other.service))
			return false;
		if (startDate == null)
		{
			if (other.startDate != null)
				return false;
		}
		else if (!startDate.equals(other.startDate))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public int compareTo(CampaignEntity o)
	{
		return this.campaignId.compareTo(o.campaignId);
	}

	public String getItemKey()
	{
		return this.getName();
	}
}
