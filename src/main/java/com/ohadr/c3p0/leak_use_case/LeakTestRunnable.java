package com.ohadr.c3p0.leak_use_case;

import org.apache.log4j.Logger;
import com.ohadr.c3p0.leak_use_case.filter.data.CampaignEntity;

/**
 * this runnable runs in a loop (this flag becomes false) and keeps calling the AffiliateManager,
 * to try to simulate the DB-conn-pool leak
 * 
 * @author ohadr
 *
 */
public class LeakTestRunnable implements Runnable 
{
	private static Logger log = Logger.getLogger(LeakTestRunnable.class);

	private static final String POKER_STRATEGY_AFF_NAME = "pokerStrategy";

	private AffiliateManager affiliateManager;

	public LeakTestRunnable(AffiliateManager affiliateManager)
	{
		this.affiliateManager = affiliateManager;
	}
	
	@Override
	public void run() 
	{
		log.info("thread is running");

		CampaignEntity ce = affiliateManager.getAffiliateActiveSignupCampaign(POKER_STRATEGY_AFF_NAME);
		log.info("CampaignEntity: " + ce);
	}
}
