package ibf2022.batch1.csf.assessment.server;

import ibf2022.batch1.csf.assessment.server.models.Review;
import jakarta.json.JsonObject;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Utils {

    public static Review jsontoReview(JsonObject movieJsonObj) {
        Review review = new Review();
        review.setTitle(movieJsonObj.get("display_title").toString());
        review.setRating(movieJsonObj.get("mpaa_rating").toString());
        review.setByline(movieJsonObj.get("byline").toString());
        review.setHeadline(movieJsonObj.get("headline").toString());
        review.setSummary(movieJsonObj.get("summary_short").toString());
        review.setReviewURL(movieJsonObj.get("link.url").toString());
        review.setImage(movieJsonObj.get("multimedia.src").toString());
        return review;
    }

    public static JsonObject reviewToJson(Review review) {
        return Json.createObjectBuilder()
                .add("title", review.getTitle())
                .add("rating", review.getRating())
                .add("byline", review.getByline())
                .add("headline", review.getHeadline())
                .add("summary", review.getSummary())
                .add("reviewURL", review.getReviewURL())
                .add("image", review.getImage())
                .add("commentCount", review.getCommentCount())
                .build();
    }
}
