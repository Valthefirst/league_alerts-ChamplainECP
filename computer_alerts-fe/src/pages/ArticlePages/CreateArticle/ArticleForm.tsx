import React, { useState } from "react";
import axios from "axios";
import { ArticleRequestModelI } from "features/articles/models/ArticleRequestModel";
import {
  SuccessMessage,
  ConfirmationPopup,
} from "../../../assets/SuccessMessage/SuccessMessage"; // Adjust path if necessary

import "./ArticleForm.css";

const ArticleForm = () => {
  const [formData, setFormData] = useState<ArticleRequestModelI>({
    title: "",
    body: "",
    photoUrl:
      "https://loudounsportstherapy.com/wp-content/uploads/2017/09/canstockphoto2191080-1.jpg",
    wordCount: 222,
    articleStatus: "ARTICLE_REVIEW",
    tags: "",
    tagsTag: "NBA",
    timePosted: "",
    authorIdentifier: "3b63de68-9161-4925-b38b-e686dd88f848",
  });

  const [showPopup, setShowPopup] = useState(false);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [successMessageText, setSuccessMessageText] = useState("");

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

  const handleSubmit = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/articles",
        formData,
      );
      console.log("Article created:", response.data);
      setSuccessMessageText("You have successfully created an article.");
      setShowSuccessMessage(true);

      // Hide success message after 3 seconds
      setTimeout(() => {
        setShowSuccessMessage(false);
      }, 3000);
    } catch (error) {
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
      setShowSuccessMessage(true);

      // Hide success message after 3 seconds
      setTimeout(() => {
        setShowSuccessMessage(false);
      }, 3000);
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
        <label htmlFor="photoUrl">PhotoUrl</label>
        <input
          type="text"
          name="photoUrl"
          placeholder="PhotoUrl"
          value={formData.photoUrl}
          onChange={handleChange}
          className="article-form__input"
        />
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
          value={formData.tags}
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
            <button
              type="submit"
              onClick={handleDraftSubmit}
              className="article-form-draft__button"
            >
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
