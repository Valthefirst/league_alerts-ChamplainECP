import axiosInstance from "../models/articlesAxiosInstance";
import { Article } from "../models/Article";

export const fectchArticleBytag = async (tag: string): Promise<Article[]> => {
  const response = await axiosInstance.get<Article[]>(`/articles/tag/${tag}`);
  return response.data;
}