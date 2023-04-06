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

    // public static Review jsontoReview(JsonObject movieJsonObj) {
    // Review review = new Review();
    // review.setTitle(jsonNullChecker(movieJsonObj, "display_title"));
    // review.setRating(jsonNullChecker(movieJsonObj, "mpaa_rating"));
    // review.setByline(jsonNullChecker(movieJsonObj, "byline"));
    // review.setHeadline(jsonNullChecker(movieJsonObj, "headline"));
    // review.setSummary(jsonNullChecker(movieJsonObj, "summary_short"));

    // JsonObject link = movieJsonObj.get("link").asJsonObject();
    // String linkurl = jsonNullChecker(link, "url");
    // review.setReviewURL(linkurl);

    // JsonObject multimedia = movieJsonObj.get("multimedia").asJsonObject();
    // String multimediasrc = jsonNullChecker(multimedia, "src");
    // review.setImage(multimediasrc);

    // // TODO
    // System.out.println("review Title >>>> " + review.getTitle());
    // // try {
    // // int count = movieRepo.countComments("god");
    // // System.out.println(" Utils count >>> " + count);
    // // review.setCommentCount(count);
    // // } catch (NullPointerException e) {
    // // e.printStackTrace();
    // // }
    // return review;
    // }

    // private static String jsonNullChecker(JsonObject jsonObj, String attribute) {
    // if (jsonObj.isNull(attribute)) {
    // return "undefined";
    // } else {
    // return jsonObj.get(attribute).toString();
    // }
    // }

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
