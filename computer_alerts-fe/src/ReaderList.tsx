import React, { useEffect, useState } from "react";
import { testAdminEdpoint } from "./testAdminEdpoint";

interface Reader {
  readerId: number;
  name: string;
  lastname: string;
  // other properties based on the response
}

const ReaderList = () => {
  const [readers, setReaders] = useState<Reader[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchReaders = async () => {
      try {
        const data = await testAdminEdpoint();
        setReaders(data);
      } catch (err) {
        setError("Error fetching data");
      } finally {
        setLoading(false);
      }
    };

    fetchReaders();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div>
      <h2>Readers List</h2>
      <ul>
        {readers.map((reader) => (
          <li key={reader.readerId}>{reader.name}</li>
        ))}
      </ul>
    </div>
  );
};

export default ReaderList;
