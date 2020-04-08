DROP TABLE IF EXISTS news_topics;
DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS topics;
CREATE TABLE news(id bigint IDENTITY PRIMARY KEY, title VARCHAR(50), description VARCHAR(250), created_date timestamp, last_modified_date timestamp, version bigint, status VARCHAR(20));
CREATE TABLE topics(id bigint IDENTITY PRIMARY KEY, name VARCHAR(50), description VARCHAR(250), created_date timestamp, last_modified_date timestamp, version bigint);
CREATE TABLE news_topics (news_id bigint not null, tag_id bigint not null, primary key (news_id, tag_id));
ALTER TABLE news_topics ADD CONSTRAINT FK7yp6i5syt6dtm9bj5ukkgoxlx FOREIGN KEY(tag_id) REFERENCES topics;
ALTER TABLE news_topics ADD CONSTRAINT  FKf7dm39p6brwxbmobamsill7og FOREIGN KEY (news_id) REFERENCES news;
