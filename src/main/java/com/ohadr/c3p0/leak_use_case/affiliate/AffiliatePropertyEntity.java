package com.ohadr.c3p0.leak_use_case.affiliate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang3.StringUtils;
import com.ohadr.c3p0.leak_use_case.AffiliateEntity;

@Entity
@Table(name = "AFFILIATE_PROPERTIES", uniqueConstraints = @UniqueConstraint(columnNames = { "AFFILIATE_ID", "NAME" }) )
public class AffiliatePropertyEntity implements Serializable
{
	private static final long serialVersionUID = 7292399253433538978L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AFFILIATE_PROPERTY_ID", unique = true, nullable = false)
	private Long affiliatePropertyId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "INSERT_DATE")
	private Date insertDate;

	@Column(name = "UPDATE_DATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AFFILIATE_ID")
	private AffiliateEntity affiliate;

	/**
	 * Initializes a new instance of the AffiliatePropertyEntity class.
	 */
	protected AffiliatePropertyEntity()
	{

	}

	/**
	 * Initializes a new instance of the AffiliatePropertyEntity class.
	 * 
	 * @param affiliate
	 *            the affiliate
	 * @param name
	 *            the parameter name
	 * @param value
	 *            the parameter value
	 */
	public AffiliatePropertyEntity(final AffiliateEntity affiliate, final String name, final String value)
	{
		if (affiliate == null)
		{
			throw new IllegalArgumentException("affiliate is null");
		}
		if (StringUtils.isEmpty(name))
		{
			throw new IllegalArgumentException("name is null or empty");
		}
		if (StringUtils.isEmpty(value))
		{
			throw new IllegalArgumentException("value is null or empty");
		}

		this.affiliate = affiliate;
		this.name = name;
		this.value = value;

		insertDate = new Date();
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
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
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
	 * @return the affiliatePropertyId
	 */
	public Long getAffiliatePropertyId()
	{
		return affiliatePropertyId;
	}

	/**
	 * @param affiliatePropertyId
	 *            the affiliatePropertyId to set
	 */
	public void setAffiliatePropertyId(Long affiliatePropertyId)
	{
		this.affiliatePropertyId = affiliatePropertyId;
	}

	/**
	 * @return the affiliate
	 */
	public AffiliateEntity getAffiliate()
	{
		return affiliate;
	}

	/**
	 * @param affiliate
	 *            the affiliate to set
	 */
	public void setAffiliate(AffiliateEntity affiliate)
	{
		this.affiliate = affiliate;
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
		result = prime * result + ((affiliatePropertyId == null) ? 0 : affiliatePropertyId.hashCode());
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
		AffiliatePropertyEntity other = (AffiliatePropertyEntity) obj;
		if (affiliatePropertyId == null)
		{
			if (other.affiliatePropertyId != null)
				return false;
		}
		else if (!affiliatePropertyId.equals(other.affiliatePropertyId))
			return false;
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
		final String affiliateName = affiliate == null ? "" : affiliate.getName();
		return "AffiliatePropertyEntity [affiliatePropertyId=" + affiliatePropertyId + ", name=" + name + ", value=" + value + ", insertDate=" + insertDate + ", updateDate=" + updateDate + ", affiliate=" + affiliateName + "]";
	}

}
