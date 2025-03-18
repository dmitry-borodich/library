CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS libraries (
     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
     address TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS books (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(50),
    author VARCHAR(50),
    year INT,
    is_in_library BOOLEAN,
    library_id UUID,
    FOREIGN KEY (library_id) REFERENCES libraries (id)
);

CREATE TABLE IF NOT EXISTS readers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50),
    age INT,
    passport_number VARCHAR(50),
    library_id UUID,
    FOREIGN KEY (library_id) REFERENCES libraries (id)
);

CREATE TABLE IF NOT EXISTS history (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    book_id UUID,
    reader_id UUID,
    date DATE,
    is_returned BOOLEAN,
    library_id UUID,
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (reader_id) REFERENCES readers (id) ON DELETE CASCADE,
    FOREIGN KEY (library_id) REFERENCES libraries (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS books_library_id_idx ON books(library_id);
CREATE INDEX IF NOT EXISTS readers_library_id_idx ON readers(library_id);
CREATE INDEX IF NOT EXISTS history_book_id_idx ON history(book_id);
CREATE INDEX IF NOT EXISTS history_reader_id_idx ON history(reader_id);
CREATE INDEX IF NOT EXISTS history_library_id_idx ON history(library_id);