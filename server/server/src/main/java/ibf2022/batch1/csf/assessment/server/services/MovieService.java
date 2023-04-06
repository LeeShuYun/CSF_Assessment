package ibf2022.batch1.csf.assessment.server.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.batch1.csf.assessment.server.Utils;
import ibf2022.batch1.csf.assessment.server.models.Review;
import ibf2022.batch1.csf.assessment.server.repositories.MovieRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ibf2022.batch1.csf.assessment.server.Utils.*;

@Service
public class MovieService {

	@Autowired
	MovieRepository movieRepo;

	private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

	private static final String API_URL = "https://api.nytimes.com/svc/movies/v2/reviews/search.json";

	@Value("${nyt.api.key}")
	private String apiKey;

	// TODO: Task 4
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// https://api.nytimes.com/svc/movies/v2/reviews/search.json?query=godfather&api-key=
	public List<Review> searchReviews(String query) {
		String movieSearchURL = UriComponentsBuilder
				.fromUriString(API_URL)
				.queryParam("query", query)
				.queryParam("api-key", apiKey)
				.toUriString();

		RequestEntity<Void> req = RequestEntity.get(movieSearchURL)
				.accept(MediaType.APPLICATION_JSON)
				.build();

		// client to do the request
		RestTemplate template = new RestTemplate();

		// init the Response to catch the response data
		ResponseEntity<String> jsonResponse = null;

		// send the request using the client
		try {
			// executes the req entity and returns a response with json payload body
			jsonResponse = template.exchange(req, String.class);
		} catch (RestClientException ex) {
			ex.printStackTrace();
			return Collections.EMPTY_LIST;
		}

		// decode jsonResponse into JsonObj, get JsonArray out of JsonObj
		String payload = jsonResponse.getBody();
		// System.out.println(payload); //success
		JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject movieResp = reader.readObject();

		// System.out.println(movieResp.toString());

		// retrieve the results which contain array of movie objs
		JsonArray resultList = movieResp.getJsonArray("results");

		// convert jsonarray to List<Review>
		List<Review> reviewsList = new ArrayList<Review>();
		for (JsonValue movie : resultList) {
			JsonObject movieJsonObj = movie.asJsonObject();
			reviewsList.add(jsontoReview(movieJsonObj));
		}

		return reviewsList;
	}

	public Review jsontoReview(JsonObject movieJsonObj) {
		Review review = new Review();
		review.setTitle(jsonNullChecker(movieJsonObj, "display_title"));
		review.setRating(jsonNullChecker(movieJsonObj, "mpaa_rating"));
		review.setByline(jsonNullChecker(movieJsonObj, "byline"));
		review.setHeadline(jsonNullChecker(movieJsonObj, "headline"));
		review.setSummary(jsonNullChecker(movieJsonObj, "summary_short"));

		JsonObject link = movieJsonObj.get("link").asJsonObject();
		String linkurl = jsonNullChecker(link, "url");
		review.setReviewURL(linkurl);

		if (movieJsonObj.isNull("multimedia")) {
			review.setImage("undefined");
		} else {
			JsonObject multimedia = movieJsonObj.getJsonObject("multimedia");
			String multimediasrc = jsonNullChecker(multimedia, "src");
			review.setImage(multimediasrc);
		}

		System.out.println("review Title >>>> " + review.getTitle());
		try {
			int count = movieRepo.countComments(review.getTitle());
			// System.out.println(" Utils count >>> " + count);
			review.setCommentCount(count);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return review;
	}

	private String jsonNullChecker(JsonObject jsonObj, String attribute) {
		if (jsonObj.isNull(attribute)) {
			return "undefined";
		} else {
			return jsonObj.get(attribute).toString();
		}
	}

}
