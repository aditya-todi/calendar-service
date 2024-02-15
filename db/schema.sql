CREATE SCHEMA IF NOT EXISTS calendar;

CREATE TABLE IF NOT EXISTS calendar.user (
    user_id uuid,
    email_id CHARACTER VARYING (512) NOT NULL,
    name CHARACTER VARYING (512),
    PRIMARY KEY (user_id),
    CONSTRAINT unq_email_id_tmp UNIQUE(email_id)
);

CREATE TABLE IF NOT EXISTS calendar.event (
    event_id uuid,
    title CHARACTER VARYING (512),
    start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (event_id),
    CONSTRAINT start_end_time CHECK (end_time > start_time)
);

CREATE TABLE IF NOT EXISTS calendar.attendee (
    event_id uuid NOT NULL,
    user_id uuid NOT NULL,
    status CHARACTER VARYING (512),
    PRIMARY KEY (event_id, user_id),
    FOREIGN KEY (event_id) REFERENCES calendar.event (event_id),
    FOREIGN KEY (user_id) REFERENCES calendar.user (user_id)
);