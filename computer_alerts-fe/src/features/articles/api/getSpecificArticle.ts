// src/services/articleService.ts
import axiosInstance from "../models/articlesAxiosInstance";
import { Article } from "../models/Article";

export const fetchArticleByArticleId = async (articleId: string): Promise<Article> => {

  await axiosInstance.patch(`/articles/${articleId}`);

  const response = await axiosInstance.get<Article>(`/articles/${articleId}`);
  return response.data;
};
