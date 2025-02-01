import axiosInstance from "../models/articlesAxiosInstance";

export const acceptArticle = async (articleId: string): Promise<void> => {
  try {
    const accessToken = localStorage.getItem("accessToken");
    await axiosInstance.patch(`/articles/acceptArticle/${articleId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
  } catch (error: any) {
    if (error.response && error.response.status === 403) {
      // Redirect to unauthorized page if status is 401
      window.location.href = "/unauthorized";
    } else {
      console.error("Error in editArticle API call:", error);
    }
    throw error;
  }
};
