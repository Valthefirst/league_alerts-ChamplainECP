import React, { useState } from "react";
import axios from "axios";
import { ArticleRequestModelI } from "features/articles/models/ArticleRequestModel";
import {
  SuccessMessage,
  ConfirmationPopup,
} from "../../../assets/SuccessMessage/SuccessMessage"; // Adjust path if necessary

import "./ArticleForm.css";
import { uploadImage } from "features/articles/api/uploadImage";
import { useNavigate } from "react-router-dom";
import { searchTags } from "features/tags/api/searchTag";

const ArticleForm = () => {
  const [formData, setFormData] = useState<ArticleRequestModelI>({
    title: "",
    body: "",
    photoUrl:
      "https://loudounsportstherapy.com/wp-content/uploads/2017/09/canstockphoto2191080-1.jpg",
    wordCount: 222,
    articleStatus: "ARTICLE_REVIEW",
    category: "",
    tagsTag: "NBA",
    timePosted: "",
    authorIdentifier: "3b63de68-9161-4925-b38b-e686dd88f848",
    articleDescpition: "",
    fileName: "",
  });

  const [showPopup, setShowPopup] = useState(false);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [successMessageText, setSuccessMessageText] = useState("");
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [fileName, setFileName] = useState<string>("");
  const navigate = useNavigate();

  const [tagSearch, setTagSearch] = useState(""); // User's search input
  const [tagResults, setTagResults] = useState<string[]>([]); // Search results
  const [showTagResults, setShowTagResults] = useState(false); // Show/hide results


  const handleTagSearch = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const query = e.target.value;
    setTagSearch(query);

    if (query.length > 1) {
      try {
        const results = await searchTags(query); // Fetch matching tags
        setTagResults(results.map((tag) => tag.tagName)); // Extract tag names
        setShowTagResults(true);
      } catch (error) {
        console.error("Error searching tags", error);
        setTagResults([]);
      }
    } else {
      setTagResults([]);
      setShowTagResults(false);
    }
  };

  const handleTagSelect = (tag: string) => {
    setFormData({ ...formData, tagsTag: tag }); // Update selected tag
    setTagSearch(tag); // Update search input
    setShowTagResults(false); // Hide results
  };
  
    const handleAddTag = () => {
      navigate("/addTagForm"); 
    };
 
  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >,
  ) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };


  const handleImageUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setImageFile(file);
      setFileName(file.name);
      alert("Image file selected:" + file.name);
    } else if (!file) {
      setImageFile(null);

      alert("File could not be uploaded:");
    }
  };

  const handleSubmit = async () => {
    try {
      const accessToken = localStorage.getItem("accessToken");
      if (imageFile) {
        const response = await uploadImage(imageFile);
        const photoUrl = response.data;
        console.log("Photo URL:", photoUrl);
        formData.photoUrl = photoUrl;
      }

      const response = await axios.post(
        "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/articles",
        // "http://localhost:8080/api/v1/articles",
        formData,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        },
      );

      console.log("Article created:", response.data);
      setSuccessMessageText("You have successfully created an article.");
      setShowSuccessMessage(true);
      console.error("Error creating article:", formData.wordCount);
      // Hide success message after 3 seconds
      setTimeout(() => {
        setShowSuccessMessage(false);
      }, 3000);
    } catch (error: any) {
      if (error.response && error.response.status === 403) {
        window.location.href = "/unauthorized";
      } else {
        console.error("Error in unlikeArticle API call:", error);
      }
      throw error;
    }
  };

  const handleDraftSubmit = async () => {
    try {
      const accessToken = localStorage.getItem("accessToken");
      const response = await axios.post(
        "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/articles/acceptDraft",
        // "http://localhost:8080/api/v1/articles/acceptDraft",
        formData,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        },
      );
      console.log("Article saved:", response.data);
      setSuccessMessageText(
        "You have successfully created a Draft of your article.",
      );
      // setShowSuccessMessage(true);

      // // Hide success message after 3 seconds
      // setTimeout(() => {
      //   setShowSuccessMessage(false);
      // }, 3000);
    } catch (error: any) {
      if (error.response && error.response.status === 403) {
        window.location.href = "/unauthorized";
      } else {
        console.error("Error in unlikeArticle API call:", error);
      }
      throw error;
    }
  };

  const handlePopupAccept = () => {
    setShowPopup(false);
    handleSubmit();
  };

  return (
    <div>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          setShowPopup(true);
        }}
        className="article-form"
      >
        <h1 className="form-title">Create an Article</h1>

        {/* Title Section */}
        <div className="article-field-box">
          <label htmlFor="title" className="field-title">
            Title
          </label>
          <input
            type="text"
            name="title"
            placeholder="Title"
            value={formData.title}
            onChange={handleChange}
            className="article-form__input"
          />
        </div>

        {/* Category Section */}
        <div className="article-field-box">
          <label htmlFor="category" className="field-title">
            Category
          </label>
          <input
            type="text"
            name="category"
            placeholder="Category"
            value={formData.category}
            onChange={handleChange}
            className="article-form__input"
          />
        </div>

        {/* File Upload Section */}
        <div className="article-field-box">
          <label className="field-title">New File Name</label>
          <input
            type="text"
            name="author"
            value={fileName}
            readOnly
            className="article-form__input"
          />
          <div className="button-container">
            <button
              type="button"
              onClick={() => document.getElementById("fileInput")?.click()}
              className="upload-button"
            >
              Upload Image
            </button>
            <input
              id="fileInput"
              type="file"
              accept="image/*"
              onChange={handleImageUpload}
              style={{ display: "none" }}
            />
          </div>
        </div>

        {/* Body Section */}
        <div className="article-field-box">
          <label htmlFor="body" className="field-title">
            Body
          </label>
          <textarea
            name="body"
            placeholder="Body"
            value={formData.body}
            onChange={handleChange}
            className="article-form__textarea"
          />
        </div>

     {/* Tag Search Section */}
     <div className="article-field-box">
    <label htmlFor="tags" className="field-title">
      Search for Tag
    </label>
    <input
      type="text"
      placeholder="Search tags..."
      value={tagSearch}
      onChange={handleTagSearch}
      className="article-form__input"
    />
    {showTagResults && tagResults.length > 0 && (
      <ul className="tag-dropdown">
        {tagResults.map((tag, index) => (
          <li key={index} onClick={() => handleTagSelect(tag)}>
            {tag}
          </li>
        ))}
      </ul>
    )}
    {showTagResults && tagResults.length === 0 && (
      <div className="no-results">No tags found.</div>
    )}
    </div>

        {/* Display Selected Tag */}
        {formData.tagsTag && (
          <div className="selected-tag">
            <strong>Selected Tag:</strong> {formData.tagsTag}
          </div>
        )}

      <div className="add-tag-button">
      <button type="button" className="submit-button" onClick={handleAddTag}>
        Add Tag
      </button>
    </div>


        {/* Submit Buttons */}
        <div className="row">
          <div className="col-6">
            <button onClick={handleDraftSubmit} className="cancel-button">
              Draft Article
            </button>
          </div>
          <div className="col-6">
            <button type="submit" className="submit-button">
              Create Article
            </button>
          </div>
        </div>
      </form>

      {showPopup && (
        <ConfirmationPopup
          onClose={() => setShowPopup(false)}
          onAccept={handlePopupAccept}
        />
      )}

      {showSuccessMessage && <SuccessMessage message={successMessageText} />}
    </div>
  );
};

export default ArticleForm;
