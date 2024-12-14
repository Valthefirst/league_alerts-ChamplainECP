// import { CommentModel } from "../model/CommentModel";
// import axiosInstance from "../model/commentsAxiosInstance";

// export const getAllComments = async (): Promise<CommentModel> => {
//   try {
//     const response = await axiosInstance.get<CommentModel>('');
//     return response.data;
//   } catch (err) {
//     console.error("Error fetching comments", err);
//     throw err;
//   }
// };

export const getAllComments = () => {
  const eventSource = new EventSource('http://localhost:8080/api/v1/interactions/comments');
  return eventSource;
};