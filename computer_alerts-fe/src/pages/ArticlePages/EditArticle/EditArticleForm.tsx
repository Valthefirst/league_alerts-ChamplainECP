import React, { useState, useEffect, ChangeEvent, FormEvent } from "react";
import { useParams, useNavigate } from "react-router-dom";
//import { updateArticle } from "@/features/articles/api/updateArticle"; // Import the API call
import { ArticleRequestModel } from "../../../features/articles/models/ArticleRequestModel"; 
import { Button, Form } from "react-bootstrap"; // Bootstrap components
import "./EditArticlePage.css"; // Import your CSS for styling
import { editArticle } from "features/articles/api/editArticle";

interface EditArticlePageProps {
   
    article: ArticleRequestModel;
    refreshArticlesList: () => void;
    onClose: () => void; 
}

export default function EditArticle(
    {    
        article, 
        refreshArticlesList,
        onClose,
     }
        : EditArticlePageProps): JSX.Element {

            const [formData, setFormData] = useState<ArticleRequestModel>(article);
            const [error, setError] = useState<string | null>(null);

            const handleChanges = (
                e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>

            ): void => {
                const{name, value} = e.target;
                setFormData({...formData, [name]: value});
            }

            const handleTagChanges = (
                e: React.ChangeEvent<HTMLSelectElement>
            ): void => {
                const selectElement = e.target as HTMLSelectElement;
                const selectedOption = Array.from(selectElement.selectedOptions);

                // eslint-disable-next-line @typescript-eslint/no-unused-vars
                const selectedTags = selectedOption.map(option => ({
                    tagId: option.value,
                    tagName: option.textContent ?? '',
                }));
                }

                const handleSubmit = async(e:FormEvent) => {
                    e.preventDefault();
                    if(!validate()) return;
        
                    try{
                        await editArticle(article.articleId, formData);
                        alert("Article updated successfully");
                        refreshArticlesList();
                        onClose();
        
                    }catch(err){
                        setError('Failed to update article. Please try again later.');
                    }
        
                }
                
        const validate = (): boolean => {
            const newErrors: { [key: string]: string } = {};
            if (!formData.title) newErrors.title = 'Title is required';
            if (!formData.tagsTag) newErrors.lastName = 'Tags are required name is required';
            if (!formData.articleDescpition) newErrors.articleDescpition = 'Article Descpition is required';
            if (!formData.tags) newErrors.tags = 'Category is required';
            if (!formData.body) newErrors.body = 'Body is required';
            if (!formData.photoUrl) newErrors.photoUrl = 'Photo URL is required';
        

            setError(error);
            return Object.keys(newErrors).length === 0;
  };
            return (
                <div className="edit-article-page">
                <h1>Edit Article</h1>
                
                {/* Title Field */}
                <div className="field">
                  <label>Title</label>
                  <input
                    type="text"
                    name="title"
                    value={formData.title}
                    onChange={handleChanges}
                  />
                </div>
          
                {/* Tag Field */}
                <div className="field">
                  <label>Categories</label>
                  <input
                    type="text"
                    name="tags"
                    value={formData.tags}
                    onChange={handleChanges}
                  />
                </div>
          
                {/* Category Field */}
                <div className="field">
                  <label>Tags</label>
                  <select
                    name="Tag"
                    value={formData.tagsTag}
                    onChange={handleTagChanges}
                  >
                    <option value="NBA">NBA</option>
                    <option value="NHL">NHL</option>
                    <option value="UFC">UFC</option>
                    <option value="NFL">NFL</option>
                    <option value="MLB">MLB</option>
                  </select>
                </div>
          
                {/* Description Field */}
                <div className="field">
                  <label>Description</label>
                  <textarea
                    name="body"
                    value={formData.body}
                    onChange={handleChanges}
                  />
                </div>
          
                {/* Photo URL Field */}
                <div className="field">
                  <label>Photo URL</label>
                  <input
                    type="text"
                    name="photoUrl"
                    value={formData.photoUrl}
                    onChange={handleChanges}
                  />
                </div>
          
                {/* Body Field */}
                <div className="field">
                  <label>Body</label>
                  <textarea
                    name="body"
                    value={formData.body}
                    onChange={handleChanges}
                  />
                </div>
          
                
                <Button variant="primary" type="submit" onClick={handleSubmit}>Update Article</Button>
              </div>
            );
        }