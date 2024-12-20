import styles from "../Footer/Footer.module.css";
import LeagueImage from "../LeagueAlertsImg.jpg";

export default function Footer() {
  return (
    <div className={styles.container}>
      <div className="row">
        <div className="col-6">
          <div className={styles.imgContainer}>
            <img src={LeagueImage} alt="League Alerts" className={styles.img} />
          </div>
        </div>
        <div className="col-6">
          <div className="row">
            <div className={styles.icons}>
              <a
                href="https://www.instagram.com/leaguealerts/"
                target="_blank"
                rel="noopener noreferrer"
                className={styles.instagram}
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="40"
                  height="40"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="black"
                  strokeWidth="2"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  className="lucide lucide-instagram"
                >
                  <rect width="20" height="20" x="2" y="2" rx="5" ry="5" />
                  <path d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z" />
                  <line x1="17.5" x2="17.51" y1="6.5" y2="6.5" />
                </svg>
              </a>

              <a
                href="https://x.com/leaguealerts"
                target="_blank"
                rel="noopener noreferrer"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  x="0px"
                  y="0px"
                  width="40"
                  stroke="black"
                  height="40"
                  viewBox="0 0 50 50"
                >
                  <path d="M 11 4 C 7.1456661 4 4 7.1456661 4 11 L 4 39 C 4 42.854334 7.1456661 46 11 46 L 39 46 C 42.854334 46 46 42.854334 46 39 L 46 11 C 46 7.1456661 42.854334 4 39 4 L 11 4 z M 11 6 L 39 6 C 41.773666 6 44 8.2263339 44 11 L 44 39 C 44 41.773666 41.773666 44 39 44 L 11 44 C 8.2263339 44 6 41.773666 6 39 L 6 11 C 6 8.2263339 8.2263339 6 11 6 z M 13.085938 13 L 22.308594 26.103516 L 13 37 L 15.5 37 L 23.4375 27.707031 L 29.976562 37 L 37.914062 37 L 27.789062 22.613281 L 36 13 L 33.5 13 L 26.660156 21.009766 L 21.023438 13 L 13.085938 13 z M 16.914062 15 L 19.978516 15 L 34.085938 35 L 31.021484 35 L 16.914062 15 z" />
                </svg>
              </a>
            </div>
          </div>
          <div className="row">
            <p className={styles.textParagraph}>
              Thanks for accepting us we try our best to provide you with the
              best articles. Our team is working hard day in and day out to
              provide our readers the most up to date news in the world of
              sports.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
