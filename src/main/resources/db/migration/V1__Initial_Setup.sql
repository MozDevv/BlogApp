CREATE TABLE post
(
    postId      BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    content     TEXT NOT NULL,
    image       VARCHAR(255) NOT NULL,
    dateCreated VARCHAR(55) NOT NULL
);
