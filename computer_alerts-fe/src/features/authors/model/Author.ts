export interface ArticleIdentifier {
  articleId: string;
}

export interface ArticleList {
  articleList: ArticleIdentifier[] | null;
}

export interface Author {
  authorId: string;
  emailAddress: string;
  firstName: string;
  lastName: string;
  biography: string;
  articles: ArticleList;
}
