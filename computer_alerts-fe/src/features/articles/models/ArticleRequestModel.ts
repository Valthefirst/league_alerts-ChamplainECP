export interface ArticleRequestModel {
  articleId: string; // Top-level property
  title: string;
  body: string;
  wordCount: number;
  articleStatus: string;
  requestCount: number;
  tags: string;
  timePosted: string;
}
