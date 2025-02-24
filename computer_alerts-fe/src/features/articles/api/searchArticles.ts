import axiosInstance from "../models/articlesAxiosInstance";
import { ArticleRequestModel } from "../models/ArticleRequestModel";

/**
 * Search articles by a query (in title or body) within a specific category.
 * @param {string} category - Category to filter articles by.
 * @param {string} query - Search query for title or body.
 * @returns {Promise<ArticleRequestModel[]>} List of articles matching the query within the category.
 */
export const searchArticlesByCategoryAndQuery = async (
  category: string,
  query: string,
): Promise<ArticleRequestModel[]> => {
  const response = await axiosInstance.get<ArticleRequestModel[]>(
    `/articles/categories/${category}/search`,
    { params: { query } },
  );
  return response.data;
};
