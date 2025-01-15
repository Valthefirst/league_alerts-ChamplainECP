import React, { useState } from "react";
import axios from "axios";
import { ArticleRequestModelI } from "features/articles/models/ArticleRequestModel";
import {
  SuccessMessage,
  ConfirmationPopup,
} from "../../../assets/SuccessMessage/SuccessMessage"; // Adjust path if necessary

import "./ArticleForm.css";
import { uploadImage } from "features/articles/api/uploadImage";

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
  const [fileName, setFileName] = useState<string>("")

  enum TagsTagEnum {
    Tag1 = "NBA",
    Tag2 = "NHL",
    Tag3 = "UFC",
    Tag4 = "NFL",
    Tag5 = "MLB",
  }

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
      if  (file) {
        setImageFile(file);
        setFileName(file.name);
        alert("Image file selected:" + file.name);
      } else if (!file){
        setImageFile(null);
       
        alert("File could not be uploaded:");
      };
  }


  const handleSubmit = async () => {
    try {
      if(imageFile){
        const response = await uploadImage(imageFile)
        const photoUrl = response.data;
        console.log("Photo URL:", photoUrl);
        formData.photoUrl = photoUrl;
      }

      const response = await axios.post(
        "http://localhost:8080/api/v1/articles",
        formData,
      );
      console.log("Article created:", response.data);
      setSuccessMessageText("You have successfully created an article.");
      setShowSuccessMessage(true);
      console.error("Error creating article:", formData.wordCount);
      // Hide success message after 3 seconds
      setTimeout(() => {
        setShowSuccessMessage(false);
      }, 3000);
      
    } catch (error) {
      console.error("Error creating article:", formData.wordCount);
      console.error("Error creating article:", error);
    }
  };

  const handleDraftSubmit = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/articles/acceptDraft",
        formData,
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
    } catch (error) {
      console.error("Error creating article:", error);
    }
  };

  const handlePopupAccept = () => {
    setShowPopup(false);
    handleSubmit();
  };

  return (
    <div className="article-form-container">
      <form
        onSubmit={(e) => {
          e.preventDefault();
          setShowPopup(true);
        }}
        className="article-form"
      >
        <label htmlFor="title">Title</label>
        <input
          type="text"
          name="title"
          placeholder="Title"
          value={formData.title}
          onChange={handleChange}
          className="article-form__input"
        />

        <label htmlFor="category">Category</label>
        <input
          type="text"
          name="category"
          placeholder="Category"
          value={formData.category}
          onChange={handleChange}
          className="article-form__input"
        />


          <label className="field-title">
            New File Name
            </label>
          <input
            type="text"
            name="author"
            value={fileName}
            readOnly
          />
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
            onChange={handleImageUpload}
            style={{ display: "none" }}
          />
        </div>

        <label htmlFor="body">Body</label>
        <textarea
          name="body"
          placeholder="Body"
          value={formData.body}
          onChange={handleChange}
          className="article-form__textarea"
        />
        <label htmlFor="tags">Tags</label>
        <select
          name="tags"
          value={formData.tagsTag}
          onChange={handleChange}
          className="article-form__select"
        >
          <option value={TagsTagEnum.Tag1}>NBA</option>
          <option value={TagsTagEnum.Tag2}>NHL</option>
          <option value={TagsTagEnum.Tag3}>UFC</option>
          <option value={TagsTagEnum.Tag4}>NFL</option>
          <option value={TagsTagEnum.Tag5}>MLB</option>
        </select>

        <div className="row">
          <div className="col-6">
            <button onClick={handleDraftSubmit} className="article-form-draft">
              Draft Article
            </button>
          </div>
          <div className="col-6">
            <button type="submit" className="article-form__button">
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
