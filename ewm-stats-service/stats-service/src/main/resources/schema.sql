DROP TABLE IF EXISTS stats;

CREATE TABLE IF NOT EXISTS stats (
id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
app VARCHAR NOT NULL,
uri VARCHAR NOT NULL,
ip VARCHAR NOT NULL,
timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
CONSTRAINT pk_stats PRIMARY KEY (id)
);