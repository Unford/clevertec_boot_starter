
CREATE TABLE user_sessions
(
    id               VARCHAR(255) NOT NULL,
    login            VARCHAR(255),
    create_date_time TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_user_sessions PRIMARY KEY (id),
    CONSTRAINT uc_user_sessions_login UNIQUE (login)
);




