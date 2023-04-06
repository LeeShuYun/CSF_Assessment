package ibf2022.batch1.csf.assessment.server;

import org.springframework.beans.factory.annotation.Autowired;

import ibf2022.batch1.csf.assessment.server.models.Review;
import ibf2022.batch1.csf.assessment.server.repositories.MovieRepository;
import jakarta.json.JsonObject;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Utils {

    public static JsonObjectBuilder reviewToJson(Review review) {
        // System.out.println("reviewToJson()>>>" + review.getTitle());
        return Json.createObjectBuilder()
                .add("title", review.getTitle())
                .add("rating", review.getRating())
                .add("byline", review.getByline())
                .add("headline", review.getHeadline())
                .add("summary", review.getSummary())
                .add("reviewURL", review.getReviewURL())
                .add("image", review.getImage())
                .add("commentCount", review.getCommentCount());
    }
}
