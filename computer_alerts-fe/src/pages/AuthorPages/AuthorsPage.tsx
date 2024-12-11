import AuthorList from "features/authors/components/AuthorList";

export default function AuthorsPage() {
  return (
    <div>
      <h1 style={{ textAlign: "center" }}>All of Your Authors</h1>
      <AuthorList />
    </div>
  );
}
