import React, { useState } from "react";
import "./SuccessMessage.css"; // Import your CSS file for styling

type SuccessMessageProps = {
  message: string;
};

export const SuccessMessage: React.FC<SuccessMessageProps> = ({ message }) => {
  return <div className="success-message">{message}</div>;
};

export const ConfirmationPopup: React.FC<{
  onClose: () => void;
  onAccept: () => void;
}> = ({ onClose, onAccept }) => {
  return (
    <div className="confirmation-popup">
      <div className="popup-content">
        <p>Are you sure you want to do this action?</p>
        <button onClick={onAccept}>Accept</button>
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

export const App: React.FC = () => {
  const [showMessage, setShowMessage] = useState(false);
  const [showPopup, setShowPopup] = useState(false);

  const handleAccept = () => {
    setShowMessage(true);
    setShowPopup(false);

    // Hide the success message after 3 seconds
    setTimeout(() => {
      setShowMessage(false);
    }, 3000);
  };

  return (
    <div>
      <button onClick={() => setShowPopup(true)}>Trigger Action</button>
      {showPopup && (
        <ConfirmationPopup
          onClose={() => setShowPopup(false)}
          onAccept={handleAccept}
        />
      )}
      {showMessage && <SuccessMessage message={"Hello my name is correct"} />}
    </div>
  );
};

export default App;
