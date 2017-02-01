package com.ohadr.c3p0.leak_use_case.service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author tomerb
 *
 */
@Entity
@Table(name = "SERVICES", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
public class ServiceEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2454288001877989564L;

	private Long serviceId;

	private String name;

	private String description;

//	private Set<DepartmentEntity> departments = new HashSet<DepartmentEntity>();

	public ServiceEntity()
	{
		// Default contractor
	}

	/**
	 * @param serviceName
	 * @param departments
	 */
	/**
	 * @param name
	 */
	public ServiceEntity(String name)
	{
		this.name = name;
	}

	/**
	 * @return the serviceId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SERVICE_ID", unique = true, nullable = false)
	public Long getServiceId()
	{
		return serviceId;
	}

	/**
	 * @param serviceId
	 *            the serviceId to set
	 */
	public void setServiceId(Long serviceId)
	{
		this.serviceId = serviceId;
	}

	/**
	 * @return the name
	 */
	@Column(name = "NAME", nullable = false, unique = true)
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
	@Column(name = "DESCRIPTION", nullable = true)
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
	 * @return the departments
	 */

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
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
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
		ServiceEntity other = (ServiceEntity) obj;
		if (serviceId == null)
		{
			if (other.serviceId != null)
				return false;
		}
		else if (!serviceId.equals(other.serviceId))
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
		StringBuilder builder = new StringBuilder();
		builder.append("ServiceEntity [id=");
		builder.append(serviceId);
		builder.append(", serviceName=");
		builder.append(name);
		builder.append(", serviceDescription=");
		builder.append(description);
//		builder.append(", departmentsSize=");
//		builder.append(departments.size());
		builder.append("]");
		return builder.toString();
	}

}
