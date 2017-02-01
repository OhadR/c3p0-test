/**
 * Copyright (c) 2016, William Hill Online. All rights reserved
 */
package com.ohadr.c3p0.leak_use_case;

import java.util.Date;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ohadr.c3p0.leak_use_case.affiliate.AffiliateCampaignEntity;
import com.ohadr.c3p0.leak_use_case.filter.data.CampaignEntity;


@Component
public class AffiliateManager 
{
	private static Logger log = Logger.getLogger(AffiliateManager.class);
	/**
	 * UID for AffiliateManager
	 */
	public final static String UID = "affiliateManager";

	@Autowired
	private AffiliateDao dao;


	public AffiliateManager()
	{}



	private AffiliateEntity getAffiliate(final String affiliateName)
	{
		if (StringUtils.isEmpty(affiliateName))
		{
			throw new IllegalArgumentException("affiliateName is null or empty.");
		}
		AffiliateEntity result = dao.getAffiliate(affiliateName);
		return result;
	}


	public CampaignEntity getAffiliateActiveSignupCampaign(final String affiliateName)
	{
		AffiliateEntity affiliate = getAffiliate(affiliateName);
		if (affiliate == null)
		{
			String message = String.format("No affiliate with name %s was found.", affiliateName);
			log.error(message);
			throw new RuntimeException(message);
		}

		final Date date = new Date();

		Set<AffiliateCampaignEntity> camps = affiliate.getAffiliateCampaigns();		//leak is here
		for(AffiliateCampaignEntity ace : camps)
		{
			CampaignEntity ce = ace.getCampaign();
			if( isActiveCampaign4Signup(date, ce) )
				return ce;
		}

		return null;
	}
	
	private boolean isActiveCampaign4Signup(final Date date, final CampaignEntity campaign)
	{
		boolean active = campaign != null;
		active = campaign.getActive();

		if (active)
		{
			Date startDate = campaign.getStartDate();
			if (startDate != null)
			{
				active = date.after(startDate);
			}
		}

		if (active)
		{
			Date endDate = campaign.getEndDate();
			if (endDate != null)
			{
				active = date.before(endDate);
			}
		}
		return active;
	}

//TODO:
	public void caller()
	{
		CampaignEntity affiliateParentActiveCampaign = getAffiliateActiveSignupCampaign("affiliateName");
	}

}
