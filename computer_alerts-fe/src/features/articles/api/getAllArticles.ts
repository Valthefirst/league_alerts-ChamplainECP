import axiosInstance from "../models/articlesAxiosInstance";
import { ArticleRequestModel } from "../models/ArticleRequestModel";

export const fetchAllsArticles = async (): Promise<ArticleRequestModel[]> => {
  try {
    const response =
      await axiosInstance.get<ArticleRequestModel[]>(`/articles`);
    return response.data;
  } catch (err) {
    console.error("error fetching articles by tag", err);
    throw err;
  }
};
