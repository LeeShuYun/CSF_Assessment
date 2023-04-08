package ibf2022.batch1.csf.assessment.server.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
		ResponseEntity<String> resp = null;

		// send the request using the client
		try {
			// executes the req entity and returns a response with json payload body
			resp = template.exchange(req, String.class);
		} catch (RestClientException ex) {
			ex.printStackTrace();
			return new ArrayList<Review>();
		}

		// retrieval of payload data
		String payload = resp.getBody();
		System.out.println(payload);
		JsonReader reader = Json.createReader(new StringReader(payload));
		JsonObject movieResp = reader.readObject();

		// catching "results" array nulls, when query doesn't match to any movietitle
		if (movieResp.isNull("results")) {
			return new ArrayList<Review>();
		}

		// System.out.println(movieResp.toString());

		// if not null and query successful, "results" contains movie objs
		JsonArray resultList = movieResp.getJsonArray("results");

		// System.out.println(resultList.toString());

		// convert jsonarray to List<Review>
		// List<Review> reviewsList = new LinkedList<Review>();
		// for (JsonValue movie : resultList) {
		// JsonObject movieJsonObj = movie.asJsonObject(); //cast to jsonobj
		// reviewsList.add(jsontoReview(movieJsonObj)); //
		// }

		List<Review> reviewsList = resultList.stream()
				.map(v -> v.asJsonObject())
				.map(v -> jsontoReview(v))
				.toList();

		return reviewsList;
	}

	public Review jsontoReview(JsonObject movieJsonObj) {
		Review review = new Review();
		review.setTitle(movieJsonObj.getString( "display_title"));
		review.setRating(movieJsonObj.getString( "mpaa_rating"));
		review.setByline(movieJsonObj.getString( "byline"));
		review.setHeadline(movieJsonObj.getString( "headline"));
		review.setSummary(movieJsonObj.getString("summary_short"));
		review.setReviewURL(movieJsonObj.getJsonObject("link").getString("url"));

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
			return jsonObj.getString(attribute);
		}
	}

}
