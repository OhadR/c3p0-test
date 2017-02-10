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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import com.ohadr.c3p0.leak_use_case.AffiliateEntity;
import com.ohadr.c3p0.leak_use_case.filter.data.CampaignEntity;

/**
 * Links affiliate with campaigns.
 * 
 * @author Yuriy Stul
 *
 */
@Entity
@Table(name = "AFFILIATE_CAMPAIGNS", uniqueConstraints = @UniqueConstraint(columnNames = { "AFFILIATE_ID", "CAMPAIGN_ID" }) )
public class AffiliateCampaignEntity implements Serializable
{
	private static final long serialVersionUID = 2575292789399144288L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AFFILIATE_CAMPAIGN_ID", unique = true, nullable = false)
	private Long affiliateCampaignId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AFFILIATE_ID")
	private AffiliateEntity affiliate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAMPAIGN_ID")
	private CampaignEntity campaign;

	@Column(name = "INSERT_DATE")
	private Date insertDate;

	@Column(name = "UPDATE_DATE", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	/**
	 * Initializes a new instance of the AffiliateCampaignEntity.
	 */
	protected AffiliateCampaignEntity()
	{
	}

	/**
	 * Initializes a new instance of the AffiliateCampaignEntity.
	 * 
	 * @param affiliate
	 *            the affiliate
	 * @param campaign
	 *            the campaign
	 */
	public AffiliateCampaignEntity(final AffiliateEntity affiliate, final CampaignEntity campaign)
	{
		if (affiliate == null)
		{
			throw new IllegalArgumentException("affiliate is null.");
		}
		if (campaign == null)
		{
			throw new IllegalArgumentException("campaign is null.");
		}

		this.affiliate = affiliate;
		this.campaign = campaign;
	}

	@PrePersist
	void preInsert() {
		insertDate = new Date();
	}
	
	/**
	 * @return the affiliateCampaignId
	 */
	public Long getAffiliateCampaignId()
	{
		return affiliateCampaignId;
	}

	/**
	 * @param affiliateCampaignId the affiliateCampaignId to set
	 */
	public void setAffiliateCamapignId(Long affiliateCampaignId)
	{
		this.affiliateCampaignId = affiliateCampaignId;
	}

	/**
	 * @return the affiliate
	 */
	public AffiliateEntity getAffiliate()
	{
		return affiliate;
	}

	/**
	 * @param affiliate the affiliate to set
	 */
	public void setAffiliate(AffiliateEntity affiliate)
	{
		this.affiliate = affiliate;
	}

	/**
	 * @return the campaign
	 */
	public CampaignEntity getCampaign()
	{
		return campaign;
	}

	/**
	 * @param campaign the campaign to set
	 */
	public void setCampaign(CampaignEntity campaign)
	{
		this.campaign = campaign;
	}

	/**
	 * @return the insertDate
	 */
	public Date getInsertDate()
	{
		return insertDate;
	}

	/**
	 * @param insertDate the insertDate to set
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
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((affiliate == null) ? 0 : affiliate.hashCode());
		result = prime * result + ((campaign == null) ? 0 : campaign.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		AffiliateCampaignEntity other = (AffiliateCampaignEntity) obj;
		if (affiliate == null)
		{
			if (other.affiliate != null)
				return false;
		}
		else if (!affiliate.equals(other.affiliate))
			return false;
		if (campaign == null)
		{
			if (other.campaign != null)
				return false;
		}
		else if (!campaign.equals(other.getCampaign()))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "AffiliateCampaignEntity [affiliate=" + affiliate.getName() + ", campaign=" + (campaign == null? "null":campaign.getName()) + ", insertDate=" + insertDate + ", updateDate=" + updateDate + "]";
	}
}
