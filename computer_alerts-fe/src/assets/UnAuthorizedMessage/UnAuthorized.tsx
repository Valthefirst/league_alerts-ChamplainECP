import styles from "./UnAuthorized.module.css";
export default function UnAuthorized() {
  return (
    <div className="container">
      <div className="row">
        <h1 className={styles.h1Class}>
          It seems like you are either not logged in or do not have the
          necessary permission to perform this act.{" "}
        </h1>
      </div>
    </div>
  );
}
