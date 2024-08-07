DROP TABLE IF EXISTS participation_requests;
DROP TABLE IF EXISTS compilations_events;
DROP TABLE IF EXISTS compilations;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS categories;

-- users
CREATE TABLE IF NOT EXISTS users (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
email VARCHAR(254) NOT NULL CHECK (CHAR_LENGTH(email) >= 6 AND CHAR_LENGTH(email) <= 254),
name VARCHAR(250) NOT NULL CHECK (CHAR_LENGTH(name) >= 2 AND CHAR_LENGTH(name) <= 250),
CONSTRAINT pk_users PRIMARY KEY(id),
CONSTRAINT uq_users_email UNIQUE(email)
);
--

-- locations
CREATE TABLE IF NOT EXISTS locations (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
lat FLOAT,
lon FLOAT,
radius FLOAT,
name VARCHAR,
description VARCHAR,
type VARCHAR(9),
CONSTRAINT pk_locations PRIMARY KEY (id)
);
--

-- categories
CREATE TABLE IF NOT EXISTS categories (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
name VARCHAR(50) NOT NULL CHECK (CHAR_LENGTH(name) >= 1 AND CHAR_LENGTH(name) <= 50),
CONSTRAINT pk_categories PRIMARY KEY(id),
CONSTRAINT uq_categories_name UNIQUE(name)
);
--

-- events
CREATE TABLE IF NOT EXISTS events (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
annotation VARCHAR(2000) NOT NULL CHECK (CHAR_LENGTH(annotation) >= 20 AND CHAR_LENGTH(annotation) <= 2000),
category_id BIGINT NOT NULL,
description VARCHAR(7000) NOT NULL CHECK (CHAR_LENGTH(description) >= 20 AND CHAR_LENGTH(description) <= 7000),
event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
location_id BIGINT NOT NULL,
paid boolean NOT NULL,
participant_limit INTEGER NOT NULL,
request_moderation boolean NOT NULL,
title VARCHAR(120) NOT NULL CHECK (CHAR_LENGTH(title) >= 3 AND CHAR_LENGTH(title) <= 120),
user_id BIGINT NOT NULL,
state VARCHAR(9) NOT NULL,
created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
published_on TIMESTAMP WITHOUT TIME ZONE,
CONSTRAINT pk_events PRIMARY KEY(id),
CONSTRAINT fk_events_category FOREIGN KEY(category_id) REFERENCES categories (id),
CONSTRAINT fk_events_users FOREIGN KEY(user_id) REFERENCES users (id),
CONSTRAINT fk_events_locations FOREIGN KEY(location_id) REFERENCES locations (id)
);
--

-- participation_requests
CREATE TABLE IF NOT EXISTS participation_requests(
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
event_id BIGINT NOT NULL,
requester_id BIGINT NOT NULL,
status VARCHAR(9) NOT NULL,
CONSTRAINT pk_participation_requests PRIMARY KEY (id),
CONSTRAINT fk_participation_requests_events FOREIGN KEY (event_id) REFERENCES events (id),
CONSTRAINT fk_participation_requests_users FOREIGN KEY (requester_id) REFERENCES users(id),
CONSTRAINT uq_participation_requests_event_id_requester_id UNIQUE(event_id, requester_id)
);
--

-- compilations
CREATE TABLE IF NOT EXISTS compilations(
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
pinned boolean NOT NULL,
title VARCHAR(50) NOT NULL CHECK (CHAR_LENGTH(title) >= 1 AND CHAR_LENGTH(title) <= 50),
CONSTRAINT pk_compilations PRIMARY KEY(id),
CONSTRAINT uq_compilations_title UNIQUE(title)
);
--

-- compilations_events
CREATE TABLE IF NOT EXISTS compilations_events(
compilation_id BIGINT NOT NULL,
event_id BIGINT NOT NULL,
CONSTRAINT pk_compilations_events PRIMARY KEY (compilation_id, event_id),
CONSTRAINT fk_compilations_events_compilations FOREIGN KEY (compilation_id) REFERENCES compilations(id),
CONSTRAINT fk_compilations_events_events FOREIGN KEY (event_id) REFERENCES events(id)
);
--

--CREATE OR REPLACE FUNCTION distance(lat1 float, lon1 float, lat2 float, lon2 float)
--    RETURNS float
--AS
--'
--declare
--    dist float = 0;
--    rad_lat1 float;
--    rad_lat2 float;
--    theta float;
--    rad_theta float;
--BEGIN
--    IF lat1 = lat2 AND lon1 = lon2
--    THEN
--        RETURN dist;
--    ELSE
--        -- переводим градусы широты в радианы
--        rad_lat1 = pi() * lat1 / 180;
--        -- переводим градусы долготы в радианы
--        rad_lat2 = pi() * lat2 / 180;
--        -- находим разность долгот
--        theta = lon1 - lon2;
--        -- переводим градусы в радианы
--        rad_theta = pi() * theta / 180;
--        -- находим длину ортодромии
--        dist = sin(rad_lat1) * sin(rad_lat2) + cos(rad_lat1) * cos(rad_lat2) * cos(rad_theta);
--
--        IF dist > 1
--            THEN dist = 1;
--        END IF;
--
--        dist = acos(dist);
--        -- переводим радианы в градусы
--        dist = dist * 180 / pi();
--        -- переводим градусы в километры
--        dist = dist * 60 * 1.8524;
--
--        RETURN dist;
--    END IF;
--END;
--'
--LANGUAGE PLPGSQL;