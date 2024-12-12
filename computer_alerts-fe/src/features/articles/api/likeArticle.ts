import axiosInstance from "../models/articlesAxiosInstance";

/**
 * Sends a request to like an article.
 * @param articleId - The ID of the article to like.
 * @param readerId - The ID of the reader liking the article.
 */
export const likeArticle = async (
  articleId: string,
  readerId: string,
): Promise<void> => {
  console.log("Sending like request:", { articleId, readerId }); // Debugging log
  try {
    await axiosInstance.post(`/interactions/like`, null, {
      params: {
        articleId,
        readerId,
      },
    });
  } catch (error) {
    console.error("Error in likeArticle API call:", error);
    throw error;
  }
};
