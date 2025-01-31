import { useState } from "react";
import styles from "../Footer/Footer.module.css";
import LeagueImage from "../LeagueAlertsImg.jpg";
import SubscriptionImage from "../../assets/subscription.png";
import { subscribeToNotifications } from "../../features/emailing/subscribingApi";

export default function Footer() {
  const [showPopup, setShowPopup] = useState(false);
  const [email, setEmail] = useState("");
  const [category, setCategory] = useState("NBA");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleSubscribe = async () => {
    setLoading(true);
    setError("");
    setSuccess("");
    try {
      await subscribeToNotifications(email, category);
      setSuccess("Successfully subscribed to " + category);
    } catch (err) {
      setError("Failed to subscribe. Please try again.");
    }
    setLoading(false);
  };

  return (
    <div className={styles.container}>
      <div className={styles.footerContent}>
        {/* Left Section: Logo & Social Media Icons */}
        <div className={styles.leftSection}>
          <div className={styles.imgContainer}>
            <img src={LeagueImage} alt="League Alerts" className={styles.img} />
          </div>
          <div className={styles.icons}>
            <a href="https://www.instagram.com/leaguealerts/" target="_blank" rel="noopener noreferrer">
              <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 24 24" fill="none" stroke="black" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <rect width="20" height="20" x="2" y="2" rx="5" ry="5" />
                <path d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z" />
                <line x1="17.5" x2="17.51" y1="6.5" y2="6.5" />
              </svg>
            </a>
            <a href="https://x.com/leaguealerts" target="_blank" rel="noopener noreferrer">
              <svg xmlns="http://www.w3.org/2000/svg" className={styles.xLogo} width="30" height="30" viewBox="0 0 50 50" stroke="black">
                <path d="M 11 4 C 7.1456661 4 4 7.1456661 4 11 L 4 39 C 4 42.854334 7.1456661 46 11 46 L 39 46 C 42.854334 46 46 42.854334 46 39 L 46 11 C 46 7.1456661 42.854334 4 39 4 L 11 4 z M 11 6 L 39 6 C 41.773666 6 44 8.2263339 44 11 L 44 39 C 44 41.773666 41.773666 44 39 44 L 11 44 C 8.2263339 44 6 41.773666 6 39 L 6 11 C 6 8.2263339 8.2263339 6 11 6 z M 13.085938 13 L 22.308594 26.103516 L 13 37 L 15.5 37 L 23.4375 27.707031 L 29.976562 37 L 37.914062 37 L 27.789062 22.613281 L 36 13 L 33.5 13 L 26.660156 21.009766 L 21.023438 13 L 13.085938 13 z M 16.914062 15 L 19.978516 15 L 34.085938 35 L 31.021484 35 L 16.914062 15 z" />
              </svg>
            </a>
          </div>
        </div>

        {/* Right Section: Subscription */}
        <div className={styles.subscriptionContainer}>
          <p className={styles.textParagraph}>
            Stay updated with the latest articles! Subscribe to notifications.
          </p>
          <img
            src={SubscriptionImage}
            alt="Subscribe to Notifications"
            className={styles.subscribeImage}
            onClick={() => setShowPopup(true)}
          />
        </div>
      </div>

      {showPopup && (
        <div className={styles.popupOverlay}>
          <div className={styles.popup}>
            <h3>Subscribe to Notifications</h3>
            <p>Enter your email and choose a category</p>
            {error && <p className={styles.errorText}>{error}</p>}
            {success && <p className={styles.successText}>{success}</p>}
            <input
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className={styles.inputField}
            />
            <select
              value={category}
              onChange={(e) => setCategory(e.target.value)}
              className={styles.selectField}
            >
              <option value="NBA">NBA</option>
              <option value="NFL">NFL</option>
              <option value="NHL">NHL</option>
              <option value="UFC">UFC</option>
              <option value="MLB">MLB</option>
            </select>
            <div className={styles.buttonGroup}>
              <button onClick={handleSubscribe} className={styles.subscribeConfirmButton} disabled={loading}>
                {loading ? "Subscribing..." : "Subscribe"}
              </button>
              <button onClick={() => setShowPopup(false)} className={styles.closeButton}>
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
