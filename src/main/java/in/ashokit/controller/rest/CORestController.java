package in.ashokit.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.service.COService;

@RestController
@RequestMapping("co-service")
public class CORestController {
	
	@Autowired
	private COService coService;

	@GetMapping("/co-status")
	public ResponseEntity<?> correspondence() {
		Map<String, Object> notifications = coService.sendNotification();
		return new ResponseEntity<Map<String, Object>>(notifications, HttpStatus.OK);
	}
}
