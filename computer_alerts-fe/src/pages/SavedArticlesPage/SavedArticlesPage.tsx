import SavedArticlesList from "features/savedArticles/components/SavedArticlesList";

export default function SavedArticlesPage() {
  return (
    <div>
      <h1 style={{ textAlign: "center" }}>Saved Articles</h1>
      <SavedArticlesList readerId={"06a7d573-bcab-4db3-956f-773324b92a80"} />
    </div>
  );
}
