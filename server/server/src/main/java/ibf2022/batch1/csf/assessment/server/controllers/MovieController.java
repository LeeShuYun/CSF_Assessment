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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.batch1.csf.assessment.server.Utils;
import ibf2022.batch1.csf.assessment.server.models.Review;
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
	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

	// TODO: Task 3, Task 4, Task 8

	// task 3
	@GetMapping(path = "/api/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> getMovieByName(
			@RequestParam(required = true) String query) {

		logger.debug("searchterm : " + query);

		List<Review> movieList = movieService.searchReviews(query);

		System.out.printf("movieList>>> ", movieList.toString());
		logger.debug("movieList>>> " + query);
		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		// convert ArticleObj to JsonObj and add to ArrayBuilder
		for (Review review : movieList) {
			JsonObject jObj = Utils.reviewToJson(review);
			arrBuilder.add(jObj);
		}

		if (movieList.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.contentType(MediaType.APPLICATION_JSON)
					.body("{not found}");
		} else {
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(arrBuilder.build().toString());
		}
	}

	@PostMapping(path = "/api/comment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postComments(String hahha) {

		String jsonPayload = Json.createObjectBuilder()
				.add("success", "keep on keeping on")
				.build()
				.toString();

		return ResponseEntity.ok(jsonPayload);
	}
}
