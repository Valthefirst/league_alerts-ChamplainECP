import axiosInstance from "../models/articlesAxiosInstance";
import { Article } from "../models/Article";

export const fetchArticleByTag = async (
  tagName: string,
): Promise<Article[]> => {
  try {
    const response = await axiosInstance.get<Article[]>(
      `/articles/tag/${tagName}`,
    );
    return response.data;
  } catch (err) {
    console.error("error fetching articles by tag", err);
    throw err;
  }
};
