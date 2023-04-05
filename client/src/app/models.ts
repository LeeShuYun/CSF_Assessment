//transport from view 1 search review
export interface Review {
  title: string
  rating: number
  summary: string
  reviewUrl: string
  numberOfComments: number
  imageUrl: string
}

//transport from view 3 post comment
export interface Comment {
  movieName: string
  name: string
  rating: number
  comment: string
}

