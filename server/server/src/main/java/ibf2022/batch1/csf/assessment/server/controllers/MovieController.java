package ibf2022.batch1.csf.assessment.server.controllers;

import java.util.ArrayList;
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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonObject;

@RestController
// @CrossOrigin(origins = "*")
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

	@Autowired
	private MovieService movieService;

	@Autowired
	private MovieRepository movieRepo;

	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

	// TODO: Task 3, Task 4, Task 8

	// task 3
	@GetMapping(path = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> getMovieByName(
			@RequestParam(required = true) String query) {

		// logger types to use
		logger.info("searchterm >>>>>> " + query);
		logger.error("searchterm >>>>>> " + query);

		List<Review> movieList = movieService.searchReviews(query);

		// System.out.println("Controller movieList>>>>" + movieList.get(0).toString());

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

		movieList.stream()
				.forEach(v -> {
					arrBuilder.add(Utils.reviewToJson(v));
				});

		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(arrBuilder.build().toString());

	}

	// Task 7
	@PostMapping(path = "/comment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> insertComments(Comment comment) {
		// System.out.println("insertComment>>> " + comment.getComment());
		movieRepo.insertComment(comment);
		System.out.println("comment in controller>>>> " + comment);
		String jsonPayload = Json.createObjectBuilder()
				.add("status", "Comment Submitted.")
				.build()
				.toString();
		return new ResponseEntity<String>(jsonPayload, HttpStatus.OK);
	}
}
