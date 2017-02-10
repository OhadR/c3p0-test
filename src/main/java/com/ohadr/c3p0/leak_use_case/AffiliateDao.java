package com.ohadr.c3p0.leak_use_case;

import javax.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class AffiliateDao extends BaseGenericDao<AffiliateEntity>
{
	public AffiliateEntity getAffiliate(final String affiliateName)
	{
		if (StringUtils.isEmpty(affiliateName))
		{
			throw new IllegalArgumentException("affiliateName is null or empty.");
		}
		TypedQuery<AffiliateEntity> query = getEntityManager().createNamedQuery("affiliateByName", AffiliateEntity.class);

		query.setParameter("affiliateName", affiliateName);

		return query.getSingleResult();
	}
}
