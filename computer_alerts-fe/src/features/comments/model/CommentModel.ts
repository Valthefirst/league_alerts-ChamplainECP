export interface CommentModel {
  commentId: string;
  content: string;
  wordCount: number;
  timestamp: Date;
  articleId: string;
  readerId: string;
}
