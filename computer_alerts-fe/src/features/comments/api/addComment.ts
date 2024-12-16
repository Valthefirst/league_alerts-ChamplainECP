import { CommentModel } from "../model/CommentModel";
import axiosInstance from "../model/commentsAxiosInstance";

export async function addComment(comment:Partial<CommentModel>): Promise<void> {
    try {
        const response = await axiosInstance.post<void>('', comment);
        return response.data;
      } catch (err) {
        console.error("Error posting comments", err);
        throw err;
      }
}