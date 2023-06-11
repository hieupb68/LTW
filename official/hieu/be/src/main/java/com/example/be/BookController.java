package com.example.be;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class BookController {
	BookDAO bookDAO = new BookDAO();
	FeedbackDAO fbDAO = new FeedbackDAO();

	@GetMapping("/books")
	public List<Book> getBooks() {
		List<Book> books = new ArrayList();
		try {
			books = bookDAO.getBooks();
			 return books;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	
	@GetMapping("/book/{id}")
	public Book getBookById(@PathVariable int id) {
		try {
			return bookDAO.getBookById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/book-detail/{id}")
	public BookDetail getBookDetailById(@PathVariable int id) {
		try {
			Book book = bookDAO.getBookById(id);
			List<Feedback> fbs = fbDAO.getFb(id);
			
			return new BookDetail(book, fbs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 @GetMapping("/image/{imageName}")
	    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
            String uploadPath = "images/";
            BufferedImage bImage = ImageIO.read(new File(uploadPath + imageName));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos );
            byte[] imageBytes = bos.toByteArray();

	        return ResponseEntity.ok().body(imageBytes);
	    }
	    
	 @PostMapping("/upload-image")
	    public ResponseEntity<String> uploadImage(
	    		@RequestParam("id") int id,
	    		@RequestParam("title") String title,
	    		@RequestParam("author") String author,
	    		@RequestParam("description") String description,
	    		@RequestParam("release") String date,
	    		@RequestParam("pages") int pages,
	    		@RequestParam("category") String category,
	    		@RequestParam("price") int price,
	    		@RequestParam("file") MultipartFile file) {
	        try {
	        		        	
	        	String projectPath = System.getProperty("user.dir");
	            String uploadPath = "/images/";
	            String fileName = file.getOriginalFilename();

	            Book book = new Book(id, title, author, description, Utils.stringToDate(date), pages, category, price, fileName);
	            
	            boolean isSuccessfull = bookDAO.upload(book);
	            if(isSuccessfull) {
	            	file.transferTo(new File(projectPath + uploadPath + title + "-" + fileName));
		            return ResponseEntity.ok("{\"message\":\"Book uploaded successfully\"}");
	            } else 
	            	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 	                    .body("{\"message\":\"Fail to add\"}");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("{\"message\":\"" + e.getMessage() + "\"}");
	        }
	    }
	 
	 @PostMapping("/upload")
	    public ResponseEntity<String> uploadNotImage(
	    		@RequestParam("id") int id,
	    		@RequestParam("title") String title,
	    		@RequestParam("author") String author,
	    		@RequestParam("description") String description,
	    		@RequestParam("release") String date,
	    		@RequestParam("pages") int pages,
	    		@RequestParam("price") int price,
	    		@RequestParam("category") String category) {
	        try {

	            Book book = new Book(id, title, author, description, Utils.stringToDate(date), pages, category, price, "");
	            
	            boolean isSuccessfull = bookDAO.upload(book);
	            if(isSuccessfull) {
		            return ResponseEntity.ok("{\"message\":" + "\"Book uploaded successfully\"}");
	            } else 
	            	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 	                    .body("{\"message\":\"Fail to add\"}");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("{\"message\":\"" + e.getMessage() +"\"}");
	        }
	    }
	 
	 @DeleteMapping("/delete/{id}")
	 public ResponseEntity<String> uploadNotImage(@PathVariable int id) {
	        try {
	        	Book book = bookDAO.getBookById(id);
	            boolean isSuccessfull = bookDAO.delete(id);
	            if(isSuccessfull) {
	            	String imagePath = "images/" + book.getTitle() + "-" + book.getImage();
	                File imageFile = new File(imagePath);
	                if (imageFile.exists()) {
	                    boolean deleted = imageFile.delete();
	                    if (deleted) {
	                        System.out.println("Image file deleted successfully.");
	                    } else {
	                        System.out.println("Failed to delete the image file.");
	                    }
	                }
		            return ResponseEntity.ok("{\"message\":" + "\"Book id delete successfully\"}");
	            } else 
	            	 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	 	                    .body("{\"message\":\"Fail to add\"}");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("{\"message\":\"" + e.getMessage() +"\"}");
	        }
	    }
}
