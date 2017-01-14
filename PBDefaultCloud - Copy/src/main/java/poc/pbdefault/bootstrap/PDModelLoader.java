package poc.pbdefault.bootstrap;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import poc.pbdefault.domain.Factors;
import poc.pbdefault.domain.PDModel;
import poc.pbdefault.repositories.FactorRepository;
import poc.pbdefault.repositories.PDModelRepository;

@Component
public class PDModelLoader implements ApplicationListener<ContextRefreshedEvent> {

	
	@Autowired
	PDModelRepository pdModelRepository;

    @Autowired
    FactorRepository factorRepository;

    private Logger log = Logger.getLogger(PDModelLoader.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

/*    	Factors device1 = new Factors("device01", "fan","OFF");
    	Factors device2 = new Factors("device02", "light","OFF");
    	Factors device3 = new Factors("device03", "thermostat");
    	Factors device4 = new Factors("device04", "air conditioner");

    	factorRepository.save(device1);
    	factorRepository.save(device2);
    	factorRepository.save(device3);
    	factorRepository.save(device4);*/

    	List<PDModel> pdModels = new LinkedList<PDModel>();
    	pdModels.add(new PDModel(100,100,83.7,1,true,"Defaulter","0.80")); 
    	pdModelRepository.save(pdModels);
    }
}
