db.comments.find();

db.comments.insert({
    movieName: "godfather",
    name:"hello world",
    rating: 5,
    comment: "comment comment"
});

db.comments.find({ 
   movieName:  "test"
}).count();

db.comments.find({ 
   movieName: { $regex: "test", $options: "i"},
});

db.comments.countDocuments();