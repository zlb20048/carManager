package com.cars.simple.mode;

import com.cars.simple.service.persistence.IPersistProxy;


/**
 * 领域接口
 * @author shifeng
 *
 */
public interface Domain {
	
	long save(IPersistProxy persist);
	
	int edit(IPersistProxy persist);
	
	int remove(IPersistProxy persist);

}
