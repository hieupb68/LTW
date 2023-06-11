package com.example.be;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BuyingController {
	BuyingDAO buyingDAO = new BuyingDAO();
	
	@PostMapping("/buyings")
	public List<Buying> getAllBuyings(@RequestParam int id) throws IOException {
		
		List<Buying> buyings = new ArrayList<Buying>();
		try {
			buyings = buyingDAO.getAllBuyings(id);
			 return buyings;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/buy")
	public HttpEntity<?> buyingBook(@RequestParam int id_user, @RequestParam int id_book, @RequestParam int quantity) {
		try {            
			Buying buying = new Buying();
			buying.setId_book(id_book);
			buying.setQuantity(quantity);
            boolean isSuccessfull = buyingDAO.add(buying, id_user);
            if(isSuccessfull) {
	            return ResponseEntity.ok("{\"message\":\"Buying uploaded successfully\"}");
            } else 
            	 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
 	                    .body("{\"message\":\"Fail to add\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() + "\"}");
        }
	}
	
	@PostMapping("/cancel")
	public HttpEntity<?> buyingBook(@RequestParam int id) {
		try {
			System.out.println(id);
            boolean isSuccessfull = buyingDAO.cancel(id);
            if(isSuccessfull) {
	            return ResponseEntity.ok("{\"message\":\"Buying cancel successfully\"}");
            } else 
            	 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
 	                    .body("{\"message\":\"Fail to cancel\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"" + e.getMessage() + "\"}");
        }
	}
}
