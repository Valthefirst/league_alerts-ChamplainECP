import React, { useState } from "react";
import "./CheckMark.css";

interface CircleCheckButtonProps {
  onClick?: (isFilled: boolean) => void;
  messageProp: string; // Prop for the success message
  fillColor?: string; // Color for the filled state
  strokeColor?: string; // Color for the stroke
  messageDuration?: number; // Duration for the success message
}

const CircleCheckButton: React.FC<CircleCheckButtonProps> = ({
  onClick,
  messageProp,
  fillColor = "green", // Default fill color
  strokeColor = "black", // Default stroke color
  messageDuration = 3000, // Default message display time
}) => {
  const [isFilled, setIsFilled] = useState(false);
  const [showMessage, setShowMessage] = useState(false);

  const handleClick = () => {
    const newFilledState = !isFilled; // Determine the new filled state
    setIsFilled(newFilledState);

    if (newFilledState) {
      setShowMessage(true);
      setTimeout(() => {
        setShowMessage(false);
      }, messageDuration);
    }

    if (onClick) {
      onClick(newFilledState);
    }
  };

  return (
    <div className="circle-check-container">
      {/* SVG Icon */}
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill={isFilled ? fillColor : "none"}
        stroke={strokeColor}
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
        className={`circle-check-button ${isFilled ? "filled" : ""}`}
        onClick={handleClick}
        style={{ cursor: "pointer", transition: "transform 0.2s ease-in-out" }}
        aria-label={`Approve ${messageProp}`} // Accessibility label
        tabIndex={0} // Allow keyboard focus
        onKeyDown={(e) => e.key === "Enter" && handleClick()} // Handle Enter key
      >
        <circle cx="12" cy="12" r="10" />
        <path d="m9 12 2 2 4-4" />
      </svg>

      {/* Success Message */}
      {showMessage && (
        <div className="success-message">
          You have approved the <strong>{messageProp}</strong> of the selected
          article.
        </div>
      )}
    </div>
  );
};

export default CircleCheckButton;
