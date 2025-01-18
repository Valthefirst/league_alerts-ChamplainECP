// import { useEffect, useState } from "react";
// import { useParams } from "react-router-dom";
// import EditArticle from "features/articles/components/EditArticle/EditArticleForm";
// import { ArticleRequestModel } from "features/articles/models/ArticleRequestModel";
// import { fetchArticleByArticleId } from "features/articles/api/getSpecificArticle";

// export default function EditArticlePage(): JSX.Element {
//   const { id } = useParams<{ id: string }>(); // Extract the article ID from the URL
//   const [article, setArticle] = useState<ArticleRequestModel | null>(null);
//   const [loading, setLoading] = useState<boolean>(true);
//   const [error, setError] = useState<string | null>(null);

//   useEffect(() => {
//     const fetchArticle = async () => {
//       setLoading(true);
//       try {
//         if (id) {
//           const fetchedArticle = await fetchArticleByArticleId(id);
//           setArticle(fetchedArticle);
//         }
//       } catch (err) {
//         setError("Failed to fetch the article. Please try again later.");
//       } finally {
//         setLoading(false);
//       }
//     };

//     if (id) fetchArticle();
//   }, [id]);

//   if (loading) return <div>Loading...</div>;
//   if (error) return <div>Error: {error}</div>;
//   if (!article) return <div>No article found</div>;

//   return (
//     <div className="edit-article-page">
//       <EditArticle article={article} />
//     </div>
//   );
// }
export {}