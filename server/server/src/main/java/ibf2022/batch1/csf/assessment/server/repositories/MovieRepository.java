package ibf2022.batch1.csf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class MovieRepository {

	@Autowired
	private MongoTemplate template;

	private static final String COLLECTION_COMMENTS = "comments";

	// TODO: Task 5
	// You may modify the parameter but not the return type
	// Write the native mongo database query in the comment below
	// db.collection.countDocuments();
	public int countComments(Object param) {
		int count = (int) template.count(new Query(), COLLECTION_COMMENTS);
		return count;
	}

	// TODO: Task 8
	// Write a method to insert movie comments comments collection
	// Write the native mongo database query in the comment below
	// db.comment.insertOne({
	// "comments": "I found this very interesting blah blah",
	// ...
	// })
	public void insertComment(Comment comment) {
		template.insert(comment, "comment");
	}

}
