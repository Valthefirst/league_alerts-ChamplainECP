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
  try {
    const accessToken = localStorage.getItem("accessToken");
    await axiosInstance.delete(`/interactions/unlike`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: {
        articleId,
        readerId,
      },
    });
  } catch (error: any) {
    if (error.response && error.response.status === 401) {
      window.location.href = "/unauthorized";
    } else {
      console.error("Error in unlikeArticle API call:", error);
    }
    throw error;
  }
};
