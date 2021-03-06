CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT,
    password TEXT
);

CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    created TIMESTAMP DEFAULT NOW(),
    done BOOLEAN DEFAULT false,
    user_id INTEGER NOT NULL REFERENCES users(id)
);