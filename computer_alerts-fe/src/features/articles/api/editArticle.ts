import { AxiosResponse } from "axios";
import { ArticleRequestModel } from "../models/ArticleRequestModel";
import axiosInstance from "../models/articlesAxiosInstance";

export const editArticle = async (
  articleId: string,
  article: ArticleRequestModel,
): Promise<AxiosResponse<void>> => {
  try {
    const accessToken = localStorage.getItem("accessToken");

    // Corrected axios request with headers included in the options parameter
    return await axiosInstance.put<void>(`/articles/${articleId}`, article, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
  } catch (error: any) {
    if (error.response && error.response.status === 401) {
      // Redirect to unauthorized page if status is 401
      window.location.href = "/unauthorized"; 
    } else {
      console.error("Error in editArticle API call:", error);
    }
    throw error; 
  }
};
