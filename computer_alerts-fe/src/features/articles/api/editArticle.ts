import { AxiosResponse } from "axios";
import { ArticleRequestModel } from "../models/ArticleRequestModel";
import axiosInstance from "../models/articlesAxiosInstance";

export const editArticle = async (
  articleId: string,
  article: ArticleRequestModel,
): Promise<AxiosResponse<void>> => {
  try {
    return await axiosInstance.put<void>(`/articles/${articleId}`, article);
  } catch (err) {
    console.error("error editing article", err);
    throw err;
  }
};
