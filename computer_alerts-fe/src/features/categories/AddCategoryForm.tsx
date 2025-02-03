import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

import "./AddCategoryForm.css";
import { fetchAllsArticles } from "./api/getAllCategories";
import { addNewCategories } from "./api/addNewCategory";


// Category Model
interface Category {
  id: string;
  categoryName: string;
}

const CategoryForm: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [categories, setCategories] = useState<Category[]>([]);
  const [newCategory, setNewCategory] = useState("");
  const navigate = useNavigate(); // Hook to handle navigation

  // Fetch categories from API
  useEffect(() => {
    const getCategories = async () => {
      try {
        const data = await fetchAllsArticles();

        const formattedData: Category[] = data.map((category) => ({
          id: category.id,
          categoryName: category.categoryName,
        }));

        setCategories(formattedData);
      } catch (err) {
        console.error("Failed to fetch categories:", err);
      }
    };

    getCategories();
  }, []);

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!newCategory.trim()) return;

    try {
      const newCat = await addNewCategories(newCategory);

      // Now newCat will have both id and categoryName
      const formattedData: Category = {
        id: newCat.categoryId,          // Correctly access 'id'
        categoryName: newCat.categoryName,  // Correctly access 'categoryName'
      };

      setCategories([...categories, formattedData]); // Add the new category to the list
      setNewCategory(""); // Reset input field
      alert("Category created successfully!");
      window.location.reload(); // Optionally reload to refresh data
    } catch (err) {
      console.error("Error creating category:", err);
    }
  };

  // Handle cancel action (go back to home)
  const handleCancel = () => {
    navigate("/"); // Redirect to the home page
  };

  return (
    <div className="category-container">
      <div className="category-form">
        <h1 className="form-title">{id ? "Edit Category" : "Add New Category"}</h1>
        <form onSubmit={onSubmit}>
          <div className="category-field-box">
            <label htmlFor="categoryName">New Category Name</label>
            <input
              type="text"
              className="form-control"
              id="categoryName"
              name="categoryName"
              placeholder="Enter Category Name"
              value={newCategory}
              onChange={(e) => setNewCategory(e.target.value)}
            />
          </div>
          <div className="button-container">
            <button type="submit" className="category-submit-button">
              {id ? "Update Category" : "Create Category"}
            </button>
            <button type="button" className="cancel-button" onClick={handleCancel}>
              Cancel
            </button>
          </div>
        </form>

        {/* Existing Categories inside the form */}
        <div className="existing-categories">
          <h2>Existing Categories</h2>
          <ul>
            {categories.map((category) => (
              <li key={category.id}>{category.categoryName}</li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default CategoryForm;
