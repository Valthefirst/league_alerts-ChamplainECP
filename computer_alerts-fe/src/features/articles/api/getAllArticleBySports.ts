import axiosInstance from "../models/articlesAxiosInstance";
import { ArticleRequestModel } from "../models/ArticleRequestModel";

export const fetchArticleByCategory = async (
  category: string,
): Promise<ArticleRequestModel[]> => {
  try {
    const response = await axiosInstance.get<ArticleRequestModel[]>(
      `/articles/categories/${category}`,
      {},
    );
    return response.data;
  } catch (err) {
    console.error("error fetching articles by category", err);
    throw err;
  }
};
