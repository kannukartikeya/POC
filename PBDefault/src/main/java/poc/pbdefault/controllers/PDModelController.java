package poc.pbdefault.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonObject;

import poc.pbdefault.domain.Factors;
import poc.pbdefault.domain.PDModel;
import poc.pbdefault.mqtt.MqttPublishSubscribeUtility;
import poc.pbdefault.repositories.FactorRepository;
import poc.pbdefault.repositories.PDModelRepository;

@Controller
@PreAuthorize("hasAuthority('USER')")
public class PDModelController {

	@Autowired
	PDModelRepository repository;

	@Autowired
	FactorRepository deviceRepository;

	@RequestMapping("/room/{id}")
	public String room(@PathVariable Long id, Model model) {
		model.addAttribute("room", repository.findOne(id));
		model.addAttribute("devices", deviceRepository.findAll());
		return "room";
	}
	
	@RequestMapping("/device/{id}")
	public String device(@PathVariable Long id, Model model) {
		//model.addAttribute("developer", repository.findOne(id));
		model.addAttribute("device", deviceRepository.findOne(id));
		return "device";
	}
	

	@RequestMapping(value="/pdModels",method=RequestMethod.GET)
	public String pdList(Model model) {
		model.addAttribute("pdModels", repository.findAll());
		return "pdModels";
	}

	@RequestMapping(value="/pdModels",method=RequestMethod.POST)
	public String roomAdd(@RequestParam int last_fico_range_high, @RequestParam int last_fico_range_low,@RequestParam float revol_util, @RequestParam int inq_last_6mths, @RequestParam boolean is_rent, Model model) {
		PDModel pdModel = new PDModel();
		pdModel.setLast_fico_range_high(last_fico_range_high);
		pdModel.setLast_fico_range_low(last_fico_range_low);
		pdModel.setRevol_util(revol_util);
		pdModel.setInq_last_6mths(inq_last_6mths);
		pdModel.setIs_rent(is_rent);
		
		repository.save(pdModel);


		
		/*model.addAttribute("pdModel", pdModel);
		model.addAttribute("devices", deviceRepository.findAll());*/
		return "redirect:/pdModels/";
	}

/*	@RequestMapping(value="/room/{id}/devices", method=RequestMethod.POST)
	public String roomAddDevice(@PathVariable Long id,@RequestParam String label, @RequestParam String description,@RequestParam String status,Model model) {
		//Skill skill = skillRepository.findOne(skillId);
		PDModel room = repository.findOne(id);
		Factors device = new Factors();
		device.setLabel(label);
		device.setDescription(description);
		device.setStatus(status);
		
		deviceRepository.save(device);
		


		List<Factors> deviceList = deviceRepository.findByLabel(label);
		
		if (room != null) {
			room.getDevices().addAll(deviceList);
			
			repository.save(room);
			model.addAttribute("room", repository.findOne(id));
			model.addAttribute("devices", deviceRepository.findAll());
			return "redirect:/room/" + room.getId();
		}

		model.addAttribute("rooms", repository.findAll());
		return "redirect:/rooms";
	}*/
	
	
	@RequestMapping(value="/device/{id}/update", method=RequestMethod.POST)
	public String updateDevice(@PathVariable Long id,@RequestParam String status,Model model) {
		//Skill skill = skillRepository.findOne(skillId);
		Factors device = deviceRepository.findOne(id);
		/*skill.setLabel(label);
		skill.setDescription(description);*/
		device.setStatus(status);
		
		deviceRepository.save(device);
		
		JsonObject payload = new JsonObject();
		payload.addProperty("deviceLabel", device.getLabel());
        payload.addProperty("deviceDescription", device.getDescription());
        payload.addProperty("deviceStatus", status);
        /*payload.addProperty("dateTimeStamp",  ledModel.getDateTimeStamp());
        payload.addProperty("blinkCounter", ledModel.getBlinkCounter());*/
		
        MqttPublishSubscribeUtility mqttPublishSubscribeUtility = new MqttPublishSubscribeUtility();
        mqttPublishSubscribeUtility.mqttConnectNPublishNSubscribe(payload);
		
		device = deviceRepository.findOne(id);
		
		model.addAttribute("device", device);
		return "redirect:/device/" + device.getId();
		
	}
	
/*	
	@RequestMapping(value="/developer/{id}/skills", method=RequestMethod.POST)
	public String developersAddSkill(@PathVariable Long id, @RequestParam Long skillId, Model model) {
		Skill skill = skillRepository.findOne(skillId);
		Developer developer = repository.findOne(id);

		if (developer != null) {
			if (!developer.hasSkill(skill)) {
				developer.getSkills().add(skill);
			}
			repository.save(developer);
			model.addAttribute("developer", repository.findOne(id));
			model.addAttribute("skills", skillRepository.findAll());
			return "redirect:/developer/" + developer.getId();
		}

		model.addAttribute("developers", repository.findAll());
		return "redirect:/developers";
	}*/

}
