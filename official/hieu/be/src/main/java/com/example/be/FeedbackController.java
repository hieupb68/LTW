package com.example.be;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class FeedbackController {
	FeedbackDAO fbDAO = new FeedbackDAO();
	
	@PostMapping("/upload-fb")
	public ResponseEntity<String> uploadFb(@RequestParam int id_user, @RequestParam int id_book, @RequestParam int rate, @RequestParam String message) {
		try {
			Feedback fb = new Feedback();
			fb.setId_user(id_user);
			fb.setId_book(id_book);
			fb.setRate(rate);
			fb.setMessage(message);
			fb.setTime(new Date());
			
			boolean isSuccessfull = fbDAO.addFb(fb);
			if(isSuccessfull) {
				return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\" add fb successfully \"}");
			} 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\" add fb failurely \"}");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"" + e.getMessage() + "\"}");
		}
	}
}
