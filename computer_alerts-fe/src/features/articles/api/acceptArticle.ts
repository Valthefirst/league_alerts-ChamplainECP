import axiosInstance from "../models/articlesAxiosInstance";

export const acceptArticle = async (articleId: string): Promise<void> => {
  try {
    await axiosInstance.patch(`/articles/acceptArticle/${articleId}`);
  } catch (error) {
    throw error;
  }
};
