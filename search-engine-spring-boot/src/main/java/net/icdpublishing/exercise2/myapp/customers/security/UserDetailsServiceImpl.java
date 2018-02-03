package net.icdpublishing.exercise2.myapp.customers.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.icdpublishing.exercise2.myapp.customers.dao.CustomerDao;
import net.icdpublishing.exercise2.myapp.customers.dao.CustomerNotFoundException;
import net.icdpublishing.exercise2.myapp.customers.domain.Customer;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    private CustomerDao customerDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Customer customer = null;
    	Set<GrantedAuthority> grantedAuthorities = null;
        try {
			customer = customerDao.findCustomerByEmailAddress(username);
			grantedAuthorities = new HashSet<>();
		    grantedAuthorities.add(new SimpleGrantedAuthority(customer.getEmailAddress()));
		} catch (CustomerNotFoundException e) {
			return null;
		}
        return new org.springframework.security.core.userdetails.User(customer.getEmailAddress(), customer.getEmailAddress(), grantedAuthorities);
    }
}
