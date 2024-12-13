export interface ArticleRequestModel {
  articleId: string; // Top-level property
  title: string;
  body: string;
  wordCount: number;
  articleStatus: string;
  requestCount: number;
  likeCount: number;
  tags: string;
  tagsTag: string;
  timePosted: string;
  photoUrl: string;
  authorIdentifier: string;
}

export interface ArticleRequestModelI {
  title: string;
  body: string;
  photoUrl: string;
  wordCount: number;
  articleStatus: string;
  tags: string;
  tagsTag: string;
  timePosted: string;
  authorIdentifier: string;
}
