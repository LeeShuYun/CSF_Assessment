package ibf2022.batch1.csf.assessment.server.controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.batch1.csf.assessment.server.Utils;
import ibf2022.batch1.csf.assessment.server.models.Comment;
import ibf2022.batch1.csf.assessment.server.models.Review;
import ibf2022.batch1.csf.assessment.server.repositories.MovieRepository;
import ibf2022.batch1.csf.assessment.server.services.MovieService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

	@Autowired
	private MovieService movieService;

	@Autowired
	private MovieRepository movieRepo;

	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

	// TODO: Task 3, Task 4, Task 8

	// task 3
	@GetMapping(path = "/api/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> getMovieByName(
			@RequestParam(required = true) String query) {

		logger.debug("searchterm : " + query);

		// TODO - seems a lil broken and I'm running out of time... T_T
		List<Review> movieList = movieService.searchReviews(query);
		// List<Review> movieList = new LinkedList<Review>();
		// Review review = new Review();
		// review.setCommentCount(4);
		// review.setByline("BEN KENIGSBERG");
		// review.setTitle("The Black Godfather");
		// review.setRating("TV-MA");
		// review.setHeadline("‘The Black Godfather’ Review: The Music Executive Who
		// Made It All Happen");
		// review.setSummary("Reginald Hudlin’s documentary about Clarence Avant
		// includes many golden anecdotes.");
		// review.setReviewURL("https://www.nytimes.com/2019/06/06/movies/the-black-godfather-review.html");
		// review.setImage(
		// "https://static01.nyt.com/images/2019/06/05/arts/blackgodfather1/blackgodfather1-mediumThreeByTwo210.jpg");

		// movieList.add(review);

		System.out.printf("movieList>>> ", movieList.toString());

		logger.debug("movieList>>> " + movieList.toString());

		// the marshalling works at least
		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		// convert ArticleObj to JsonObj and add to ArrayBuilder
		for (Review review2 : movieList) {
			JsonObject jObj = Utils.reviewToJson(review2);
			arrBuilder.add(jObj);
		}

		if (movieList.isEmpty()) {
			JsonObject error = Json.createObjectBuilder()
					.add("message", "No movies found")
					.build();
			return ResponseEntity.status(404).body(error.toString());
		} else {
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(arrBuilder.build().toString());
		}
	}

	// Task 5. returns number of documents in comments collection
	@GetMapping(path = "/api/commentNum", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> countComments(String movieName) {
		int count = movieRepo.countComments();

		String jsonPayload = Json.createObjectBuilder()
				.add("count", count)
				.build()
				.toString();

		return ResponseEntity.ok(jsonPayload);
	}

	// Task 7
	@PostMapping(path = "/api/comment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> insertComments(@RequestBody Comment comment) {
		// Comment comment = new Comment();
		// comment.setMovieName((String) model.getAttribute("movieName"));
		// comment.setName((String) model.getAttribute("name"));
		// comment.setRating((int) model.getAttribute("rating"));
		// comment.setComment((String) model.getAttribute("comment"));

		System.out.println("insertComment>>> " + comment.getComment());
		// Comment comment = new Comment();
		// comment.setMovieName("Baby First Movie");
		// comment.setName("User1802934");
		// comment.setRating(5);
		// comment.setComment("lots of cute scenes");

		movieRepo.insertComment(comment);

		return new ResponseEntity<String>("Comment Submitted.", HttpStatus.OK);
	}
}
