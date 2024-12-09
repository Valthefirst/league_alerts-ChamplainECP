import { Author } from '../model/Author';
import axiosInstance from '../model/authorsAxiosInstance';

export const getAuthorById = async (authorId: string): Promise<Author> => {
  const response = await axiosInstance.get<Author>('/' + authorId);
  return response.data;
};
