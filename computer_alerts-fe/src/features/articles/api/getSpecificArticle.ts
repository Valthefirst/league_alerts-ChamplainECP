// src/services/articleService.ts
import axiosInstance from "../models/articlesAxiosInstance";
import { ArticleRequestModel } from "../models/ArticleRequestModel";

export const fetchArticleByArticleId = async (
  articleId: string,
): Promise<ArticleRequestModel> => {
  await axiosInstance.patch(`/articles/${articleId}`);

  const response = await axiosInstance.get<ArticleRequestModel>(
    `/articles/${articleId}`,
  );
  return response.data;
};

export const fetchArticleByArticleIdWithNoPatch = async (
  articleId: string,
): Promise<ArticleRequestModel> => {
  const response = await axiosInstance.get<ArticleRequestModel>(
    `/articles/${articleId}`,
  );
  return response.data;
};
