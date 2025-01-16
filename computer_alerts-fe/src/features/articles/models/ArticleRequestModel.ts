export interface ArticleRequestModel {
  articleId: string; // Top-level property
  title: string;
  body: string;
  wordCount: number;
  articleStatus: string;
  requestCount: number;
  likeCount: number;
  category: string;
  tagsTag: string;
  timePosted: string;
  photoUrl: string;
  authorIdentifier: string;
  articleDescpition: string;
}

export interface ArticleRequestModelI {
  fileName: string | number | readonly string[] | undefined;
  title: string;
  body: string;
  photoUrl: string;
  wordCount: number;
  articleStatus: string;
  category: string;
  tagsTag: string;
  timePosted: string;
  authorIdentifier: string;
  articleDescpition: string;
}
