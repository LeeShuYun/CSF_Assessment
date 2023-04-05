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
	// movieName: { $regex: "godfather", $options: "i"},
	// });
	public int countComments() {
		Criteria criterial = Criteria.where("title").regex("crystal", "i");
		Query query = Query.query(criterial);
		List<Document> list = template.find(query, Document.class, Constants.COLLECTION_COMMENTS);
		System.out.println("countComments>>>> " + list.size());
		return list.size();
	}

	// TODO: Task 8
	// Write a method to insert movie comments comments collection
	// Write the native mongo database query in the comment below
	// db.comments.insertOne({
	// "name": "I found this very interesting blah blah",
	// })
	public void insertComment(Comment comment) {
		// Document commentDoc = ;

		// Document newDoc = template.insert(commentDoc, COLLECTION_COMMENTS);
		template.insert(comment, Constants.COLLECTION_COMMENTS);
	}

}
