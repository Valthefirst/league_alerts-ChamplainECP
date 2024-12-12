export interface Article {
  articleId: string; // Top-level property
  title: string;
  body: string;
  wordCount: number;
  articleStatus: string;
  tags: string;
  tagsTag: string;
  timePosted: string;
  authorIdentifier: string;
}




export interface ArticleRequestModel{
  title: string;
  body: string;
  wordCount: number;
  articleStatus: string;
  tags: string;
  tagsTag: string;
  timePosted: string;
  authorIdentifier: string;

}