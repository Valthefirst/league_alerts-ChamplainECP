import axiosInstance from "../models/articlesAxiosInstance";
import { ArticleRequestModel } from "../models/ArticleRequestModel";

export const fetchArticleByCategory = async (
  category: string,
): Promise<ArticleRequestModel[]> => {
  console.log("Fetching articles by category");
  try {
    const accessToken = localStorage.getItem("accessToken");

    if (!accessToken) {
      throw new Error("Access token is missing. Please Log in again.");
    }

    const response = await axiosInstance.get<ArticleRequestModel[]>(
      `/articles/categories/${category}`,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      },
    );
    return response.data;
  } catch (err) {
    console.error("error fetching articles by category", err);
    throw err;
  }
};
