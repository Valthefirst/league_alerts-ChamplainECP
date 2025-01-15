import React, { useState, ChangeEvent, FormEvent, useEffect } from "react";
import { ArticleRequestModel } from "../../models/ArticleRequestModel";
import { editArticle } from "features/articles/api/editArticle";
import { useNavigate } from "react-router-dom";
import { editArticleImage } from "features/articles/api/updateArticleImage";
import "./EditArticleForm.css"; // Adjust the path based on your file structure


interface EditArticlePageProps {
  article: ArticleRequestModel;
}

export default function EditArticle({
  article,
}: EditArticlePageProps): JSX.Element {
  const [formData, setFormData] = useState<ArticleRequestModel>(article);
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [error, setError] = useState<string | null>(null);
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [loading, setLoading] = useState<boolean>(false); // For showing a loading state
  const [imageFile, setImageFile] = useState<File | null>(null); 
  const [fileName, setFileName] = useState<string>("") //temporary file
  const navigate = useNavigate();

 
    
  const handleChanges = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ): void => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleImageChanges = async (
    e: ChangeEvent<HTMLInputElement>,
    ) => {
    const file = e.target.files?.[0];
      if  (file) {
        setImageFile(file);
        setFileName(file.name);
        alert("Image file selected:" + file.name);
      } else if (!file){
        setImageFile(null);
       
        alert("File could not be uploaded:");
      };
    };

  const handleTagChanges = (e: React.ChangeEvent<HTMLSelectElement>): void => {
    const { value } = e.target; // Get the selected value
    setFormData({ ...formData, tagsTag: value }); // Update tagsTag in formData
  };


     // Ensure the formData is updated whenever the article prop changes
     useEffect(() => {
      setFormData(article);
    }, [article]);

    const handleCancel = () => {
      navigate(`/articles/${article.articleId}`); // Adjust the path if needed
    };

  const handleSubmit = async (e: FormEvent) => {

    e.preventDefault();
    
    if (!validate()) {
      return;
    }

    setLoading(true);

   try{
    if(imageFile){
      const response = await editArticleImage(article.articleId, imageFile);
      const photoUrl = response.data;
      formData.photoUrl = photoUrl;
    }

    await editArticle(article.articleId, formData);
    alert("Article updated successfully");
    navigate(`/articles/${article.articleId}`);
   }catch(err){
     console.error("Error updating article:", err);
     setError("Failed to update article. Please try again later.");
  } finally {
    setLoading(false);
  }
};

  const validate = (): boolean => {
    const newErrors: { [key: string]: string } = {};

    if (!formData.title) newErrors.title = "Title is required";
    if (!formData.category) newErrors.category = "Categories are required";
    if (!formData.tagsTag) newErrors.tagsTag = "Tags are required";
    if (!formData.body) newErrors.body = "Body is required";
    if (!formData.photoUrl) newErrors.photoUrl = "Photo URL is required";

    if (Object.keys(newErrors).length > 0) {
      console.error("Validation errors:", newErrors);
      setError("Please fix the errors above.");
      return false;
    }

    setError(null);
    return true;
  };

  return (
    <div className="edit-article-container">
    <div className="edit-con-color">
      <div className="edit-container">
        <h1 className="edit-title">Edit Article</h1>
  
        {/* Title and Categories Fields */}
        <div className="article-fields-box sameLine">
          <div className="title-field" style={{ flex: 1, marginRight: "10px" }}>
            <label className="field-title">Title</label>
            <input
              type="text"
              name="title"
              value={formData.title}
              onChange={handleChanges}
            />
          </div>
  
          <div className="article-field-box" style={{ flex: 1 }}>
            <label className="field-title">Categories</label>
            <input
              type="text"
              name="category"
              value={formData.category}
              onChange={handleChanges}
            />
          </div>
        </div>
  
        {/* Tags Select Field */}
        <div className="article-field-box">
          <label className="field-title">Tags</label>
          <select
            name="tagsTag"
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
        <div className="article-field-box">
          <label className="field-title">
            New File Name
            </label>
          <input
            type="text"
            name="author"
            value={fileName}
            readOnly
          />
          </div>
        
        {/* Photo URL Field
        <div className="article-field-box">
          <label className="field-title">Photo URL</label>
          <input
            type="text"
            name="photoUrl"
            value={formData.photoUrl}
            readOnly
          />
        </div> */}
  
        {/* Photo Upload Button */}
        <div className="button-container">
          <button
            type="button"
            onClick={() => document.getElementById("fileInput")?.click()}
            className="upload-button"
          >
            Upload Image
          </button>
  
          {/* Hidden File Input */}
          <input
            id="fileInput"
            type="file"
            accept="image/*"
            onChange={handleImageChanges}
            style={{ display: "none" }}
          />
        </div>
  
        {/* Body Field */}
        <div className="article-field-box">
          <label className="field-title">Body</label>
          <textarea
            name="body"
            value={formData.body}
            onChange={handleChanges}
          />
        </div>
  
        {/* Submit Button */}
        <div className="button-container">
          <button
            className="submit-Update-button"
            type="submit"
            onClick={handleSubmit}
          >
            Update Article
          </button>
          <button
              className="cancel-update-button"
              type="button"
              onClick={handleCancel}
            >
              Cancel
            </button>
        </div>
      </div>
    </div>
    </div>
  
  );
}
