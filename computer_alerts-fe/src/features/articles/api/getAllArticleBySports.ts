import axiosInstance from "../models/articlesAxiosInstance";
import { ArticleRequestModel } from "../models/ArticleRequestModel";

export const fetchArticleByCategory = async (
  category: string,
): Promise<ArticleRequestModel[]> => {
  console.log("Fetching articles by category");
  try {
    const response = await axiosInstance.get<ArticleRequestModel[]>(
      `/articles/categories/${category}`,
      {},
    );
    console.log("API Response: ", response.data);
    console.log("API Response: ", category);
    return response.data;
  } catch (err) {
    console.error("error fetching articles by category", err);
    throw err;
  }
};
