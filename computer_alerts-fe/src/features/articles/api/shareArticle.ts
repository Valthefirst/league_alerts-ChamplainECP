import axiosInstance from "../models/articlesAxiosInstance";

/**
 * Sends a request to like an article.
 * @param articleId - The ID of the article to like.
 * @param readerId - The ID of the reader liking the article.
 */
export const shareArticle = async (
  articleId: string,
  readerId: string,
): Promise<void> => {
  console.log("Sending share request:", { articleId, readerId }); // Debugging log
  try {
    const accessToken = localStorage.getItem("accessToken");
    await axiosInstance.post(`/interactions/share`, null, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      params: {
        articleId,
        readerId,
      },
    });
  }catch (error: any) {
    
    if (error.response && error.response.status === 401) {
      
      
      window.location.href = "/unauthorized"; 
    } else {
      console.error("Error in unlikeArticle API call:", error);
    }
    throw error; 
  }
};
