package Dao;

import java.util.ArrayList;


import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.query.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import model.Customer;

public class CustomerDAO {
	
	

	public ArrayList<Customer> getAllCustomers() {
		try(Session session = SessionUtil.getSession()){
		Query query = session.createQuery("from Customer");
		ArrayList<Customer> customers = (ArrayList<Customer>)query.list();
		session.close();
		return customers;
		}catch (HibernateException e) {
			 //e.printStackTrace();
			System.out.print("errokuy:"+e);
		}
		return null;
	}

	public boolean addCustomer(Customer c) {
		try {
			Session session = SessionUtil.getSession();
			Transaction tx = session.beginTransaction();
			tx.begin();
			session.save(c);
			tx.commit();
			session.close();
		} catch (TransactionException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Customer findCustomerbyname(String userName) {
		Session session = SessionUtil.getSession();
		Query cr = session.createQuery("from Customer where username="+userName);
		return (Customer) cr.list().get(0) ;
		}

	public Customer findCustomer(Customer c) {
		Session session = SessionUtil.getSession();
		Criteria cr = session.createCriteria(Customer.class);
		cr.add(Restrictions.eq("username", c.getUsername()));
		cr.add(Restrictions.eq("pwd", c.getPwd()));
		if(cr.list().size() == 0) return null ;
		return (Customer) cr.list().get(0);
		}


}
