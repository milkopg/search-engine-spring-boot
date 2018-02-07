package net.icdpublishing.exercise2.myapp.customers.dao;

import net.icdpublishing.exercise2.myapp.charging.dao.ChargingDao;

public interface CustomChargingDao extends ChargingDao{
	public Integer getCustomerBalance(String email);
}
