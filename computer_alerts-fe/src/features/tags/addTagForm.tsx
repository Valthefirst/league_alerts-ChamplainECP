import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { addNewTag } from "./api/addNewtags";
import { getAllTags } from "./api/getAllTags";
import "./addTagForm.css";

// Tag Model
interface Tags {
  id: string;
  tagName: string;
}

const TagForm: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [tag, setTag] = useState<Tags[]>([]);
  const [newTag, setNewTag] = useState("");
  const navigate = useNavigate(); // Hook to handle navigation

  // Fetch tags from API
  useEffect(() => {
    const getTags = async () => {
      try {
        const data = await getAllTags();

        const formattedData: Tags[] = data.map((tag) => ({
          id: tag.tagId,
          tagName: tag.tagName,
        }));

        setTag(formattedData);
      } catch (err) {
        console.error("Failed to fetch tags:", err);
      }
    };

    getTags();
  }, []);

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!newTag.trim()) return;

    try {
      const nTag = await addNewTag(newTag);

      const formattedData: Tags = {
        id: nTag.tagId,
        tagName: nTag.tagName,
      };

      setTag([...tag, formattedData]); // Add the new tag to the list
      setNewTag(""); // Reset input field
      alert("Tag created successfully!");
      window.location.reload(); // Optionally reload to refresh data
    } catch (err) {
      console.error("Error creating tag:", err);
    }
  };

  // Handle cancel action (go back to home)
  const handleCancel = () => {
    navigate("/"); // Redirect to the home page
  };

  return (
    <div className="tag-container">
      <div className="tag-form">
        <h1 className="form-title">{id ? "Edit Tag" : "Add New Tag"}</h1>
        <form onSubmit={onSubmit}>
          <div className="tag-field-box">
            <label htmlFor="tagName">New Tag Name</label>
            <input
              type="text"
              className="form-control"
              id="tagName"
              name="tagName"
              placeholder="Enter Tag Name"
              value={newTag}
              onChange={(e) => setNewTag(e.target.value)}
            />
          </div>
          <div className="button-container">
            <button type="submit" className="tag-submit-button">
              {id ? "Update Tag" : "Create Tag"}
            </button>
            <button type="button" className="cancel-button" onClick={handleCancel}>
              Cancel
            </button>
          </div>
        </form>

        {/* Existing Tags inside the form */}
        <div className="existing-tags">
          <h2>Existing Tags</h2>
          <ul>
            {tag.map((tag) => (
              <li key={tag.id}>{tag.tagName}</li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default TagForm;
