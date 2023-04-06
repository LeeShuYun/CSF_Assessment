package ibf2022.batch1.csf.assessment.server.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch1.csf.assessment.server.models.*;
import ibf2022.batch1.csf.assessment.server.Constants;

@Repository
public class MovieRepository {

	@Autowired
	private MongoTemplate template;

	// TODO: Task 5
	// You may modify the parameter but not the return type
	// Write the native mongo database query in the comment below
	// db.comments.find({
	// movieName: "test"
	// }).count();
	public int countComments(String searchTerm) {
		Criteria criterial = Criteria.where("movieName").is(searchTerm);
		Query query = Query.query(criterial);
		int count = (int) template.count(query, Constants.COLLECTION_COMMENTS);
		System.out.println("count comments of " + searchTerm + ">>>> " + count);
		return count;
	}

	// TODO: Task 8
	// Write a method to insert movie comments comments collection
	// Write the native mongo database query in the comment below
	// db.comments.insert({
	// movieName: "godfather",
	// name:"hello world",
	// rating: 5,
	// comment: "comment comment"
	// });
	public void insertComment(Comment comment) {
		template.insert(comment, Constants.COLLECTION_COMMENTS);
	}

}
