import React, { useState, ChangeEvent, FormEvent } from "react";
import { ArticleRequestModel } from "../../models/ArticleRequestModel";
import { editArticle } from "features/articles/api/editArticle";
import { useNavigate } from "react-router-dom";

interface EditArticlePageProps {
  article: ArticleRequestModel;
}

export default function EditArticle({
  article,
}: EditArticlePageProps): JSX.Element {
  const [formData, setFormData] = useState<ArticleRequestModel>(article);
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleChanges = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ): void => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleTagChanges = (e: React.ChangeEvent<HTMLSelectElement>): void => {
    const { value } = e.target; // Get the selected value
    setFormData({ ...formData, tagsTag: value }); // Update tagsTag in formData
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    console.log("Submit button clicked");
    if (!validate()) {
      console.log("Validation failed");
      return;
    }

    try {
      console.log("Sending request to editArticle");
      await editArticle(article.articleId, formData);
      alert("Article updated successfully");
      navigate(`/articles/${article.articleId}`);
    } catch (err) {
      console.error("Error updating article:", err);
      setError("Failed to update article. Please try again later.");
    }
  };

  const validate = (): boolean => {
    const newErrors: { [key: string]: string } = {};

    if (!formData.title) newErrors.title = "Title is required";
    if (!formData.category) newErrors.category = "Categories are required";
    if (!formData.tagsTag) newErrors.tagsTag = "Tags are required";
    if (!formData.body) newErrors.body = "Body is required";
    //if (!formData.photoUrl) newErrors.photoUrl = "Photo URL is required";

    if (Object.keys(newErrors).length > 0) {
      console.error("Validation errors:", newErrors);
      setError("Please fix the errors above.");
      return false;
    }

    setError(null);
    return true;
  };

  return (
    <div className="con-color">
      <div className="container">
        <h1 className="center">Edit Article</h1>

        {/* Title and Tags Fields */}
        <div className="sameLine">
          <div className="field" style={{ flex: 1, marginRight: "10px" }}>
            <label>Title</label>
            <input
              type="text"
              name="title"
              value={formData.title}
              onChange={handleChanges}
            />
          </div>

          <div className="field" style={{ flex: 1 }}>
            <label>Categories</label>
            <input
              type="text"
              name="category"
              value={formData.category}
              onChange={handleChanges}
            />
          </div>
        </div>

        {/* Tags Select Field */}
        <div className="field">
          <label>Tags</label>
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

        {/* Description Field
                <div className="field">
                    <label>Description</label>
                    <textarea
                        name="descitpion"
                        value={formData.articleDescpition}
                        onChange={handleChanges}
                    />
                </div> */}

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

        <button
          className="submit-Update"
          type="submit"
          onClick={handleSubmit}
          style={{
            backgroundColor: "#CDA09F", // Set button color
            color: "white", // Text color
            padding: "10px 20px", // Button padding
            borderRadius: "8px", // Rounded corners
            border: "none", // Remove default border
            fontSize: "16px", // Adjust font size
            fontWeight: "bold", // Make text bold
            cursor: "pointer", // Change cursor to pointer on hover
            transition: "all 0.3s ease", // Smooth transition
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)", // Subtle shadow
          }}
          onMouseOver={(e) => {
            e.currentTarget.style.backgroundColor = "#A67C6D"; // Darken the color on hover
            e.currentTarget.style.transform = "translateY(-3px)"; // Lift the button slightly
            e.currentTarget.style.boxShadow = "0 6px 12px rgba(0, 0, 0, 0.2)"; // Darker shadow on hover
          }}
          onMouseOut={(e) => {
            e.currentTarget.style.backgroundColor = "#CDA09F"; // Reset button color
            e.currentTarget.style.transform = "translateY(0)"; // Reset transform
            e.currentTarget.style.boxShadow = "0 4px 8px rgba(0, 0, 0, 0.1)"; // Reset shadow
          }}
          onFocus={(e) => {
            e.currentTarget.style.outline = "none"; // Remove focus outline
            e.currentTarget.style.boxShadow = "0 0 0 4px #A67C6D"; // Add a glowing effect when focused
          }}
          onBlur={(e) => {
            e.currentTarget.style.boxShadow = "0 4px 8px rgba(0, 0, 0, 0.1)"; // Reset shadow after focus
          }}
        >
          Update Article
        </button>
      </div>
    </div>
  );
}
