import axiosInstance from "../models/articlesAxiosInstance";
import { ArticleRequestModel } from "../models/ArticleRequestModel";

export const fetchArticleByTag = async (
  tagName: string,
): Promise<ArticleRequestModel[]> => {
  try {

    const accessToken = localStorage.getItem("accessToken");

    if(!accessToken){
      throw new Error("Access token is missing. Please Log in again.")
    }

    const response = await axiosInstance.get<ArticleRequestModel[]>(
      `/articles/tag/${tagName}`,{
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    return response.data;
  } catch (err) {
    console.error("error fetching articles by tag", err);
    throw err;
  }
};