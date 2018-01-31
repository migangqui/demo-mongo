package com.migangqui.example.props;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService implements IPropertiesService {
	
	@Autowired
	private Environment env;
	
	@Override
	public boolean insertData() {
		return env.getProperty("data.insert", boolean.class);
	}
	
	@Override
	public boolean deleteData() {
		return env.getProperty("data.delete", boolean.class);
	}
	
	@Override
	public int getTotalData() {
		return env.getProperty("data.quantity.total", int.class);
	}
	
	@Override
	public int getFoundData() {
		return env.getProperty("data.quantity.found", int.class);
	}

}
