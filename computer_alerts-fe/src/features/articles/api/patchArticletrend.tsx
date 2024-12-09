// Patch article trend
import axiosInstance from "../models/articlesAxiosInstance";
import { Article } from "../models/Article";

export const patchArticleTrend = async (
  articleId: string
): Promise<Article> => {
  const response = await axiosInstance.patch<Article>(`/articles/${articleId}`);
  return response.data;
};
