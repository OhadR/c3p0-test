package com.ohadr.c3p0.leak_use_case;

import static org.hibernate.criterion.Restrictions.eq;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

/**
 * Supplies base generic DAO functionality.
 * 
 */
@Transactional
public abstract class BaseGenericDao<T>
{

	private final Class<T> type;

	@PersistenceContext
	protected EntityManager entityManager;

	private static Logger log = Logger.getLogger(BaseGenericDao.class);

	@SuppressWarnings("unchecked")
	public BaseGenericDao()
	{
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		type = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	/**
	 * Gets the entity manager.
	 * 
	 * @return the entity manager.
	 */
	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	/**
	 * Sets the entity manager.
	 * 
	 * @param entityManager
	 *            the entity manager to set.
	 */
	public void setEntityManager(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	/**
	 * Returns an object by ID.
	 * 
	 * @param id
	 *            object ID in the repository.
	 * @return the object for specified ID, if succeeded; null - otherwise.
	 */
	public T get(Long id)
	{
		if (id == null)
		{
			throw new NullPointerException("id is null.");
		}

		T theObject;
		try
		{
			theObject = getEntityManager().find(type, id);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}

		return theObject;
	}

	/**
	 * Returns all objects.
	 * 
	 * @return all objects.
	 */
	@SuppressWarnings("unchecked")
	public Collection<T> getAll()
	{
		Query query = getEntityManager().createQuery("select c from " + type.getName() + " c");
		List<T> theObjects;

		try
		{
			theObjects = query.getResultList();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}

		return theObjects;
	}

	@SuppressWarnings("unchecked")
	public Collection<T> getAllOrdered(String orderBy, boolean desc)
	{
		// in case orderdBy is empty get all without order.
		if (StringUtils.isEmpty(orderBy))
		{
			return getAll();
		}
		else
		{
			// create order by query add order by filed and descending.
			String descStr = desc ? "desc" : "asc";
			String queryStr = "select c from " + type.getName() + " c order by c." + orderBy + " " + descStr;
			Query query = getEntityManager().createQuery(queryStr);
			try
			{
				return query.getResultList();
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	/**
	 * Adds new object.
	 * 
	 * @param t
	 *            the object to add.
	 */
	public void add(T t)
	{
		try
		{
			getEntityManager().persist(t);
			// need to remove the flush when we move to transactional db
			// (InnoDB)
			// getEntityManager().flush();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * delete exist object.
	 * 
	 * @param t
	 *            the object to delete.
	 */
	public void delete(T t)
	{
		try
		{
			getEntityManager().remove(t);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * delete exist object by single primary key.
	 * 
	 * @param id
	 *            the object to delete.
	 */
	public void delete(Long id)
	{
		try
		{
			T t = get(id);
			getEntityManager().remove(t);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Updates an object.
	 * 
	 * @param t
	 *            the object to update.
	 * @return
	 */
	public T update(T t)
	{
		try
		{
			return getEntityManager().merge(t);
			// need to remove the flush when we move to transactional db
			// (InnoDB)
			// getEntityManager().flush();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public T get(Map<String, Object> properties)
	{

		Session session = (Session) entityManager.getDelegate();
		Criteria criteria = session.createCriteria(type);

		for (Map.Entry<String, Object> entry : properties.entrySet())
		{
			criteria.add(eq(entry.getKey(), entry.getValue()));
		}

		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(Map<String, Object> properties)
	{

		Session session = (Session) entityManager.getDelegate();
		Criteria criteria = session.createCriteria(type);

		for (Map.Entry<String, Object> entry : properties.entrySet())
		{
			criteria.add(eq(entry.getKey(), entry.getValue()));
		}

		return criteria.list();
	}

	public Collection<T> bulkAdd(Collection<T> entities)
	{
		final List<T> savedEntities = new ArrayList<T>(entities.size());
		for (T t : entities)
		{
			try
			{
				entityManager.persist(t);
				savedEntities.add(t);
			}
			catch (Exception e)
			{
				// entityManager.clear();
				log.error("Failed to add item. " + t);
			}

		}

		 entityManager.flush();
		 entityManager.clear();

		return savedEntities;
	}

	public Collection<T> bulkUpdate(Collection<T> entities)
	{

		final List<T> savedEntities = new ArrayList<T>(entities.size());
		for (T t : entities)
		{
			try
			{
				T item = entityManager.merge(t);
				savedEntities.add(item);
			}
			catch (Exception e)
			{
				// entityManager.clear();
				log.error("Failed to update item. " + t);
			}

		}

		 entityManager.flush();
		 entityManager.clear();

		return savedEntities;

	}

}
