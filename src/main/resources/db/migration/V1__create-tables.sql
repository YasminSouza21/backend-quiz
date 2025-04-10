CREATE TABLE users(
    id bigint primary key auto_increment,
    name varchar(100) not null,
    email varchar(50) not null unique,
    password varchar(50) not null,
    birthday date not null,
    age int not null
);

CREATE TABLE categories(
    id bigint primary key auto_increment,
    name varchar(50) not null
);
CREATE TABLE questions(
    id bigint primary key auto_increment,
    text text not null,
    category_id bigint,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE responses(
    id bigint primary key auto_increment,
    text text not null,
    question_id bigint,
    category_id bigint,
    is_correct tinyint,
    response_correct text,
    FOREIGN KEY (question_id) REFERENCES questions(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE games(
    id bigint primary key auto_increment,
    user_id bigint,
    category_id bigint,
    score int,
    time_seconds int,
    start_date timestamp,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

ALTER TABLE questions ADD COLUMN game_id bigint;
ALTER TABLE questions ADD CONSTRAINT fk_game_id FOREIGN KEY (game_id) REFERENCES games(id);