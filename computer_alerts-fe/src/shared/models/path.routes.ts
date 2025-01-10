export enum AppRoutePaths {
  Default = "/",
  HomePage = "/home",
  Authors = "/authors",
  ArticlesByTag = "/articles/tag/:tagName",
  SpecificArticle = "/articles/:id",
  EditArticle = "/articles/edit/:id",
  NBA = "/nba",
  NHL = "/nhl",
  NFL = "/nfl",
  UFC = "/ufc",
  MLB = "/mlb",
  CREATE_ACCOUNT = "/create-account",

  //
  //
  AuthorHomePage = "/authHome",
  AutherYourArticle = "/authYourArticles",
  AutherCreateArticle = "/authCreateArticle",
  AutherDrafts = "/authYourDrafts",

  //
  //

  AdminHomePage = "/adminHomePage",
  AdminAuthors = "/adminAuthors",
  AdminCreateAuthor = "/admin/createAuthor",
  AdminReviewArticles = "/adminReviewArticles",
}
