INSERT INTO "user" (nickname, name, email)
VALUES ('user1', 'John Doe', 'john.doe@example.com'),
       ('user2', 'Jane Doe', 'jane.doe@example.com'),
       ('user3', 'Alice Smith', 'alice.smith@example.com'),
       ('user4', 'Bob Johnson', 'bob.johnson@example.com'),
       ('user5', 'Eve Brown', 'eve.brown@example.com');

INSERT INTO category (title)
VALUES ('Technology'),
       ('Science'),
       ('Travel'),
       ('Food'),
       ('Sports');

INSERT INTO channel (title, description, image, author_id, create_date, category_id, language)
VALUES ('Tech News', 'Latest tech updates', null, 1, '2023-01-01', 1, 'ru'),
       ('Space Explorers', 'Discover the universe', null, 2, '2023-02-01', 2, 'eng'),
       ('Adventure Travel', 'Explore the world', null, 3, '2023-03-01', 3, 'ua'),
       ('Foodie Delights', 'Delicious recipes', null, 4, '2023-04-01', 4, 'ru'),
       ('Sports Mania', 'Exciting sports coverage', null, 5, '2023-05-01', 5, 'eng');

INSERT INTO user_channel_subscription (channel_id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);