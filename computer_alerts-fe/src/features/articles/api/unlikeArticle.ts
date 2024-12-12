import axiosInstance from "../models/articlesAxiosInstance";

/**
 * Sends a request to unlike an article.
 * @param articleId - The ID of the article to unlike.
 * @param readerId - The ID of the reader unliking the article.
 */
export const unlikeArticle = async (
  articleId: string,
  readerId: string,
): Promise<void> => {
  console.log("Sending unlike request:", { articleId, readerId }); // Debugging log
  try {
    await axiosInstance.delete(`/interactions/unlike`, {
      params: {
        articleId,
        readerId,
      },
    });
  } catch (error) {
    console.error("Error in unlikeArticle API call:", error);
    throw error;
  }
};
