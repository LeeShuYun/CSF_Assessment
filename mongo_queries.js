db.comment.insert({
    title: "disenchante", season: 1, 
});

db.comment.aggregate([
{
    $group: {
        count: { $sum: 1 }, // count the number of docs in each group of rating
    }
    }
])